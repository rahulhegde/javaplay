package testsuiterunnerjunit5;

import org.junit.Test;
import org.junit.experimental.categories.Category;

public class UserLoginTest {
	@Test
	public void Test1() {
		System.out.println("UserLoginTest - Test1");
	}
	
	@Category({
		LoginQuickTestCategory.class, 
		LoginFastTestCategory.class
		})
	@Test
	public void Test2() {
		System.out.println("UserLoginTest - Test2");
	}
	
	@Test
	public void Test3() {
		System.out.println("UserLoginTest - Test3");
	}
	
	@Category(LoginQuickTestCategory.class)
	@Test
	public void Test4() {
		System.out.println("UserLoginTest - Test4");
	}
}
