package createdelete;

public class StaticNConstructorParentInitializer {

	
	static 
	{
		System.out.println("1 - static Parent  - initializer 1");
	}
	
	{
		System.out.println("5 - constructor Parent - initializer 1");
	}

	
	StaticNConstructorParentInitializer() {
		System.out.println("7 - constructor Parent - inside");
	}

	{
		System.out.println("6 - constructor Parent - initializer 2");
	}
	
	static {
		System.out.println("2 - static Parent - initializer 2");
	}
}
