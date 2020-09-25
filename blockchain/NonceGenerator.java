import java.io.UnsupportedEncodingException;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Random;

/**
 * Name: Yesmine Zribi
 * Student number: 8402454
 * Class: CSI2510
 * Assignment#2
 * 
 * This class generates the proof of work of a blockchain 
 * @author Yesmine Zribi (JZRIB059@uottawa.ca)
 *
 */
public class NonceGenerator {
	/**
	 *Stores all lower case alphanumeric characters
	 */
	private static final String ALPHA_LOW = "abcdefghijklmnopqrstuvwxyz";
	
	/**
	 *Stores all upper case alphanumeric characters
	 */
	private static final String ALPHA_UP = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 *Stores all numbers from 0-9
	 */
	private static final String NUMS = "0123456789";
	
	/**
	 *Stores all special characters
	 */
	private static final String SPECIAL_CHAR = "!@#$%^&*()_-=+{}[].';,.:'\\/";
	
	/**
	 *Stores statistics of the number of trials per transaction
	 */
	private static ArrayList<Integer> transactions = new ArrayList<Integer>(); //keep track of the number of trials
	
	
	/**
	 *Prints the number of trials of each transaction that required proof of work generation
	 */
	public static void statistics() {
		System.out.println(transactions);
		for (int i = 0; i < transactions.size(); i++) {
			System.out.println("Numbr of trials of newly added transaction "+(i+1)+" :"+transactions.get(i));
		}
	}
	
	
	/**
	 *This method generates a random nonce 
	 *@param the length of the nonce 
	 *@return the randomly generated nonce string
	 */
	public static String generateNonce(int length) {
		Random rand = new Random();
		String source = NUMS+ ALPHA_LOW + ALPHA_UP + SPECIAL_CHAR;
		StringBuilder nonce = new StringBuilder(); //use stringBuilder for memory efficiency
		int index; 
		for (int i = 0; i < length; i++) {
			index = rand.nextInt(source.length());
			nonce.append(source.charAt(index));
		}
		return nonce.toString();
	}
	
	
	/**
	 *This method keeps generating nonces until it finds a valid one
	 *@param the block to which the proof of work is requried 
	 *@return the valid nonce
	 */
	//Generates a valid nonce for a valid hash
	//Once found the valid hash is set for that block
	public static String proofOfWorkGenerator(Block block) throws UnsupportedEncodingException {
		boolean notValid = true;
		Random rand = new Random();
		String nonce = generateNonce(20);
		block.setNonce(nonce);
		String hash = Sha1.hash(block.toString(),Sha1.OUT_HEX);
		int numberOfTrials = 0;
		int index;
		while (hash.charAt(0) != '0' || hash.charAt(1) != '0' ||
				hash.charAt(2) != '0'|| hash.charAt(3) != '0' ||
				hash.charAt(4) != '0') {
			index = rand.nextInt(20);
			nonce = generateNonce(index); //generate a nonce string
			block.setNonce(nonce); //set it as nonce
			hash = Sha1.hash(block.toString(),Sha1.OUT_HEX); //convert the block to hash
			numberOfTrials++;
		}
		transactions.add(numberOfTrials);
		block.setHash(hash); //set the hash of this block to be hash
		return nonce;
	}
	

}
