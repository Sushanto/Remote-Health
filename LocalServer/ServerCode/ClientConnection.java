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

public class ClientConnection
{
	private String clientID;
	private Socket clientSocket;
	private PrintWriter strWriter;
	private BufferedReader strReader;
	private DataOutputStream outStream;
	private DataInputStream inStream;
	private static final int FILE_SIZE = 6022386;

	public ClientConnection(Socket socket, String id)
	{
		try {
			this.clientID = id;
			this.clientSocket = socket;
			this.outStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
			this.inStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
			this.strWriter = new PrintWriter(clientSocket.getOutputStream(), true);
			this.strReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			strWriter.println(this.clientID);

		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public String getClientID() {
		return this.clientID;
	}

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
			System.exit(1);
		}
	}

	public int sendFile(File inFile)
	{
		try {
			FileInputStream ifStream = new FileInputStream(inFile);
			BufferedInputStream biStream = new BufferedInputStream(ifStream);

			byte[] fileBuffer = new byte[(int)inFile.length()];
			biStream.read(fileBuffer, 0, fileBuffer.length);

			outStream.writeInt((int)inFile.length());

			outStream.write(fileBuffer, 0, fileBuffer.length);
			outStream.flush();

			return fileBuffer.length;
		} catch(IOException e) {
			e.printStackTrace();
			return RHErrors.RHE_IOE;
		}
	}

	public int receiveFile(String outFileName)
	{
		try {
			int origFileLength = inStream.readInt();

			if (origFileLength < 0) {
				/* handle errror here, there was some error */
				return origFileLength;
			}
			int fileLength = origFileLength;

			File outFile = new File(outFileName);
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

	public int sendInt(int val)
	{
		sendString(Integer.toString(val));
		return 0;
	}

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

	public void sendString(String str)
	{
		this.strWriter.println(str);
		return;
	}

	public String receiveString() throws Exception
	{
		String str = this.strReader.readLine();
		if(str == null)
			throw new Exception("Received null string");
		return str;
	}
}
