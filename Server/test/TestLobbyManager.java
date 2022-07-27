package test;

import server.LobbyManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestLobbyManager {
    LobbyManager lobbyManager = new LobbyManager();

    @Test
    public void testDefaultConstructor() {
        assertEquals(0, lobbyManager.numOfLobbies);
        assertNotNull(lobbyManager.lobbies);
    }

    @Test
    public void testCreateLobby() {
        lobbyManager.createLobby("Lobby 1");
        assertEquals(1, lobbyManager.numOfLobbies);
    }

    @Test
    public void testDisplayLobbies() {
        assertEquals("[NO LOBBY]", lobbyManager.displayLobbies());
        lobbyManager.createLobby("Lobby 1");
        assertEquals("Lobby 1, ", lobbyManager.displayLobbies());
    }

    @Test
    public void testViewLobby() {
        assertEquals("[LOBBY NOT FOUND]", lobbyManager.viewLobby("Lobby 1"));
    }

    @Test
    public void testDeleteLobby() {
        assertFalse(lobbyManager.deleteLobby("Lobby 1"));
    }
}
