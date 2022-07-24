package server;
/*
    Author: Haolin Zhang
    File: Lobby.java
    Date: July 19, 2022
    Ver: 1.5

    Description: Lobby holds players in same game
 */

import java.io.IOException;
import java.util.ArrayList;

public class Lobby {
    private String lobbyName;
    private static ArrayList<Player> players;
    public boolean isEmpty;
    private LobbyStatus lobbyStatus;
    public int numOfPlayers;

    /*
     * default constructor for Lobby class
     */
    public Lobby(String lobbyName) {
        players = new ArrayList<>();
        this.lobbyStatus = LobbyStatus.Open;
        this.lobbyName = lobbyName;
    }

    /*
     * add player to current connected player list
     */
    public void connectClient(Player player) {
        players.add(player);
        numOfPlayers++;
        final int MAXIMUM_CLIENT = 5;
        if (numOfPlayers == MAXIMUM_CLIENT) {
            this.lobbyStatus = LobbyStatus.Full;
        }
    }

    /*
     * remove the player from current lobby room
     */
    public void disconnectClient(Player player) {
        players.remove(player);
        numOfPlayers--;
        if (numOfPlayers == 0) {
            isEmpty = true;
        }
    }

    /*
     * return a String contains every player's name in this game
     */
    public String displayClientNames() {
        String s = "";
        for (Player player : players) {
            s += player.username + ", ";
        }
        return s;
    }

    /*
     * return true if given player is existed in current lobby room
     */
    public boolean playerExist(Player player) {
        return players.contains(player);
    }

    /*
     * return the status of the current lobby room
     */
    public LobbyStatus getLobbyStatus() {
        return this.lobbyStatus;
    }

    /*
     * return the name of the current lobby room
     */
    public String getLobbyName() {
        return this.lobbyName;
    }

    /*
     * broadcast the msg to every client connected to current lobby room
     */
    public void send(Message msg) {
        try{
            for(Player player : players) {
                player.getOos().writeObject(msg);
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

enum LobbyStatus {
    Open,
    Full
}
