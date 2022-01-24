package enums;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

import abstaction.PrimitiveHolder;

public class EnumPlay {

	private static int value = 10;
	
	public EnumPlay() {
		System.out.println("static variable - value: " + EnumPlay.value);
	}
			
	
	private void TestEnum_OrderOfEnumConstantCreation() {
		System.out.println("inside test - TestEnum_OrderOfEnumConstantCreation");
		Current.printCurrentStateEnums();
		Current state = Current.ON;
		
		Current.ON.specialHandling();
		Current.ON.specialHandlingPerConstant();
		
		System.out.println("compare On vs Off - " + Current.ON.compareTo(Current.OFF));
	}
		
	// alternate to bitwise AND operation
	// Best to store 64 entries - uses RegularEnumSet such single long field, 
	// Post > 64 - JumboEnumSet is used, spanned across multiple long
	private void TestEnum_EnumSetExample() {
		EnumSet<TextFormaterEnum> formats = EnumSet.of(TextFormaterEnum.BOLD, TextFormaterEnum.UNDERLINE);
		
		System.out.println("contains BOLD test - " +  formats.contains(TextFormaterEnum.BOLD));
		System.out.println("contains ITALIC test - " +  formats.contains(TextFormaterEnum.ITALIC));
		System.out.println("contains UNDERLINE test - " +  formats.contains(TextFormaterEnum.UNDERLINE));	
		
		System.out.println("toString for enum set - " +  formats.toString());	
		
		// contains all - input is an collection
		System.out.println("containsAll (Italics, Underline) for enum set - " +  formats.containsAll(Arrays.asList(TextFormaterEnum.ITALIC, TextFormaterEnum.UNDERLINE)));	
		System.out.println("containsAll (Bold, Underline) for enum set - " +  formats.containsAll(Arrays.asList(TextFormaterEnum.BOLD, TextFormaterEnum.UNDERLINE)));
		
		
		// Same EnumSet object can be altered - it is not immutable
		// difference between final v/s immutable /unmodifiable set/object like string
		// https://stackoverflow.com/questions/2420096/what-does-collections-unmodifiableset-do-in-java/2420153
		formats.remove(TextFormaterEnum.BOLD);
		System.out.println("contains BOLD test after remove - " +  formats.contains(TextFormaterEnum.BOLD));
		System.out.println("contains UNDERLINE test - " +  formats.contains(TextFormaterEnum.UNDERLINE));	
		
		// creating a empty enumset
		EnumSet<TextFormaterEnum> emptyEnumSet = EnumSet.noneOf(TextFormaterEnum.class);
		System.out.println("emptyEnumSet - check " + emptyEnumSet.contains(TextFormaterEnum.BOLD));
	}
	
	// enum map represent backed datastructure as an array with key element index
	// based on the ordinal of the element
	// there is no JumboEnumMap as there is in EnumSet
	private void TestEnum_EnumMapExample() {
		Map<Current, String> enumMap = new EnumMap<>(Current.class);
		enumMap.put(Current.OFF, "OFF");
		enumMap.put(Current.ON, "ON");
		enumMap.put(Current.INVALID, "INVALID");
		System.out.println("print enum map using for_each: " + enumMap);
		enumMap.forEach((key, value) -> {
			System.out.println("printing from enum map - key: " + key + ", value: " + value);
		});
		
	}
	
	public void TestPrimitiveHolder(PrimitiveHolder<Boolean> holder) {
		holder.setValue(true);	
	}
	
	public void EnumPlayTest( ) {
		// 
		TestEnum_OrderOfEnumConstantCreation();
		
		// enum set example, a replacement of bitwise operation
		TestEnum_EnumSetExample();
				
		// enum map example
		TestEnum_EnumMapExample();

		// boxed primitive
		PrimitiveHolder<Boolean> holder = new PrimitiveHolder<>(false);
		System.out.println("primite holder -1: " + holder.getValue());
		TestPrimitiveHolder(holder);
		System.out.println("primite holder - 2: " + holder.getValue());
	}
}
