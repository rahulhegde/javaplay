package interfaces;

public class Bike implements Vehicle2Interface {

	@Override
	public void Ride() {
		System.out.println("Riding Bike!!, tire count: " + VehicleInterface.tireCount);
	}

	@Override
	public void Ride2(DIRECTION type) {
		System.out.println("Riding Bike2!!, tire count: " + VehicleInterface.tireCount + ", Direction: " + type);
	}
	
	@Override
	public void Model() {
		System.out.println("Bike overrides the default/extension method implementation");
	}

	@Override
	public void Compare(Vehicle2Interface type) {
		// TODO Auto-generated method stub
	
	}

}
