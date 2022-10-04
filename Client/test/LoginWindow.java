package test;
/*
 * Author: 		Diego Ureno
 * File: 		LoginWindow.java
 * 
 * File Description: GUI functionality for the login
 * window.
 */

import javax.swing.*;
import java.io.*;

public class LoginWindow implements LoginInterface {
	
	//To determine successful login
	private boolean login;
	
	//Create a test user to test GUI
	private TestUser user;
	
	//Client object
	//private Client user;
	
	//Socket Connections
	private ObjectOutputStream out;		//output
	private ObjectInputStream in;		//input
	
	//LoginWindow Constructor
	public LoginWindow(TestUser newUser) {
		user = newUser;
	}
	
	//LoginWindow using Client and Server
//	public LoginWindow(ObjectInputStream input, ObjectOutputStream output) {
//		out = output;
//		in = input;
//		processCommands();
//	}
	
	/*
	 * Runs GUI Window Interface
	 */
	public void processCommands() {
		
		//Create two text boxes
		JTextField fieldOne = new JTextField(15);
		JTextField fieldTwo = new JTextField(15);
		
		//Create a GUI Window
		JPanel loginPanel = new JPanel();
		loginPanel.add(new JLabel("Username:"));			//Username textbox
		loginPanel.add(fieldOne);
		loginPanel.add(Box.createHorizontalStrut(10));		//Space between textboxes
		loginPanel.add(new JLabel("Password:"));			//Password textbox
		loginPanel.add(fieldTwo);
		
		//Prompt user input
		int inputs = JOptionPane.showConfirmDialog(null, loginPanel,
				"Enter User Credentials", JOptionPane.OK_CANCEL_OPTION);
		
		//Print to console (test user interface)
		if (inputs == JOptionPane.OK_OPTION) {
			System.out.println("Username: " + fieldOne.getText());
			System.out.println("Password: " + fieldTwo.getText());
		}
	}
}