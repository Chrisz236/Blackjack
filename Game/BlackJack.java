/*
  Author: Steven Tran
  File:   Server.java
  Date:   July 21, 2022
  Ver:    1.1

  Description:
          Game class for game rule
 */
import java.util.*;
import java.io.*;

import cs401bjproject.Lobby;
import java.util.Vector;
import javax.swing.JTextField;
public class BlackJack2 {
	private int user;
	private static ArrayList<Player> players;
	private static Player dealer;
	private Deck deck;
	private Scanner scan;
	private int index = 0;
	private static ObjectOutputStream os;
	private static ObjectInputStream ois;
	private int amount = 0;
	private String sourceName = "userFile.txt";
	/*
	 * need to put in Oos and Ios 
	 */
	/*
	 * default constructor for BlackJack game
	 * initialize all variables
	 */
	public BlackJack2(Player dealer, ArrayList<Player> players, ObjectOutputStream os, ObjectInputStream ois) {
		this.dealer = dealer;
		this.players = players;
		this.os = os;
		this.ois = ois;
	}
	public void userFileName(String filename) {
		/*int names = 0;
		Message[] nameArray = new Message[5];
		for(int i = 0; i < Lobby.numOfPlayers; i++) { 
			nameArray[i] = new Message(Type.ShowPlayerInfo);
		}
		*/
		File file = new File(filename);
		
		System.out.print("IT works \n");
		try {
			Scanner sc = new Scanner(file);
			
			//String line;
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				System.out.println(line);
		}
			
			sc.close();
		}catch (IOException e){
			System.out.println(e);
		}
		
	}
	/*
	 * add bet amount to player
	 */
	public void bet(Player player, int amount) {
		//int amount1 = 0;
		this.amount = amount;
		/*
		Scanner userInput = new Scanner(System.in);
		
		while (amount1 > 0) {
			//Play on 
			// take the bets 	
			amount1 = userInput.nextDouble(); 
		}
		*/
		System.out.println("betting portion");
		Message msg = new Message();
		Message[] msgArray = new Message[5];
		
		for(int i = 0; i < Lobby.numOfPlayers; i++) { 
			msgArray[i] = new Message(Type.ShowPlayerInfo);
		}
		
		msg = new Message("STEVEN TRAN", Type.GetBalance);
		//msg2 = new Message("ANDREW", Type.GetBalance);
		Message balance = new Message();
		balance = new Message("BALANCE", Type.ShowBalance);
		Integer.parseInt((String) balance.getData());
		try {
			os.writeObject(balance);
			os.writeObject(msg);
			//os.writeObject(msg2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		//msg2 = new Message("ANDREW", Type.GetBalance);
		try {
			os.writeObject(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//double playerBalance = Player.getBalance(players.get(index).username); 
		
	/* 
	 * 
	 * Haolin needs to help with this portion 	
	 * How do we get balance from Server/ Client to update for every betting 
	 * 
	 * 
	*/
		
		
	}

	/*
	 * draw a card from main deck
	 */
	public void hit(Player player, Deck tmpdeck) {
		
		/* here the player needs to choose the button hit 
		 * after the hit button is selected the updated amount of cards is added by one 
		 * //they hit

		//load players value then add a new amount 

		*/	
		Message H = new Message("STEVEN TRAN, D-5", Type.Hit);
		try {
			String cardInfo = player.deck.draw(deck);
			String username = player.username;
			Message H = new Message(player.username)
			System.out.println(deck.toString());
			os.writeObject(H); // broadcasting to other players in game 
			ois.readObject();
			if (deck.cardsValue() > 21) throw new Exception ("you lose");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(tmpdeck);
		System.out.println("hello");
		
		//Deck playingDeck = new Deck();
		//Deck playerDeck = new Deck();
		//playerDeck.draw(playingDeck);
		
		//System.out.print("Your Hand is: "+ playerDeck.cardsValue());
		
		
//--------------the amount bet will be taken from player 
		}
			//playerTurn(player,deck);
	

	/*
	 * do nothing, mark as skip
	 */
	public void stay(Player player) {
		
		System.out.println("Stay is called");
	}

	/*
	 * add a new player to ArrayList<Player> players
	 */
	public void addPlayer(Player player) {
		
		players.add(player);
		System.out.println("Adding " + player.username + " to the game");
		
	}

	/*
	 * String contains who is in the game and their cards
	 * looks like this:
	 * "DEALER, ANDREW BUSTOS, CLUB TWO, DIAMOND SIX\n PLAYER, HAOLIN ZHANG, SPADE FIVE, SPADE THREE\n PLAYER, STEVEN TRAN, HEART FIVE, DIAMOND SIX..."
	 */
	public String getInfo() {
		Message gI = new Message("(USERNAME), (CARD)", Type.ViewAllPlayerInfo);
		System.out.print("I'm confused");
		for (int i = 0; i < Lobby.numOfPlayers; i++) {
			System.out.println(players.get(i).username + "\n");
			System.out.println(players.get(i).toString());
			System.out.print(deck.toString());
			//Displayer Dealer's Hand
			System.out.println("\n\n" + dealer + "'s Hand: \n" + deck.getCard(0).toString() + "\n[Hidden]");
			
		}
		return "";
	}

	public void start() {
		
		/* Order of operations
		 * Accept players into a lobby (addPlayer) done by 
		 * 
		 * First accept available bets 
		 * 
		 */

		//Create playing deck
		int choice;
		
		scan = new Scanner(System.in);
		Deck playingDeck = new Deck();
		//id++;
		playingDeck.createFullDeck();
		playingDeck.shuffle();
		//Create a deck for a player
		Deck playerDeck = new Deck();
		//id++;
		Deck dealerDeck = new Deck();

		//Start dealing
		//player gets cards
		playerDeck.draw(playingDeck);
		playerDeck.draw(playingDeck);
		//dealer gets cards
		dealerDeck.draw(playingDeck);
		dealerDeck.draw(playingDeck);			
		//System.out.println(playingDeck);
		/*
		if(playerDeck.cardsValue() == 21 ) {
			System.out.print("You win! \n");
		}
		if(dealerDeck.cardsValue() == 21 ) {
			System.out.print("You lose");
		}
		
		
		/*
		Scanner userInput = new Scanner(System.in);
		JTextField myField = new JTextField();
		replace scanner with public functions to call other classes 
		
		System.out.print("Player's Hand is: "+ playerDeck.cardsValue());
		System.out.print(playerDeck.toString());
		//Displayer Dealers Hand
		System.out.println("\nDealer's Hand: \n\n" + dealerDeck.getCard(0).toString() + "\n[Hidden]");
	*/	
		
		//create a for loop for which players turn 
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < Lobby.numOfPlayers; j++) {
				System.out.print(i);
			}
			
//			System.out.println(players);
		}
		
		//Display Player's hand
		System.out.print(players + "'s Hand is: "+ playerDeck.cardsValue());
		System.out.print(playerDeck.toString());
		//Displayer Dealer's Hand
		System.out.println("\n\n" + dealer + "'s Hand: \n" + dealerDeck.getCard(0).toString() + "\n[Hidden]");
		
		
		//Print number of players in lobby 
		System.out.println(Lobby.numOfPlayers);
		
		//just to test for scanner
		System.out.println("\n" + "choose 0 to hit" + "\n" + "choose 1 to stay" + "\n" + "choose 2 to show all hands" + 
		"\n" + "choose 3 to bet" + "\n" + "choose 4 to view all players credentials"
		+ "\n" + "choose 5 testing the hit" + "\n" + "choose 6 to view all players credentials");

		/* if (Lobby.numOfPlayers = 0) 
		 * get the first player from arraylist and allow them to use different commands 
		 * set the index of players in lobby 
		 * while(index < Lobby.numOfPlayers)
		 * 
		 * intialize players to a number
		 */
		
		while(index < 6) {
			do {
				choice = scan.nextInt();
				scan.nextLine();
				switch(choice) 
				{
				case 0: 
				if (playerDeck.cardsValue() < 21) 
					if (dealerDeck.cardsValue() < 17) 
					//hit(null, null);
					hit(players.get(index), playingDeck);
					System.out.println("Hit is called");
					System.out.print(players + "'s Hand is: "+ deck.cardsValue());
					System.out.print(deck.toString());
					
				case 1: stay(null);
					//stay(players.get(index));
					index++;
					System.out.println(index);
					break;
				case 2: 
					getInfo();
					continue;
				case 3: 
					//bet(players.get(index), amount);
					bet(null, amount);
					break;
				case 4: 
					userFileName("C:\\Users\\Steven\\Desktop\\School\\Cal State East Bay\\Summer 22\\CS 401\\Code Examples\\BlackJack\\src\\cs401bjproject\\userFile.txt");
					break;
				case 5: //testing hit 
					
					playerDeck.draw(playingDeck);
					System.out.print(players + "'s Hand is: "+ playerDeck.cardsValue());
					System.out.print(playerDeck.toString());
					if (playerDeck.cardsValue() > 21 ) {
						System.out.println("\n Player busts and has: " + playerDeck.cardsValue());
						index++;
						break;
					}
					break;
				case 6: 
					System.out.print(players + "'s Hand is: "+ playerDeck.cardsValue());
					System.out.print(playerDeck.toString());
					System.out.println("\n\n" + dealer + "'s Hand: \n" + dealerDeck.getCard(0).toString() + "\n[Hidden]");
					continue;
				default: System.out.println("Invalid entry");	
				}
				
			}while (choice != 1); 
			//while (index < players.size());
			//while(index < Lobby.numOfPlayers);
			//while (index == Lobby.numOfPlayers) 
			while (index == 6){
				while(dealerDeck.cardsValue() < 17) {
					dealerDeck.draw(playingDeck);
					System.out.print(dealer + "'s Hand is: "+ dealerDeck.cardsValue());
					System.out.println("\n Dealer Draws: " + dealerDeck.getCard(dealerDeck.deckSize()-1).toString());
				}
				if(dealerDeck.cardsValue() > 21 ) {
					System.out.println("Dealer busts! you win.");
					break;
				}
				if(dealerDeck.cardsValue() < 21) {
					System.out.print(dealerDeck.toString());
					break;
				}
				
			}
			
		}
		if(playerDeck.cardsValue() == 21 ) {
			System.out.print("You win! \n");
		}
		if(dealerDeck.cardsValue() == 21 ) {
			System.out.print("You lose");
		}
		if(dealerDeck.cardsValue() > playerDeck.cardsValue()){
			System.out.println("You lose, Try again!");
		}
		if(dealerDeck.cardsValue() < playerDeck.cardsValue()){
			System.out.println("\n You win, Try again!");
		}
		/*playerList = players.size();
		listIterator()
		*/
}
	
	public static void main(String[] args) {
		System.out.println("Welcome to BlackJack\n");
		/*
		 * Make bets at this section 
		playingDeck.createFullDeck();
		playingDeck.shuffle();
		//System.out.println(playingDeck);
		 */
		
		
		BlackJack2 blackJack = new BlackJack2(dealer, players, os, ois); 
		blackJack.start();

		Deck playingDeck = new Deck();
		/*
		 * After game is over move used cards back to playing deck
		 */
		Deck usedDeck = new Deck();
		usedDeck.moveAllToDeck(playingDeck);

	}
}	

/*		
 * 			//GameLoop 
 * 
 * 				if(response ==1) {
					playerDeck.draw(playingDeck);;
					System.out.println("You draw a: " + playerDeck.getCard(playerDeck.deckSize()-1).toString());
					//Bust if >21
					if(playerDeck.cardsValue() > 21) {
						System.out.println("Bust. Currently valued at: " + playerDeck.cardsValue());
						playerMoney -= playerBet;
						endRound = true;
						break;
					}
				}

			while(playerMoney > 0) {
				//continue play
				//Take the players bet
				System.out.println("You have: $" + playerMoney + ", how much do you want to bet? ");
				double playerBet = userInput.nextDouble();
				if(playerBet > playerMoney) {
					System.out.println("You cannot bet more than you have ");
					break;
				}
			
				boolean endRound = false;
				//Reveal dealers hand
				System.out.println("Dealer Cards: " + dealerDeck.toString());
				//see if dealer has more points than player
				if((dealerDeck.cardsValue() > playerDeck.cardsValue() && endRound == false)){
					System.out.println("Dealer beats you!");
					playerMoney -= playerBet;
					endRound = true;
				}
				//Dealer Draws at 16 and stays 17 
				while((dealerDeck.cardsValue() < 17 && endRound == false)) {
					dealerDeck.draw(playingDeck);
					System.out.println("Dealer Draws: " + dealerDeck.getCard(dealerDeck.deckSize()-1).toString());
				}
				//Display total value for dealer
				System.out.println("Dealer's hand is valued at: " + dealerDeck.cardsValue());
				//Determine if dealer busted
				if((dealerDeck.cardsValue() > 21 && endRound == false)) {
					System.out.println("Dealer busts! you win.");
					playerMoney+= playerBet;
					endRound = true;
				}
			
				//Determine if push
				if((playerDeck.cardsValue() == dealerDeck.cardsValue()) && endRound == false) {
					System.out.println("Push");
					endRound = true;
				}
			
				if((playerDeck.cardsValue() > dealerDeck.cardsValue()) && endRound ==false) {
					System.out.println("You win the Hand");
					playerMoney += playerBet;
					endRound = true;
				}
			
				playerDeck.moveAllToDeck(playingDeck);
				dealerDeck.moveAllToDeck(playingDeck);
				System.out.println("End of hand");
			}
		
			System.out.println("Out of money!");
		}
*/
