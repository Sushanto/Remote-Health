package RHCrypto;
/**
* Provides XML encryption functions
*/

//For file IO
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//For Cryptographic primitives
import java.security.Key;

import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

//For XML Security
import org.apache.xml.security.utils.JavaUtils;

import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.EncryptedData;
import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.AgreementMethod;

//For XML handling
import org.w3c.dom.Document;
import org.w3c.dom.Element;

//Exceptions
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.apache.xml.security.encryption.XMLEncryptionException;
import java.security.InvalidParameterException;

//Logging
import java.util.logging.Logger;
import java.util.logging.Level;

/**
*	Encrypt_AES class used to encrypt an XML File using AES-128 Symmetric key
*	This symmetric key is generated during encryption and encrypted using DESede 
*	key and the encryption key is stored in file and the encrypted symmetric key 
*	is stored in the KeyInfo field in the encrypted xml file. 
*	For the same segment ( access level) same encryption key is used for every xml file.
*	However 3 encryption keys are used for the 3 different segments.   
*/
class Encrypt_AES{

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
			log.logp(Level.SEVERE, "Encrypt_AES", "loadKeyEncryptionKey()", "Null Pointer", npe);
		} catch (InvalidKeyException ike) {
			log.logp(Level.SEVERE, "Encrypt_AES", "loadKeyEncryptionKey()", "Bad Key", ike);
		} catch (NoSuchAlgorithmException nsae) {
			log.logp(Level.SEVERE, "Encrypt_AES", "loadKeyEncryptionKey()", "DESede not found", nsae);
		} catch (InvalidKeySpecException ikse) {
			log.logp(Level.SEVERE, "Encrypt_AES", "loadKeyEncryptionKey()", "Bad key specification", ikse);
		}
		return null;
	}

	/**
	* Generate the AES-128 symmetric key for encrypting xml file
	* @return SecretKey returns generated symmetric key
	*/
	protected static SecretKey GenerateSymmetricKey() {
		try {
			String jceAlgorithmName = "AES";
			KeyGenerator keyGenerator = KeyGenerator.getInstance(jceAlgorithmName);
			keyGenerator.init(128);
			return keyGenerator.generateKey();
		} catch (NoSuchAlgorithmException nsae) {
			log.logp(Level.SEVERE, "Encrypt_AES", "generateSymmetricKey()", "AES not installed", nsae);
		} catch (InvalidParameterException ipe) {
			log.logp(Level.SEVERE, "Encrypt_AES", "generateSymmetricKey()", "bad parameter", ipe);
		}
		return null;
	}

	/**
	* Encrypt a single element of an XML File using the generated symmetric key
	* @param document parsed XML file as a Document
	* @param elementName	tag name to be encrypted
	* @param fileName name of the file containing the key encryption key to be used
	*/
	protected static void encryptElement(Document document,String elementName,String fileName) throws Exception {
		try {
			Key symmetricKey = GenerateSymmetricKey();

			// Get a key to be used for encrypting the symmetric key
			Key keyEncryptKey = loadKeyEncryptionKey(fileName);

			// initialize cipher
			XMLCipher keyCipher = XMLCipher.getInstance(XMLCipher.TRIPLEDES_KeyWrap);
			keyCipher.init(XMLCipher.WRAP_MODE, keyEncryptKey);
				 
			// encrypt symmetric key
			EncryptedKey encryptedKey = keyCipher.encryptKey(document, symmetricKey);
				 
			Element rootElement = document.getDocumentElement();
			Element elementToEncrypt =(Element)rootElement.getElementsByTagName(elementName).item(0);
			if (elementToEncrypt == null) {
				throw new RHCException("Element to encrypt " + elementName + " not found in " + fileName);
			} 

			// initialize cipher
			XMLCipher xmlCipher = XMLCipher.getInstance(XMLCipher.AES_128);
			xmlCipher.init(XMLCipher.ENCRYPT_MODE, symmetricKey);

			// add key info to encrypted data element
			EncryptedData encryptedDataElement = xmlCipher.getEncryptedData();
			KeyInfo keyInfo = new KeyInfo(document);
			keyInfo.add(encryptedKey);
			encryptedDataElement.setKeyInfo(keyInfo);
				 
			// do the actual encryption
			boolean encryptContentsOnly = false;
			xmlCipher.doFinal(document, elementToEncrypt, encryptContentsOnly);
		} catch (XMLEncryptionException xmlee) {
			log.logp(Level.SEVERE, "Encrypt_AES", "encryptElement()", "XML encryption problem", xmlee);
		} catch (Exception e) {
			log.logp(Level.SEVERE, "Encrypt_AES", "encryptElement()", "Something bad happened", e);
		}
	}
}
