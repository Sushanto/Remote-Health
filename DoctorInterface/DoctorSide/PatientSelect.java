package DoctorSide;

import java.io.File;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;
import java.util.Collections;
import java.util.ArrayList;

/**
* Select a patient from waiting list, visited list or search by name
* @author Sushanto Halder
*/
public class PatientSelect
{
	private JFrame patientSelectFrame;
	private JButton group2KioskSelectButton,group2PatientSelectButton,group1PatientSelectButton,group3PatientSelectButton,backButton,confirmButton;
	private JTextField group3PatientIdField,group1PatientNameField;
	private JLabel group2KioskSelectLabel,group2PatientSelectLabel,group1PatientSelectLabel,frameLabel,group3WarningLabel;
	private JLabel nameLabel,nameValue,group3PatientSelectLabel;
	private JRadioButton group2RadioButton,group1RadioButton,group3RadioButton;
	private JComboBox<String> group2KioskSelectComboBox,group2PatientSelectComboBox,group1PatientSelectComboBox;
	private JPanel patientInformationPanel,group2Panel,group1Panel,group3Panel;
	private ButtonGroup groupSelect;
	private Font font;
	private PatientReport patientReport;
	private String confirmMessage,networkErrorMessage,textFieldInfoMessage;
	private final DoctorClient connection;
	private final Doctor doctor;
	private boolean isGroup2ListFilled = false;
	
	/**
	* Set text of all labels and buttons
	*/
	private void setLanguage()
	{
		frameLabel.setText("PATIENT SELECT");
		group3WarningLabel.setText("Invalid patient-ID*");
		backButton.setText("Sign out");
		confirmButton.setText("Confirm");

		group1RadioButton.setText("Recently Visited Patient :");
		group1PatientSelectLabel.setText("Select Patient :");
		group1PatientSelectButton.setText("Select");
		if(doctor.getPatientNameList().size() > 0)
			group1PatientNameField.setText(doctor.getPatientNameList().get(doctor.getPatientNameList().size()-1));

		group2RadioButton.setText("Waiting Patients :");
		group2KioskSelectLabel.setText("Select Kiosk :");
		group2PatientSelectLabel.setText("Select Patient :");
		group2KioskSelectButton.setText("Select");
		group2PatientSelectButton.setText("Select");

		group3RadioButton.setText("Search A Patient :");
		group3PatientSelectLabel.setText("Enter Patient-ID :");
		group3PatientSelectButton.setText("Select");

		group3WarningLabel.setFont(Constants.SMALLLABELFONT);
		backButton.setFont(Constants.SMALLBUTTONFONT);
		confirmButton.setFont(Constants.SMALLBUTTONFONT);

		group1RadioButton.setFont(Constants.SMALLLABELFONT);
		group1PatientSelectLabel.setFont(Constants.SMALLLABELFONT);
		group1PatientSelectButton.setFont(Constants.SMALLBUTTONFONT);
		group1PatientNameField.setFont(Constants.SMALLLABELFONT);

		group2RadioButton.setFont(Constants.SMALLLABELFONT);
		group2KioskSelectLabel.setFont(Constants.SMALLLABELFONT);
		group2PatientSelectLabel.setFont(Constants.SMALLLABELFONT);
		group2KioskSelectButton.setFont(Constants.SMALLBUTTONFONT);
		group2PatientSelectButton.setFont(Constants.SMALLBUTTONFONT);

		group3RadioButton.setFont(Constants.SMALLLABELFONT);
		group3PatientSelectLabel.setFont(Constants.SMALLLABELFONT);
		group3PatientSelectButton.setFont(Constants.SMALLBUTTONFONT);

		confirmMessage = "Are you sure?";
		networkErrorMessage = "DoctorClient error! Try again later!";
		textFieldInfoMessage = "Enter registration no.";
	}

