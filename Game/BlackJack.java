/*
  Author: Steven Tran
  File:   Server.java
  Date:   July 21, 2022
  Ver:    1.1

  Description:
          Game class for game rule
 */
import java.util.*;

public class BlackJack {
	private ArrayList<Player> players;
	private Player dealer;
	private Deck deck;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Welcome msg
		System.out.println("Welcome to BlackJack");
		
		//Create playing deck
		Deck playingDeck = new Deck();
		playingDeck.createFullDeck();
		playingDeck.shuffle();
		//Create a deck for a player
		Deck playerDeck = new Deck();
		
		Deck dealerDeck = new Deck();
		
		double playerMoney = 100.00;
		
		Scanner userInput = new Scanner(System.in);
		
		//GameLoop 
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
			
			//Start dealing
			//player gets cards
			playerDeck.draw(playingDeck);
			playerDeck.draw(playingDeck);
			//dealer gets cards
			dealerDeck.draw(playingDeck);
			dealerDeck.draw(playingDeck);
			
			while(true) {
				System.out.print("Your Hand is: "+ playerDeck.cardsValue());
				System.out.print(playerDeck.toString());
				
				//Displayer Dealers Hand
				System.out.println("\nDealer's Hand: \n\n" + dealerDeck.getCard(0).toString() + "\n[Hidden]");
				
				//What does the player want to do?
				System.out.println("\nWould you like to (1)hit or (2)stay?");
				int response = userInput.nextInt();
				
				//they hit
				if(response ==1) {
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
				
				if(response == 2) {
					break;
				}
			}
			
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

	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public void drawCard(Player player) {

	}

	public void hit(Player player) {

	}

	public void stay() {

	}

	public void bet(int amount, Player player) {

	}

	public Deck showAllHands(Player player) {
		return player.getDeck();
	}
}
