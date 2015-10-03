package RHCrypto;
/**
* Provides various utility functions needed for RHCrypto
*/

//for XML stuff
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.InputSource;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;

//for File/String IO
import java.io.File;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringWriter;
import java.io.StringReader;

//for Base64 Encoding
//import java.util.Base64;	//If you ever upgrade to Java 8 use this, because the following package sucks.
//import sun.misc.BASE64Encoder;
//import sun.misc.BASE64Decoder;
import org.apache.commons.codec.binary.Base64;

//Exceptions
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import org.xml.sax.SAXException;
import java.io.FileNotFoundException;

//Logging
import java.util.logging.Logger;
import java.util.logging.Level;

/**
*Utility class stores all the secondary functions required for operation of the encryption algorithms
*/
public class Utility {

	/* logger */
	private static final Logger log = Logger.getLogger("RHCrypto");

	/**
	* Parse an XML File into Document
	* @param fileName name of the XML file to be parsed
	* @return Document return parsed XML file in Document format
	*/
	public static Document parseFile(String fileName) {
	    	try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				dbf.setNamespaceAware(true);
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document document = db.parse(fileName);
				return document;
		} catch (ParserConfigurationException pce) {
			log.logp(Level.SEVERE, "Utility", "parseFile()", "Parser in bad state", pce);
		} catch (SAXException saxe) {
			log.logp(Level.SEVERE, "Utility", "parseFile()", "SAXException", saxe);
		} catch (IOException ioe) {
			log.logp(Level.SEVERE, "Utility", "parseFile()", "IO error", ioe);
		} catch (IllegalArgumentException iae) {
			log.logp(Level.SEVERE, "Utility", "parseFile()", "Bad arguments to internal call", iae);
		}

