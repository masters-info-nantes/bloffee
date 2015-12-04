package fr.alma.mw1516.services;

import java.util.Date;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import fr.alma.mw1516.model.User;

public class Log {

	private static Log instance;

    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }

        return instance;
    }
    
    public void sendAuthenticationLog(Long imei, User u, String message)
    {
    	try {
    	// get the initial context
            InitialContext ctx = new InitialContext();
                                                                               
            // lookup the queue object
            Queue queue = (Queue) ctx.lookup("queue/queue0");
                                                                               
            // lookup the queue connection factory
            QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.lookup("iiop://localhost:53506/queue/connectionFactory");
                                                                               
            // create a queue connection
            QueueConnection queueConn = connFactory.createQueueConnection();
                                                                               
            // create a queue session
            QueueSession queueSession = queueConn.createQueueSession(false,
                Session.DUPS_OK_ACKNOWLEDGE);
                                                                               
            // create a queue sender
            QueueSender queueSender = queueSession.createSender(queue);
            queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            
            // create a log message 
            MapMessage logMessage = queueSession.createMapMessage();
            logMessage.setString("actionType", "identification");
            logMessage.setString("vendingMachineId", "");
            logMessage.setString("vendingMachineType", "");
            logMessage.setString("userType", "mobilePhone");
            logMessage.setString("userId", imei.toString());
            logMessage.setString("isoDate", new Date().toString() );
            logMessage.setBoolean("successful", u != null);
            logMessage.setString("failReason", message);
         
                                                                               
            // send the message
        	queueSender.send(logMessage);
                                                                              
        	// print what we did
        	System.out.println("sent: " + logMessage.toString());
                                                                          
        	// close the queue connection
        	queueConn.close();
    	} catch (JMSException | NamingException e) {
    		e.printStackTrace();
    	}
    }
}
