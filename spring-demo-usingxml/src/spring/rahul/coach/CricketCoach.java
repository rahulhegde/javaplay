/**
 * 
 */
package spring.rahul.coach;

import java.lang.management.GarbageCollectorMXBean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;

/**
 * @author RahulHegde
 *
 */
public class CricketCoach implements Coach, DisposableBean{

	private FortuneService myFortuneService;
	private String team;
	private String emailId;


	public CricketCoach(FortuneService myFortuneService) {
		System.out.println("CricketCoach::CricketCoach constructor - arg1 myFortuneService");
		this.myFortuneService = myFortuneService;
	}

	public CricketCoach() {
		System.out.println("CricketCoach::CricketCoach constructor - noarg");
	}


	@Override
	public String toString() {
		return "CricketCoach [myFortuneService=" + myFortuneService + ", team=" + team + ", emailId=" + emailId + "]";
	}

	public void setMyFortuneService(FortuneService myFortuneService) {
		System.out.println("CricketCoach:setMyFortuneService - " + myFortuneService);
		this.myFortuneService = myFortuneService;
	}

	public String getTeam() {
		return team;
	}


	public void setTeam(String team) {
		System.out.println("CricketCoach::setTeam");
		this.team = team;
	}

	public String getEmailId() {
		return emailId;
	}


	public void setEmailId(String emailId) {
		System.out.println("CricketCoach::setEmailId");
		this.emailId = emailId;
	}


	@Override
	public String getSuggestion( ) {
		return "Play 15mins in batting cage";
	}

	@Override
	public String getFortune() {
		return "Cricket Coach: Team: " + this.getTeam() + ", address: " + myFortuneService + ", " + myFortuneService.getFortune();
	}

	@Override
	public String getFortuneAddress() {
		return myFortuneService.toString();
	}

	public void Init( ) {
		System.out.println("CricketCoach Bean - Init Container: " + this);
	}

	public void Destroy( ) {
		System.out.println("CricketCoach Bean - Destroy Container");
	}

	public void destroy() {
		System.out.println("CricketCoach Bean - Destroy from Disposible Implementation: " + this);
	}
}
