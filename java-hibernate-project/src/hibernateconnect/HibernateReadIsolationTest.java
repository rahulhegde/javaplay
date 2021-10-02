package hibernateconnect;


import java.sql.Connection;

import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// Reference: https://vladmihalcea.com/a-beginners-guide-to-jpa-hibernate-entity-state-transitions/
// this documentation shows the different state of the entity object

public class HibernateReadIsolationTest {
	
	Session globalSession = null;
	SessionFactory globalSessionFactory = null;
	

	
	
	public void demoHibernate_ReadIsolation_Thread1ReadThread2Write() {

		try {	
			// create hibernate session factory
			// save is hibernate specific API whereas persist is JPA adhered
			globalSessionFactory = new Configuration()
					.configure("hibernate.cfg.xml")
					.addAnnotatedClass(Student.class)
					.setProperty("hibernate.connection.isolation", 
							String.valueOf(Connection.TRANSACTION_READ_COMMITTED))
					.buildSessionFactory();

			// TRANSACTION_REPEATABLE_READ 	- 	t1 always get the same value in the scope of the transaction 
			// 									even t2 writes/commits a new value
			
			// TRANSACTION_READ_COMMITTED	-  	t1 gets latest committed value such that once t2 writes new value
			//									same is visible to t1.
			
			
			// TRANSACTION_READ_UNCOMMITTED - 	t1 get uncommitted value set by t2. If t2 rollback, it get last committed 
			// 									value

			Thread t1 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					Session session = globalSessionFactory.getCurrentSession();
//					session.setHibernateFlushMode(FlushMode.COMMIT);

					session.beginTransaction();

					for (int index = 0; index < 15; index++) {
						Student s = session.get(Student.class, 49);
						System.out.println("changebyThread1 f-i - Student - " + s);
						s.setEmail("changebyThread11 - " + Integer.toString(index));
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// clear the first level cache
						session.clear();
						System.out.println("changebyThread1 f-o - Student - " + s);


					}
					session.getTransaction().commit();
					session.close();
				}
			});
			
			Thread t2 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Session session = globalSessionFactory.getCurrentSession();
