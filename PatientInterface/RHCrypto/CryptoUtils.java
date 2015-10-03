package RHCrypto;
/**
* Provides functions for symmetric encryption
*/

//For file IO
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

//For cryptographic primitives
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;


//Exceptions
import java.io.IOException;
import java.security.InvalidKeyException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

//Logging
import java.util.logging.Logger;
import java.util.logging.Level;
 
/**
* A utility class that encrypts or decrypts a file
*/
public class CryptoUtils {
	/* Algorithm used */
	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES";
	
	/* logger */
	private static final Logger log = Logger.getLogger("RHCrypto");
	
	/**
	* This function prepares the inpout for encryption
	* @param key The SecretKey used to encrypt the data
	* @param inputFileName The name of the input file
	* @return byte[] the encrypted data in bytes
	*/
	protected static byte[] encrypt(SecretKey key, String inputFileName) {
		try {
			File inputFile=new File(inputFileName);
			return doCrypto(Cipher.ENCRYPT_MODE, key, inputFile);
		} catch (NullPointerException npe) {
			log.logp(Level.SEVERE, "CryptoUtils", "encrypt()", "Argument was null", npe);
		}
		return null;
	}
	
	/**
	* This function prepares the inout file decrypting
	* @param key The SecretKey to decrypt with
	* @param inputFileName the name of the inout file
	* @return byte[] the decrypted bytes
	*/	 
	protected static byte[] decrypt(SecretKey key, String inputFileName) {
		try {
			File inputFile=new File(inputFileName);
			return doCrypto(Cipher.DECRYPT_MODE, key, inputFile);
		} catch (NullPointerException npe) {
			log.logp(Level.SEVERE, "CryptoUtils", "decrypt()", "Argument was null", npe);
		}
		return null;
	}
	
	/**
	* This function provides the back-end to the above functions, does either encryption or decryption
	* @param cipherMode encrypting or decrypting?
	* @param secretKey the key to encrypt/decrypt with
	* @param inputFile the file ot encrypt/decrypt
	* @return the encrypted/decrypted bytes
	*/	 
	private static byte[] doCrypto(int cipherMode, SecretKey secretKey, File inputFile) {
		log.entering("CryptoUtils", "doCrypto()");
		try {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(cipherMode, secretKey);
				     
			FileInputStream inputStream = new FileInputStream(inputFile);
			byte[] inputBytes = new byte[(int) inputFile.length()];
			inputStream.read(inputBytes);
				     
			byte[] outputBytes = cipher.doFinal(inputBytes);
			return outputBytes;
		} catch (IOException ioe) {
			log.logp(Level.SEVERE, "CryptoUtils", "doCrypto()", "File IO problem", ioe);
		} catch (BadPaddingException bpe) {
			log.logp(Level.SEVERE, "CryptoUtils", "doCrypto()", "Bad Padding", bpe);
		} catch (NoSuchAlgorithmException nsae) {
			log.logp(Level.SEVERE, "CryptoUtils", "doCrypto()", "AES not found in system", nsae);
		} catch (NoSuchPaddingException nspe) {
			log.logp(Level.SEVERE, "CryptoUtils", "doCrypto()", "What was that padding?", nspe);
		} catch (IllegalBlockSizeException ibse) {
			log.logp(Level.SEVERE, "CryptoUtils", "doCrypto()", "Bad blocksize", ibse);
		} catch (InvalidKeyException ike) {
			log.logp(Level.SEVERE, "CryptoUtils", "doCrypto()", "Wrong key, right lock", ike);
		}
		log.exiting("CryptoUtils", "doCrypto()");
		return null;
	}
	
	/**
	* This function encrypts a string
	* @param secretKey the key to encrypt/decrypt with
	* @param inString the String to encrypt/decrypt
	* @return the encrypted/decrypted bytes
	*/	 
	public static byte[] encryptString(SecretKey secretKey, String inString) {
		log.entering("CryptoUtils", "doCrypto()");
		try {
			Cipher cipher = Cipher.getInstance("ARCFOUR");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
				     
			byte[] inputBytes = inString.getBytes("ISO-8859-1");
				     
			byte[] outputBytes = cipher.doFinal(inputBytes);
			return outputBytes;
		} catch (UnsupportedEncodingException uee) {
			log.logp(Level.SEVERE, "CryptoUtils", "doCrypto()", "ISO-8859-1 not supported", uee);
		} catch (BadPaddingException bpe) {
			log.logp(Level.SEVERE, "CryptoUtils", "doCrypto()", "Bad Padding", bpe);
		} catch (NoSuchAlgorithmException nsae) {
			log.logp(Level.SEVERE, "CryptoUtils", "doCrypto()", "AES not found in system", nsae);
		} catch (NoSuchPaddingException nspe) {
			log.logp(Level.SEVERE, "CryptoUtils", "doCrypto()", "What was that padding?", nspe);
		} catch (IllegalBlockSizeException ibse) {
			log.logp(Level.SEVERE, "CryptoUtils", "doCrypto()", "Bad blocksize", ibse);
		} catch (InvalidKeyException ike) {
			log.logp(Level.SEVERE, "CryptoUtils", "doCrypto()", "Wrong key, right lock", ike);
		}
		log.exiting("CryptoUtils", "doCrypto()");
		return null;
	}
	
	/**
	* This function encrypts a string
	* @param secretKey the key to encrypt/decrypt with
	* @param inString the String to encrypt/decrypt
	* @return the encrypted/decrypted bytes
	*/	 
	public static byte[] decryptString(SecretKey secretKey, String inString) {
		log.entering("CryptoUtils", "doCrypto()");
		try {
			Cipher cipher = Cipher.getInstance("ARCFOUR");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
				     
			byte[] inputBytes = inString.getBytes("ISO-8859-1");
				     
			byte[] outputBytes = cipher.doFinal(inputBytes);
			return outputBytes;
		} catch (UnsupportedEncodingException uee) {
			log.logp(Level.SEVERE, "CryptoUtils", "doCrypto()", "ISO-8859-1 not supported", uee);
		} catch (BadPaddingException bpe) {
			log.logp(Level.SEVERE, "CryptoUtils", "doCrypto()", "Bad Padding", bpe);
		} catch (NoSuchAlgorithmException nsae) {
			log.logp(Level.SEVERE, "CryptoUtils", "doCrypto()", "AES not found in system", nsae);
		} catch (NoSuchPaddingException nspe) {
			log.logp(Level.SEVERE, "CryptoUtils", "doCrypto()", "What was that padding?", nspe);
		} catch (IllegalBlockSizeException ibse) {
			log.logp(Level.SEVERE, "CryptoUtils", "doCrypto()", "Bad blocksize", ibse);
		} catch (InvalidKeyException ike) {
			log.logp(Level.SEVERE, "CryptoUtils", "doCrypto()", "Wrong key, right lock", ike);
		}
		log.exiting("CryptoUtils", "doCrypto()");
		return null;
	}
}
