package com.example.jmstemplateexample;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

	@Autowired
	private ConnectionFactory connectionFactory;
	
	@Autowired
	private JmsTemplate jmsTemplate;

	@PostConstruct
	public void init() {
		this.jmsTemplate = new JmsTemplate(connectionFactory);
		this.jmsTemplate.setSessionTransacted(true);
		System.out.println("jms template");
	}

	public void sendMessage(String queueName, String message) {
		
		System.out.println("sending: " + message);
		jmsTemplate.send(queueName, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				System.out.println("session - " + session.hashCode());
				return session.createTextMessage(message);
			}
		});
	}
}
