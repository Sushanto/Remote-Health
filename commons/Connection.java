package commons;

import java.io.*;
import java.net.*;

public class Connection
{
	private Socket clientSocket;
	private PrintWriter strWriter;
	private BufferedReader strReader;
	private DataOutputStream outStream;
	private DataInputStream inStream;
	private static final int FILE_SIZE=6022386;

	public Connection(Socket socket)
	{
		try
		{
			clientSocket=socket;
			outStream=new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
			inStream=new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
			strWriter=new PrintWriter(clientSocket.getOutputStream(),true);
			strReader=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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

				outStream.writeLong((long)inFile.length());

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

			long fileLength=inStream.readLong();

			byte[] byteArray=new byte[FILE_SIZE];
			int byteRead=0;

			while(fileLength>0 && (byteRead=inStream.read(byteArray,0,(int)Math.min(byteArray.length,fileLength)))!=-1)
			{
				boStream.write(byteArray,0,byteRead);
				fileLength-=byteRead;
			}

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
