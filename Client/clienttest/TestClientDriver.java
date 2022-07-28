package clienttest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import clientdriver.ClientDriver;

public class TestClientDriver {
	ClientDriver clientDriver = new ClientDriver();

    @Test
    public void testClientDriver() {
        assertNotNull(clientDriver);
    }
}
