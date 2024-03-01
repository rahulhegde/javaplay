package org.baeldung;

/**
 * Hello world!
 *
 */
public class WebApp {
	
	public boolean checkString(String s) {
		CoreApp p = new CoreApp();
		p.checkStringv12(s);
		return s.isBlank();
	}	
}
