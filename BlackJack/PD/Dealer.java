
public class Dealer {
	
	private String hand[];
	private String Name;
	static int handCounter;
	
	
	
	
	
	public Dealer(String name) {
		hand= new String[15];
		setName(name);
		handCounter=0;
		
		
	}
	
	public String[] getHand() {
		return hand;
	}


	public void clearHand() {
		hand= new String[15];
	}


	public void setHand(String hand) {

		this.hand[handCounter]=hand;
		handCounter++;
	}




	public String getName() {
		return Name;
	}




	public void setName(String name) {
		Name = name;
	}
	



}
