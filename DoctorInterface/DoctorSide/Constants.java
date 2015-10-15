package DoctorSide;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.IOException;

/**
* Constants class to store constant values as static members
* @author Sushanto Halder
*/
public class Constants
{
	protected static final int SIZE_X = 900,SIZE_Y = 700,PANEL2_HEIGHT = 100;
	protected static JPanel JPANEL1,JPANEL2;
	protected static Color LABELCOLOR1,LABELCOLOR2,LABELCOLOR3,LABELCOLOR4,WARNINGCOLOR,MOUSEENTER,JPANELCOLOR1;
	protected static Color HEADERCOLOR1,JPANELCOLOR2;
	protected static JLabel NAME_LABEL;
	protected static Font HEADERFONT,SMALLLABELFONT,SMALLBUTTONFONT,BENGALILABELFONT,BENGALIBUTTONFONT;
	protected static String serverHostName;
	protected static String doctorId;
	protected static String dataFolder;
	protected static int serverPort;

	static
	{
		JPANELCOLOR1 = UIManager.getColor("Button.click");
		JPANELCOLOR2 = Color.YELLOW.darker();
		LABELCOLOR1 = Color.BLACK;
		LABELCOLOR2 = Color.GREEN.darker().darker();
		LABELCOLOR3 = Color.GREEN.darker().darker();
		LABELCOLOR4 = Color.BLACK;
		
		WARNINGCOLOR = Color.RED;
		MOUSEENTER = Color.BLUE;
		HEADERCOLOR1 = Color.WHITE;

		HEADERFONT = new Font("Serif",Font.BOLD,34);
		SMALLLABELFONT = new Font("Serif",Font.BOLD,12);
		SMALLBUTTONFONT = new Font("Serif",Font.BOLD,12);
		BENGALILABELFONT = new Font("Arial black",Font.BOLD,14);
		BENGALIBUTTONFONT = new Font("Serif",Font.BOLD,14);


		JPANEL1 = new JPanel();
		JPANEL1.setBounds(0,0,1300,SIZE_Y);
		JPANEL1.setBackground(JPANELCOLOR1);
		JPANEL1.setBorder(BorderFactory.createRaisedBevelBorder());


		JPANEL2 = new JPanel();
		JPANEL2.setBounds(0,0,1300,PANEL2_HEIGHT);
		JPANEL2.setBackground(JPANELCOLOR2);
		JPANEL2.setBorder(BorderFactory.createRaisedBevelBorder());

		NAME_LABEL = new JLabel();
		NAME_LABEL.setBounds(20,20,150,50);
		NAME_LABEL.setForeground(LABELCOLOR4);
		NAME_LABEL.setFont(new Font(NAME_LABEL.getFont().getName(),Font.BOLD,14));

		/*
		* Get system username as default doctor id
		*/
		doctorId = System.getProperty("user.name");
		dataFolder = "tempFolder/";
		readInfo();

	}

	/**
	* Reads information such as Ip address, port etc. from DoctorMetadata.cfg file
	*/
	private static void readInfo()
	{
		try
		{
			FileReader fReader = new FileReader(new File("DoctorMetadata.cfg"));
			BufferedReader bReader = new BufferedReader(fReader);
			System.out.println("Doctor Information reading....");

			String line;
			while((line = bReader.readLine()) != null)
			{
				/*
				* Split the line and check for token
				*/
				String[] tokens = line.split("=");
				switch(tokens[0])
				{
					case "DOCTOR_ID":
						doctorId = tokens[1];
						break;
					case "SERVER_HOST_NAME":
						serverHostName = tokens[1];
						break;
					case "SERVER_PORT":
						serverPort = Integer.parseInt(tokens[1]);
						break;
					case "DATA_FOLDER":
						dataFolder = tokens[1];
						break;
					default:
						break;
				}
			}
			bReader.close();
			fReader.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}