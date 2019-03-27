import org.jgroups.*;
import org.jgroups.util.Util;
import pl.edu.agh.dsrg.sr.protos.HashMapMessageProtos.HashMapMessage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Vector;


public class Receiver extends ReceiverAdapter {

    private DistributedMap state;
    private JChannel jChannel;

    public Receiver(DistributedMap local_hash_map, JChannel jChannel){
        this.state = local_hash_map;
        this.jChannel = jChannel;
    }

    public void getState(OutputStream output) throws Exception {
        synchronized(state) {
            Util.objectToStream(state.getLocalHashMap(), new DataOutputStream(output));
        }
    }

    public void setState(InputStream input) throws Exception {
        HashMap <String, Integer> hashMap;
        hashMap=(HashMap<String, Integer>)Util.objectFromStream(new DataInputStream(input));
        synchronized(state) {
            state.update(hashMap);
        }
    }

    public void receive(Message msg) {
        //System.out.println(msg.getSrc() + ": " + msg.getObject());
        HashMapMessage hashMapMessage = (HashMapMessage) msg.getObject();
        switch (hashMapMessage.getType()){
            case PUT:
                state.getLocalHashMap().put(hashMapMessage.getKey(), hashMapMessage.getValue());
                break;
            case REMOVE:
                state.getLocalHashMap().remove(hashMapMessage.getKey());
            break;
        }
    }
    public void viewAccepted(View new_view) {
        handleView(jChannel, new_view);
    }

    private static void handleView(JChannel ch, View new_view) {
        if(new_view instanceof MergeView) {
            ViewHandler handler=new ViewHandler(ch, (MergeView)new_view);
            // requires separate thread as we don't want to block JGroups
            handler.start();
        }
    }

    private static class ViewHandler extends Thread {
        JChannel ch;
        MergeView view;

        private ViewHandler(JChannel ch, MergeView view) {
            this.ch=ch;
            this.view=view;
        }

        public void run() {
            Vector<View> subgroups= (Vector<View>) view.getSubgroups();
            View tmp_view=subgroups.firstElement(); // picks the first
            Address local_addr=ch.getAddress();
            if(!tmp_view.getMembers().contains(local_addr)) {
                System.out.println("Not member of the new primary partition ("
                        + tmp_view + "), will re-acquire the state");
                try {
                    ch.getState(null, 30000);
                }
                catch(Exception ex) {
                }
            }
            else {
                System.out.println("Not member of the new primary partition ("
                        + tmp_view + "), will do nothing");
            }
        }
    }
}
