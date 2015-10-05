package ServerCode;

import java.net.Socket;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


import java.io.IOException;
import java.io.EOFException;


/**
	ClientConnection, a Connection object in the client for maintainig a connection to the server
*/
public class ClientConnection
{
	private String clientID; 	/* who am I? */
	private Socket clientSocket;	/* via which socket? */
	private PrintWriter strWriter;	/* for writing strings */
	private BufferedReader strReader;	/* for reading strings */
	private DataOutputStream outStream;	/* For general IO */
	private DataInputStream inStream;
	private static final int FILE_SIZE = 6022386;	/* This is arbitrary (kinda) */

	/**
	* Intialize a ClientConnection with this socket
	* @param socket The clients socket object
	*/
	public ClientConnection(Socket socket, String id)
	{
		try {
			this.clientID = id;
			this.clientSocket = socket;
			this.outStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
			this.inStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
			this.strWriter = new PrintWriter(clientSocket.getOutputStream(), true);
			this.strReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			strWriter.println(this.clientID);	/* send my ID, first things first */

		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	* Return the ID of the client this object is connected to
	* @return String The clients ID
	*/
	public String getClientID() {
		return this.clientID;
	}

	/**
	* Disconnect from the client by closing all the streams and the socket
	*/
	public void disconnect()
	{
		try {
			strReader.close();
			strWriter.close();
			inStream.close();
			outStream.close();
			clientSocket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/*
	* Send a file to the connected server
	* @param inFile The File object corresponding to the file to send
	* @return int if file was sent successfully then the length of the file, otherwise error number
	*/
	public int sendFile(File inFile)
	{
		try {
			FileInputStream ifStream = new FileInputStream(inFile);
			BufferedInputStream biStream = new BufferedInputStream(ifStream);

			byte[] fileBuffer = new byte[(int)inFile.length()];
			biStream.read(fileBuffer, 0, fileBuffer.length);

			// outStream.writeLong((long)inFile.length());

			outStream.write(fileBuffer, 0, fileBuffer.length);
			outStream.flush();

			return fileBuffer.length;
		} catch(IOException e) {
			e.printStackTrace();
			return RHErrors.RHE_IOE;
		}
	}

	/**
	* Receive a file from the server
	* @param outFileName The name of the file in the server (to save as)
	* @return int The length of the received file, otherwise error number
	*/
	public int receiveFile(String outFileName,int origFileLength)
	{
		try {
			// int origFileLength = (int)inStream.readLong();

			if (origFileLength < 0) {
				/* handle errror here, there was some error */
				return origFileLength;
			}
			int fileLength = origFileLength;

			File outFile = new File(outFileName);
			outFile.getParentFile().mkdirs();
			outFile.createNewFile();
			FileOutputStream ofStream = new FileOutputStream(outFile);
			BufferedOutputStream boStream = new BufferedOutputStream(ofStream);

			byte[] fileBuffer = new byte[fileLength];
			int byteRead = 0;

			while((fileLength > 0) && (byteRead = inStream.read(fileBuffer, 0, Math.min(fileBuffer.length, fileLength))) != -1)
			{
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

	/*
	* Send an int (as a String; this is the more reliable way)
	* @param val Integer to send
	* @ return int A Random Number (see: https://xkcd.com/221/)
	*/
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

	/**
	* Send a string to the client
	* @param str The string to send
	*/
	public void sendString(String str)
	{
		this.strWriter.println(str);
		return;
	}

	/**
	* Receive a string from the client
	* @return The received string
	* @throws Exception if a null string was received
	*/
	public String receiveString() throws Exception
	{
		String str = this.strReader.readLine();
		if(str == null)
			throw new Exception("Received null string");
		return str;
	}
}
