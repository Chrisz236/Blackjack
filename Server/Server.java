import java.io.*;
import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.util.*;

public class Server {
    private String hostname;
    private int port;
    private boolean online = false;
    private int numOfClient;
    Map<String, Account> clientInfo = new HashMap<>();
    // LobbyManager lobbyManager = new LobbyManager();

    public Server(String host, int port) {
        this.hostname = host;
        this.port = port;
        this.online = true;
        this.numOfClient = 0;
        loadUserData();
        System.out.println(this.toString());
    }

    /*
     * function to validate user entered information
     * @param Message msg
     * @return Boolean true  for valid
     *                 false for invalid
     */
    public boolean validateLogin(Message msg) {
        if (msg.getType().equals("login")) {
            String info = msg.getData();
            String[] line = info.split(",");
            String username = line[0];
            if (clientInfo.get(username).getPassword(username).equals(line[1])) {
                return true;
            }
        }
        return false;
    }

    /*
        load userFile.txt to Map clientInfo
        associate Key: String username, Value: Account account
        initialize clientInfo
     */
    public void loadUserData() {
        // localData[0] = USERNAME
        // localData[1] = PASSWORD
        // localData[2] = BALANCE
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

    public void newConnections() {

    }

    public String toString() {
        return String.format("Server is running on: \'%s\', at port \'%d\'", hostname, port);
    }

    public void run() {
        ServerSocket serverSocket;
        try{
            serverSocket = new ServerSocket(this.port);
            while (online) {
                Socket cSocket = serverSocket.accept();
                System.out.println("[NEW CLIENT CONNECTED]: " + cSocket);
                ServerThread newThread = new ServerThread(cSocket, this);
                this.numOfClient++;
                new Thread(newThread).start();
            }
        } catch (IOException e) {
            System.out.println("[CANNOT CREATE SOCKET]: " + e.getMessage());
        }

    }
}
