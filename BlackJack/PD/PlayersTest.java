import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class PlayersTest {



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
		
		@Test
		public void SetHandTest() {
			Player player=new Player("amanuel",1000);
			player.setHand("A1");
			player.setHand("K2");
			player.setHand("Q1");
			String temp[]=player.getHand();
			
			
			
			
			
			assertAll("check if empty",
					() -> {
			
					  assertEquals(player.handCounter,3);

					  assertAll("dvd Default constructor initilization", () -> assertEquals("A1", temp[0]), () -> assertEquals("K2", temp[1]),
							() -> assertEquals("Q1", temp[2]));

				});
		}
		
		
		@Test
		public void CheckBalanceAfterBetTest() {
			Player player1 =new Player("amanuel",1000);
			player1.BetAmount(100);
			assertEquals(900, player1.getBalance());
		}
	}



