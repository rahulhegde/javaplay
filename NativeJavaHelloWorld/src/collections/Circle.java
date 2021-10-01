package collections;

public class Circle extends Shape {
	private static final String type = "Circle";

	@Override
	void draw() {
		System.out.println("drawing Circle");
	}

	@Override
	String type( ) {
		return type;
	}
	
}
