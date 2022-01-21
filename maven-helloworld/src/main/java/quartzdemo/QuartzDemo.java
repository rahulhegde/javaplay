package quartzdemo;

import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;


public class QuartzDemo {
	private static final Logger logger = Logger.getLogger(QuartzDemo.class);
	public static void main(String[] args) throws InterruptedException {
        try {
        	logger.info("starting the logger");
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            JobDetail job1 = JobBuilder.newJob(PrinterJob.class)
            		.withIdentity(new JobKey("job1"))
            		.usingJobData("jobname", "job1fired")
            		.requestRecovery(true)
            		.build();

            JobDetail job2 = JobBuilder.newJob(PrinterJob.class)
            		.withIdentity(new JobKey("job2"))
            		.usingJobData("jobname", "job2fired")
            		.build();

            Trigger trigger1 = TriggerBuilder.newTrigger()
            		.startNow()
            		.withIdentity(new TriggerKey("job1"))
            		.withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(3, 2))
            		.build();	
            
//            Trigger trigger2 = TriggerBuilder.newTrigger()
//            		.startNow()
//            		.withIdentity("trigger2")
//            		.withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(3, 2))
//            		.build();
            
            scheduler.scheduleJob(job1, trigger1);
            scheduler.scheduleJob(job2, trigger1);
            
            // and start it off
            scheduler.start();
            
            Thread.sleep(20000);            

            scheduler.shutdown();
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
	}

}
