package RHCrypto;
/**
* Provides the functions required to generate an ECDHE Key Pair and generate a Shared secret from there
*/

//For math
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.interfaces.ECPrivateKey;

//For Cryptographic primitives
import java.security.SecureRandom;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.MessageDigest;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

//for copyOf()
import java.util.Arrays;

//Exceptions
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchProviderException;
import java.security.NoSuchAlgorithmException;

//Logging
import java.util.logging.Logger;
import java.util.logging.Level;

class GenerateKeyPair {
	
	/* logger */
	private static final Logger log = Logger.getLogger("RHCrypto");
	
	/**
	* Generate the ECDHE KeyPair
	* @return KeyPair returns the generated ECDHE KeyPair
	*/
	protected static KeyPair generateKeyPair() {
		try {
			//Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("prime256v1");
			KeyPairGenerator g = KeyPairGenerator.getInstance("ECDH", "BC"); //was ECDSA
			g.initialize(ecSpec, new SecureRandom());
			KeyPair pair = g.generateKeyPair();
			return pair;
		} catch (NullPointerException npe) {
			log.logp(Level.SEVERE, "GenerateKeyPair", "generateKeyPair()", "Some argument was null", npe);
		} catch (NoSuchAlgorithmException nase) {
			log.logp(Level.SEVERE, "GenerateKeyPair", "generateKeyPair()", "ECDH not found in system", nase);
		} catch (NoSuchProviderException nspe) {
			log.logp(Level.SEVERE, "GenerateKeyPair", "generateKeyPair()", "Bouncy castle bounced off", nspe);
		} catch (IllegalArgumentException iae) {
			log.logp(Level.SEVERE, "GenerateKeyPair", "generateKeyPair()", "Bad arguments to internal call", iae);
		} catch (InvalidAlgorithmParameterException iape) {
			log.logp(Level.SEVERE, "GenerateKeyPair", "generateKeyPair()", "Argument was bad", iape);
		}
		return null;
	}

	/**
	* Generate the shared secret from my private key and received public key
	* @param pub received publicKey
	* @param priv my privateKey
	* @return ECPoint returns the generated shared secret
	*/
	protected static ECPoint generateSharedSecret(PublicKey pub, PrivateKey priv) {

		ECPublicKey pubKey = (ECPublicKey)pub;
		ECPrivateKey privKey = (ECPrivateKey)priv;

		ECPoint P = pubKey.getQ();
		ECPoint Q = P.multiply(privKey.getD());

		return Q;
	}
	
	/**
	* XOR 2 Byte arrys assuming they are the same length, returns the XOR-ed byte array
	* @param ba? byte[] Byte arrays to be XOR-ed with each other ;)
	* @return byte[] The Xored byte array :P
	*/
	protected static byte[] xorArray(byte[] ba1, byte[] ba2) {
		int i;
		byte[] res = new byte[ba1.length];
		for (i = 0; i < ba1.length; i++)
			res[i] = (byte)(0xff & ((int)ba1[i] ^ (int)ba2[i]));
		return res;
	}

	/**
	* Generate the Session Key(Incomplete)
	* @param Q the shared secret
	* @return SecretKey Returns the session key generated from Shared Secret
	*/
	protected static SecretKey generateSessionKey (ECPoint Q) {
		try {
			//hash the ECPoint and return preferably as Key type
			byte[] xEnc = Q.normalize().getAffineXCoord().getEncoded();
			byte[] yEnc = Q.normalize().getAffineYCoord().getEncoded();
			//byte[] skBytes = xorArray(xEnc,yEnc);
		
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
		
			md5.update(xEnc);
			sha.update(yEnc);
		
			byte[] xDigest = md5.digest();
			byte[] yDigest = Arrays.copyOf(sha.digest(), xDigest.length);
		
			byte[] skBytes = xorArray(xDigest, yDigest);
			SecretKey sessionKey = new SecretKeySpec(skBytes, "AES");
			return sessionKey;
			
		} catch (IllegalStateException ise) {
			log.logp(Level.SEVERE, "GenerateKeyPair", "generateSessionKey()", "Bad state", ise);
		} catch (NoSuchAlgorithmException nsae) {
			log.logp(Level.SEVERE, "GenerateKeyPair", "generateSessionKey()", "MD5/SHA-1 not found in system", nsae);
		} catch (NegativeArraySizeException nase) {
			log.logp(Level.SEVERE, "GenerateKeyPair", "generateSessionKey()", "Array size was less than zero", nase);
		} catch (NullPointerException npe) {
			log.logp(Level.SEVERE, "GenerateKeyPair", "generateSessionKey()", "Some pointer was null", npe);
		} catch (IllegalArgumentException iae) {
			log.logp(Level.SEVERE, "GenerateKeyPair", "generateSessionKey()", "BAd arguments tto internal call", iae);
		}
		return null;
	}

	/**
	*Generates a KeyPair and derives the shared secret from it and from that generates the Session Key
	*/
	protected static SecretKey getSessionKey(PublicKey rcKey, KeyPair myKeys) {
		ECPoint Q = generateSharedSecret(rcKey, myKeys.getPrivate());
		SecretKey sessionKey = generateSessionKey(Q);
		return sessionKey;
	}
}
