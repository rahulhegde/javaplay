package collections;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentHashMapPlay {
	private Map<Integer, String> mapTest = new ConcurrentHashMap<>();

	/** 
	 * Multiple threads are responsibility Two threa
	 */
	private void ConcurrentHashMapPlay_ConcurrentInsertSetView() {
		ExecutorService threadPool = Executors.newFixedThreadPool(5);

		Runnable task_insert_1 = () -> {
			int i = 0;
			while (i < 10) {
				mapTest.put(i, "hello");
				System.out.println("Thread Id: " + Thread.currentThread().getId() + ", insert_1");
				i++;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		};		

		Runnable task_insert_2 = () -> {
			int i = 10;
			while (i < 20) {
				mapTest.put(i, "hello");
				System.out.println("Thread Id: " + Thread.currentThread().getId() + ", insert_2");
				i++;
				try {
					Thread.sleep(500);
					TimeUnit.DAYS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		};

		Runnable task_read = () -> {
			int i = 0;
			Set<Integer> mapKeySet = mapTest.keySet();
			while (i < 30) {
				
				System.out.println("Thread Id: " + Thread.currentThread().getId() + ", map set size: " + mapKeySet.size());
				i++;
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		};

		threadPool.submit(task_insert_1);
		threadPool.submit(task_insert_2);
		threadPool.submit(task_read);

		threadPool.shutdown();
		System.out.println("task complete");
	}

	public void ConcurrentHashMapPlayTest() {
		ConcurrentHashMapPlay_ConcurrentInsertSetView();
	}

}
