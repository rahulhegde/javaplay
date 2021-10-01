package interfaces;

public class Bike implements Vehicle2 {

	@Override
	public void Ride() {
		System.out.println("Riding Bike!!, tire count: " + Vehicle.tireCount);
	}

	@Override
	public void Ride2() {
		System.out.println("Riding Bike2!!, tire count: " + Vehicle.tireCount);
	}
	
	@Override
	public void Model() {
		System.out.println("Bike overrides the default/extension method implementation");
	}

}
