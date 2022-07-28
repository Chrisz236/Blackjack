package client;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import server.Player;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameRoomWindow {
	private Client client;
	private ObjectOutputStream os;
	private ObjectInputStream ois;
	private BlackJack game = null;
	private boolean gameStarted = false;
	public Map<String, Player> playersInfo = new HashMap<>();
	
	private JFrame Window = new JFrame("Game Room Window");
	private static GamePanel gamePanel = null;
	
	public class Window extends JFrame {
		Window() {
	        this.setLayout(new GridLayout(0, 1, 10, 10));
		}
	}
	
	public GameRoomWindow(Client client, ObjectOutputStream output, ObjectInputStream input) {
		this.client = client;
		os = output;
		ois = input;
		gamePanel = new GamePanel(Window);
		try {
			os.writeObject(new Message(Type.ViewAllPlayerInfo));
			Message msg = (Message) ois.readObject();
			playersInfo = (HashMap<String, Player>) msg.getData();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Message msg = null;
//		try {
//			msg = (Message) ois.readObject();
//			if (msg.getType() == Type.StartGame)
//				game.start();
//  		} catch (IOException | ClassNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
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
                Window.setSize(600,  700);
                Window.setLocation(500, 100);
                Window.setDefaultLookAndFeelDecorated(true);
                Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Window.add(gamePanel);
            }
        });
	}
	
	public class GamePanel extends JPanel {
		private JButton HitButton = new JButton("Hit");
		private JButton StayButton = new JButton("Stay");
		private JButton ExitButton = new JButton("Exit");
		private JButton StartButton = new JButton("Start");
		private JTextField BetField = new JTextField();
		private JLabel label = new JLabel("Bet:");
    	
    	GamePanel(JFrame frame) {
            this.setLayout(null);
            
            HitButton.setBounds(190,600,75,25);
            StayButton.setBounds(270, 600, 75, 25);
            BetField.setBounds(50,500,20,25);
    		ExitButton.setBounds(10, 600, 75, 25);
    		StartButton.setBounds(10, 550, 75, 25);
    		label.setLocation(45, 500);
    		
    		this.add(HitButton);
    		this.add(StayButton);
    		this.add(ExitButton);
		
    		HitButton.addActionListener((ActionListener) new ActionListener() {
    			@Override
		        public void actionPerformed(ActionEvent e) {
    				try {
    					os.writeObject(new Message(Type.ViewPlayerInfo));
    	    			Message msg = (Message) ois.readObject();
    	    			String data = (String) msg.getData();
    	    			String username = data.split(",")[0];
    					os.writeObject(new Message(username, Type.Hit));
              		} catch (IOException | ClassNotFoundException e1) {
  						// TODO Auto-generated catch block
  						e1.printStackTrace();
  					}
    			}
	    	});
    		
    		ExitButton.addActionListener((ActionListener) new ActionListener() {
    			@Override
    	        public void actionPerformed(ActionEvent e) {
              		try {
              			Message newMsg = new Message(Type.ExitLobby);
    					os.writeObject(newMsg);
    					frame.setVisible(false);
    					frame.dispose();
              		} catch (IOException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
    			}
        	});
    		
    		if (!client.userRole.equals("DEALER")) {
    			this.add(label);
    			this.add(BetField);
    			BetField.addActionListener((ActionListener) new ActionListener() {
        			@Override
        			public void actionPerformed(ActionEvent e) {
        				if (setBet(BetField.getText())) {
        					return;
        				}
        			}
        				
        		});
            }
    		else {
    			this.add(StartButton);
    			StartButton.addActionListener((ActionListener) new ActionListener() {
        			@Override
        			public void actionPerformed(ActionEvent e) {
        				if (playersInfo.size() >= 2) {
        					Player dealer = null;
        					ArrayList<Player> players = new ArrayList<>();
        					for (var player : playersInfo.entrySet()) {
        		                if(player.getValue().isDealer) {
        		                    dealer = (Player) player;
        		                } else {
        		                     players.add((Player) player);
        		                }
        		            }
        					game = new BlackJack(dealer, players, os, ois);
        					try {
            					os.writeObject(new Message(Type.StartGame));
                      		} catch (IOException e1) {
          						// TODO Auto-generated catch block
          						e1.printStackTrace();
          					}
        				}
        				else {
        					JOptionPane.showMessageDialog(frame, "There must be at least one more player to start");
        				}
        			}
        				
        		});
    		}
	    }
    	
    	@Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }
    	
    	private boolean setBet(String amount) {
    		int bet = Integer.parseInt(amount);
    		
    		if (bet <= 0) {
    			JOptionPane.showMessageDialog(Window, "Please enter an amount > 0");
    			return false;
    		}
    		try {
    			os.writeObject(new Message(Type.ViewPlayerInfo));
    			Message msg = (Message) ois.readObject();
    			String data = (String) msg.getData();
    			String username = data.split(",")[0];
				os.writeObject(new Message(username + ", " + amount, Type.Bet));
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return true;
    	}
    }
}
