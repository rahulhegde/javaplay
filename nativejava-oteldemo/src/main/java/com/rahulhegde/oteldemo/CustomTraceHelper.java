package com.rahulhegde.oteldemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;

public class CustomTraceHelper {
	private static Logger logger = LoggerFactory.getLogger(CustomTraceHelper.class);

	@WithSpan(value = "input-processor")
	public void processInput(String id) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		validateInput();
		matchInput();
		generateReport(id);
		Thread.sleep(100);
		long endTime = System.currentTimeMillis();
		logger.info("total time to process input id: {} - {} ms", id, endTime - startTime);
	}

	@WithSpan(value = "input-validator")
	private void validateInput() throws InterruptedException {
		long startTime = System.currentTimeMillis();
		Thread.sleep(200);
		long endTime = System.currentTimeMillis();
		logger.info("total time to validateInput - {} ms", endTime - startTime);
	}

	@WithSpan(value = "input-matcher")
	private void matchInput() throws InterruptedException {
		long startTime = System.currentTimeMillis();
		Thread.sleep(300);
		long endTime = System.currentTimeMillis();
		logger.info("total time to matchInput - {} ms", endTime - startTime);
	}

	@WithSpan(value = "report-generator")
	private void generateReport(@SpanAttribute(value = "input_id") String id) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		Thread.sleep(500);
		long endTime = System.currentTimeMillis();
		logger.info("total time to generateReport - {} ms", endTime - startTime);
	}
}