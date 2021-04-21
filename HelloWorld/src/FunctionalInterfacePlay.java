
// Refer compilation error report upon use of @FunctionalInterface 
// defining the class as not a interface
@FunctionalInterface
public class FunctionalInterfaceBar {
	public void method();
}

@FunctionalInterface
public interface FunctionalInterfaceZar {
	public void method();
}

public class FunctionInterfaceExtended extends FunctionalInterfaceBar, FunctionalInterfaceZar {
	
}


