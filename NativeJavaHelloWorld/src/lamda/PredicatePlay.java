package lamda;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

// Predicate are predefined functional interface and has negate, and as default defined methods
public class PredicatePlay {


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

	// Reference:
	// http://tutorials.jenkov.com/java-functional-programming/functional-composition.html
	// take 1 input and returns 1 output
	void Function_Test() {
		Function<Integer, Integer> adder = (n) -> (n + 5);
		Function<Integer, Integer> multiply = (n) -> (n * 10);

		// compose is composition
		System.out
				.println("function compose: 5 + 5 => + 5 => X 10: " + multiply.compose(adder.compose(adder)).apply(5));

		System.out.println("function andThen: 5 X 10 => + 5: " + multiply.andThen(adder).apply(5));
	}

	// takes no argument but returns - like New Object
	void Suppier_Test() {
		Supplier<Double> randomNumber = Math::random;
		System.out.println(randomNumber.get());
	}

	void Comparator_Test() {
		Comparator<Person> comparePersonByAge = (p1, p2) -> {
			if (p1.getAge() > p2.getAge()) {
				return 1;
			}
			return 0;
		};

		Person pOlder = new Person(50);
		Person pYounger = new Person(10);

		System.out.println("Comparator: person compare: " + comparePersonByAge.compare(pOlder, pYounger));
	}

	void TestPractice() {
		// create a Predicate that does string empty check using lamda and method reference

		// create a Predicate that check number is between 20 and 30 (inclusive).
		
		// create a supplier, take no argument, creates output. 
		// Practice - which creates a new object like Person or current LocalDate
		
		// create a comparator which compares two object, take 2 object and compare
		// based on attribute (age) to say equal(0), greater(1), less than(-1)

		// create Consumer<?>, take input and no output (accept and andThen), take
		// number input and get the % of 10 (5/10 -> 50%).

		// create a Function<?> takes input, give output -> take input, add 10 and
		// returns the summation

		Predicate<String> stringEmptyCheckv1 = String::isEmpty;
		System.out.println("Practice: " + stringEmptyCheckv1.test("rahul"));

		Predicate<String> stringEmptyCheckv2 = n -> n.isEmpty();
		System.out.println("Practice: " + stringEmptyCheckv1.test(""));

		Function<Integer, Integer> addition = (a) -> a + 20;
		Function<Integer, Integer> multiplier = (a) -> a * 10;
		System.out.println("Practice Function " + addition.compose(multiplier).apply(30));

		Supplier<LocalDate> getLocalDate = LocalDate::now;
		System.out.println("Practice supplier: " + getLocalDate.get());

		Comparator<Person> compareByAge = (p1, p2) -> {
			int status = 0;
			if (p1.getAge() > p2.getAge()) {
				status = 1;
			}
			return status;
		};

		Person oldperson1 = new Person(10);
		Person youngperson1 = new Person(5);
		Person oldperson2 = new Person(10);

		System.out.printf("Practice - comparator - expected 1: %d %n", compareByAge.compare(oldperson1, youngperson1));
		System.out.printf("Practice - comparator - expected 0: %d %n", compareByAge.compare(oldperson1, oldperson2));

		Consumer<Integer> consumer1 = (a) -> {
			System.out.println(" print consumer 1: " + a);
		};
		
		Consumer<Integer> consumer2 = (b) -> {
			System.out.println(" print consumer 2: " + b);
		};
		
		consumer1.andThen(consumer2).accept(10);
	}
	

	public void PredicatePlay_Enter() {
		TestPractice();

		Predicate_Integer();
		Predicate_StaticMethod();

		Function_Test();
		Suppier_Test();
		Comparator_Test();
		
		Predicate<Integer> greaterThan20 = a -> a > 20;
		System.out.println("***** " + greaterThan20.test(200));
	}
}
