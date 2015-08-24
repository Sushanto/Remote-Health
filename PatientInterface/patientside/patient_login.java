package patientside;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.xml.bind.*;
import javax.xml.bind.annotation.*;



public class patient_login extends JFrame
{
	public static void main(String args[])
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new patient_login_applet();
			}
		});
	}

	patient_login()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new patient_login_applet();
			}
		});
	}
}

class patient_login_applet extends JFrame
{
	private Constants Constant=new Constants();
	JButton newPatient,existingPatient,back_button,submit_button,confirm_button;
	JTextField patientIdField;
	JLabel frame_label,warning_label;
	JLabel name_label,name_value;
	JPanel patientInformation_Panel;
	Font font;
	Patient_Report patient_Report;
	String Language,confirmMessage,networkErrorMessage,textFieldInfoMessage;
	
	void setLanguage(String str)
	{
		if(str.equals("বাংলা"))
		{
			frame_label.setText("রোগীর লগইন");
			warning_label.setText("অবৈধ রেজিস্ট্রেশন নম্বর*");
			newPatient.setText("নতুন রোগী");
			existingPatient.setText("পুনরায় সাক্ষাৎকারি রোগী");
			back_button.setText("সাইন আউট");
			confirm_button.setText("নিশ্চিত");
			submit_button.setText("জমা দিন");

			warning_label.setFont(Constant.BENGALILABELFONT);
			newPatient.setFont(Constant.BENGALILABELFONT);
			existingPatient.setFont(Constant.BENGALILABELFONT);
			back_button.setFont(Constant.BENGALIBUTTONFONT);
			confirm_button.setFont(Constant.BENGALIBUTTONFONT);
			submit_button.setFont(Constant.BENGALIBUTTONFONT);

			confirmMessage="আপনি কি নিশ্চিত?";
			networkErrorMessage="নেটওয়ার্ক সমস্যা! পরে আবার চেষ্টা করুন";
			textFieldInfoMessage="এখানে রোগীর রেজিস্ট্রেশন নম্বর লিখুন";
		}
		else if(str.equals("English"))
		{
			frame_label.setText("PATIENT LOGIN");
			warning_label.setText("Invalid registration no.*");
			newPatient.setText("New patient");
			existingPatient.setText("Existing patient");
			back_button.setText("Sign out");
			confirm_button.setText("Confirm");
			submit_button.setText("Submit");

			warning_label.setFont(Constant.SMALLLABELFONT);
			newPatient.setFont(Constant.SMALLLABELFONT);
			existingPatient.setFont(Constant.SMALLLABELFONT);
			back_button.setFont(Constant.SMALLBUTTONFONT);
			confirm_button.setFont(Constant.SMALLBUTTONFONT);
			submit_button.setFont(Constant.SMALLBUTTONFONT);

			confirmMessage="Are you sure?";
			networkErrorMessage="Connection error! Try again later!";
			textFieldInfoMessage="Enter registration no.";
		}
	}




