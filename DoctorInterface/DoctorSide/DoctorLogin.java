package DoctorSide;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import javax.xml.bind.*;

public class DoctorLogin
{
	public static void main(String args[])
	{
		if(!(new File(Constants.dataFolder)).exists())
			(new File(Constants.dataFolder)).mkdir();
		new DoctorLogin();
	}

	public DoctorLogin()
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
            		java.util.logging.Logger.getLogger(DoctorLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (InstantiationException ex)
        		{
            		java.util.logging.Logger.getLogger(DoctorLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (IllegalAccessException ex)
        		{
            		java.util.logging.Logger.getLogger(DoctorLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (javax.swing.UnsupportedLookAndFeelException ex)
        		{
            		java.util.logging.Logger.getLogger(DoctorLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
				new DoctorLoginApplet();
			}
		});
	}
}

class DoctorLoginApplet extends JFrame
{
	private JTextField useridBox;
	private JPasswordField passwordBox;
	private JLabel useridLabel,passwordLabel,errorLabel,signupLabel,showPasswordLabel,frameLabel;
	private JPanel jpanel1,jpanel2;
	private JButton signinButton;
	private JCheckBox showPassword;
	private String confirmMessage,networkErrorMessage;
	private DoctorClient connection;

	/**
	*Set language specified by language
	*@param language Bengali/English
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
	*Constructor of the login interface
	*/

	@SuppressWarnings("unchecked")
	protected DoctorLoginApplet()
	{
		/*set frame*/
		final JFrame jframe = this;
		setTitle("DOCTOR LOGIN");
		setSize(Constants.SIZE_X,Constants.SIZE_Y);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{
				if(JOptionPane.showConfirmDialog(jframe,confirmMessage) == JOptionPane.OK_OPTION)
				{
                    System.exit(0);
					dispose();
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
		signupLabel = new JLabel();


		setLanguage();


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

		errorLabel.setVisible(false);


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
				connection = createNewDoctorClient();
				String username = useridBox.getText();
				String password = new String(passwordBox.getPassword());
				if(connection != null)
				{
					if(check(username,password))
					{
						String filename = Constants.dataFolder+"tempEmployee.xml";
						errorLabel.setVisible(false);
						Doctor doctor = getDoctor(username);
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
							dispose();
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
				// dispose();
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

		add(Constants.JPANEL2);
		add(Constants.JPANEL1);
	}

	/**
	*Creates new DoctorClient
	*@return DoctorClient object
	*/

	private Doctor getDoctor(String username)
	{
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
				JOptionPane.showMessageDialog(this,RHErrors.getErrorDescription(response));
			}
			return null;
		}
	}

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
			else JOptionPane.showMessageDialog(this, RHErrors.getErrorDescription(response));
			return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
}