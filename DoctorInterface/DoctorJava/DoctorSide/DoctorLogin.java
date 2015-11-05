package DoctorSide;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;
import java.io.File;
/**
* DoctorLogin: First frame for Doctor's login
* @author Sushanto Halder
*/
public class DoctorLogin
{
	private JFrame doctorLoginFrame;
	private JTextField useridBox;
	private JPasswordField passwordBox;
	private JLabel useridLabel,passwordLabel,errorLabel,signupLabel,showPasswordLabel,frameLabel;
	private JPanel jpanel1,jpanel2;
	private JButton signinButton;
	private JCheckBox showPassword;
	private String confirmMessage,networkErrorMessage;
	private DoctorClient connection;

	/**
	* Sets all texts
	*/
	private void setLanguage()
	{
		frameLabel.setText("DOCTOR LOGIN");
		useridLabel.setText("User-ID: ");
		passwordLabel.setText("Password: ");
		showPasswordLabel.setText("Show Password");
		errorLabel.setText("Invalid user-ID or password*");
		signinButton.setText("Sign in");
		signupLabel.setText("New user sign up here");

		useridLabel.setFont(Constants.SMALLLABELFONT);
		passwordLabel.setFont(Constants.SMALLLABELFONT);
		showPasswordLabel.setFont(Constants.SMALLLABELFONT);
		errorLabel.setFont(Constants.SMALLLABELFONT);
		signupLabel.setFont(Constants.SMALLLABELFONT);
		signinButton.setFont(Constants.SMALLBUTTONFONT);

		confirmMessage = "Are you sure?";
		networkErrorMessage = "connection error! Try again later!";
	}

	/**
	* Creates the doctor login GUI
	*/
	@SuppressWarnings("unchecked")
	protected DoctorLogin()
	{
		/*set frame*/
		doctorLoginFrame = new JFrame();
		final JFrame jframe = doctorLoginFrame;
		doctorLoginFrame.setTitle("DOCTOR LOGIN");
		doctorLoginFrame.setSize(Constants.SIZE_X,Constants.SIZE_Y);
		doctorLoginFrame.setResizable(false);
		doctorLoginFrame.setVisible(true);
		doctorLoginFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		doctorLoginFrame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{
				if(JOptionPane.showConfirmDialog(jframe,confirmMessage) == JOptionPane.OK_OPTION)
				{
                    System.exit(0);
					doctorLoginFrame.dispose();
				}
			}
		});

		/*
		* Initialize all labels, buttons etc.
		*/
		frameLabel = new JLabel();
		useridBox = new JTextField();
		passwordBox = new JPasswordField();
		useridLabel = new JLabel();
		passwordLabel = new JLabel();
		showPassword = new JCheckBox();
		showPasswordLabel = new JLabel();
		errorLabel = new JLabel();
		signinButton = new JButton();
		signupLabel = new JLabel();


		setLanguage();


		/*
		* Set positions
		*/
		frameLabel.setBounds(325,10,400,100);
		useridBox.setBounds(350,260,200,30);
		passwordBox.setBounds(350,320,200,30);
		useridLabel.setBounds(270,260,100,30);
		passwordLabel.setBounds(270,320,100,30);
		showPassword.setBounds(385,350,20,20);
		showPasswordLabel.setBounds(410,350,110,20);
		errorLabel.setBounds(350,370,310,15);
		signinButton.setBounds(400,385,100,30);
		signupLabel.setBounds(350,425,310,15);


		/*
		* Set fonts and colors
		*/
		frameLabel.setFont(Constants.HEADERFONT);
		passwordBox.setEchoChar('*');
		frameLabel.setForeground(Constants.HEADERCOLOR1);
		useridLabel.setForeground(Constants.LABELCOLOR1);
		passwordLabel.setForeground(Constants.LABELCOLOR1);
		showPassword.setBackground(Constants.JPANELCOLOR1);
		showPasswordLabel.setForeground(Constants.LABELCOLOR2);
		errorLabel.setForeground(Constants.WARNINGCOLOR);
		signupLabel.setForeground(Constants.LABELCOLOR3);

		errorLabel.setVisible(false);


		/*
		* Add action listeners
		*/
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
				/*
				* create new connection,
				* send login request to server
				* if username and password correct then
				*	get doctor's information
				*	go to PatientSelect frame
				* else
				* 	show error message
				*	send logout request to server
				* end if
				*/
				connection = createNewDoctorClient();
				String username = useridBox.getText();
				String password = new String(passwordBox.getPassword());
				if(connection != null)
				{
					if(check(username,password))
					{
						String filename = Constants.dataFolder+"tempEmployee.xml";
						errorLabel.setVisible(false);
						final Doctor doctor = getDoctor(username);
						if(doctor != null)
						{
							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									new PatientSelect(connection,doctor);
								}
							});
							System.out.println("Success");
							doctorLoginFrame.dispose();
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
			/*
			* Not added yet
			*/
			public void mouseClicked(MouseEvent ae)
			{

				// new RegisterNewEmployee();
				// doctorLoginFrame.dispose();
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

		/*
		* Add button, labels to frame
		*/
		doctorLoginFrame.add(frameLabel);
		doctorLoginFrame.add(useridBox);
		doctorLoginFrame.add(passwordBox);
		doctorLoginFrame.add(useridLabel);
		doctorLoginFrame.add(passwordLabel);
		doctorLoginFrame.add(showPassword);
		doctorLoginFrame.add(showPasswordLabel);
		doctorLoginFrame.add(errorLabel);
		doctorLoginFrame.add(signinButton);
		doctorLoginFrame.add(signupLabel);

		doctorLoginFrame.add(Constants.JPANEL2);
		doctorLoginFrame.add(Constants.JPANEL1);
	}

	/**
	* Get Doctor's information
	* @param username Doctor's username
	* @return Doctor object, in case of any error return null
	*/
	private Doctor getDoctor(String username)
	{
		/*
		* Temporary doctor file name
		*/
		String doctorFileName = Constants.dataFolder+"tempDoctor.xml";
		File doctorFile = new File(doctorFileName);
		int response;
		try
		{
			response = connection.getRequest(username+".xml",doctorFileName);
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
				JAXBContext jc = JAXBContext.newInstance(Doctor.class);
				Unmarshaller jum = jc.createUnmarshaller();
				Doctor doctor = (Doctor)jum.unmarshal(doctorFile);
				doctorFile.delete();
				errorLabel.setVisible(false);
				return doctor;
			}
			catch(JAXBException jaxbe)
			{
				jaxbe.printStackTrace();
				return null;
			}
		}
		else
		{
			if(doctorFile.isFile())
				doctorFile.delete();
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
				JOptionPane.showMessageDialog(doctorLoginFrame,RHErrors.getErrorDescription(response));
			}
			return null;
		}
	}


	/**
	*Creates new DoctorClient
	*@return DoctorClient object
	*/
	private DoctorClient createNewDoctorClient()
	{
		try
		{
			DoctorClient myCon = new DoctorClient(Constants.doctorId,Constants.serverHostName,Constants.serverPort);
			return myCon;
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
		try
		{
			int response = connection.loginRequest(user,pw);
			if(response >= 0)
				return true;
			else JOptionPane.showMessageDialog(doctorLoginFrame, RHErrors.getErrorDescription(response));
			return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
}