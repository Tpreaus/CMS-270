
public class CheckingAccount extends Account {
	
	// Data Members
	
	// Num of checks allowed in a month
	private int checksLeft = 20;
	
	private static final double OVERFEE = 20.00;
	
////////////////////////////////////////////////////////////////
	
	// Constructor
	public CheckingAccount (String o, int ac, double bal) {
		// Use setters to access private data members in account
		this.setOwner(o); 
		this.setAccountNum(ac);
		this.setBalance(bal);
		
	}
	
////////////////////////////////////////////////////////////////
	
	// Getters + Setters
	
	public int getChecksLeft() {
		return checksLeft;
	}
	
	public void setChecksLeft(int checksLeft) {
		this.checksLeft = checksLeft;
	}
	
	public double getOverFee() {
		return OVERFEE;
	}
	
////////////////////////////////////////////////////////////////
	
	// Methods
	
	public void newMonth() {
		checksLeft = 20;
	}
	
	@Override
	public void withdrawl(double amount) {
		// Check if there are checks left 
		if (checksLeft > 0) {
			checksLeft -= 1;
			// Change balance by amount entered
			double balance = getBalance();
			setBalance((balance - amount));

			// Check if overDrafted
			if (getBalance() < 0.00) {
				balance = getBalance();
				setBalance(balance - OVERFEE);
			}
		
		} else 
			System.out.println(getAccountNum() + " Out of Checks");		
	}

	@Override
	public void deposit(double amount) {
		// Check if Checks left
		if (checksLeft > 0) {
			checksLeft -= 1;
			// Change balance by amount entered
			double balance = getBalance();
			setBalance((balance + amount));
			
		} else 
			System.out.println(getAccountNum() + " Out of Checks");
	}
		
	@Override
	public void transfer(Account x, double amount) {
		if (checksLeft > 0) {
			checksLeft -= 1;
			// Subtract amount from Current balance
			double balance = getBalance();
			setBalance((balance - amount));
			
			// Add amount to destination (x)
			double xBal = x.getBalance();
			x.setBalance((xBal + amount));
			
			// Check for Overdraft
			if (getBalance() < 0.00) {
				balance = getBalance();
				setBalance(balance - OVERFEE);
			}
			
		} else 
			System.out.println(getAccountNum() + " Out of Checks");
		
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getAccountNum() + " C " + getOwner() + " " + String.format("%.2f", getBalance()));
		return sb.toString();	}
////////////////////////////////////////////////////////////////
	
}
