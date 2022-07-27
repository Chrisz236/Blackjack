package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import server.Message;
import server.Type;
import server.Lobby;
import server.LobbyManager;

public class LobbyViewWindow {
	private static Client client;
	private static Socket socket;
	private static ObjectOutputStream objectOutput;
	private static ObjectInputStream objectInput;
	
	private static Boolean lobbyOpened = false;
	private int ButtonCount = 1;    
	private static List<Lobby> lobbies = new ArrayList<Lobby>(); 

	private static JFrame Window = new JFrame("Lobby View Window");
	private JList lobbiesJList = new JList();
	private static LobbyPanel lobbyPanel = null;
	//private static Lobby currentLobby = null;
	private static LobbyManager lobbyManager;
	private static GameRoomWindow gameRoom = null;
	
	public class Window extends JFrame {
		Window() {
	        this.setLayout(new GridLayout(0, 1, 10, 10));
		}
	}
	
    public class LobbyPanel extends JPanel {
		private JButton JoinLobbyButton = new JButton("Join");
		private JButton ReloadButton = new JButton("Reload");
		private JButton ExitButton = new JButton("Exit");
		private JButton NewLobbyButton = new JButton("New Lobby");
		private JScrollPane ScrollTextArea = new JScrollPane(lobbiesJList);
		private JTextArea clientInfo = new JTextArea("");
		private String lobbyName = "";
    	
    	LobbyPanel(JFrame frame) {
            this.setLayout(null);
            clientInfo.setBounds(10, 10, 160, 100);
    		JoinLobbyButton.setBounds(190,630,75,25);
    		NewLobbyButton.setBounds(10, 600, 100, 25);
    		ReloadButton.setBounds(10,500,100,25);
    		ExitButton.setBounds(10, 630, 75, 25);
    		ScrollTextArea.setBounds(180, 45, 200, 575);

    		this.add(clientInfo);
    		this.add(JoinLobbyButton);
    		this.add(ExitButton);
    		this.add(ScrollTextArea);
    		
    		clientInfo.setEditable(false);
    		clientInfo.setLineWrap(true);
    		setClientInfo("");
    		resetLobbiesJList();
    		lobbiesJList.addListSelectionListener(new ListSelectionListener() { // whenever a list item is selected
   			 @Override
   			 public void valueChanged(ListSelectionEvent arg) {
   				 if (!arg.getValueIsAdjusting()) { // if the list isn't currently being modified
   					 try {
   						 //updateDVDInfo(lobbiesJList.getSelectedIndex()); // update the UI to show the selected dvd
   						 lobbyName = updateLobbyName(lobbiesJList.getSelectedIndex());
   					 } 
   					 catch (Exception e) { // update the UI to select and show the first dvd
   						//lobbiesJList.setSelectedIndex(0);
   					 }
   				 }
   			 }
    		});
		
    		JoinLobbyButton.addActionListener((ActionListener) new ActionListener() {
    			@Override
		        public void actionPerformed(ActionEvent e) {
		          	if (lobbyOpened) {	
		          		return;
		          	}
		          	else {
		          		try {
	              			Message newMsg = new Message(lobbyName, Type.JoinLobby);
	  						objectOutput.writeObject(newMsg);
	  						
	  						gameRoom = new GameRoomWindow(client, socket, objectOutput, objectInput);
	              		} catch (IOException e1) {
	  						// TODO Auto-generated catch block
	  						e1.printStackTrace();
	  					}
		          	}
    			}
	    	});
    		
    		ExitButton.addActionListener((ActionListener) new ActionListener() {
    			@Override
    	        public void actionPerformed(ActionEvent e) {
              		try {
              			Message newMsg = new Message(Type.Logout);
    						objectOutput.writeObject(newMsg);
    						System.exit(0);
              		} catch (IOException e1) {
    						// TODO Auto-generated catch block
    						e1.printStackTrace();
    					}
    			}
        	});
    		
    		if (client.userRole.equals("DEALER")) {
    			this.add(NewLobbyButton);
    			NewLobbyButton.addActionListener((ActionListener) new ActionListener() {
        			@Override
        			public void actionPerformed(ActionEvent e) {
                  	
        	          	String name = JOptionPane.showInputDialog("Enter Name Of Lobby");
        				if (name == null) { return; }
        				try {
        					objectOutput.writeObject(new Message(name, Type.CreateLobby));
        				} catch (IOException e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        				}
        				resetLobbiesJList();
        	          }
        		});
            }
            else {
            	this.add(ReloadButton);
        		ReloadButton.addActionListener((ActionListener) new ActionListener() {
        			@Override
    		        public void actionPerformed(ActionEvent e) {
    	          		try {
    	          			String input = JOptionPane.showInputDialog("Enter how much you want to load in your current balance");
                  			Message newMsg = new Message(input, Type.ReloadBalance);
      						objectOutput.writeObject(newMsg);
      						setClientInfo(input);
                  		} catch (IOException e1) {
      						// TODO Auto-generated catch block
      						e1.printStackTrace();
      					}
        			}
    	    	});
            }
	    }
    	
    	@Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }
    	
    	public void setClientInfo(String string) {
    		clientInfo.setText(string);
    	}
    	
    	private String updateLobbyName(int indx) {
   		 Lobby selectedLobby = lobbyManager.lobbies.get(indx);
   		 return selectedLobby.toString();
   	 	}
    }

    public LobbyViewWindow(Socket sock, ObjectOutputStream output, ObjectInputStream input, Client Client) {
		socket = sock;
		objectOutput = output;
		objectInput = input;
		client =  Client;
		
        lobbyPanel = new LobbyPanel(Window);
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }
                
                Window.pack();
                Window.setLocationRelativeTo(null);
                Window.setVisible(true);
                Window.setResizable(false);
                Window.setSize(400,  700);
                Window.setLocation(100, 150);
                Window.setDefaultLookAndFeelDecorated(true);
                Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Window.add(lobbyPanel);
            }
        });
    }
    
	 private void resetLobbiesJList() {
		 Message msg = null;
		 try {
			 objectOutput.writeObject(new Message(Type.GetLobbyManagerInfo));
			 msg = (Message) objectInput.readObject();
		 } catch (IOException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			 e1.printStackTrace();
		 }
		 Scanner scan = new Scanner((String) msg.getData());
		 scan.useDelimiter(",");
		 scan.next();
		 scan.next();
		 while (scan.hasNext()) {
			lobbies.add(new Lobby(scan.next()));
		 }
		 scan.close();
		 lobbiesJList.setListData(lobbies.toArray());
	 }
	
	public void NewLobbyMessage(Message msg) {
				
		Lobby newLobby = new Lobby((String)msg.getData());
		
//	    ButtonCount = ButtonCount + 1;
//	    buttonList.setLayout(new GridLayout(ButtonCount, 0));
	    
		lobbies.add(newLobby);
		resetLobbiesJList();
	}
}
