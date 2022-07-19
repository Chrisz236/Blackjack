/*
  Author: Haolin Zhang
  File:   Account.java
  Date:   July 19, 2022
  Ver:    1.0

  Description:
          Basic information for each client
 */
public class Account {
    private String username;
    private String password;
    private int balance;

    public Account(String username, String password, int balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public String getPassword(String username) {
        return this.password;
    }

    public int getBalance(String username) {
        return this.balance;
    }

    public void setBalance(int newBalance) {
        this.balance = newBalance;
    }
}
