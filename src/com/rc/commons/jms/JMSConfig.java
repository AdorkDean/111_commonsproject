package com.rc.commons.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @Title: JMSConfig.java
 * @Description:
 * @author yinbinhome@163.com
 * @date 2011-6-28 上午10:28:10
 * @version V1.0
 */

public class JMSConfig {
	private static Context context = null;
	private static ConnectionFactory factory = null;
	private static Connection connection = null;
	private static String factoryName = "ConnectionFactory";
	private static String destName = "queue1";
	private static Destination dest = null;
	private static Session session = null;
	private static MessageProducer sender = null;
	private static JMSConfig instance=null;

	private JMSConfig() {
		try {
			// create the JNDI initial context.
			context = new InitialContext();

			// look up the ConnectionFactory
			factory = (ConnectionFactory) context.lookup(factoryName);

			// look up the Destination
			dest = (Destination) context.lookup(destName);

			// create the connection
			connection = factory.createConnection();

			// create the session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// create the sender
			sender = session.createProducer(dest);

			// start the connection, to enable message sends
			connection.start();

			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static JMSConfig getInstance() {
		if (instance == null) {
			instance = new JMSConfig();
		}
		return instance;
	}

	public static void send(String txt) {
		instance = getInstance();
		try {
			TextMessage message = session.createTextMessage();
			message.setText(txt);
			sender.send(message);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
			System.out.print("=>");
			JMSConfig.send("发送");
			System.out.println("<=");
	}
}
