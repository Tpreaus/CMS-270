 *  Process a batch of transactions
 *  
 *  Theo Preaus
 *  
 *	CMS 270
 */
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class BatchProcessor {

////////////////////////////////////////////////////////////////

	// Data Members
	
	private static int accountNum;
	
	private static double amount;
	
	private static String name;
	
	private static Account destAcct;
	
	private static String transactionType;
	
////////////////////////////////////////////////////////////////

	// Methods
	public static int findAccount(ArrayList<Account> x, int accountNum) {
		int length = x.size();
		boolean found = false;
		int i = 0;
		int index = -1;
		while (found == false && i < length) {
			if (x.get(i).getAccountNum() == accountNum) {
				index = i;
				found = true;
			}
			i += 1;
		}
		// If index is > -1 it was successful
		return index;
	}
	
	public static void interest(Account x) {
		// find the interest knowing the account entered must be a IBC
		double bal = x.getBalance();
		
		// For interest purpose make sure its positive 
		if (bal < 0) {
			System.out.println("Account: " + x.getAccountNum() + " recives no interest on a negative balance");
			return;
		}
		
		if (bal < 5000) {
			// 0.2% interest
			x.setBalance(bal * 1.002);
		} else if (bal < 20000) {
			// 1% interest
			x.setBalance(bal * 1.01);
		} else if (bal < 750000) {
			// 2% interest
			x.setBalance(bal * 1.02);
		} else if (bal >= 750000) {
			// 5% interest
			x.setBalance(bal * 1.05);
		}
	}
	
	public static ArrayList<Account> loadAccounts() {
		// Make arrayList
		ArrayList<Account> accounts = new ArrayList<>();
		// Read through acounts.txt and fill array
		try {
			Scanner fileReader = new Scanner(new File("accounts.txt"));
			
			//Loop through the entire file
			while (fileReader.hasNextLine()) {
				
				//Split the line
				String line = fileReader.nextLine();
				
				String[] words = line.split(" ");
				
				// Label the different parts of the line and change to required type
				
				int accountNum = Integer.parseInt(words[0]);
					
				
				String accountType = words[1];
					
				String accountOwner = (words[2] + ' ' +words[3]);
				
				double accountBal = Double.parseDouble(words[4]);
				
				
				// Create the corresponding AccountItem and add it to the list
				
				if (accountType.equals("S")) {
					accounts.add(new SavingAccount(accountOwner, accountNum, accountBal));
				} else if (accountType.equals("C")) {
					accounts.add(new CheckingAccount(accountOwner, accountNum, accountBal));
				} else if (accountType.equals("I")) { 
					accounts.add(new IbcAccount(accountOwner, accountNum, accountBal));
				}
				
			}
			// Close the scanner
			fileReader.close();
			
			
		} catch (FileNotFoundException e) {
			System.out.println("An error occured");
			e.printStackTrace();
		}
		
		return accounts;
		
	}
	
	public static Account proccessTransaction(Account x){
		
		Account y = x;
		
		if (transactionType.equals("W")) {
			y.withdrawl(amount);
		} else if (transactionType.equals("D")) {
			y.deposit(amount);
		} else if (transactionType.equals("T")) {
			y.transfer(destAcct, amount);
		} else if (transactionType.equals("C")) {
			y.close(name);
		}
		return y;
	}
	
	public static void updateAccounts(ArrayList<Account> accounts) {
		// Update Account.txt
		
	    try {
	    	// Clear the contents of accounts.txt
	    	new FileWriter("accounts.txt", false).close();
	    	// open FileWriter to write to file
	        FileWriter myWriter = new FileWriter("accounts.txt");
	        
	        // Loop through ArrayList of Accounts
	        for (int i = 0; i < accounts.size(); i++) {
	        	myWriter.write(accounts.get(i).toString());
	        	if (i != accounts.size() - 1) {
	        		myWriter.write("\n");
	        	}
	        }
	        // Close Writer
	        myWriter.close();
	        
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	}
	
	public static void resetMonth(ArrayList<Account> accounts) {
		// Reset Monthly Value
		for (int i = 0; i < accounts.size(); i++) {
			accounts.get(i).newMonth();
		}
	}

////////////////////////////////////////////////////////////////
	
	// Main
	public static void main(String[] args) {
		
		//Create arrayList of accounts to hold accounts
		ArrayList<Account> accounts = loadAccounts();;
		
		// Read the batch.txt
		try {
			Scanner fileReader = new Scanner(new File("batch.txt"));
			
			//Loop through the entire file
			while (fileReader.hasNextLine()) {
				
				// Split the line
				String line = fileReader.nextLine();
				
				String[] words = line.split(" ");
				
				// Check first letter to find correct transaction type
				
				transactionType = words[0];
				
				// W --> Withdrawal Param(Account Number, Amount, Name of Person)
				
				
				if (words[0].equals("W")) {
					
					accountNum = Integer.parseInt(words[1]);
					
					amount = Double.parseDouble(words[2]);
					
					name = (words[3] + " " + words[4]);
					
						//Search for Account using Account number 
					
						int acctIndex = findAccount(accounts, accountNum);
					
						if (acctIndex == -1) {
							System.out.println("Account Not Found");
							System.out.println(name + " could not withdrawl from account " + accountNum);
						} else {						
							// Check account name
							if (name.equals(accounts.get(acctIndex).getOwner())) {
								// If name equal perform withdraw
								accounts.set(acctIndex, proccessTransaction(accounts.get(acctIndex)));
							} else {
								System.out.println(name + " could not withdrawl from account " + accountNum);
							}

						}
					
				// D --> Deposit Param(Account Number, Amount)
					
				} else if (words[0].equals("D")) {
					
					accountNum = Integer.parseInt(words[1]);
					
					amount = Double.parseDouble(words[2]);
					
					// Search for account using account Number
					int acctIndex = findAccount(accounts, accountNum);
					
					if (acctIndex == -1) {
						System.out.println("Account Not Found funds not deposited");
					} else {
						accounts.set(acctIndex, proccessTransaction(accounts.get(acctIndex)));

					}

				// T --> Transfer Param(Account Number (Source), Account Number (destination), Amount, Name) 
					
				} else if (words[0].equals("T")) {
					
					accountNum = Integer.parseInt(words[1]);
					
					int destAcctIdex = Integer.parseInt(words[2]);
					
					amount = Double.parseDouble(words[3]);
					
					name = (words[4] + " " + words[5]);
					
						// Search for account using account Number

					int sourceAcctIndex = findAccount(accounts, accountNum);
					
					int destAcctIndex = findAccount(accounts, destAcctIdex);
					
					
					if (sourceAcctIndex == -1 || destAcctIndex == -1) {
						System.out.println("Account Invalid");
						System.out.println(name + " could not transfer funds from account " + accountNum);
					} else {
						
						// Make destAccount Acount object
						destAcct = accounts.get(destAcctIndex);
						// Check account name
						if (name.equals(accounts.get(sourceAcctIndex).getOwner())) {
							// If name equal perform withdraw
							accounts.set(sourceAcctIndex, proccessTransaction(accounts.get(sourceAcctIndex)));

						} else {
							System.out.println(name + " could not transfer funds from account " + accountNum);
						}

					}
					
					
				// C --> Close Param(Account Number, Name)
					
				} else if (words[0].equals("C")) {
					
					accountNum = Integer.parseInt(words[1]);
					
					name = (words[2] + " " + words[3]);
					
					// Search for account using account Number
					
					int acctIndex = findAccount(accounts, accountNum);
					
					if (acctIndex == -1) {
						System.out.println("Account Not valid can not be closed");
					} else {
						accounts.set(acctIndex, proccessTransaction(accounts.get(acctIndex)));
						// check to see if acount must be removed
						if (accounts.get(acctIndex).isActive() == false) {
							accounts.remove(acctIndex);
						}
					}		
				}
			}
			
			// Close Scanner
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("An error occured");
			e.printStackTrace();
		}
		
		// Calculate Interest
		for (int i = 0; i < accounts.size(); i++) {
			// Check if its a IBC
			if (accounts.get(i) instanceof IbcAccount) {
				// Calculate Interest
				interest(accounts.get(i));
			}
		}
		
		// Update Account.txt
		updateAccounts(accounts);
		
		// Reset the once a month amounts
		resetMonth(accounts);
		
	}

////////////////////////////////////////////////////////////////

}
