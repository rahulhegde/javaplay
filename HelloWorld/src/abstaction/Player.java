package abstaction;

abstract public class Player {
	private int count;
	
	@SuppressWarnings("unused")
	private int privateInt;
	protected int protectedInt;
	int defaultInt;
	public int publicInt;
	
	public static int staticInt;
	
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
