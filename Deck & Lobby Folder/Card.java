
public class Card {
    private Suit suit;
    private Rank rank;

    //create a card given a suit and a rank
    public Card(Suit suit, Rank rank){
        this.suit = suit;
        this.rank = rank;
    }

    public int getValue(){
        return rank.rankvalue;
    }
    
    public Suit getSuit(){
        return suit;
    }

    public Rank getRank(){
        return rank;
    }

    public String toString(){
        return ("["+rank+" of "+ suit + "] ("+this.getValue()+")");

    }
    public Card(Card card) {
    	this.suit = card.getSuit();
    	this.rank = card.getRank();
    }
}
