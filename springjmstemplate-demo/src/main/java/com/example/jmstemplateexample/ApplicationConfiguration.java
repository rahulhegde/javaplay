package com.example.jmstemplateexample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfiguration {
	@Value("${mq.hostname:localhost}")
	String hostname;
	
	@Value("${mq.queuemanager:QM1}")
	String queueManager;
	
	@Value("${mq.port:1414}")
	int port;
	
	@Value("${mq.channel}")
	String channel;
	
	@Value("${mq.userid:app}")
	String userId;
	
	@Value("${mq.password:passw0rd}")
	String password;
	
	@Value("${mq.cipherspec:}")
	String cipherSpec;
	
	@Value("${app.mq.connectiontype:1}")
	int mqConnectionProfile;

	@Value("${mq.queuename:DEV.QUEUE.1}")
	String mqQueueName;
	
	public String getHostname() {
		return hostname;
	}

	public String getQueueManager() {
		return queueManager;
	}

	public int getPort() {
		return port;
	}

	public String getChannel() {
		return channel;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getCipherSpec() {
		return cipherSpec;
	}

	public String getMqQueueName() {
		return mqQueueName;
	}

	public int getMqConnectionProfile() {
		return mqConnectionProfile;
	}

	@Override
	public String toString() {
		return "ApplicationConfiguration [hostname=" + hostname + ", queueManager=" + queueManager + ", port=" + port
				+ ", channel=" + channel + ", userId=" + userId + ", password=" + password + ", cipherSpec="
				+ cipherSpec + ", mqConnectionProfile=" + mqConnectionProfile + ", mqQueueName=" + mqQueueName + "]";
	}
}
