package com.rahulhegde.oteldemo;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBOtelHelper {
    private static Logger logger = LoggerFactory.getLogger(DBOtelHelper.class);
    
    /**
     * scenario 1 - setMaxActive=5, connection.close() - commented,
     * 	- graph - max connection reached, pending request piled up, application throws unable to fetch a connection
     */
    public void metricTomcatJdbcPoolConnectionLeakExample() throws Exception {

    	// pool properties - 
    	PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://localhost:3306/hb_student_tracker?useSSL=false");
        p.setDriverClassName("com.mysql.cj.jdbc.Driver");
        p.setUsername("hbstudent");
        p.setPassword("hbstudent");
    	p.setMaxActive(10);
    	p.setMaxIdle(4);
    	p.setMinIdle(2);
    	p.setInitialSize(2);
    	p.setMinEvictableIdleTimeMillis(300);
    	p.setName(LocalDateTime.now().toString());
    	p.setMaxWait(30000);
    	
		// Get a connection from the pool
		org.apache.tomcat.jdbc.pool.DataSourceProxy dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		dataSource.setPoolProperties(p);
		dataSource.createPool();

		// Execute a sample query
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		executor.setCorePoolSize(10);
		executor.setMaximumPoolSize(200);
		executor.setKeepAliveTime(5, TimeUnit.SECONDS);

		logger.info("sleep before the actual database works starts");
		Thread.sleep(10000);
		int counter = 100;

		while (counter > 0) {
			executor.submit(() -> {
				try {
					Connection connection = dataSource.getConnection();
					String query = "insert into student_1 (email, first_name, last_name) values ('xa3@gmail.com', 'xafn3', 'xaln3')";
					Statement insertStatement = connection.createStatement();
					int count = insertStatement.executeUpdate(query);
					Thread.sleep(3000);
					insertStatement.close();
					//connection.close();
					logger.info("db record insert work complete {}", count);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			if (counter % 5 == 0) {
				logger.info("put the thread pool work submitter to sleep - 10s");
				Thread.sleep(10000);
			}
			counter--;
		}
		
    	logger.info("dbhelper shutdown initiated");
    	executor.shutdown();
    	executor.wait();
    	logger.info("dbhelper shutdown completed");
    	
		Thread.sleep(10000);
    }
}