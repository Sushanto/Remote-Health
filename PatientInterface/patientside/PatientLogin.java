package patientside;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;



public class PatientLogin extends JFrame
{
	PatientLogin(Connection myCon,Employee emp)
	{
		final Connection connection=myCon;
		final Employee employee=emp;
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new PatientLoginApplet(connection,employee);
			}
		});
	}
}

class PatientLoginApplet extends JFrame
{
	private JButton newPatient,existingPatient,backButton,submitButton,confirmButton;
	private JTextField patientIdField;
	private JLabel frameLabel,warningLabel;
	private JLabel nameLabel,nameValue;
	private JPanel patientInformationPanel;
	private Font font;
	private PatientReport patientReport;
	private String language,confirmMessage,networkErrorMessage,textFieldInfoMessage;
	private final Connection connection;
	private final Employee employee;
	
	private void setLanguage(String str)
	{
		if(str.equals("বাংলা"))
		{
			frameLabel.setText("রোগীর লগইন");
			warningLabel.setText("অবৈধ রেজিস্ট্রেশন নম্বর*");
			newPatient.setText("নতুন রোগী");
			existingPatient.setText("পুনরায় সাক্ষাৎকারি রোগী");
			backButton.setText("সাইন আউট");
			confirmButton.setText("নিশ্চিত");
			submitButton.setText("জমা দিন");

			warningLabel.setFont(Constants.BENGALILABELFONT);
			newPatient.setFont(Constants.BENGALILABELFONT);
			existingPatient.setFont(Constants.BENGALILABELFONT);
			backButton.setFont(Constants.BENGALIBUTTONFONT);
			confirmButton.setFont(Constants.BENGALIBUTTONFONT);
			submitButton.setFont(Constants.BENGALIBUTTONFONT);

			confirmMessage="আপনি কি নিশ্চিত?";
			networkErrorMessage="নেটওয়ার্ক সমস্যা! পরে আবার চেষ্টা করুন";
			textFieldInfoMessage="এখানে রোগীর রেজিস্ট্রেশন নম্বর লিখুন";
		}
		else if(str.equals("English"))
		{
			frameLabel.setText("PATIENT LOGIN");
			warningLabel.setText("Invalid registration no.*");
			newPatient.setText("New patient");
			existingPatient.setText("Existing patient");
			backButton.setText("Sign out");
			confirmButton.setText("Confirm");
			submitButton.setText("Submit");

			warningLabel.setFont(Constants.SMALLLABELFONT);
			newPatient.setFont(Constants.SMALLLABELFONT);
			existingPatient.setFont(Constants.SMALLLABELFONT);
			backButton.setFont(Constants.SMALLBUTTONFONT);
			confirmButton.setFont(Constants.SMALLBUTTONFONT);
			submitButton.setFont(Constants.SMALLBUTTONFONT);

			confirmMessage="Are you sure?";
			networkErrorMessage="Connection error! Try again later!";
			textFieldInfoMessage="Enter registration no.";
		}
	}




