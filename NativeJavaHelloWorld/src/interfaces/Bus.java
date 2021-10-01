package interfaces;

public class Bus implements Vehicle2 {

	@Override
	public void Ride() {
		System.out.println("Ride1 Bus!!, tire count: " + Vehicle.tireCount);
	}

	@Override
	public void Ride2() {
		System.out.println("Ride2 Bus!!, tire count: " + Vehicle.tireCount);
	}
}
