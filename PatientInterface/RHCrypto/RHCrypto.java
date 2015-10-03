package RHCrypto;
/**
* The RHCrypto class provides the interface for the implementation of the cryptographic functions
*/


//For File IO
import java.io.File;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;

//For XML stuff
import org.w3c.dom.*;
import org.xml.sax.InputSource;

//For Cryptographic primitives
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;

import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.jce.interfaces.ECPrivateKey;

import java.security.Security;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.KeyFactory;

//For data storage
import java.util.HashMap;
import java.util.ArrayList;

//Exceptions
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.spec.InvalidKeySpecException;

//Logging
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

/* This class contains functions to interface with the Crypto functions
 * - Key generation
 * - Key management
 * - File encryption/decryption
 * - File signing/verification
 * - File MAC generation/verification
 * - Outgoing File generation (Sign then encrypt and then MAC)
 * - Incoming File preparation
 */
public class RHCrypto {
	private static RHCrypto INSTANCE; 	/* static RHCrypto instance for singleton design pattern */

	/* List of message types */
	public final static int LOGINREQ = 1,
			REQFWD = 2, 
			REDIR = 3,
			CREDREQ = 4,
			CRED = 5,
			KEYFWD = 8,
			AUTHREP = 9,
			DATA = 10,
			LOGOUTREQ = 11,
			NORESPONSE = 13,
			BADCRED = 14,
			UNKNOWN = 15,
			MAKEKEYS = 20,
			NEWKEYS = 21;

	private String myID;		/* ID of the entity associated with this instance */
	private KeyPair myKeyPair;	/* entity's keypair */
	private HashMap<String, ECPublicKey> publicKeys;		/* The public keys of the known entities */
	private HashMap<String, SecretKey> sessionKeys;			/* Their session keys */
	private ArrayList<String> segmentNames;	/* List of segment names */
	private ArrayList<String> KEKFileNames; /* List of KEKFiles */
	private Logger rhclogger;	/* logging across the whole of RHCrypto done through single logger */
	private boolean isDataServer;	/* boolean to check data server mode */
	private boolean isServer;	/* is this instance a server? */
	private HashMap<String, ECPublicKey> longtermKeys; /* The long term keys for the servers */
	private KeyPair myLongtermPair;

	/*Initialize Apache Security Library */
	static {
		org.apache.xml.security.Init.init();
	}

