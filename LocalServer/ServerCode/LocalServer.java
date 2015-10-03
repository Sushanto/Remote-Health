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

	private static ArrayList<Connection> connections;

	public LocalServer()
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

	public LocalServer(int port)
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
	public static ClientConnection con;
	public static String tempDataPath = "final/";
	public static String finalDataPath = "temp/";
	public static String logintype = null;

	public static int moveFile(String source,String destination)
	{
		try
		{
			Path src=Paths.get(source);
			Path dest=Paths.get(destination);
			Files.move(src,dest,StandardCopyOption.REPLACE_EXISTING);
			File copied=dest.toFile();
			return (int)copied.length();
		}
		catch(DirectoryNotEmptyException dnee)
		{
			dnee.printStackTrace();
			return RHErrors.RHE_GENERAL;
		}
		catch(SecurityException se)
		{
			se.printStackTrace();
			return RHErrors.RHE_NOPERM;
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return RHErrors.RHE_IOE;
		}
	}

	public static Socket connectToServer(String serverHostName, int port)
	{
		try {
			Socket mysock = new Socket(serverHostName, port);
			return mysock;
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SecurityException se) {
			se.printStackTrace();
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
		}
		return null;
	}

	public static int handleCommand(String command) throws Exception
	{
		int resp = RHErrors.RHE_GENERAL;
		String[] tokens = command.split("\\s+");
		switch (tokens[0]) {
		case "get":
			con.sendString(command);
			resp = con.receiveInt();
			if (resp > 0)
				if(logintype != null && logintype.equals("DOC")) {
					con.receiveFile(tempDataPath + "got_" + tokens[1]);
				}
				else if(logintype!=null && logintype.equals("OP")
				{
					int max=900;
					File file=new File(finalDataPath+tokens[1]);
					while(file.isFile() && resp!=(int)file.length()) && (max--)!=0)
						Thread.sleep(1000);
				}
			return resp;
		case "put":
			File tosend = new File(tempDataPath + tokens[1]);
			if (tosend.exists() && tosend.isFile()) {
				String lenstr = Integer.toString((int)tosend.length());
				command += (" " + lenstr);
			} else {
				return RHErrors.RHE_FNF;
			}
			con.sendString(command);
			if (logintype != null && logintype.equals("DOC")) {
				con.sendFile(tosend);
			} else {
				moveFile(tempDataPath+tokens[1],finalDataPath+tokens[1]);
			}
			resp = con.receiveInt();
			return resp;
		case "login":
		case "newlogin":
		case "chpasswd":
		case "logout":
			con.sendString(command);
			resp = con.receiveInt();
			if (tokens[0].equals("login"))
				logintype = tokens[3];
			if (tokens[0].equals("logout"))
				logintype = null;
			break;
		default:
		}
		return resp;
	}

/****************************************************************************************************************************/

}