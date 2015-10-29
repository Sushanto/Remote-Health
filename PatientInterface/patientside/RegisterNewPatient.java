package patientside;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.JAXBException;

/**
* RegisterNewPatient: Frame to register new patient
* @author Sourav Maji
*/
public class RegisterNewPatient
{
	private JFrame frame;
	private JTextField nameField,phoneField,ageField,referenceField,occuField,heightField;

	private final ButtonGroup jb = new ButtonGroup();
	private final ButtonGroup cb = new ButtonGroup();
	private JTextArea addressArea,familyHisArea,medicalHisArea;
	private JButton btnSubmit,btnReset,btnBack;
	private JRadioButton rdbtnMale,rdbtnFemale;
	private JLabel warnField,lblPidvalue,lblOccupation,lblStatus,lblReview,lblHeight,heightUnit_label,lblFamilyHistory,lblMedicalHistory;
	private JCheckBox chckbxSon,chckbxDaughter,chckbxW;
	private JLabel status_value,lblImage;
	private JLabel lblPatientId,lblBasicInformation,lblName,lblDate,lblDateValue,lblGender,lblAge,ageUnit_label,lblPhoneNumber,lblAddress;
	private Font LABELFONT = new Font("Serif",Font.BOLD,16);

	private String patientId,fileDirectory,confirmMessage = "আপনি কি নিশ্চিত?",networkErrorMessage = "নেটওয়ার্ক সমস্যা! পরে আবার চেষ্টা করুন";
	private String submissionConfirmMessage = "তথ্য সংরক্ষিত করা হয়েছে";
	private String imageFileName;

	private int countId;

	private String nameVar,reNameVar,dateVar,referenceVar,genVar,ageVar,phoneVar,addressVar,occuVar,statusVar,heightVar,familyVar,medicalVar;
	private final Connection connection;
	private Employee emp;
	private PatientReport patientReport;

	/**
	* Checks if fields are valid or not
	* @return Return true if all fields are non-empty and valid
	*/
	private boolean checkField()
	{
		boolean emptyCheck = !(nameVar.trim().isEmpty() || addressVar.trim().isEmpty() 
		 || occuVar.trim().isEmpty() || referenceField.getText().trim().isEmpty() || ageVar.trim().isEmpty()
		 || heightVar.trim().isEmpty());

		boolean nameCheck = !nameVar.matches(".*[0-9]+.*");
		boolean sdwCheck = !referenceField.getText().matches(".*[0-9]+.*");
		boolean occupationCheck = !occuVar.matches(".*[0-9]+.*");
		boolean phoneCheck = !phoneField.getText().matches(".*[a-zA-Z]+.*");
		boolean ageCheck = !ageVar.matches(".*[a-zA-Z]+.*");
		boolean heightCheck = !heightVar.matches(".*[a-zA-Z]+.*");

		if(nameCheck)
			nameField.setBorder(BorderFactory.createLineBorder(Color.black));
		else
			nameField.setBorder(BorderFactory.createLineBorder(Color.red));
		if(sdwCheck)
			referenceField.setBorder(BorderFactory.createLineBorder(Color.black));
		else
			referenceField.setBorder(BorderFactory.createLineBorder(Color.red));
		if(occupationCheck)
			occuField.setBorder(BorderFactory.createLineBorder(Color.black));
		else
			occuField.setBorder(BorderFactory.createLineBorder(Color.red));
		if(phoneCheck)
			phoneField.setBorder(BorderFactory.createLineBorder(Color.black));
		else
			phoneField.setBorder(BorderFactory.createLineBorder(Color.red));
		if(ageCheck)
			ageField.setBorder(BorderFactory.createLineBorder(Color.black));
		else
			ageField.setBorder(BorderFactory.createLineBorder(Color.red));
		if(heightCheck)
			heightField.setBorder(BorderFactory.createLineBorder(Color.black));
		else
			heightField.setBorder(BorderFactory.createLineBorder(Color.red));

		return (emptyCheck & nameCheck & sdwCheck & occupationCheck & phoneCheck & ageCheck & heightCheck);
	}