	/**
	* Read and store a public key from file
	* @param fileName The name of the file
	* @param entityName The name of the entity with whom to associate the read public key
	*/
	private void getPubKeyFromFile(String fileName, String entity) {
		try{
			File ltFile = new File(fileName);
			if (ltFile.exists() && !ltFile.isDirectory()) {
				byte[] pubKeyBytes = new byte[(int)ltFile.length()];
				DataInputStream dis = new DataInputStream(new FileInputStream(ltFile));
				dis.readFully(pubKeyBytes);
				X509EncodedKeySpec ks = new X509EncodedKeySpec(pubKeyBytes);
		    		KeyFactory kf = java.security.KeyFactory.getInstance("ECDH","BC");
				ECPublicKey remotePublicKey = (ECPublicKey)kf.generatePublic(ks);
				this.longtermKeys.put(entity, remotePublicKey);
			}
		} catch (IOException ioe) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "getPubKeyFromFile()", "File IO problem", ioe);
		} catch (NoSuchProviderException nspe) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "getPubKeyFromFile()", "Bouncy castle not installed", nspe);
		} catch (NoSuchAlgorithmException nsae) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "getPubKeyFromFile()", "ECDH not installed", nsae);
		} catch (SecurityException se) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "getPubKeyFromFile()", "Security violation", se);
		} catch (NullPointerException npe) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "getPubKeyFromFile()", "Pointer to internal call was null", npe);
		} catch (Exception e) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "getPubKeyFromFile()", "Something bad happened", e);
		}
	}

	/**
	* Associate the long term keys for this instance
	* We need only 2 at most, one for data server, the other for authserver
	* The keys are used to sign unencrypted messages
	*/
	private void associateLongtermKeys() {
		getPubKeyFromFile("data_ltpubkey", "DataServer");
		getPubKeyFromFile("auth_ltpubkey",  "AuthServer");
	}

	/**
	* Load servers long term key pair from file
	*
	*/
	private void loadMyLongtermKeys() {
		try {
			ECPublicKey myLTPublicKey = null;
			ECPrivateKey myLTPrivateKey = null;
			File myLTFile = new File("my_ltpubkey");
			if (myLTFile.exists() && !myLTFile.isDirectory()) {
				byte[] pubKeyBytes = new byte[(int)myLTFile.length()];
				DataInputStream dis = new DataInputStream(new FileInputStream(myLTFile));
				dis.readFully(pubKeyBytes);
				X509EncodedKeySpec ks = new X509EncodedKeySpec(pubKeyBytes);
		    		KeyFactory kf = java.security.KeyFactory.getInstance("ECDH","BC");
		    		myLTPublicKey = (ECPublicKey)kf.generatePublic(ks);
			} else {
				rhclogger.logp(Level.SEVERE, "RHCrypto", "loadMyLongtermKeys()", "Could not find my long term public key");
			}
			myLTFile = new File("my_ltprivkey");
			if (myLTFile.exists() && !myLTFile.isDirectory()) {
				byte[] privKeyBytes = new byte[(int)myLTFile.length()];
				DataInputStream dis = new DataInputStream(new FileInputStream(myLTFile));
				dis.readFully(privKeyBytes);
				PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(privKeyBytes);
		    		KeyFactory kf = java.security.KeyFactory.getInstance("ECDH","BC");
		    		myLTPrivateKey = (ECPrivateKey)kf.generatePrivate(ks);
			} else {
				rhclogger.logp(Level.SEVERE, "RHCrypto", "loadMyLongtermKeys()", "Could not find my long term private key");
			}
			this.myLongtermPair = new KeyPair(myLTPublicKey, myLTPrivateKey);
		} catch (InvalidKeySpecException ikse) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "loadMyLongtermKeys()", "key spec not suspported", ikse);
		} catch (IOException ioe) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "loadMyLongtermKeys()", "File IO problem", ioe);
		} catch (NoSuchAlgorithmException nsae) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "loadMyLongtermKeys()", "ECDH not found", nsae);
		} catch (NoSuchProviderException nspe) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "loadMyLongtermKeys()", "Bouncy castle not found", nspe);
		} catch (SecurityException se) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "loadMyLongtermKeys()", "Security voilation", se);
		} catch (NullPointerException npe) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "loadMyLongtermKeys()", "Call to internal call was null", npe);
		} catch (Exception e) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "loadMyLongtermKeys()", "Something bad happened", e);
		}
	}

	/**
	* Private constructor to initialize RHCrypto instance
	*/
	private RHCrypto(String ID, boolean isServer) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		this.myID = ID;
		this.KEKFileNames=new ArrayList<String>();
		this.segmentNames=new ArrayList<String>();
		publicKeys = new HashMap<String, ECPublicKey>();
		sessionKeys = new HashMap<String, SecretKey>();
		this.isDataServer = false;
		//generate the keypair fot this entity
		this.myKeyPair = GenerateKeyPair.generateKeyPair();

		//create a logger
		rhclogger = Logger.getLogger("RHCrypto");
		try {
			//initialize the logger
			FileHandler fh = new FileHandler("RHCrypto.log", false);
			fh.setLevel(Level.FINER);
			fh.setFormatter(new SimpleFormatter());
			rhclogger.addHandler(fh);
			rhclogger.setLevel(Level.FINER);
		} catch (IOException ioe) {
			rhclogger.severe("Failed to set up logger with filehandler");
		} catch (SecurityException se) {
			rhclogger.severe("Failed to set up logger with filehandler");
		} catch (IllegalArgumentException iae) {
			rhclogger.severe("Failed to set up logger with filehandler");
		} catch (NullPointerException npe) {
			rhclogger.severe("Failed to set up logger with filehandler");
		}

		//associate the long term keys
		longtermKeys = new HashMap<String, ECPublicKey>();
		associateLongtermKeys();

		if (isServer) {
			this.isServer = true;
			loadMyLongtermKeys();
		}
	}

	/**
	* The method to get an instance of RHCrypto
	* @param ID The ID to use for this instance (copied over to myID)
	* @return RHCrypto the instance for this
	*/
	public static RHCrypto getInstance(String ID, boolean isServer) {
		if (INSTANCE == null) {
			INSTANCE = new RHCrypto(ID, isServer);
		}
		return INSTANCE;
	}

	/**
	* Set this instance to the data server mode (no second level decryption)
	*/
	public void setAsDataServer() {
		this.isDataServer = true;
	}

	/**
	* Reset the RHCrypto instance of this program to initial form
	* The next time getInstance() is called, the constructor is run again
	*/
	public void terminate() {
		//reset the RHCtypyo instance
		publicKeys.clear();
		publicKeys = null;
		sessionKeys.clear();
		sessionKeys = null;
		segmentNames.clear();
		segmentNames = null;
		KEKFileNames.clear();
		KEKFileNames = null;
		longtermKeys.clear();
		longtermKeys = null;
		myID = null;
		myKeyPair = null;
		rhclogger = null;
		INSTANCE = null;
	}

	/**
	* Set key file names for segments
	* @param segName The name of a segment to encrypt
	*/
	public void setSegmentName(String segName) {
		segmentNames.add(segName);
		String fileName = "Key" + Integer.toString(KEKFileNames.size());
		KEKFileNames.add(fileName);
	}

	/**
	* Return number of segments added
	* This should be used to create the for MAKEKEYS request
	*/
	public int getSegmentCount() {
		return this.segmentNames.size();
	}

	/**
	* Return nonprintable encoded public key; encode this as base64 to get printable version
	* @return String The encoded public key of this instance
	*/
	public String getPublicKeyEncoded() {
		try {
			return new String(this.myKeyPair.getPublic().getEncoded(),"ISO-8859-1");
		} catch (UnsupportedEncodingException uee) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "getPublicKeyEncoded()", "ISO-8859-1 not supported", uee);
		}
		return null;
	}

	/**
	* Get the encoded session key of an entity
	* @param ID The ID of the entity whose session key you want
	* @return String the encoded session key for the ID, null on error (ID not found)
	*/
	protected String getSessionKey(String ID) {
		SecretKey sk = sessionKeys.get(ID);
		if (sk != null) {
			return Utility.getB64Encoded(sk.getEncoded());
		}
		return null;
	}

	/**
	* Add a public key to the list of known users
	* @param entityID The ID of the entity whose public key you are adding
	* @param pubKeyEncoded The encoded public key of the entity
	*/
	public void addEntityPublicKey(String entityID, String pubKeyEncoded) {
		try {
			byte[] pubKeyBytes = pubKeyEncoded.getBytes("ISO-8859-1");
			X509EncodedKeySpec ks = new X509EncodedKeySpec(pubKeyBytes);
	    		KeyFactory kf = java.security.KeyFactory.getInstance("ECDH","BC");
			ECPublicKey remotePublicKey = (ECPublicKey)kf.generatePublic(ks);
			this.publicKeys.put(entityID, remotePublicKey);
			SecretKey sessKey = GenerateKeyPair.getSessionKey(remotePublicKey, this.myKeyPair);
        		this.sessionKeys.put(entityID, sessKey);
       		} catch (NoSuchProviderException nspe) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "addEntityPublicKey()", "Bouncy castle provider not found", nspe);
		} catch (NoSuchAlgorithmException nsae) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "addEntityPublicKey()", "ECDH not found in system", nsae);
		} catch (SecurityException se) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "addEntityPublicKey()", "Security violated", se);
		} catch (NullPointerException npe) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "addEntityPublicKey()", "Some pointer was null", npe);
		} catch (Exception e) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "addEntityPublicKey()", "Something bad happened", e);
		}
	}

	/**
	* Removes the entity info (public Key, session key) from the system
	* @param entityID The ID string of the entity
	*/
	public void removeEntity(String entityID) {
		this.publicKeys.remove(entityID);
		this.sessionKeys.remove(entityID);
	}

	/**
	* Create separate KEK files by parsing file received from authserver
	*/
	protected void handleKEK(String fileName) throws RHCException {
		Document doc = Utility.parseFile(fileName);
		if(doc == null){
			throw new RHCException("Key file is corrupt, could not be parsed");
		}
		Element KEK = doc.getDocumentElement();
		PrintWriter pw;

		try {

			for(int i = 0; i < KEKFileNames.size(); i++) {
				String Key = KEK.getAttribute("Key" + (i+1));
				if (Key != null) {
					pw = new PrintWriter(KEKFileNames.get(i));
					pw.println(Key);
					pw.close();
				}
			}
			if (this.isDataServer) {
				for(int i = 0; i < KEKFileNames.size(); i++) {
					String Key = KEK.getAttribute("NewKey" + (i+1));
					if (Key != null) {
						pw = new PrintWriter("New" + KEKFileNames.get(i));
						pw.println(Key);
						pw.close();
					}
				}
			}

	    	File toDel = new File(fileName);
	    	toDel.delete();

	    } catch (IndexOutOfBoundsException ioobe) {
	    	rhclogger.logp(Level.SEVERE, "RHCrypto", "handleKEK()", "Not enough filenames added", ioobe);
	    } catch (FileNotFoundException fnfe) {
	    	rhclogger.logp(Level.SEVERE, "RHCrypto", "handleKEK()", "Input file somehow disappeared", fnfe);
	    } catch (NullPointerException npe) {
	    	rhclogger.logp(Level.SEVERE, "RHCrypto", "handleKEK()", "Some argument was null", npe);
	    }
	}

	/**
	* Decrypt and verify a file, write it to outFileName, return the FileData object(no Document inside)
	* This method handles the file recieved from another entity.
	* The file containing the payload and header id parsed, the header data containing the MAC, sign etc is verified
	* The payload is decrypted using the Session key of the source, if the file contained data then the file is further decrypted
	* @param inFileName The name of the input file
	* @param outFileName The decrypted contents are written to this file
	* @return FileData The fileData object contains the header information and the decrypted Document
	* @throws RHCException
	*/
	public FileData getPlaintextFile(String inFileName, String outFileName) throws RHCException {
		rhclogger.entering("RHCrypto", "getPlaintextFile()");
		FileData fd = Utility.readFile(inFileName);
		SecretKey sk = sessionKeys.get(fd.srcID);
		PublicKey ltPubKey = null;
		byte[] data = null;
		boolean signCheck = false;

		switch(fd.type) {	//destination
		case LOGINREQ:		//dataserver
		case LOGOUTREQ:		//dataserver
		case NORESPONSE:	//client
		case BADCRED:		//client
		case UNKNOWN:		//anyone
			//unencrypted
			Utility.writeDocToFile(fd.text,outFileName);
			//nothing to do here
			break;
		case MAKEKEYS:		//authserver
		case REQFWD:		//authserver
		case REDIR:		//client
			ltPubKey = longtermKeys.get("DataServer");
			data = Utility.getXMLDataBytes(fd.text);
			signCheck = Sign.verifyECDSASignature(data, ltPubKey, fd.sign);
			if (!signCheck) {
				throw new RHCException("Sign check failed on file " + inFileName);
			}
			Utility.writeDocToFile(fd.text,outFileName);
			break;
		case AUTHREP:		//dataserver
		case CREDREQ:		//client
			ltPubKey = longtermKeys.get("AuthServer");
			data = Utility.getXMLDataBytes(fd.text);
			signCheck = Sign.verifyECDSASignature(data, ltPubKey, fd.sign);
			if (!signCheck) {
				throw new RHCException("Sign check failed on file " + inFileName);
			}
			Utility.writeDocToFile(fd.text,outFileName);
			break;
		case CRED:		//authserver
		case DATA:		//dataserver/client
			//encrypted
			Document docT=fd.text;
			byte[] enc1 = Utility.getPayload(fd.text);
			boolean macCheck1 = (java.util.Arrays.equals(fd.mac,GenerateMAC.getMAC(enc1, sk)));
			if (!macCheck1) {
				throw new RHCException("MAC Check failed on file " + inFileName);
			}
			Utility.writeByteArrayToFile(enc1, "tempfile");
			byte[] dec1 = CryptoUtils.decrypt(sk, "tempfile");
			File tf = new File("tempfile");
			tf.delete();
			if(fd.type == DATA){
				Utility.writeByteArrayToFile(dec1,"temp.xml");
				docT = Utility.parseFile("temp.xml");
				if(docT == null){
					throw new RHCException("Problem parsing recieved file");
				}
				File fin = new File("temp.xml");
				fin.delete();
				if (!this.isDataServer) {
					for(String s : KEKFileNames){
						Decrypt_AES.decryptElement(docT,s);
					}
				}
			}
			PublicKey srcPubKey1 = (PublicKey)this.publicKeys.get(fd.srcID);
			if (srcPubKey1 == null) {
				throw new RHCException("Public Key of " + fd.srcID + " not found in system");
			}
			boolean signCheck1 = Sign.verifyECDSASignature(dec1, srcPubKey1, fd.sign);
			if (!signCheck1) {
				throw new RHCException("Signature verification on file " + inFileName + " from " + fd.srcID + " failed");
			}
			if(fd.type==DATA){
				Utility.writeDocToFile(docT,outFileName);
			}
			else{
				Utility.writeByteArrayToFile(dec1, outFileName);
			}
			break;
		case KEYFWD:	//client
			//encrypted with KEK handling
			byte[] enc2 = Utility.getPayload(fd.text);
			//TODO: Use a better comparator
			boolean macCheck2 = (java.util.Arrays.equals(fd.mac, GenerateMAC.getMAC(enc2, sk)));
			if (!macCheck2) {
				throw new RHCException("MAC Check failed on file " + inFileName);
			}
			Utility.writeByteArrayToFile(enc2, "tempfile");
			byte[] dec2 = CryptoUtils.decrypt(sk, "tempfile");
			PublicKey srcPubKey2 = (PublicKey)this.publicKeys.get(fd.srcID);
			if (srcPubKey2 == null) {
				throw new RHCException("Public Key of " + fd.srcID + " not found in system");
			}
			boolean signCheck2 = Sign.verifyECDSASignature(dec2, srcPubKey2, fd.sign);
			if (!signCheck2) {
				throw new RHCException("Signature verification on file " + inFileName + " from " + fd.srcID + " failed");
			}
			Utility.writeByteArrayToFile(dec2, outFileName);
			handleKEK(outFileName);
			break;
		case NEWKEYS:		//dataserver
			//decrypt using long term keys
			SecretKey ltSecret = GenerateKeyPair.getSessionKey(longtermKeys.get("AuthServer"), myLongtermPair);
			byte[] enc3 = Utility.getPayload(fd.text);
			boolean macCheck3 = java.util.Arrays.equals(fd.mac, GenerateMAC.getMAC(enc3, ltSecret));
			if (!macCheck3) {
				throw new RHCException("MAC Check failed on file " + inFileName);
			}
			Utility.writeByteArrayToFile(enc3, "tempfile");
			byte[] dec3 = CryptoUtils.decrypt(ltSecret, "tempfile");
			File todel = new File("tempfile");
			todel.delete();
			PublicKey srcPubKey3 = longtermKeys.get("AuthServer");
			if (srcPubKey3 == null) {
				throw new RHCException("Public Key of auth server no found");
			}
			boolean signCheck3 = Sign.verifyECDSASignature(dec3, srcPubKey3, fd.sign);
			if (!signCheck3) {
				throw new RHCException("Signature verification failed on file " + inFileName + " from " + fd.srcID);
			}
			Utility.writeByteArrayToFile(dec3, outFileName);
			handleKEK(outFileName);
			break;
		}
		rhclogger.exiting("RHCypto", "getPlaintextFile()");
		return fd;
	}

	/**
	* Encrypt and prepare a file for sending
	* This method encrypts a file using the session key, and additionally using XML encryption
	* @param inFileName The name of the inout file
	* @param destID The destination's ID
	* @param type The type of message you're trying to send
	* @param outFileName The output file where the encrypted file is wriiten
	* @throws RHCException
	* @throws Exception
	*/
	public void prepareCiphertextFile(String inFileName, String destID, int type, String outFileName) throws RHCException, Exception {
		rhclogger.entering("RHCrypto", "prepareCiphertextFile()");
		/* prepare FileData Object */
		FileData fd = new FileData();
		fd.type = type;
		fd.srcID = this.myID;
		fd.dstID = destID;
		SecretKey sk = sessionKeys.get(destID);

		switch(type) {		//source
		case LOGINREQ:		//client
		case LOGOUTREQ:		//client
		case NORESPONSE:	//dataserver/authserver
		case BADCRED:		//authserver
		case UNKNOWN:		//anyone
			//unencrypted
			fd.mac = new byte[]{0};
			fd.sign = new byte[]{0};
			fd.text = Utility.parseFile(inFileName);
			fd.isEncrypted = false;
			break;
		case REQFWD:		//dataserver
		case REDIR:		//dataserver
		case CREDREQ:		//authserver
		case AUTHREP:		//authserver
		case MAKEKEYS:		//dataserver
			fd.mac = new byte[]{0};
			fd.sign = Sign.getECDSASignature(inFileName, myLongtermPair.getPrivate());
			fd.text = Utility.parseFile(inFileName);
			fd.isEncrypted = false;
			break;
		case CRED:	//client
		case DATA:	//dataserver/client
		case KEYFWD:	//authserver
			//encrypted
			fd.isEncrypted = true;
			Document docT1;
			byte[] enc1;

			/* If type is data then second layer of encryption needs to be added */
			if(type==DATA){
				docT1 = Utility.parseFile(inFileName);
				if (!this.isDataServer) {
					for(int i = 0; i < KEKFileNames.size(); i++){
						Encrypt_AES.encryptElement(docT1, segmentNames.get(i), KEKFileNames.get(i));
					}
				}
				Utility.writeDocToFile(docT1, "temp"+destID+".xml");
				fd.sign = Sign.getECDSASignature("temp"+destID+".xml", this.myKeyPair.getPrivate());
				enc1 = CryptoUtils.encrypt(sk, "temp"+destID+".xml");
				File todel = new File("temp"+destID+".xml");
				todel.delete();
			} else {
				fd.sign = Sign.getECDSASignature(inFileName, this.myKeyPair.getPrivate());
				enc1 = CryptoUtils.encrypt(sk, inFileName);
			}
			fd.text = Utility.createPayload(enc1);
			fd.mac = GenerateMAC.getMAC(enc1, sk);
			break;
		case NEWKEYS:	//authserver
			//encrypt using long term keys
			fd.isEncrypted = true;
			Document docT2;
			SecretKey ltSecret = GenerateKeyPair.getSessionKey(longtermKeys.get("DataServer"), myLongtermPair);
			fd.sign = Sign.getECDSASignature(inFileName, this.myLongtermPair.getPrivate());
			byte[] enc2 = CryptoUtils.encrypt(ltSecret, inFileName);
			fd.text = Utility.createPayload(enc2);
			fd.mac = GenerateMAC.getMAC(enc2, ltSecret);
			break;
		}
		rhclogger.exiting("RHcrypto", "prepareCiphertextFile()");
		Utility.writeFile(fd, outFileName);
	}

	/**
	* Encrypt a string for direct transmission
	* @param input The string to encrypt
	* @param dest The name of the destination
	* @return String The encrypted string
	*/
	public String prepareCiphertextString(String input, String dest) throws RHCException {
		try {
			SecretKey sk = sessionKeys.get(dest);
			if (sk == null) {
				throw new RHCException("Public Key of " + dest + " not found in system");
			}
			byte[] ctBytes = CryptoUtils.encryptString(sk, input);
			return new String(ctBytes,"ISO-8859-1");
		} catch (UnsupportedEncodingException uee) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "prepareCiphertextString()", "ISO-8859-1 not supported", uee);
		}
		return null;
	}

	/**
	* Decrypt an encrypted string
	* @param cipher The encrypted string
	* @param src The ID of the source of the encrypted string
	* @return String The decrypted string
	*/
	public String getPlaintextString(String cipher, String src) throws RHCException {
		try {
			SecretKey sk = sessionKeys.get(src);
			if (sk == null) {
				throw new RHCException("Public Key of " + src + " not found in system");
			}
			byte[] ptBytes = CryptoUtils.decryptString(sk, cipher);
			return new String(ptBytes, "ISO-8859-1");
		} catch (UnsupportedEncodingException uee) {
			rhclogger.logp(Level.SEVERE, "RHCrypto", "getPlaintextString()", "ISO-8859-1 not supported", uee);
		}
		return null;
	}
}
