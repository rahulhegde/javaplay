package com.rahulhegde.xa.config;

import java.sql.SQLException;
import java.util.HashMap;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.jms.AtomikosConnectionFactoryBean;
import com.ibm.mq.jms.MQXAQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import com.mysql.cj.jdbc.MysqlXADataSource;

@Configuration
public class AtomikosConfiguration {
	// Create variables for the connection to MQ
	private static final String HOST = "localhost"; // Host name or IP address
	private static final int PORT = 1414; // Listener port for your queue manager
	private static final String CHANNEL = "DEV.APP.SVRCONN"; // Channel name
	private static final String QMGR = "QM1"; // Queue manager name
	private static final String APP_USER = "app"; // User name that application uses to connect to MQ
	private static final String APP_PASSWORD = "passw0rd"; // Password that the application uses to connect to MQ
	private static final String QUEUE_NAME = "DEV.QUEUE.1"; // Queue that the application uses to put and get messages to and from

	@Bean(initMethod = "init", destroyMethod = "close")
	public DataSource dataSource() throws SQLException {
		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		mysqlXaDataSource.setURL("jdbc:mysql://localhost:3306/hb_student_tracker?useSSL=false");
		mysqlXaDataSource.setUser("hbstudent");
		mysqlXaDataSource.setPassword("hbstudent");
		
		// MySQL does not support TMJOIN hence the Atomikos suggestion
		// TestAtomikosHibernatenJMSUsingAnnotation fails if not set to true with invalid data source
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("mysql-ds1");
		xaDataSource.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
		
		// Helps to run multiple connection test cases
		xaDataSource.setMaxPoolSize(10);
		return xaDataSource;
	}

	@Bean
	public ConnectionFactory connectionFactory() throws JMSException {
		MQXAQueueConnectionFactory cf = new MQXAQueueConnectionFactory();

		// Set the properties
		cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, HOST);
		cf.setIntProperty(WMQConstants.WMQ_PORT, PORT);
		cf.setStringProperty(WMQConstants.WMQ_CHANNEL, CHANNEL);
		cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
		cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, QMGR);
		cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "JmsPutGet (JMS)");
		cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
		cf.setStringProperty(WMQConstants.USERID, APP_USER);
		cf.setStringProperty(WMQConstants.PASSWORD, APP_PASSWORD);

		AtomikosConnectionFactoryBean xaConnectFactory = new AtomikosConnectionFactoryBean();
		xaConnectFactory.setXaConnectionFactory(cf);
		xaConnectFactory.setUniqueResourceName("My_MQSeries_XA_RMI");

		System.out.println("xa mq connection " + xaConnectFactory);
		return xaConnectFactory;
	}



	public UserTransaction userTransaction() throws Throwable {
		UserTransactionImp userTransactionImp = new UserTransactionImp();
		userTransactionImp.setTransactionTimeout(1000);
		return userTransactionImp;
	}

	@Bean(initMethod = "init", destroyMethod = "close")
	public TransactionManager transactionManager() throws Throwable {
		UserTransactionManager userTransactionManager = new UserTransactionManager();
		userTransactionManager.setForceShutdown(false);
		return userTransactionManager;
	}

	@Bean
	public PlatformTransactionManager platformTransactionManager() throws Throwable {
		UserTransaction userTransaction = userTransaction();
		TransactionManager transactionManager = transactionManager();
		return new JtaTransactionManager(userTransaction, transactionManager);
	}
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		return hibernateJpaVendorAdapter;
	}
	
	@Bean 
	public EntityManagerFactory entityManagerFactory() throws Throwable {
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("javax.persistence.transactionType", "JTA");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.put("hibernate.current_session_context_class", "jta");
		properties.put("hibernate.transaction.manager_lookup_class", "com.atomikos.icatch.jta.hibernate3.TransactionManagerLookup");
		properties.put("hibernate.hbm2ddl.auto", "none");
		
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setJtaDataSource(dataSource());
		entityManager.setJpaVendorAdapter(jpaVendorAdapter());
		entityManager.setJpaPropertyMap(properties);
		entityManager.afterPropertiesSet();

		return entityManager.getObject();
	}
}