package patientside;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
* Constants: Stores constant values as static members
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
	protected static String tempDataPath = "temp/", finalDataPath = "final/Kiosk_01/", dataPath = "tempFolder/", workingPath = "";
	protected static String localServerHostName = "127.0.0.1";
	protected static String kioskNo = "0x",deviceId;
	protected static int localServerPort = 36698;
	protected static String language = "Bengali";

	static
	{
		JPANELCOLOR1 = UIManager.getColor("Button.click");
		// JPANELCOLOR1 = Color.WHITE;
		JPANELCOLOR2 = Color.GREEN.darker().darker();
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
		deviceId = System.getProperty("user.name");

		readInfo();

	}

	/**
	* Reads device informations from DeviceMetadata.cfg
	*/
	private static void readInfo()
	{
		try
		{
			FileReader fReader = new FileReader(new File("DeviceMetadata.cfg"));
			BufferedReader bReader = new BufferedReader(fReader);
			System.out.println("Kiosk Information reading....");

			String line;
			while((line = bReader.readLine()) != null)
			{
				String[] tokens = line.split("=");
				switch(tokens[0])
				{
					case "DEVICE_ID":
						deviceId = tokens[1];
						break;
					case "KIOSK_NO":
						kioskNo = tokens[1];
						break;
					case "LOCAL_SERVER_HOST_NAME":
						localServerHostName = tokens[1];
						break;
					case "LOCAL_SERVER_PORT":
						localServerPort = Integer.parseInt(tokens[1]);
						break;
					case "FINAL_FOLDER":
						finalDataPath = tokens[1];
						break;
					case "TEMP_FOLDER":
						tempDataPath = tokens[1];
						break;
					case "DATA_FOLDER":
						dataPath = tokens[1];
						break;
					case "WORKING_FOLDER":
						workingPath = tokens[1];
						break;
					case "LANGUAGE":
						language = tokens[1];
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