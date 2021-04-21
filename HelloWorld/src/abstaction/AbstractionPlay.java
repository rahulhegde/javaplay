package abstaction;

public class AbstractionPlay {

	public void AbstractionPlay_Test () {
		// implement the abstract API only if it is been used
		Player p1 = new FootballPlayer(10);
		System.out.println("football player count: " + p1.getCount());
		// p1.getNameOfTheTeam();
	}
}
