import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import DatePlay.DatePlay;

class Example {
	HashMap<String, DatePlay> mapper = new HashMap<String, DatePlay>();
	Integer x;

	void print() {
		SubExample ee2 = new SubExample();
		ee2.check();
	}

	class SubExample {
		void check() {
			System.out.println("printing integer" + x);
		}
	}

	static Consumer<Integer> lambdaWrapper(Consumer<Integer> consumer) {
		return i -> {
			try {
				consumer.accept(i);
			} catch (ArithmeticException e) {
				System.err.println("Arithmetic Exception occured : " + e.getMessage());
			}
		};
	}
	
	private Map<String, Boolean> clientIdExistHolder = new HashMap<>();
	
	public void parse(List<String> files) {
		
		for (String file : files) {
			FileInputStream filehandle;
			String[] splits;
			try {
				filehandle = new FileInputStream(file);
				splits = filehandle.readAllBytes().toString().split(",");
				if (splits.length > 0) {
					clientIdExistHolder.put(splits[1], true);
				}
				
			} 
			catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void example(List<Integer> arr) {
		Collections.sort(arr);
		System.out.println(arr);
		List<Integer> returnArr = new ArrayList<>();
		if (arr.size() > 2) {
			int size = arr.size();
			returnArr.add(arr.get(size -1));			
			returnArr.add(arr.get(size -2));
			int b_sum = 0;
			returnArr.forEach(i -> System.out.println(sum + i));
			System.out.println("sum: " + sum);
			
			int a_counter = 0;;
			int b_counter = size - 2;
			int a_sum =0;
			for (; a_counter < b_counter; a_counter++) {
				if (a_sum + arr.get(a_counter) > b_sum) {
					returnArr.add(arr.get(a_counter));
					b_counter--;
				}
			}			
		}else {
			returnArr.addAll(arr);
		}
		return returnArr;	
	}

	public static void main(String args[]) {

//		int i = 100;
//		int j = 200;
//		StringBuilder key = new StringBuilder();
		// System.out.println(key.append(i).append('_').append(j)());

//		ExceptionPlay excepPlay = new ExceptionPlay();
//		excepPlay.exceptionPlay();

//		Map<String, Boolean> holder = new HashMap<>(2);
//		holder.put("1", true);
//		holder.put("2", true);
//
//		if (holder.get("1") == holder.get("2") && holder.get("1") == Boolean.TRUE) {
//			System.out.println("true object is same");
//		} else {
//			System.out.println("true object is not same");
//		}

		StringPlay s1 = new StringPlay();
		s1.StringPlayTest();

		//System.out.println("Check Electric State: " + ElectricState.NE.isSame(4));

//		EnumPlay esp = new EnumPlay();
//		esp.EnumPlayTest();

//		StaticCollection st =new StaticCollection();
//		st.print();

//		CreateDeletePlay c = new CreateDeletePlay();
//		c.TestCreateDeletePlay();
//
//		DatePlay datePlay = new DatePlay();
// 		datePlay.TestDatePlay();

// Abstract & Interface Class
//		ExtPackageFootballPlayer footballPlayer = new ExtPackageFootballPlayer(11);
//		System.out.println("player - " + footballPlayer);

//		AbstractionPlay ap1 = new AbstractionPlay();
//		ap1.TestAbstractionPlay();
		
//		InterfacesPlay ip1 = new InterfacesPlay();
//		ip1.InterfacePlay();

		
//		ListPlay listPlay = new ListPlay();
//		listPlay.TestListPlay();

//		LambdaPlay lambda = new LambdaPlay();
//		lambda.TestLamdaPlay();
//
//		PredicatePlay predicatePlay = new PredicatePlay();
//		predicatePlay.PredicatePlay_Enter();

//		ConcurrentHashMapPlay p = new ConcurrentHashMapPlay();
//		p.ConcurrentHashMapPlayTest();

	}

	public void printMapper() {
		System.out.println("print mapper size " + mapper.size());
		mapper.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + " " + entry.getValue().value);
		});
	}

	public HashMap<String, DatePlay> getMapper() {
		return mapper;
	}

	public void ChangeDate(DatePlay d, Integer change) {
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
	
	public void TestWithFileOpen() {

		try (BufferedReader buffer = new BufferedReader(new FileReader("hello"))) {
			String buf = buffer.readLine();
			if (buf != null) {
				System.out.println(buf);
			}
			Files.lines(Paths.get("filename")).forEach(System.out::println);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}