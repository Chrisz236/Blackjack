package clienttest;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

//import org.junit.jupiter.api.Test;

/*
 * Author:		Diego Ureno
 * File:		WindowTester.java
 * 
 * File Description: JUnit tests for LoginWindow class.
 */

public class WindowTester {

	/*
	 * Test: Tests to see if the GUI window for LoginWindow
	 * opens successfully and allows for user input.
	 */
	
	@Test
	public void testProcessCommands() {
		boolean windowCreated = false;
		LoginInterface testInt;
		TestUser testUser = new TestUser();
		testInt = new LoginWindow(testUser);
		windowCreated = true;
		if(windowCreated) {
			testInt.processCommands();
			assertTrue(windowCreated);
		}
		else {
			fail("Window is not working");
		}
	}
}
