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
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.DirectoryNotEmptyException;

/**
* Kiosk Coordinator login interface class
* @author Sushanto Halder
*/

public class KioskLogin
{
	private JFrame kioskLoginFrame;
	private JTextField useridBox;
	private JPasswordField passwordBox;
	private JLabel useridLabel,passwordLabel,errorLabel,signupLabel,showPasswordLabel,frameLabel,languageLabel;
	private JPanel jpanel1,jpanel2;
	private JButton signinButton,languageSaveButton;
	private JCheckBox showPassword;
	private JComboBox languageComboBox;
	private String confirmMessage,networkErrorMessage;
	private KioskClient connection;
	private Employee emp;

	/**
	* Set language specified by language
	* @param language Bengali/English
	*/
	private void setLanguage(String language)
	{
		if(language.equals("Bengali"))
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

			confirmMessage = "আপনি কি নিশ্চিত?";
			networkErrorMessage = "নেটওয়ার্ক সমস্যা! পরে আবার চেষ্টা করুন";
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

			confirmMessage = "Are you sure?";
			networkErrorMessage = "Connection error! Try again later!";
		}
	}

	/**
	* Creates the GUI
	*/

	@SuppressWarnings("unchecked")
	public KioskLogin()
	{
		/*set frame*/
		kioskLoginFrame = new JFrame();
		final JFrame jframe = kioskLoginFrame;
		kioskLoginFrame.setTitle("KIOSK LOGIN");
		kioskLoginFrame.setSize(Constants.SIZE_X,Constants.SIZE_Y);
		kioskLoginFrame.setResizable(false);
		kioskLoginFrame.setVisible(true);
		kioskLoginFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		kioskLoginFrame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{
				if(JOptionPane.showConfirmDialog(jframe,confirmMessage) == JOptionPane.OK_OPTION)
				{
                    System.exit(0);
					kioskLoginFrame.dispose();
				}
			}
		});

		frameLabel = new JLabel();
		useridBox = new JTextField();
		passwordBox = new JPasswordField();
		useridLabel = new JLabel();
		passwordLabel = new JLabel();
		showPassword = new JCheckBox();
		showPasswordLabel = new JLabel();
		errorLabel = new JLabel();
		signinButton = new JButton();
		languageSaveButton = new JButton();
		signupLabel = new JLabel();
		languageLabel = new JLabel();

		String str[] = {"বাংলা","English"};
		languageComboBox = new JComboBox(str);

		setLanguage(Constants.language);


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
				if(ie.getStateChange() == ItemEvent.SELECTED)
					passwordBox.setEchoChar((char)0);
				else passwordBox.setEchoChar('*');
			}
		});

		signinButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				connection = createNewConnection();
				String username = useridBox.getText();
				String password = new String(passwordBox.getPassword());
				try
				{
					String usernameParts[] = username.split("_");
					if(!usernameParts[1].equals(Constants.kioskNo))
					{
						JOptionPane.showMessageDialog(kioskLoginFrame, "Cannot login here! Wrong Kiosk!");
						return;
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(kioskLoginFrame, "Username not valid!");
					return;
				}

				if(connection != null)
				{
					if(check(username,password))
					{
						String filename = Constants.dataPath+"tempEmployee.xml";
						errorLabel.setVisible(false);
						final Employee employee = getEmployee(username);
						if(employee != null)
						{
							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									new PatientLogin(connection,employee);
								}
							});
							System.out.println("Success");
							kioskLoginFrame.dispose();
						}
					}
					else
					{
						errorLabel.setText("Invalid user-ID or password*");
						errorLabel.setVisible(true);
						useridBox.setText(null);
						passwordBox.setText(null);
						try
						{
							connection.logoutRequest();
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
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

				// new RegisterNewEmployee();
				// kioskLoginFrame.dispose();
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
				if(Constants.language.equals("Bengali"))
					languageComboBox.setSelectedIndex(0);
				else if(Constants.language.equals("English"))
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

				if(languageComboBox.getSelectedIndex() == 0)
					Constants.language = "Bengali";
				else Constants.language = "English";

				changeKioskLanguage();

				setLanguage(Constants.language);
			}
		});

		kioskLoginFrame.add(frameLabel);
		kioskLoginFrame.add(useridBox);
		kioskLoginFrame.add(passwordBox);
		kioskLoginFrame.add(useridLabel);
		kioskLoginFrame.add(passwordLabel);
		kioskLoginFrame.add(showPassword);
		kioskLoginFrame.add(showPasswordLabel);
		kioskLoginFrame.add(errorLabel);
		kioskLoginFrame.add(signinButton);
		kioskLoginFrame.add(signupLabel);
		kioskLoginFrame.add(languageLabel);
		kioskLoginFrame.add(languageComboBox);
		kioskLoginFrame.add(languageSaveButton);

		kioskLoginFrame.add(Constants.JPANEL2);
		kioskLoginFrame.add(Constants.JPANEL1);
	}

	/**
	* Changes language of kiosk, english or bengali
	*/
	private void changeKioskLanguage()
	{
		File tempFile = new File("tempData.cfg");
		try
		{
			FileReader fReader = new FileReader(new File("DeviceMetadata.cfg"));
			BufferedReader bReader = new BufferedReader(fReader);
			System.out.println("Kiosk Information reading....");

			tempFile.createNewFile();
			FileWriter fWriter = new FileWriter(tempFile);
			PrintWriter pWriter = new PrintWriter(fWriter);

			String line;
			while((line = bReader.readLine()) != null)
			{
				String[] tokens = line.split("=");
				if(!tokens[0].equals("LANGUAGE"))
					pWriter.println(line);
			}
			pWriter.print("LANGUAGE=" + Constants.language);

			bReader.close();
			fReader.close();

			fWriter.close();
			pWriter.close();

			moveFile("tempData.cfg","DeviceMetadata.cfg");

		}
		catch(Exception ioe)
		{
			ioe.printStackTrace();
			if(tempFile.isFile())
				tempFile.delete();
		}
	}

	/**
	* Get Employee's information
	* @param username Employee's username
	* @return Employee object, in case of any error return null
	*/
	private Employee getEmployee(String username)
	{
		/*
		* Temporary employee file name
		*/
		String employeeFileName = Constants.dataPath+"tempDoctor.xml";
		File employeeFile = new File(employeeFileName);
		int response;
		try
		{
			response = connection.getRequest(username+".xml",employeeFileName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		if(response >= 0)
		{
			try
			{
				/*
				* Parse with JAXB parser
				*/
				JAXBContext jc = JAXBContext.newInstance(Employee.class);
				Unmarshaller jum = jc.createUnmarshaller();
				Employee employee = (Employee)jum.unmarshal(employeeFile);
				employeeFile.delete();
				errorLabel.setVisible(false);
				return employee;
			}
			catch(JAXBException jaxbe)
			{
				jaxbe.printStackTrace();
				return null;
			}
		}
		else
		{
			if(employeeFile.isFile())
				employeeFile.delete();
			try
			{
				connection.logoutRequest();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			connection = null;
			if(response == -2)
				errorLabel.setVisible(true);
			else
			{
				errorLabel.setVisible(false);
				JOptionPane.showMessageDialog(kioskLoginFrame,RHErrors.getErrorDescription(response));
			}
			return null;
		}
	}

	/**
	* Creates new connection
	* @return KioskClient object
	*/
	private KioskClient createNewConnection()
	{
		try
		{
			KioskClient myCon = new KioskClient(Constants.deviceId,Constants.serverHostName,Constants.serverPort,"final/Kiosk_01");
			return myCon;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	* Checks username and password
	* @param user Username provided in the username textfield
	* @param pw Password provided in the password textfield
	* @return boolean If username and password correct or not
	*/

	private boolean check(String user,String pw)
	{
		try
		{
			int response = connection.loginRequest(user,pw);
			if(response >= 0)
				return true;
			else JOptionPane.showMessageDialog(kioskLoginFrame, RHErrors.getErrorDescription(response));
			return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	* Move a file
	* @param source Source file name
	* @param destination Destination file name
	* @return Destination file object
	*/
	private File moveFile(String source, String destination) throws DirectoryNotEmptyException, IOException, SecurityException
	{
		Path src = Paths.get(source);
		Path dst = Paths.get(destination);
		Files.move(src, dst, StandardCopyOption.REPLACE_EXISTING);
		File copied = dst.toFile();
		return copied;
	}
}