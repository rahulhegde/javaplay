package createdelete;

public class StaticNConstructorChildInitializer extends StaticNConstructorParentInitializer {
	private final static int staticValue = staticMethod();

	public static int staticMethod() {
		System.out.println("class static variables are created after all static initializer calls are complete");
		return 1;
	}

	
	// static initializer 
	static 
	{
		System.out.println("3 - static child - initializer 1");
	} 
	
	// instance 
	{
		System.out.println("8 - constructor child - initializer 1");
	}

	
	StaticNConstructorChildInitializer() {
		System.out.println("10 - constructor child - inside");
	}

	{
		System.out.println("9 - constructor child - initializer 2");
	}
	
	static {
		System.out.println("4 - static child - initializer 2");
	}
}
