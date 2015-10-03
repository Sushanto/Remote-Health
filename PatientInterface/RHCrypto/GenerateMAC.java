package RHCrypto;
/* 
* Provides the functions required to generate the Message Authentication Code (MAC) of a message
*/

//For cryptographic primitives
import javax.crypto.SecretKey;
import java.security.KeyPair;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

//Exceptions
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

//Logging
import java.util.logging.Logger;
import java.util.logging.Level;

class GenerateMAC {

	/* logger */
	private static final Logger log = Logger.getLogger("RHCrypto");	

	/* Generate the MAC of the data with the SecretKey sKey 
	 * @param data The data to be authenticated
	 * @param sKey The secretkey to authenticate with
	 * @return byte[] The SHA1-HMAC of the data
	 */	
	public static byte[] getMAC(byte[] data, SecretKey sKey) {
		try {
			Mac macdonalds = Mac.getInstance("HmacSHA1");
			macdonalds.init(sKey);
			byte[] hmac = macdonalds.doFinal(data);
			return hmac;
		} catch (InvalidKeyException ike) {
			log.logp(Level.SEVERE, "GenerateMAC", "getMAC()", "Bad key sent", ike);
		} catch (IllegalStateException ise) {
			log.logp(Level.SEVERE, "GenerateMAC", "getMAC()", "System in bad shape", ise);
		} catch (NoSuchAlgorithmException nsae) {
			log.logp(Level.SEVERE, "GenerateMAC", "getMAC()", "HmacSHA1 not in system", nsae);
		}
		return null;
	}
}
