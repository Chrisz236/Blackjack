import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable {

    String hostname;
    int port;
    private static String fileName = "Server/userFile.txt";
    Map<String, Account> clientInfo = new HashMap<>();
    // LobbyManager lobbyManager = new LobbyManager();

    boolean online = false;

    public Server(String host, int port) {
        this.hostname = host;
        this.port = port;
        System.out.println(this.toString());
        loadUserData();
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

    public void loadUserData() {
        // localData[0] = USERNAME
        // localData[1] = PASSWORD
        // localData[2] = BALANCE
        try {
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

    public void newConnections() {

    }

    public String toString() {
        return String.format("Server is running on: \'%s\', at port \'%d\'", hostname, port);
    }

    @Override
    public void run() {
        while (online) {
            newConnections();
        }
    }
}
