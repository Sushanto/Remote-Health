package ServerPrograms;

import CommonPackage.Connection;
import java.io.*;

public class TestServer
{
	static RHServer rhServer;
	static Connection myCon;
	static BufferedReader bin;

	public static void main(String args[])
	{
		try
		{
			rhServer=new RHServer();
			System.out.println("Server Started");

			myCon=rhServer.listen();
			bin=new BufferedReader(new InputStreamReader(System.in));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		// while(true)
		// {
		// 	try
		// 	{
		// 		System.out.println(myCon.receiveString());
		// 		myCon.sendString(bin.readLine());
		// 	}
		// 	catch(Exception e)
		// 	{
		// 		myCon.disconnect();
		// 		System.out.println("TestServer: Connection Removed");
		// 		rhServer.disconnect(myCon);
		// 		break;
		// 	}
		// }

		myCon.receiveFile("ServerPrograms/test1.jpg");
		myCon.sendFile("ServerPrograms/test2.jpg");
		myCon.receiveFile("ServerPrograms/test3.jpg");
		myCon.sendFile("ServerPrograms/test4.jpg");
	}
}