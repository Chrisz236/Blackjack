package server;
/*
  Author: Haolin Zhang
  File:   Player.java
  Date:   July 19, 2022
  Ver:    1.1

  Description:
          Basic information for each client
 */
public class Player {
    public String username;
    private String password;
    private int balance;
    public Deck deck;

    public Player() {
        this.username = "[Undefined]";
        this.password = "[Undefined]";
        this.balance = -1;
        this.deck = new Deck();
    }

    /*
     * constructor for Account class, initialize all variables
     */
    public Player(String username, String password, int balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    /*
     * getter function for password
     * @return String password
     */
    public String getPassword(String username) {
        return this.password;
    }

    /*
     * getter function for password
     * @return int balance
     */
    public int getBalance(String username) {
        return this.balance;
    }

    /*
     * setter function for balance
     * @param int newBalance
     */
    public void addBalance(int amount) {
        this.balance = this.balance + amount;
    }

    public void setBalance(int amount) {
        this.balance = amount;
    }
}
