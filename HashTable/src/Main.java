import java.util.Scanner;

public class Main {

    public static void main(String []args) throws Exception {
        System.setProperty("java.net.preferIPv4Stack","true");
        DistributedMap distributedMap = new DistributedMap("HashMapCluster");
        Scanner scanner = new Scanner(System.in);
        String message;
        while(true){
            System.out.println("Write: ");
            message = scanner.nextLine();
            if (message.equals("q")) break;
            readMessage(message, distributedMap);
        }
    }

    private static Object readMessage(String message, DistributedMap distributedMap){
        String []splited = message.split(" ");
        if (splited.length<2) return "At least TYPE and ARG";
        else {
            switch (splited[0]) {
                case "put":
                    if (splited.length==3)
                        return distributedMap.put(splited[1], Integer.parseInt(splited[2]));
                    else return "PUT need 2 ARGS";
                case "remove":
                    return distributedMap.remove(splited[1]);
                case "containsKey":
                    return distributedMap.containsKey(splited[1]);
                case "get":
                    return distributedMap.get(splited[1]);
            }
            return "Wrong type";
        }
    }
}
