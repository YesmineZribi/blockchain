/**
 * Name: Yesmine Zribi
 * Student number: 8402454
 * Class: CSI2510
 * Assignment#2
 * 
 * This class creates a transaction object 
 * @author Yesmine Zribi (JZRIB059@uottawa.ca)
 *
 */
public class Transaction {
	
	/**
	 *Stores the id of the sender
	 */
	private String sender;
	
	/**
	 *Stores the id of the receiver
	 */
	private String receiver; 
	
	/**
	 *Stores the amount exchanged
	 */
	private int amount;

	
	/**
	 *Constructor 1: initializes this transaction
	 * @param the sender of amount
	 * @param the receiver of the amount
	 * @param the amount exchanged
	 */
	public Transaction(String sender, String receiver, int amount) {
		this.sender = sender; 
		this.receiver = receiver; 
		this.amount = amount;
	}

	/**
	 *Constructor 2: initializes this transaction with empty variables
	 */
	public Transaction() {
		
	}


	/**
	 * Getter for the sender 
	 *@return the sender of this transaction
	 */
	public String getSender() {
		return sender;
	}



	/**
	 * Setter for the sender
	 * @param a sender id
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}



	/**
	 * Getter for the receiver 
	 * @return the receiver of this transaction 
	 */
	public String getReceiver() {
		return receiver;
	}



	/**
	 * Setter for the receiver
	 * @param a receiver id
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}



	/**
	 * Getter for the amount 
	 * @return the amount exchanged in this transaction 
	 */
	public int getAmount() {
		return amount;
	}



	/**
	 * Setter for the amount 
	 * @param the amount to exchange
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}



	/**
	 * String representation of this object 
	 * @return the string representation of the transaction
	 */
	  public String toString() {

		   return sender + ":" + receiver + "=" + amount;

		  }
}