
public class Player {
	
	
	private boolean hasNoMoney;
	private boolean hasLost;
	private String hand[];
	private String Name;
	private float balance;
	static int handCounter;
	private float bet;
	private boolean hitOrpass;
	
	
	public Player(String name, float balance) {
		hand=new String[15];
		setName(name);
		setBalance(balance);
		handCounter=0;
		hasNoMoney=false;
		hasLost=false;
		bet=0;
		hitOrpass=false;
		setHasNoMoney();
		
	}



	public boolean getHasNoMoney() {
		return hasNoMoney;
	}



	public void setHasNoMoney() {
		if(getBalance()<=0) {
			hasNoMoney=true;
		}
		
		else
			hasNoMoney= false;
	}



	public boolean HasLost() {
		return hasLost;
	}



	public void setHasLost(boolean hasLost) {
		this.hasLost = hasLost;
	}



	public String[] getHand() {
		return hand;
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



	public float getBalance() {
		return balance;
	}



	public void setBalance(float balance) {
		this.balance = balance;
	}
	

	public void UPDATEBalance(double balance) {
		this.balance += balance;
	}



	public float getBet() {
		return bet;
	}



	public void BetAmount(float bet) {
		this.bet = bet;
		setBalance(balance-bet);
	}



	public boolean getHitOrpass() {
		return hitOrpass;
	}



	public void Stay(boolean hitOrpass) {
		this.hitOrpass = hitOrpass;
	}
	
}
