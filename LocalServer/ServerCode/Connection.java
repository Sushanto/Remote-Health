package ServerCode;

import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.io.IOException;
import java.io.File;
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
					case "LOGIN":
						String username=receiveString();
						String password=receiveString();
						int response=handleCommand("login "+username+" "+password+" OP");
						if(response>0)
							sendString("LOGGED_IN");
						else sendString(RHErrors.getErrorDescription(response));
						break;
					case "LOGOUT":
						handleCommand("logout");
						sendString("LOGGED_OUT");
						break;
					case "NEW_LOGIN":
						String username=receiveString();
						String password=receiveString();
						int response=handleCommand("newlogin "+username+" "+password);
						if(response>0)
							sendString("NEW_USER_ADDED");
						else sendString(RHErrors.getErrorDescription(response));
						break;
					case "CHANGE_PASSWORD":
						String username=receiveString();
						String oldPassword=receiveString();
						String newPassword=receiveString()
						int response=handleCommand("chpasswd "+username+" "+oldPassword+" "+newPassword);
						if(response>0)
							sendString("PASSWORD_CHANGED");
						else sendString(RHErrors.getErrorDescription(response));
						break;
					case default:
						throw new IOException();
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
			String type=receiveString();
			sendString("CTS");
			receiveFile(LocalServer.tempDataPath+localFileName);
			if(type.equals("FINAL"))
			{
				int response=LocalServer.handleCommand("put "+localFileName);
				System.out.println("Handle command response : "+response);
			}
			System.out.println("File received: "+localFileName);
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
			if(new File(LocalServer.tempDataPath+localFileName).isFile())
			{
				sendString("RTS");
				sendFile(LocalServer.tempDataPath+localFileName);
				System.out.println("File sent: "+localFileName);
				return true;
			}
			else if(new File(LocalServer.finalDataPath+localFileName).isFile())
			{
				int response=LocalServer.handleCommand("get "+localFileName);
				if(response>0 && response==(int)(new File(LocalServer.finalDataPath+localFileName).length()))
				{
					sendString("RTS");
					sendFile(LocalServer.tempDataPath+localFileName);
					System.out.println("File sent: "+localFileName);
					return true;
				}
				else
				{
					sendString("NACK");
					return false;
				}
			}
			{
				sendString("NACK");
				return false;
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
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

			String[] tokens=outFileName.split("/");
			String dirname="";
			for(int i=0;i<tokens.length-1;i++)
				dirname+=tokens[i]+"/";
			File dir=new File(dirname);
			if(!dir.exists())
				dir.mkdirs();

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
		catch(IOException e)
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
}