//					session.setHibernateFlushMode(FlushMode.COMMIT);
					session.beginTransaction();
					for (int index = 10; index < 12; index++) {
						Student s = session.get(Student.class, 49);
						System.out.println("changebyThread2 f-i: Student - " + s);
						s.setEmail("changebyThread22 - " + Integer.toString(index));
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						session.flush();
						System.out.println("changebyThread2 f-o: Student - " + s);

					}
					session.getTransaction().commit();
					session.close();
				}
			});
			
			t1.start();
			t2.start();
			
			t1.join();
			t2.join();

			System.out.println("thread execution complete!");

		} catch (Exception ex){ 
			ex.printStackTrace();			
		} finally {		
			// cleanup
			globalSessionFactory.close();
			if (globalSession != null) {
				globalSession.close();
				globalSession = null;
			}
		}
	}
	
	
	public void demoHibernate_TestWriteThread1WriteThread2_PessimisticReadPessimisticRead_Deadlock() {
		
		try {	
			// create hibernate session factory
			// save is hibernate specific API whereas persist is JPA adhered
			globalSessionFactory = new Configuration()
					.configure("hibernate.cfg.xml")
					.addAnnotatedClass(Student.class)
//					.setProperty("hibernate.connection.isolation", 
//							String.valueOf(Connection.TRANSACTION_READ_COMMITTED))
					.buildSessionFactory();


			Thread t1 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					Session session = globalSessionFactory.getCurrentSession();
//					session.setHibernateFlushMode(FlushMode.COMMIT);

					session.beginTransaction();

					for (int index = 0; index < 1; index++) {
						Student s = session.get(Student.class, 49, 
								new LockOptions(LockMode.PESSIMISTIC_READ));

						// hold the write so that other PR thread can write first
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						System.out.println("changebyThread1 f-i - Student - " + s);			
						s.setEmail("changebyThread-PRPR-11 - " + Integer.toString(index));

						session.flush();
						
						System.out.println("changebyThread1 f-o1 - Student - " + s);

						// hold the write so that other PR thread can write first
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("changebyThread1 f-o2 - Student - " + s);

					}
					session.getTransaction().commit();
					session.close();

				}
			});
			
			Thread t2 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(15000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Session session = globalSessionFactory.getCurrentSession();
//					session.setHibernateFlushMode(FlushMode.COMMIT);
					
					session.beginTransaction();
					for (int index = 10; index < 11; index++) {
						Student s = session.get(Student.class, 49, 
								new LockOptions(LockMode.PESSIMISTIC_READ));
						System.out.println("changebyThread2 f-i: Student - " + s);
						s.setEmail("changebyThread-PRPR-27 - " + Integer.toString(index));
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						session.flush();
						System.out.println("changebyThread2 f-o: Student - " + s);
					}

					session.getTransaction().commit();
					session.close();
				}
			});
			
			t1.start();
			t2.start();
			
			t1.join();
			t2.join();

			System.out.println("thread execution complete!");

		} catch (Exception ex){ 
			ex.printStackTrace();			
		} finally {		
			// cleanup
			globalSessionFactory.close();
			if (globalSession != null) {
				globalSession.close();
				globalSession = null;
			}
		}
	}
	
	public void demoHibernate_TestWriteThread1WriteThread2_PessimisticReadPessimisticWrite_Deadlock() {
		
		try {	
			// create hibernate session factory
			// save is hibernate specific API whereas persist is JPA adhered
			globalSessionFactory = new Configuration()
					.configure("hibernate.cfg.xml")
					.addAnnotatedClass(Student.class)
//					.setProperty("hibernate.connection.isolation", 
//							String.valueOf(Connection.TRANSACTION_READ_COMMITTED))
					.buildSessionFactory();


			Thread t1 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					Session session = globalSessionFactory.getCurrentSession();
//					session.setHibernateFlushMode(FlushMode.COMMIT);

					session.beginTransaction();

					for (int index = 0; index < 1; index++) {
						Student s = session.get(Student.class, 49, 
								new LockOptions(LockMode.PESSIMISTIC_READ));

						// hold the write so that other PR thread can write first
						try {
							Thread.sleep(15000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						System.out.println("changebyThread1 f-i - Student - " + s);			
						s.setEmail("changebyThread-PRPW-17 - " + Integer.toString(index));

						session.flush();
						System.out.println("changebyThread1 f-o - Student - " + s);

					}
					session.getTransaction().commit();
					session.close();

				}
			});
			
			Thread t2 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Session session = globalSessionFactory.getCurrentSession();
//					session.setHibernateFlushMode(FlushMode.COMMIT);
					
					session.beginTransaction();
					for (int index = 10; index < 15; index++) {
						Student s = session.get(Student.class, 49, 
								new LockOptions(LockMode.PESSIMISTIC_WRITE));
						System.out.println("changebyThread2 f-i: Student - " + s);
						s.setEmail("changebyThread-PRPW-27 - " + Integer.toString(index));
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						session.flush();
						System.out.println("changebyThread2 f-o: Student - " + s);
					}

					session.getTransaction().commit();
					session.close();
				}
			});
			
			t1.start();
			t2.start();
			
			t1.join();
			t2.join();
			
			globalSessionFactory.close();

			System.out.println("thread execution complete!");

		} catch (Exception ex){ 
			ex.printStackTrace();			
		} finally {		
			// cleanup
			globalSessionFactory.close();
			if (globalSession != null) {
				globalSession.close();
				globalSession = null;
			}
		}
	}
	
	public void demoHibernate_TestWriteThread1WriteThread2() {

		try {	
			// create hibernate session factory
			// save is hibernate specific API whereas persist is JPA adhered
			globalSessionFactory = new Configuration()
					.configure("hibernate.cfg.xml")
					.addAnnotatedClass(Student.class)
//					.setProperty("hibernate.connection.isolation", 
//							String.valueOf(Connection.TRANSACTION_READ_COMMITTED))
					.buildSessionFactory();


			Thread t1 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					Session session = globalSessionFactory.getCurrentSession();
//					session.setHibernateFlushMode(FlushMode.COMMIT);

					session.beginTransaction();

					for (int index = 0; index < 1; index++) {
						Student s = session.get(Student.class, 49);
						System.out.println("changebyThread1 f-i - Student - " + s);
						s.setEmail("changebyThread-NML-11 - " + Integer.toString(index));

						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						session.flush();
						System.out.println("changebyThread1 f-o - Student - " + s);
					}
					session.getTransaction().commit();
					session.close();
				}
			});
			
			Thread t2 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Session session = globalSessionFactory.getCurrentSession();
