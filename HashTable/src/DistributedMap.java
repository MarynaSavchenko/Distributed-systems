import pl.edu.agh.dsrg.sr.protos.HashMapMessageProtos.HashMapMessage;

import java.util.HashMap;

public class DistributedMap implements SimpleStringMap {

    private HashMap<String, Integer> local_hash_map;
    private Channel channel;
    HashMapMessage hashMapMessage;

    public DistributedMap(String cluster_name) throws Exception {
        this.local_hash_map = new HashMap<>();
        this.channel = new Channel(cluster_name, this);
    }

    public HashMap<String,Integer> getLocalHashMap(){
        return local_hash_map;
    }
    @Override
    public boolean containsKey(String key) {
        return local_hash_map.containsKey(key);
    }

    @Override
    public Integer get(String key) {
        return local_hash_map.get(key);
    }

    @Override
    public Integer put(String key, Integer value) {
        try {
            hashMapMessage = HashMapMessage.newBuilder()
                    .setType(HashMapMessage.HashMapType.PUT)
            .setKey(key)
            .setValue(value)
            .build();
            channel.send(hashMapMessage);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Error while putting pair to map");
        }
        return local_hash_map.put(key, value);
    }

    @Override
    public Integer remove(String key) {
        try {
            hashMapMessage = HashMapMessage.newBuilder()
                    .setType(HashMapMessage.HashMapType.REMOVE)
                    .setKey(key)
                    .build();
            channel.send(hashMapMessage);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Error while removing key");
        }
        return local_hash_map.remove(key);
    }


    public void update(HashMap<String, Integer> hashMap) {
        local_hash_map.clear();
        local_hash_map.putAll(hashMap);
    }


}
