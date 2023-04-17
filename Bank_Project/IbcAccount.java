
public class IbcAccount extends Account {
	
	//Data members
	private static final double MIN_BAL = 2000;
	
	private static final double MAINTFEE = 20.00;
	
	private static final double OVERFEE = 25.00;
	
	private int checksLeft = 40;
	
////////////////////////////////////////////////////////////////
	
	// Constructors
	
	public IbcAccount(String o, int ac, double bal) {
		// Use setters to access private data members in account
		this.setOwner(o); 
		this.setAccountNum(ac);
		this.setBalance(bal);
		
	}
	
////////////////////////////////////////////////////////////////
	
	// Getter + Setter

	public static double getMinBal() {
		return MIN_BAL;
	}

	public double getMaintFee() {
		return MAINTFEE;
	}

	public double getOverFee() {
		return OVERFEE;
	}
	
////////////////////////////////////////////////////////////////
	
	// Methods
	
	public void newMonth() {
		checksLeft = 40;
	}
	
	@Override
	public void withdrawl(double amount) {
		// Check for checks
		if (checksLeft > 0) {
			checksLeft -= 1;
			// Change balance by amount entered
			double balance = getBalance();
			setBalance((balance - amount));
		
			// Check if below Min_Bal
			if (getBalance() < MIN_BAL) {
				balance = getBalance();
				setBalance(balance - MAINTFEE);
			}
			// Check if negative
			if (getBalance() < 0.00) {
				balance = getBalance();
				setBalance(balance - OVERFEE);
			}
		} else
			System.out.println(getAccountNum() + " Out of Checks");		
	}

	@Override
	public void deposit(double amount) {
		if (checksLeft > 0) {
			checksLeft -= 1;
			// Change balance by amount entered
			double balance = getBalance();
			setBalance((balance + amount));
			
			// Check if below Min_Bal
			if (getBalance() < MIN_BAL) {
				balance = getBalance();
				setBalance(balance - MAINTFEE);
			}
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
			
			// Check for Maint fee and Overdraft
			
			if (getBalance() < MIN_BAL) {
				balance = getBalance();
				setBalance(balance - MAINTFEE);
			}
			
			if (getBalance() < 0.00) {
				balance = getBalance();
				setBalance(balance - OVERFEE);
			}
		} else
			System.out.println(getAccountNum() + " Out of Checks");
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getAccountNum() + " I " + getOwner() + " " + String.format("%.2f", getBalance()));
		return sb.toString();	}
////////////////////////////////////////////////////////////////
	
}
