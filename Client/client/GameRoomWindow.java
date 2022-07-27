package client;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import server.Lobby;
import server.Message;
import server.Type;

import javax.imageio.ImageIO;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameRoomWindow {
	private Client client;
	private Socket socket;
	private ObjectOutputStream os;
	private ObjectInputStream ois;
	
	public GameRoomWindow(Client client, Socket sock, ObjectOutputStream output, ObjectInputStream input) {
		this.client = client;
		socket = sock;
		os = output;
		ois = input;
		
		
	}
	
	public class LobbyPanel extends JPanel {
		private JButton JoinLobbyButton = new JButton("Join");
		private JButton ReloadButton = new JButton("Reload");
		private JButton ExitButton = new JButton("Exit");
		private JButton NewLobbyButton = new JButton("New Lobby");
		private JTextArea clientInfo = new JTextArea("");
		private String lobbyName = "";
    	
    	LobbyPanel(JFrame frame) {
            this.setLayout(null);
            clientInfo.setBounds(10, 10, 160, 100);
    		JoinLobbyButton.setBounds(190,630,75,25);
    		NewLobbyButton.setBounds(10, 600, 100, 25);
    		ReloadButton.setBounds(10,500,100,25);
    		ExitButton.setBounds(10, 630, 75, 25);

    		this.add(clientInfo);
    		this.add(JoinLobbyButton);
    		this.add(ExitButton);
    		
    		clientInfo.setEditable(false);
    		clientInfo.setLineWrap(true);
    		setClientInfo("");
		
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
}
