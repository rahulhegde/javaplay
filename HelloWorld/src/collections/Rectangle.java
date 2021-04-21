package collections;

public class Rectangle extends Shape {
	private final static String type = "Rectangle";

	@Override
	void draw() {
		System.out.println("drawing Rectangle");
	}

	@Override
	String type( ) {
		return type;
	}
}
