package com.rahulhegde.maven;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

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
    }
       
       
    @Test
    public void spyAppObjectTest_WhenThenReturn( ) {

    	// for Spy Object specifically, the function code below (multiple) is executed however only
    	// the return value is mocked.
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
    
    @Test
    public void spyAppObjectTest_DoRetrunWhen( ) {
    	
    	// for Spy Object specifically, the complete function (multiple) is mocked such that no code is 
    	// executed however the return value is mocked.
    	Mockito.doReturn(10).when(appSpyTest).multiple(Mockito.anyInt(), (int)Mockito.eq(2));
    	assertEquals(appContainer.multiple(1, 2), 10);	

    	// failure cases
    	assertNotEquals(appContainer.multiple(1, 3), 10); 
    	assertNotEquals(appContainer.multiple(1, 3), 1); 
    	assertNotEquals(appContainer.multiple(1, 2), 6); 

    	// spy unchanged addition function
    	assertEquals(appContainer.addition(10, 2), 12);    	    	
    	assertNotEquals(appContainer.addition(10, 12), 12);	
    }
    
    @Test
    public void 
}
