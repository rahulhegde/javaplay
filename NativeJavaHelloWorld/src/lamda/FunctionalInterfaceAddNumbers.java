package lamda;

@FunctionalInterface
public interface FunctionalInterfaceAddNumbers {
	int sum(int a, int b);
	
	default void printer() {
		System.out.println("default or extension method in interface");
	}
 }
