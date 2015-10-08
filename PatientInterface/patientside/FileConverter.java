package patientside;

import java.io.*;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;
import javax.xml.bind.annotation.*;
import javax.xml.bind.*;


public class FileConverter
{
	public static int decodeFile(String inFileName,String outFileName)
	{
		try
		{
			File inFile = new File(inFileName);
			JAXBContext jc = JAXBContext.newInstance(FileData.class);
			Unmarshaller jum = jc.createUnmarshaller();
			FileData fileData = (FileData)jum.unmarshal(inFile);

			String encodedStr = fileData.getBinaryData();
			byte[] byteArray;

            BASE64Decoder decoder = new BASE64Decoder();
            byteArray = decoder.decodeBuffer(encodedStr);

            File outFile = new File(outFileName);
            FileOutputStream ofStream = new FileOutputStream(outFile);
            BufferedOutputStream boStream = new BufferedOutputStream(ofStream);
            boStream.write(byteArray,0,byteArray.length);
            boStream.flush();
            boStream.close();
            ofStream.close();
            return 0;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}


	public static int encodeFile(String inFileName,String outFileName)
	{
		try
		{
			File inFile = new File(inFileName);
			FileInputStream ifStream = new FileInputStream(inFile);
			BufferedInputStream biStream = new BufferedInputStream(ifStream);

			byte[] byteArray = new byte[(int)inFile.length()];
			biStream.read(byteArray,0,byteArray.length);
			biStream.close();
			ifStream.close();

            BASE64Encoder encoder = new BASE64Encoder();
            String encodedStr = encoder.encode(byteArray);

            FileData fileData = new FileData();
            fileData.setBinaryData(encodedStr);

            File outFile = new File(outFileName);

            JAXBContext jc = JAXBContext.newInstance(FileData.class);
            Marshaller jm = jc.createMarshaller();
            jm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            jm.marshal(fileData,outFile);
            return 0;
        }
        catch (Exception e)
        {
           e.printStackTrace();
           return -1;
        }
	}
}


@XmlRootElement(name="FileData")
@XmlType(propOrder={"binaryData"})
class FileData
{
	String binaryData;

	public String getBinaryData()
	{
		return binaryData;
	}
	@XmlElement(name="BinaryData")
	public void setBinaryData(String binaryData)
	{
		this.binaryData = binaryData;
	}
}