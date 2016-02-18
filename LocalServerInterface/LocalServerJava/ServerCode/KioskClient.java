package ServerCode;

import java.net.Socket;
import java.net.UnknownHostException;

import java.util.Date;
import java.text.SimpleDateFormat;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.DirectoryNotEmptyException;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
*	KioskClient: a ClientConnection object wrapper for connecting with the server at the kiosk end
* 	@author Rounak Das
*/
public class KioskClient
{
	private ClientConnection con = null; 		/* The connection to the data server (if any) */
	private String mode = null;	 		/* Either "DS"(directly connected to data server) or "GD"(connected via google drive) */
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
	private RHClientSecurity rhcs;

	/**
	* Initiate a kiosk object with an ID(specific to machine), server name, port and syncfolder location
	* @param id The ID of the machine of the kiosk
	* @param serverHostName The hostname of the data server
	* @param port The remote health application port number
	*/
	KioskClient(String id, String serverHostName, int port)
	{
		Socket sock = connectToServer(serverHostName, port);
		if (sock != null) {
			this.con = new ClientConnection(sock, id);
			System.out.println(df.format(new Date()) + "   LocalServer\t> Set KC mode to DS");
			this.mode = "DS";
		} else {
			System.out.println(df.format(new Date()) + "   LocalServer\t> Set KC mode to GD");
			this.mode = "GD";
		}
		this.rhcs = null;
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
	* Utility function for moving a file from one location to another
	* @param source source path (including filename)
	* @param destination destination path (including filename)
	* @return File a File object of the moved file
	* @throws DirectoryNotEmptyException see java.nio.files.Files.move()
	* @throws IOException see java.nio.files.Files.move()
	* @throws SecurityException see java.nio.files.Files.move()
	**/
	private File moveFile(String source, String destination) throws DirectoryNotEmptyException, IOException, SecurityException
	{
		Path src = Paths.get(source);
		Path dst = Paths.get(destination);
		Files.move(src, dst, StandardCopyOption.REPLACE_EXISTING);
		File copied = dst.toFile();
		return copied;
	}

	/**
	* Decode a base64 String encoded file to normal file
	* @param inFileName Name of input file
	*/
	private void decodeFile(String inFileName)
	{
		FileConverter.decodeFile(inFileName, inFileName + ".tmp");
		try {
			moveFile(inFileName + ".tmp", inFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	* Encode file to base64 string, then put it in a XML file
	* @param inFileName Name of input file
	* @return File The file object of the output file
	*/
	private File encodeFile(String inFileName)
	{
		FileConverter.encodeFile(inFileName, inFileName + ".tmp");
		return new File(inFileName + ".tmp");
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

	/*
	* Request keys from the server (DS or GD)
	* @return String The Base64 encoded key string, or null in case of error
	*/
	private String getEncryptionKey() {
		if (this.mode.equals("DS")) {
			String command = "key";
			con.sendString(command);
			try {
				String keystr = con.receiveString();
				return keystr;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		} else if (this.mode.equals("GD")) {
			int resp = runGDScript(new String[]{"key", "key.tmp"});
			if (resp >= 0) {
				try {
					File tmpf = new File("key.tmp");
					BufferedReader br = new BufferedReader(new FileReader(tmpf));
		                        String keystr = br.readLine();
		                        br.close();
					tmpf.delete();
					return keystr;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		return null;
	}

	/*
	* Initialize Client Security module
	* @return Error code, or 0 if no error
	* @throws Exception from RHCS
	*/
	public int initSecurityMeasure() throws Exception {
		this.rhcs = new RHClientSecurity();
		String keystr = getEncryptionKey();
		if (keystr == null) {	//Request failed
			return RHErrors.RHE_GENERAL;
		} else if (keystr.length() < 24) {	//less than AES min key Length
			return Integer.parseInt(keystr);	//actually a response code
		}
		this.rhcs.loadKey(keystr);
		return 0;
	}

	/**
	* Do a get request to the data server.
	* @param serverFileName File name to get at server
	* @param localFileName File name to store as locally
	* @return int Size of received file or error number
	* @throws Exception if socket was closed before receiving reply,
	* in case of error, try to reconnect by re-making the KioskClient object
	*/
	public int getRequest(String serverFileName, String localFileName) throws Exception
	{
		if (this.rhcs == null) {
			return RHErrors.RHE_NOTSECURE;
		}
		if (this.mode.equals("DS")) {
			String command = "get " + serverFileName;
			con.sendString(command);
			int resp = con.receiveInt();
			if (resp > 0) {
				con.receiveFileln(localFileName, resp);
				this.rhcs.decryptFile(localFileName);
				decodeFile(localFileName);
			}
			return resp;
		} else {
			System.out.println(df.format(new Date()) + "   " + "LocalServer\t> get(): Falling back to GD script");
			int resp = runGDScript(new String[]{"get", serverFileName, localFileName});
			this.rhcs.decryptFile(localFileName);
			decodeFile(localFileName);
			return resp;
		}
	}

	/**
	* Do a put request to the data server.
	* @param serverFileName File name at server
	* @param localFileName File name to send locally
	* @return int 0 or error number
	* @throws Exception if socket was closed before receiving reply,
	* in case of error, try to reconnect by re-making the KioskClient object
	*/
	public int putRequest(String localFileName, String serverFileName, boolean newfile) throws Exception
	{
		if (this.rhcs == null) {
			return RHErrors.RHE_NOTSECURE;
		}
		File localFile=new File(localFileName);
		if (this.mode.equals("DS")) {
			String lenstr = Integer.toString((int)localFile.length());
			String command = "put " + serverFileName + " " + lenstr;
			int resp = RHErrors.RHE_GENERAL;
			if (localFile.exists()) {
				con.sendString(command);
				encodeFile(localFileName);
				this.rhcs.encryptFile(localFileName + ".tmp");
				con.sendFileln(new File(localFileName + ".tmp"));
				resp = con.receiveInt();
				new File(localFileName + ".tmp").delete();
			}
			return resp;
		} else {
			System.out.println("put(): Falling back to GD script");
			File ef = encodeFile(localFileName);
			this.rhcs.encryptFile(localFileName + ".tmp");
			int resp = RHErrors.RHE_GENERAL;
			if (!newfile){
				resp = runGDScript(new String[]{"put", serverFileName, localFileName + ".tmp"});
			} else {
				resp = runGDScript(new String[]{"new", serverFileName, localFileName + ".tmp"});
			}
			ef.delete();
			return resp;
		}
	}

	/**
	* Do a login request to the data server.
	* @param userid Users ID
	* @param passwd Users Password
	* @return int 0 or error number
	* @throws Exception if socket was closed before receiving reply,
	* in case of error, try to reconnect by re-making the KioskClient object
	*/
	public int loginRequest(String userid, String passwd) throws Exception
	{
		if (this.mode.equals("DS")) {
			String command = "login " + userid + " " + passwd;
			con.sendString(command);
			int resp = con.receiveInt();
			return resp;
		} else {
			/* There is no proper authentication method yet for when we are not connected to the server
			   The script for GoogleDrive has methods for getting authentication links
			   That should be used for presenting a nice way for the user to login to the GoogleDrive account
			   currently the script can not handle saving credentials in this cause
			   In this current design that can also not be fixed.
			   The best way to fix this is to use the script from the command line for that one time
			*/
			return 0;
		}
	}

	/**
	* Do a login request to the data server.
	* @return int 0 or error number
	* @throws Exception if socket was closed before receiving reply,
	* in case of error, try to reconnect by re-making the KioskClient object
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
	* in case of error, try to reconnect by re-making the KioskClient object
	*/
	public int chpasswdRequest(String userid, String oldpasswd, String newpasswd) throws Exception
	{
		if (this.mode.equals("DS")) {
			String command = "chpasswd "+ oldpasswd + " " + newpasswd;
			con.sendString(command);
			int resp = con.receiveInt();
			return resp;
		}
		return 0;
	}

	/*
	* Lock a file in the server for editing
	* @param serverFileName File at server to lock
	* @return int The response code of the request
	* @throws Exception In case the server closed the Connection
	*/
	public int lockRequest(String serverFileName) throws Exception
	{
		if (this.mode.equals("DS")) {
			String command = "lock " + serverFileName;
			con.sendString(command);
			int resp = con.receiveInt();
			return resp;
		}
		return 0;
	}

	/*
	* Unlock a file after editing it and sending it to the server
	* @param serverFileName File name at server to unlock
	* @return int Response code of the request
	* @throws Exception In case the server closed the Connection
	*/
	public int unlockRequest(String serverFileName) throws Exception
	{
		if (this.mode.equals("DS")) {
			String command = "unlock " + serverFileName;
			con.sendString(command);
			int resp = con.receiveInt();
			return resp;
		}
		return 0;
	}
}
