package spring.rahul.coach;

import org.springframework.beans.factory.DisposableBean;

public class HappyFortuneService implements FortuneService, DisposableBean {

	public HappyFortuneService() {
		System.out.println("HappyFortuneService: " + this);
	}
	
	@Override
	public String getFortune() {
		return "Today is your lucky day";
	}
	
	public void HappyFortuneInit( ) {
		System.out.println("HappyFortuneService Init: " + this);
	}

	public void HappyFortuneDestroy( ) {
		System.out.println("HappyFortuneService Destroy: " + this);
	}
	
	public void destroy() {
		System.out.println("HappyFortuneService Bean - Destroy from Disposible Implementation: " + this);
	}

}
