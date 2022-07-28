package client;
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
import javax.swing.JTextField;
import server.Player;

public class consoleBlackJack {
	private int user;
	private static ArrayList<Player> players;
	private static Player dealer;
	private Deck deck;
	private Scanner scan;
	private int index = 0;
	private static ObjectOutputStream os;
	private static ObjectInputStream ois;
	private int amount = 0;
	private int totalBet = 0;
	private String sourceName = "userFile.txt";
	/*
	 * need to put in Oos and Ios 
	 */
	/*
	 * default constructor for BlackJack game
	 * initialize all variables
	 */
	public consoleBlackJack(Player dealer, ArrayList<Player> players, ObjectOutputStream os, ObjectInputStream ois) {
		this.dealer = dealer;
		this.players = players;
		this.os = os;
		this.ois = ois;
	}

	/*
	 * add bet amount to player
	 */
	
	//taking into consideration of index turns
	//public void bet(Player player, int amount)	{
	public void bet(int turn, int amount) {
		/*if (!players.get(turn).isDealer) {
		System.out.println("betting portion");
		
		
				Message balancemsg = new Message();
		int balance = players.get(turn).getBalance();
		int newbalance = balance - amount; 
		int newbalance = Integer.parseInt((String) balancemsg.getData());
		players.get(turn).addBalance(amount);
		
		totalBet = amount;
		Message msg = new Message();
		msg = new Message();
		
		*/

		}

	public void hit(Player player, Deck tmpdeck) {
		
		System.out.println("hello");

		}


	/*
	 * do nothing, mark as skip
	 */
	public void stay(Player player) {
	}

	public String getInfo() {
		return "";
	}

	public void start() {
	
		//Create playing deck
		int choice;
		
		scan = new Scanner(System.in);

		Deck playingDeck = new Deck();
		//id++;
		playingDeck.createFullDeck();
		playingDeck.shuffle();
		//Create a deck for a player
//		Deck playerDeck = new Deck();
		//id++;
		Deck dealerDeck = new Deck();

		dealerDeck.draw(playingDeck);
		dealerDeck.draw(playingDeck);

		System.out.println("\n\n" + "Dealer's Hand: " + dealerDeck.getCard(0).getValue());
		System.out.println(dealerDeck.getCard(0).toString() + "\n[Hidden]");
		
		Deck[] deckArray = new Deck[4];
		playingDeck.shuffle();
		for (int index = 0; index < deckArray.length; index++) {
			deckArray[index] = new Deck();
			deckArray[index].draw(playingDeck);
			deckArray[index].draw(playingDeck);
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
				else System.out.println("Dealer Wins!");
				return;
			}
		
		
		while(index < 4) {
			do {
				if (index < 4) {
				System.out.println("\nPlayer" + (index) + "'s value: " + deckArray[index].cardsValue());
				System.out.println("\n" + "choose 0 to hit" + "\n" + "choose 1 to stay" + "\n" + "choose 2 to show all hands" + "\n");
				
				}
				choice = scan.nextInt();
				scan.nextLine();
				switch(choice) 
				{
				case 0: //hit		
					deckArray[index].draw(playingDeck);
					System.out.print("Player" + index + " draws: "+ deckArray[index].getCard(0));
					System.out.print(deckArray[index].toString());
					if (deckArray[index].cardsValue() > 21 ) {
						System.out.println("\nPlayer busts and has: " + deckArray[index].cardsValue());
						index++;
						break;
					}
					break;
				case 1: //stay
					System.out.println("Player" + index + " stays at: " + deckArray[index].cardsValue());
					index++;
					break;
				case 2:  // show all cards out
					System.out.println( "\nAll cards out of the deck");
					for (int index = 0; index < deckArray.length; index++) {
					System.out.print(deckArray[index].toString());
					}
					System.out.println("\n\n" + "Dealer's Hand: " + dealerDeck.cardsValue() + "\n" + dealerDeck.getCard(0).toString() + "\n[Hidden]");
					continue;

				default: System.out.println("Invalid entry");	
				}
				
			}while (choice != 1); 
			while (index == 4){
				System.out.println("\n\n" + "Dealer's Hand: " + dealerDeck.cardsValue() + 
						"\n" + dealerDeck.toString());
				while(dealerDeck.cardsValue() < 17) {
					dealerDeck.draw(playingDeck);
					System.out.println("\n" + "Dealer Draws: " + dealerDeck.getCard(dealerDeck.deckSize()-1).toString());
					System.out.print("Dealer's Hand is: "+ dealerDeck.cardsValue() + "\n");
					
				}
				if(dealerDeck.cardsValue() > 21 ) {
					
					break;
				}
				if(dealerDeck.cardsValue() < 21) {
					System.out.println(dealerDeck.toString());
					System.out.println("\n" + "Dealer's hand value: " + dealerDeck.cardsValue());
					break;
				}
				
			}
			
		}
		for (int index = 0; index < deckArray.length; index++) {
			System.out.print("\nPlayer " + index + "'s hand: "  + deckArray[index].cardsValue());
			//System.out.println(deckArray[index] + "\n");
			if(dealerDeck.cardsValue() > 21 ) {
				System.out.println("\n" + "Dealer busts! you win.");
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

	}

	public static void main(String[] args) {
		System.out.println("Welcome to BlackJack\n");

		BlackJack blackJack = new BlackJack(dealer, players, os, ois); 
		blackJack.start();

		Deck playingDeck = new Deck();

		Deck usedDeck = new Deck();
		usedDeck.moveAllToDeck(playingDeck);

	}
}	
		

