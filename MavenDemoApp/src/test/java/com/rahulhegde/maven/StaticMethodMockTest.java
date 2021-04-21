package com.rahulhegde.maven;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import junit.framework.Assert;

/**
 * Unit test for simple App.
 */

/** Notes for static method mocking -  
 * 
 * Reference: https://frontbackend.com/java/how-to-mock-static-methods-with-mockito
 * - note that mocked static method will be only visible in the try-with-resources block, 
 * so different test cases should be tested in different try blocks,
 * - static methods could be mocked only using an inline way,
 * - If you are using mockito-core you will need to replace it with mockito-inline dependency (>=3.6.28)
 *
 */


public class StaticMethodMockTest 
{
	App appTest;
	
	@Before
	public void setup() {
		appTest = new App();
	}
	
    @Test
    public void testMultiply() {
        assertEquals("Regular Multiplication", appTest.multiple(2, 5), 10);          
    }
    

    @Test
    public void mockSystemStaticMethodNoArgs_LocalDate() {
        LocalDate localDate = LocalDate.now();
        System.out.println("current localdate now " + localDate);        
        localDate = localDate.plusDays(5);
        LocalDate localDate2 = localDate.plusDays(100);
    	System.out.println("localdate::parse 100days " + localDate2);
        
        try (MockedStatic<LocalDate> mockedStaticLocalDate = Mockito.mockStatic(LocalDate.class)) {
        	mockedStaticLocalDate.when(LocalDate::now).thenReturn(localDate);
        	mockedStaticLocalDate.when(() -> LocalDate.parse("2012-20-11")).thenReturn(localDate2);

        	System.out.println("mocked localdate::now to add 5 days: " + LocalDate.now()); 
        	System.out.println("mocked localdate::now to add 5 days: " + LocalDate.now()); 
        	System.out.println("mocked localdate::now to add 5 days: " + LocalDate.now());
           	
        	System.out.println("mocked localdate::parse 100days " + LocalDate.parse("2012-20-11"));
        	assertEquals(LocalDate.now(), localDate);
        }
    }

    // showcase multiple return based on the argument match
    @Test
    public void mockStaticMethodArgsN_LocalDate() {
    	LocalDate localDate = LocalDate.now();
    	ZoneId zoneId = ZoneId.of("Asia/Kolkata");
    	System.out.println("localdate::parse 100days " + localDate);
       
    	try (MockedStatic<LocalDate> mockStaticMethod = Mockito.mockStatic(LocalDate.class)) {
        	mockStaticMethod
        	.when(() -> LocalDate.now(zoneId))
        	.thenReturn(localDate)
        	.thenReturn(null);
        	
        	System.out.println("does the localdate + 5day returns null? " + LocalDate.now(ZoneId.systemDefault()));	
        	System.out.println("does the localdate + 5day returns null? " + LocalDate.now(zoneId));	

    	}
    	System.out.println("Zone Id" + ZoneId.of("Asia/Kolkata"));
    }
    
    
    @Test
    public void testMultiplyWithZero() {    	   	
        assertEquals("Regular Multiplication", appTest.multiple(0, 5), 0);          
    }
    
    @Test
    public void mockAppObjectInstanceMethod() {
    	App appMock = Mockito.mock(App.class);
    	Mockito.when(appMock.getProperty()).thenReturn(100);
    
    	assertEquals(appMock.getProperty(), 100);    	
    	assertNotEquals(appMock.getProperty(), 20);   	

    }
}
