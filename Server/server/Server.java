package server;
/*
  Author: Haolin Zhang
  File:   Server.java
  Date:   July 11, 2022
  Ver:    1.3

  Description:
          Server class as the main process for all threads
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private final String hostname;
    private final int port;
    private final boolean online;
    public int numOfClientsOnline;
    public Map<String, Player> playerInfo = new HashMap<>();
    public LobbyManager lobbyManager;

    /*
     * default constructor of Server class
     */
    public Server(String host, int port) {
        this.hostname = host;
        this.port = port;
        this.online = true;
        this.numOfClientsOnline = 0;
        lobbyManager = new LobbyManager();
        loadUserData();
    }

    /*
     *  load userFile.txt to Map clientInfo
     *  associate Key: String username, Value: Account account
     *  initialize clientInfo
     *  localData[0] = USERNAME
     *  localData[1] = PASSWORD
     *  localData[2] = BALANCE
     *  localData[3] = DEALER/PLAYER
     */
    public void loadUserData() {
        try {
            String fileName = "Server/userFile.txt";
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String textLine;
            while ((textLine = br.readLine()) != null) {
                String[] localData = textLine.split(",");
                Player player = new Player(localData[0], localData[1],
                        Integer.parseInt(localData[2].trim()));
                if(localData[3].trim().equals("DEALER")){
                    player.isDealer = true;
                }
                playerInfo.put(localData[0], player);
            }
            fr.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * return basic information of current connection
     */
    public String toString() {
        return String.format("[Server is running on: \'%s\', at port \'%d\']", hostname, port);
    }

    /*
     * main entrance of the whole server
     */
    public void run() {
        ServerSocket serverSocket;
        System.out.println(this.toString());
        System.out.println("[WAITING FOR CONNECTION...]");
        try{
            serverSocket = new ServerSocket(this.port);
            while (online) {
                Socket cSocket = serverSocket.accept();
                ServerThread newThread = new ServerThread(cSocket, this);
                new Thread(newThread).start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
