package createdelete;

public class StaticNConstructorChildInitializer extends StaticNConstructorParentInitializer {

	
	static 
	{
		System.out.println("3 - static child - initializer 1");
	} 
	
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
