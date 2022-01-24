package abstaction;

import java.util.ArrayList;
import java.util.List;

public class AbstractionPlay {
	
	// public/private - accessibility within and across packages depending upon scope
	// protected - 	accessibility outside package to another class not allowed, but allowed for sub-class 
	//				outside the package
	// default - accessibility within package only
	// Reference: https://beginnersbook.com/2013/05/java-access-modifiers/
	private void TestJavaScope() {
		Player footballPlayer = new FootballPlayer(11);
		Player tenniPlayer = new TennisPlayer(2);

		List<Player> playerList = new ArrayList<Player>();
		playerList.add(footballPlayer);
		playerList.add(tenniPlayer);
		
		
		FootballPlayer footballPlayer2 = new FootballPlayer(11);
		
		@SuppressWarnings("unused")
		int a = footballPlayer2.defaultInt;
		
		playerList.forEach(player -> { System.out.printf("Player: %s, "
				+ "count: %s\n", player.getName(), player.getCount()); });
	}
	
	public void TestAbstractionPlay () {
		TestJavaScope();
	}
}
