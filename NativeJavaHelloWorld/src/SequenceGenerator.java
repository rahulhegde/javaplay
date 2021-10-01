public class SequenceGenerator {
    
    private int currentValue = 0;

    public synchronized int getNextSequence() {
    	
    	System.out.println(currentValue);
        currentValue = currentValue + 1;
        return currentValue;
    }

}