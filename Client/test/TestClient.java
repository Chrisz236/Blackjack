package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import client.Client;

public class TestClient {
	Client client = new Client("localhost");
	
	@Test
    public void testConstructor() {
		assertTrue(client.socketOpen);
		assertTrue(client.userRole.equals("DEALER") || client.userRole.equals("PLAYER"));
	}
}