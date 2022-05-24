import java.util.concurrent.Callable;

public class SequenceGenerator implements Callable<Integer> {
    
    private int currentValue = 0;
    
    SequenceGenerator(Integer value) {
    	currentValue = value;
    }

    @Override
    public synchronized Integer call() throws Exception {
        currentValue = currentValue + 1;
        if (currentValue != 0) {
        	System.out.println(currentValue);
        	//un-comment below to test callable handling of exception
        	throw new Exception("exception thrown during callable call - hello world");       	
        }
        return currentValue;
    }

}