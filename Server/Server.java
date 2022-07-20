import java.io.*;
import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.util.*;

public class Server {
    private String hostname;
    private int port;
    private boolean online;
    private int numOfClient;
    Map<String, Account> clientInfo = new HashMap<>();
    // LobbyManager lobbyManager = new LobbyManager();

    public Server(String host, int port) {
        this.hostname = host;
        this.port = port;
        this.online = true;
        this.numOfClient = 0;
        loadUserData();
    }

    /*
     *  load userFile.txt to Map clientInfo
     *  associate Key: String username, Value: Account account
     *  initialize clientInfo
     *  localData[0] = USERNAME
     *  localData[1] = PASSWORD
     *  localData[2] = BALANCE
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
                Account tmpAcc = new Account(localData[0], localData[1],
                        Integer.parseInt(localData[2].trim()));
                clientInfo.put(localData[0], tmpAcc);
            }
            fr.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getPlayerBalance(String username) {
        return clientInfo.get(username).getBalance(username);
    }

    public void setPlayerBalance(String username, int amount) {
        clientInfo.get(username).setBalance(amount);
    }
    public Map<String, Account> getClientInfo() {
        return clientInfo;
    }

    public String toString() {
        return String.format("[Server is running on: \'%s\', at port \'%d\']", hostname, port);
    }

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
                this.numOfClient++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