	/**
	* Creates the GUI
	* @param myCon DoctorClient object, used for file transfer between server and client
	* @param doc Doctor object, information of doctor
	*/
	protected PatientSelect(DoctorClient myCon,Doctor doc)
	{
		patientSelectFrame = new JFrame();
		connection = myCon;
		doctor = doc;
		final JFrame jframe = patientSelectFrame;
		
		font = new Font("Monotype Corsiva",Font.BOLD,15);
		patientSelectFrame.setSize(Constants.SIZE_X,Constants.SIZE_Y);
		patientSelectFrame.setResizable(false);
		patientSelectFrame.setTitle("PATIENT SELECT");
		patientSelectFrame.setVisible(true);
		patientSelectFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		patientSelectFrame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{
				if(JOptionPane.showConfirmDialog(jframe,confirmMessage) == JOptionPane.OK_OPTION)
				{
                    System.exit(0);
					patientSelectFrame.dispose();
				}
			}
		});

		patientInformationPanel = new JPanel();
		group2Panel = new JPanel();
		group1Panel = new JPanel();
		group3Panel = new JPanel();

		frameLabel = new JLabel();
		group3WarningLabel = new JLabel();
		group2KioskSelectLabel = new JLabel();
		group2PatientSelectLabel = new JLabel();
		group1PatientSelectLabel = new JLabel();
		group3PatientSelectLabel = new JLabel();

		group1PatientNameField = new JTextField();
		group3PatientIdField =  new JTextField();

		backButton = new JButton();
		confirmButton = new JButton();
		group2KioskSelectButton = new JButton();
		group2PatientSelectButton = new JButton();
		group1PatientSelectButton = new JButton();
		group3PatientSelectButton = new JButton();

		String[] kioskList = {"01","02","03"};
		group2KioskSelectComboBox = new JComboBox<String>(kioskList);
		group2PatientSelectComboBox = new JComboBox<String>();
		group1PatientSelectComboBox = new JComboBox<String>();

		group2RadioButton = new JRadioButton();
		group1RadioButton = new JRadioButton();
		group3RadioButton = new JRadioButton();

		groupSelect = new ButtonGroup();

		Collections.reverse(doctor.getPatientIdList());
		group1PatientSelectComboBox.setModel(getDefaultComboBoxModel(doctor.getPatientIdList()));
		Collections.reverse(doctor.getPatientIdList());

		// nameLabel = new JLabel("Name :");
		// dob_label = new JLabel("Date of Birth : ");
		// phone_label = new JLabel("Phone no. : ");
		// address_label = new JLabel("Address : ");
		// gender_label = new JLabel("Gender : ");

		nameValue = new JLabel("");
		// dob_value = new JLabel("");
		// phone_value = new JLabel("");
		// address_value = new JLabel("");
		// gender_value = new JLabel("");
		setLanguage();

		patientInformationPanel.setBounds(0,500,900,200);
		frameLabel.setBounds(280,10,400,100);
		backButton.setBounds(50,600,100,30);
		confirmButton.setBounds(750,600,100,30);

		nameValue.setBounds(330,550,400,30);

		group1Panel.setBounds(0,100,300,400);
		group1RadioButton.setBounds(40,220,210,25);
		group1PatientSelectLabel.setBounds(40,250,210,25);
		group1PatientSelectComboBox.setBounds(40,280,210,25);
		group1PatientNameField.setBounds(40,310,210,25);
		group1PatientSelectButton.setBounds(40,340,210,25);


		group2Panel.setBounds(300,100,300,400);
		group2RadioButton.setBounds(340,220,210,25);
		group2KioskSelectLabel.setBounds(340,250,210,25);
		group2KioskSelectComboBox.setBounds(340,280,210,25);
		group2KioskSelectButton.setBounds(340,310,210,25);
		group2PatientSelectLabel.setBounds(340,340,210,25);
		group2PatientSelectComboBox.setBounds(340,370,210,25);
		group2PatientSelectButton.setBounds(340,400,210,25);


		group3Panel.setBounds(600,100,300,400);
		group3RadioButton.setBounds(640,220,210,25);
		group3PatientSelectLabel.setBounds(640,250,210,25);
		group3PatientIdField.setBounds(640,280,210,25);
		group3WarningLabel.setBounds(640,300,210,20);
		group3PatientSelectButton.setBounds(640,330,210,25);

		frameLabel.setFont(Constants.HEADERFONT);
		nameValue.setFont(font);

		patientInformationPanel.setBackground(Constants.JPANELCOLOR1);
		frameLabel.setForeground(Constants.HEADERCOLOR1);
		group3WarningLabel.setForeground(Constants.WARNINGCOLOR);

		// confirmButton.setVisible(false);
		// group3WarningLabel.setVisible(false);
		// group3PatientIdField.setVisible(false);
		// setPatientBasicDataVisible(false);
		// group3WarningLabel.setVisible(false);

		patientInformationPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		group1Panel.setBorder(BorderFactory.createRaisedBevelBorder());
		group2Panel.setBorder(BorderFactory.createRaisedBevelBorder());
		group3Panel.setBorder(BorderFactory.createRaisedBevelBorder());


		backButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					connection.logoutRequest();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				new DoctorLogin();
				patientSelectFrame.dispose();
			}
		});

		group1RadioButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				setGroup1Enabled(true);
				setGroup2Enabled(false);
				setGroup3Enabled(false);
			}
		});

		group2RadioButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				setGroup1Enabled(false);
				setGroup2Enabled(true);
				setGroup3Enabled(false);
			}
		});

		group3RadioButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				setGroup1Enabled(false);
				setGroup2Enabled(false);
				setGroup3Enabled(true);
			}
		});

		group1PatientSelectComboBox.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent ie)
			{
				int reverseIdIndex = group1PatientSelectComboBox.getSelectedIndex();
				int nameIndex = doctor.getPatientNameList().size()-reverseIdIndex-1;
				group1PatientNameField.setText(doctor.getPatientNameList().get(nameIndex));
			}
		});

		group1PatientSelectButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				String patientId = (String)group1PatientSelectComboBox.getSelectedItem();
				selectButtonAction(patientId);
			}
		});

		group2KioskSelectButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				/*
				* Get patient log from that partculat Kiosk
				* Set it to combobox
				* If no patient log found show error message
				*/
				String fileName = Constants.dataFolder+"tempLog.xml";
				File file = new File(fileName);
				int response=0;
				try
				{
					response = connection.getRequest("Patient_"+(String)group2KioskSelectComboBox.getSelectedItem()+"_Log.xml",fileName);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				if(response >= 0)
				{
					PatientLog patientLog;
					try
					{
						JAXBContext jc = JAXBContext.newInstance(PatientLog.class);
						Unmarshaller jum = jc.createUnmarshaller();
						patientLog = (PatientLog)jum.unmarshal(file);


						if(patientLog != null)
						{
							group2PatientSelectLabel.setEnabled(true);
							group2PatientSelectComboBox.setEnabled(true);
							group2PatientSelectButton.setEnabled(true);
							isGroup2ListFilled = true;

							group2PatientSelectComboBox.removeAllItems();
							for(int i = 0;i < patientLog.getEmergency().size();i++)
								group2PatientSelectComboBox.addItem("E-"+patientLog.getEmergency().get(i));
							for(int i = 0;i < patientLog.getNormal().size();i++)
								group2PatientSelectComboBox.addItem("N-"+patientLog.getNormal().get(i));
						}
						else
						{
							group2PatientSelectLabel.setEnabled(false);
							group2PatientSelectComboBox.setEnabled(false);
							group2PatientSelectButton.setEnabled(false);
							isGroup2ListFilled = false;
						}
						file.delete();
					}
					catch(JAXBException jaxbe)
					{
						jaxbe.printStackTrace();
					}
				}
				else
				{
					if(file.isFile())
						file.delete();
					if(response == -2)
						JOptionPane.showMessageDialog(jframe,"No log file found!!");
					else
					{
						JOptionPane.showMessageDialog(jframe,RHErrors.getErrorDescription(response));
						try
						{
							connection.logoutRequest();
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						new DoctorLogin();
						patientSelectFrame.dispose();
					}
				}
			}
		});

		group2PatientSelectButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				String str = (String)group2PatientSelectComboBox.getSelectedItem();
				String[] patientId = str.split("-");
				selectButtonAction(patientId[1]);
			}
		});

		group3PatientIdField.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent me)
			{
				if(group3PatientIdField.getText().equals(textFieldInfoMessage))
				{	
					group3PatientIdField.setText("");
					group3PatientIdField.setForeground(Color.BLACK);
				}
			}

			public void mouseExited(MouseEvent me)
			{
				if(group3PatientIdField.getText().equals(""))
				{
					group3PatientIdField.setText(textFieldInfoMessage);
					group3PatientIdField.setForeground(Color.GRAY);
				}
			}
		});

		group3PatientIdField.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent ke)
			{
				if(group3PatientIdField.getText().equals(textFieldInfoMessage))
				{	
					group3PatientIdField.setText("");
					group3PatientIdField.setForeground(Color.BLACK);
				}
			}
		});

		group3PatientSelectButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				String patientId = group3PatientIdField.getText();
				selectButtonAction(patientId);
			}
		});

		confirmButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				String[] tempArray = patientReport.getPatientBasicData().getId().split("_");
				String kioskNumber = tempArray[1];
				new PatientPrescriptionForm(connection,doctor,patientReport,kioskNumber);
				patientSelectFrame.dispose();
			}
		});

		Constants.NAME_LABEL.setText(doctor.getDoctorName());
		// Constants.JPANEL1.setBounds(0,550,900,400);

		patientSelectFrame.add(frameLabel);
		patientSelectFrame.add(group3WarningLabel);
		patientSelectFrame.add(backButton);
		patientSelectFrame.add(confirmButton);

		groupSelect.add(group1RadioButton);
		patientSelectFrame.add(group1PatientSelectLabel);
		patientSelectFrame.add(group1PatientSelectComboBox);
		patientSelectFrame.add(group1PatientNameField);
		patientSelectFrame.add(group1PatientSelectButton);

		groupSelect.add(group2RadioButton);
		patientSelectFrame.add(group2KioskSelectLabel);
		patientSelectFrame.add(group2KioskSelectComboBox);
		patientSelectFrame.add(group2KioskSelectButton);
		patientSelectFrame.add(group2PatientSelectLabel);
		patientSelectFrame.add(group2PatientSelectComboBox);
		patientSelectFrame.add(group2PatientSelectButton);


		groupSelect.add(group3RadioButton);
		patientSelectFrame.add(group3PatientSelectLabel);
		patientSelectFrame.add(group3PatientIdField);
		patientSelectFrame.add(group3PatientSelectButton);

		patientSelectFrame.add(group2RadioButton);
		patientSelectFrame.add(group1RadioButton);
		patientSelectFrame.add(group3RadioButton);
		patientSelectFrame.add(nameValue);
		patientSelectFrame.add(Constants.NAME_LABEL);

		patientSelectFrame.add(patientInformationPanel);
		patientSelectFrame.add(group1Panel);
		patientSelectFrame.add(group2Panel);
		patientSelectFrame.add(group3Panel);
		patientSelectFrame.add(Constants.JPANEL2);
		patientSelectFrame.add(Constants.JPANEL1);

		setGroup1Enabled(false);
		setGroup2Enabled(true);
		setGroup3Enabled(false);
		group2RadioButton.setSelected(true);
		confirmButton.setVisible(false);
	}

	/**
	* Set patient name and age visible or non-visible
	* @param visible if true, set visible else set non-visible
	*/
	private void setPatientBasicDataVisible(boolean visible)
	{
		patientInformationPanel.setVisible(visible);
		nameValue.setVisible(visible);
		confirmButton.setVisible(visible);
		group3WarningLabel.setVisible(!visible);
	}

	/**
	* Let group1 labels and buttons enable
	* @param enable If true, set enable else disable
	*/
	private void setGroup1Enabled(boolean enable)
	{
		group1PatientSelectLabel.setEnabled(enable);
		group1PatientSelectComboBox.setEnabled(enable);
		group1PatientNameField.setEnabled(enable);
		group1PatientSelectButton.setEnabled(enable);
		// group1Panel.setEnabled(enable);
	}

	/**
	* Let group2 labels and buttons enable
	* @param enable If true, set enable else disable
	*/
	private void setGroup2Enabled(boolean enable)
	{
		group2KioskSelectLabel.setEnabled(enable);
		group2KioskSelectComboBox.setEnabled(enable);
		group2KioskSelectButton.setEnabled(enable);
		group2PatientSelectLabel.setEnabled(enable & isGroup2ListFilled);
		group2PatientSelectComboBox.setEnabled(enable & isGroup2ListFilled);
		group2PatientSelectButton.setEnabled(enable & isGroup2ListFilled);
		// group2Panel.setEnabled(enable);
	}

	/**
	* Let group3 labels and buttons enable
	* @param enable If true, set enable else disable
	*/
	private void setGroup3Enabled(boolean enable)
	{
		group3PatientSelectLabel.setEnabled(enable);
		group3PatientIdField.setEnabled(enable);
		group3WarningLabel.setEnabled(enable);
		group3PatientSelectButton.setEnabled(enable);
		// group3Panel.setEnabled(enable);
	}

	/**
	* Change combobox options
	* @param list List of new choices
	* @return New set of choices in combobox
	*/
	private DefaultComboBoxModel<String> getDefaultComboBoxModel(ArrayList<String> list)
	{
		String []array = new String[list.size()];
		list.toArray(array);
		return new DefaultComboBoxModel<String>(array);
	}

	/**
	* Download the patient file specified by patientId, and set name and age
	* @param patientId Id of patient choosed by doctor
	*/
	private void selectButtonAction(String patientId)
	{
		String fileName = Constants.dataFolder+"tempPatientReport.xml";
		File file = new File(fileName);
		int response=0;
		try
		{
			response = connection.getRequest(patientId+".xml",fileName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		if(response >= 0)
		{
			try
			{
				JAXBContext jc = JAXBContext.newInstance(PatientReport.class);
				Unmarshaller jum = jc.createUnmarshaller();
				patientReport = (PatientReport)jum.unmarshal(file);
				nameValue.setVisible(true);
				nameValue.setText(patientReport.getPatientBasicData().getName()+"/ "+patientReport.getPatientBasicData().getAge()+" yrs");
				confirmButton.setVisible(true);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			file.delete();
			group3WarningLabel.setVisible(false);
		}
		else
		{
			if(file.isFile())
				file.delete();
			if(response == -2)
			{
				group3WarningLabel.setVisible(true);
				nameValue.setVisible(false);
				confirmButton.setVisible(false);
			}
			else
			{
				JOptionPane.showMessageDialog(patientSelectFrame,RHErrors.getErrorDescription(response));
				try
				{
					connection.logoutRequest();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				new DoctorLogin();
				patientSelectFrame.dispose();
			}
		}
	}
}