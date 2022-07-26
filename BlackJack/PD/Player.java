
public class Player {
	
	
	private boolean hasNoMoney;
	private boolean hasLost;
	private String hand[];
	private String Name;
	private double balance;
	static int handCounter;
	private double bet;
	private boolean hitOrpass;
	
	
	public Player(String name, double balance) {
		hand[15]=" ";
		setName(name);
		setBalance(balance);
		handCounter=0;
		hasNoMoney=false;
		hasLost=false;
		bet=0;
		hitOrpass=false;
		
	}



	public boolean getHasNoMoney() {
		return hasNoMoney;
	}



	public boolean setHasNoMoney() {
		if(getBalance()==0) {
			return false;
		}
		
		else
			return true;
	}



	public boolean isHasLost() {
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



	public double getBalance() {
		return balance;
	}



	public void setBalance(double balance) {
		this.balance = balance;
	}
	

	public void UPDATEBalance(double balance) {
		this.balance += balance;
	}



	public double getBet() {
		return bet;
	}



	public void BetAmount(double bet) {
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
