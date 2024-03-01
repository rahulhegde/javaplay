package com.example.jmstemplateexample;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

@Configuration
@Component
public class JMSConfiguration {
    private static Logger logger = LoggerFactory.getLogger(JMSConfiguration.class);

	JmsTemplate jmsTemplate;
	
	@Autowired
	ApplicationConfiguration appConfig;
	
	private static ConnectionFactory cf = null;
	
	public ConnectionFactory connectionFactory() {
		MQConnectionFactory connectionFactory = new MQConnectionFactory(); 

		// docker run -d -p 1414:1414 -p 9443:9443 -e LICENSE=accept -e MQ_QMGR_NAME=QM1 -e MQ_APP_USER=app -e MQ_APP_PASSWORD=passw0rd ibmcom/mq

		try {
			connectionFactory.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
			//connectionFactory.setShareConvAllowed(10);
			
			
			connectionFactory.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, appConfig.getQueueManager());
			connectionFactory.setStringProperty(WMQConstants.WMQ_HOST_NAME, appConfig.getHostname());
			connectionFactory.setIntProperty(WMQConstants.WMQ_PORT, appConfig.getPort());

			if (!appConfig.getUserId().isEmpty()) {
				System.out.println("setting user");
				connectionFactory.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
				connectionFactory.setStringProperty(WMQConstants.USERID, appConfig.getUserId());
				connectionFactory.setStringProperty(WMQConstants.PASSWORD, appConfig.getPassword());
			}

			if (!appConfig.getChannel().isEmpty()) {
				System.out.println("setting channel");
				connectionFactory.setStringProperty(WMQConstants.WMQ_CHANNEL, appConfig.getChannel());
			}
			
			if (!appConfig.getCipherSpec().isEmpty()) {
				System.out.println("setting cipherspec" + appConfig.getCipherSpec());
				connectionFactory.setStringProperty(WMQConstants.WMQ_SSL_CIPHER_SPEC, appConfig.getCipherSpec());
			}
			
			if (appConfig.getMqConnectionProfile() == 2) {
				cf = new SingleConnectionFactory(connectionFactory);
				System.out.println("creating single connection factory profile");

			} else if (appConfig.getMqConnectionProfile() == 3){
				cf = new CachingConnectionFactory(connectionFactory);
				System.out.println("creating caching connection factory profile");
			} else {
				cf = connectionFactory;
				System.out.println("creating mq connection factory profile");
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cf;
	}
	
	public void init() {
		this.jmsTemplate = new JmsTemplate(connectionFactory());
		this.jmsTemplate.setSessionTransacted(true);
		logger.info("jms template");
	}

	@Transactional
	public void sendMessage(String queueName, String message) {
		
		logger.info("sending: {}" + message);
		jmsTemplate.send(queueName, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				logger.info("session - {}", session.hashCode());
				return session.createTextMessage(message);
			}
		});
	}
}