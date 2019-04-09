import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Doctor {

    static final String corrId = UUID.randomUUID().toString();
    static Channel channel;
    // exchange
    private static String EXCHANGE_NAME = "doctor-technician";
    private static String EXCHANGE_NAME_ADMIN = "admin-exchange";

    public static void main(String[] argv) throws Exception {

        // info
        System.out.println("DOCTOR");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(EXCHANGE_NAME_ADMIN, BuiltinExchangeType.DIRECT);
        ListenAdmin listenAdmin = new ListenAdmin();
        listenAdmin.start();

        String key="";

        while (true) {

            String callbackQueueName = channel.queueDeclare().getQueue();
            BasicProperties props = new BasicProperties
                    .Builder()
                    .correlationId(corrId)
                    .replyTo(callbackQueueName)
                    .build();
            // read msg
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while(!key.equals("knee")&&!key.equals("elbow")&&!key.equals("hip")) {
                System.out.println("Enter body part: ");
                key = br.readLine();
            }
            System.out.println("Enter name: ");
            String message = br.readLine();

            // break condition
            if ("exit".equals(message)) {
                break;
            }

            // publish
            channel.basicPublish(EXCHANGE_NAME, key, props, message.getBytes("UTF-8"));
            System.out.printf("Send mess to technik: %s with key: %s\n", message, key);
            message = "Message from doctor: NAME: " + message + " ,BODY PART: " + key;
            channel.basicPublish(EXCHANGE_NAME_ADMIN, "", null, message.getBytes("UTF-8"));
            System.out.printf("Send mess to admin: %s \n", message);

            GetResponseFromTechnician myThread = new GetResponseFromTechnician(callbackQueueName);
            myThread.start();
            key = "";
        }
    }

    public static class GetResponseFromTechnician extends Thread {
        String callbackQueueName;

        public GetResponseFromTechnician(String callbackQueueName){
            this.callbackQueueName = callbackQueueName;
        }

        public void run(){
            final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
            String ctag = null;
            try {
                ctag = channel.basicConsume(callbackQueueName, true, (consumerTag, delivery) -> {
                    if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                        response.offer(new String(delivery.getBody(), "UTF-8"));
                    }
                }, consumerTag -> {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            String result = null;
            try {
                result = response.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Get message from technician: " + result);
            try {
                channel.basicCancel(ctag);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ListenAdmin extends Thread {
        public void run()  {
            // queue & bind
            String queueName = null;
            try {
                queueName = channel.queueDeclare().getQueue();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                channel.queueBind(queueName, EXCHANGE_NAME_ADMIN, "1");
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println("created queue: " + queueName);

            // consumer (message handling)
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("Received message from admin: " + message);
                }
            };

            // start listening
            //System.out.println("Waiting for messages...");
            try {
                channel.basicConsume(queueName, true, consumer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
