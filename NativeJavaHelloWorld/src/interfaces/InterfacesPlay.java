package interfaces;

import interfaces.Vehicle2Interface.DIRECTION;

public class InterfacesPlay {

	void InterfacePlay_HelloWorld( ) {
		Vehicle2Interface v1 = new Bike();
		// class that implements interface must implement/override all methods
		// the function if that is been used otherwise there is runtime exception
		v1.Ride();	
		v1.Ride2(DIRECTION.RIGHT);
		v1.Model();

		Vehicle2Interface b1 = new Bus(100);	
		b1.Ride2(Vehicle2Interface.DIRECTION.CENTER);
		b1.Compare(b1);
		b1.Model();
	}
	
	public void InterfacePlay () {
		System.out.println("** Entering Interface Test ***");

		// refer comments added to interface vehicle2
		InterfacePlay_HelloWorld();
	}
}