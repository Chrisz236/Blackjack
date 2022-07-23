package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Scrollable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import server.Message;

public class GameRoomWindow {
	private static Client client;
	private static Socket socket;
	private static ObjectOutputStream objectOutput;
	private static ObjectInputStream objectInput;
	
	private static Boolean lobbyOpened = false;
	private int ButtonCount = 1;    
	private static List<Lobby> lobbies = new ArrayList<Lobby>(); 

	private static JFrame Window = new JFrame("Game Window");
	private static LobbyPanel lobbyPanel = null;
	private static LobbyList lobbyList = null;
	private static Lobby currentLobby = null;
	private static JButton newLobbyButton = null;
	
	public class Window extends JFrame {
		Window() {
	        this.setLayout(new GridLayout(0, 1, 10, 10));
		}
	}

	
	
    public class LobbyPanel extends JPanel {
		private LobbyList lobbyList = new LobbyList();
		private JButton ChatSendButton = new JButton("Send"); //todo
		private JButton AddUserSendButton = new JButton("Add User");
		private JFrame GuiFrame = new JFrame("Chat Window");
		private JPanel jp = new JPanel();
		private JTextArea TextArea = new JTextArea(); // suggest columns & rows
		private JScrollPane ScrollTextArea = new JScrollPane(TextArea);
		private TextField textField = new TextField();
		private TextField memberTextArea = new TextField();;
    	
    	LobbyPanel(JFrame frame) {
            this.setLayout(null);
    		textField.setBounds(10, 630, 675, 25);
    		memberTextArea.setBounds(10, 10, 650, 25);
    		ChatSendButton.setBounds(695,630,75,25); 
    		AddUserSendButton.setBounds(670,10,100,25); 
    		ScrollTextArea.setBounds(10, 45, 760, 575);

    		this.add(textField);
    		this.add(memberTextArea);
    		this.add(ChatSendButton);
    		this.add(AddUserSendButton);
    		this.add(ScrollTextArea);
    		
    		TextArea.setEditable(false);
    		memberTextArea.setEditable(false);
		
    		ChatSendButton.addActionListener((ActionListener) new ActionListener() {
    			@Override
		        public void actionPerformed(ActionEvent e) {
    				System.out.println("send button pressed");
		          	if (lobbyOpened) {	
		          		String currentChatId = currentLobby.ID;
		          		
		          		if (currentChatId.equals(new String("-1"))) {} else {
		              		String text = getTextForSending();
		
		              		try {
		              			Message newMsg = new Message();
		              			newMsg.setType("text message");
		              			newMsg.appendToData(currentChatId);
		              			newMsg.appendToData(text);
		  						objectOutput.writeObject(newMsg);
		  						
		              		} catch (IOException e1) {
		  						// TODO Auto-generated catch block
		  						e1.printStackTrace();
		  					}
		          		}
		          	}
    			}
    		});

		
			AddUserSendButton.addActionListener((ActionListener) new ActionListener() {
	          @Override
	          public void actionPerformed(ActionEvent e) {
	          	if (lobbyOpened) {
	
	          		String currentChatId = currentLobby.ID;
	          		if (currentChatId.equals(new String("-1"))) {} else {
	              		String Name = JOptionPane.showInputDialog("Enter User To Add");
	              		if (Name == null) { return; }
	              		try {
	              			Message newMsg = new Message();
	              			newMsg.setType("new chat user");
	              			newMsg.appendToData(currentChatId);
	              			newMsg.appendToData(Name);
	  						objectOutput.writeObject(newMsg);
	  					} catch (IOException e1) {
	  						// TODO Auto-generated catch block
	  						e1.printStackTrace();
	  					}
	          		}
	          	}
	          }
	      });

    	}
		public String getTextForSending() {
			String text = textField.getText();
			textField.setText("");
			return text;
		}
    	@Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }
    	
    	public void SetChatText(String string) {
			TextArea.setText(string);
    	}
    	
    	public void SetMembersText(String string) {
			memberTextArea.setText(string);
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
    

    public GameRoomWindow(Socket sock, ObjectOutputStream output, ObjectInputStream input, Client Client) {
		socket = sock;
		objectOutput = output;
		objectInput = input;
		client =  Client;

        lobbyPanel = new LobbyPanel(Window);
        lobbyList = new LobbyList();
        newLobbyButton = lobbyList.NewButton("New Lobby");
        
        
        newLobbyButton.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
          	
	          	String Name = JOptionPane.showInputDialog("Enter Name Of Lobby");
				if (Name == null) { return; }
				String User = JOptionPane.showInputDialog("Enter Your Username");
				if (User == null) { return; }
				try {
					objectOutput.writeObject(new Message("New Lobby", Name+'\n'+User));
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


//	@Override
//	public void processCommands() {
//		// TODO Auto-generated method stub
//		
//	}
	
//	public static void UpdateChatText() {
//		if (CurrentConvo != null) {
//			chatPanel.SetChatText(CurrentConvo.chatsToText());
//			chatPanel.SetMembersText(CurrentConvo.membersToText());
//		}
//	}
	
	public void NewLobbyMessage(Message Message) {
				
		Lobby newLobby = new Lobby(Message.getData());

		for (int i = 0; i < lobbies.size(); i++) {
			if (lobbies.get(i).ID.equals(newLobby.ID) ) {
				lobbies.get(i).ID = newLobby.ID;
				lobbies.get(i).Name = newLobby.Name;
				lobbies.get(i).Members = newLobby.Members;
				lobbies.get(i).Chats = newLobby.Chats;
				//UpdateChatText();
				return;
			}
		}
		
	    ButtonCount = ButtonCount + 1;
	    lobbyList.setLayout(new GridLayout(ButtonCount, 0));
	    
		JButton convoButton = lobbyList.NewButton(newLobby.Name);
		lobbies.add(newLobby);

		convoButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	lobbyOpened = true;
            	currentLobby = newLobby;
            	//UpdateChatText();
//            	System.out.println(CurrentConvo.ID);
                setChatVisible(true);
                
                Message newMsg = new Message();
    			newMsg.setType("refresh");
				try {
					objectOutput.writeObject(newMsg);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

	}
	
	public static void setChatVisible(Boolean bool) {
//      ScrollTextArea.setVisible(bool);
//      textField.setVisible(bool);
//      memberTextArea.setVisible(bool);
//      ChatSendButton.setVisible(bool);
//      AddUserSendButton.setVisible(bool);
//		memberTextArea.setVisible(bool);
	}
	
	
	public void Test(String str) {
	// TODO Auto-generated method stub
	// ((ConversationList) ConversationList).NewButton(str);
	}
	
	private static void PrintMessage(Message msg) {
		System.out.println("Type: " + msg.getType());
		System.out.println("Status: " + msg.getStatus());
		System.out.println("Data: " + msg.getData());
		System.out.println("-----------------------------------------------------------");
	}
}
