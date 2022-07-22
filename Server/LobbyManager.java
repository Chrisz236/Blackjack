import java.util.*;

public class LobbyManager {
    private ArrayList<Lobby> lobbies;
    public int numOfLobbies;

    public LobbyManager() {
        lobbies = new ArrayList<>();
        numOfLobbies = 0;
    }

    public String displayLobbies() {
        StringBuilder lobbiesName = new StringBuilder();
        if(lobbies.size() > 0) {
            for (Lobby lobby : lobbies) {
                lobbiesName.append(lobby.getLobbyName()).append("\n");
            }
        } else {
            return "[NO LOBBY]";
        }
        return lobbiesName.toString();
    }

    public LobbyStatus getLobbyStatus(String lobbyName) {
        for (Lobby lobby : lobbies) {
            if(lobby.getLobbyName().equals(lobbyName)) {
                return lobby.getLobbyStatus();
            }
        }
        return null;
    }

    public String viewLobby(String lobbyName) {
        for (Lobby lobby : lobbies) {
            if(lobby.getLobbyName().equals(lobbyName)) {
                return lobby.displayClientNames();
            }
        }
        return "[LOBBY NOT FOUND]";
    }

    public void createLobby(String lobbyName) {
        Lobby lobby = new Lobby(lobbyName);
        lobbies.add(lobby);
        numOfLobbies++;
    }

    public boolean deleteLobby(String lobbyName) {
        if(numOfLobbies == 0)
            return false;
        for(int i = 0 ; i < lobbies.size(); i++) {
            if(lobbies.get(i).getLobbyName().equals(lobbyName)){
                lobbies.remove(i);
                numOfLobbies--;
                return true;
            }
        }
        return false;
    }

    public void addPlayerToLobby(String lobbyName, Player player) {
        for (Lobby lobby : lobbies) {
            if (lobby.getLobbyName().equals(lobbyName))
                lobby.connectClient(player);
        }
    }
}