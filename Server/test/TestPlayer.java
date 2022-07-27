package test;

import server.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestPlayer {
    Player player = new Player();

    @Test
    public void testDefaultConstructor() {
        assertNotNull(player);
        assertEquals("[Undefined]", player.username);
        assertEquals("[Undefined]", player.password);
        assertEquals(-1, player.balance);
    }

    @Test
    public void testConstructor() {
        player = new Player("Player 1", "abc123", 1000);
        assertEquals("Player 1", player.username);
        assertEquals("abc123", player.password);
        assertEquals(1000, player.balance);
    }

    @Test
    public void testGetPassword() {
        assertEquals("[Undefined]", player.password);
    }

    @Test
    public void testGetBalance() {
        assertEquals(-1, player.balance);
    }

    @Test
    public void testAddBalance() {
        player.addBalance(1000);
        assertEquals(999, player.balance);
    }
}
