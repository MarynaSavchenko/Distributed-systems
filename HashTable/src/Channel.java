import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.protocols.*;
import org.jgroups.protocols.pbcast.*;
import org.jgroups.stack.ProtocolStack;
import pl.edu.agh.dsrg.sr.protos.HashMapMessageProtos;

import java.net.InetAddress;
import java.util.HashMap;

public class Channel extends JChannel {

    public Channel(String cluster_name, DistributedMap local_hash_map) throws Exception {
        super(false);
        ProtocolStack stack=new ProtocolStack();
        this.setProtocolStack(stack);
        stack.addProtocol(new UDP().setValue("mcast_group_addr", InetAddress.getByName("230.100.213.3")))
                .addProtocol(new PING())
                .addProtocol(new MERGE3())
                .addProtocol(new FD_SOCK())
                .addProtocol(new FD_ALL().setValue("timeout", 12000).setValue("interval", 3000))
                .addProtocol(new VERIFY_SUSPECT())
                .addProtocol(new BARRIER())
                .addProtocol(new NAKACK2())
                .addProtocol(new UNICAST3())
                .addProtocol(new STABLE())
                .addProtocol(new GMS())
                .addProtocol(new UFC())
                .addProtocol(new MFC())
                .addProtocol(new FRAG2())
                .addProtocol(new SEQUENCER())
                .addProtocol(new FLUSH())
                .addProtocol(new STATE());

        stack.init();
        this.setReceiver(new Receiver(local_hash_map, this));
        this.connect(cluster_name);
        this.getState(null, 0);
    }

    public void send(HashMapMessageProtos.HashMapMessage message) throws Exception {
        Message msg = new Message(null, null, message);
        this.send(msg);
    }
}
