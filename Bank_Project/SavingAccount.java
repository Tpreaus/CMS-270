
public class SavingAccount extends Account {
	
	//Data members
	private static final double MIN_BAL = 100.00;
	
	private static final double MAINTFEE = 5.00;
	
	private static final double OVERFEE = 60.00;
	
	private boolean overDraw = false;
	
	private boolean belowMin = false;
	
////////////////////////////////////////////////////////////////
	
	// Constructor
	public SavingAccount (String o, int ac, double bal) {
		// Use setters to access private data members in account
		this.setOwner(o); 
		this.setAccountNum(ac);
		this.setBalance(bal);
		
	}
////////////////////////////////////////////////////////////////
	// Getters + Setters
	
	public static double getMinBal() {
		return MIN_BAL;
	}

	public double getMaintFee() {
		return MAINTFEE;
	}

	public double getOverFee() {
		return OVERFEE;
	}
	
	public boolean isOverDraw() {
		return overDraw;
	}

	public void setOverDraw(boolean overDraw) {
		this.overDraw = overDraw;
	}
	
	public boolean isBelowMin() {
		return belowMin;
	}

	public void setBelowMin(boolean belowMin) {
		this.belowMin = belowMin;
	}
	
	
////////////////////////////////////////////////////////////////

	// Methods
	
	public void newMonth() {
		overDraw = false;
		belowMin = false;
	}

	@Override
	public void withdrawl(double amount) {
		
		// Change balance by amount entered
		double balance = getBalance();
		setBalance((balance - amount));
		
		// Check if below Min_Bal
		if (getBalance() < MIN_BAL && belowMin == false) {
			balance = getBalance();
			setBalance(balance - MAINTFEE);
			belowMin = true;
		}
		// Check if negative
		if (getBalance() < 0.00 && overDraw == false) {
			balance = getBalance();
			setBalance(balance - OVERFEE);
			overDraw = true;
		}
		
	}

	@Override
	public void deposit(double amount) {
		
		// Change balance by amount entered
		double balance = getBalance();
		setBalance((balance + amount));
		
		// Check if below Min_Bal
		if (getBalance() < MIN_BAL && belowMin == false) {
			balance = getBalance();
			setBalance(balance - MAINTFEE);
			belowMin = true;
		}
		
	}

	@Override
	public void transfer(Account x, double amount) {
		
		// Subtract amount from Current balance
		double balance = getBalance();
		setBalance((balance - amount));
		
		// Add amount to destination (x)
		double xBal = x.getBalance();
		x.setBalance((xBal + amount));
		
		// Check for Maint fee and Overdraft
		
		if (getBalance() < MIN_BAL && belowMin == false) {
			balance = getBalance();
			setBalance(balance - MAINTFEE);
			belowMin = true;
		}
		
		if (getBalance() < 0.00 && overDraw == false) {
			balance = getBalance();
			setBalance(balance - OVERFEE);
			overDraw = true;
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getAccountNum() + " S " + getOwner() + " " + String.format("%.2f", getBalance()));
		return sb.toString();
		}
	
////////////////////////////////////////////////////////////////
}
