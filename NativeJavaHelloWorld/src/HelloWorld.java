import java.util.HashMap;
import java.util.function.Consumer;

import functionInterface.FunctionalInterfacePlay;
import lamda.LambdaPlay;
import lamda.PredicatePlay;  

class Example {
	HashMap <String, DatePlay> mapper = new HashMap<String, DatePlay>();
	Integer x;
	
	void print() {
		SubExample ee2 = new SubExample();
		ee2.check();
	}
	
	class SubExample {
		void check( ) {
			System.out.println("printing integer" + x);
		}
	}

	
	static Consumer<Integer> lambdaWrapper(Consumer<Integer> consumer) {
	    return i -> {
	        try {
	            consumer.accept(i);
	        } catch (ArithmeticException e) {
	            System.err.println(
	              "Arithmetic Exception occured : " + e.getMessage());
	        }
	    };
	}
	
	
	public static void main(String args[]) {
		int i = 100;
		int j = 200;
		StringBuilder key = new StringBuilder();
		key.append(i).append('_').append(j);
		System.out.println("string - " + key);
		
		
//		DatePlay datePlay = new DatePlay();
//		int retryIndex = 0;
//		for (; retryIndex < 10; retryIndex++) {
//			try {
//				System.out.println("inside retry" + Math.pow(-1, 0));
//				break;
//			} catch(Exception e) {
//				System.out.println(e);
//			}		
//			
//		}
		
		//datePlay.TestDatePlay();
		

	
//		ExtPackageFootballPlayer footballPlayer = new ExtPackageFootballPlayer(11);
//		System.out.println("player - " + footballPlayer);
		
		// collection 
//		ListPlay listPlay = new ListPlay();
//		listPlay.TestListPlay();
		
//		AbstractionPlay ap1 = new AbstractionPlay();
//		ap1.AbstractionPlay_Test();
		
		// interfaces
//		InterfacesPlay ip1 = new InterfacesPlay();
//		ip1.InterfacePlay();
		
		// functional interface or lambda
//		FunctionalInterfacePlay functionInterface = new FunctionalInterfacePlay();
//		functionInterface.FunctionalInterfacePlay_Entry();
		
		// test lamda
//		LambdaPlay lambda = new LambdaPlay();
//		lambda.TestLamdaPlay();
		
		PredicatePlay predicatePlay = new PredicatePlay();
		predicatePlay.PredicatePlay_Enter();
	}
	

	
	

	public void printMapper () {
		System.out.println("print mapper size " + mapper.size());
		mapper.entrySet().forEach( entry -> {
			System.out.println(entry.getKey() + " " + entry.getValue().value);
		});
	}

	public HashMap <String, DatePlay> getMapper() {
		return mapper;
	}

	public void ChangeDate (DatePlay d, Integer change) {
		System.out.println("Before - DatePlay " + d.value);
		d.value = change;
		System.out.println("After - DatePlay " + d.value);
		change = 1000;
		this.x = 1000;
	}
	
	public void ChangeIntegerValue(int value[]) {
		value[0] = 1000;
	}
	
	public void ChangeIntegerValue1(String message) {
		message += " world";
	}
}