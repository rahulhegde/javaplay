package com.rahulhegde.xa.config;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.managed.DataSourceXAConnectionFactory;
import org.apache.commons.dbcp2.managed.ManagedDataSource;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionManagerImple;
import com.arjuna.ats.internal.jta.transaction.arjunacore.UserTransactionImple;
import com.ibm.mq.jms.MQXAQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import com.mysql.cj.jdbc.MysqlXADataSource;

@Configuration
public class DatabaseConfig {
	// Create variables for the connection to MQ
	private static final String HOST = "localhost"; // Host name or IP address
	private static final int PORT = 1414; // Listener port for your queue manager
	private static final String CHANNEL = "DEV.APP.SVRCONN"; // Channel name
	private static final String QMGR = "QM1"; // Queue manager name
	private static final String APP_USER = "app"; // User name that application uses to connect to MQ
	private static final String APP_PASSWORD = "passw0rd"; // Password that the application uses to connect to MQ
	private static final String QUEUE_NAME = "DEV.QUEUE.1"; // Queue that the application uses to put and get messages to and from

	public XADataSource mysqlXADataSource() {
		MysqlXADataSource source = new MysqlXADataSource();
		source.setUrl("jdbc:mysql://localhost:3306/hb_student_tracker?useSSL=false");
		source.setUser("hbstudent");
		source.setPassword("hbstudent");
		return source;
	}

	@Bean (name="dataSource")
	public DataSource dataSource() {
        DataSourceXAConnectionFactory dataSourceXAConnectionFactory =
                new DataSourceXAConnectionFactory(transactionManager(), mysqlXADataSource());
        PoolableConnectionFactory poolableConnectionFactory =
                new PoolableConnectionFactory(dataSourceXAConnectionFactory, null);
        GenericObjectPool<PoolableConnection> connectionPool =
                new GenericObjectPool<>(poolableConnectionFactory);
        poolableConnectionFactory.setPool(connectionPool);
        return new ManagedDataSource<>(connectionPool,
                dataSourceXAConnectionFactory.getTransactionRegistry());
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

		CachingConnectionFactory ccf = new CachingConnectionFactory(cf);
		
		return ccf;
	}


	public UserTransaction userTransaction() {
		return new UserTransactionImple();
	}

    public TransactionManager transactionManager() {
		return new TransactionManagerImple();
    }

	@Bean(name = "transactionManager")
	public JtaTransactionManager jtaTransactionManager() {
		JtaTransactionManager tm = new JtaTransactionManager();
		tm.setTransactionManager(transactionManager());
		tm.setUserTransaction(userTransaction());
		return tm;
	}
	
//	public JpaVendorAdapter jpaVendorAdapter() {
//		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
//		hibernateJpaVendorAdapter.setShowSql(true);
//		hibernateJpaVendorAdapter.setGenerateDdl(true);
//		return hibernateJpaVendorAdapter;
//	}
	
//	@Bean (name="entityManagerFactory")
//	public EntityManagerFactory em1() throws Throwable {
//		HashMap<String, Object> properties = new HashMap<String, Object>();
//		properties.put("javax.persistence.transactionType", "JTA");
//		properties.put("hibernate.current_session_context_class", "jta");
//		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//		properties.put("hibernate.hbm2ddl.auto", "none");
//		
//		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
//		entityManager.setJtaDataSource(dataSource());
//		entityManager.setJpaVendorAdapter(jpaVendorAdapter());
//		entityManager.setJpaPropertyMap(properties);
//		entityManager.afterPropertiesSet();
//
//		return entityManager.getObject();
//	}
}