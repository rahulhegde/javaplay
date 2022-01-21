package testsuiterunner;

import org.junit.Assert;
import org.junit.Test;

public class UserViewTest {

	@Test
	public void Test1() {
		System.out.println("UserViewTest - Test1");
	}
	
	@Test
	public void Test2() {
		System.out.println("UserViewTest - Test2");
	}
	
	@Test
	public void Test3() {
		System.out.println("UserViewTest - Test3");
	}
	
	@Test
	public void Test4() {
		System.out.println("UserViewTest - Test4");
		Assert.assertTrue("test failed", true);
	}
}
