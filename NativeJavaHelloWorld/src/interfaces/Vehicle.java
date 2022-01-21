package interfaces;

public interface Vehicle {
	
	// Reference: https://stackoverflow.com/questions/2430756/why-are-interface-variables-static-and-final-by-default#:~:text=In%20Java%20%2C%20interface%20doesn't,different%20from%20an%20instance%20variable.
	// Interface variables are static because Java interfaces cannot be instantiated in their own right; 
	// the value of the variable must be assigned in a static context in which no instance exists. 
	// The final modifier ensures the value assigned to the interface variable is a true constant 
	// that cannot be re-assigned by program code.
	public static int tireCount = 1;
	
	static int getTireCount() {
		return tireCount;
	}
	
	// interface function to implement
	void Ride();
	
	// extension or default method for interface that can be overridden
	default public void Model() {
		System.out.println("default method/extension method implementation of vehicle used");
	}
}