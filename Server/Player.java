/*
  Author: Haolin Zhang
  File:   Account.java
  Date:   July 19, 2022
  Ver:    1.1

  Description:
          Basic information for each client
 */
public class Player {
    public String username;
    private String password;
    private int balance;

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
}
