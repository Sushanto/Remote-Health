package ServerCode;

import java.net.Socket;
import java.net.UnknownHostException;

import java.util.Scanner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.DirectoryNotEmptyException;

import java.io.File;


import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;

/**
*	DoctorClient: a ClientConnection object wrapper for connecting with the server at the doctor end
*/
public class KioskClient
{
	private ClientConnection con = null; 		/* The connection to the data server (if any) */
	private String mode = null;	 		/* Either "DS"(directly connected to data server) or "GD" (connected via google drive) */
	private String syncpath = null;			/* Where to put sync files */
	private static String logintype = "KOP";	/* This will only run at the kiosk end */

	/**
	* Initiate a kiosk object with an ID(specific to machine), server name, port and syncfolder location
	* @param id The ID of the machine of the kiosk
	* @param serverHostName The hostname of the data server
	* @param port The remote health application port number
	*/
	KioskClient(String id, String serverHostName, int port, String syncfolder)
	{
		Socket sock = connectToServer(serverHostName, port);
		if (sock != null) {
			this.con = new ClientConnection(sock, id);
			System.out.println("Set DC mode to DS");
			this.mode = "DS";
		} else {
			System.out.println("Set DC mode to GD");
			this.mode = "GD";
		}
		this.syncpath = syncfolder;
	}


	public String getMode()
	{
		return mode;
	}


	/**
	* Connect to the data server
	* @param serverHostName The host name of the server
	* @param port The remote health port number
	*/
	public Socket connectToServer(String serverHostName, int port)
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
	* Check if a file is an xml or txt file. If not, then encode it as such.
	* @param inFileName Name of input file
	*/
	private void checkAndDecode(String inFileName)
	{
		FileConverter.decodeFile(inFileName, inFileName + ".temp");
		try {
			moveFile(inFileName + ".temp", inFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		command[1] = "gdrive_simple.py";
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
	* @return int Size of received file or error number
	* @throws Exception if socket was closed before receiving reply,
	* in case of error, try to reconnect by re-making the KioskClient object
	*/
	public int getRequest(String serverFileName, String localFileName) throws Exception
	{
		if (this.mode.equals("DS")) {
			String command = "get " + serverFileName;// + " " + localFileName;
			con.sendString(command);
			int resp = con.receiveInt();
			if (resp > 0)
				con.receiveFileln(localFileName, resp);
			checkAndDecode(localFileName);
			return resp;
		} else {
			System.out.println("get(): Falling back to GD script");
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
				localFile = checkAndEncode(localFileName);
				con.sendFileln(localFile);
				resp = con.receiveInt();
			}
			return resp;
		} else {
			System.out.println("put(): Falling back to GD script");
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
			String command = "chpasswd " + userid + " "+ oldpasswd + " " + newpasswd;
			con.sendString(command);
			int resp = con.receiveInt();
			return resp;
		}
		return 0;
	}

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



	/**
	* Check if a file is an xml or txt file. If not, then encode it as such.
	* @param inFileName Name of input file
	* @return File The file object of the output file
	*/
	private File checkAndEncode(String inFileName)
	{
		String[] fileNameParts = inFileName.split("\\.");
		String extension = fileNameParts[fileNameParts.length - 1];
		switch (extension) {
		case "xml":
		case "txt":
		case "XML":
		case "TXT":
			File file = null;
			try
			{
				file = copyFile(inFileName,inFileName + ".tmp");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			System.out.println("copy file...");
			return file;
		default:
			FileConverter.encodeFile(inFileName, inFileName + ".tmp");
			return new File (inFileName + ".tmp");
		}
	}


	/**
	* Utility function for copying a file from one location to another
	* @param source source path (including filename)
	* @param destination destination path (including filename)
	* @return File a File object of the copied file
	* @throws DirectoryNotEmptyException see java.nio.files.Files.copy()
	* @throws IOException see java.nio.files.Files.copy()
	* @throws SecurityException see java.nio.files.Files.copy()
	**/
	private File copyFile(String source, String destination) throws DirectoryNotEmptyException, IOException, SecurityException
	{
		Path src = Paths.get(source);
		Path dst = Paths.get(destination);
		System.out.println("in copy file function...");
		Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
		System.out.println("file copied...");
		File copied = dst.toFile();
		return copied;
	}
}
