package interfaces;

public class Bus implements Vehicle2Interface {
	int value;

	Bus(int value) {
		this.value = value;
	}

	@Override
	public void Ride() {
		System.out.println("Ride1 Bus!!, tire count: " + VehicleInterface.tireCount);
	}

	@Override
	public void Ride2(DIRECTION type) {
		System.out.println("Ride2 Bus!!, tire count: " + VehicleInterface.tireCount + ", DIRECTION: " + type);
	}
	
	@Override
	public void Compare(Vehicle2Interface type) {
		Bus bus1 = (Bus)type;
		System.out.println("bus value - " + bus1.value);		
	}
}

