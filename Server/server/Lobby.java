package server;
import java.util.ArrayList;

public class Lobby {
    private String lobbyName;
    private Player dealer;
    private static ArrayList<Player> players;
    public boolean isEmpty;
    private LobbyStatus lobbyStatus;
    public int numOfPlayers;
    public BlackJack blackjack;

    public Lobby(String lobbyName) {
        players = new ArrayList<>();
        this.dealer =  new Player();
        this.lobbyStatus = LobbyStatus.Open;
        this.lobbyName = lobbyName;
    }

    public void connectClient(Player player) {
        players.add(player);
        numOfPlayers++;
        final int MAXIMUM_CLIENT = 50;
        if (numOfPlayers == MAXIMUM_CLIENT) {
            this.lobbyStatus = LobbyStatus.Full;
        }
    }

    public void disconnectClient(Player player) {
        players.remove(player);
        numOfPlayers--;
        if (numOfPlayers == 0) {
            isEmpty = true;
        }
    }

    public String displayClientNames() {
        String s = "";
        for (Player player : players) {
            s += player.username + "\n";
        }
        return s;
    }

    public boolean playerExist(Player player) {
        return players.contains(player);
    }

    public LobbyStatus getLobbyStatus() {
        return this.lobbyStatus;
    }

    public String getLobbyName() {
        return this.lobbyName;
    }

    public boolean setDealer(Player player) {
        if(this.dealer.username.equals("[Undefined]")){
            this.dealer = player;
            return true;
        }
        return false;
    }

    public void startGame() {
        blackjack = new BlackJack(dealer, players);
        blackjack.start();
    }
}

enum LobbyStatus {
    Open,
    Full
}
