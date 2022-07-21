import java.util.Scanner;

public class Player extends Person {
	
    Scanner input = new Scanner(System.in);
	    /**
	     * Create a new Player
	     */
	    public Player(){
	        super.setName("Player");
	    }

		public void makeDecision(Deck deck, Deck discard) {
			int  decision = 0;
			boolean getNum = true;
			while(getNum){
				try{
					System.out.println("Would you like to: 1) Hit or 2) Stand");
		            decision = input.nextInt();
		            getNum = false;
		        }
		           
				catch(Exception e){
					System.out.println("Invalid");
					input.next();
		           }
		        System.out.println("You selected: " + decision);   //we don't close the scanner, because we will need it later.
		       }

		       //if they decide to hit
		       if (decision == 1) {
		           //hit the deck using the deck and discard deck
		           this.hit(deck, discard);
		           //return (exit the method) if they have blackjack or busted
		           if(this.getHand().calculatedValue()>20){
		               return;
		           }
		           //if they didnt bust or get 21, allow them to decide to hit or stand again by going back to this same method
		           else{
		               this.makeDecision(deck, discard);
		           }

		           //if they type any number other than 1, we'll assume they're standing
		       } else {
		           System.out.println("You stand.");
		       }
		   }
		public void hit(Deck deck, Deck discard){
			if (!deck.hasCards()) {
		        deck.reloadDeckFromDiscard(discard);
		    }
		    this.hand.takeCardFromDeck(deck);
		    System.out.println(this.name + " gets a card");
		    this.printHand();

		}
		
}



