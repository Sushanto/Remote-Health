package ServerCode;

import java.io.*;
import java.nio.file.*;
import java.net.*;
import java.util.ArrayList;

public class LocalServer
{
	// ServerSocket serverSocket;

	// public static void main(String args[])
	// {

	// }

	private String clientHostName;
	private int clientPort;

	public static String kioskId;
	public static String serverHostName;
	public static int serverPort;
	public static String syncFolder;

	public static String loginUsername;
	public static String loginPassword;

	private ServerSocket serverSocket;
	public static KioskClient client;


	private static ArrayList<Connection> connections;

	public LocalServer()
	{
		readInfo();
		try
		{
			serverSocket=new ServerSocket(clientPort,1,InetAddress.getByName(clientHostName));
			connections=new ArrayList<Connection>();
			client=new KioskClient(kioskId,serverHostName,serverPort,syncFolder);
			client.loginRequest(loginUsername,loginPassword);
			System.out.println("Local server is running....");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void readInfo()
	{
		try
		{
			FileReader fReader = new FileReader(new File("ServerCode/KioskInfo.gpg"));
			BufferedReader bReader = new BufferedReader(fReader);
			System.out.println("Kiosk Information reading....");

			String line;
			while((line=bReader.readLine())!=null)
			{
				String[] tokens = line.split("=");
				switch(tokens[0])
				{
					case "CLIENT_HOST_NAME":
						clientHostName = tokens[1];
						break;
					case "CLIENT_PORT":
						clientPort = Integer.parseInt(tokens[1]);
						break;
					case "KIOSK_ID":
						kioskId = tokens[1];
						break;
					case "SERVER_HOST_NAME":
						serverHostName = tokens[1];
						break;
					case "SERVER_PORT":
						serverPort = Integer.parseInt(tokens[1]);
						break;
					case "SYNC_FOLDER":
						syncFolder = tokens[1];
						break;
					case "LOGIN_ID":
						loginUsername = tokens[1];
						break;
					case "LOGIN_PASSWORD":
						loginPassword = tokens[1];
						break;
					default:
						break;
				}
			}
			bReader.close();
			fReader.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	public Connection listen()
	{
		try
		{
			System.out.println("Listening.....");

			Socket clientSocket=serverSocket.accept();
			Connection newCon=new Connection(clientSocket);
			connections.add(newCon);
			System.out.println("Connection Added : "+connections.size());

			if(!newCon.login())
				newCon.disconnect();
			return newCon;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static synchronized void remove(Connection oldCon)
	{
		connections.remove(oldCon);
		oldCon=null;
		System.out.println("Connection Removed : "+connections.size());
	}

	public static void main(String args[])
	{
		LocalServer rhServer=new LocalServer();
		while(true)
		{
			Connection mycon=rhServer.listen();
			mycon.start();
		}
	}

/***********************************************************************************************************************************/
	public static String tempDataPath = "temp";
	public static String finalDataPath = "final/Kiosk_01";
	public static String logintype = null;



/****************************************************************************************************************************/

}