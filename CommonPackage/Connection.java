package CommonPackage;

import java.io.*;
import java.net.*;

public class Connection
{
	private Socket clientSocket;
	private PrintWriter strWriter;
	private BufferedReader strReader;
	private OutputStream outStream;
	private InputStream inStream;
	private static final int FILE_SIZE=Integer.MAX_VALUE;

	public Connection(Socket socket)
	{
		try
		{
			clientSocket=socket;
			outStream=clientSocket.getOutputStream();
			inStream=clientSocket.getInputStream();
			strWriter=new PrintWriter(outStream,true);
			strReader=new BufferedReader(new InputStreamReader(inStream));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void disconnect()
	{
		try
		{
			strReader.close();
			strWriter.close();
			inStream.close();
			outStream.close();
			clientSocket.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void sendFile(String inFileName)
	{
		try
		{
			File inFile=new File(inFileName);
			if(inFile.isFile())
			{
				FileInputStream ifStream=new FileInputStream(inFile);
				BufferedInputStream biStream=new BufferedInputStream(ifStream);

				byte[] byteArray=new byte[(int)inFile.length()];
				biStream.read(byteArray,0,byteArray.length);

				outStream.write(byteArray,0,byteArray.length);
				outStream.flush();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void receiveFile(String outFileName)
	{
		try
		{
			File outFile=new File(outFileName);
			outFile.createNewFile();

			FileOutputStream ofStream=new FileOutputStream(outFile);
			BufferedOutputStream boStream=new BufferedOutputStream(ofStream);

			byte[] byteArray=new byte[FILE_SIZE];
			int current=0,byteRead=0;

			// while(byteRead>0)
			{
				byteRead=inStream.read(byteArray,current,byteArray.length-current);
				if(byteRead>=0)
					current+=byteRead;
			}

			boStream.write(byteArray,0,current);
			boStream.flush();

			boStream.close();
			ofStream.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void sendString(String str)
	{
		strWriter.println(str);
		return;
	}

	public String receiveString()
	throws Exception
	{
		String str=strReader.readLine();
		if(str==null)
			throw new Exception();
		return str;
	}
}