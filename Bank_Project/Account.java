
public abstract class Account {
	
	// Data Members
	private String owner;
	private int accountNum;
	private double balance;
	private boolean active = true;
	
	// No constructor because abstract 

////////////////////////////////////////////////////////////////

	// Getter and setters
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public int getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(int acountNum) {
		this.accountNum = acountNum;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
////////////////////////////////////////////////////////////////
	
	// Methods 
	
	public abstract void newMonth();
	
	public abstract void withdrawl(double amount);
	
	public abstract void deposit(double amount);
	
	public abstract void transfer(Account x, double amount);
	
	// Works on any account type doesn't matter since all just need owner and bal
	public void close(String x) {
		// Check if owner is correct
		if (x.equals(owner)) {
			//Check for positive Balance
			if (balance > 0) {
				active = false;
				return;
			} else {
				System.out.println("Account " + accountNum + " could not be closed since it has a negative balance");
				return;
			}
		}  
		System.out.println("Account " + accountNum + " could not be closed since it owner is not present");
		return;
	}
////////////////////////////////////////////////////////////////
}
