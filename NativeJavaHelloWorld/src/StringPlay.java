
public class StringPlay {

	private void jumpToAnotherFunction() {
		String s = "hello";
		System.out.println("sting constant addr: " + s.hashCode());		
	}
	
	
	private void Test_StringPlay_StringPoolConstant( ) {
		// string created on heap - separate memory address
		String s1 = new String("hello"); // goes on heap

		// string created on heap - separate memory address
		String s3 = new String ("hello");
		
		// memory address are different
		if (s1 != s3 ) {
			System.out.println("1 - heap string references are always not equal");
		}
		
		// string that are constant are stored on Heap but dedicated area called String Constant Pool
		String s2 = "hello"; // constant stored on string constant 
		
		// forces to create memory in SCP if not available but s4 and s2 address are same but not s1/s2 or s
		String s4 = s1.intern(); // constant stored on string constant 
		
		if (s2 == s4 ) {
			System.out.println("2 - heap address from SCP is same - s2 and s4");
		}
		
		if (s1 != s4 ) {
			System.out.println("3 - heap address (separate) and heap address from SCP are not same - s1 and s4");
		}

		// there is already SCP constant stored and hence re-use
		String s5 = s3.intern(); 
		if (s5 == s4 ) {
			System.out.println("4 - heap string from SCP is reused - s5 and s4");
		}
		
		System.out.println("sting constant addr: " + s5.hashCode());	
		
		jumpToAnotherFunction();	
		
		
	}
	
	public void StringPlayTest() {
		Test_StringPlay_StringPoolConstant();
	}
}
