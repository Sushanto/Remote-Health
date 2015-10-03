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

public class Constants
{
	public static final int SIZE_X=900,SIZE_Y=700,PANEL2_HEIGHT=100;
	public static JPanel JPANEL1,JPANEL2;
	public static Color LABELCOLOR1,LABELCOLOR2,LABELCOLOR3,LABELCOLOR4,WARNINGCOLOR,MOUSEENTER,JPANELCOLOR1;
	public static Color HEADERCOLOR1,JPANELCOLOR2;
	public static JLabel NAME_LABEL;
	public static Font HEADERFONT,SMALLLABELFONT,SMALLBUTTONFONT,BENGALILABELFONT,BENGALIBUTTONFONT;
	// public static final String SERVER="192.168.250.58";
	// public static final String SERVER="203.197.107.110";
	// public static final String SERVER="192.168.137.240";
	// public static final String SERVER="10.10.149.108";
	public static final String SERVER="127.0.0.1";
	public static final String USERNAME="user1";
	public static final int PORT=36699;

	static
	{
		JPANELCOLOR1=UIManager.getColor("Button.click");
		// JPANELCOLOR1=Color.WHITE;
		JPANELCOLOR2=Color.YELLOW.darker()/*.darker()*/;
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

	public static String getKioskNumber()
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
}