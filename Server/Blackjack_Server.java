/*
  Author: Haolin Zhang
  File:   Blackjack_Server.java
  Date:   July 18, 2022
  Ver:    1.0

  Description:
          Driver class for Blackjack server
 */
public class Blackjack_Server {
    String host = "localhost";
    int port = 7777;

    Server server = new Server(host, port);
}
