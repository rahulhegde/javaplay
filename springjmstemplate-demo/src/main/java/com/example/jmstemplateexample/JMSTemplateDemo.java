package com.example.jmstemplateexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;


// docker run -d -p 1414:1414 -p 9443:9443 -e LICENSE=accept -e MQ_QMGR_NAME=QM1 -e MQ_APP_USER=app -e MQ_APP_PASSWORD=passw0rd ibmcom/mq

@SpringBootApplication
@EnableJms
public class JMSTemplateDemo {

	@Autowired
	ApplicationConfiguration config;
	
	@Autowired
	MessageSender msgSender;

	public static void main(String[] args) {
		JMSTemplateDemo jmsapp = SpringApplication.run(JMSTemplateDemo.class, args)
				.getBean(JMSTemplateDemo.class);
		System.out.println("JMSTemplateDemo config" + jmsapp.config);

		//jmsapp.SendMoreMessages();

		try {
			jmsapp.msgSender.sendMessage(jmsapp.config.getMqQueueName(), "hello1");
			Thread.sleep(5000);
//			jmsapp.msgSender.sendMessage(jmsapp.config.getMqQueueName(), "hello2");
//			Thread.sleep(8000);
//			//System.exit(1);
//			jmsapp.msgSender.sendMessage(jmsapp.config.getMqQueueName(), "hello3");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Transactional
//	public void SendMoreMessages() {
//		msgSender.sendMessage(config.getMqQueueName(), "hello1");
//		msgSender.sendMessage(config.getMqQueueName(), "hello2");
//	}
}
