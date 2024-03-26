package com.rahulhegde.oteldemo;


import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class OtelDemoApp {
    private static Logger logger = LoggerFactory.getLogger(OtelDemoApp.class);
    
    public static void main(String[] args) throws Exception {
    	logger.info("otel demo start");

    	// example to generate custom metrics
    	// customMetricUsage();
    	
    	// example to add custom signals
    	customTraceUsage();
    	
    	// thread spinner to show usage of JVM metrics
    	// jvmMetricsThreadSpinner();

    	// db usage metrics
    	//OtelDemoApp.dbusage();

    	// jms usage metrics/signal
    	//OtelDemoApp.jmsusage();

    	logger.info("otel demo complete");
    }
    
    private static void dbusage() throws Exception {
    	DBOtelHelper dbhlp = new DBOtelHelper();
    	dbhlp.metricTomcatJdbcPoolConnectionLeakExample();
    }
    
    private static void jmsusage() throws Exception {
    	JMSOtelHelper jmsCfg = new JMSOtelHelper();
    	jmsCfg.init();

    	int counter = 1;
		while (counter > 0) {
			jmsCfg.sendMessage("DEV.QUEUE.1", "hello");
			logger.info("mq message sent, wait before retreiving the msg");
			Thread.sleep(2000);
			jmsCfg.getMessage("DEV.QUEUE.1");
			counter--;
		}
    }
    
    private static void customMetricUsage() throws InterruptedException {
    	CustomMetricHelper mtrHelper = new CustomMetricHelper();
    	mtrHelper.customMetricCreator();
    }
    
    private static void customTraceUsage() throws InterruptedException {
    	CustomTraceHelper trcHelper = new CustomTraceHelper();
		logger.info("otel demo app for traces");
    	trcHelper.processInput("I1");
    }

    private static void jvmMetricsThreadSpinner() throws InterruptedException {
    	ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    	executor.setCorePoolSize(5);
    	executor.setMaximumPoolSize(20);
		executor.setKeepAliveTime(2, TimeUnit.SECONDS);
    	
    	logger.info("all task execution started");
    	for (int index = 0; index < 200; index++) {
    		executor.submit(() -> {
    		    Thread.sleep(5000);
    		    return null;
    		});
    		
    		if (index % 10 == 0) {
    			logger.info("thread resting time start, this should thread usage count {}", executor.getPoolSize());
    			Thread.sleep(20000);
    			logger.info("thread resting time over, this should thread usage count {}", executor.getPoolSize());
    		}
    	}
    	
    	logger.info("shutdown initiated");
    	executor.shutdown();
    	logger.info("shutdown completed");
    }
}