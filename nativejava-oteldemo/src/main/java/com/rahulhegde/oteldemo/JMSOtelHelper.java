package com.rahulhegde.oteldemo;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

@Configuration
public class JMSOtelHelper {
    private static Logger logger = LoggerFactory.getLogger(JMSOtelHelper.class);

	JmsTemplate jmsTemplate;
	
	private static ConnectionFactory cf = null;
	
	@Bean
	public ConnectionFactory connectionFactory() {
		MQConnectionFactory connectionFactory = new MQConnectionFactory(); 

		// docker run -d -p 1414:1414 -p 9443:9443 -e LICENSE=accept -e MQ_QMGR_NAME=QM1 -e MQ_APP_USER=app -e MQ_APP_PASSWORD=passw0rd ibmcom/mq

		try {
			connectionFactory.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
			connectionFactory.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, "QM1");
			connectionFactory.setStringProperty(WMQConstants.WMQ_HOST_NAME, "localhost");
			connectionFactory.setIntProperty(WMQConstants.WMQ_PORT, 1414);
			connectionFactory.setStringProperty(WMQConstants.WMQ_CHANNEL, "DEV.APP.SVRCONN");
			connectionFactory.setStringProperty(WMQConstants.USERID, "app");
			connectionFactory.setStringProperty(WMQConstants.PASSWORD, "passw0rd");
			cf = connectionFactory;

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cf;
	}
	
	public void init() {
		this.jmsTemplate = new JmsTemplate(this.connectionFactory());
		this.jmsTemplate.setSessionTransacted(true);
		logger.info("jms template");
	}

	@Transactional
	public void sendMessage(String queueName, String message) {
		logger.info("sendMessage: {}", message);
		jmsTemplate.send(queueName, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				logger.info("session - {}", session.hashCode());
				return session.createTextMessage(message);
			}
		});
	}
	
	@Transactional
	public void getMessage(String queueName) throws Exception {
		Message msg = jmsTemplate.receive(queueName);
		logger.info("getMessage: {}", msg);
	}
}