	/**
	* Checks if a string is null or empty
	* @param str String to be checked
	* @return String if not null or empty else return null
	*/
	private String checkNullString(String str)
	{
		if(str.equals(""))
			return null;
		else return str;
	}

	/**
	* Get values of fields
	*/
	private void getValues(){
		nameVar = nameField.getText();
		addressVar = addressArea.getText();
		phoneVar = phoneField.getText();
		if(rdbtnMale.isSelected())
			genVar = "Male";
		else genVar = "Female";
		ageVar = ageField.getText();
		if(chckbxSon.isSelected())
			referenceVar = "Son";
		else if(chckbxDaughter.isSelected())
			referenceVar = "Daughter";
		else if(chckbxW.isSelected())
			referenceVar = "Wife";
		reNameVar = referenceVar + " of " + referenceField.getText();
		occuVar = occuField.getText();


		statusVar = "New";
		heightVar = heightField.getText();
		// weightVar = weightField.getText();
		familyVar = checkNullString(familyHisArea.getText());
		medicalVar = checkNullString(medicalHisArea.getText());
		

	}

	/**
	* Reset all fields
	*/
	private void resetField(){
		phoneField.setText("");
		addressArea.setText("");
		nameField.setText("");
		referenceField.setText("");
		phoneField.setText("");
		ageField.setText("");
		occuField.setText("");
		heightField.setText("");
		familyHisArea.setText("");
		medicalHisArea.setText("");
		warnField.setVisible(false);
		if(new File(Constants.dataPath + imageFileName).exists())
			new File(Constants.dataPath + imageFileName).delete();
		lblImage.setIcon(null);
	}

	/**
	* Increment patientIdCount for the particular Kiosk
	*/
	private void incrementPatientIdCount()
	{
		try
		{
			File file = new File(Constants.dataPath + "PatientIdCount.txt");
			file.createNewFile();
			BufferedWriter bout = new BufferedWriter(new FileWriter(file));
			bout.write(String.valueOf(countId));
			bout.close();
			int sendResponse;
			if((sendResponse = connection.sendToServer(Constants.dataPath + "PatientIdCount.txt",Constants.finalDataPath + "Patient_" + Constants.kioskNo + "_IdCount.txt")) < 0)
			{
				JOptionPane.showMessageDialog(frame,RHErrors.getErrorDescription(sendResponse));
				file.delete();
				connection.unlockFile("Patient_" + Constants.kioskNo + "_IdCount.txt");
				new PatientLogin(connection,emp);
				frame.dispose();
			}
			else file.delete();
			connection.unlockFile("Patient_" + Constants.kioskNo + "_IdCount.txt");	
		}
		catch(IOException e)
		{
			e.printStackTrace();
			connection.unlockFile("Patient_" + Constants.kioskNo + "_IdCount.txt");	
		}
	}

