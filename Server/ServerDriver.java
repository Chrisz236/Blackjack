/*
  Author: Haolin Zhang
  File:   Blackjack_Server.java
  Date:   July 18, 2022
  Ver:    1.0

  Description:
          Driver class for Blackjack server
 */
public class ServerDriver {
    final static String host = "localhost";
    final static int port = 7777;

    public static void main(String args[]) {
        Server server = new Server(host, port);

        // ------------------- FOR TEST PURPOSE ONLY -------------------- //
        Message m = new Message("HAOLIN ZHANG, hZhang", "login");

        if(server.validateLogin(m)) {
            System.out.println("login verified!");
        } else {
            System.out.println("login failed!");
        }

        System.out.print("Balance for HAOLIN ZHANG: ");
        System.out.println(server.getPlayerBalance("HAOLIN ZHANG"));

        System.out.print("\nChanging balance to 8120...");
        server.setPlayerBalance("HAOLIN ZHANG", 8120);
        System.out.print("Balance for HAOLIN ZHANG: ");
        System.out.println(server.getPlayerBalance("HAOLIN ZHANG"));

    }
}
