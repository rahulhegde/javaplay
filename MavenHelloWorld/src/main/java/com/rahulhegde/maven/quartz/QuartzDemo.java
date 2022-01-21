package com.rahulhegde.maven.quartz;


import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;

public class QuartzDemo {
	private static final Logger logger = getL

	public static void main(String[] args) {
		logger.info("Quartz Demo starts");
		 try {
             // Grab the Scheduler instance from the Factory
             Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

             // and start it off
             scheduler.start();

             scheduler.shutdown();

         } catch (SchedulerException se) {
             se.printStackTrace();
         }
	}
}