	/**
	* Create a new Id for new patient
	*/
	private void createId()
	{
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
		this.dateVar = ft.format(date);
		this.fileDirectory = Constants.dataPath + "";
		// Thread.sleep(100);
		int lockResponse = connection.lockFile("Patient_" + Constants.kioskNo + "_IdCount.txt");
		if(lockResponse < 0)
		{
			JOptionPane.showMessageDialog(frame, RHErrors.getErrorDescription(lockResponse));
			new KioskLogin();
			frame.dispose();
		}
		int response = connection.receiveFromServer("Patient_" + Constants.kioskNo + "_IdCount.txt",Constants.dataPath + "PatientIdCount.txt");
		if(response >= 0)
		{
			try
			{
				BufferedReader bin = new BufferedReader(new FileReader(this.fileDirectory + "PatientIdCount.txt"));
				countId = Integer.parseInt(bin.readLine());
				bin.close();
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
			String temp = "Patient_" + Constants.kioskNo + "_" + String.format("%02d",(countId++));
			patientId = temp;
			if((new File(Constants.dataPath + "PatientIdCount.txt")).isFile())
				(new File(Constants.dataPath + "PatientIdCount.txt")).delete();
		}
		else
		{
			if(response == -2)
				JOptionPane.showMessageDialog(frame,"File does not exist");    
			else
			{
				JOptionPane.showMessageDialog(frame,RHErrors.getErrorDescription(response));
				new KioskLogin();
				frame.dispose();
			}
		}
	}
	
	/**
	* Take picture from default camera
	*/
	private void takePicture()
	{
		try
		{
			Process ps = Runtime.getRuntime().exec("java "/**/ + "-cp PatientApp.jar "/**/ + "patientside.CaptureImage " + Constants.dataPath + imageFileName);
			ps.waitFor();
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	
	}

	/**
	* Set image to the image label
	*/
	private void setImage()
	{
		if(new File(Constants.dataPath + imageFileName).exists())
		{
			ImageIcon imageIcon = new ImageIcon(Constants.dataPath + imageFileName); // load the image to a imageIcon
			int h = lblImage.getHeight();
			int w = lblImage.getWidth();
		
		
			Image image = imageIcon.getImage(); // transform it 
			Image newimg = image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			imageIcon = new ImageIcon(newimg);
			lblImage.setIcon(imageIcon);
		}
	}

	/**
	* Set language of the frame
	* @param str Language to be set, English or Bengali
	*/
	private void setLanguage(String str)
	{
		if(str.equals("Bengali"))
		{
			lblPatientId.setText("রেজিস্ট্রেশন নম্বর : ");
			lblBasicInformation.setText("প্রাথমিক তথ্য");
			lblName.setText("নাম : ");
			chckbxSon.setText("পুত্র");
			chckbxDaughter.setText("কন্যা");
			chckbxW.setText("স্ত্রী");
			lblDate.setText("তারিখ :");
			lblGender.setText("লিঙ্গ : ");
			rdbtnMale.setText("পুরুষ");
			rdbtnFemale.setText("মহিলা");
			lblAge.setText("বয়স :");
			ageUnit_label.setText("বছর");
			lblPhoneNumber.setText("ফোন নম্বর : ");
			lblAddress.setText("ঠিকানা : ");
			warnField.setText("*** সব ক্ষেত্র পূর্ণ করা আবশ্যক");
			lblOccupation.setText("পেশা :");
			lblStatus.setText("অবস্থা : ");
			lblReview.setText("নতুন");
			lblHeight.setText("উচ্চতা :");
			heightUnit_label.setText("সেমি");
			lblFamilyHistory.setText("পারিবারিক তথ্য : ");
			lblMedicalHistory.setText("<html>চিকিৎসার<br>তথ্য&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp:</html>");
			btnSubmit.setText("জমা");
			btnBack.setText("ফেরত যান");
			btnReset.setText("পরিষ্কার");

			lblPatientId.setFont(Constants.BENGALILABELFONT);
			// lblBasicInformation.setFont(Constants.BENGALILABELFONT);
			lblName.setFont(Constants.BENGALILABELFONT);
			chckbxSon.setFont(Constants.BENGALILABELFONT);
			chckbxDaughter.setFont(Constants.BENGALILABELFONT);
			chckbxW.setFont(Constants.BENGALILABELFONT);
			lblDate.setFont(Constants.BENGALILABELFONT);
			lblGender.setFont(Constants.BENGALILABELFONT);
			rdbtnMale.setFont(Constants.BENGALILABELFONT);
			rdbtnFemale.setFont(Constants.BENGALILABELFONT);
			lblAge.setFont(Constants.BENGALILABELFONT);
			ageUnit_label.setFont(Constants.BENGALILABELFONT);
			lblPhoneNumber.setFont(Constants.BENGALILABELFONT);
			lblAddress.setFont(Constants.BENGALILABELFONT);
			warnField.setFont(Constants.BENGALILABELFONT);
			lblOccupation.setFont(Constants.BENGALILABELFONT);
			lblStatus.setFont(Constants.BENGALILABELFONT);
			lblReview.setFont(Constants.BENGALILABELFONT);
			lblHeight.setFont(Constants.BENGALILABELFONT);
			heightUnit_label.setFont(Constants.BENGALILABELFONT);
			lblFamilyHistory.setFont(Constants.BENGALILABELFONT);
			lblMedicalHistory.setFont(Constants.BENGALILABELFONT);
			btnSubmit.setFont(Constants.BENGALILABELFONT);
			btnBack.setFont(Constants.BENGALILABELFONT);
			btnReset.setFont(Constants.BENGALILABELFONT);


			confirmMessage = "আপনি কি নিশ্চিত?";
			networkErrorMessage = "নেটওয়ার্ক সমস্যা! পরে আবার চেষ্টা করুন";
			submissionConfirmMessage = "তথ্য সংরক্ষিত করা হয়েছে";

			lblBasicInformation.setBounds(380, 12, 500, 43);
		}
		else if(str.equals("English"))
		{
			lblPatientId.setText("Registration no. : ");
			lblBasicInformation.setText("BASIC INFORMATION");
			lblName.setText("Name : ");
			chckbxSon.setText("Son");
			chckbxDaughter.setText("D");
			chckbxW.setText("W");
			lblDate.setText("Date :");
			lblGender.setText("Gender : ");
			rdbtnMale.setText("Male");
			rdbtnFemale.setText("Female");
			lblAge.setText("Age :");
			ageUnit_label.setText("Years");
			lblPhoneNumber.setText("Phone: ");
			lblAddress.setText("Address: ");
			warnField.setText("*** All fields are mandatory");
			lblOccupation.setText("Occup :");
			lblStatus.setText("Status: ");
			lblReview.setText("New");
			lblHeight.setText("Height:");
			heightUnit_label.setText("cm");
			lblFamilyHistory.setText("Family history : ");
			lblMedicalHistory.setText("<html>Medical<br>history&nbsp&nbsp&nbsp:</html>");
			btnSubmit.setText("Submit");
			btnBack.setText("Back");
			btnReset.setText("Clear");

			lblPatientId.setFont(Constants.SMALLLABELFONT);
			// lblBasicInformation.setFont(Constants.SMALLLABELFONT);
			lblName.setFont(Constants.SMALLLABELFONT);
			chckbxSon.setFont(Constants.SMALLLABELFONT);
			chckbxDaughter.setFont(Constants.SMALLLABELFONT);
			chckbxW.setFont(Constants.SMALLLABELFONT);
			lblDate.setFont(Constants.SMALLLABELFONT);
			lblGender.setFont(Constants.SMALLLABELFONT);
			rdbtnMale.setFont(Constants.SMALLLABELFONT);
			rdbtnFemale.setFont(Constants.SMALLLABELFONT);
			lblAge.setFont(Constants.SMALLLABELFONT);
			ageUnit_label.setFont(Constants.SMALLLABELFONT);
			lblPhoneNumber.setFont(Constants.SMALLLABELFONT);
			lblAddress.setFont(Constants.SMALLLABELFONT);
			warnField.setFont(Constants.SMALLLABELFONT);
			lblOccupation.setFont(Constants.SMALLLABELFONT);
			lblStatus.setFont(Constants.SMALLLABELFONT);
			lblReview.setFont(Constants.SMALLLABELFONT);
			lblHeight.setFont(Constants.SMALLLABELFONT);
			heightUnit_label.setFont(Constants.SMALLLABELFONT);
			lblFamilyHistory.setFont(Constants.SMALLLABELFONT);
			lblMedicalHistory.setFont(Constants.SMALLLABELFONT);
			btnSubmit.setFont(Constants.SMALLLABELFONT);
			btnBack.setFont(Constants.SMALLLABELFONT);
			btnReset.setFont(Constants.SMALLLABELFONT);

			confirmMessage = "Are you sure?";
			networkErrorMessage = "Connection error! Try again later!";
			submissionConfirmMessage = "Data Saved";


			lblBasicInformation.setBounds(220, 12, 500, 43);
		}
	}

	/**
	* Creates the GUI
	* @param myCon Connection object, used for communication with the local server
	* @param e Employee object, contains information of the employee
	*/
	public RegisterNewPatient(Connection myCon,Employee e)
	{
		connection = myCon;
		emp = e;
		frame = new JFrame();
		frame.setVisible(true);
		

		frame.setResizable(false);
		frame.setTitle("KIOSK ENTERPRISE");
		frame.setBackground(UIManager.getColor("Button.focus"));
		frame.setBounds(55, 25, 860, 500);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLayout(null);
		frame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{

				if(JOptionPane.showConfirmDialog(frame,confirmMessage) == JOptionPane.YES_OPTION)
				{
					if(new File(Constants.dataPath + imageFileName).exists())
						new File(Constants.dataPath + imageFileName).delete();
					frame.dispose();
                    System.exit(0);
				}
			}
		});
		createId();
		imageFileName = patientId + "_image.jpg";


		//patient id
		lblPatientId = new JLabel("রেজিস্ট্রেশন নম্বর : ");
		lblPatientId.setForeground(Color.WHITE);
		lblPatientId.setFont(LABELFONT);
		lblPatientId.setBackground(UIManager.getColor("Button.focus"));
		lblPatientId.setBounds(10, 70, 130, 23);
		frame.add(lblPatientId);

		lblPidvalue = new JLabel(patientId);
		lblPidvalue.setFont(new Font("Century Schoolbook L", Font.BOLD | Font.ITALIC, 16));
		lblPidvalue.setForeground(Color.WHITE);
		lblPidvalue.setBounds(140, 70, 230, 23);
		frame.add(lblPidvalue);
		
		
		//BASIC INFORMATION
		lblBasicInformation = new JLabel("প্রাথমিক তথ্য");
		lblBasicInformation.setForeground(Constants.HEADERCOLOR1);
		lblBasicInformation.setFont(Constants.HEADERFONT);
		lblBasicInformation.setBounds(380, 12, 500, 43);
		frame.add(lblBasicInformation);
		
		//NAME
		lblName = new JLabel("নাম : ");
		// lblName.setFont(LABELFONT);
		lblName.setFont(LABELFONT);
		lblName.setForeground(Color.BLACK);
		lblName.setBounds(170, 120, 60, 23);
		frame.add(lblName);
		
		nameField = new JTextField();
		nameField.setSelectedTextColor(Color.BLACK);
		nameField.setForeground(Color.BLACK);
		nameField.setFont(new Font("Century Schoolbook L", Font.BOLD, 12));
		nameField.setBounds(230, 119, 230, 30);
		frame.add(nameField);
		nameField.setColumns(10);


		//REFERENCE FIELD
		chckbxSon = new JCheckBox("পুত্র");
		chckbxSon.setSelected(true);
		cb.add(chckbxSon);
		chckbxSon.setForeground(Color.BLACK);
		chckbxSon.setFont(LABELFONT);
		chckbxSon.setBackground(UIManager.getColor("Button.focus"));
		chckbxSon.setBounds(465, 120, 50, 23);
		frame.add(chckbxSon);
		
		chckbxDaughter = new JCheckBox("কন্যা");
		cb.add(chckbxDaughter);
		chckbxDaughter.setBackground(UIManager.getColor("Button.focus"));
		chckbxDaughter.setFont(LABELFONT);
		chckbxDaughter.setForeground(Color.BLACK);
		chckbxDaughter.setBounds(515, 120, 50, 23);
		frame.add(chckbxDaughter);
		
		chckbxW = new JCheckBox("স্ত্রী");
		cb.add(chckbxW);
		chckbxW.setBackground(UIManager.getColor("Button.focus"));
		chckbxW.setForeground(Color.BLACK);
		chckbxW.setFont(LABELFONT);
		chckbxW.setBounds(565, 120, 45, 23);
		frame.add(chckbxW);

		lblDate = new JLabel("তারিখ :");
		lblDate.setFont(LABELFONT);
		lblDate.setForeground(Color.WHITE);
		lblDate.setBounds(680, 70, 50, 23);
		frame.add(lblDate);

		lblDateValue = new JLabel(dateVar);
		lblDateValue.setFont(new Font("Century Schoolbook L", Font.ITALIC | Font.BOLD, 16));
		lblDateValue.setForeground(Color.WHITE);
		lblDateValue.setBounds(740, 70, 100, 23);
		frame.add(lblDateValue);

		
		referenceField = new JTextField();
		referenceField.setBounds(607, 119, 223, 30);
		frame.add(referenceField);
		referenceField.setColumns(10);
		
		//GENDER
		lblGender = new JLabel("লিঙ্গ : ");
		lblGender.setForeground(Color.BLACK);
		lblGender.setFont(LABELFONT);
		lblGender.setBounds(170, 160, 70, 23);
		frame.add(lblGender);
		
		
		rdbtnMale = new JRadioButton("পুরুষ");
		rdbtnMale.setSelected(true);
		rdbtnMale.setFont(LABELFONT);
		rdbtnMale.setBackground(UIManager.getColor("Button.focus"));
		rdbtnMale.setForeground(Color.BLACK);
		jb.add(rdbtnMale);
		rdbtnMale.setBounds(230, 160, 70, 23);
		frame.add(rdbtnMale);
		
		rdbtnFemale = new JRadioButton("মহিলা");
		rdbtnFemale.setFont(LABELFONT);
		rdbtnFemale.setBackground(UIManager.getColor("Button.focus"));
		rdbtnFemale.setForeground(Color.BLACK);
		jb.add(rdbtnFemale);
		rdbtnFemale.setBounds(290, 160, 90, 23);
		frame.add(rdbtnFemale);

		
		//AGE
		lblAge = new JLabel("বয়স :");
		lblAge.setFont(LABELFONT);
		lblAge.setForeground(Color.BLACK);
		lblAge.setBounds(430, 160, 50, 23);
		frame.add(lblAge);
		
		ageField = new JTextField();
		ageField.setBounds(470, 160, 90, 30);
		frame.add(ageField);
		ageField.setColumns(10);

		ageUnit_label = new JLabel("বছর");
		ageUnit_label.setFont(LABELFONT);
		ageUnit_label.setForeground(Color.BLACK);
		ageUnit_label.setBounds(562,160,48,23);
		frame.add(ageUnit_label);

		//PHONE NUMBER
		lblPhoneNumber = new JLabel("ফোন নম্বর : ");
		lblPhoneNumber.setFont(LABELFONT);
		lblPhoneNumber.setForeground(Color.BLACK);
		lblPhoneNumber.setBounds(170, 200, 60, 23);
		frame.add(lblPhoneNumber);
		

		phoneField = new JTextField();
		phoneField.setBounds(230, 200, 230, 30);
		frame.add(phoneField);
		phoneField.setColumns(10);
		
		
		//ADDRESS
		lblAddress = new JLabel("ঠিকানা : ");
		lblAddress.setFont(LABELFONT);
		lblAddress.setForeground(Color.BLACK);
		lblAddress.setBounds(465, 200, 75, 23);
		frame.add(lblAddress);
		
		addressArea = new JTextArea();
		addressArea.setBounds(530, 200, 300, 80);
		addressArea.setLineWrap(true);
		frame.add(addressArea);

		JScrollPane ap = new JScrollPane(addressArea);
		ap.setBounds(530,200,300,80);
		frame.add(ap);
		
		//WARN FIELD
		warnField = new JLabel("*** সব ক্ষেত্র পূর্ণ করা আবশ্যক");
		warnField.setVisible(false);
		warnField.setForeground(Color.RED);
		warnField.setFont(new Font("Dialog", Font.ITALIC, 14));
		warnField.setBounds(10, 420, 300, 23);
		frame.add(warnField);

		
		//OCCUPATION
		lblOccupation = new JLabel("পেশা :");
		lblOccupation.setFont(LABELFONT);
		lblOccupation.setForeground(Color.BLACK);
		lblOccupation.setBounds(170, 240, 50, 23);
		frame.add(lblOccupation);
		
		occuField = new JTextField();
		occuField.setBounds(230, 240, 230, 30);
		frame.add(occuField);
		occuField.setColumns(10);

		//IMAGE
		lblImage = new JLabel();
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		lblImage.setBorder(border);
		lblImage.setBounds(10, 110, 150, 150);
		lblImage.setBackground(Color.lightGray);
		lblImage.setOpaque(true);
		frame.add(lblImage);
		
		//STATUS
		lblStatus = new JLabel("অবস্থা : ");
		lblStatus.setFont(LABELFONT);
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setBounds(335, 70, 60, 23);
		frame.add(lblStatus);

		lblReview = new JLabel("নতুন");
		lblReview.setFont(LABELFONT);
		lblReview.setForeground(Color.WHITE);
		lblReview.setBounds(385, 70, 55, 23);
		frame.add(lblReview);
		

		
		//HEIGHT
		lblHeight = new JLabel("উচ্চতা :");
		lblHeight.setForeground(Color.BLACK);
		lblHeight.setFont(LABELFONT);
		lblHeight.setBounds(630, 160, 55, 23);
		frame.add(lblHeight);
		
		heightField = new JTextField();
		heightField.setBounds(680, 160, 115, 30);
		frame.add(heightField);
		heightField.setColumns(10);

		heightUnit_label = new JLabel("সেমি");
		heightUnit_label.setFont(LABELFONT);
		heightUnit_label.setForeground(Color.BLACK);
		heightUnit_label.setBounds(797,160,30,23);
		frame.add(heightUnit_label);



		
		//FAMILY HISTORY
		lblFamilyHistory = new JLabel("পারিবারিক তথ্য : ");
		lblFamilyHistory.setForeground(Color.BLACK);
		lblFamilyHistory.setFont(LABELFONT);
		lblFamilyHistory.setBounds(10, 290, 120, 23);
		frame.add(lblFamilyHistory);
		
		familyHisArea = new JTextArea();
		familyHisArea.setBounds(130, 290, 330, 80);
		familyHisArea.setLineWrap(true);
		frame.add(familyHisArea);

		JScrollPane fp = new JScrollPane(familyHisArea);
		fp.setBounds(130, 290, 330, 80);
		frame.add(fp);

		//MEDICAL HISTORY
		lblMedicalHistory = new JLabel("<html>চিকিৎসার<br>তথ্য&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp:</html>");
		lblMedicalHistory.setForeground(Color.BLACK);
		lblMedicalHistory.setFont(LABELFONT);
		lblMedicalHistory.setBounds(465, 290, 100, 50);
		frame.add(lblMedicalHistory);

		 medicalHisArea = new JTextArea();
		medicalHisArea.setBounds(530, 290, 300, 80);
		medicalHisArea.setLineWrap(true);
		frame.add(medicalHisArea);

		JScrollPane mp = new JScrollPane(medicalHisArea);
		mp.setBounds(530, 290, 300, 80);
		frame.add(mp);

		
		//SUBMIT BUTTON
		btnSubmit = new JButton("জমা");
		btnSubmit.setBorder(UIManager.getBorder("EditorPane.border"));
		btnSubmit.setFont(Constants.BENGALIBUTTONFONT);
		btnSubmit.setForeground(Color.BLACK);
		btnSubmit.setBounds(720, 380, 110, 30);
		frame.add(btnSubmit);

		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				getValues();

				if(checkField())
				{
					warnField.setVisible(false);
					
					incrementPatientIdCount();
					createPatientBasicData();
					JOptionPane.showMessageDialog(frame,submissionConfirmMessage);
					new PatientForm(connection,patientReport,emp);
					frame.dispose();
				}
				else{
					warnField.setVisible(true);
				}
			}
		});
		
		//BACK BUTTON
		btnBack = new JButton("ফেরত যান");
		btnBack.setForeground(Color.BLACK);
		btnBack.setFont(Constants.BENGALIBUTTONFONT);
		btnBack.setBorder(UIManager.getBorder("EditorPane.border"));
		btnBack.setBounds(10, 380, 110, 30);
		frame.add(btnBack);
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(new File(Constants.dataPath + imageFileName).exists())
					new File(Constants.dataPath + imageFileName).delete();
				new PatientLogin(connection,emp);
				frame.dispose();
			}
		});

		//RESET BUTTON
		btnReset = new JButton("পরিষ্কার");
		btnReset.setForeground(Color.BLACK);
		btnReset.setFont(Constants.BENGALIBUTTONFONT);
		btnReset.setBorder(UIManager.getBorder("EditorPane.border"));
		btnReset.setBounds(720, 420, 110, 30);
		frame.add(btnReset);

		setLanguage(Constants.language);


		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetField();
			}
		});

		//image handler
		lblImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				takePicture();
				setImage();
				
			}
		});
				
		frame.add(Constants.JPANEL2);
		frame.add(Constants.JPANEL1);
	}

	private void createPatientBasicData()
	{
		patientReport = new PatientReport();
		patientReport.setPatientBasicData( new PatientBasicData() );


		patientReport.getPatientBasicData().setId(patientId);
		patientReport.getPatientBasicData().setName(nameVar);
		patientReport.getPatientBasicData().setDate(dateVar);
		patientReport.getPatientBasicData().setAddress(addressVar);
		patientReport.getPatientBasicData().setPhone(phoneVar);
		// patientReport.getPatientBasicData().setDateofbirth(birthVar);
		patientReport.getPatientBasicData().setGender(genVar);
		patientReport.getPatientBasicData().setReference(reNameVar);
		patientReport.getPatientBasicData().setAge(ageVar);
		patientReport.getPatientBasicData().setOccupation(occuVar);
		patientReport.getPatientBasicData().setStatus(statusVar);
		patientReport.getPatientBasicData().setHeight(heightVar);
		if(new File(Constants.dataPath + imageFileName).exists())
			patientReport.getPatientBasicData().setImage(imageFileName);
		else			
			patientReport.getPatientBasicData().setImage(null);

		patientReport.getPatientBasicData().setFamilyhistory(familyVar);
		patientReport.getPatientBasicData().setMedicalhistory(medicalVar);

		try
		{
			JAXBContext context = JAXBContext.newInstance(PatientReport.class);
		    Marshaller m = context.createMarshaller();
		    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		    m.marshal(patientReport, new File(Constants.dataPath + "tempPatient.xml"));
		}
		catch(JAXBException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame,networkErrorMessage);
			new PatientLogin(connection,emp);
			frame.dispose();
		}
	    int sendResponse;

	    // try
	    // {
    	File imageFile = new File(Constants.dataPath + imageFileName);
	    if(imageFile.isFile() && (sendResponse = connection.sendToServer(Constants.dataPath + imageFileName,Constants.tempDataPath + imageFileName)) < 0)
	    {
	    	JOptionPane.showMessageDialog(frame,RHErrors.getErrorDescription(sendResponse));
	    	new PatientLogin(connection,emp);
	    	frame.dispose();
	    }
	    if(imageFile.isFile())
		    imageFile.delete();
		// }
		// catch(Exception e)
		// {
		// 	e.printStackTrace();
		// }

	    if((sendResponse = connection.sendToServer(Constants.dataPath + "tempPatient.xml",Constants.tempDataPath + patientId + ".xml")) < 0)
	    {
	    	JOptionPane.showMessageDialog(frame,RHErrors.getErrorDescription(sendResponse));
	    	new PatientLogin(connection,emp);
	    	frame.dispose();
	    }
	    (new File(Constants.dataPath + "tempPatient.xml")).delete();
	}
}