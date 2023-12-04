package com.rahulhegde.oteldemo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;

public class CustomTraceHelper {
    private static Logger logger = LoggerFactory.getLogger(CustomTraceHelper.class);
    
    @WithSpan
    public void doMultipleApiCall() throws InterruptedException {
		logger.info("otel demo app for traces - do doMultipleApiCall");
    	api1();
    	api2();
    	api3(10);
		Thread.sleep(100);
    }
    
    @WithSpan(value = "mq-process")
	private void api1() throws InterruptedException {
		Thread.sleep(200);
		logger.info("otel demo app - do mq-process");
    }

    @WithSpan(value = "database-process")
	private void api2() throws InterruptedException {
		Thread.sleep(300);
		logger.info("otel demo app - do database-process");
    }

    @WithSpan(value = "report-process")
	private void api3(@SpanAttribute(value = "reportCounter") int reportCount) throws InterruptedException {
		Thread.sleep(500);
		logger.info("otel demo app - do report-process {}", reportCount);
    }
 }