package ClientPrograms;

import CommonPackage.Connection;
import java.io.*;
import java.net.*;

public class RHClient
{
	public static final String SERVER="127.0.0.1";
	public static final int PORT=5000;

	public static void main(String args[])
	throws Exception
	{
		Socket clientSocket=new Socket(InetAddress.getByName(SERVER),PORT);
		Connection myCon=new Connection(clientSocket);

		BufferedReader bin=new BufferedReader(new InputStreamReader(System.in));

		// while(true)
		{
			// myCon.sendString(bin.readLine());
			// System.out.println(myCon.receiveString());
			myCon.sendFile("ClientPrograms/test.jpg");
			// myCon.receiveFile("ClientPrograms/test2.txt");
		}
	}
}