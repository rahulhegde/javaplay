package quartzdemo;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class PrinterJob implements Job{
	private static final Logger logger = Logger.getLogger(PrinterJob.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("job detail fired: " + context.getFireTime() + ", refire count: " + context.getRefireCount() + 
				" attributes: " + context.getJobDetail().getJobDataMap().getString("jobname"));
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("job detail completed: " + context.toString());
		
		JobExecutionException jbException = new JobExecutionException();
		throw jbException;
	}
}