		return null;
	}

	/**
	* Write encrypted document to file
	* @param doc The Document file to be written to file
	* @param fileName The name of the output file
	*/
	public static void writeDocToFile(Document doc, String fileName) {
		try {
			File encryptionFile = new File(fileName);
			FileOutputStream outStream = new FileOutputStream(encryptionFile);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(outStream);
			transformer.transform(source, result);
			outStream.close();
		} catch (NullPointerException npe) {
			log.logp(Level.SEVERE, "Utility", "writeDocToFile()", "Some pointer was null to internal call", npe);
		} catch (DOMException dome) {
			log.logp(Level.SEVERE, "Utility", "writeDocToFile()", "DOMException, blame dominic", dome);
		} catch (FileNotFoundException fnfe) {
			log.logp(Level.SEVERE, "Utility", "writeDocToFile()", "Input file not found", fnfe);
		} catch (SecurityException se) {
			log.logp(Level.SEVERE, "Utility", "writeDocToFile()", "Security violation", se);
		} catch (TransformerConfigurationException tce) {
			log.logp(Level.SEVERE, "Utility", "writeDocToFile()", "Decepticons have won!", tce);
		} catch (IllegalArgumentException iae) {
			log.logp(Level.SEVERE, "Utility", "writeDocToFile()", "Bad argument to internal call", iae);
		} catch (TransformerException te) {
			log.logp(Level.SEVERE, "Utility", "writeDocToFile()", "Autobots rule!", te);
		} catch (IOException ioe) {
			log.logp(Level.SEVERE, "Utility", "writeDocToFile()", "IO problem", ioe);
		}
	}

	/**************** BASE64 encoding **************/
	/**
	* Turn raw bytes into base64 encoded string
	* @param rawBytes The array of bytes to be encoded
	* @return String The encoded string (null if error)
	*/
	public static String getB64Encoded(byte[] rawBytes) {
		try {
			return Base64.encodeBase64String(rawBytes);
		} catch (Exception e) {
			log.logp(Level.SEVERE, "Utility", "getB64Encoded()", "Encoding failed", e);
		}
		return null;
	}

	/**
	* Return the decoded byte array
	* @param b64String the B64 Encoded string
	* @return the deocoded byte array (null if error)
	*/
	public static byte[] getB64Decoded(String b64String) {
		try {
			return Base64.decodeBase64(b64String);
		} catch (Exception e) {
			log.logp(Level.SEVERE, "Utility", "getB64Decoded()", "Decoding failed", e);
		}
		return null;
	}

	/* Turn raw bytes into Hex encoded string */
	public static String getHexEncoded(byte[] rawBytes) {
		StringBuilder sb = new StringBuilder(rawBytes.length * 2);
		for(byte b: rawBytes)
			sb.append(String.format("%02x", b & 0xff));
		return sb.toString();
	}

	/* Return the decoded byte array */
	public static byte[] getHexDecoded(String hexString) {
		int len = hexString.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i/2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i+1), 16));
		}
		return data;
	}

	/************* RHCrypto File format ***********/
	/**
	* Read a file, parse it into header and data
	* @param inputFileName the name of the XML file to parse into payload and header
	* @return FileData a FileData object containing the header info and the rest of the Document
	*/
	public static FileData readFile(String inputFileName) {
		//parse to document
		Document d = parseFile(inputFileName);
		//get root element
		Element root = d.getDocumentElement();
		//get header child node
		Node header = root.getElementsByTagName("Header").item(0);
		try {
			root.removeChild(header);	//remove header
		} catch (DOMException dome) {
			log.logp(Level.SEVERE, "Utility", "readFile()", "DOMException", dome);
		}

		//get values from header
		String macString = ((Element)header).getAttribute("mac");
		String signString = ((Element)header).getAttribute("sign");
		String srcString = ((Element)header).getAttribute("src");
		String destString = ((Element)header).getAttribute("dst");
		int type = 0;
		try {
			type = Integer.parseInt(((Element)header).getAttribute("type"));
		} catch (NumberFormatException nfe) {
			log.logp(Level.SEVERE, "Utility", "readFile()", "Parsing numner from string failed", nfe);
		}
		Boolean encFlag = Boolean.parseBoolean(((Element)header).getAttribute("isEncrypted"));

		//build FileData object
		FileData fd = new FileData();
		fd.mac = getB64Decoded(macString);
		fd.sign = getB64Decoded(signString);
		fd.srcID = srcString;
		fd.dstID = destString;
		fd.type = type;
		fd.text = d;
		fd.isEncrypted = encFlag;

		//return the modified document
		return fd;
	}

	/**
	* Add a header to a file and write it to a file
	* @param fd A FileData object containing the header info and Document
	* @param outputFileName	The name of the output file
	*/
	public static void writeFile(FileData fd, String outputFileName) {
		String macString = getB64Encoded(fd.mac);
		String signString = getB64Encoded(fd.sign);
		String srcString = fd.srcID;
		String destString = fd.dstID;
		String typeString = Integer.toString(fd.type);
		String encFlagString = Boolean.toString(fd.isEncrypted);

		Document d = fd.text;
		Element header = d.createElement("Header");
		header.setAttribute("mac", macString);
		header.setAttribute("sign", signString);
		header.setAttribute("src", srcString);
		header.setAttribute("dst", destString);
		header.setAttribute("type", typeString);
		header.setAttribute("isEncrypted", encFlagString);

		Element root = d.getDocumentElement();
		try {
			root.appendChild(header);
		} catch (DOMException dome) {
			log.logp(Level.SEVERE, "Utility", "writeFile()", "DOMException", dome);
		}

		writeDocToFile(d, outputFileName);
	}

	/**
	* Read a file into a string
	* @param filePath the name of the file
	* @return String a String containing the contents of the file
	*/
	protected static String readFileAsString(String filePath) {
		try {
			StringBuffer fileData = new StringBuffer(1000);
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			char[] buf = new char[1024];
			int numRead=0;
			while((numRead=reader.read(buf)) != -1){
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
				buf = new char[1024];
			}
			reader.close();
			return fileData.toString();
		} catch (NegativeArraySizeException nase) {
			log.logp(Level.SEVERE, "Utility", "readFileAsString()", "Array size negative", nase);
		} catch (FileNotFoundException fnfe) {
			log.logp(Level.SEVERE, "Utility", "readFileAsString()", "No file to read", fnfe);
		} catch (IOException ioe) {
			log.logp(Level.SEVERE, "Utility", "readFileAsString()", "IO problem", ioe);
		} catch (IndexOutOfBoundsException ioobe) {
			log.logp(Level.SEVERE, "Utility", "readFileAsString()", "Index of array was more than it should be", ioobe);
		}
		return null;
	}

	/**
	* Strip payload from document and write to file
	* @param xml the XML Document object containing the payload
	* @return byte[] An array of bytes containing payload bytes
	*/
	public static byte[] getPayload(Document xml) {
		try {
			Element root = xml.getDocumentElement();
			String val = root.getFirstChild().getNodeValue();
			return getB64Decoded(val);
		} catch (DOMException dome) {
			log.logp(Level.SEVERE, "Utility", "getPayoad()", "DOMException", dome);
		}
		return null;
	}

	/**
	* Add encrypted payload to outgoing xml file
	* @param payloadBytes An array of bytes that are to be put in the payload
	* @return Document A document object containing the payload
	*/
	public static Document createPayload(byte[] payloadBytes) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();
			Element root = doc.createElement("Payload");
			root.appendChild(doc.createTextNode(getB64Encoded(payloadBytes)));
			doc.appendChild(root);
			return doc;
		} catch (DOMException dome) {
			log.logp(Level.SEVERE, "Utility", "createPayload()", "DOMException", dome);
		} catch (ParserConfigurationException pce) {
			log.logp(Level.SEVERE, "Utility", "createPayload()", "Parser in bad state", pce);
		}
		return null;
	}

	/**
	* Write a byte array to file (no encoding)
	* @param ba the byte array to write
	* @param tempFileName the name of the file to write to
	*/
	public static void writeByteArrayToFile(byte[] ba, String tempFileName) {
		try {
			FileOutputStream fos = new FileOutputStream(tempFileName);
			fos.write(ba);
			fos.close();
		} catch (FileNotFoundException fnfe) {
			log.logp(Level.SEVERE, "Utility", "writeByteArrayToFile()", "File to write to not found", fnfe);
		} catch (SecurityException se) {
			log.logp(Level.SEVERE, "Utility", "writeByteArrayToFile()", "Security Violation", se);
		} catch (IOException ioe) {
			log.logp(Level.SEVERE, "Utility", "writeByteArrayToFile()", "IO problem", ioe);
		}
	}

	/**
	* Get the bytes in a DOM XML document
	* @param doc The Document whose bytes we need
	* @return byte[] The bytes from the Doc
	*/
	public static byte[] getXMLDataBytes(Document doc) {
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer bumblebee = tf.newTransformer();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			StreamResult sr = new StreamResult(bos);
			bumblebee.transform(new DOMSource(doc), sr);
			return bos.toByteArray();
		} catch (Exception e) {
			log.logp(Level.SEVERE, "Utility", "getXMLDataBytes()", "Something bad happened", e);
		}
		return null;
	}

}
