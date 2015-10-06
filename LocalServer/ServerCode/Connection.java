package ServerCode;

import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.io.IOException;
import java.io.EOFException;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

class Connection extends Thread
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
		catch(IOException e)
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
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		try
		{
			while(true)
			{
				String request=receiveString();
				switch(request)
				{
					case "SEND_FILE":
						sendToClient();
						break;
					case "RECEIVE_FILE":
						receiveFromClient();
						break;
				}
			}
		}
		catch(IOException e)
		{
			// e.printStackTrace();
			disconnect();
			LocalServer.remove(this);
		}
	}

	public boolean receiveFromClient()
	{
		try
		{
			String localFileName=receiveString();
			String[] fileInfo=localFileName.split(" ");
			String[] folders=fileInfo[0].split("/");
			System.out.println("File received: "+localFileName);
			receiveFile1(fileInfo[0],Integer.parseInt(fileInfo[1]));
			sendInt(0);
			if(folders[0].equals(LocalServer.finalDataPath))
			{
				File file=new File(LocalServer.tempDataPath+"/"+folders[1]);
				if(file.isFile())
					file.delete();
				LocalServer.client.putRequest(fileInfo[0],folders[1]);
			}
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean sendToClient()
	{
		try
		{
			String localFileName=receiveString();
			File tempFile = new File(LocalServer.tempDataPath+"/"+localFileName);
			if(tempFile.isFile())
			{
				sendInt((int)tempFile.length());
				sendFile1(tempFile);
				System.out.println("File sent: "+localFileName);
				return true;
			}
			else
			{
				int response=LocalServer.client.getRequest(localFileName,LocalServer.finalDataPath+"/"+localFileName);
				System.out.println("Response: "+response);
				File finalFile= new File(LocalServer.finalDataPath+"/"+localFileName);
				if(response>=0 && response==(int)finalFile.length())
				{
					sendInt(response);
					sendFile1(finalFile);
					System.out.println("File sent: "+localFileName);
					return true;
				}
				else
				{
					sendInt(response);
					return false;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public int sendFile1(File inFile)
	{
		try
		{
			FileReader fReader = new FileReader(inFile);
			BufferedReader bReader= new BufferedReader(fReader);

			String line="";

			sendString("FILE_START");
			while((line=bReader.readLine())!=null)
			{
				sendString(line);
			}
			sendString("FILE_END");
			bReader.close();
			fReader.close();
			return 0;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}

	public int sendFile(File inFile)
	{
		try {
			FileInputStream ifStream = new FileInputStream(inFile);
			BufferedInputStream biStream = new BufferedInputStream(ifStream);

			byte[] fileBuffer = new byte[(int)inFile.length()];
			biStream.read(fileBuffer, 0, fileBuffer.length);

			outStream.write(fileBuffer, 0, fileBuffer.length);
			outStream.flush();
			System.out.println("return from sendFile");
			return fileBuffer.length;
		} catch(IOException e) {
			e.printStackTrace();
			return RHErrors.RHE_IOE;
		}
	}

	public int receiveFile1(String outFileName, int fileLength)
	{
		try
		{
			File outFile = new File(outFileName);
			FileWriter fWriter = new FileWriter(outFile);
			PrintWriter pWriter = new PrintWriter(outFile);

			String reply="";
			reply= receiveString();
			if(reply.equals("FILE_START"))
			{
				reply=receiveString();
				pWriter.print(reply);

				while(!(reply=receiveString()).equals("FILE_END"))
					pWriter.print("\n"+reply);
			}

			pWriter.close();
			return 0;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}

	public int receiveFile(String outFileName, int fileLength)
	{
		try {
			int origFileLength = fileLength;
			/* Send the OK to send */
			this.sendInt(0);
			System.out.println("Sent zero OK, fileLength = " + fileLength);
			File outFile = new File(outFileName);
			outFile.getParentFile().mkdirs();
			outFile.createNewFile();
			FileOutputStream ofStream = new FileOutputStream(outFile);
			BufferedOutputStream boStream = new BufferedOutputStream(ofStream);

			byte[] fileBuffer = new byte[fileLength];
			int byteRead = 0;

			while((fileLength > 0) && (byteRead = inStream.read(fileBuffer, 0, Math.min(fileBuffer.length, fileLength))) != -1) {
				System.out.println("Inside read loop: " + byteRead + ", " + byteRead);
				boStream.write(fileBuffer, 0, byteRead);
				fileLength -= byteRead;
			}

			boStream.flush();
			boStream.close();
			ofStream.close();

			return origFileLength;
		} catch(IOException e) {
			e.printStackTrace();
			return RHErrors.RHE_IOE;
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
		System.out.println("Garbage Collected: Connection");
	}

	public boolean login()
	{
		try
		{
			if(receiveString().equals("LOGIN"))
			{
				String username=receiveString();
				String password=receiveString();
				sendString("LOGGED_IN");
				return true;
			}
			else return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public int sendInt(int val)
	{
		sendString(Integer.toString(val));
		return 0;
	}

	/*
	* Receive an integer from the client
	* @return the received int or error number
	* @throws Exception if a null string was received
	*/
	public int receiveInt() throws Exception {
		int val = RHErrors.RHE_GENERAL;
		try {
			String str = receiveString();
			val = Integer.parseInt(str);
		} catch (EOFException eofe) {
			eofe.printStackTrace();
			return RHErrors.RHE_IOE;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return RHErrors.RHE_IOE;
		} catch (Exception e) {
			throw new Exception("Received null string");
		}
		return val;
	}
}