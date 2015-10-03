package RHCrypto;
/** Generate a pair of long term key pairs, and store them in disk 
*/
//For cryptographic primitives
import java.security.Security;
import java.security.KeyPair;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

//For io
import java.util.Scanner;
import java.io.FileOutputStream;

//Exceptions
import java.io.IOException;

public class GenerateLongTermKey {
	
	private static KeyPair ltKeyPair;
	
	public static void generateLTKeyPair() {
		ltKeyPair = GenerateKeyPair.generateKeyPair();
	}
	
	public static void storeLTKeyPair() {
		ECPrivateKey privKey = (ECPrivateKey)ltKeyPair.getPrivate();
		ECPublicKey pubKey = (ECPublicKey)ltKeyPair.getPublic();
		
		try {
			X509EncodedKeySpec x509 = new X509EncodedKeySpec(pubKey.getEncoded());
			FileOutputStream fos = new FileOutputStream("my_ltpubkey");
			fos.write(x509.getEncoded());
			fos.close();
		
			PKCS8EncodedKeySpec pkcs8 = new PKCS8EncodedKeySpec(privKey.getEncoded());
			fos = new FileOutputStream("my_ltprivkey");
			fos.write(pkcs8.getEncoded());
			fos.close();
		} catch (IOException ioe) {
			System.err.println("File IO error");
			ioe.printStackTrace();
		} catch (NullPointerException npe) {
			System.err.println("Key bytes are null");
			npe.printStackTrace();
		} catch (Exception e) {
			System.err.println("Something bad happened");
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		System.out.println("Generate new long term pair?(y/N)");
		System.out.println("(WARNING: THIS WILL OVERWRITE YOUR EXISTING PAIR)\n(The files my_ltprivkey and my_ltpubkey)");
		Scanner sc = new Scanner(System.in);
		String ans = sc.nextLine();
		if (ans.equals("y") || ans.equals("Y")) {
			generateLTKeyPair();
			storeLTKeyPair();
			System.out.println("New keys have bneen generated. Make sure everybody gets your new public key!");
		} else {
			System.out.println("Bailing out.");
		}
	}

}

