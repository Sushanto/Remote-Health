package RHCrypto;
//For Class objects required
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

//For File IO
import java.io.FileInputStream;
import java.io.File;

//Exceptions
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.SignatureException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

//Logging
import java.util.logging.Logger;
import java.util.logging.Level;

public class Sign {

	/* logger */
	private static final Logger log = Logger.getLogger("RHCrypto");

	/**
	* Generate an ECDSA signature of the input file with respect to a private key
	* @param fileName The name of the fiel to be signed
	* @param priv The Private Key used to sign
	* @return byte[] the signature bytes
	*/
	protected static byte[] getECDSASignature(String fileName, PrivateKey priv) {
		try {
			Signature dsa = Signature.getInstance("SHA1withECDSA", "BC");
			dsa.initSign(priv);
			File inputFile = new File(fileName);
			FileInputStream fis = new FileInputStream(inputFile);
			byte[] inputBytes = new byte[(int)inputFile.length()];
			fis.read(inputBytes);
			dsa.update(inputBytes);
			byte[] b = dsa.sign();
			fis.close();
			return b;
		} catch (NoSuchAlgorithmException nsae) {
			log.logp(Level.SEVERE, "Sign", "getECDSASignature()", "SHA1withECDSA not found", nsae);
		} catch (InvalidKeyException ike) {
			log.logp(Level.SEVERE, "Sign", "getECDSASignature()", "Bad keys sent", ike);
		} catch (NullPointerException npe) {
			log.logp(Level.SEVERE, "Sign", "getECDSASignature()", "Some internal call got a null pointer", npe);
		} catch (FileNotFoundException fnfe) {
			log.logp(Level.SEVERE, "Sign", "getECDSASignature()", "Input file not found", fnfe);
		} catch (SecurityException se) {
			log.logp(Level.SEVERE, "Sign", "getECDSASignature()", "Security violation", se);
		} catch (IOException ioe) {
			log.logp(Level.SEVERE, "Sign", "getECDSASignature()", "IO exception", ioe);
		} catch (SignatureException sne) {
			log.logp(Level.SEVERE, "Sign", "getECDSASignature()", "Bad signature", sne);
		} catch (NoSuchProviderException nspe) {
			log.logp(Level.SEVERE, "Sign", "getECDSASignature()", "Bouncy Castle not installed", nspe);
		}
		return null;
	}

	/**
	* Verifies an ECDSA Signature
	* @param fileData the data of the signed file
	* @param pub The public key of the signing entity
	* @param sign The signature bytes to verify
	* @return boolean Was the signature successfully verified or not
	*/
	protected static boolean verifyECDSASignature(byte[] fileData, PublicKey pub, byte[] sign) {
		try {
			Signature dsa = Signature.getInstance("SHA1withECDSA", "BC");
			dsa.initVerify(pub);
			dsa.update(fileData);
			boolean ret = dsa.verify(sign);
			return ret;
		} catch (InvalidKeyException ike) {
			log.logp(Level.SEVERE, "Sign", "verifyECDSASignature()", "Bad key sent", ike);
		} catch (NoSuchAlgorithmException nsae) {
			log.logp(Level.SEVERE, "Sign", "verifyECDSASignature()", "SHA1withECDSA not found", nsae);
		} catch (SignatureException sne) {
			log.logp(Level.SEVERE, "Sign", "verifyECDSASignature()", "Bad signature", sne);
		} catch (NoSuchProviderException nspe) {
			log.logp(Level.SEVERE, "Sign", "getECDSASignature()", "Bouncy Castle not installed", nspe);
		}
		return false;
	}
}
