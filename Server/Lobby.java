import java.util.ArrayList;

public class Lobby {
    private String lobbyName;
    // private Dealer dealer;
    private ArrayList<Player> players;
    private boolean isGameOver;
    private boolean isEmpty;
    final private int MAXIMUM_CLIENT = 50;
    private LobbyStatus lobbyStatus;
    public int numOfPlayers;
    private Game game;

    public Lobby(String lobbyName) {
        players = new ArrayList<>();
        this.lobbyStatus = LobbyStatus.Open;
        this.lobbyName = lobbyName;
    }

    public void connectClient(Player player) {
        players.add(player);
        numOfPlayers++;
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

    public void bet(Player player, int amount) {

    }

    public void setLobbyStatus(LobbyStatus lobbyStatus) {
        this.lobbyStatus = lobbyStatus;
    }

    public LobbyStatus getLobbyStatus() {
        return this.lobbyStatus;
    }

    public String getLobbyName() {
        return this.lobbyName;
    }
}

enum LobbyStatus {
    Open,
    Full
}
