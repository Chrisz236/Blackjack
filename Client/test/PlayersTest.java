package test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



public class PlayersTest {
	
	
	
	




	     @Test
	      public void ConstructorTest() {
		Player player=new Player("amanuel",5000);
		
		assertAll("check Default constructor initilization", () -> assertNotNull(player.getHand()), () -> assertEquals("amanuel", player.getName()),
				() -> assertEquals(5000, player.getBalance()),() -> assertEquals(0, player.handCounter),() -> assertFalse( player.getHasNoMoney()),
				() -> assertFalse( player.HasLost()),()-> assertEquals(0, player.getBet()),()->assertFalse( player.getHitOrpass()));
	     }

		@Test
		public void HasMoneyTest() {
			Player player=new Player("amanuel",5000);
			
			assertFalse(player.getHasNoMoney());
		}
		
		

		@Test
		public void UpdateBalanceTest() {
			Player player=new Player("amanuel",5000);
			player.UPDATEBalance(4000);
			assertEquals(9000, player.getBalance());
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
			
			
			
			
			
			assertAll("check handcounter first",
					() -> {
			
					  assertEquals(player.handCounter,3);

					  assertAll("Check hand", () -> assertEquals("A1", temp[0]), () -> assertEquals("K2", temp[1]),
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



