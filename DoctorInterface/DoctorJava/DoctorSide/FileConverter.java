package DoctorSide;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Base64;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;



/**
* Convert binary files to xml binary data for easier transmission
*/
public class FileConverter {

	/**
	* Decodes a file.
	* @param inFileName Name of input file
	* @param outFileName Name of output file
	*/
	public static void decodeFile(String inFileName, String outFileName)
	{
		try {
			File inFile = new File(inFileName);
			JAXBContext jc = JAXBContext.newInstance(FileData.class);
			Unmarshaller jum = jc.createUnmarshaller();
			FileData fileData = (FileData)jum.unmarshal(inFile);

			String encodedStr = fileData.getBinaryData();
			byte[] byteArray;

			/*BASE64Decoder decoder = new BASE64Decoder();
			byteArray = decoder.decodeBuffer(encodedStr);*/
			byteArray = Base64.getDecoder().decode(encodedStr);

			File outFile = new File(outFileName);
			FileOutputStream ofStream = new FileOutputStream(outFile);
			BufferedOutputStream boStream = new BufferedOutputStream(ofStream);
			boStream.write(byteArray, 0, byteArray.length);
			boStream.flush();
			boStream.close();
			ofStream.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch(JAXBException jaxbe) {
			jaxbe.printStackTrace();
		}
	}

	/**
	* Encode a file
	* @param inFileName name of encoded file
	* @param outFileName name of output file
	*/
	public static void encodeFile(String inFileName, String outFileName)
	{
		try {
			File inFile = new File(inFileName);
			FileInputStream ifStream = new FileInputStream(inFile);
			BufferedInputStream biStream = new BufferedInputStream(ifStream);

			byte[] byteArray = new byte[(int)inFile.length()];
			biStream.read(byteArray,0,byteArray.length);
			biStream.close();
			ifStream.close();

			/*BASE64Encoder encoder = new BASE64Encoder();
			String encodedStr = encoder.encode(byteArray);*/
			String encodedStr = Base64.getEncoder().encodeToString(byteArray);

			FileData fileData = new FileData();
			fileData.setBinaryData(encodedStr);

			File outFile = new File(outFileName);

			JAXBContext jc = JAXBContext.newInstance(FileData.class);
			Marshaller jm = jc.createMarshaller();
			jm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
			jm.marshal(fileData, outFile);
	        } catch (IOException ioe) {
			ioe.printStackTrace();
		} catch(JAXBException jaxbe) {
			jaxbe.printStackTrace();
		}
	}
}


@XmlRootElement(name="FileData")
@XmlType(propOrder={"binaryData"})
class FileData
{
	String binaryData;
	public String getBinaryData() {
		return binaryData;
	}

	@XmlElement(name="BinaryData")
	public void setBinaryData(String binaryData) {
		this.binaryData = binaryData;
	}
}
