package RHCrypto;
/**
* Provides XML Decryption function
*/

//For File IO
import java.io.File;

//For Cryptographic primitives
import java.security.Key;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

//For XML Decryption
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.utils.JavaUtils;
import org.apache.xml.security.utils.EncryptionConstants;

//For XML handling
import org.w3c.dom.Document;
import org.w3c.dom.Element;

//Exceptions
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.apache.xml.security.encryption.XMLEncryptionException;

//Logging
import java.util.logging.Logger;
import java.util.logging.Level;

/**
*	Decrypt_XML class
*	used to decrypt an AES-128 encrypted XML file. 
*	The DESede keys used to encrypt the symmetric keys that
*	were used to encrypt the XML file are first loaded from
*	fileName. Then the different segments are sequentially
*	decrypted. Partial decryption is supported. For this 
*	the key encryption files for the segments to be decrypted
*	are provided as command line arguments.
*/
class Decrypt_AES{
	
	/* logger */
	private static final Logger log = Logger.getLogger("RHCrypto");	
	
	/**
	* Load the key used to encrypt symmetric key
	* @param fileName name of file where the key encryption key is stored
	* @return SecretKey returns the DESede key read from fileName  
	*/
	protected static SecretKey loadKeyEncryptionKey(String fileName) {
		try {
			String jceAlgorithmName = "DESede";
			 
			File kekFile = new File(fileName);
	
			byte[] s = Utility.getB64Decoded(Utility.readFileAsString(fileName));
			DESedeKeySpec keySpec = new DESedeKeySpec(s);
			SecretKeyFactory skf = SecretKeyFactory.getInstance(jceAlgorithmName);
			SecretKey key = skf.generateSecret(keySpec);
			return key;
		} catch (NullPointerException npe) {
			log.logp(Level.SEVERE, "Decrypt_AES", "loadKeyEncryptionKey()", "Argument was null", npe);
		} catch (InvalidKeyException ike) {
			log.logp(Level.SEVERE, "Decrypt_AES", "loadKeyEncryptionKey()", "Wrong Key", ike);
		} catch (NoSuchAlgorithmException nsae) {
			log.logp(Level.SEVERE, "Decrypt_AES", "loadKeyEncryptionKey()", "DESede not found in system", nsae);
		} catch (InvalidKeySpecException ikse) {
			log.logp(Level.SEVERE, "Decrypt_AES", "loadKeyEncryptionKey()", "Bad Key Spec", ikse);
		}
		return null;
	}

	/**
	* Decrypt an encrypted element of an XML File
	* @param document parsed XML file as a Document
	* @param fileName name of the file containing the key encryption key to be used
	*/ 
	protected static void decryptElement(Document document, String fileName) {
		log.entering("Decrypt_AES", "decryptElement()");
		try {
			// get the encrypted data element
			String namespaceURI = EncryptionConstants.EncryptionSpecNS;
			String localName = EncryptionConstants._TAG_ENCRYPTEDDATA;
			Element encryptedDataElement =(Element)document.getElementsByTagNameNS(namespaceURI, localName).item(0);

			// Load the key encryption key.
			Key keyEncryptKey = loadKeyEncryptionKey(fileName);

			// initialize cipher
			XMLCipher xmlCipher = XMLCipher.getInstance();
			xmlCipher.init(XMLCipher.DECRYPT_MODE, null);

			xmlCipher.setKEK(keyEncryptKey);

			// do the actual decryption
			xmlCipher.doFinal(document, encryptedDataElement);
				    
		} catch (XMLEncryptionException xmlee) {
			log.logp(Level.SEVERE, "Decrypt_AES", "decryptElement()", "XML encryption problem", xmlee);
		} catch (Exception e) {
			log.logp(Level.SEVERE, "Decrypt_AES", "decryptElement()", "Something bad happened", e);
		}
		log.exiting("Decrypt_AES", "decryptElement()");
	}
}
