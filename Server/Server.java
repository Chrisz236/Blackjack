import java.io.*;
import java.net.*;

public class Server implements Runnable {

    String hostname;
    int port;

    boolean online = true;

    public Server(String host, int port) {
        this.hostname = host;
        this.port = port;
        System.out.println(this.toString());
    }

    /*
     * function to validate user entered information
     * @param Message msg
     * @return Boolean true  for valid
     *                 false for invalid
     */
    public boolean validateLogin(Message msg) {
        String[] loginMsg = msg.getData().split(",");
        String username = loginMsg[0];
        String password = loginMsg[1];

        try (BufferedReader br = new BufferedReader(new FileReader("userFile.txt"))) {
            String textLine;
            while ((textLine = br.readLine()) != null) {
                String[] localData = textLine.split(",");
                if (username.equals(localData[0]) && password.equals(localData[1])) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("[userFile.txt not found]");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void newConnections() {

    }

    public String toString() {
        return String.format("Server is running on %s @port %d", hostname, port);
    }

    @Override
    public void run() {
        while (online) {
            newConnections();
        }
    }
}
