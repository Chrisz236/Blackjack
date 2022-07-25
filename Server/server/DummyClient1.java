package server;

import java.io.*;
import java.net.*;
import java.util.Map;

public class DummyClient1 {
    public static String host = "localhost";
    public static int port = 7777;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(host, port);
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(inputStream);

            try {
                // Example of Login
                oos.writeObject(new Message("HAOLIN ZHANG, hZhang", Type.Login));

                /*
                 * PLEASE READ:
                 * In actual client, you will just receive the response from Server (a Message object)
                 * but don't know what that message contains, so you will need a big switch to sort
                 * all those message by "getType()" of that message, then fetch (decode) the "data"
                 * by standard. For most of the cases, returned info are String and separated by comma ",".
                 * The only one exception is:
                 *              "Type.ShowAllPlayerInfo"
                 *
                 * The message with this return type, data part is a "Map<String, Player>" object, which
                 * contains all players' info in Server, including:
                 *              "username", (String)
                 *              "password", (String)
                 *              "balance",  (int)
                 *              "isDealer"  (boolean)
                 *              "oos"       (DO NOT MODIFY!)
                 *
                 * Then you can use this information to initialize your local player data for game play
                 * More detailed usage can be found in code below.
                 */

                // Read message returned from Server
                Message LoginResult = (Message) ois.readObject();

                // If Login was succeeded
                if (LoginResult.getType() == Type.Succeed) {
                    System.out.println("Login Succeed");

                    // Data part in LoginResult is "PLAYER" / "DEALER"
                    System.out.println("Role: " + LoginResult.getData());

                    // Example of ViewPlayerInfo (for certain user)
                    oos.writeObject(new Message(Type.ViewPlayerInfo));
                    Message msg = (Message) ois.readObject();
                    System.out.println("Message from getUserInfo():\n" + msg.getData());

                    // Example of ViewAllPlayerInfo (one request for all user)
                    // Every client call this function will return the full player's info Map
                    // Could be used in initializing stage
                    oos.writeObject(new Message(Type.ViewAllPlayerInfo));
                    try{
                        // First type cast (get raw message)
                        Message msg1 = (Message) ois.readObject();

                        if(msg1.getType() == Type.ShowAllPlayerInfo){
                            // Second type cast (get all player's info as a Map)
                            Map<String, Player> map = (Map<String, Player>) msg1.getData();

                            // For-each loop to get every player's info in that map, then extract required info
                            for(var player : map.entrySet()) {
                                // This is how you access the data
                                System.out.printf("%s, %s, %s%n", player.getValue().username, player.getValue().balance, player.getValue().isDealer);
                            }
                        }
                    } catch (ClassCastException e) {
                        System.out.println(e.getMessage());
                    }

                    // Example of ReloadBalance
                    oos.writeObject(new Message("2000", Type.ReloadBalance));

                    // Example of CreateLobby
                    oos.writeObject(new Message("Test Lobby", Type.CreateLobby));

                    // Example of GetLobbyManagerInfo
                    oos.writeObject(new Message(Type.GetLobbyManagerInfo));
                    Message msg1 = (Message) ois.readObject();
                    System.out.println("Message from GetLobbyManagerInfo():\n" + msg1.getData());

                    // Example of JoinLobby
                    oos.writeObject(new Message("Test lobby", Type.JoinLobby));

                    // Example of ExitLobby
                    oos.writeObject(new Message(Type.ExitLobby));

                    // Example of DeleteLobby
                    oos.writeObject(new Message("Test lobby", Type.DeleteLobby));

                    // Example of StartGame (game command)
                    oos.writeObject(new Message("HAOLIN ZHANG", Type.StartGame));

                    // Example of Hit (game command)
                    oos.writeObject(new Message("HAOLIN ZHANG, DIAMOND FIVE", Type.Hit));

                    // Example of Bet (game command)
                    oos.writeObject(new Message("HAOLIN ZHANG, 2000", Type.Bet));

                    // Example of Exit (game command)
                    oos.writeObject(new Message(Type.Exit));

                    // Example of Logout
                    oos.writeObject(new Message(Type.Logout));
                } else {
                    System.out.println("Login Failed");
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
