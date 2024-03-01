package org.baeldung;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
    	CoreApp app = new CoreApp();
    	assertTrue(app.checkStringv11(""));
    	assertTrue(app.checkStringv8(""));
    }
}
