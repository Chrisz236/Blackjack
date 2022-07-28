import static org.junit.jupiter.api.Assertions.*;

//import org.junit.jupiter.api.Test;
import org.junit.Test;

class DealerTest {

	 @Test
     public void ConstructorTest() {
	Dealer dealer=new Dealer("amanuel");
	
	assertAll("check  constructor initilization", () -> assertNotNull(dealer.getHand()), () -> assertEquals("amanuel", dealer.getName()),
			() -> assertEquals(0, dealer.handCounter));
	 }

	 @Test
	 public void HandTest() {
		 Dealer dealer=new Dealer("amanuel");
		 dealer.setHand("A1");
		 dealer.setHand("Q3"); 
		 
		 String hand[]=dealer.getHand();
		 assertAll("check Default constructor initilization", () -> assertEquals("A1", hand[0]),
					() -> assertEquals("Q3", hand[1]));
			 }

		 
	 
}
