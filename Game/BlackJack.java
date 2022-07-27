/*
  Author: Steven Tran
  File:   Server.java
  Date:   July 21, 2022
  Ver:    1.1

  Description:
          Game class for game rule
 */
import java.util.*;
import java.awt.Window.Type;
import java.io.*;
import server.*;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import server.Player;

public class BlackJack {
	private int user;
	private static ArrayList<Player> players;
	private static Player dealer;
	private Deck deck;
	private Scanner scan;
	private int index = 0;
	private static ObjectOutputStream oos;
	private static ObjectInputStream ois;
	private int amount = 0;
	private static int totalBet = 0;
	private String sourceName = "userFile.txt";
	private Deck[] deckArray = new Deck[4];
	/*
	 *
	 * default constructor for BlackJack game
	 * initialize all variables
	 */
	
	//creates a local copy 
	public BlackJack(Player dealer, ArrayList<Player> players, ObjectOutputStream oos, ObjectInputStream ois) {
		this.dealer = dealer;
		this.players = players;
		this.oos = oos;
		this.ois = ois;
	}
	/*
	 * draw a card from main deck
	 */
	public void hit(int turn, Deck tmpdeck, boolean isDealer) {
		if (isDealer) {
			
			Deck playingDeck = new Deck();
			String card = tmpdeck.draw(playingDeck);
			
			try {
				oos.writeObject(new Message(dealer.username + "," + card, server.Type.Hit));// sends "request" to the server 
				
			} 
			catch (IOException  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}

			else {
				Deck playingDeck = new Deck();
				String card2 = tmpdeck.draw(playingDeck);
				try {
					oos.writeObject(new Message(players.get(turn).username + "," + card2, server.Type.Hit));// sends "request" to the server 
			
				} 
				catch (IOException e) {
			
			// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}

	public void stay(Player player) {
		//everyone will eventually stay 
		// 
		try {
			oos.writeObject(new Message(player.username + ",", server.Type.Stay));// sends "request" to the server 
			//Message playerInfo = (Message) ois.readObject();
			//if (playerInfo.getType() == server.Type.Stay){ 	
			//}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Stay is called");
	}

	public void start() {
	/*
	 * Before: the bets should be taken set. 
	 * 
	 * Start should have every players hand and dealers hand dealt
	 * 
	 * 
	 */

		Deck playingDeck = new Deck();
		//id++;
		playingDeck.createFullDeck();
		playingDeck.shuffle();
		//Create a deck for a player
//		Deck playerDeck = new Deck();
		//id++;
		Deck dealerDeck = new Deck();
		dealerDeck.shuffle();
		//Start dealing
		//player gets cards
		//dealer gets cards
		hit(0, dealerDeck , true);
		hit(0, dealerDeck , true);
		//dealerDeck.draw(playingDeck);
		//dealerDeck.draw(playingDeck);
		System.out.println("\n\n" + dealer + "'s Hand: " + dealerDeck.getCard(0).getValue());
		System.out.println(dealerDeck.getCard(0).toString() + "\n[Hidden]");
		//Deck[] deckArray = new Deck[4];
		playingDeck.shuffle();
		for (int index = 0; index < deckArray.length; index++) {
			deckArray[index] = new Deck();
			
			hit(index, deck , false);
			hit(index, deck , false);
			//deckArray[index].draw(playingDeck);
			//deckArray[index].draw(playingDeck);
			System.out.print("\nPlayer " + index + "'s hand: "  + deckArray[index].cardsValue());
			System.out.println(deckArray[index] + "\n");
			if(deckArray[index].cardsValue() == 21 ) {
				System.out.print("Player" + index +  " Wins! \n");
			}
		}
			while (dealerDeck.cardsValue() == 21) {
				if (dealerDeck.cardsValue() == deckArray[index].cardsValue()) {
				System.out.println("You Push!");
				}
				else System.out.println(dealer +" Wins!");
				return;
			}
		
		
		while(index < 4) {
			
				JOptionPane.showMessageDialog(null, "It is" + players.get(index).username + "turn!");
				if (index < 4) {
				System.out.println("\nPlayer" + (index) + "'s turn: " + deckArray[index].cardsValue());
				}
				//choice = scan.nextInt();
				//scan.nextLine();
				
				Message actionmsg = null;
				try {
					actionmsg = (Message) ois.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				switch(actionmsg.getType()) 
				
				{
				case Hit: 
					if (deckArray[index].cardsValue() < 21) {
						if (dealerDeck.cardsValue() < 17) 
							hit(index, dealerDeck, true);
						else 
							hit(index, playingDeck, false);
					} 
					break;
					
				case Stay: //stay(null);
					stay(players.get(index));
					index++;
					break;

				default: System.out.println("Invalid entry");	
				}
				
			while (index == 4){
				JOptionPane.showMessageDialog(null, "It is the Dealer's turn!");					
				
				if(dealerDeck.cardsValue() > 21 ) {	
					JOptionPane.showMessageDialog(null, "Dealer busted!");
					break;
				}
			}
		}
		for (int index = 0; index < deckArray.length; index++) {
			System.out.print("\nPlayer " + index + "'s hand: "  + deckArray[index].cardsValue());
			if(dealerDeck.cardsValue() > 21 ) {
				System.out.println("\n" + dealer + " busts! you win.");
			}
			else if(dealerDeck.cardsValue() > deckArray[index].cardsValue()) {
				System.out.println("\nPlayer " + index + " has lost");
			}
			else if(dealerDeck.cardsValue() < deckArray[index].cardsValue()) {
				System.out.println("\nPlayer " + index + " has won!");
			}
			else if(dealerDeck.cardsValue() == deckArray[index].cardsValue()) {
				System.out.println("\nYou push!");
			}
		}
		Deck usedDeck = new Deck();
		usedDeck.moveAllToDeck(playingDeck);
	}
}
