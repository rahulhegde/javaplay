package com.rahulhegde.maven.quartz;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;

public class JobDetail implements Job {

	private static final Logger logger = Logger.getLogger(JobDetail.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("job detail execution {}", context);
	}
}
