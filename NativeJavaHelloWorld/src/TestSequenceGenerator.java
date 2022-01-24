import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.Throwables;



public class TestSequenceGenerator {

	@Test
	public void givenUnsafeSequenceGenerator_whenRaceCondition_thenUnexpectedBehavior() throws Exception {
	    int count = 1;
	    Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGenerator(0), count);
	    Assert.assertEquals(count, uniqueSequences.size());
	}

	private Set<Integer> getUniqueSequences(SequenceGenerator generator, int count) throws Exception {
	    ExecutorService executor = Executors.newFixedThreadPool(1);
	    Set<Integer> uniqueSequences = new LinkedHashSet<>();
	    List<Future<Integer>> futures = new ArrayList<>();

	    for (int i = 0; i < count; i++) {
	    	try {
	    		futures.add(executor.submit(generator));
	    	} catch (Exception  e) {
	    		System.out.println("task cannot be scheduled - " + e); 		
			}
	    }

	    for (Future<Integer> future : futures) {
	    	try {
		        uniqueSequences.add(future.get());
	    	} catch(Exception ex) {
	    		System.out.println("future is done: " + Throwables.getStacktrace(ex));
	    	}
	    }

	    executor.awaitTermination(1, TimeUnit.SECONDS);
	    executor.shutdown();

	    return uniqueSequences;
	}
}
