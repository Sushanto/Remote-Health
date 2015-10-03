package patientside;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
*Drivier class
*@author Sushanto Halder
*/

public class KioskLogin extends JFrame
{
	/**
	*Drivier method
	*@param args Unused
	*/
	public static void main(String args[])
	{
		if(!(new File("tempFolder")).exists())
			(new File("tempFolder")).mkdir();
		new KioskLogin();
	}

	/**
	*Contructor, called by main method
	*/
	public KioskLogin()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
            		for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            		{
                		if ("Nimbus".equals(info.getName()))
                		{
							javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    		break;
                		}
            		}
        		}
        		catch (ClassNotFoundException ex)
        		{
            		java.util.logging.Logger.getLogger(RegisterNewPatient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (InstantiationException ex)
        		{
            		java.util.logging.Logger.getLogger(RegisterNewPatient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (IllegalAccessException ex)
        		{
            		java.util.logging.Logger.getLogger(RegisterNewPatient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (javax.swing.UnsupportedLookAndFeelException ex)
        		{
            		java.util.logging.Logger.getLogger(RegisterNewPatient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}

				new loginApplet();
			}
		});
	}
}

/**
*Kiosk Coordinator login interface class
*/

class loginApplet extends JFrame
{
	private JTextField useridBox;
	private JPasswordField passwordBox;
	private JLabel useridLabel,passwordLabel,errorLabel,signupLabel,showPasswordLabel,frameLabel,languageLabel;
	private JPanel jpanel1,jpanel2;
	private JButton signinButton,languageSaveButton;
	private JCheckBox showPassword;
	private JComboBox languageComboBox;
	private String language,confirmMessage,networkErrorMessage;
	private Connection connection;
	private Employee emp;

	/**
	*Set language specified by language
	*@param language Bengali/English
	*/
	private void setLanguage(String language)
	{
		if(language.equals("বাংলা"))
		{
			frameLabel.setText("কিয়স্ক লগইন");
			useridLabel.setText("ইউসার-আইডি: ");
			passwordLabel.setText("পাসওয়ার্ড: ");
			showPasswordLabel.setText("পাসওয়ার্ড দেখাও");
			errorLabel.setText("অবৈধ ইউসার-আইডি বা পাসওয়ার্ড*");
			signinButton.setText("সাইন ইন");
			languageSaveButton.setText("সংরক্ষণ করুন");
			signupLabel.setText("নতুন ইউজার এখানে সাইন আপ করুন");
			languageLabel.setText("ভাষা নির্বাচন করুন");

			useridLabel.setFont(Constants.BENGALILABELFONT);
			passwordLabel.setFont(Constants.BENGALILABELFONT);
			showPasswordLabel.setFont(Constants.BENGALILABELFONT);
			errorLabel.setFont(Constants.BENGALILABELFONT);
			signupLabel.setFont(Constants.BENGALILABELFONT);
			signinButton.setFont(Constants.BENGALIBUTTONFONT);
			languageSaveButton.setFont(Constants.BENGALIBUTTONFONT);
			languageLabel.setFont(Constants.BENGALILABELFONT);

			confirmMessage="আপনি কি নিশ্চিত?";
			networkErrorMessage="নেটওয়ার্ক সমস্যা! পরে আবার চেষ্টা করুন";
		}
		else if(language.equals("English"))
		{
			frameLabel.setText("KIOSK LOGIN");
			useridLabel.setText("User-ID: ");
			passwordLabel.setText("Password: ");
			showPasswordLabel.setText("Show Password");
			errorLabel.setText("Invalid user-ID or password*");
			signinButton.setText("Sign in");
			languageSaveButton.setText("Save");
			signupLabel.setText("New user sign up here");
			languageLabel.setText("Change Language");

			useridLabel.setFont(Constants.SMALLLABELFONT);
			passwordLabel.setFont(Constants.SMALLLABELFONT);
			showPasswordLabel.setFont(Constants.SMALLLABELFONT);
			errorLabel.setFont(Constants.SMALLLABELFONT);
			signupLabel.setFont(Constants.SMALLLABELFONT);
			signinButton.setFont(Constants.SMALLBUTTONFONT);
			languageSaveButton.setFont(Constants.SMALLBUTTONFONT);
			languageLabel.setFont(Constants.SMALLLABELFONT);

			confirmMessage="Are you sure?";
			networkErrorMessage="Connection error! Try again later!";
		}
	}

	/**
	*Constructor of the login interface
	*/

	@SuppressWarnings("unchecked")
	public loginApplet()
	{
		/*set frame*/
		final JFrame jframe=this;
		setTitle("KIOSK LOGIN");
		setSize(Constants.SIZE_X,Constants.SIZE_Y);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{
				if(JOptionPane.showConfirmDialog(jframe,confirmMessage)==JOptionPane.OK_OPTION)
				{
                    System.exit(0);
					dispose();
				}
			}
		});

		frameLabel=new JLabel();
		useridBox=new JTextField();
		passwordBox=new JPasswordField();
		useridLabel=new JLabel();
		passwordLabel=new JLabel();
		showPassword=new JCheckBox();
		showPasswordLabel=new JLabel();
		errorLabel=new JLabel();
		signinButton=new JButton();
		languageSaveButton=new JButton();
		signupLabel=new JLabel();
		languageLabel=new JLabel();

		String str[]={"বাংলা","English"};
		languageComboBox=new JComboBox(str);

		setLanguage(language=Constants.readCurrentLanguage());


		frameLabel.setBounds(350,10,400,100);
		useridBox.setBounds(350,260,200,30);
		passwordBox.setBounds(350,320,200,30);
		useridLabel.setBounds(270,260,100,30);
		passwordLabel.setBounds(270,320,100,30);
		showPassword.setBounds(385,350,20,20);
		showPasswordLabel.setBounds(410,350,110,20);
		errorLabel.setBounds(350,370,310,15);
		signinButton.setBounds(400,385,100,30);
		signupLabel.setBounds(350,425,310,15);
		languageLabel.setBounds(20,620,150,30);
		languageComboBox.setBounds(20,620,100,30);
		languageSaveButton.setBounds(130,620,120,30);


		

		frameLabel.setFont(Constants.HEADERFONT);
		passwordBox.setEchoChar('*');
		// showPasswordLabel.setFont(new Font(showPasswordLabel.getFont().getName(),Font.BOLD,11));
		// errorLabel.setFont(new Font(errorLabel.getFont().getName(),Font.PLAIN,11));
		// signupLabel.setFont(new Font(signupLabel.getFont().getName(),Font.PLAIN,11));

		frameLabel.setForeground(Constants.HEADERCOLOR1);
		useridLabel.setForeground(Constants.LABELCOLOR1);
		passwordLabel.setForeground(Constants.LABELCOLOR1);
		showPassword.setBackground(Constants.JPANELCOLOR1);
		showPasswordLabel.setForeground(Constants.LABELCOLOR2);
		errorLabel.setForeground(Constants.WARNINGCOLOR);
		signupLabel.setForeground(Constants.LABELCOLOR3);
		languageLabel.setForeground(Constants.LABELCOLOR3);

		errorLabel.setVisible(false);
		languageComboBox.setVisible(false);
		languageSaveButton.setVisible(false);


		showPassword.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent ie)
			{
				if(ie.getStateChange()==ItemEvent.SELECTED)
					passwordBox.setEchoChar((char)0);
				else passwordBox.setEchoChar('*');
			}
		});

		signinButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				connection=createNewConnection();
				String username=useridBox.getText();
				String password=new String(passwordBox.getPassword());
				if(connection!=null)
				{
					if(check(username,password))
					{
						String filename="tempFolder/tempEmployee.xml";
						errorLabel.setVisible(false);
						new PatientLogin(connection,emp);
						dispose();
					}
					else
					{
						errorLabel.setText("Invalid user-ID or password*");
						errorLabel.setVisible(true);
						useridBox.setText(null);
						passwordBox.setText(null);
						connection.disconnect();
					}
				}
				else
				{
					errorLabel.setText(networkErrorMessage);
					errorLabel.setVisible(true);
				}
			}
		});

		signupLabel.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent ae)
			{

				new RegisterNewEmployee();
				dispose();
			}

			public void mouseEntered(MouseEvent ae)
			{
				signupLabel.setForeground(Constants.MOUSEENTER);
			}
			public void mouseExited(MouseEvent ae)
			{
				signupLabel.setForeground(Constants.LABELCOLOR3);
			}
		});

		languageLabel.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent ae)
			{
				languageLabel.setVisible(false);
				languageComboBox.setVisible(true);
				languageSaveButton.setVisible(true);
				if(language.equals("বাংলা"))
					languageComboBox.setSelectedIndex(0);
				else if(language.equals("English"))
					languageComboBox.setSelectedIndex(1);
			}

			public void mouseEntered(MouseEvent ae)
			{
				languageLabel.setForeground(Constants.MOUSEENTER);
			}
			public void mouseExited(MouseEvent ae)
			{
				languageLabel.setForeground(Constants.LABELCOLOR3);
			}
		});

		languageSaveButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				languageSaveButton.setVisible(false);
				languageComboBox.setVisible(false);
				languageLabel.setVisible(true);

				language=(String)languageComboBox.getSelectedItem();
				try
				{
					File file=new File("tempFolder/Language.abc");
					if(!file.isFile())
						file.createNewFile();
					FileOutputStream fout=new FileOutputStream(file);
					PrintWriter pwriter=new PrintWriter(fout);
					pwriter.println(language);
					pwriter.close();
					fout.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				setLanguage(language);
			}
		});

		add(frameLabel);
		add(useridBox);
		add(passwordBox);
		add(useridLabel);
		add(passwordLabel);
		add(showPassword);
		add(showPasswordLabel);
		add(errorLabel);
		add(signinButton);
		add(signupLabel);
		add(languageLabel);
		add(languageComboBox);
		add(languageSaveButton);

		add(Constants.JPANEL2);
		add(Constants.JPANEL1);
	}

	/**
	*Creates new connection
	*@return Connection object
	*/
	private Connection createNewConnection()
	{
		try
		{
			Socket mySocket=new Socket(InetAddress.getByName(Constants.SERVER),Constants.PORT);
			Connection myCon=new Connection(mySocket);
			
			return myCon;
		}
		catch(UnknownHostException uhe)
		{
			return null;
		}
		catch(IOException ioe)
		{
			return null;
		}
	}

	/**
	*Checks username and password
	*@param user Username provided in the username textfield
	*@param pw Password provided in the password textfield
	*@return boolean If username and password correct or not
	*/

	private boolean check(String user,String pw)
	{
		String username=user;
		String password=pw;
		String ServerFile="Server/EmployeeInfo/"+username+".xml";
		String LocalFile="tempFolder/tempEmployee.xml";
		if(connection.receiveFromServer(ServerFile,LocalFile))
		{
			try
			{
				File file=new File(LocalFile);
				JAXBContext jc=JAXBContext.newInstance(Employee.class);
				Unmarshaller jum=jc.createUnmarshaller();
				emp=(Employee)jum.unmarshal(file);
				file.delete();
				file=new File("tempFolder/tempEmployee.abc");
				file.createNewFile();
				FileWriter fw=new FileWriter(file);
				BufferedWriter bw=new BufferedWriter(fw);
				bw.write(emp.getEmployeeId());
				bw.close();
				fw.close();
				if(password.equals(emp.getPassword()))
					return true;
				else
				{
					file.delete();
					return false;
				}
			}
			catch(JAXBException jaxbe)
			{
				jaxbe.printStackTrace();
				JOptionPane.showMessageDialog(this, networkErrorMessage);
				return false;
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
				JOptionPane.showMessageDialog(this, networkErrorMessage);
				return false;
			}
		}
		else return false;
	}
}