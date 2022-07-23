/*
  Author: Steven Tran
  File:   Server.java
  Date:   July 21, 2022
  Ver:    1.1

  Description:
          Deck class for game playing
 */
import java.util.*;

public class Deck {
	private ArrayList<Card> cards;

	/*
	 * default constructor for deck class
	 */
	public Deck() {
		this.cards = new ArrayList<>();
	}

	/*
	 * create a full deck with 52 cards (all possible combo)
	 */
	public void createFullDeck() {
		//Create Cards
		for (Suit cardSuit : Suit.values()) {
			for (Value cardValue : Value.values()) {
				// add new card
				this.cards.add(new Card(cardSuit, cardValue));
			}
		}
	}

	/*
	 * re-arrange the order of the calling deck
	 */
	public void shuffle() {
		ArrayList<Card> tmpDeck = new ArrayList<>();
		Random random = new Random();
		int randomCardIndex;
		int originalSize = this.cards.size();
		for (int i = 0; i < originalSize; i++) {
			randomCardIndex = random.nextInt((this.cards.size() - 1) + 1);
			tmpDeck.add(this.cards.get(randomCardIndex));
			this.cards.remove(randomCardIndex);
		}
		this.cards = tmpDeck;
	}

	/*
	 * remove card at index i
	 */
	public void removeCard(int i) {
		this.cards.remove(i);
	}

	/*
	 * get one card at index i
	 */
	public Card getCard(int i) {
		return this.cards.get(i);
	}

	/*
	 * add a card to calling deck
	 */
	public void addCard(Card addCard) {
		this.cards.add(addCard);
	}

	/*
	 * draw a card from parameter deck to calling deck (top one)
	 */
	public void draw(Deck deck) {
		this.cards.add(deck.getCard(0));
		deck.removeCard(0);
	}

	/*
	 * literally do nothing
	 */
	public void stay() {
	}

	/*
	 * return the number od
	 */
	public int deckSize() {
		return this.cards.size();
	}

	public void moveAllToDeck(Deck moveTo) {
		int thisDeckSize = this.cards.size();

		//put cards into moveTo deck
		for (int i = 0; i < thisDeckSize; i++) {
			moveTo.addCard(this.getCard(i));
		}

		for (int i = 0; i < thisDeckSize; i++) {
			this.removeCard(0);
		}
	}

	//return total value of cards in deck
	public int cardsValue() {
		int totalValue = 0;
		int aces = 0;

		for (Card aCard : this.cards) {
			switch (aCard.getValue()) {
				case TWO:
					totalValue += 2;
					break;
				case THREE:
					totalValue += 3;
					break;
				case FOUR:
					totalValue += 4;
					break;
				case FIVE:
					totalValue += 5;
					break;
				case SIX:
					totalValue += 6;
					break;
				case SEVEN:
					totalValue += 7;
					break;
				case EIGHT:
					totalValue += 8;
					break;
				case NINE:
					totalValue += 9;
					break;
				case TEN:
					totalValue += 10;
					break;
				case JACK:
					totalValue += 10;
					break;
				case QUEEN:
					totalValue += 10;
					break;
				case KING:
					totalValue += 10;
					break;
				case ACE:
					totalValue += 1;
					break;
			}
		}
		for (int i = 0; i < aces; i++) {
			if (totalValue > 10) {
				totalValue += 1;
			} else {
				totalValue += 11;
			}
		}

		return totalValue;

	}

	public String toString() {
		String cardListOutput = "";
		for (Card aCard : this.cards) {
			cardListOutput += "\n" + aCard.toString();

		}
		return cardListOutput;
	}
}