	public PatientLoginApplet(Connection myCon,Employee emp)
	{
		connection=myCon;
		employee=emp;
		final JFrame jframe=this;
		
		font=new Font("Monotype Corsiva",Font.BOLD,15);
		setSize(Constants.SIZE_X,Constants.SIZE_Y);
		setResizable(false);
		setTitle("PATIENT LOGIN");
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

		patientInformationPanel=new JPanel();
		frameLabel=new JLabel();
		warningLabel=new JLabel();
		patientIdField= new JTextField();
		newPatient=new JButton();
		existingPatient=new JButton();
		backButton=new JButton();
		confirmButton=new JButton();
		submitButton=new JButton();

		

		// nameLabel=new JLabel("Name :");
		// dob_label=new JLabel("Date of Birth : ");
		// phone_label=new JLabel("Phone no. : ");
		// address_label=new JLabel("Address : ");
		// gender_label=new JLabel("Gender : ");

		nameValue=new JLabel("");
		// dob_value=new JLabel("");
		// phone_value=new JLabel("");
		// address_value=new JLabel("");
		// gender_value=new JLabel("");
		setLanguage(language=Constants.readCurrentLanguage());

		patientInformationPanel.setBounds(300,500,300,30);
		frameLabel.setBounds(350,10,400,100);
		warningLabel.setBounds(300,280,300,15);
		existingPatient.setBounds(300,250,300,30);
		newPatient.setBounds(300,200,300,30);
		backButton.setBounds(50,600,100,30);
		confirmButton.setBounds(750,600,100,30);
		patientIdField.setBounds(300,250,300,30);
		submitButton.setBounds(400,300,100,30);

		// nameLabel.setBounds(50,180,200,30);
		// dob_label.setBounds(50,210,200,30);
		// gender_label.setBounds(50,240,200,30);
		// phone_label.setBounds(50,270,200,30);
		// address_label.setBounds(50,300,200,30);

		nameValue.setBounds(330,500,400,30);
		// dob_value.setBounds(300,210,200,30);
		// gender_value.setBounds(300,340,200,30);
		// phone_value.setBounds(300,270,200,30);
		// address_value.setBounds(300,300,200,30);

		frameLabel.setFont(Constants.HEADERFONT);
		nameValue.setFont(font);

		patientInformationPanel.setBackground(Constants.JPANELCOLOR1);
		frameLabel.setForeground(Constants.HEADERCOLOR1);
		warningLabel.setForeground(Constants.WARNINGCOLOR);

		confirmButton.setVisible(false);
		warningLabel.setVisible(false);
		patientIdField.setVisible(false);
		submitButton.setVisible(false);
		setPatientBasicDataVisible(false);
		warningLabel.setVisible(false);

		patientInformationPanel.setBorder(BorderFactory.createRaisedBevelBorder());


		backButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				connection.disconnect();
				new KioskLogin();
				dispose();
			}
		});

		newPatient.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				new RegisterNewPatient(connection,employee);
				dispose();
			}
		});

		existingPatient.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				patientIdField.setText(textFieldInfoMessage);
				patientIdField.setForeground(Color.GRAY);
				patientIdField.setVisible(true);
				submitButton.setVisible(true);
				existingPatient.setVisible(false);
			}
		});

		patientIdField.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent me)
			{
				if(patientIdField.getText().equals(textFieldInfoMessage))
				{	
					patientIdField.setText("");
					patientIdField.setForeground(Color.BLACK);
				}
			}

			public void mouseExited(MouseEvent me)
			{
				if(patientIdField.getText().equals(""))
				{
					patientIdField.setText(textFieldInfoMessage);
					patientIdField.setForeground(Color.GRAY);
				}
			}
		});

		patientIdField.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent ke)
			{
				if(patientIdField.getText().equals(textFieldInfoMessage))
				{	
					patientIdField.setText("");
					patientIdField.setForeground(Color.BLACK);
				}
			}
		});

		submitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				String patientId=patientIdField.getText();
				String filename="tempFolder/tempPatientReport.xml";
				if(connection.receiveFromServer("Server/PatientInfo/"+patientId+".xml",filename))
				{
					File file=new File(filename);
					try
					{
						JAXBContext jc=JAXBContext.newInstance(PatientReport.class);
						Unmarshaller jum=jc.createUnmarshaller();
						patientReport=(PatientReport)jum.unmarshal(file);
					}
					catch(JAXBException jaxbe)
					{
						jaxbe.printStackTrace();
					}
					file.delete();
					nameValue.setText(patientReport.patientBasicData.getName()+"/ "+patientReport.patientBasicData.getAge()+" yrs");

					setPatientBasicDataVisible(true);
				}
				else setPatientBasicDataVisible(false);
			}
		});

		confirmButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				new PatientForm(connection,patientReport,employee);
				dispose();
			}
		});

		Constants.NAME_LABEL.setText(employee.getName());

		add(frameLabel);
		add(warningLabel);
		add(newPatient);
		add(existingPatient);
		add(backButton);
		add(confirmButton);
		add(patientIdField);
		add(submitButton);

		add(nameValue);
		add(Constants.NAME_LABEL);

		add(patientInformationPanel);
		add(Constants.JPANEL2);
		add(Constants.JPANEL1);
	}

	private void setPatientBasicDataVisible(boolean visible)
	{
		patientInformationPanel.setVisible(visible);
		nameValue.setVisible(visible);
		confirmButton.setVisible(visible);
		warningLabel.setVisible(!visible);
	}
}