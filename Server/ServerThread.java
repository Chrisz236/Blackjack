/*
    Author: Haolin Zhang
    File: ServerThread.java
    Date: July 19, 2022
    Ver: 1.0

    Description: Multiple threading handler for client connections
 */

import java.io.*;
import java.net.*;

public class ServerThread implements Runnable {
    private Boolean socketIsOpen = true;
    private final Socket cSocket;
    private static Server server;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private String username;

    public ServerThread(Socket socket, Server server) {
        this.cSocket = socket;
        ServerThread.server = server;
        try {
            OutputStream outputStream = socket.getOutputStream();
            this.oos = new ObjectOutputStream(outputStream);
            InputStream inputStream = socket.getInputStream();
            this.ois = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            // socketIsOpen = false;
            System.out.println(e.getMessage());
        }
    }

    /*
     * function to validate user entered information
     * @param Message msg
     * @return Boolean true  for valid
     *                 false for invalid
     */
    public boolean validateLogin(Message msg) {
        String info = msg.getData();
        String[] line = info.split(",");
        this.username = line[0];
        if (server.clientInfo.containsKey(username)) {
            return server.clientInfo.get(username).getPassword(username).equals(line[1]);
        }
        return false;
    }

    public Message getUserInfo() {
        String info = String.format("Username: %s, Balance: %s", username,
                server.clientInfo.get(username).getBalance(username));
        return new Message(info, Type.ShowPlayerInfo);
    }

    public Message getLobbyInfo() {
        String info = String.format("Number of lobbies: %d, Online: %d", server.lobbyManager.numOfLobbies, server.onlineNumber);
        return new Message(info, Type.ShowLobbyInfo);
    }

    @Override
    public void run() {
        try {
            System.out.println("[NEW CLIENT CONNECTED]: " + cSocket);
            while (socketIsOpen) {
                Message msg = (Message) ois.readObject();
                switch (msg.getType()) {
                    case Login:
                        if (validateLogin(msg)) {
                            System.out.println("[New Player Connected!]\n");
                            server.onlineNumber++;
                        } else {
                            System.out.println("[Login Failed!]");
                        }
                        break;

                    case GetPlayerInfo:
                        System.out.println("[Requesting PlayerInfo]");
                        oos.writeObject(getUserInfo());
                        System.out.println("[UserInfo sent]\n");
                        break;

                    case GetLobbyInfo:
                        System.out.println("[Requesting LobbyInfo]");
                        oos.writeObject(getLobbyInfo());
                        System.out.println("[LobbyInfo sent]\n");
                        break;

                    case AddLobby:
                        System.out.println("Type is Add Lobby");
                        break;

                    case DeleteLobby:
                        System.out.println("Type is Delete Lobby");
                        break;

                    case Logout:
                        System.out.println("Type is Logout");
                        server.onlineNumber--;
                        socketIsOpen = false;
                        break;

                    default:
                        System.out.println("[Unknown command]");
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


}
