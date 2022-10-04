package test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import client.ClientDriver;

public class TestClientDriver {
	ClientDriver clientDriver = new ClientDriver();

    @Test
    public void testClientDriver() {
        assertNotNull(clientDriver);
    }
}
