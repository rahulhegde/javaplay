package collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class ListPlay {

	void ListPlay_ArrayList( ) {
		List<Integer> arrayList = new ArrayList<Integer>();

		int index = 100;
		for (;index > 0;index--) {
			arrayList.add(1);
			arrayList.add(5);
			arrayList.add(3);
		}		

		for (Integer i : arrayList) {
			System.out.println("element in array list: " + i);
		}
	}

	// Reference: https://stackoverflow.com/questions/40930861/what-is-the-use-of-collections-synchronizedlist-method-it-doesnt-seem-to-syn
	void ListPlay_CollectionSynchronizedList( ) {
		List<String> synList = Collections.synchronizedList(new ArrayList<String>());

		Thread t1 = new Thread (new Runnable() {
			@Override
			public void run() {
				int i;
				// if synchronized is enabled, this will locked until the sync code
				// block is complete hence the loop will be complete with 10 entries 
				// of thread 1 - ... and then 10 more entries of thread 2 - ... 
				// (order can change)
				// synchronized (synList) 
				{
					for (i = 10; i > 0; i--) {
						System.out.println("thread Id: " + Thread.currentThread().getName() + " - "  + i);
						// synchronized list created using collections synchronize at the 
						// API entries.
						synList.add("thread 1 - " + i);
					}
				}
			}
		});

		Thread t2 = new Thread (new Runnable() {
			@Override
			public void run() {
				int i;
				//synchronized (synList) 
				{
					for (i = 20; i > 10; i--) {
						System.out.println("thread Id: " + Thread.currentThread().getName() + " - " +  i);
						synList.add("thread 2 - " + i);
					}
				}
			}
		});		

		// start the thread processing
		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("size: " + synList.size());
	}

	// accepts all classes extending from shape - shape, circle and rectangle
	void ListPlay_Wildcard_UpperBound(List<? extends Shape> extList) {
		System.out.println("upperbound extends: " + extList);
	}

	// accepts all classes super of Cirle such that Cirle and Shape
	void ListPlay_Wildcard_LowerBound(List<? super Circle> extList) {
		System.out.println("lowerbound super: " + extList);
	}

	// accepts shapes only
	void ListPlay_Wildcard_Exact(List<Shape> extList) {
		System.out.println("Exact: " + extList);
	}


	void ListPlay_Wildcard() {
		List<Shape> shapeList = new ArrayList<Shape>();
		shapeList.add(new Circle());
		shapeList.add(new Rectangle());

		List<Rectangle> rectangleList = new ArrayList<Rectangle>();
		rectangleList.add(new Rectangle());

		List<Circle> cirleList = new ArrayList<Circle>();
		cirleList.add(new Circle());

		// upperbound - accepts cirle, rectangle and circle list
		// use case - Bounded wildcards are just what one needs to handle the example of the DMV passing 
		// its data to the census bureau. Our example assumes that the data is represented by mapping 
		// from names (represented as strings) to people (represented by reference types such as Person or its subtypes, 
		// such as Driver). Map<K,V> is an example of a generic type that takes two type arguments, 
		// representing the keys and values of the map.
		ListPlay_Wildcard_UpperBound(shapeList);
		ListPlay_Wildcard_UpperBound(rectangleList);
		ListPlay_Wildcard_UpperBound(cirleList);

		// lowerbound - accept circle -> shape list only
		ListPlay_Wildcard_LowerBound(shapeList);
		ListPlay_Wildcard_LowerBound(cirleList);
		// fails compilation if enabled as it rectangle is 
		// not linked with circle
		// ListPlay_Wildcard_LowerBound(rectangleList);

		// exact accept Shape list only
		ListPlay_Wildcard_Exact(shapeList);
		// fails compilation as this API accepts list of Shapes
		//	ListPlay_Wildcard_Exact(cirleList);
		//	ListPlay_Wildcard_Exact(rectangleList);

	}

	// removeif is O(n) v/s o(nxm) for iterate + iterator.remove approah
	void ListPlay_RemoveIf_Predicate( ) {	
		// java.util.ArrayList supports removeIf where Arrays.asList returns Arrays.ArrayList
		// which does not support removeIf hence typecast
		List<Integer> singlePredicateList = new ArrayList(Arrays.asList(1, 3, 5, 8, 10, 20, 30, 40, 60, 30));
		Predicate<Integer> isGreaterThanTen = n -> n > 10;
		singlePredicateList.removeIf(isGreaterThanTen);
		System.out.println("Remove entries greater than 10: " + singlePredicateList);
		
		// predicate chaining works
		List<Integer> predicateChainLists = new ArrayList(Arrays.asList(1, 3, 5, 8, 10, 20, 30, 40, 60, 30));
		Predicate<Integer> isLessThanEqual60 = n -> n <= 60;
		predicateChainLists.removeIf(isLessThanEqual60.and(isGreaterThanTen));
		System.out.println("Predicate Chaining works - Remove entries if >10 and <= 60: " + predicateChainLists);
		
		// double condition in predicate - works
		List<Integer> doubleConditionPredicateLists = new ArrayList(Arrays.asList(1, 3, 5, 8, 10, 20, 30, 40, 60, 30));
		Predicate<Integer> isGreaterThan10andLessThanEqual60 = (n -> (n > 10  && n <= 60));
		doubleConditionPredicateLists.removeIf(isGreaterThan10andLessThanEqual60);
		System.out.println("Double Condition works in Predicate - Remove entries >10 and <= 60: " + doubleConditionPredicateLists);

		// combining multiple predicates and testing for the number
		System.out.println("predicate test API is 11 > 10 AND <= 60: " + 
				isGreaterThanTen.and(isLessThanEqual60).test(12));
		
		// removeIf - predicate on the object
		List<Shape> shapeList = new ArrayList(Arrays.asList(new Rectangle(), new Circle()));
		Predicate<Shape> detectShapeCircle = shape -> (shape.type().equals("Circle"));
		shapeList.removeIf(detectShapeCircle);
		System.out.println("circle removed - " + shapeList);
	}
	
	
	public void TestListPlay() {
		System.out.println("** Entering Collection List Test ***");

		//ListPlay_ArrayList();
		//ListPlay_CollectionSynchronizedList();

		//ListPlay_Wildcard();
		
		//ListPlay_RemoveIf_Predicate();
	}


}
