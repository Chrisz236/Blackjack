import java.util.ArrayList;

public class Deck {

    private ArrayList<Card> deck;

    public Deck(){
        deck = new ArrayList<Card>();
    }
    
    public void addCard(Card card) {
    	deck.add(card);

    }
    public String toString() {
    	String Output = "";
    	for (Card card : this.deck) {
    		Output += card;
    		Output += "\n";
    	}
    	return Output;
    	
    }
    public Deck(boolean makeDeck) {
    	deck = new ArrayList<Card>();
    	if(makeDeck) {
    		
    		for(Suit suit : Suit.values()) {
    			
    			for (Rank rank : Rank.values()) {
    				deck.add(new Card(suit, rank));
    			}
    		}
    	}
    }
    
    public void shuffle() {
    	ArrayList<Card> shuffled = new ArrayList<Card>();
    	while(deck.size()>0) {
    		int cardToPull = (int)(Math.random()*(deck.size()-1));
    		shuffled.add(deck.get(cardToPull));
    		deck.remove(cardToPull);
    	}
    	deck = shuffled;
    }
    public Card takeCard() {
    	
    	Card cardToTake = new Card(deck.get(0));
    	deck.remove(0);
    	return cardToTake;
    	
    }
    public boolean hasCards() {
    	if (deck.size()>0) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    public void emptyDeck(){
        deck.clear();
    }
    public void addCards(ArrayList<Card> cards){
        deck.addAll(cards);
    }
    public void reloadDeckFromDiscard(Deck discard){
        this.addCards(discard.getCards());
        this.shuffle();
        discard.emptyDeck();
        System.out.println("Ran out of cards, creating new deck from discard pile & shuffling deck.");
    }
    public int cardsLeft() {
    	return deck.size();
    }
}
