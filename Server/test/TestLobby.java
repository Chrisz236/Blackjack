package test;

import server.Lobby;
import org.junit.jupiter.api.Test;
import server.Player;

import static org.junit.jupiter.api.Assertions.*;

public class TestLobby {
    Lobby lobby = new Lobby("Lobby 1");

    @Test
    public void testDefaultConstructor() {
        assertNotNull(lobby);
    }

    @Test
    public void testConnectClient() {
        Player player = new Player();
        lobby.connectClient(player);
        assertEquals(1, lobby.numOfPlayers);
        assertFalse(lobby.isEmpty);
    }

    @Test
    public void testDisplayClientNames() {
        assertEquals("", lobby.displayClientNames());
    }

    @Test
    public void testPlayerExist() {
        Player player = new Player();
        assertFalse(lobby.playerExist(player));
    }
}
