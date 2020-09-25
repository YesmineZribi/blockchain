import java.io.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Name: Yesmine Zribi
 * Student number: 8402454
 * Class: CSI2510
 * Assignment#2
 * 
 * This class creates a a blockchain  
 * @author Yesmine Zribi (JZRIB059@uottawa.ca)
 *
 */
public class BlockChain {
	/**
	 *Stores the blockchain
	 */
	private Block[] blockchain;
	
	/**
	 * Stores the size of the blockchain
	 */
	private int size;
	
	/**
	 * Stores the initialize capacity of the blockchain
	 */
	private final int INITIAL_CAPACITY = 3;
	
	/**
	 * Stores true if the filename is true and false otherwise
	 */
	private static boolean validFileName = false;
	
	
	/**
	 * Constructor: initializes the blockchain
	 */
	public BlockChain() {
		blockchain = new Block[INITIAL_CAPACITY];
		size = 0;
	}
	
	
	/**
	 * This method adds a block to the blockchain 
	 * @param the block to be added
	 */
	public void add(Block block) {
		if (size == blockchain.length) {
			Block[] newArray = new Block[size*2];
			for (int i = 0; i < size; i++) {
				newArray[i] = blockchain[i];
			}
			blockchain = newArray;
		}
		blockchain[size++] = block;
		
		int currentIndex = size - 1; //the block last added
		if (currentIndex == 0) { //this means there is no previous 
			block.setPreviousHash("00000");
		} else { //set this block's previous hash 
			Block previousBlock = get(currentIndex-1);
			block.setPreviousHash(previousBlock.getHash());
		}
	}
	
	
	/**
	 * This method gets the block at a certain index 
	 * @param the index 
	 * @return the block at index index
	 */
	public Block get(int i) {
		return blockchain[i];
	}
	
	/**
	 * This method returns the size of the blockchain 
	 * @return the size of the blockchain
	 */
	public int getSize() {
		return size; 
	}
	
	/**
	 * Setter for the blockchain 
	 * @param blockchain to set the blockchain variable with 
	 */
	public void setBlockChain(Block[] blockchain) {
		this.blockchain = blockchain;
		size = blockchain.length;
	}
	
	/**
	 * This method reads a file and creates a blockchain from it 
	 * @param the file 
	 * @return a blockchain
	 */
	public static BlockChain fromFile(String fileName) {
		BlockChain blockchain = new BlockChain();
		File file = new File(fileName);
		try {
			BufferedReader bf = new BufferedReader(new FileReader(file));
			
			String line; 
			boolean endOfFile = false; //Check if we reached end of file
			int i=0;
			while (!endOfFile) { 
				Block block = new Block(); //for each 7 lines create a block
				Transaction transaction = new Transaction(); //create transition for that block
				block.setTransaction(transaction); 
				while (i < 7) { //each block info occupies 7 lines in file
					line = bf.readLine(); //read next line
					switch(i) {
					case 0: //line 0 is the index
						block.setIndex(Integer.parseInt(line));
						break;
					case 1: //line 1 is the timestamp
						block.setTimestamp(Long.parseLong(line));
						break;
					case 2: //line 2 is the sender's name
						transaction.setSender(line);
						break;
					case 3: //line 3 is the receiver's name
						transaction.setReceiver(line);
						break;
					case 4: //line 4 is the amount
						transaction.setAmount(Integer.parseInt(line));
						break;
					case 5: //line 5 is the nonce value
						block.setNonce(line);
						break;
					case 6: //line 6 is the hash value
						block.setHash(line);
						break;
					}
					i++;
				} //At this point i is bigger than 7
				//Add block to the blockchain
				blockchain.add(block);

				// reset i so we can resume reading info of next block if end of file leave loop
				bf.mark(i); //mark the line we're at
				if (i == 7 && bf.readLine() != null) {
					i = 0;
					bf.reset();//go back to the line we didn't process yet
				} else {
					endOfFile = true;
				}
				
			}	
		bf.close();	
		validFileName = true;
		} catch (FileNotFoundException e1) {
			System.out.println("File not found");
			validFileName = false;
		} catch (IOException e2) {
			System.out.println("Could not read line");
			validFileName = false;
		}
		
		return blockchain;
	}


