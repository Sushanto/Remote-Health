package ServerCode;

import java.io.*;
import java.nio.file.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Date;
import java.text.SimpleDateFormat;


public class LocalServer
{
	protected static HashMap<String, String> filelocks;

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
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH:mm:ss");


	private static ArrayList<Connection> connections;

	private LocalServer()
	{
		System.out.println("_________________________________________________________________________________________________________");
		System.out.println("_________________________________________________________________________________________________________");
		System.out.println("_________________________________________________________________________________________________________");
		System.out.println("_________________________________________________________________________________________________________");
		readInfo();
		try
		{
			serverSocket = new ServerSocket(clientPort,1,InetAddress.getByName(clientHostName));
			connections = new ArrayList<Connection>();
			filelocks = new HashMap<String , String>();
			// client=new KioskClientSync(kioskId,serverHostName,serverPort,syncFolder);
			if(!(new File(finalDataPath).exists()))
				(new File(finalDataPath)).mkdirs();
			if(!(new File(tempDataPath).exists()))
				(new File(tempDataPath)).mkdirs();
			client = new KioskClient(kioskId,serverHostName,serverPort);
			client.loginRequest(loginUsername,loginPassword);
			try
			{
				client.initSecurityMeasure();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return;
			}
			System.out.println(dateFormat.format(new Date()) + "   " + "LocalServer\t> Local server is running....");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	/**
	* Lock a file for getting/editing so that some edit operation can finish
	* @param filename The name of the file to lock
	*/
	protected static synchronized int lockFile(String filename, String conId)
	{

		String lockerID = getLockerID(filename);
		if (lockerID != null && !conId.equals(lockerID))
			return RHErrors.RHE_OP_LOCKED;
		int response = -1;
		try
		{
			response = client.lockRequest(filename);
		}
		catch(Exception e)
		{
			return response;
		}
		if(response >= 0)
			filelocks.put(filename, conId);
		return response;
	}

	/**
	* Unlock a previously locked file so that edit operations can continue
	* @param filename the file to unlock
	*/
	protected static synchronized int unlockFile(String filename,String conId)
	{
		String lockerID = getLockerID(filename);
		if (lockerID != null && !conId.equals(lockerID))
			return RHErrors.RHE_OP_LOCKED;
		int response = -1;
		try
		{
			response = client.unlockRequest(filename);
		}
		catch(Exception e)
		{
			return response;
		}
		if(response >= 0)
			filelocks.remove(filename);
		return response;
	}

	/**
	* Remove all locks belonging to a connection given by ID
	* @param conId The ID of the connection
	*/
	protected static synchronized void removeLocks(String conId)
	{
		/* remove all entries that have conId as value */
		filelocks.values().removeAll(Collections.singleton(conId));
		for(HashMap.Entry<String,String> entry : filelocks.entrySet())
		{
			if(entry.getValue().equals(conId))
			{
				filelocks.remove(entry.getKey());
				try
				{
					client.unlockRequest(entry.getKey());
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return;
	}

	protected static synchronized String getLockerID(String filename)
	{
		return filelocks.get(filename);
	}

	private void readInfo()
	{
		try
		{
			FileReader fReader = new FileReader(new File("KioskMetadata.cfg"));
			BufferedReader bReader = new BufferedReader(fReader);
			System.out.println(dateFormat.format(new Date()) + "   " + "LocalServer\t> Kiosk Information reading....");

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
			System.out.println(dateFormat.format(new Date()) + "   " + "LocalServer\t> Listening.....");
			System.out.println("_________________________________________________________________________________________________________");

			Socket clientSocket = serverSocket.accept();
			Connection newCon = new Connection(clientSocket);
			connections.add(newCon);
			System.out.println(dateFormat.format(new Date()) + "   " + "LocalServer\t> Connection Added : " + connections.size());

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

	protected static synchronized void remove(Connection oldCon, String connectionId)
	{
		connections.remove(oldCon);
		oldCon = null;
		System.out.println(dateFormat.format(new Date()) + "   " + "LocalServer\t> Connection Removed : " + connectionId);
		System.out.println(dateFormat.format(new Date()) + "   " + "LocalServer\t> Total connections : " + connections.size());
		System.out.println("_________________________________________________________________________________________________________");
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