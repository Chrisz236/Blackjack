package test;

import server.ServerDriver;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestServerDriver {
    ServerDriver serverDriver = new ServerDriver();

    @Test
    public void testServerDriver() {
        assertNotNull(serverDriver);
    }
}
