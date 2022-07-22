/*
 * Author: 		Diego Ureno
 * File: 		LoginWindow.java
 * 
 * File Description: GUI functionality for the login
 * window.
 */

//To allow GUI
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class LoginWindow implements Client {
	
	//To determine successful login
	private boolean login;
	
	//Client object
	private Client user;
	
//	//Socket Connections(?)
//	ObjectOutputStream objectOutput = new ObjectOutputStream();
//	ObjectInputSream objectInput = new ObjectInputStream();
	
	//LoginWindow Constructor
	public LoginWindow(Client c) {
		
		user = c;
	}
	
	public void processCommands() {
		
		JTextField fieldOne = new JTextField(5);
		JTextField fieldTwo = new JTextField(5);
		
		JPanel loginPanel = new JPanel();
		loginPanel.add(new JLabel("Username:"));
		loginPanel.add(fieldOne);
		loginPanel.add(Box.createHorizontalStrut(15));
		loginPanel.add(new JLabel("Password:"));
		loginPanel.add(fieldTwo);
		
		int inputs = JOptionPane.showConfirmDialog(null, loginPanel,
				"Enter User Credentials", JOptionPane.OK_CANCEL_OPTION);
		if (inputs == JOptionPane.OK_OPTION) {
			System.out.println("Username: " + fieldOne.getText());
			System.out.println("Password: " + fieldTwo.getText());
		}
		
		
		
//		String[] inputs = {"Username", "Password"};
		
//		do {
//			JOptionPane.showInputDialog(null, "Username")
//		}
	}
}