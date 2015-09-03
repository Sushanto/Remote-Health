package ServerPrograms;

import java.io.*;
import java.net.*;
import CommonPackage.Connection;

public class DataServer
{
	static RHServer ds;

	public static void main(String args[])
	{
		ds=new RHServer();
		while(true)
		{
			Connection con=ds.listen();
			DataServerProtocol dsp=new DataServerProtocol(con);
			dsp.run();
			/* TODO : Add a IP to dsp hashmap */
		}
	}
}