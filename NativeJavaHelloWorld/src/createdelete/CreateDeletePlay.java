package createdelete;

public class CreateDeletePlay {

	void TestInitilizer() {
		StaticNConstructorChildInitializer t = new StaticNConstructorChildInitializer();
	}

	void TestCleanerExample() {
		try (HotelRoom h202 = new HotelRoom(202)) {
			
		}
		System.out.println("TestCleanerExample");
	}

	public void TestCreateDeletePlay() {
		TestInitilizer();
		TestCleanerExample();
	}
}
