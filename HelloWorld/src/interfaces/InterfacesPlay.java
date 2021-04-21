package interfaces;

public class InterfacesPlay {

	void InterfacePlay_HelloWorld( ) {
		Vehicle2 v1 = new Bike();
		// class that implements interface must implement/override 
		// the function if that is been used otherwise there is runtime
		// exception
		v1.Ride();		
		v1.Model();

		Vehicle2 b1 = new Bus();
		b1.Ride2();		
		b1.Model();
	}
	
	public void InterfacePlay () {
		System.out.println("** Entering Interface Test ***");

		InterfacePlay_HelloWorld();
	}
}