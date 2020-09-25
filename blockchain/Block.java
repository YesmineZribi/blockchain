import java.sql.Timestamp;

/**
 * Name: Yesmine Zribi
 * Student number: 8402454
 * Class: CSI2510
 * Assignment#2
 * 
 * This class creates a block object
 * @author Yesmine Zribi (JZRIB059@uottawa.ca)
 *
 */
public class Block {

	/**
	 * Stores the index of this box
	 */
	private int index;
	
	
	/**
	 *Stores the timestamp this box was created
	 */
	private java.sql.Timestamp timestamp;

	/**
	 *Stores the transaction within this box
	 */
	private Transaction transaction;

	/**
	 *Stores the nonce of this box
	 */
	private String nonce;

	/**
	 *Stores the hash of the previous block in the blockchain
	 */
	private String previousHash;

	/**
	 *Stores the hashcode of this block
	 */
	private String hash;

	/**
	 *Construct 1: initializes this object
	 *@param the index of this block
	 *@param the transaction to be stored in this object
	 */
	public Block(int index, Transaction transaction) {
		this.index = index;
		this.transaction = transaction;
		timestamp = new Timestamp(System.currentTimeMillis());
	}

	/**
	 *Construct 2: initializes this object with empty variables
	 */
	public Block() {

	}

	/**
	 * Getter for the index
	 * @return the index of this block
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Setter for the index 
	 * @param the index variable
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
	/**
	 * Getter for the timestamp
	 * @return the timestamp of this block
	 *
	 */
	public java.sql.Timestamp getTimestamp() {
		return timestamp;
	}

	/**
	 * Setter for the timestamp
	 * @param the timestamp variable
	 *
	 */
	public void setTimestamp(long time) {
		this.timestamp = new Timestamp(time);
	}

	/**
	 * Getter for the transaction
	 * @return the transaction held in this block
	 */
	public Transaction getTransaction() {
		return transaction;
	}

	/**
	 * Setter for the transaction 
	 * @param the transaction variable
	 *
	 */
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	
	/**
	 * Getter for the nonce
	 * @return the nonce of this block
	 *
	 */
	public String getNonce() {
		return nonce;
	}

	/**
	 * Setter for the nonce
	 * @param the nonce variable
	 */
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	
	
	/**
	 * Getter for the previous hash 
	 * @return the hash of the previous block
	 */
	public String getPreviousHash() {
		return previousHash;
	}

	/**
	 * Setter for the previous hash
	 * @param the hash of the previous block
	 */
	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	/**
	 * Getter for the hash 
	 * @return the hash of this block
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * Setter for the hash 
	 * @param the hash variable
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}

	/**
	 * String representation of this block
	 * @return the string representation of this object 
	 */
	public String toString() {

		return timestamp.toString() + ":" + transaction.toString()

				+ "." + nonce + previousHash;

	}

}
