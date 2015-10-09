package patientside;

import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.EOFException;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.DirectoryNotEmptyException;
public class Connection
{
	private Socket clientSocket;
	private PrintWriter strWriter;
	private BufferedReader strReader;
	private DataOutputStream outStream;
	private DataInputStream inStream;
	private static final int FILE_SIZE=6022386;

	public Connection(Socket socket)
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

	public Connection(String address,int port)
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
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	public void disconnect()
	{
		try
		{
			strReader.close();
			strWriter.close();
			inStream.close();
			outStream.close();
			clientSocket.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	public int receiveFromServer(String serverFileName,String localFileName)
	{
		try
		{
			sendString("SEND_FILE");
			sendString(serverFileName);
			int response=receiveInt();
			if(response>=0)
			{
				receiveFile1(localFileName,response);
				checkAndDecode(localFileName);
				return 0;
			}
			else return response;
		}
		catch(Exception ioe)
		{
			ioe.printStackTrace();
			return -1;
		}
	}

	public int sendToServer(String localFileName,String serverFileName)
	{
		try
		{
			sendString("RECEIVE_FILE");
			File localFile=new File(localFileName);
			sendString(serverFileName+" "+localFile.length());
			localFile = checkAndEncode(localFileName);
			sendFile1(localFile);
			// localFile.delete();
			int response=receiveInt();
			return response;
		}
		catch(Exception ioe)
		{
			ioe.printStackTrace();
			return -1;
		}
	}

	public int sendFile(File inFile)
	{
		try {
			FileInputStream ifStream = new FileInputStream(inFile);
			BufferedInputStream biStream = new BufferedInputStream(ifStream);

			byte[] fileBuffer = new byte[(int)inFile.length()];
			biStream.read(fileBuffer, 0, fileBuffer.length);
			try {
				int x = this.receiveInt();
				System.out.println("x= " + x);
			} catch (Exception e) {
				e.printStackTrace();
			}
			outStream.write(fileBuffer, 0, fileBuffer.length);
			outStream.flush();

			return fileBuffer.length;
		} catch(IOException e) {
			e.printStackTrace();
			return RHErrors.RHE_IOE;
		}
	}



	public int sendFile1(File inFile)
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

	public int receiveFile(String outFileName, int fileLength)
	{
		try {
			int origFileLength = fileLength;
			File outFile = new File(outFileName);
			outFile.getParentFile().mkdirs();
			outFile.createNewFile();
			FileOutputStream ofStream = new FileOutputStream(outFile);
			BufferedOutputStream boStream = new BufferedOutputStream(ofStream);

			byte[] fileBuffer = new byte[fileLength];
			int byteRead = 0;

			System.out.println("receive file, start");
			while((fileLength > 0) && (byteRead = inStream.read(fileBuffer, 0, Math.min(fileBuffer.length, fileLength))) != -1)
			{
				boStream.write(fileBuffer, 0, byteRead);
				fileLength -= byteRead;
				System.out.println("fileLength : "+fileLength+" byteRead : "+byteRead);
			}
			System.out.println("receive file, done");
			boStream.close();
			ofStream.close();

			return origFileLength;
		} catch(IOException e) {
			e.printStackTrace();
			return RHErrors.RHE_IOE;
		}
	}



	public int receiveFile1(String outFileName, int fileLength)
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

	public void sendString(String str)
	{
		strWriter.println(str);
		return;
	}

	public String receiveString()
	throws IOException
	{
		String str=strReader.readLine();
		if(str==null)
			throw new IOException();
		return str;
	}

	public boolean login(String username,String password)
	{
		try
		{
			sendString("LOGIN");
			sendString(username);
			sendString(password);
			if(receiveString().equals("LOGGED_IN"))
				return true;
			else return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public int sendInt(int val)
	{
		sendString(Integer.toString(val));
		return 0;
	}

	/*
	* Receive an integer from the client
	* @return the received int or error number
	* @throws Exception if a null string was received
	*/
	public int receiveInt() throws Exception {
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

	protected void finalize()
	{
		System.out.println("Garbage Collection: Connection");
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
		Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
		File copied = dst.toFile();
		return copied;
	}
}