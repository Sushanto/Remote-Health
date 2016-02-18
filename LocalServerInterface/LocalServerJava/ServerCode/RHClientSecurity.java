package ServerCode;

import java.io.File;

import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class RHClientSecurity
{
        private Key key;
        private Cipher encCipher, decCipher;
        public static String ALGO = "AES";

	public RHClientSecurity() {
		this.key = null;
		this.encCipher = null;
		this.decCipher = null;
	}

        public void loadKey(String keystr) throws Exception {
                try {
                        this.key = new SecretKeySpec(Base64.getDecoder().decode(keystr), RHClientSecurity.ALGO);
                        this.encCipher = Cipher.getInstance(RHClientSecurity.ALGO);
                        this.encCipher.init(Cipher.ENCRYPT_MODE, this.key);
                        this.decCipher = Cipher.getInstance(RHClientSecurity.ALGO);
                        this.decCipher.init(Cipher.DECRYPT_MODE, this.key);
                } catch (Exception e) {
                        throw new Exception("[RHCS] Exception during key load operation");
                }
        }

        public void encryptFile(String inFileName) throws Exception {
		try {
			File ifile = new File(inFileName);
			JAXBContext jc = JAXBContext.newInstance(FileData.class);
			Unmarshaller um = jc.createUnmarshaller();
			FileData infd = (FileData)um.unmarshal(ifile);

			//System.out.println("[RHCS] Unmarshalled@E: " + infd.getBinaryData());

			//actual encryption
			String str = infd.getBinaryData();
			byte[] data = Base64.getDecoder().decode(str);
			byte[] encdata = this.encCipher.doFinal(data);
			String encstr = Base64.getEncoder().encodeToString(encdata);
			//System.out.println("[RHCS] Encrypted@E: " + encstr);
			infd.setBinaryData(encstr);

			ifile.delete();
			Marshaller jm = jc.createMarshaller();
			jm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
			jm.marshal(infd, ifile);
		} catch (Exception e) {
			throw new Exception("[RHCS] Exception during encryption");
		}
	}

        public void decryptFile(String inFileName) throws Exception {
		try {
			File ifile = new File(inFileName);
			JAXBContext jc = JAXBContext.newInstance(FileData.class);
			Unmarshaller um = jc.createUnmarshaller();
			FileData infd = (FileData)um.unmarshal(ifile);

			//System.out.println("[RHCS] Unmarshalled@D: " + infd.getBinaryData());

			//actual decryption
			String encstr = infd.getBinaryData();
			byte[] encdata = Base64.getDecoder().decode(encstr);
			byte[] data = this.decCipher.doFinal(encdata);
			String str = Base64.getEncoder().encodeToString(data);
			//System.out.println("[RHCS] Decrypted@D: " + str);
			infd.setBinaryData(str);

			ifile.delete();
			Marshaller jm = jc.createMarshaller();
			jm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
			jm.marshal(infd, ifile);
		} catch (Exception e) {
			throw new Exception("[RHCS] Exception during decryption");
		}
        }
}
