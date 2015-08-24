package patientside;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class Constants
{
	public static final int SIZE_X=900,SIZE_Y=700,PANEL2_HEIGHT=100;
	public static JPanel JPANEL1,JPANEL2;
	public static Color LABELCOLOR1,LABELCOLOR2,LABELCOLOR3,LABELCOLOR4,WARNINGCOLOR,MOUSEENTER,JPANELCOLOR1;
	public static Color HEADERCOLOR1,JPANELCOLOR2;
	public static JLabel NAME_LABEL;
	public static Font HEADERFONT,SMALLLABELFONT,SMALLBUTTONFONT,BENGALILABELFONT,BENGALIBUTTONFONT;
	// public static final String SERVER="192.168.250.58";
	public static final String SERVER="203.197.107.110";
	// public static final String SERVER="192.168.137.240";
	// public static final String SERVER="10.10.149.108";
	// public static final String SERVER="192.168.43.73";
	public static final String USERNAME="user1";
	public static final int PORT=5000;

	Constants()
	{
		JPANELCOLOR1=UIManager.getColor("Button.click");
		// JPANELCOLOR1=Color.WHITE;
		JPANELCOLOR2=Color.GREEN.darker().darker();
		LABELCOLOR1=Color.BLACK;
		LABELCOLOR2=Color.GREEN.darker().darker();
		LABELCOLOR3=Color.GREEN.darker().darker();
		LABELCOLOR4=Color.BLACK;
		
		WARNINGCOLOR=Color.RED;
		MOUSEENTER=Color.BLUE;
		HEADERCOLOR1=Color.WHITE;

		HEADERFONT=new Font("Serif",Font.BOLD,34);
		SMALLLABELFONT=new Font("Serif",Font.BOLD,12);
		SMALLBUTTONFONT=new Font("Serif",Font.BOLD,12);
		BENGALILABELFONT=new Font("Arial black",Font.BOLD,14);
		BENGALIBUTTONFONT=new Font("Serif",Font.BOLD,14);


		JPANEL1=new JPanel();
		JPANEL1.setBounds(0,0,1300,SIZE_Y);
		JPANEL1.setBackground(JPANELCOLOR1);
		JPANEL1.setBorder(BorderFactory.createRaisedBevelBorder());


		JPANEL2=new JPanel();
		JPANEL2.setBounds(0,0,1300,PANEL2_HEIGHT);
		JPANEL2.setBackground(JPANELCOLOR2);
		JPANEL2.setBorder(BorderFactory.createRaisedBevelBorder());

		NAME_LABEL=new JLabel();
		NAME_LABEL.setBounds(20,20,150,50);
		NAME_LABEL.setForeground(LABELCOLOR4);
		NAME_LABEL.setFont(new Font(NAME_LABEL.getFont().getName(),Font.BOLD,14));

	}

	String readCurrentLanguage()
	{
		File file=new File("tempFolder/Language.abc");
		if(file.isFile())
		{
			try
			{
				FileInputStream fin=new FileInputStream(file);
				BufferedReader br=new BufferedReader(new InputStreamReader(fin));
				String str=br.readLine();
				br.close();
				fin.close();
				return str;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return "বাংলা";
			}
		}
		return "বাংলা";
	}

	String getKioskNumber()
	{
		try
		{
			BufferedReader bin=new BufferedReader(new FileReader("tempFolder/KioskNumber.abc"));
			String KioskNumber=bin.readLine();
			bin.close();
			return KioskNumber;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	static boolean RecieveFromServer(String ServerFile,String LocalFile)
	{
		try
		{
			// Thread.sleep(100);
			Socket socket=new Socket(InetAddress.getByName(SERVER),5000);
			PrintWriter pwriter=new PrintWriter(socket.getOutputStream(),true);
			BufferedReader bin=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pwriter.println(USERNAME);
			pwriter.println("FILE_CLIENT");
			pwriter.println("SEND_FILE");
			String signal=bin.readLine();
			if(signal.equals("File sending initiated"))
			{
				pwriter.println(ServerFile);
				String signal2=bin.readLine();
				if(signal2.startsWith("RTS"))
				{
					String filesize=signal2.substring(signal2.lastIndexOf(" ")+1);
					File file=new File(LocalFile);
					file.createNewFile();
					FileOutputStream fos=new FileOutputStream(file);
					BufferedOutputStream bos=new BufferedOutputStream(fos);
					byte[] bytearray=new byte[6022386];
					InputStream is=socket.getInputStream();
					int byteread=0,current=0;
					while(byteread>-1)
					{
						byteread=is.read(bytearray,current,bytearray.length-current);
						if(byteread>=0)
							current+=byteread;
					}
					bos.write(bytearray,0,current);
					bos.flush();
					bos.close();
					fos.close();
					bin.close();
					pwriter.close();
					socket.close();
					if(filesize.equals(String.valueOf(file.length())))
					{
						return true;
					}
					else
					{
						return false;
					} 
				}
			}
			bin.close();
			pwriter.close();
			socket.close();
			return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	static boolean SendToServer(String LocalFile,String ServerFile)
    {
        try
        {
			// Thread.sleep(100);
            Socket socket=new Socket(InetAddress.getByName(SERVER),PORT);
            File file=new File(LocalFile);
            PrintWriter pwriter=new PrintWriter(socket.getOutputStream(),true);
            BufferedReader bin=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pwriter.println(USERNAME);
			pwriter.println("FILE_CLIENT");

            pwriter.println("RECEIVE_FILE");
            String signal=bin.readLine();
            if(signal.equals("File recieving initiated"))
            {
                pwriter.println(ServerFile);
                pwriter.println(String.valueOf(file.length()));
                String signal2=bin.readLine();
                if(signal2.equals("CTS"))
                {
	                FileInputStream fis=new FileInputStream(file);
	                BufferedInputStream bis=new BufferedInputStream(fis);
	                byte[] bytearray=new byte[(int)file.length()];
	                bis.read(bytearray,0,bytearray.length);

	                OutputStream os=socket.getOutputStream();
	                os.write(bytearray,0,bytearray.length);
	                os.flush();
	                os.close();
	                bis.close();
	                fis.close();
	                socket.close();

	                socket=new Socket(InetAddress.getByName(SERVER),PORT);
	                bin=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	                String ack=bin.readLine();
		            bin.close();
		            pwriter.close();
	                socket.close();
	                if(ack.equals("ACK"))
	                {
		                return true;
	                }
		           	else
		           	{
		           		return false;
		           	}
	            }
            }
            bin.close();
            pwriter.close();
            socket.close();
            return false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }
}