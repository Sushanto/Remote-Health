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

	protected static String kioskId;
	protected static String serverHostName;
	protected static int serverPort;
	protected static String syncFolder = "final/Kiosk_01/";

	protected static String loginUsername;
	protected static String loginPassword;

	private ServerSocket serverSocket;
	// protected static KioskClientSync client;
	protected static KioskClient client;

	protected static String tempDataPath = "temp";
	protected static String finalDataPath = "final/Kiosk_01";
	protected static String logintype = null;


	private static ArrayList<Connection> connections;

	private LocalServer()
	{
		readInfo();
		try
		{
			serverSocket = new ServerSocket(clientPort,1,InetAddress.getByName(clientHostName));
			connections = new ArrayList<Connection>();
			// client=new KioskClientSync(kioskId,serverHostName,serverPort,syncFolder);
			client = new KioskClient(kioskId,serverHostName,serverPort,syncFolder);
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
			FileReader fReader = new FileReader(new File("KioskMetadata.cfg"));
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
						String[] path = tokens[1].split("/");
						finalDataPath = path[0];
						for(int i = 1; i < path.length; i++)
							finalDataPath += "/" + path[i];
						syncFolder = tokens[1];
						break;
					case "TEMP_FOLDER":
						String[] tempPath = tokens[1].split("/");
						tempDataPath = tempPath[0];
						for(int i = 1; i < tempPath.length; i++)
							tempDataPath += "/" + tempPath[i];
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

	private Connection listen()
	{
		try
		{
			System.out.println("Listening.....");

			Socket clientSocket = serverSocket.accept();
			Connection newCon = new Connection(clientSocket);
			connections.add(newCon);
			System.out.println("Connection Added : " + connections.size());

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

	protected static synchronized void remove(Connection oldCon)
	{
		connections.remove(oldCon);
		oldCon = null;
		System.out.println("Connection Removed : "+connections.size());
	}

	public static void main(String args[])
	{
		LocalServer rhServer = new LocalServer();
		while(true)
		{
			Connection mycon = rhServer.listen();
			mycon.start();
		}
	}

}