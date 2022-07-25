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

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Scrollable;
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
	private static LobbyList lobbyList = null;
	//private static Lobby currentLobby = null;
	private static JButton newLobbyButton = null;
	private static LobbyManager lobbyManager = new LobbyManager();
	
	public class Window extends JFrame {
		Window() {
	        this.setLayout(new GridLayout(0, 1, 10, 10));
		}
	}
	
    public class LobbyPanel extends JPanel {
		private JButton JoinLobbyButton = new JButton("Join");
		private JButton ReloadButton = new JButton("Reload");
		private JButton ExitButton = new JButton("Exit");
		private JScrollPane ScrollTextArea = new JScrollPane(lobbiesJList);
		private TextArea clientInfo = new TextArea();
		private String lobbyName = "";
    	
    	LobbyPanel(JFrame frame) {
            this.setLayout(null);
            clientInfo.setBounds(10, 10, 650, 25);
    		JoinLobbyButton.setBounds(695,630,75,25); 
    		ReloadButton.setBounds(670,10,100,25);
    		ExitButton.setBounds(500, 20, 75, 25);
    		ScrollTextArea.setBounds(10, 45, 760, 575);
    		
    		lobbiesJList.setListData(lobbies.toArray());

    		this.add(clientInfo);
    		this.add(JoinLobbyButton);
    		this.add(ReloadButton);
    		this.add(ExitButton);
    		this.add(ScrollTextArea);
    		
    		clientInfo.setEditable(false);
    		
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
              			Message newMsg = new Message();
              			newMsg.setType(Type.Exit);
  						objectOutput.writeObject(newMsg);
  						System.exit(0);
              		} catch (IOException e1) {
  						// TODO Auto-generated catch block
  						e1.printStackTrace();
  					}
    			}
	    	});
    		
    		ReloadButton.addActionListener((ActionListener) new ActionListener() {
    			@Override
		        public void actionPerformed(ActionEvent e) {
	          		try {
              			Message newMsg = new Message();
              			newMsg.setType(Type.ReloadBalance);
  						objectOutput.writeObject(newMsg);
              		} catch (IOException e1) {
  						// TODO Auto-generated catch block
  						e1.printStackTrace();
  					}
    			}
	    	});
    		frame.pack();
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
   		 return selectedLobby.getLobbyName();
   	 	}
    }
    
    
    public class LobbyList extends JPanel implements Scrollable {
    	
    	public static JButton NewButton(String str) {
            GridBagConstraints GridBagConstraints = new GridBagConstraints();
            GridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
            GridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            JButton button = new JButton(str);
            lobbyList.add(button);
            button.setPreferredSize(new Dimension(0, 20));
            Window.setSize(899,  699);
            Window.setSize(900,  700);
            return button;
    	}
    	
        public LobbyList() { 
            setLayout(new GridLayout(ButtonCount, 0));
	    }

        @Override public Dimension getPreferredScrollableViewportSize() { return new Dimension(100, 100); }
        @Override public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) { return 64; }
        @Override public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) { return 64; }
        @Override public boolean getScrollableTracksViewportWidth() { return true; }
        @Override public boolean getScrollableTracksViewportHeight() { return false; }

    }
    

    public LobbyViewWindow(Socket sock, ObjectOutputStream output, ObjectInputStream input, Client Client) {
		socket = sock;
		objectOutput = output;
		objectInput = input;
		client =  Client;
		lobbies = lobbyManager.lobbies;

        lobbyPanel = new LobbyPanel(Window);
        lobbyList = new LobbyList();
        newLobbyButton = lobbyList.NewButton("Create Lobby");
        
        
        newLobbyButton.addActionListener((ActionListener) new ActionListener() {
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
	          }
		});
        
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
                Window.setLocation(100, 150);
                Window.setDefaultLookAndFeelDecorated(true);
                Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Window.add(lobbyPanel);
                Window.add(new JScrollPane(lobbyList), BorderLayout.LINE_START);
            }
        });
    }
    
	 private void resetLobbiesJList() {
		 lobbiesJList.setListData(lobbies.toArray());
	 }
	
	public void NewLobbyMessage(Message msg) {
				
		Lobby newLobby = new Lobby(msg.getData());

//		for (int i = 0; i < lobbies.size(); i++) {
//			if (lobbies.get(i).getLobbyName().equals(newLobby.getLobbyName()) ) {
//				return;
//			}
//		}
		
	    ButtonCount = ButtonCount + 1;
	    lobbyList.setLayout(new GridLayout(ButtonCount, 0));
	    
		//JButton lobbyButton = lobbyList.NewButton(newLobby.getLobbyName());
		lobbies.add(newLobby);
		resetLobbiesJList();

//		lobbyButton.addActionListener((ActionListener) new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            	lobbyOpened = true;
//            	currentLobby = newLobby;
//                
//                Message newMsg = new Message();
//    			//newMsg.setType(Type.Refresh); TODO add type of refresh for messages
//				try {
//					objectOutput.writeObject(newMsg);
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//            }
//        });
	}
	
	//public static void setChatVisible(Boolean bool) {
//      ScrollTextArea.setVisible(bool);
//      textField.setVisible(bool);
//      memberTextArea.setVisible(bool);
//      ChatSendButton.setVisible(bool);
//      AddUserSendButton.setVisible(bool);
//		memberTextArea.setVisible(bool);
	//}
}
