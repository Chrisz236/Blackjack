import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void HasMoney() {
		Player player=new Player("amanuel",5000);
		
		assertFalse(player.getHasNoMoney());
	}

}
