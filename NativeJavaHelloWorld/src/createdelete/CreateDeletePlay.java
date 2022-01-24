package createdelete;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;

public class CreateDeletePlay {

	void TestStaticInstanceInitilizer() {
		StaticNConstructorChildInitializer t = new StaticNConstructorChildInitializer();
	}

	void TestCleanerExample() {
		try (HotelRoom h202 = new HotelRoom(202)) {
		}
		System.out.println("TestCleanerExample");
	}

	void TestWeakReference() {
		Integer cost = new Integer(100);
		WeakReference<Integer> costWR = new WeakReference<Integer>(cost);
		cost = null;
		System.out.println(costWR.get());

		boolean status = true;
		while (status == true) {
			System.out.println(
					"weak reference availability status pre-GC value: " + costWR.get() + ", status: " + status);
			System.gc();
			status = costWR.get() != null;
			System.out.println(
					"weak reference availability status pre-GC value: " + costWR.get() + ", status: " + status);
		}
	}
	
	void TestCacheUsingValueOf( ) {
		String str1 = new String("new");
		String str2 = new String("new");
		
		if (str1 != str2) {
			System.out.println("string reference is different for one created using new operator, else use intern/literal");
		}

		// provides caching functionality thus giving same object reference for same value
		Integer I1 = Integer.valueOf(1);
		Integer I2 = Integer.valueOf(1);
		if (I1 == I2) {
			System.out.println("integer reference of two different object is same due to caching: " + I1 + I2);
		}

		BigDecimal b1 = BigDecimal.valueOf(1);
		BigDecimal b2 = BigDecimal.valueOf(1);
		if (b1 == b2) {
			System.out.println("big decimal reference of two different object is same due to caching: " + b1 + b2);
		}
		
		BigDecimal b3 = BigDecimal.valueOf(100);
		BigDecimal b4 = BigDecimal.valueOf(100);
		if (b3 != b4) {
			System.out.println("big decimal reference of two different object is not same as caching is limited: " + b3.equals(b4));
		}
		
	}

	public void TestCreateDeletePlay() {
		// can have multiple initializer in the class, order in the class defines the
		// execution
		// static initializer - called whenever any static member or instance is
		// created.
		// instance initializer - called before constructor for the instance is called.
		// https://stackoverflow.com/questions/335311/what-is-the-difference-between-a-static-and-a-non-static-initialization-code-blo
		TestStaticInstanceInitilizer();

		// implements autocloseable that calls override close to complete
		// all graceful resource deallocation - socket, non jvm-memory.
		TestCleanerExample();

		// References are hints to the GC if the heap allocated memory does not have any
		// references in the application (JVM) then GC would 
		// - if marked WeakReference will be eagerly GCed
		// - if marked SoftReference will be GCed only if there is a low memory condition like OOM
		TestWeakReference();
		
		// shows usage of caching upon use of valueof static API. Caching is applicable limited set 
		TestCacheUsingValueOf();
	}
}
