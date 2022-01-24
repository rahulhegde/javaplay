package abstaction;

// abstract class cannot be instantiated
abstract public class Player {
	// accessible only within this class
	private int count;
	
	private int privateInt;
	// accessible to classes within this package
	// accessible to subclass extending player outside this package
	protected int protectedInt;

	// accessible to classes within this package
	int defaultInt;
	
	// accessible to classes within and outside this package
	public int publicInt;
	
	public static int staticInt;
	
	// this will be accessible from external package, ExtPackageFootballPlayer.java
	// if protected is removed i.e. default then a compile time error of not visible 
	protected Player (int count) {
		this.count = count;
	}
		
	public int getCount() {
		return count;
	}
	
	// cannot be private
	abstract protected String getName();
	
	abstract public String getNameOfTheTeam();
}
