package collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class ListPlay {
	
	public ListPlay() {
		System.out.println("*** Collection Play ***");
	}
	
	public enum State {
		ON(1), OFF(2);
		
		int state;

		State(int i) {
			System.out.println("enum state: " + i);
			state = i;
		}

		int getState() {
			return state;
		}
	}

	void TestListView( ) {
		// create an list and then slice. The slice point to underlying data structure.
		// so there is no duplicate list created.
		List<Integer> arrayList = new ArrayList<Integer>();
		int index = 15;
		for (;index > 0;index--) {
			arrayList.add(1);
			arrayList.add(5);
			arrayList.add(3);
		}		
		System.out.println("list - size: " + arrayList.size() + ", elements: " + arrayList);

		// non structural changes - like updates are safe during iteration
		List<Integer> sliceList = arrayList.subList(0, 10);
		sliceList.set(1, 2);
		arrayList.subList(0, 10);
		System.out.println("list size - slice of Ten, element update on index=1, slicelist size: " + sliceList.size() + ", elements: " + sliceList);
	
		//	structural changes - changing size may not be safe and can throw exception, 
		// if there is iteration in-progress in a separate thread
		sliceList.subList(3, 5).clear();
		System.out.println("list size - after deleting 2 elements from slicelist, array size: " + arrayList.size() + 
				", sliceList size: " + sliceList.size());
	}

	// Reference: https://stackoverflow.com/questions/40930861/what-is-the-use-of-collections-synchronizedlist-method-it-doesnt-seem-to-syn
	void ListPlay_CollectionSynchronizedList( ) {
		
		// caution during iterator use on synchronized list or any synchronized collection
		// Reference: https://stackoverflow.com/questions/18873547/need-to-manually-synchronize-the-synchronized-list-while-iteration-when-it-could
		// hasNext if true then use Next, however there can be many changes to list thus marking iterator completely
		// invalid. These iterator operations needs to be synchronized in total by programmer.
		
		// Check the iterator position: Reference: https://docs.oracle.com/javase/8/docs/api/java/util/ListIterator.html
		// it is between the elements. So three element list will have 4 positions.
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

		System.out.println("synchronized list, size: " + synList.size());
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
		System.out.println("predicate test API is 11 > 10 AND <= 60: " + isGreaterThanTen.and(isLessThanEqual60).test(12));

		// removeIf - predicate on the object
		List<Shape> shapeList = new ArrayList(Arrays.asList(new Rectangle(), new Circle()));
		Predicate<Shape> detectShapeCircle = shape -> (shape.type().equals("Circle"));
		shapeList.removeIf(detectShapeCircle);
		System.out.println("circle removed - " + shapeList);
	}


	public void TestListPlay() {
		// Best Practice
		// list sort internally uses mergesort, works well on partial sorted list 
		// listiterator - provides bi-directional, a special list iterator for bi-directional traversal
		// avoid performing traversal using list[index], use iterator.
		// list is not re-entrant and hence use concurrent collections/list
		// 
				
		System.out.println("State: " + State.ON);

		// use of sublist or view from the List
		TestListView();
		
		ListPlay_CollectionSynchronizedList();

		// shows extends/super/exact match 
		ListPlay_Wildcard();

		ListPlay_RemoveIf_Predicate();
	}


}
