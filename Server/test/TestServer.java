package test;

import server.Server;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestServer {
    Server server = new Server("localhost", 7777);

    @Test
    public void testDefaultConstructor() {
        assertEquals(0, server.numOfClientsOnline);
        assertNotNull(server.playerInfo);
        assertNotNull(server.lobbyManager);
    }

    @Test
    public void testLoadUserData() {
        server.loadUserData();
        assertEquals(6, server.playerInfo.size());
    }

    @Test
    public void testSaveUserData() {
        server.saveUserData();
    }

    @Test
    public void testToString() {
        assertEquals("[Server is running on: 'localhost', at port '7777']", server.toString());
    }
}
