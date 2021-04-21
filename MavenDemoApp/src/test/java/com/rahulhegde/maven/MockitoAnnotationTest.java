package com.rahulhegde.maven;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.exceptions.base.MockitoInitializationException;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Unit test for simple App.
 */


// Enable the use of Mockito Annotation using either 
// - Runwith OR openMocks

//@RunWith(MockitoJUnitRunner.class)
public class MockitoAnnotationTest 
{	
	@Spy
	App appSpyTest;

	@InjectMocks
	AppContainer appContainer;
	
	@Before
	public void init() {
		// activate annotation usage through openMocks OR RunWith
	    MockitoAnnotations.openMocks(this);
	}
	
    @Test
    public void mockAppObjectTest() {
       	App appTest = Mockito.mock(App.class);
    
       	Mockito.when(appTest.multiple(2, 5)).thenReturn(10);
    	
    	assertEquals("Regular Multiplication", appTest.multiple(2, 5), 10); 
    	
    	// note: only 2,5 is mocked to return 10
    	assertEquals(" Multiplication", appTest.multiple(2, 1), 0); 
    }
       
       
    @Test
    public void spyAppObjectTest( ) {
    	Mockito.when(appSpyTest.multiple(Mockito.anyInt(), (int)Mockito.eq(2))).thenReturn(10);
    	
    	assertEquals(appContainer.multiple(1, 2), 10);	
    	// failure cases
    	assertNotEquals(appContainer.multiple(1, 3), 10); 
    	assertNotEquals(appContainer.multiple(1, 3), 1); 
    	assertNotEquals(appContainer.multiple(1, 2), 6); 

    	// spy unchanged addition function
    	assertEquals(appContainer.addition(10, 2), 12);    	    	
    	assertNotEquals(appContainer.addition(10, 12), 12);	
    }    
}
