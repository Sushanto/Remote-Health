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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.DirectoryNotEmptyException;

class Connection extends Thread
{
	private Socket clientSocket;
	private PrintWriter strWriter;
	private BufferedReader strReader;
	private DataOutputStream outStream;
	private DataInputStream inStream;
	private static final int FILE_SIZE=6022386;

	protected Connection(Socket socket)
	{
		try
		{
			clientSocket=socket;
			outStream=new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
			inStream=new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
			strWriter=new PrintWriter(clientSocket.getOutputStream(),true);
			strReader=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	protected Connection(String address,int port)
	{
		try
		{
			InetAddress inetAddress=InetAddress.getByName(address);
			clientSocket=new Socket(inetAddress,port);
			outStream=new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
			inStream=new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
			strWriter=new PrintWriter(clientSocket.getOutputStream(),true);
			strReader=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}
		catch(UnknownHostException uhe)
		{
			uhe.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	protected void disconnect()
	{
		try
		{
			strReader.close();
			strWriter.close();
			inStream.close();
			outStream.close();
			clientSocket.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		try
		{
			while(true)
			{
				String request=receiveString();
				System.out.println(request);
				try
				{
					System.out.println("Setting mode....");
					if(LocalServer.client.getMode().equals("GD"))
					{
						System.out.println("Trying to connect to DS...");
						Socket sock;
						if((sock=LocalServer.client.connectToServer(LocalServer.serverHostName,LocalServer.serverPort))!=null)
						{
							System.out.println("Connected to DS...");
							sock.close();
							// LocalServer.client = new KioskClientSync(LocalServer.kioskId,LocalServer.serverHostName,LocalServer.serverPort,LocalServer.syncFolder);
							LocalServer.client = new KioskClient(LocalServer.kioskId,LocalServer.serverHostName,LocalServer.serverPort,LocalServer.syncFolder);
							LocalServer.client.loginRequest(LocalServer.loginUsername,LocalServer.loginPassword);
						}
						else System.out.println("Connected to GD....");
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				switch(request)
				{
					case "SEND_FILE":
						sendToClient();
						break;
					case "RECEIVE_FILE":
						receiveFromClient();
						break;
				}
			}
		}
		catch(IOException e)
		{
			// e.printStackTrace();
			disconnect();
			LocalServer.remove(this);
		}
	}

	private boolean receiveFromClient()
	{
		try
		{
			String localFileName=receiveString();
			String[] fileInfo=localFileName.split(" ");
			String[] folders=fileInfo[0].split("/");
			System.out.println("Requested file: "+localFileName);
			receiveFile1(fileInfo[0],Integer.parseInt(fileInfo[1]));
			checkAndDecode(fileInfo[0]);
			System.out.println("File received: "+localFileName);
			sendInt(0);
			if((folders[0]+"/"+folders[1]).equals(LocalServer.finalDataPath))
			{
				File file=new File(LocalServer.tempDataPath+"/"+folders[2]);
				if(file.isFile())
					file.delete();
				Thread.sleep(3000);
				System.out.println("Syncing...");
				LocalServer.client.putRequest(fileInfo[0],folders[2]);
				System.out.println("Sync complete...");
			}
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	private boolean sendToClient()
	{
		try
		{
			String localFileName=receiveString();
			System.out.println("Requested file: "+localFileName);
			File tempFile = new File(LocalServer.tempDataPath+"/"+localFileName);
			if(tempFile.isFile())
			{
				sendInt((int)tempFile.length());
				tempFile = checkAndEncode(LocalServer.tempDataPath+"/"+localFileName);
				sendFile1(tempFile);
				// tempFile.delete();
				System.out.println("File sent: "+localFileName);
				return true;
			}
			else
			{
				System.out.println("Syncing....");
				int response=LocalServer.client.getRequest(localFileName,LocalServer.finalDataPath+"/"+localFileName);
				System.out.println("Sync complete....");
				System.out.println("Response: "+response);
				File finalFile= new File(LocalServer.finalDataPath+"/"+localFileName);
				if(response>=0)
				{
					sendInt(response);
					finalFile = checkAndEncode(LocalServer.finalDataPath+"/"+localFileName);
					sendFile1(finalFile);
					// finalFile.delete();
					System.out.println("File sent: "+localFileName);
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

	private int sendFile(File inFile)
	{
		try {
			FileInputStream ifStream = new FileInputStream(inFile);
			BufferedInputStream biStream = new BufferedInputStream(ifStream);

			byte[] fileBuffer = new byte[(int)inFile.length()];
			biStream.read(fileBuffer, 0, fileBuffer.length);

			outStream.write(fileBuffer, 0, fileBuffer.length);
			outStream.flush();
			System.out.println("return from sendFile");
			return fileBuffer.length;
		} catch(IOException e) {
			e.printStackTrace();
			return RHErrors.RHE_IOE;
		}
	}

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

	private int receiveFile(String outFileName, int fileLength)
	{
		try {
			int origFileLength = fileLength;
			/* Send the OK to send */
			this.sendInt(0);
			System.out.println("Sent zero OK, fileLength = " + fileLength);
			File outFile = new File(outFileName);
			outFile.getParentFile().mkdirs();
			outFile.createNewFile();
			FileOutputStream ofStream = new FileOutputStream(outFile);
			BufferedOutputStream boStream = new BufferedOutputStream(ofStream);

			byte[] fileBuffer = new byte[fileLength];
			int byteRead = 0;

			while((fileLength > 0) && (byteRead = inStream.read(fileBuffer, 0, Math.min(fileBuffer.length, fileLength))) != -1) {
				System.out.println("Inside read loop: " + byteRead + ", " + byteRead);
				boStream.write(fileBuffer, 0, byteRead);
				fileLength -= byteRead;
			}

			boStream.flush();
			boStream.close();
			ofStream.close();

			return origFileLength;
		} catch(IOException e) {
			e.printStackTrace();
			return RHErrors.RHE_IOE;
		}
	}

	private void sendString(String str)
	{
		strWriter.println(str);
		return;
	}

	protected String receiveString()
	throws IOException
	{
		String str=strReader.readLine();
		if(str==null)
			throw new IOException();
		return str;
	}

	protected void finalize()
	{
		System.out.println("Garbage Collected: Connection");
	}

	protected boolean login()
	{
		try
		{
			if(receiveString().equals("LOGIN"))
			{
				String username=receiveString();
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