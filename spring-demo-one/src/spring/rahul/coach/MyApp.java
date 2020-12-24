package spring.rahul.coach;

public class MyApp {

	public static void main(String[] args) {
		FortuneService myFortuneService = new HappyFortuneService();
		Coach myCoach = new FootballCoach(myFortuneService);
		
		System.out.println(myCoach.getSuggestion());
	
	}
}
