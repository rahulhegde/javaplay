package abstaction;

public class TennisPlayer extends Player {

	TennisPlayer(int count) {
		super(count);
	}

	@Override
	protected String getName() {
		return "cricket";
	}
	
	@Override
	public String getNameOfTheTeam() {
		return "TennisTeam";
	}

}
