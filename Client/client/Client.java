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
	public static String userRole = "";
	public static boolean socketOpen = true;
	
	private static int port = 7777;
	private static InetAddress serverIP;
	private static Socket socket;
	
	
	private static OutputStream outputStream;
	private static ObjectOutputStream objectOutputStream;
	private static InputStream inputStream;
	private static ObjectInputStream objectInputStream;
	
	private static LoginWindow loginWindow;
	private static GameRoomWindow gameRoomWindow;
	
	public Client(String ip) throws ClassNotFoundException, IOException {
		int port = 7777;
		InetAddress serverIP;
		try {
			serverIP = InetAddress.getByName(ip);
			socket = new Socket(serverIP, port);
			outputStream = socket.getOutputStream();
			objectOutputStream = new ObjectOutputStream(outputStream);
			inputStream = socket.getInputStream();
			objectInputStream = new ObjectInputStream(inputStream);
			
			loginWindow = new LoginWindow(socket, objectOutputStream, objectInputStream, this);
			//loginWindow.processCommands();
	         
	         
	        if (socketOpen) {
	        	gameRoomWindow = new GameRoomWindow(socket, objectOutputStream, objectInputStream,  this);
				//chatWindow.processCommands();
	        }
	        
	        try {
				while (socketOpen) {
					Message NewMessage = (Message) objectInputStream.readObject();
					PrintMessage(NewMessage);
					if (NewMessage.getType() == Type.Logout) {
						if (NewMessage.getStatus().equals(new String("success"))) {
							System.out.println("Closing");
							socketOpen = false;
							break;
						}
					} else if (NewMessage.getType().equals(new String("data"))) {
						gameRoomWindow.NewConversationMessage(NewMessage);
					} //else if (NewMessage.getType().equals(new String("IT command return info"))) {
						//itWindow.SetGuiText(NewMessage.getData());
					//}
				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			System.out.println("Could not get ip address");
			return;
		} catch (IOException e) {
			System.out.println("Could not create socket");
			return;
		}	
	}
	
//	public void run() {
//		
//	}
}
