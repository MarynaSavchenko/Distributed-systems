import com.rabbitmq.client.*;

import java.io.*;


public class Admin {

    // exchange
    private static String EXCHANGE_NAME = "admin-exchange";

    public static void main(String[] argv) throws Exception {

        // info
        System.out.println("ADMIN");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        SendMess sendMess = new SendMess(channel);
        sendMess.start();

        // queue & bind
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println("created queue: " + queueName);
        File file = new File("append.txt");


        // consumer (message handling)
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received: " + message);
                FileWriter fr = new FileWriter(file, true);
                fr.write(message+"\n");
                fr.close();
            }
        };

        // start listening
        System.out.println("Waiting for messages...");
        channel.basicConsume(queueName, true, consumer);
    }

    private static class SendMess extends Thread{
        Channel channel;

        public SendMess(Channel channel){
            this.channel = channel;
        }
        public void run() {
            while (true) {
                String key = "";
                String message = "";
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Do you want to send a message? Choose 1 for DOCTOR or 2 for TECHNICIAN: ");
                try {
                    while (!key.equals("1")&&!key.equals("2"))
                        key = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Write your message: ");
                try {
                    message = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    channel.basicPublish(EXCHANGE_NAME, key, null, message.getBytes("UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Sent message to : " + key + " " + message);
            }
        }

    }
}
