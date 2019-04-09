import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Technician {

    private static String QUEUE_NAME_1 = "HIP";
    private static String QUEUE_NAME_2 = "KNEE";
    private static String QUEUE_NAME_3 = "ELBOW";
    private static String EXCHANGE_NAME = "doctor-technician";
    private static String EXCHANGE_NAME_ADMIN = "admin-exchange";
    private static Channel channel;

    public static void main(String[] argv) throws Exception {

        // info
        System.out.println("TECHNICIAN");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        // exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(EXCHANGE_NAME_ADMIN, BuiltinExchangeType.DIRECT);
        ListenAdmin listenAdmin = new ListenAdmin();
        listenAdmin.start();

        String queue_1;
        String queue_2;
        String key;


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter first body part: ");
        key = br.readLine();

        while ((queue_1 = create_queue(key))==null){
            System.out.println("Enter first body part: ");
            key = br.readLine();
        }

        System.out.println("Enter second body part: ");
        key= br.readLine();
        while ((queue_2 = create_queue(key))==null){
            System.out.println("Enter second body part: ");
            key = br.readLine();
        }

        // consumer (message handling)
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String key = envelope.getRoutingKey();
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(properties.getCorrelationId())
                        .build();

                String message = new String(body, "UTF-8");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("Received message: %s, key: %s\n", message, key);
                message ="NAME: " + message + " ,BODY PART: " + key + " done\n";
                channel.basicPublish("", properties.getReplyTo(), replyProps, message.getBytes("UTF-8"));
                System.out.println("Send response\n");
                message = "Message from technician: " + " " + message;
                channel.basicPublish(EXCHANGE_NAME_ADMIN, "", null, message.getBytes("UTF-8"));
                System.out.printf("Send mess to admin: %s \n", message);
            }
        };

        // start listening
        System.out.println("Waiting for messages...");
        channel.basicConsume(queue_1, true, consumer);
        channel.basicConsume(queue_2, true, consumer);

    }

    private static String create_queue(String key) throws IOException {

            if (key.equals(QUEUE_NAME_1.toLowerCase())) {
                // queue & bind
                channel.queueDeclare(QUEUE_NAME_1, false, false, false, null);
                channel.queueBind(QUEUE_NAME_1, EXCHANGE_NAME, key);
                System.out.println("created queue: " + QUEUE_NAME_1);
                return QUEUE_NAME_1;
            }
            else if(key.equals(QUEUE_NAME_2.toLowerCase())){
                channel.queueDeclare(QUEUE_NAME_2, false, false, false, null);
                channel.queueBind(QUEUE_NAME_2, EXCHANGE_NAME, key);
                System.out.println("created queue: " + QUEUE_NAME_2);
                return QUEUE_NAME_2;
            }
            else if(key.equals(QUEUE_NAME_3.toLowerCase())){
                channel.queueDeclare(QUEUE_NAME_3, false, false, false, null);
                channel.queueBind(QUEUE_NAME_3, EXCHANGE_NAME, key);
                System.out.println("created queue: " + QUEUE_NAME_3);
                return QUEUE_NAME_3;
            }
            else {
                System.out.println("We dont check such body part:(\n");
                return null;
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
                channel.queueBind(queueName, EXCHANGE_NAME_ADMIN, "2");
            } catch (IOException e) {
                e.printStackTrace();
            }
           // System.out.println("created queue: " + queueName);

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
