import abstaction.Player;

public class ExtPackageFootballPlayer extends Player {
	
	ExtPackageFootballPlayer (int count) {
		super(count);
	}
	
	@Override
	protected String getName() {
		System.out.println("protectedInt: " + this);
		return "Football";
	}

	@Override
	public String getNameOfTheTeam() {
		return "FootballTeam";
	}
}
