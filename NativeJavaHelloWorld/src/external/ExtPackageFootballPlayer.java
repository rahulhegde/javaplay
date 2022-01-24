package external;
import abstaction.Player;

public class ExtPackageFootballPlayer extends Player {
	
	// this has to be public as the main function is in another package
	// making it protected will not make main function calling this constructor to be accessible
	// thus giving a compile time error.
	public ExtPackageFootballPlayer (int count) {
		super(count);
	}
	
	// only protected variable/method accessible to this subclass being outside the package
	// no access to default
	@Override
	protected String getName() {
		System.out.println("protectedInt: " + this.protectedInt);
		return "Football";
	}
	
	// cannot reduce the visibility in subclass then what is defined in parent class
	// Reference: https://stackoverflow.com/questions/6851612/when-overriding-a-method-why-can-i-increase-access-but-not-decrease-it
	@Override
	public String getNameOfTheTeam() {
		return "FootballTeam";
	}
}
