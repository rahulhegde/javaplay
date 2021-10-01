package spring.rahul.coach;

public class FootballCoach implements Coach {

	private FortuneService myFortuneService;

	public FootballCoach(FortuneService myFortuneService) {
		this.myFortuneService = myFortuneService;
	}

	@Override
	public String getSuggestion() {
		return "Play for 20mins as a Goal-Keeper";
	}

	@Override
	public String getFortune() {
		return "Just do it: "  + myFortuneService.getFortune();
	}
	
	@Override
	public String getFortuneAddress() {
		return myFortuneService.toString();
	}
}
