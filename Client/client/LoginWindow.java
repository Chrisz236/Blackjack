package client;

/*
 * Author: 		Diego Ureno
 * File: 		LoginWindow.java
 * 
 * File Description: GUI functionality for the login
 * window.
 * 
 * Modified by Andrew Bustos
 */

import javax.swing.*;

import server.Type;

import java.io.*;
import java.net.*;

import server.Message;

public class LoginWindow implements ClientUI {
	private Client client;
	
	//To determine successful login
	private boolean login;
	
	//Socket Connections
	private Socket socket;
	private static ObjectOutputStream out;
	private static ObjectInputStream in;
	
	//LoginWindow using Client, socket and input/output streams
	public LoginWindow(Socket socket, ObjectInputStream input, ObjectOutputStream output, Client client) {
		this.socket = socket;
		out = output;
		in = input;
		this.client = client;
	}
	
	public Boolean Login(String username, String password) {
		try {
			out.writeObject(new Message(username + "," + password, Type.Login));
			Message newMsg = (Message) in.readObject();
			
			if (newMsg.getType() == Type.Succeed) {
				client.userRole = (String) newMsg.getData();
				return true;
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	
	    return false;
	}
	
	@Override
	public void processCommands() {
		while(client != null && client.socketOpen) {
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
			
			if (inputs == JOptionPane.OK_OPTION) {
				login = Login(fieldOne.getText(), fieldTwo.getText());
			}
			
			if (inputs == JOptionPane.CANCEL_OPTION) {
				client.socketOpen = false;
			}
			
			if (login) {
				break;
			}
		}
	}
}