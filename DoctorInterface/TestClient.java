import java.net.Socket;
import java.net.UnknownHostException;

import java.util.Scanner;

import java.io.File;
import commons.RHErrors;

import java.io.IOException;

public class TestClient
{
	private static ClientConnection con;
	private static String datapath = "client/";
	private static String logintype = null;

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
			if (resp > 0 && logintype != null && logintype.equals("DOC")) {
				con.receiveFile(datapath + tokens[1]);
			}
			return resp;
		case "put":
			File tosend = new File(datapath + tokens[1]);
			if (tosend.exists() && tosend.isFile()) {
				String lenstr = Integer.toString((int)tosend.length());
				command += (" " + lenstr);
			} else {
				return RHErrors.RHE_FNF;
			}
			con.sendString(command);
			if (logintype != null && logintype.equals("DOC")) {
				con.sendFile(tosend);
			} else if (logintype != null) {
				/* copy file to shared folder here */
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

	public static void main(String args[])
	{
		if (args.length < 3) {
			System.err.println("Give me an ID, IP, port please!");
			System.err.println("java TestClient <ID> <serverIP> <serverPort>");
			System.exit(1);
		}
		Socket mysock = connectToServer(args[1], Integer.parseInt(args[2]));
		con = new ClientConnection(mysock, args[0]);
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("Enter command for server: ");
			String command = sc.nextLine();
			try {
				int ans = handleCommand(command);
				System.out.println("Server reply: " + ans);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
