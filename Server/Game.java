import java.net.*;
import java.util.ArrayList;

public class Game implements Runnable{
    private Socket cSocket;
    private ArrayList<ServerThread> clients;

    public Game(Socket cSocket, ArrayList<ServerThread> clients){
        this.cSocket = cSocket;
        this.clients = clients;
    }

    @Override
    public void run() {
        // Actual game playing
    }
}
