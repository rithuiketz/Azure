package com.azure;

import com.azure.config.AppConfig;
import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import com.azure.storage.queue.QueueServiceClient;
import com.azure.storage.queue.QueueServiceClientBuilder;
import com.azure.storage.queue.models.QueueMessageItem;
import com.azure.storage.queue.models.QueueProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    private static  String QUEUE_URL = AppConfig.QUEUE_SAAS_URL;
    private static String SAAS_TOKEN =  AppConfig.QUEUE_SAAS_TOKEN;

    private static final String  QUEUE_NAME="rithuik";

    public static void main( String[] args )
    {
        QueueServiceClient client = new QueueServiceClientBuilder().endpoint(QUEUE_URL).sasToken(SAAS_TOKEN).buildClient();

        QueueClient queueClient  = new QueueClientBuilder().endpoint(QUEUE_URL).
                sasToken(SAAS_TOKEN).queueName(QUEUE_NAME).buildClient();
        //createQueue("rithuik",client);

        QueueProperties props = getQueueProperties(QUEUE_NAME,queueClient);
        props.getMetadata().forEach((s, s2) -> print(s+" "+s2));

        List<String> messagesToQueue =  List.of("Hello","World","Buddy");
        //addMessages(messagesToQueue,queueClient);
        //List<QueueMessageItem> messagesFromQueue= readMessages(queueClient);
        //messagesFromQueue.forEach(msg -> print(msg.toString()));
        deletQueue(QUEUE_NAME,client);

    }

    private static void print(String message)
    {
        System.out.println(message);
    }
    public static void createQueue(String name,QueueServiceClient client)
    {
            client.createQueue(name);

    }

    public static QueueProperties getQueueProperties(String queueName, QueueClient client)
    {

        return client.getProperties();

    }

    public static void addMessages(List<String> messages, QueueClient queueClient)
    {
            for(String message : messages)
            {
                queueClient.sendMessage(message);
            }
    }

    public static List<QueueMessageItem> readMessages(QueueClient queueClient)
    {
        List<QueueMessageItem> messages =  new ArrayList<>();
        QueueMessageItem msg = queueClient.receiveMessage();
        while (msg!=null)
        {
            messages.add(msg);
            msg = queueClient.receiveMessage();
        }
        return messages;
    }

    public static void deletQueue(String queName,QueueServiceClient client)
    {
        client.deleteQueue(queName);
    }


}
