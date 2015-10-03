package RHCrypto;

//For Document
import org.w3c.dom.Document;

/* A file data class to hold the header and data
   (The text itself is a xml file)
*/
public class FileData {
	public String srcID;
	public String dstID;
	public int type;
	public byte[] mac;
	public byte[] sign;
	public Document text;
	public boolean isEncrypted;
	
	/**
	* Returns a printable string of this FileData object (mac and sign might not look pretty)
	* @return String The printable string
	*/
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("FILEDATA: \n");
		sb.append("Source: " + this.srcID + "  ");
		sb.append("Destination: " + this.dstID);
		sb.append("\nMAC: "); sb.append(new String(this.mac));
		sb.append("\nSign: "); sb.append(new String(this.sign));
		sb.append("\nType: " + Integer.toString(this.type) + "\n");
		sb.append("\nENcryption: " + Boolean.toString(this.isEncrypted) + "\n");
		return sb.toString();
	}
}

