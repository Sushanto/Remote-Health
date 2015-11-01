package ServerCode;

import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.io.IOException;
import java.io.EOFException;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.text.SimpleDateFormat;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.DirectoryNotEmptyException;

/**
* Connection: Used for communication with Kiosk devices
* @author Sushanto Halder
*/

public class Connection extends Thread
{
	private Socket clientSocket;
	private PrintWriter strWriter;
	private BufferedReader strReader;
	private static final int FILE_SIZE=6022386;
	private String connectionId;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH:mm:ss");

	/**
	* Initialize socket and input output streams
	* @param socket Socket object, already connected to Kiosk device
	*/
	protected Connection(Socket socket)
	{
		try
		{
			clientSocket=socket;
			strWriter=new PrintWriter(clientSocket.getOutputStream(),true);
			strReader=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	* Disconnec connection and all input output stream
	*/
	protected void disconnect()
	{
		try
		{
			strReader.close();
			strWriter.close();
			clientSocket.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	* Listen for new request
	*/
	public void run()
	{
		try
		{
			while(true)
			{
				String request=receiveString();
				System.out.println(dateFormat.format(new Date()) + "   " + connectionId + "\t> Request : " + request);
				// try
				// {
					System.out.println(dateFormat.format(new Date()) + "   " + "LocalServer\t> Check mode......................." + LocalServer.client.getMode());
					// if(LocalServer.client.getMode().equals("GD"))
					// {
					// 	System.out.println(dateFormat.format(new Date()) + "   " + "LocalServer\t> Trying to connect to DS...");
					// 	Socket sock;
					// 	if((sock=LocalServer.client.connectToServer(LocalServer.serverHostName,LocalServer.serverPort))!=null)
					// 	{
					// 		System.out.println(dateFormat.format(new Date()) + "   " + "LocalServer\t> Connected to DS...");
					// 		sock.close();
					// 		// LocalServer.client = new KioskClientSync(LocalServer.kioskId,LocalServer.serverHostName,LocalServer.serverPort,LocalServer.syncFolder);
					// 		LocalServer.client = new KioskClient(LocalServer.kioskId,LocalServer.serverHostName,LocalServer.serverPort,LocalServer.syncFolder);
					// 		LocalServer.client.loginRequest(LocalServer.loginUsername,LocalServer.loginPassword);
					// 	}
					// 	else System.out.println(dateFormat.format(new Date()) + "   " + "LocalServer\t> Connected to GD....");
					// }
				// }
				// catch(Exception e)
				// {
				// 	e.printStackTrace();
				// }
				switch(request)
				{
					case "SEND_FILE":
						sendToClient();
						break;
					case "RECEIVE_FILE":
						receiveFromClient();
						break;
					case "LOCK_FILE":
						lockFile();
						break;
					case "UNLOCK_FILE":
						unlockFile();
						break;
				}
				System.out.println("_________________________________________________________________________________________________________");
			}
		}
		catch(IOException e)
		{
			// e.printStackTrace();
			disconnect();
			LocalServer.remove(this,connectionId);
			LocalServer.removeLocks(connectionId);
		}
	}

	/**
	* Action for lockfile request from client
	* @return True if success else false
	*/
	private boolean lockFile()
	{
		try
		{
			String fileName = receiveString();
			System.out.println(dateFormat.format(new Date()) + "   " + connectionId + "\t> Lock request: " + fileName);
			int response = LocalServer.lockFile(fileName , connectionId);
			sendInt(response);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	* Action for unlock file request from client
	* @return True if success else false
	*/
	private boolean unlockFile()
	{
		try
		{
			String fileName = receiveString();
			System.out.println(dateFormat.format(new Date()) + "   " + connectionId + "\t> Unlock request: " + fileName);
			int response = LocalServer.unlockFile(fileName , connectionId);
			sendInt(response);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	* Receive a file from client
	* @return True if success else false
	*/
	private boolean receiveFromClient()
	{
		try
		{
			String localFileName=receiveString();
			String[] fileInfo=localFileName.split(" ");
			String[] folders=fileInfo[0].split("/");
			System.out.println(dateFormat.format(new Date()) + "   " + connectionId + "\t> Requested file: "+localFileName);

			String lockerID = LocalServer.getLockerID(folders[folders.length - 1]);
			if (lockerID != null && !connectionId.equals(lockerID))
			{
				sendInt(RHErrors.RHE_OP_LOCKED);
				System.out.println(dateFormat.format(new Date()) + "   " + connectionId + "\t> " + RHErrors.RHE_OP_LOCKED);
				return false;
			}
			else sendInt(0);

			receiveFile1(fileInfo[0],Integer.parseInt(fileInfo[1]));
			checkAndDecode(fileInfo[0]);
			System.out.println(dateFormat.format(new Date()) + "   " + connectionId + "\t> File received from client : " + localFileName);
			sendInt(0);
			if((folders[0]+"/"+folders[1]).equals(LocalServer.finalDataPath))
			{
				boolean newFile = false;
				String[] fileNameParts = folders[folders.length - 1].split("\\.");
				String extension = fileNameParts[fileNameParts.length - 1];
				if(!extension.equals("xml") && !extension.equals("txt"))
					newFile |= true;
				File file=new File(LocalServer.tempDataPath+"/"+folders[folders.length - 1]);
				if(file.isFile())
				{
					newFile |= true;
					file.delete();
				}
				Thread.sleep(3000);
				System.out.println(dateFormat.format(new Date()) + "   " + connectionId + "\t> Sending to server....");
				LocalServer.client.putRequest(fileInfo[0],folders[folders.length - 1],newFile);
				System.out.println(dateFormat.format(new Date()) + "   " + connectionId + "\t> Send complete...");
			}
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	* Send file to client
	* @return True if success else false
	*/
	private boolean sendToClient()
	{
		try
		{
			String localFileName=receiveString();
			System.out.println(dateFormat.format(new Date()) + "   " + connectionId + "\t> Requested file: "+localFileName);

			String lockerID = LocalServer.getLockerID(localFileName);
			if (lockerID != null && !connectionId.equals(lockerID))
			{
				sendInt(RHErrors.RHE_OP_LOCKED);
				System.out.println(dateFormat.format(new Date()) + "   " + connectionId + "\t> " + RHErrors.RHE_OP_LOCKED);
				return false;
			}
			else sendInt(0);

			File tempFile = new File(LocalServer.tempDataPath+"/"+localFileName);
			if(tempFile.isFile())
			{
				sendInt((int)tempFile.length());
				tempFile = checkAndEncode(LocalServer.tempDataPath+"/"+localFileName);
				sendFile1(tempFile);
				tempFile.delete();
				System.out.println(dateFormat.format(new Date()) + "   " + connectionId + "\t> File sent to client : "+localFileName);
				return true;
			}
			else
			{
				System.out.println(dateFormat.format(new Date()) + "   " + connectionId + "\t> Receiving from server....");
				int response=LocalServer.client.getRequest(localFileName,LocalServer.finalDataPath+"/"+localFileName);
				System.out.println(dateFormat.format(new Date()) + "   " + connectionId + "\t> Receive complete....");
				System.out.println(dateFormat.format(new Date()) + "   " + connectionId + "\t> Receive response: "+response);
				File finalFile= new File(LocalServer.finalDataPath+"/"+localFileName);
				if(response>=0)
				{
					sendInt(response);
					finalFile = checkAndEncode(LocalServer.finalDataPath+"/"+localFileName);
					sendFile1(finalFile);
					finalFile.delete();
					System.out.println(dateFormat.format(new Date()) + "   " + connectionId + "\t> File sent to client : "+localFileName);
					return true;
				}
				else
				{
					sendInt(response);
					return false;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	* Send file utility function for sending file to client
	* @param inFile File object for input file
	* @return If success, return file length, else return negative error value
	*/
	private int sendFile1(File inFile)
	{
		try
		{
			FileReader fReader = new FileReader(inFile);
			BufferedReader bReader= new BufferedReader(fReader);

			String line="";

			sendString("FILE_START");
			while((line=bReader.readLine())!=null)
			{
				sendString(line);
			}
			sendString("FILE_END");
			bReader.close();
			fReader.close();
			return 0;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}

	/**
	* Utility function for receiving file
	* @param outFileName Name of the file to be received
	* @param fileLength Length of the file to be received, unused
	* @return If success return file length, else return negative error value
	*/
	private int receiveFile1(String outFileName, int fileLength)
	{
		try
		{
			File outFile = new File(outFileName);
			FileWriter fWriter = new FileWriter(outFile);
			PrintWriter pWriter = new PrintWriter(outFile);

			String reply="";
			reply= receiveString();
			if(reply.equals("FILE_START"))
			{
				reply=receiveString();
				pWriter.print(reply);

				while(!(reply=receiveString()).equals("FILE_END"))
					pWriter.print("\n"+reply);
			}

			pWriter.close();
			return 0;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}

	/**
	* Send a string to client
	* @param String to be send
	*/
	private void sendString(String str)
	{
		strWriter.println(str);
		return;
	}

	/**
	* Receive a string from a client
	* @return String received from client
	* @throws IOException in case of any error
	*/
	protected String receiveString()
	throws IOException
	{
		String str=strReader.readLine();
		if(str==null)
			throw new IOException();
		return str;
	}

	/**
	* Finalize method, called by garbage collector
	*/
	protected void finalize()
	{
		System.out.println(dateFormat.format(new Date()) + "   " + connectionId + "\t> Garbage Collected: Connection");
	}

	/**
	* Handle login request from client
	* @return True if success else false
	*/
	protected boolean login()
	{
		try
		{
			if(receiveString().equals("LOGIN"))
			{
				connectionId=receiveString();
				String password=receiveString();
				sendString("LOGGED_IN");
				return true;
			}
			else return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	* Send a integer to client
	* @param val Integer to be sent
	* @return 0 if success, else negative error value
	*/
	protected int sendInt(int val)
	{
		sendString(Integer.toString(val));
		return 0;
	}

	/*
	* Receive an integer from the client
	* @return the received int or error number
	* @throws Exception if a null string was received
	*/
	protected int receiveInt() throws Exception {
		int val = RHErrors.RHE_GENERAL;
		try {
			String str = receiveString();
			val = Integer.parseInt(str);
		} catch (EOFException eofe) {
			eofe.printStackTrace();
			return RHErrors.RHE_IOE;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return RHErrors.RHE_IOE;
		} catch (Exception e) {
			throw new Exception("Received null string");
		}
		return val;
	}



	/**
	* Move a file from source to destination
	* @param source Name of the source file
	* @param destination Destination file name
	* @return File object of the destination file
	* @throws DirectoryNotEmptyException see java.nio.files.Files.copy()
	* @throws IOException see java.nio.files.Files.copy()
	* @throws SecurityException see java.nio.files.Files.copy()
	*/
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
			moveFile(inFileName + ".temp",inFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			// System.out.println("copy file...");
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
		// System.out.println("in copy file function...");
		Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
		System.out.println(dateFormat.format(new Date()) + "   " + connectionId + "\t> file copied...");
		File copied = dst.toFile();
		return copied;
	}
}