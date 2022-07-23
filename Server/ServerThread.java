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
    private int lobbyIdx = -1;

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
        String data = msg.getData();
        String[] line = data.split(",");
        this.username = line[0];
        if (server.playerInfo.containsKey(username)) {
            return server.playerInfo.get(username).getPassword(username).equals(line[1]);
        }
        return false;
    }

    /*
     * return the username and balance in data part of the Message
     */
    public Message getUserInfo() {
        String info = String.format("Username: %s\nBalance: %s", username,
                server.playerInfo.get(username).getBalance(username));
        return new Message(info, Type.ShowPlayerInfo);
    }

    /*
     * return the number of lobbies and how many people online, also current lobbies (all in data part)
     */
    public Message getLobbyManagerInfo() {
        String info = String.format("Number of lobbies: %s\nOnline: %d\n", server.lobbyManager.numOfLobbies, server.numOfClientsOnline);
        if (server.lobbyManager.numOfLobbies > 0) {
            info += server.lobbyManager.displayLobbies();
        }
        return new Message(info, Type.ShowLobbyManagerInfo);
    }

    public void reloadBalance(Message msg) {
        int amount = Integer.parseInt(msg.getData());
        if (amount >= 0) {
            server.playerInfo.get(username).addBalance(amount);
        }
    }

    public Message viewLobby(Message msg) {
        String lobbyName = msg.getData();
        return new Message(server.lobbyManager.viewLobby(lobbyName) + server.lobbyManager.getLobbyStatus(lobbyName),
                Type.ShowLobby);
    }

    /*
     * create a new lobby with given name
     */
    public void createLobby(Message msg) {
        String lobbyName = msg.getData();
        server.lobbyManager.createLobby(lobbyName);
    }

    /*
     * delete lobby with specific name, return false is lobby not found
     */
    public boolean deleteLobby(Message msg) {
        String lobbyName = msg.getData();
        return server.lobbyManager.deleteLobby(lobbyName);
    }

    /*
     * connect current player to given lobby name
     */
    public void joinLobby(Message msg) {
        String lobbyName = msg.getData();
        server.lobbyManager.addPlayerToLobby(lobbyName, server.playerInfo.get(username));
        lobbyIdx = server.lobbyManager.lobbyIndex(server.playerInfo.get(username));
    }

    /*
     * disconnect current player from lobby back to lobbyManager
     */
    public void exitLobby() {
        server.lobbyManager.removePlayerFromLobby(server.playerInfo.get(username));
    }

    /*
     * set current player as dealer, return false if there is already a one
     */
    public Message beDealer() {
        if (server.lobbyManager.setPlayerAsDealer(server.playerInfo.get(username))) {
            return new Message(Type.Succeed);
        }
        return new Message(Type.Failed);
    }

    public void addBet(Message msg) {
        int amount = Integer.parseInt(msg.getData().trim());
        server.lobbyManager.addBet(server.playerInfo.get(username), amount);
    }

    /*
     * start game where current player is in
     */
    public void startGame() {
        server.lobbyManager.startGame(server.playerInfo.get(username));
    }

    @Override
    public void run() {
        try {
            System.out.println("[NEW CLIENT CONNECTED]: " + cSocket);
            while (socketIsOpen) {
                Message msg = new Message();
                synchronized (msg) {
                    msg = (Message) ois.readObject();
                }

                switch (msg.getType()) {
                    case Login:
                        if (validateLogin(msg)) {
                            System.out.println("[New Player Connected]\n");
                            server.numOfClientsOnline++;
                        } else {
                            System.out.println("[Login Failed]\n");
                        }
                        break;

                    case GetPlayerInfo:
                        System.out.println("[Requesting PlayerInfo...]");
                        oos.writeObject(getUserInfo());
                        System.out.println("[UserInfo Sent]\n");
                        break;

                    case GetLobbyManagerInfo:
                        System.out.println("[Requesting LobbyInfo...]");
                        oos.writeObject(getLobbyManagerInfo());
                        System.out.println("[LobbyInfo Sent]\n");
                        break;

                    case ReloadBalance:
                        System.out.println("[Reloading...]");
                        reloadBalance(msg);
                        System.out.println("[Balance Updated]\n");
                        break;

                    case ViewLobby:
                        System.out.println("[Viewing Lobby...]");
                        oos.writeObject(viewLobby(msg));
                        System.out.println("[LobbyInfo Sent]\n");
                        break;

                    case CreateLobby:
                        System.out.println("[Creating a New Lobby...]");
                        createLobby(msg);
                        System.out.println("[New Lobby Created]\n");
                        break;

                    case JoinLobby:
                        System.out.println("[Joining Lobby...]");
                        joinLobby(msg);
                        System.out.println("[Lobby Joint]\n");
                        break;

                    case ExitLobby:
                        System.out.println("[Exiting Lobby...]");
                        exitLobby();
                        System.out.println("[Lobby Exited]\n");
                        break;

                    case DeleteLobby:
                        System.out.println("[Deleting Lobby...]");
                        if (deleteLobby(msg)) {
                            System.out.println("[Delete Succeed]\n");
                        } else {
                            System.out.println("[Delete Failed]\n");
                        }
                        break;

                    case BeDealer:
                        System.out.println("[Choosing Dealer...]");
                        oos.writeObject(beDealer());
                        break;

                    case StartGame:
                        System.out.println("[Starting Game...]");
                        startGame();
                        System.out.println("[Game Started]");

                    case Logout:
                        System.out.println("[Disconnecting...]");
                        server.numOfClientsOnline--;
                        socketIsOpen = false;
                        System.out.println("[Disconnected]");
                        break;

                    case AddBet:
                        System.out.println("[Adding Bet...]");

                    case ShowAllHands:
                        System.out.println("[Show all hands...]");
                        oos.writeObject(beDealer());

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
