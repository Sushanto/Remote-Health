package DoctorSide;

import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public Connection(String address,int port)
	{
		try
		{
			InetAddress inetAddress=InetAddress.getByName(address);
			clientSocket=new Socket(inetAddress,port);
			outStream=new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
			inStream=new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
			strWriter=new PrintWriter(clientSocket.getOutputStream(),true);
			strReader=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}
		catch(UnknownHostException uhe)
		{
			uhe.printStackTrace();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
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
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	public int receiveFromServer(String serverFileName,String localFileName)
	{
		try
		{
			sendString("SEND_FILE");
			sendString(serverFileName);
			if(receiveString().equals("RTS"))
			{
				receiveFile(localFileName);
				return 0;
			}
			else return -1;
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return -2;
		}
	}

	public int sendToServer(String localFileName,String serverFileName)
	{
		try
		{
			sendString("RECEIVE_FILE");
			sendString(serverFileName);
			if(receiveString().equals("CTS"))
			{
				sendFile(localFileName);
				return 0;
			}
			else return -1;
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return -2;
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

				outStream.writeInt((int)inFile.length());

				outStream.write(byteArray,0,byteArray.length);
				outStream.flush();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void receiveFile(String outFileName)
	{
		try
		{
			File outFile=new File(outFileName);

			// String[] tokens=outFileName.split("/");
			// String dirname="";
			// for(int i=0;i<tokens.length-1;i++)
			// 	dirname+=tokens[i]+"/";
			// File dir=new File(dirname);
			// if(!dir.exists())
			// 	dir.mkdirs();
			
			outFile.createNewFile();

			FileOutputStream ofStream=new FileOutputStream(outFile);
			BufferedOutputStream boStream=new BufferedOutputStream(ofStream);

			int fileLength=inStream.readInt();

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
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	public void sendString(String str)
	{
		strWriter.println(str);
		return;
	}

	public String receiveString()
	throws IOException
	{
		String str=strReader.readLine();
		if(str==null)
			throw new IOException();
		return str;
	}

	protected void finalize()
	{
		System.out.println("Garbage Collection: Connection");
	}
}