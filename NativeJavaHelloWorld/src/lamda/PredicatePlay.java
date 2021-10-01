package lamda;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

// Predicate are predefined functional interface
// and has negate/and as default or extension methods
public class PredicatePlay {

	public void PredicatePlay_Enter() {
		System.out.println("** Entering Predicate Test ***");
		Predicate_Integer();
		Predicate_StaticMethod();

		Function_Test();	
		Suppier_Test();
		Comparator_Test();
	}

	// predicate chaining
	void Predicate_Integer() {
		Predicate<Integer> checkGreaterThan20 = n -> n > 20;
		Predicate<Integer> checkLessThan40 = n -> n < 40;


		System.out.println("30 is > 20 AND < 40: " + checkGreaterThan20.and(checkLessThan40).test(30));
		System.out.println("50 is > 20 AND < 40: " + checkGreaterThan20.and(checkLessThan40).test(50));		
	}


	// takes input and returns boolean
	void Predicate_StaticMethod() {
		Predicate<Object> findObjectIsNull = Objects::isNull;
		Predicate<Object> findObjectIsNotNull = findObjectIsNull.negate();

		Integer intObj = 2;

		System.out.println("objects null is null: " + findObjectIsNull.test(null));
		System.out.println("objects intger is null: - " + findObjectIsNull.test(intObj));		

		System.out.println("objects null is not null: " + findObjectIsNotNull.test(null));
		System.out.println("objects integer is not null: " + findObjectIsNotNull.test(intObj));		
	}

	// Reference: http://tutorials.jenkov.com/java-functional-programming/functional-composition.html
	// take 1 input and returns 1 output
	void Function_Test() {
		Function<Integer, Integer> adder = (n) -> (n + 5);
		Function<Integer, Integer> multiply = (n) -> (n * 10);

		// compose is composi
		System.out.println("function compose: 5 + 5 => + 5 => X 10: " + multiply.compose(adder.compose(adder)).apply(5));

		System.out.println("function andThen: 5 X 10 => + 5: " + multiply.andThen(adder).apply(5));
	}

	// takes no argument but returns - like New Object 
	void Suppier_Test() {
		Supplier<Double> randomNumber = Math::random;
		System.out.println(randomNumber.get());
	}

	void Comparator_Test() {
		Comparator<Person> compare = (p1, p2) -> {
			if (p1.getAge() > p2.getAge()) {
				return 1;
			}
			return 0;
		};

		Person pOlder = new Person(50);
		Person pYounger = new Person(10);

		System.out.println("Comparator: person compare: " + compare.compare(pOlder, pYounger));		
	}

}
