package interfaces;

// an interface is a reference type, similar to a class, that can contain only constants, method signatures, 
// default methods, static methods, and nested types. Method bodies exist only for default methods and static methods. 
// Interfaces cannot be instantiated—they can only be implemented by classes or extended by other interfaces.

// Vehicle is yet another interface that can be extended by another interface Vehicle2. 
// Interface can extend multiple interfaces whereas class can extend only 1 class.
// This can help to method override default as abstract OR abstract as default or default by another default (check MasterRide method)
// methods - defaults, abstract and static are implicit public if not specified.
// variables - are implicit public, static and final
// public interface indicates available for implementation across packages, can be controlled
public interface Vehicle2Interface extends VehicleInterface {
	// Interface variables are static because Java interfaces cannot be instantiated in their own right;
	// the value of the variable must be assigned in a static context in which no instance exists.
	// The final modifier ensures the value assigned to the interface variable is a true constant
	// that cannot be re-assigned by program code.
	// Reference: https://stackoverflow.com/questions/2430756/why-are-interface-variables-static-and-final-by-default
	public static int tireCount = 1;

	// method body - static method are more used as utility methods.
	static int getTireCount() {
		return tireCount;
	}

	// abstract or interface function to be implemented
	void Ride2(DIRECTION type);
	
	// abstract or interface function to be implemented
	void Compare(Vehicle2Interface type);
	
	// uncomment this method to force classes like Bike/Bus to implement this method/
	// there is default implementation in the VehicleMasterInterface for MasterRide
	// void MasterRide();

	// default method for interface that can be overridden by implementing class
	// default was introduced so it provides backward compatibility to existing
	// classes that have implemented
	// the interface otherwise any a new interface method must always be
	// implemented.
	default public void Model() {
		System.out.println("default method/extension method implementation of vehicle used");
	}
	
	public enum DIRECTION {
		RIGHT,
		LEFT,
		CENTER
	}
}