//					session.setHibernateFlushMode(FlushMode.COMMIT);
					session.beginTransaction();
					for (int index = 10; index < 11; index++) {
						Student s = session.get(Student.class, 49);
						System.out.println("changebyThread2 f-i: Student - " + s);
						s.setEmail("changebyThreadNML-22 - " + Integer.toString(index));
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						session.flush();
						System.out.println("changebyThread2 f-o: Student - " + s);

					}
					// simulate wait so that thread1 is blocked from calling flush
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					session.getTransaction().commit();
					session.close();
				}
			});
			
			t1.start();
			t2.start();
			
			t1.join();
			t2.join();

			System.out.println("thread execution complete!");

		} catch (Exception ex){ 
			ex.printStackTrace();			
		} finally {		
			// cleanup
			globalSessionFactory.close();
			if (globalSession != null) {
				globalSession.close();
				globalSession = null;
			}
		}
	}
	
	public void demoHibernate_TestWriteThread1WriteThread2_BothPessimisticWriteLock() {

		try {	
			// create hibernate session factory
			// save is hibernate specific API whereas persist is JPA adhered
			globalSessionFactory = new Configuration()
					.configure("hibernate.cfg.xml")
					.addAnnotatedClass(Student.class)
//					.setProperty("hibernate.connection.isolation", 
//							String.valueOf(Connection.TRANSACTION_READ_COMMITTED))
					.buildSessionFactory();


			Thread t1 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					Session session = globalSessionFactory.getCurrentSession();

					session.beginTransaction();
					for (int index = 0; index < 5; index++) {
						Student s = session.get(Student.class, 49, 
								new LockOptions(LockMode.PESSIMISTIC_WRITE));

						try {
							Thread.sleep(4000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("changebyThread1 f-i - Student - " + s);
						s.setEmail("changebyThread-PWPW-11 - " + Integer.toString(index));

						session.flush();
						System.out.println("changebyThread1 f-o - Student - " + s);

					}
					session.getTransaction().commit();
					session.close();

				}
			});
			
			Thread t2 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Session session = globalSessionFactory.getCurrentSession();
//					session.setHibernateFlushMode(FlushMode.COMMIT);
					session.beginTransaction();
					for (int index = 10; index < 15; index++) {
						Student s = session.get(Student.class, 49, 
								new LockOptions(LockMode.PESSIMISTIC_WRITE));
						
						System.out.println("changebyThread2 f-i: Student - " + s);
						s.setEmail("changebyThread-PWPW-22 - " + Integer.toString(index));
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						session.flush();
						System.out.println("changebyThread2 f-o: Student - " + s);

					}
					session.getTransaction().commit();
					session.close();
				}
			});
			
			t1.start();
			t2.start();
			
			t1.join();
			t2.join();

			System.out.println("thread execution complete!");

		} catch (Exception ex){ 
			ex.printStackTrace();			
		} finally {		
			// cleanup
			globalSessionFactory.close();
			if (globalSession != null) {
				globalSession.close();
				globalSession = null;
			}
		}
	}
	
	
	public void demoHibernate_TestWriteThread1WriteThread2_T1PessimisticWriteT2PerssimisticReadLock() {

		try {	
			// create hibernate session factory
			// save is hibernate specific API whereas persist is JPA adhered
			globalSessionFactory = new Configuration()
					.configure("hibernate.cfg.xml")
					.addAnnotatedClass(Student.class)
//					.setProperty("hibernate.connection.isolation", 
//							String.valueOf(Connection.TRANSACTION_READ_COMMITTED))
					.buildSessionFactory();


			Thread t1 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					Session session = globalSessionFactory.getCurrentSession();

					session.beginTransaction();
					for (int index = 0; index < 5; index++) {
						Student s = session.get(Student.class, 49, 
								new LockOptions(LockMode.PESSIMISTIC_WRITE));
						System.out.println("changebyThread1 f-i - Student - " + s);
						s.setEmail("changebyThread-PWPR-1111 - " + Integer.toString(index));

						try {
							Thread.sleep(4000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						session.flush();
						System.out.println("changebyThread1 f-o - Student - " + s);

					}
					session.getTransaction().commit();
					session.close();

				}
			});
			
			Thread t2 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Session session = globalSessionFactory.getCurrentSession();
//					session.setHibernateFlushMode(FlushMode.COMMIT);
					session.beginTransaction();
					for (int index = 10; index < 15; index++) {
						Student s = session.get(Student.class, 49, 
								new LockOptions(LockMode.PESSIMISTIC_READ));
						System.out.println("changebyThread2 f-i: Student - " + s);
						s.setEmail("changebyThread-PWPR-2222 - " + Integer.toString(index));
						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						session.flush();
						System.out.println("changebyThread2 f-o: Student - " + s);

					}
					session.getTransaction().commit();
					session.close();
				}
			});
			
			t1.start();
			t2.start();
			
			t1.join();
			t2.join();

			System.out.println("thread execution complete!");

		} catch (Exception ex){ 
			ex.printStackTrace();			
		} finally {		
			// cleanup
			globalSessionFactory.close();
			if (globalSession != null) {
				globalSession.close();
				globalSession = null;
			}
		}
	}
	
	public void demoHibernate_TestWriteThread1WriteThread2_OptimisticLock() {
		try {	
			// create hibernate session factory
			// save is hibernate specific API whereas persist is JPA adhered
			globalSessionFactory = new Configuration()
					.configure("hibernate.cfg.xml")
					.addAnnotatedClass(Student.class)
					.addAnnotatedClass(Person.class)
//					.setProperty("hibernate.connection.isolation", 
//							String.valueOf(Connection.TRANSACTION_READ_COMMITTED))
					.buildSessionFactory();
			
			Session session = globalSessionFactory.getCurrentSession();
			
			Person p = new Person(0, "rahul", "hegde", "hegde.rahul@gmail.com");
			session.beginTransaction();
			session.save(p);
			session.getTransaction().commit();
			
			Thread t1 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					Session session = globalSessionFactory.getCurrentSession();

					session.beginTransaction();
					for (int index = 0; index < 1; index++) {
						Person s = session.get(Person.class, 1);
						System.out.println("changebyThread1 f-i - Student - " + s);
						s.setEmail("changebyThread-OL-136 - " + Integer.toString(index));


						session.flush();
						System.out.println("changebyThread1 f-o - Student - " + s);
						

						try {
							Thread.sleep(15000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					session.getTransaction().commit();
					session.close();
				}
			});
			
			Thread t2 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Session session = globalSessionFactory.getCurrentSession();
					session.beginTransaction();
					for (int index = 10; index < 15; index++) {
						Person s = session.get(Person.class, 1);
						System.out.println("changebyThread2 f-i: Student - " + s);
						s.setEmail("changebyThread-OL-236 - " + Integer.toString(index));
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						session.flush();
						System.out.println("changebyThread2 f-o: Student - " + s);

					}
					session.getTransaction().commit();
					session.close();
				}
			});
			
			t1.start();
			t2.start();
			
			t1.join();
			t2.join();

			System.out.println("thread execution complete!");

		} catch (Exception ex){ 
			ex.printStackTrace();			
		} finally {		
			// cleanup
			globalSessionFactory.close();
			if (globalSession != null) {
				globalSession.close();
				globalSession = null;
			}
		}	
	}
	
	public static void main(String[] args) {

		HibernateReadIsolationTest test = new HibernateReadIsolationTest();
		
		// Hibernate save session 
		test.demoHibernateSession_StudentSave();
		
		// Conclusion: isolation level changes 
		// TRANSACTION_REPEATABLE_READ 	- 	t1 always get the same value in the scope of the transaction 
		// 									even t2 writes/commits a new value
		// TRANSACTION_READ_COMMITTED	-  	t1 gets latest committed value such that once t2 writes new value
		//									same is visible to t1.
		// TRANSACTION_READ_UNCOMMITTED - 	t1 get uncommitted value set by t2. If t2 rollback, it gets last committed 
		// 									value
		//test.demoHibernate_ReadIsolation_Thread1ReadThread2Write();
		
		// thread 1 holds pessimistic read lock on row continuously for 5secs
		// thread 1 holds shared lock that supports no dirty read, repeatable read 
		// thread 1 writes to the row and commits and hence a lock where other 
		// threads are not allowed to write.
		// thread 2 holding pessimistic read tries to update but fails immediately with deadlock
		//test.demoHibernate_TestWriteThread1WriteThread2_PessimisticRead_Deadlock();
		
		// uses default isolation 
		// thread 1 acquire lock first and then thread2
		// thread 2 writes and flushes the data to the database first and simulates 10sec wait
		// thread 1 writes however flush is blocked until thread2 transaction is complete
		//test.demoHibernate_TestWriteThread1WriteThread2();

		
		// Conclusion - Optimistic Locking
		// Thread1 and Thread2 work on the same object. Thread1 first accesses the object however 
		// Thread2 access the object second but commits first. Thread1 
		// gets optimistic locking exception
		//test.demoHibernate_TestWriteThread1WriteThread2_OptimisticLock();
		
		// Conclusion - MySQL (pessimistic read and write) 
		// - context of same object access by two different transaction
		
		// CASE of Exclusive Write Lock
		// Thread 1 first holds Pessimistic Write, Thread2 will wait such will not acquire 
		// either Pessimistic Write or Pessimistic Read on that object until thread1 completes
		// the transaction.
		//test.demoHibernate_TestWriteThread1WriteThread2_BothPessimisticWriteLock();
		//test.demoHibernate_TestWriteThread1WriteThread2_T1PessimisticWriteT2PerssimisticReadLock();
		
		// CASE of Shared Read Lock
		// Thread 1 first holds Pessimistic Read, Thread2 can next acquire Pessimistic Read
		// however depending upon which thread writes data (flushed to database), the second thread 
		// will throw deadlock exception (optimistic locking)
		//test.demoHibernate_TestWriteThread1WriteThread2_PessimisticReadPessimisticRead_Deadlock();
		
		// CASE of Shared Read Lock 
		// Thread 1 first holds Pessimistic Read, Thread2 will try to acquire Pessimistic Write
		// and will be blocked. 
		// If Thread 1 flushes any write on the same object then thread 2 will throw deadlock 
		// exception at the time acquiring Pessimistic Write Lock
		// test.demoHibernate_TestWriteThread1WriteThread2_PessimisticReadPessimisticWrite_Deadlock();
		
	}
}
