import java.util.*;

public class LobbyManager {
    private ArrayList<Lobby> lobbies;
    private Map<String, Integer> lobbyIndex;
    public int numOfLobbies;

    public LobbyManager() {
        lobbies = new ArrayList<>();
        lobbyIndex = new HashMap<>();
        numOfLobbies = 0;
    }

    public String displayLobbies() {
        String lobbiesName = "";
        if(lobbies.size() > 0) {
            for(int i = 0; i < lobbies.size(); i++) {
                lobbiesName += lobbies.get(i).getLobbyName() + "\n";
            }
        } else {
            return "[NO LOBBY]";
        }
        return lobbiesName;
    }

    public LobbyStatus getLobbyStatus(String lobbyName) {
        return lobbies.get(lobbyIndex.get(lobbyName)).getLobbyStatus();
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

    public void addPlayerToLobby(Player player, String lobbyName) {

    }
}