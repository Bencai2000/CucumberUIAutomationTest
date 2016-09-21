package com.tab.qa.framework.utils;

//import java.io.IOException;

import org.apache.log4j.Logger;

//import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;


public class RabbitMQHelper {
	
	private static Logger logger = Logger.getLogger(RabbitMQHelper.class);
	private static String _queue = "bet";
	//private static String _host = "10.26.128.40";
	private static String _host = "10.26.128.205";
	private static int _port = 5672;	
/*	private static String _username = "SHA";
	private static String _password = "SHATest";
*/	private static String _username = "admin_user";
	private static String _password = "5L34hf90";
	
	

	public static void Send(String message) {

		logger.info("Start Rabbit MQ ");
		try {
		    ConnectionFactory factory = new ConnectionFactory();
		    factory.setHost(_host);
		    factory.setPort(_port);		    
		    factory.setUsername(_username);
		    factory.setPassword(_password);
		    factory.setAutomaticRecoveryEnabled(false);
		    
		    Connection connection = factory.newConnection();
		    Channel channel = connection.createChannel();
		     	    
		    /*Queue.DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete,
                    Map<String, Object> arguments) throws IOException;*/
		    
		    channel.queueDeclare(_queue, true, false, false, null);
		    //String message = "Hello World!";		    

		    /*void basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body) throws IOException;*/
		    
		    channel.basicPublish("", _queue, null, message.getBytes());
		    System.out.println(" [x] Sent '" + message + "'");
		    channel.close();
		    connection.close();
		} catch(Exception e) {
			System.out.println(e);
		}
		
	}

}
