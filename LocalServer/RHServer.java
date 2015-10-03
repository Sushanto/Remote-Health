package ServerCode;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class RHServer
{
	// ServerSocket serverSocket;

	// public static void main(String args[])
	// {

	// }

	private final static String SERVER="127.0.0.1";
	private int PORT=5000;
	private ServerSocket serverSocket;

	private ArrayList<Connection> connections;

	public RHServer()
	{
		try
		{
			serverSocket=new ServerSocket(PORT,1,InetAddress.getByName(SERVER));
			connections=new ArrayList<Connection>();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public RHServer(int port)
	{
		try
		{
			this.PORT=port;
			serverSocket=new ServerSocket(PORT,1,InetAddress.getByName(SERVER));
			connections=new ArrayList<Connection>();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public Connection listen()
	{
		try
		{
			Socket clientSocket=serverSocket.accept();
			Connection newCon=new Connection(clientSocket);
			connections.add(newCon);
			System.out.println("Connection Added : "+connections.size());
			return newCon;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public void remove(Connection oldCon)
	{
		connections.remove(oldCon);
		oldCon=null;
		System.out.println("Connection Removed : "+connections.size());
	}

	public static void main(String args[])
	{
		RHServer rhServer=new RHServer();
		while(true)
		{
			Connection mycon=rhServer.listen();
			mycon.start();
		}
	}

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
			catch(Exception e)
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

		public void run()
		{
			try
			{
				while(true)
				{
					if(receiveString().equals("SEND_FILE"))
						sendToClient();
					else receiveFromClient();
				}
			}
			catch(Exception e)
			{
				// e.printStackTrace();
				disconnect();
				remove(this);
			}
		}

		public boolean receiveFromClient()
		{
			try
			{
				String localFileName=receiveString();
				sendString("CTS");
				receiveFile(localFileName);
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
				if(new File(localFileName).isFile())
				{
					sendString("RTS");
					sendFile(localFileName);
					System.out.println("File sent: "+localFileName);
					return true;
				}
				else
				{
					sendString("NACK");
					return false;
				}
			}
			catch(Exception e)
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

		protected void finalize()
		{
			System.out.println("Garbage Collected: Connection");
		}
	}
}