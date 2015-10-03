package DoctorSide;

import java.net.Socket;
import java.net.UnknownHostException;

import java.util.Scanner;

import java.io.File;
// import commons.RHErrors;

import java.io.IOException;

/**
*	DoctorClient: a ClientConnection object wrapper for connecting with the server at the doctor end
*/
public class DoctorClient
{
	private ClientConnection con = null; 		/* The connection to the data server (if any) */
	private String mode = null;	 		/* Either "DS"(directly connected to data server) or "GD" (connected via google drive) */
	private static String logintype = "DOC";	/* This will only run at the doctor end */

	/**
	* Initiate a doctor object with an ID(specific to machine), server name and port
	* @param id The ID of the machine of the doctor
	* @param serverHostName The hostname of the data server
	* @param port The remote health application port number
	*/
	DoctorClient(String id, String serverHostName, int port)
	{
		Socket sock = connectToServer(serverHostName, port);
		if (sock != null) {
			this.con = new ClientConnection(sock, id);
			this.mode = "DS";
		} else {
			this.mode = "GD";
		}
	}

	/**
	* Connect to the data server
	* @param serverHostName The host name of the server
	* @param port The remote health port number
	*/
	private Socket connectToServer(String serverHostName, int port)
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

	/**
	* Run the Google Drive python script
	* @param commandArguments Command and arguments to pass to the script, the command name and script name is excluded
	* so just {get, file, file} for a get command is required
	* @return int The scripts exit value ir error number
	*/
	private int runGDScript(String[] commandArguments)
	{
		Runtime r = Runtime.getRuntime();
		Process gdproc = null;
		String[] command = new String[commandArguments.length + 2];
		command[0] = "python";
		command[1] = "rh_gdrive.py";
		for (int i = 2, j = 0; j < commandArguments.length; i++, j++) {
			command[i] = commandArguments[j];
		}
		try {
			gdproc = r.exec(command);
			int retval = gdproc.waitFor();
			if (retval > 0)
				return RHErrors.RHE_SUBPROC;
			else
				return 0;
		} catch (SecurityException se) {
			se.printStackTrace();
			return RHErrors.RHE_GENERAL;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return RHErrors.RHE_IOE;
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			return RHErrors.RHE_NULL;
		} catch (IndexOutOfBoundsException ioobe) {
			ioobe.printStackTrace();
			return RHErrors.RHE_GENERAL;
		} catch (InterruptedException ie) {
			ie.printStackTrace();
			return RHErrors.RHE_SUBPROC;
		}
	}

	/**
	* Do a get request to the data server.
	* @param serverFileName File name to get at server
	* @param localFileName File name to store as locally
	* @return int Sizeo f received file or error number
	* @throws Exception if socket was closed before receiving reply,
	* in case of error, try to reconnect by re-making the DoctorClient object
	*/
	public int getRequest(String serverFileName, String localFileName) throws Exception
	{
		if (this.mode.equals("DS")) {
			String command = "get " + serverFileName;// + " " + localFileName;
			con.sendString(command);
			int resp = con.receiveInt();
			if (resp > 0)
				con.receiveFile(localFileName);
			return resp;
		} else {
			return runGDScript(new String[]{"get", serverFileName, localFileName});
		}
	}

	/**
	* Do a put request to the data server.
	* @param serverFileName File name at server
	* @param localFileName File name to send locally
	* @return int 0 or error number
	* @throws Exception if socket was closed before receiving reply,
	* in case of error, try to reconnect by re-making the DoctorClient object
	*/
	public int putRequest(String localFileName, String serverFileName) throws Exception
	{
		File localFile=new File(localFileName);
		if (this.mode.equals("DS")) {
			String lenstr = Integer.toString((int)localFile.length());
			String command = "put " + serverFileName + " " + lenstr;
			int resp = RHErrors.RHE_GENERAL;
			if (localFile.exists()) {
				con.sendString(command);
				con.sendFile(localFile);
				resp = con.receiveInt();
			}
			return resp;
		} else {
			return runGDScript(new String[]{"put", serverFileName, localFileName});
		}
	}

	/**
	* Do a login request to the data server.
	* @param userid Users ID
	* @param passwd Users Password
	* @return int 0 or error number
	* @throws Exception if socket was closed before receiving reply,
	* in case of error, try to reconnect by re-making the DoctorClient object
	*/
	public int loginRequest(String userid, String passwd) throws Exception
	{
		if (this.mode.equals("DS")) {
			String command = "login " + userid + " " + passwd + " " + logintype;
			con.sendString(command);
			int resp = con.receiveInt();
			return resp;
		} else {
			/* TODO: add proper authentication mechanism incase stored credentials are missing */
			return 0;
		}
	}

	/**
	* Do a login request to the data server.
	* @return int 0 or error number
	* @throws Exception if socket was closed before receiving reply,
	* in case of error, try to reconnect by re-making the DoctorClient object
	*/
	public int logoutRequest() throws Exception
	{
		if (this.mode.equals("DS")) {
			String command = "logout";
			con.sendString(command);
			int resp = con.receiveInt();
			con.disconnect();
			return resp;
		}
		return 0;
	}

	/**
	* Do a login request to the data server.
	* @param userid Users ID
	* @param oldpasswd Users old Password
	* @param newpasswd users new password
	* @return int 0 or error number
	* @throws Exception if socket was closed before receiving reply,
	* in case of error, try to reconnect by re-making the DoctorClient object
	*/
	public int chpasswdRequest(String userid, String oldpasswd, String newpasswd) throws Exception
	{
		if (this.mode.equals("DS")) {
			String command = "chpasswd " + oldpasswd + " " + newpasswd;
			con.sendString(command);
			int resp = con.receiveInt();
			return resp;
		}
		return 0;
	}
}
