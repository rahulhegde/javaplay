package com.example.jmstemplateexample;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.stereotype.Component;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

@Configuration
@Component
public class JMSConfiguration {
	
	@Autowired
	ApplicationConfiguration appConfig;
	
	@Bean
	public ConnectionFactory connectionFactory() {
		ConnectionFactory cf = null;
		MQConnectionFactory connectionFactory = new MQConnectionFactory(); 

		try {
			connectionFactory.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);

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
}