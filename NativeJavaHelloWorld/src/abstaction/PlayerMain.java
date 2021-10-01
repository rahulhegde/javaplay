package abstaction;

import java.util.ArrayList;
import java.util.List;

public class PlayerMain {

	public static void main(String[] args) {
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
}
