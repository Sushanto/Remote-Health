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

	private final static String SERVER="127.0.0.1";
	private int PORT=5000;
	private ServerSocket serverSocket;
	public static DoctorClient client;

	private static ArrayList<Connection> connections;

	public LocalServer()
	{
		try
		{
			serverSocket=new ServerSocket(PORT,1,InetAddress.getByName(SERVER));
			connections=new ArrayList<Connection>();
			client=new DoctorClient("01","10.10.149.110",36699);
			client.loginRequest("admin","admin");
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
	public static String finalDataPath = "final";
	public static String logintype = null;



/****************************************************************************************************************************/

}