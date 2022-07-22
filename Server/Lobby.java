import java.util.ArrayList;

public class Lobby {
    private String lobbyName;
    // private Dealer dealer;
    private ArrayList<Player> players;
    private boolean isGameOver;
    private boolean isEmpty;
    private LobbyStatus lobbyStatus;
    private int numOfPlayers;
    private Game game;

    public Lobby(String lobbyName) {
        this.lobbyStatus = LobbyStatus.Open;
        this.lobbyName = lobbyName;
    }

    public void connectClient(Player player) {

    }

    public void disconnectClient(Player player) {

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
    Full,
    Closed
}
