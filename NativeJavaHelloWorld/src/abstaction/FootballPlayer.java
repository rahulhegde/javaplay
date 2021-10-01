package abstaction;

public class FootballPlayer extends Player {
	
	public FootballPlayer (int count) {
		super(count);
	}
	
	public class FootballPlayerInternal {
	}
	
	@Override
	protected String getName() {
		System.out.println("protectedInt: " + this.defaultInt);
		return "Football";
	}

	@Override
	public String getNameOfTheTeam() {
		return "FootballTeam";
	}
}
