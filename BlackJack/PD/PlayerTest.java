import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void HasMoney() {
		Player player=new Player("amanuel",5000);
		
		assertFalse(player.getHasNoMoney());
	}
	
	@Test
	public void HasLost() {
		Player player=new Player("amanuel",5000);
		player.setHasLost(true);
		assertTrue(player.HasLost());
	}
	@Test
	public void HasNoMoney() {
		Player player=new Player("amanuel",-1000);
		
		assertTrue(player.getHasNoMoney());
	}
	
	
}
