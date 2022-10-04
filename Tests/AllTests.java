import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.TestLobby;
import test.TestLobbyManager;
import test.TestPlayer;
import test.WindowTester;
import test.TestServer;
import test.TestServerDriver;
import test.PlayersTest;
import test.TestClient;
import test.TestClientDriver;
import test.DealerTest;

@RunWith(Suite.class)
@SuiteClasses({DealerTest.class,PlayersTest.class,TestLobby.class,TestLobbyManager.class,TestPlayer.class,TestServer.class
	, TestServerDriver.class, WindowTester.class, TestClientDriver.class, TestClient.class})
public class AllTests {

}