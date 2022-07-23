/*
  Author: Steven Tran
  File:   Server.java
  Date:   July 21, 2022
  Ver:    1.1

  Description:
          Card class for deck
 */

public class Card {
    private Suit suit;
    private Value value;

    // default constructor for card
    public Card(Suit suit, Value value) {
        this.value = value;
        this.suit = suit;
    }

    public Value getValue() {
        return this.value;
    }

    public String toString() {
        return this.suit + "-" + this.value;
    }
}

enum Suit {
    CLUB,
    DIAMOND,
    SPADE,
    HEART
}

enum Value {
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK,
    QUEEN,
    KING,
    ACE
}