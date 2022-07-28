import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({DealerTest.class,PlayersTest.class,TestLobby.class,TestLobbyManager.class,TestPlayer.class,TestServer.class
	, TestServerDriver.class, WindowTester.classTestClient.class,TestClientDriver.class})
public class AllTests {

}
