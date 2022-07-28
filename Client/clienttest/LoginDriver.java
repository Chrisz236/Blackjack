package clienttest;
/*
 * Author: 		Diego Ureno
 * File: 		LoginDriver.java
 * 
 * File Description: Driver (main) function for LoginWindow.
 * Used to test GUI display. (Based on DVDAssignment 4)
 * 
 */

import java.util.*;

//Driver class for LoginWindow()
public class LoginDriver {
	
	//Main function
	public static void main(String[] args) {
		
		//Create a new login interface
		LoginInterface loginInt;
		
		//Create a new test user
		TestUser user = new TestUser();
		
		//Enable GUI
		loginInt = new LoginWindow(user);
		loginInt.processCommands();
	}
}