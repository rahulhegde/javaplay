package com.rahulhegde.oteldemo;


import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;

public class CustomMetricHelper {
    private static Logger logger = LoggerFactory.getLogger(CustomMetricHelper.class);
  
	public void customMetricCreator() throws InterruptedException {
		// metric builder
		Meter meter = GlobalOpenTelemetry.meterBuilder("oteldemo.metrics").build();
		
		// types of metrics
		LongCounter totalReportCount = meter.counterBuilder("reportcount").setDescription("provides reports count").setUnit("int").build();
		DoubleHistogram reportCompleteTime = meter.histogramBuilder("reportime").setDescription("provides time taken for report generation").setUnit("seconds").build();

		// additional attributes
		Attributes bd = Attributes.builder().put("businessDt", "2023-12-04").build();

		// creating Random object
		Random rd = new Random(); 
	      
		// generate custom metrics
		while (true) {
			double timeInSeconds = rd.nextDouble(0, 10000);
			totalReportCount.add(1, bd);
			reportCompleteTime.record(timeInSeconds, bd);
			Thread.sleep(1000);
			logger.info("put custom metrics for otel demo - report time ", timeInSeconds);
		}
	}
 }