package server;
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
    public Message validateLogin(Message msg) {
        String data = (String) msg.getData();
        String[] line = data.split(",");
        this.username = line[0];
        if (server.playerInfo.containsKey(username)) {
            if (server.playerInfo.get(username).getPassword().equals(line[1].trim())) {
                System.out.println("[Verify Succeed]");
                server.numOfClientsOnline++;
                if (server.playerInfo.get(username).isDealer) {
                    return new Message("DEALER", Type.Succeed);
                } else {
                    return new Message("PLAYER", Type.Succeed);
                }
            }
        }
        System.out.println("[Failed to Verify]");
        return new Message(Type.Failed);
    }

    /*
     * return the username and balance in data part of the Message
     */
    public Message getUserInfo() {
        String info = String.format("%s, %s", username,
                server.playerInfo.get(username).getBalance());
        return new Message(info, Type.ShowPlayerInfo);
    }

    /*
     * return an ArrayList<Player> players, this contains all data of a user
     * you will need "username", "balance", and "isDealer"
     */
    public Message getAllUserInfo() {
        return new Message(server.playerInfo, Type.ShowAllPlayerInfo);
    }

    /*
     * return the number of lobbies and how many people online, also current lobbies (all in data part)
     */
    public Message getLobbyManagerInfo() {
        String info = String.format("%d, %d, ", server.lobbyManager.numOfLobbies, server.numOfClientsOnline);
        if (server.lobbyManager.numOfLobbies > 0) {
            info += server.lobbyManager.displayLobbies();
        }
        return new Message(info, Type.ShowLobbyManagerInfo);
    }

    /*
     * add the given amount to current player
     * looks like this:
     *        new Message("1000", Type.ReloadBalance)
     */
    public void reloadBalance(Message msg) {
        int amount = Integer.parseInt((String) msg.getData());
        if (amount >= 0) {
            server.playerInfo.get(username).addBalance(amount);
        }
    }

    /*
     * return the balance for certain user
     * looks like this:
     *        new Message("HAOLIN ZHANG", Type.GetBalance)
     */
    public Message getBalance(Message msg) {
        return new Message(String.valueOf(server.playerInfo.get((String) msg.getData()).getBalance()), Type.ShowBalance);
    }

    /*
     * update the player's balance for certain user after a game finished
     * looks like this:
     *        new Message("HAOLIN ZHANG, 5200", Type.UpdateBalance)
     */
    public void updateBalance(Message msg) {
        String data = (String) msg.getData();
        String[] line = data.split(",");
        String username = line[0];
        int newBalance = Integer.parseInt(line[1].trim());
        server.playerInfo.get(username).balance = newBalance;
    }

    /*
     * return the String with who is in the lobby and also the status
     * looks like this:
     *        new Message("HAOLIN ZHANG, ALICE BOB, OPEN", Type.ShowLobby)
     */
    public Message viewLobby(Message msg) {
        String lobbyName = (String) msg.getData();
        return new Message(server.lobbyManager.viewLobby(lobbyName) + server.lobbyManager.getLobbyStatus(lobbyName),
                Type.ShowLobby);
    }

    /*
     * create a new lobby with given name
     * looks like this:
     *        new Message("First Lobby", Type.CreateLobby)
     */
    public void createLobby(Message msg) {
        String lobbyName = (String) msg.getData();
        server.lobbyManager.createLobby(lobbyName);
    }

    /*
     * delete lobby with specific name, return false is lobby not found
     * looks like this:
     *        new Message("First Lobby", Type.DeleteLobby)
     */
    public boolean deleteLobby(Message msg) {
        String lobbyName = (String) msg.getData();
        return server.lobbyManager.deleteLobby(lobbyName);
    }

    /*
     * connect current player to given lobby name
     * looks like this:
     *        new Message("First Lobby", Type.JoinLobby)
     */
    public void joinLobby(Message msg) {
        String lobbyName = (String) msg.getData();
        server.lobbyManager.addPlayerToLobby(lobbyName, server.playerInfo.get(username));
        server.playerInfo.get(username).setOos(oos);
    }

    /*
     * disconnect current player from lobby back to lobbyManager
     * looks like this:
     *        new Message("First Lobby", Type.ExitLobby)   - normal
     *        new Message("First Lobby", Type.Exit)        - game command
     */
    public void exitLobby() {
        server.lobbyManager.removePlayerFromLobby(server.playerInfo.get(username));
    }


    /*
     * Message looks like:
     *         new Message("(WHO START THE GAME)", Type.StartGame)
     *         new Message("(WHO BET), (AMOUNT)", Type.Bet)
     *         new Message("(WHO HIT)", Type.Hit)
     *         new Message("(WHO STAY)", Type.Stay)
     */
    public void send(Message msg) {
        // extract username from msg
        String data = (String) msg.getData();
        String[] line = data.split(",");
        String player = line[0];

        System.out.println("Action made by: " + player);

        // found which lobby player in
        int lobbyIndex = server.lobbyManager.lobbyIndex(server.playerInfo.get(player));
        System.out.println("Lobby index: " + lobbyIndex);
        System.out.println();

        // send this update to all clients in lobby where player is
        server.lobbyManager.lobbies.get(lobbyIndex).send(msg);
    }

    /*
     * save the changed file to userData.txt
     * looks like this:
     *        new Message(Type.Logout)
     */
    public void saveChangeToFile() {
        server.saveUserData();
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
                        System.out.println("[Verifying Login Info...]");
                        oos.writeObject(validateLogin(msg));
                        break;

                    case ViewPlayerInfo:
                        System.out.println("[Requesting PlayerInfo...]");
                        oos.writeObject(getUserInfo());
                        System.out.println("[UserInfo Sent]\n");
                        break;

                    case ViewAllPlayerInfo:
                        System.out.println("[Requesting AllPlayerInfo...]");
                        oos.writeObject(getAllUserInfo());
                        System.out.println("[AllUserInfo Sent]\n");
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

                    case UpdateBalance:
                        System.out.println("[Update Balance for User...]");
                        updateBalance(msg);
                        System.out.println("[Balance Updated]\n");

                    case GetBalance:
                        System.out.println("[Requesting Balance...]");
                        oos.writeObject(getBalance(msg));
                        System.out.println("[Balance Sent]\n");
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
                            System.out.println("[Delete Failed - Lobby Not Found]\n");
                        }
                        break;

                    case StartGame:
                        System.out.println("[game command - StartGame]");
                        send(msg);
                        break;

                    case Hit:
                        System.out.println("[game command - Hit/Draw]");
                        send(msg);
                        break;

                    case Bet:
                        System.out.println("[game command - Bet]");
                        send(msg);
                        break;

                    case Stay:
                        System.out.println("[game command - Stay]");
                        send(msg);
                        break;

                    case Exit:
                        System.out.println("[Exiting Game...]");
                        exitLobby();
                        System.out.println("[Game Exited...]\n");
                        break;

                    case Logout:
                        System.out.println("[Disconnecting...]");
                        server.numOfClientsOnline--;
                        socketIsOpen = false;
                        saveChangeToFile();
                        System.out.println("[Disconnected]");
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
