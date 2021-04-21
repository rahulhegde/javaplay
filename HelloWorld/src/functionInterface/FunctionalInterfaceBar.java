package functionInterface;

@FunctionalInterface
public interface FunctionalInterfaceBar {
	public void method(int a);
	
	default void oneMoreMethod() {
		System.out.println("one more default/extension method in functional interface");
	}
}