	/**
	 * This method transcribes a blockchain into a file 
	 * @param the file in which the blockchain will be written
	 */
	public void toFile(String fileName) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			for (int i = 0; i < size; i++) {
				Block block = blockchain[i];
				for (int j = 0; j < 7; j++) { //There are 7 attributes to look at for each block
					switch(j) {
					case 0: //write the index for each block
						int index = block.getIndex();
						writer.write(Integer.toString(index));
						writer.newLine();
						break;
					case 1: //write the timestamp for each block
						long time = block.getTimestamp().getTime();
						writer.write(Long.toString(time));
						writer.newLine();
						break;
					case 2: //write the sender's name for each block
						String sender = block.getTransaction().getSender();
						writer.write(sender);
						writer.newLine();
						break;
					case 3: //write the receiver's name for each block
						String receiver = block.getTransaction().getReceiver();
						writer.write(receiver);
						writer.newLine();
						break;
					case 4: //write the amount for each block
						int amount = block.getTransaction().getAmount();
						writer.write(Integer.toString(amount));
						writer.newLine();
						break;
					case 5: //write the none for each block
						String nonce = block.getNonce();
						writer.write(nonce);
						writer.newLine();
						break;
					case 6: //write the hashcode for each block
						String hash = block.getHash();
						writer.write(hash);
						writer.newLine();
						break;
					}
				}
				
			}
			writer.close();
		} catch (IOException  e) {
			System.out.println("Could not write to file");
		}
	}
	
	
	/**
	 * This method validates the blockchain 
	 * @return true if the blockchain is valid and false otherwise 
	 */
	public boolean validateBlockchain() throws UnsupportedEncodingException {
		
		for (int i = 0; i < size; i++) {
			// 1 . a. make sure the hash codes are right
			 String msg = blockchain[i].toString();
			 String expected = blockchain[i].getHash();
			 String result = Sha1.hash(msg,Sha1.OUT_HEX);
			 
			 if (!result.equals(expected)) {
				 System.out.println("result and expected not the same");
				 return false;
			 }
			 //1 . b. make sure all the hash codes start with five 0s
			 for (int index = 0; index < 5; index++) {
				 if (result.charAt(index) != '0') {
					 return false;
				 }
			 }
			 
			// 2 . Check index of each block corresponds to index in chain 
			 int j = blockchain[i].getIndex();
			 if (j != i) {
				 System.out.println("Not the right index");
				 return false;
			 }
			 
			// 3 . Check previousHash corresponds to the hash of previous block
			 String previousHash, hashOfPrevious;
			 previousHash = blockchain[i].getPreviousHash();
			 if (i == 0) {
				 hashOfPrevious = "00000"; 
			 } else {
				 hashOfPrevious = blockchain[i-1].getHash();
			 }
			 if (!previousHash.equals(hashOfPrevious)) {
				 System.out.println("Previous and current hash not the same");
				 return false;
			 }
			 
			 //4 . Check if timestamp is valid 
			 Timestamp time = blockchain[i].getTimestamp();
			 boolean timeIsValid = validateTimeStamp(time,i); //compare timestamp of this block with each previous block
			 if (!timeIsValid) {
				 System.out.println("The timestamps are not valid");
				 return false; 
			 }
			 
			 //5 . Check that sender of the first block is always bitcoin
			 String sender = blockchain[i].getTransaction().getSender(); //Get sender in current block
			 if (j == 0 && !sender.equals("bitcoin")) {
				 System.out.println("First sender is not bitcoin");
				 return false;
			 }
			 
			 //6 . Check if sender and receiver names are not the same 
			 String receiver = blockchain[i].getTransaction().getReceiver();
			 if (sender.equals(receiver)) {
				 System.out.println("This transaction's sender and receiver are the same");
				 return false;
			 }
			 
			 //7 . Check receiver is never bitcoin
			 if (receiver.equals("bitcoin")) {
				 System.out.println("Bitcoin can never be a receiver");
				 return false;
			 }
			 
			 //8 . Check if each spender did not spend more than he has and that amount is valid in the first place
			 int amount = blockchain[i].getTransaction().getAmount(); //Amount sender is spending
			 if (amount <= 0 ) {
				 System.out.println("Amount cannot be zero a negative value");
				 return false;
			 }
			 
			 int balance = getBalance(sender,i); //get balance of sender until this transaction
			 
			/*if amount is higher than balance then chainblock is not valid; exception for bitcoin in the first transaction
			 * bitcoin should not be involved in any other transaction, if so the chainblock will not be valid as he can never
			 * receive bitcoins and will be treated like any other sender
			 * */
			 if ((balance-amount) < 0 && !(sender.equals("bitcoin") && j == 0)) { 
				 System.out.println("Not enough balance for "+sender);
				 return false;
			 }
		 } //End of for loop
		return true;
	}
	
	
	/**
	 * This method compares time with the timestamps of all block up until the index stop
	 * @param the timestamp of the block at index stop 
	 * @param the index of the block we are comparing the previous blocks with 
	 * @return true if all timestamps previous to time are smaller than it and false otherwise
	 */
	private boolean validateTimeStamp(Timestamp time, int stop) {
		if (time == null || stop < 0 || stop > size) {
			throw new IllegalStateException("time cannot be null or stop is out of bounds");
			
		}
		//Go through all the blocks, verify the timestamp
		for (int i = 0; i < stop; i++) {
			if (blockchain[i].getTimestamp().compareTo(time) >= 0) { //if a previous block has a timestamp that isn't smaller the blockchain is not valid
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * This method returns the balance of sender previous to the block at index stop 
	 * @param the sender 
	 * @param the index of the last block to check 
	 * @return the balance of sender until before transaction stop
	 */
	private int getBalance(String username,int stop) { //Get balance of username up until the point stop
		if (username == null || stop < 0 || stop > size) {
			throw new IllegalStateException("username cannot be null or stop is out of bounds");
		}
		//Go through all the blocks, add bitcoins if receiver, subtract the amount if sender
		String sender, receiver; 
		int amount, sum;
		sum = 0;
		for (int i = 0; i < stop; i++) {
			sender = blockchain[i].getTransaction().getSender();
			receiver = blockchain[i].getTransaction().getReceiver();
			amount = blockchain[i].getTransaction().getAmount();
			if (sender.equals(username)) {
				sum = sum - amount;
			} else if (receiver.equals(username)) {
				sum = sum + amount;
			}
		}
		return sum;
	}
	
	
	/**
	 * This method returns the balance of username in total (until the end of the blockchain)
	 * @param the sender
	 * @return the balance of sender
	 */
	public int getBalance(String username) { //Get balance of username after all transactions
		if (username == null) {
			throw new IllegalStateException("username cannot be null");
		}
		//Go through all the blocks, add bitcoins if receiver, subtract the amount if sender
		String sender, receiver; 
		int amount, sum;
		sum = 0;
		for (int i = 0; i < size; i++) {
			sender = blockchain[i].getTransaction().getSender();
			receiver = blockchain[i].getTransaction().getReceiver();
			amount = blockchain[i].getTransaction().getAmount();
			if (sender.equals(username)) {
				sum = sum - amount;
			} else if (receiver.equals(username)) {
				sum = sum + amount;
			}
		}
		return sum;
	}
	
	
	/**
	 * This method returns the string representation of this object 
	 * @return the string representation of the blockchain
	 */
	public String toString() {
		return Arrays.toString(blockchain);
	}
	

	/**
	 * This method keeps prompting the user to answer yes or no
	 * @return the user's answer (which will only be yes or no)
	 */
	public static String promptUserForYesOrNo(String add) {
		Scanner userInput = new Scanner (System.in);
		while (!add.equals("yes") && !add.equals("no")) {
			System.out.println("I'm sorry I did not get that --");
			System.out.println("Please answer by yes or no");
			System.out.println("Would you like to enter a new transaction?(yes/no) ");
			add = userInput.nextLine().trim().toLowerCase();
		}
		return add;
	}
	
	/**
	 *This method keeps prompting the user to enter a valid transaction 
	 * @return a valid transaction
	 */
	//Prompt user for transaction 
	public static Transaction promptUserForTransaction() {
		Transaction transaction = new Transaction();
		Scanner userInput = new Scanner (System.in);
		
		//A. Ask for the sender
		System.out.print("Enter the sender's username: ");
		String sender = userInput.nextLine();
		sender = sender.trim().toLowerCase();
		//if user leaves the sender field empty or inputs bitcoin as sender ask again
		while (sender.isEmpty() || sender.equals("bitcoin")) { 
			System.out.println("sender name is not valid or field was left empty -- please try again");
			sender = userInput.nextLine().trim().toLowerCase();
			}
		

		
		//B. Ask for receiver
		System.out.print("Enter the receiver's username: ");
		String receiver = userInput.nextLine();
		receiver = receiver.trim().toLowerCase();
		//if user leaves the receiver field empty or enter bitcoin as receiver ask again
		while(receiver.isEmpty() || sender.equals("bitcoin")) {
			System.out.println("Receiver's field cannot be empty -- please try again");
			receiver = userInput.nextLine().trim().toLowerCase();
		}

		//if inputed sender and receiver are the same
		while (sender.equals(receiver)) {
			System.out.println("Sender and Receiver cannot be identical please change one or the other");
			System.out.println("Please enter a valid sender: ");
			sender = userInput.nextLine().trim().toLowerCase();
			System.out.println("Please enter a valid receiver");
			receiver = userInput.nextLine().trim().toLowerCase();	
		}
		transaction.setSender(sender); //add sender to transaction
		transaction.setReceiver(receiver); //add receiver to transaction
		
		//C. Ask for amount
		Integer amount = null;
		while (amount == null) {
			try {
				System.out.print("Please enter the bitcoin amount: ");
				String bitcoinAmount = userInput.nextLine();
				amount = Integer.parseInt(bitcoinAmount);
			} catch(NumberFormatException e) { 
				System.out.println("The amount you entered is not valid -- please try again");
				amount = null; //keep looping as long as the amount entered is not valid
			}
			
		}
		transaction.setAmount(amount); //at this point amount is valid, add it to transaction
		return transaction;
		
	}
 
	
	/**
	 * The main method of the program 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		Scanner userInput = new Scanner (System.in);

		//1. Reading the file, validating the file name, and adding it to the arrayList
		System.out.println("Please enter the name of the file: ");
		String name = userInput.nextLine().trim();
		System.out.println("Reading file...");
		BlockChain chain = BlockChain.fromFile(name);
		
		while (!validFileName) {
			System.out.println("Please try again:");
			name = userInput.nextLine().trim();
			System.out.println("Reading file...");
			chain = BlockChain.fromFile(name);
		}

		
		//2. Validating block chain
		System.out.println("Validating blockchain... ");;
		boolean valid = chain.validateBlockchain();
		System.out.println("blockchain valid: "+valid);
		
		
		if (valid) {
			
			//3. Enter new transaction 
			System.out.println("Would you like to enter a new transaction?(yes/no) ");
			String add = userInput.nextLine().trim().toLowerCase();
			
			add = promptUserForYesOrNo(add);
			//While the user wants to keep creating new transactions
			while (add.equals("yes")) {
				System.out.println("Creating transaction...");
				Transaction transaction = new Transaction();

				transaction = promptUserForTransaction();
				
				//D. Validate transaction
				System.out.println("Validating transaction...");
				int balance = chain.getBalance(transaction.getSender());
				while ((balance - transaction.getAmount()) < 0 && !transaction.getSender().equals("bitcoin")) { //if bitcoin is the sender then it's always valid
					System.out.println("This transaction is not valid. Please enter a new one: ");
					transaction = promptUserForTransaction(); //Keep prompting for transactions until user gives valid one  
				}
				System.out.println("Transaction is valid");
				
				//E. Adding transaction to blockchain
				System.out.println("Creating a block for this transaction...");
				//(a) create a block
				Block block = new Block(chain.getSize(),transaction);
				
				System.out.println("Adding block to the chain...");
				//(b) add block to chain
				chain.add(block);
				
				System.out.println("Generating hashcode for this transaction...");
				//(c) Generate a nonce and hash for this block:
				NonceGenerator.proofOfWorkGenerator(block);
				
				//Ask for more transactions:
				System.out.println("Would you like to input another transaction? ");
				add = userInput.nextLine().trim().toLowerCase();
				//If the user does not give a valid answer:
				add = promptUserForYesOrNo(add);

			}
			
			//4. Provide statistics of the hascode generation
			System.out.println("Statistics of the hashcode generation: ");
			NonceGenerator.statistics();
			
			//5 . Write the block chain to a file
			System.out.println("Transcribing the current blockchain into a file...");
			String fileName = name.replace(".txt", "");
			fileName = fileName.trim();
			chain.toFile(fileName+"_jzrib059.txt");
			

			
		} else {
			System.out.println("Ending session");
		}
		
	}
}

