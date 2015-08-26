package ServerPrograms;

import CommonPackage.Connection;
import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class RHServer
{
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

	public void disconnect(Connection oldCon)
	{
		connections.remove(oldCon);
		System.out.println("Connection Removed : "+connections.size());
	}
}