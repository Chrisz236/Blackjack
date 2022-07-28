package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import server.Message;
import server.Type;

public class Client {
	public String userRole = "";
	public static boolean socketOpen = true;
	
	private static int port = 7777;
	private static InetAddress serverIP;
	private static Socket socket;
	
	
	private static OutputStream outputStream;
	private static ObjectOutputStream objectOutputStream;
	private static InputStream inputStream;
	private static ObjectInputStream objectInputStream;
	
	private static LoginWindow loginWindow;
	private static LobbyViewWindow lobbyViewWindow;
	
	public Client(String ip) {
		int port = 7777;
		InetAddress serverIP;
		try {
			serverIP = InetAddress.getByName(ip);
			socket = new Socket(serverIP, port);
			outputStream = socket.getOutputStream();
			objectOutputStream = new ObjectOutputStream(outputStream);
			inputStream = socket.getInputStream();
			objectInputStream = new ObjectInputStream(inputStream);
			
			loginWindow = new LoginWindow(socket, objectInputStream, objectOutputStream, this);
			loginWindow.processCommands();
			
	        if (socketOpen) {
	        	lobbyViewWindow = new LobbyViewWindow(socket, objectOutputStream, objectInputStream,  this);
	        }
		} catch (UnknownHostException e) {
			System.out.println("Could not get ip address");
			return;
		} catch (IOException e) {
			System.out.println("Could not create socket");
			return;
		}	
	}
}