	patient_login_applet()
	{
		final String filename="tempFolder/tempEmployee.xml";
		final JFrame jframe=this;
		
		font=new Font("Monotype Corsiva",Font.BOLD,15);
		setSize(Constant.SIZE_X,Constant.SIZE_Y);
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
					if((new File("tempFolder/tempEmployee.abc")).isFile())
						(new File("tempFolder/tempEmployee.abc")).delete();
					File file=new File(filename);
					if(file.isFile())
						file.delete();
					// if(!(new File("tempFolder")).exists())
					// 	(new File("tempFolder")).delete();
                    System.exit(0);
					dispose();
				}
			}
		});

		patientInformation_Panel=new JPanel();
		frame_label=new JLabel();
		warning_label=new JLabel();
		patientIdField= new JTextField();
		newPatient=new JButton();
		existingPatient=new JButton();
		back_button=new JButton();
		confirm_button=new JButton();
		submit_button=new JButton();

		

		// name_label=new JLabel("Name :");
		// dob_label=new JLabel("Date of Birth : ");
		// phone_label=new JLabel("Phone no. : ");
		// address_label=new JLabel("Address : ");
		// gender_label=new JLabel("Gender : ");

		name_value=new JLabel("");
		// dob_value=new JLabel("");
		// phone_value=new JLabel("");
		// address_value=new JLabel("");
		// gender_value=new JLabel("");
		setLanguage(Language=Constant.readCurrentLanguage());

		patientInformation_Panel.setBounds(300,500,300,30);
		frame_label.setBounds(350,10,400,100);
		warning_label.setBounds(300,280,300,15);
		existingPatient.setBounds(300,250,300,30);
		newPatient.setBounds(300,200,300,30);
		back_button.setBounds(50,600,100,30);
		confirm_button.setBounds(750,600,100,30);
		patientIdField.setBounds(300,250,300,30);
		submit_button.setBounds(400,300,100,30);

		// name_label.setBounds(50,180,200,30);
		// dob_label.setBounds(50,210,200,30);
		// gender_label.setBounds(50,240,200,30);
		// phone_label.setBounds(50,270,200,30);
		// address_label.setBounds(50,300,200,30);

		name_value.setBounds(330,500,400,30);
		// dob_value.setBounds(300,210,200,30);
		// gender_value.setBounds(300,340,200,30);
		// phone_value.setBounds(300,270,200,30);
		// address_value.setBounds(300,300,200,30);

		frame_label.setFont(Constant.HEADERFONT);
		// warning_label.setFont(new Font(warning_label.getFont().getName(),Font.PLAIN,11));
		// name_label.setFont(font);

		name_value.setFont(font);

		patientInformation_Panel.setBackground(Constant.JPANELCOLOR1);
		frame_label.setForeground(Constant.HEADERCOLOR1);
		warning_label.setForeground(Constant.WARNINGCOLOR);

		confirm_button.setVisible(false);
		warning_label.setVisible(false);
		patientIdField.setVisible(false);
		submit_button.setVisible(false);
		setPatientBasicDataVisible(false);
		warning_label.setVisible(false);

		patientInformation_Panel.setBorder(BorderFactory.createRaisedBevelBorder());


		back_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if((new File("tempFolder/tempEmployee.abc")).isFile())
					(new File("tempFolder/tempEmployee.abc")).delete();
				new kiosk_login();
				File file=new File(filename);
				if(file.isFile())
					file.delete();
				dispose();
			}
		});

		newPatient.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				new RegisterNewPatient();
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
				submit_button.setVisible(true);
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

		submit_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				String patientId=patientIdField.getText();
				String filename="tempFolder/tempPatient_Report.xml";
				if(Constant.RecieveFromServer("Server/PatientInfo/"+patientId+".xml",filename))
				{
					// Constant.RecieveFromServer("Server/PatientInfo/"+patientId+".xml","temp.xml");
					File file=new File(filename);
					getPatientBasicData(file);
					setPatientBasicDataVisible(true);
				}
				else setPatientBasicDataVisible(false);
			}
		});

		confirm_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				new PatientForm(patient_Report.patientBasicData.getId());
				// PatientHealthInformation patientHealthInformation=new PatientHealthInformation(patient_Report.patientBasicData.getId());
				// try
				// {
				// 	patientHealthInformation.t.join();
				// }
				// catch(Exception e)
				// {
				// 	System.out.println("Error : "+e);
				// }
				// System.out.println("Patient login disposed");
				dispose();
			}
		});

		Employee emp=GetEmployeeData(filename);
		Constant.NAME_LABEL.setText(emp.getName());

		add(frame_label);
		add(warning_label);
		add(newPatient);
		add(existingPatient);
		add(back_button);
		add(confirm_button);
		add(patientIdField);
		add(submit_button);

		// add(name_label);
		// add(dob_label);
		// add(phone_label);
		// add(address_label);
		// add(gender_label);

		add(name_value);
		// add(dob_value);
		// add(phone_value);
		// add(address_value);
		// add(gender_value);

		add(Constant.NAME_LABEL);
		add(patientInformation_Panel);
		add(Constant.JPANEL2);
		add(Constant.JPANEL1);
	}

	void getPatientBasicData(File file)
	{
		try
		{
			JAXBContext jc=JAXBContext.newInstance(Patient_Report.class);
			Unmarshaller jum=jc.createUnmarshaller();
			patient_Report=(Patient_Report)jum.unmarshal(file);

			// Marshaller jm=jc.createMarshaller();
			// jm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
			// jm.marshal(patient_Report,new File("tempFolder/tempPatient_Report.xml"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		file.delete();
		name_value.setText(patient_Report.patientBasicData.getName()+"/ "+patient_Report.patientBasicData.getAge()+" yrs");
		// dob_value.setText(patient_Report.patientBasicData.getDateofbirth());
		// phone_value.setText(patient_Report.patientBasicData.getPhone());
		// address_value.setText(patient_Report.patientBasicData.getAddress());
		// gender_value.setText(patient_Report.patientBasicData.getGender());
	}

	void setPatientBasicDataVisible(boolean VISIBLE)
	{
		patientInformation_Panel.setVisible(VISIBLE);
		// name_label.setVisible(VISIBLE);
		// dob_label.setVisible(VISIBLE);
		// phone_label.setVisible(VISIBLE);
		// address_label.setVisible(VISIBLE);
		// gender_label.setVisible(VISIBLE);

		name_value.setVisible(VISIBLE);
		// dob_value.setVisible(VISIBLE);
		// phone_value.setVisible(VISIBLE);
		// address_value.setVisible(VISIBLE);
		// gender_value.setVisible(VISIBLE);

		confirm_button.setVisible(VISIBLE);
		warning_label.setVisible(!VISIBLE);
	}

	Employee GetEmployeeData(String filename)
	{
		Employee emp;
		try
		{
			Thread.sleep(100);
			File file=new File(filename);
			FileReader fr=new FileReader(new File("tempFolder/tempEmployee.abc"));
			BufferedReader br=new BufferedReader(fr);
			String EmployeeId=br.readLine();
			br.close();
			fr.close();
			if(Constant.RecieveFromServer("Server/EmployeeInfo/"+EmployeeId+".xml",filename))
			{
				JAXBContext context=JAXBContext.newInstance(Employee.class);
				Unmarshaller um=context.createUnmarshaller();

				emp=(Employee)um.unmarshal(file);
				file.delete();
				return emp;
			}
			else
			{
				JOptionPane.showMessageDialog(this,networkErrorMessage+EmployeeId);
				// (new File("tempFolder/tempEmployee.abc")).delete();
				if((new File("tempFolder/tempEmployee.abc")).isFile())
					(new File("tempFolder/tempEmployee.abc")).delete();
				if((new File("tempFolder/tempEmployee.xml")).isFile())
					(new File("tempFolder/tempEmployee.xml")).delete();
				new kiosk_login();
				dispose();
				return emp=new Employee();
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			emp=new Employee();
			return emp;
		}
	}
}