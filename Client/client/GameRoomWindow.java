package client;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.LobbyViewWindow.LobbyPanel;
import server.Lobby;
import server.LobbyManager;
import server.Message;
import server.Type;

import javax.imageio.ImageIO;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
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
	
	private JFrame Window = new JFrame("Game Room Window");
	private static GamePanel gamePanel = null;
	
	public class Window extends JFrame {
		Window() {
	        this.setLayout(new GridLayout(0, 1, 10, 10));
		}
	}
	
	public GameRoomWindow(Client client, Socket sock, ObjectOutputStream output, ObjectInputStream input) {
		this.client = client;
		socket = sock;
		os = output;
		ois = input;
		
		gamePanel = new GamePanel(Window);
		
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
                Window.setSize(900,  700);
                Window.setLocation(960, 540);
                Window.setDefaultLookAndFeelDecorated(true);
                Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Window.add(lobbyPanel);
            }
        });
	}
	
	public class GamePanel extends JPanel {
		private JButton HitButton = new JButton("Hit");
		private JButton StayButton = new JButton("Stay");
		private JButton ExitButton = new JButton("Exit");
		private JTextField BetField = new JTextField(10);
    	
    	GamePanel(JFrame frame) {
            this.setLayout(null);
            
            HitButton.setBounds(190,630,75,25);
            StayButton.setBounds(10, 600, 100, 25);
            BetField.setBounds(10,500,100,25);
    		ExitButton.setBounds(10, 630, 75, 25);

    		this.add(HitButton);
    		this.add(StayButton);
    		this.add(ExitButton);
		
    		HitButton.addActionListener((ActionListener) new ActionListener() {
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
    	
    	private String updateLobbyName(int indx) {
   		 Lobby selectedLobby = lobbies.get(indx);
   		 return selectedLobby.toString();
   	 	}
    }
}
