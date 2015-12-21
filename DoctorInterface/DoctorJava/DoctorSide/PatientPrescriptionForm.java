package DoctorSide;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Desktop;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
// import org.apache.commons.io.FileUtils;
// import com.github.sarxos.webcam.Webcam;
// import com.github.sarxos.webcam.WebcamPanel;
// import com.github.sarxos.webcam.WebcamResolution;
// import com.github.sarxos.webcam.WebcamDiscoveryService;


/**
* PatientPrescriptionForm: Frame to see patient complaint and prescription
* @author Sushanto Halder
*/
public class PatientPrescriptionForm
{
	private JFrame patientPrescriptionFormFrame;
	private JLabel form_label, picture, reg_no, status, date, name, sdw_of, occupation, ph_no, address, age, year, gender,bloodGroupLabel, 
	height, cm, bmi, bp, weight, kg, pulse, spO2,percent, temperature, celcius, family_history, medical_history, prev_diagnosis, complaint_of,
	on_examination, advice, medication, diagnostic_test, provisional_diagnosis, referral, final_diagnosis, kiosk_coordinator,
	kiosk_coordinator_name, kiosk_coordinator_date,doctorLabel, doctor_name,doctor_date;
	
	private JTextField reg_no_field, status_field, date_field, name_field, sdw_of_field, occupation_field, ph_no_field, age_field,
	 gender_field,bloodGroupField, height_field, bmi_field, bp_field1,bp_field2, weight_field, pulse_field, spO2_field, temperature_field,
	  prev_diagnosis_field,final_diagnosis_field,kiosk_coordinator_name_field, kiosk_coordinator_date_field, doctor_name_field,
	  doctor_date_field;
	
	private JTextArea address_area,  family_history_area, medical_history_area, complaint_of_area,  on_examination_area, advice_area,
	 medication_area, diagnostic_test_area, provisional_diagnosis_area, final_diagnosis_area, referral_area,  kiosk_coordinator_area,
	  doctor_area;
	
	private JScrollPane address_pane,family_history_pane, medical_history_pane, complaint_of_pane,  on_examination_pane, advice_pane,
	 medication_pane, diagnostic_test_pane, provisional_diagnosis_pane, final_diagnosis_pane, referral_pane;

	private JButton refresh_button,pictureDownloadButton,back_button,back2_button,submit_button,next_button,prev_button,prescribeButton,
	prescriptionButton;

	private JPanel BasicDataPanel,HealthInfoPanel,DoctorPrescriptionPanel,KioskCoordinatorPanel,DoctorPanel,ButtonPanel,CommunicationPanel;
	private JLabel medicationTypeLabel,medicationNameLabel,medicationDoseLabel,medicationDurationLabel,additionalReportsLabel,prevDateLabel;
	private JComboBox<String> prevDateComboBox,additionalReportsComboBox,medicationTypeComboBox,medicationNameComboBox,medicationDoseComboBox,
	medicationInstructionComboBox1,medicationInstructionComboBox2,medicationTimeComboBox1,medicationTimeComboBox2,medicationDurationComboBox1,
	medicationDurationComboBox2;

	private JTextField medicationTimeField,medicationInstructionField;

	private JButton prevDateButton,medicationTimeButton,medicationInstructionButton,medicationSelectButton,medicationResetButton,
	additionalReportsButton;

	private JRadioButton medicationTimeRadioButton,medicationInstructionRadioButton;
	private ButtonGroup medicationButtonGroup;
	private PatientReport patientReport;
	private Doctor doctor;
	private Font font;
	private int current_report_count = -1;
	private boolean PatientBasicDataEditMode = false;
	private int imageSet = 0;
	private String imgstr,kioskNumber;
	private String confirmMessage,networkErrorMessage,newComplaintErrorMessage,imageString = null;
	private ArrayList<File> selectedFiles = new ArrayList<File>();
	private final DoctorClient connection;

    // Webcam webcam;
    // WebcamPanel webcamPanel;
    // Socket socket;
    // BufferedReader bin;
    // PrintWriter pwriter;
    // RecieveImage ri;
    // SendImage si;
    // JLabel videocam_label;
    // JButton callend_button;

	/**
	* Sets text of all labels and buttons
	*/
	private void setLanguage()
	{
	    reg_no.setText("Reg. no.:");
	    status.setText("Status:");
	    date.setText("Registration Date :");
	    name.setText("Name :");
	    sdw_of.setText("S/D/W of :");
	    occupation.setText("Occup :");
	    ph_no.setText("Phone :");
	    address.setText("Address :");
	    age.setText("Age :");
	    year.setText("years");
	    gender.setText("Gender :");
	    bloodGroupLabel.setText("Blood :");
	    height.setText("Height :");
	    cm.setText("cm");
	    bmi.setText("BMI:");
	    bp.setText("BP:");
	    weight.setText("Weight:");
	    kg.setText("kg");
	    pulse.setText("Pulse :");
	    spO2.setText("spO2:");
	    percent.setText("%");
	    temperature.setText("Temp :");
	    celcius.setText("C");

	    family_history.setText("<html>Family<br>history&nbsp&nbsp&nbsp:</html>");
	    medical_history.setText("<html>Medical<br>history&nbsp&nbsp&nbsp:</html>");
	    prev_diagnosis.setText("Did patient bring any previous prescription?");
	    complaint_of.setText("C/O :");
	    on_examination.setText("On Examintion :");
	    additionalReportsLabel.setText("<html>Additional<br>Reports&nbsp&nbsp&nbsp:</html>");
	    advice.setText("Advice :");
	    medication.setText("Medication :");
	    diagnostic_test.setText("Diagnostic Test :");
	    provisional_diagnosis.setText("Provisional Diagnosis :");
	    referral.setText("Referral :");
	    final_diagnosis.setText("Final Diagnosis :");
	    kiosk_coordinator.setText("Health Asistant :");
	    kiosk_coordinator_name.setText("Name :");
	    kiosk_coordinator_date.setText("Date :");
	    doctorLabel.setText("Doctor :");
	    doctor_name.setText("Name :");
	    doctor_date.setText("Date :");

	    medicationTypeLabel.setText("Type :");
	    medicationNameLabel.setText("Name :");
	    medicationDoseLabel.setText("Dose :");
	    medicationDurationLabel.setText("Duration :");

	    prevDateLabel.setText("Previous prescription : ");
	    prevDateButton.setText("Select");

	    medicationTimeRadioButton.setText("Timing :");
	    medicationInstructionRadioButton.setText("Instruction:");

	    medicationTimeButton.setText("Add");
	    medicationInstructionButton.setText("Add");
	    medicationSelectButton.setText("Select");
	    medicationResetButton.setText("Reset");




	//set font
	    reg_no.setFont(Constants.SMALLLABELFONT);
	    status.setFont(Constants.SMALLLABELFONT);
	    date.setFont(Constants.SMALLLABELFONT);
	    name.setFont(Constants.SMALLLABELFONT);
	    sdw_of.setFont(Constants.SMALLLABELFONT);
	    occupation.setFont(Constants.SMALLLABELFONT);
	    ph_no.setFont(Constants.SMALLLABELFONT);
	    address.setFont(Constants.SMALLLABELFONT);
	    age.setFont(Constants.SMALLLABELFONT);
	    year.setFont(Constants.SMALLLABELFONT);
	    gender.setFont(Constants.SMALLLABELFONT);
	    bloodGroupLabel.setFont(Constants.SMALLLABELFONT);
	    height.setFont(Constants.SMALLLABELFONT);
	    cm.setFont(Constants.SMALLLABELFONT);
	    bmi.setFont(Constants.SMALLLABELFONT);
	    bp.setFont(Constants.SMALLLABELFONT);
	    weight.setFont(Constants.SMALLLABELFONT);
	    kg.setFont(Constants.SMALLLABELFONT);
	    pulse.setFont(Constants.SMALLLABELFONT);
	    spO2.setFont(Constants.SMALLLABELFONT);
	    percent.setFont(Constants.SMALLLABELFONT);
	    temperature.setFont(Constants.SMALLLABELFONT);
	    celcius.setFont(Constants.SMALLLABELFONT);

	    family_history.setFont(Constants.SMALLLABELFONT);
	    medical_history.setFont(Constants.SMALLLABELFONT);
	    prev_diagnosis.setFont(Constants.SMALLLABELFONT);
	    complaint_of.setFont(Constants.SMALLLABELFONT);
	    on_examination.setFont(Constants.SMALLLABELFONT);
	    additionalReportsLabel.setFont(Constants.SMALLLABELFONT);
	    advice.setFont(Constants.SMALLLABELFONT);
	    medication.setFont(Constants.SMALLLABELFONT);
	    diagnostic_test.setFont(Constants.SMALLLABELFONT);
	    provisional_diagnosis.setFont(Constants.SMALLLABELFONT);
	    referral.setFont(Constants.SMALLLABELFONT);
	    final_diagnosis.setFont(Constants.SMALLLABELFONT);
	    kiosk_coordinator.setFont(Constants.SMALLLABELFONT);
	    kiosk_coordinator_name.setFont(Constants.SMALLLABELFONT);
	    kiosk_coordinator_date.setFont(Constants.SMALLLABELFONT);
	    doctorLabel.setFont(Constants.SMALLLABELFONT);
	    doctor_name.setFont(Constants.SMALLLABELFONT);
	    doctor_date.setFont(Constants.SMALLLABELFONT);


	    medicationTypeLabel.setFont(Constants.SMALLLABELFONT);
	    medicationNameLabel.setFont(Constants.SMALLLABELFONT);
	    medicationDoseLabel.setFont(Constants.SMALLLABELFONT);
	    medicationDurationLabel.setFont(Constants.SMALLLABELFONT);

	    medicationTimeRadioButton.setFont(Constants.SMALLLABELFONT);
	    medicationInstructionRadioButton.setFont(Constants.SMALLLABELFONT);

	    medicationTimeButton.setFont(Constants.SMALLBUTTONFONT);
	    medicationInstructionButton.setFont(Constants.SMALLBUTTONFONT);
	    medicationSelectButton.setFont(Constants.SMALLBUTTONFONT);
	    medicationResetButton.setFont(Constants.SMALLBUTTONFONT);

	    prevDateLabel.setFont(Constants.SMALLBUTTONFONT);
	    prevDateButton.setFont(Constants.SMALLBUTTONFONT);

        back_button.setText("Back");
        refresh_button.setText("Refresh");
        pictureDownloadButton.setText("Download");
        back2_button.setText("Cancel");
        submit_button.setText("Submit");
        next_button.setText("Next");
        prev_button.setText("Prev");
        prescribeButton.setText("Prescribe");
        prescriptionButton.setText("View In Prescription Format");
        additionalReportsButton.setText("View");


        back_button.setFont(Constants.SMALLBUTTONFONT);
        refresh_button.setFont(Constants.SMALLBUTTONFONT);
        pictureDownloadButton.setFont(Constants.SMALLBUTTONFONT);
        back2_button.setFont(Constants.SMALLBUTTONFONT);
        submit_button.setFont(Constants.SMALLBUTTONFONT);
        next_button.setFont(Constants.SMALLBUTTONFONT);
        prev_button.setFont(Constants.SMALLBUTTONFONT);
        prescribeButton.setFont(Constants.SMALLBUTTONFONT);
        prescriptionButton.setFont(Constants.SMALLBUTTONFONT);
        additionalReportsButton.setFont(Constants.SMALLBUTTONFONT);


		confirmMessage = "Are you sure?";
		networkErrorMessage = "DoctorClient error! Try again later!";
		newComplaintErrorMessage = "Previous Complaint has not solved yet!";
	}

	/**
	* Creates the GUI
	* @param myCon DoctorClient object, used for file transfer between server and client
	* @param doc Doctor object, contains information of logged in doctor
	* @param pr PatientReport object, contains all information of patient
	* @param kioskNum Kiosk id from which patient belongs
	*/
	protected PatientPrescriptionForm(DoctorClient myCon,Doctor doc,PatientReport pr,String kioskNum)
	{
		patientPrescriptionFormFrame = new JFrame();
		connection = myCon;
		patientReport = pr;
		doctor = doc;
		final JFrame jframe = patientPrescriptionFormFrame;

		kioskNumber = kioskNum;
		font = new Font("Century Schoolbook L", Font.BOLD, 14);

        patientPrescriptionFormFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		patientPrescriptionFormFrame.setVisible(true);
		patientPrescriptionFormFrame.setLayout(null);
		
		patientPrescriptionFormFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		patientPrescriptionFormFrame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{
				if(JOptionPane.showConfirmDialog(jframe,confirmMessage) == JOptionPane.OK_OPTION)
				{
					for(File tempFile: new File(Constants.dataFolder).listFiles())
						tempFile.delete();
					patientPrescriptionFormFrame.dispose();
				}
			}
		});
		
		patientPrescriptionFormFrame.setTitle("PATIENT PRESCRIPTION FORM");
		patientPrescriptionFormFrame.setBackground(UIManager.getColor("Button.focus"));
		
		
		form_label = new JLabel("PATIENT PRESCRIPTION FORM");
		form_label.setFont(Constants.HEADERFONT);
		form_label.setForeground(Constants.HEADERCOLOR1);

		/*
		* Initialize all Labels and Buttons etc.
		*/
        picture = new JLabel();
        reg_no = new JLabel();
        status = new JLabel();
        date = new JLabel();
        name = new JLabel();
        sdw_of = new JLabel();
        occupation = new JLabel();
        ph_no = new JLabel();
        address = new JLabel();
        age = new JLabel();
        year = new JLabel();
        gender = new JLabel();
        bloodGroupLabel = new JLabel();
        height = new JLabel();
        cm = new JLabel();
        bmi = new JLabel();
        bp = new JLabel();
        weight = new JLabel();
        kg = new JLabel();
        pulse = new JLabel();
        spO2 = new JLabel();
        percent = new JLabel();
        temperature = new JLabel();
        celcius = new JLabel();

        family_history = new JLabel();
        medical_history = new JLabel();
        prev_diagnosis = new JLabel();
        complaint_of = new JLabel();
        on_examination = new JLabel();
        additionalReportsLabel =  new JLabel();
        advice = new JLabel();
        medication = new JLabel();
        diagnostic_test = new JLabel();
        provisional_diagnosis = new JLabel();
        referral = new JLabel();
        final_diagnosis = new JLabel();
        kiosk_coordinator = new JLabel();
        kiosk_coordinator_name = new JLabel();
        kiosk_coordinator_date = new JLabel();
        doctorLabel = new JLabel();
        doctor_name = new JLabel();
        doctor_date = new JLabel();

		reg_no_field = new JTextField();
		status_field = new JTextField();
		date_field = new JTextField();
		name_field = new JTextField();
		sdw_of_field = new JTextField();
		occupation_field = new JTextField();
		ph_no_field = new JTextField();
		age_field = new JTextField();
		gender_field = new JTextField();
		bloodGroupField = new JTextField();
		height_field = new JTextField();
		bmi_field = new JTextField();
		bp_field1 = new JTextField();
		bp_field2 = new JTextField();
		weight_field = new JTextField();
		pulse_field = new JTextField();
		spO2_field = new JTextField();
		temperature_field = new JTextField();
		prev_diagnosis_field = new JTextField();
		final_diagnosis_field = new JTextField();
        kiosk_coordinator_name_field = new JTextField();
        kiosk_coordinator_date_field = new JTextField();
        doctor_name_field = new JTextField();
        doctor_date_field = new JTextField();

        back_button = new JButton();
        refresh_button = new JButton();
        pictureDownloadButton = new JButton();
        back2_button = new JButton();
        submit_button = new JButton();
        next_button = new JButton();
        prev_button = new JButton();
        prescribeButton = new JButton();
        prescriptionButton = new JButton();
        additionalReportsButton = new JButton();

        medicationTypeLabel  =  new JLabel();
	    medicationNameLabel  =  new JLabel();
	    medicationDoseLabel  =  new JLabel();
	    medicationDurationLabel  =  new JLabel();

	    medicationTimeRadioButton  =  new JRadioButton();
	    medicationInstructionRadioButton  =  new JRadioButton();

	    medicationTimeButton  =  new JButton();
	    medicationInstructionButton  =  new JButton();
	    medicationSelectButton  =  new JButton();
	    medicationResetButton  =  new JButton();

	    prevDateLabel = new JLabel();
	    prevDateComboBox = new JComboBox<String>();
	    prevDateButton = new JButton();

	    medicationTimeField  =  new JTextField();
	    medicationInstructionField  =  new JTextField();

	    medicationButtonGroup  =  new ButtonGroup();

	    String []medicationTypeList = {"Tablet","Syrup","Injection","Capsule"};
	    String []medicationNameList = getMedicineNameList();
	    String []medicationTimeList1 = {"1.00","2.00","3.00","4.00","5.00","6.00","7.00","8.00","9.00","10.00","11.00","12.00"};
	    String []medicationTimeList2 = {"AM","PM"};
	    String []medicationInstructionList1 = {"After","Before"};
	    String []medicationInstructionList2 = {"Food","Breakfast","Lunch","Dinner"};
	    String []medicationDoseList = {"1","2","3","4","5"};
	    String []medicationDurationList1 = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
	    String []medicationDurationList2 = {"Day(s)","Week(s)","Month(s)"};
	    medicationTypeComboBox = new JComboBox<String>(medicationTypeList);
	    medicationNameComboBox = new JComboBox<String>(medicationNameList);
	    medicationTimeComboBox1 = new JComboBox<String>(medicationTimeList1);
	    medicationTimeComboBox2 = new JComboBox<String>(medicationTimeList2);
	    medicationInstructionComboBox1 = new JComboBox<String>(medicationInstructionList1);
	    medicationInstructionComboBox2 = new JComboBox<String>(medicationInstructionList2);
	    medicationDoseComboBox = new JComboBox<String>(medicationDoseList);
	    medicationDurationComboBox1 = new JComboBox<String>(medicationDurationList1);
	    medicationDurationComboBox2 = new JComboBox<String>(medicationDurationList2);
	    additionalReportsComboBox = new JComboBox<String>();
		
		address_area = new JTextArea();
		family_history_area = new JTextArea();
		medical_history_area = new JTextArea();
		complaint_of_area = new JTextArea();
		on_examination_area = new JTextArea();
		advice_area = new JTextArea();
		medication_area = new JTextArea();
		diagnostic_test_area = new JTextArea();
		provisional_diagnosis_area = new JTextArea();
		final_diagnosis_area = new JTextArea();
		referral_area = new JTextArea();
		kiosk_coordinator_area = new JTextArea();
		doctor_area = new JTextArea();

	    /*
	    * Set auto complete mode for medicine list
	    */
        AutoCompleteDecorator.decorate(medicationNameComboBox);


        setLanguage();

        /*
        * Set editable
        */
		reg_no_field.setEditable(false);
		status_field.setEditable(false); 
		date_field.setEditable(false);
		name_field.setEditable(false);
		sdw_of_field.setEditable(false);
		occupation_field.setEditable(false);
		ph_no_field.setEditable(false);
		age_field.setEditable(false);
		gender_field.setEditable(false);
		bloodGroupField.setEditable(false);
		height_field.setEditable(false);

		bmi_field.setEditable(false);
		bp_field1.setEditable(false);
		bp_field2.setEditable(false);
		weight_field.setEditable(false);
		pulse_field.setEditable(false);
		spO2_field.setEditable(false);
		temperature_field.setEditable(false);
		prev_diagnosis_field.setEditable(false);

        kiosk_coordinator_name_field.setEditable(false);
        kiosk_coordinator_date_field.setEditable(false);
        doctor_name_field.setEditable(false);
		doctor_date_field.setEditable(false);

		address_area.setEditable(false);
		family_history_area.setEditable(false);
		medical_history_area.setEditable(false);
		complaint_of_area.setEditable(false);
		on_examination_area.setEditable(false);
		kiosk_coordinator_area.setEditable(false);
		doctor_area.setEditable(false);


		back2_button.setVisible(false);
		submit_button.setVisible(false);
		prescriptionButton.setVisible(false);


		/*
		* Set border
		*/
		bmi_field.setBorder(BorderFactory.createLineBorder(Color.black));
		bp_field1.setBorder(BorderFactory.createLineBorder(Color.black));
		bp_field2.setBorder(BorderFactory.createLineBorder(Color.black));
		pulse_field.setBorder(BorderFactory.createLineBorder(Color.black));
		temperature_field.setBorder(BorderFactory.createLineBorder(Color.black));
		spO2_field.setBorder(BorderFactory.createLineBorder(Color.black));
		prev_diagnosis_field.setBorder(BorderFactory.createLineBorder(Color.black));
		weight_field.setBorder(BorderFactory.createLineBorder(Color.black));

		complaint_of_area.setBorder(BorderFactory.createLineBorder(Color.black));
		on_examination_area.setBorder(BorderFactory.createLineBorder(Color.black));

		picture.setBorder(BorderFactory.createLineBorder(Color.black));

		/*
		* Set linewrap
		*/
		address_area.setLineWrap(true);
		provisional_diagnosis_area.setLineWrap(true);
		final_diagnosis_area.setLineWrap(true);
		advice_area.setLineWrap(true);
		family_history_area.setLineWrap(true);
		medical_history_area.setLineWrap(true);
		diagnostic_test_area.setLineWrap(true);
		referral_area.setLineWrap(true);
		complaint_of_area.setLineWrap(true);
		on_examination_area.setLineWrap(true);

		
		/*
		* Set position of all labels, buttons and etc
		*/		
		form_label.setBounds(300, 10, 1000, 40);
		picture.setBounds(10,60,140,140);
		reg_no.setBounds(160,60,65,20);
		reg_no_field.setBounds(220,60,108,27);
		status.setBounds(330,60,60,20);
		status_field.setBounds(380,60,60,27);
		date.setBounds(450,60,140,20);
		date_field.setBounds(580,60,90,27);
		name.setBounds(160,90,60,20);
		name_field.setBounds(220,90,200,27);
		sdw_of.setBounds(440,90,80,20);
		sdw_of_field.setBounds(520,90,270,27);
		occupation.setBounds(160,120,60,20);
		occupation_field.setBounds(220,120,150,27);
		ph_no.setBounds(390,120,65,20);
		ph_no_field.setBounds(450,120,100,27);
		address.setBounds(570,120,70,20);
		address_area.setBounds(640,120,150,80);
		age.setBounds(160,150,150,20);
		age_field.setBounds(220,150,60,27);
		year.setBounds(280,150,60,20);
		gender.setBounds(390,150,60,20);
		gender_field.setBounds(450,150,60,27);
		bloodGroupLabel.setBounds(390,180,60,20);
		bloodGroupField.setBounds(450,180,60,27);
		height.setBounds(160,180,60,20);
		height_field.setBounds(220,180,60,27);
		cm.setBounds(280,180,30,20);

		family_history.setBounds(10,210,80,30);
		family_history_area.setBounds(100,210,300,90);
		medical_history.setBounds(410,210,80,30);
		medical_history_area.setBounds(490,210,300,90);
		prev_diagnosis.setBounds(10,310,400,20);
		prev_diagnosis_field.setBounds(330,310,300,20);

		weight.setBounds(10,340,60,20);
		weight_field.setBounds(70,340,45,20);
		kg.setBounds(115,340,30,20);
		bmi.setBounds(160,340,35,20);
		bmi_field.setBounds(190,340,40,20);
		bp.setBounds(260,340,30,20);
		bp_field1.setBounds(290,340,40,20);
		bp_field2.setBounds(335,340,40,20);
		pulse.setBounds(390,340,60,20);
		pulse_field.setBounds(450,340,30,20);
		temperature.setBounds(530,340,60,20);
		temperature_field.setBounds(580,340,40,20);
		celcius.setBounds(630,340,10,20);
		spO2.setBounds(680,340,50,20);
		spO2_field.setBounds(720,340,40,20);
		percent.setBounds(770,340,20,20);

		complaint_of.setBounds(10,370,45,20);
		complaint_of_area.setBounds(50,370,310,165);
		on_examination.setBounds(380,370,120,20);
		on_examination_area.setBounds(500,370,290,130);
		additionalReportsLabel.setBounds(380,490,120,40);
		additionalReportsComboBox.setBounds(500,510,200,25);
		additionalReportsButton.setBounds(710,510,80,25);

		provisional_diagnosis.setBounds(835,60,170,20);
		provisional_diagnosis_area.setBounds(830,80,250,95);
		final_diagnosis.setBounds(1105,60,170,20);
		final_diagnosis_area.setBounds(1100,80,250,95);
		advice.setBounds(835,180,70,20);
		advice_area.setBounds(830,200,250,145);
		diagnostic_test.setBounds(1105,180,150,20);
		diagnostic_test_area.setBounds(1100,200,250,60);
		referral.setBounds(1105,265,150,20);
		referral_area.setBounds(1100,285,250,60);
		medication.setBounds(835,350,100,20);
		medication_area.setBounds(830,370,520,120);

		medicationTypeLabel.setBounds(835,495,100,20);
		medicationTypeComboBox.setBounds(830,515,100,25);
	    medicationNameLabel.setBounds(945,495,100,20);
	    medicationNameComboBox.setBounds(940,515,410,25);
	    medicationTimeRadioButton.setBounds(835,545,100,20);
	    medicationTimeComboBox1.setBounds(940,545,100,25);
	    medicationTimeComboBox2.setBounds(1045,545,100,25);
	    medicationTimeButton.setBounds(1150,545,60,25);
	    medicationTimeField.setBounds(1215,545,135,25);
	    medicationInstructionRadioButton.setBounds(835,575,120,20);
	    medicationInstructionComboBox1.setBounds(940,575,100,25);
	    medicationInstructionComboBox2.setBounds(1045,575,100,25);
	    medicationInstructionButton.setBounds(1150,575,60,25);
	    medicationInstructionField.setBounds(1215,575,135,25);
	    medicationDoseLabel.setBounds(835,600,100,20);
	    medicationDoseComboBox.setBounds(830,620,100,25);
	    medicationDurationLabel.setBounds(945,600,100,25);
	    medicationDurationComboBox1.setBounds(940,620,100,25);
	    medicationDurationComboBox2.setBounds(1045,620,100,25);
	    medicationSelectButton.setBounds(1150,620,100,25);
	    medicationResetButton.setBounds(1255,620,95,25);


		kiosk_coordinator.setBounds(10,550,360,20);
		kiosk_coordinator_name.setBounds(10,570,50,20);
		kiosk_coordinator_name_field.setBounds(60,570,200,23);
		kiosk_coordinator_date.setBounds(10,670,50,20);
		kiosk_coordinator_date_field.setBounds(60,670,100,23);
		doctorLabel.setBounds(390,550,130,20);
		doctor_name.setBounds(390,570,50,20);
		doctor_name_field.setBounds(450,570,200,23);
		doctor_date.setBounds(390,670,50,20);
		doctor_date_field.setBounds(450,670,100,23);
		
		back_button.setBounds(10,15,80,25);
		pictureDownloadButton.setBounds(12,170,135,25);
		refresh_button.setBounds(100,15,100,25);
		back2_button.setBounds(10,15,80,25);
		next_button.setBounds(690,710,100,25);
		prev_button.setBounds(10,710,100,25);
		prescribeButton.setBounds(1200,15,150,25);
		submit_button.setBounds(1200,15,150,25);
		prescriptionButton.setBounds(240,710,250,25);

		prevDateLabel.setBounds(835,710,170,25);
		prevDateComboBox.setBounds(1005,710,150,25);
		prevDateButton.setBounds(1155,710,100,25);

		medicationTypeLabel.setForeground(Color.WHITE);
		medicationNameLabel.setForeground(Color.WHITE);
		medicationTimeRadioButton.setForeground(Color.WHITE);
		medicationInstructionRadioButton.setForeground(Color.WHITE);
		medicationDoseLabel.setForeground(Color.WHITE);
		medicationDurationLabel.setForeground(Color.WHITE);


		/*
		* Add action listeners
		*/
		back_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				/*
				* Delete temporary files if there is any
				*/
				for(File tempFile: new File(Constants.dataFolder).listFiles())
					tempFile.delete();
				new PatientSelect(connection,doctor);
				patientPrescriptionFormFrame.dispose();
			}
		});

		refresh_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				getPatientReport(patientReport.getPatientBasicData().getId());
				setPatientReport();
			}
		});

		pictureDownloadButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				String imageFileName = patientReport.getPatientBasicData().getImage();
				System.out.println("imageFileName : " + imageFileName);
				int response = 0;
				try
				{
					response = connection.getRequest(imageFileName , Constants.dataFolder + imageFileName);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				File imageFile = new File(Constants.dataFolder + imageFileName);
				if(response >= 0)
				{
					/*
					* Scale and set image
					*/
					ImageIcon imageIcon = new ImageIcon(Constants.dataFolder + imageFileName); // load the image to a imageIcon
					int h = picture.getHeight();
					int w = picture.getWidth();

					Image image = imageIcon.getImage(); // transform it 
					Image newimg = image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
					imageIcon = new ImageIcon(newimg);
					picture.setIcon(imageIcon);
					imageFile.delete();
					pictureDownloadButton.setVisible(false);
				}
				else
				{
					if(imageFile.isFile())
						imageFile.delete();
					pictureDownloadButton.setVisible(false);
					JOptionPane.showMessageDialog(patientPrescriptionFormFrame,"No picture found!");
				}
			}
		});

		back2_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				/*
				* Back from prescribe mode to normal mode
				* Unlock patient file
				*/
				setReport(current_report_count);
				setDoctorPrescriptionEditable(false);

				prev_button.setVisible(true);
				next_button.setVisible(true);
				prevDateLabel.setVisible(true);
				prevDateComboBox.setVisible(true);
				prevDateButton.setVisible(true);
				prescribeButton.setVisible(true);
				back_button.setVisible(true);
				refresh_button.setVisible(true);
				back2_button.setVisible(false);
				submit_button.setVisible(false);

				int unlockResponse = 0;
				try
				{
					unlockResponse = connection.unlockRequest(reg_no_field.getText() + ".xml");
				}
				catch(Exception e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(jframe,RHErrors.getErrorDescription(unlockResponse));
					try
					{
						connection.logoutRequest();
					}
					catch(Exception ex)
					{
						e.printStackTrace();
					}
					new DoctorLogin();
					patientPrescriptionFormFrame.dispose();
				}
			}
		});

		additionalReportsButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				/*
				* Get file name from combobox
				* Download the file
				* If file exists, then
				* 	Open in default program
				* else show error
				* end if
				*/
				String fileName = (String)additionalReportsComboBox.getSelectedItem();
				File file = new File(Constants.dataFolder + fileName);
				if(!file.isFile())
				{
					int response = 0;
					try
					{
						response = connection.getRequest(fileName,Constants.dataFolder + fileName);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					if(response >= 0)
					{
						try
						{
							Desktop.getDesktop().open(file);
						}
						catch(IOException ioe)
						{
							ioe.printStackTrace();
						}
					}
					else if(response == -2)
						JOptionPane.showMessageDialog(jframe,"File does not exist");
					else
					{
						JOptionPane.showMessageDialog(jframe,RHErrors.getErrorDescription(response));
						// try
						// {
						// 	connection.logoutRequest();
						// }
						// catch(Exception e)
						// {
						// 	e.printStackTrace();
						// }
						// for(File tempFile: new File(Constants.dataFolder).listFiles())
						// 	tempFile.delete();
						// new DoctorLogin();
						// patientPrescriptionFormFrame.dispose();
					}
				}
				else
				{
					try
					{
						Desktop.getDesktop().open(file);
					}
					catch(IOException ioe)
					{
						ioe.printStackTrace();
					}
				}
			}
		});

		prescribeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				/*
				* Lock patient file
				* Download updated patient file
				* Set prescribe mode
				* If already locked, show message
				*/
				int lockResponse = 0;
				try
				{
					lockResponse = connection.lockRequest(reg_no_field.getText() + ".xml");
				}
				catch(Exception e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(jframe,"Error");
					return;
				}
				if(lockResponse >= 0)
				{
					getPatientReport(reg_no_field.getText());
					setPatientReport();
					if(patientReport.getReports().get(patientReport.getReports().size()-1).getDoctorPrescription().getDoctorName() == null)
					{
						current_report_count = patientReport.getReports().size()-1;
						next_button.setEnabled(false);
						if(current_report_count == 0)
						{
							prev_button.setEnabled(false);
						}
						setReport(current_report_count);

						setDoctorPrescriptionEditable(true);
						provisional_diagnosis_area.setText("");
						final_diagnosis_area.setText("");
						advice_area.setText("");
						medication_area.setText("");
						diagnostic_test_area.setText("");
						referral_area.setText("");

						doctor_name_field.setText(doctor.getDoctorName());
						SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
						doctor_date_field.setText(date.format(new Date()));

						back_button.setVisible(false);
						refresh_button.setVisible(false);

						back2_button.setVisible(true);
						prev_button.setVisible(false);
						next_button.setVisible(false);
						prevDateLabel.setVisible(false);
						prevDateComboBox.setVisible(false);
						prevDateButton.setVisible(false);
						submit_button.setVisible(true);
						prescribeButton.setVisible(false);
						prescriptionButton.setVisible(false);
					}
					else
					{
						JOptionPane.showMessageDialog(jframe,"Patient is already prescribed");
						int unlockResponse = 0;
						try
						{
							unlockResponse = connection.unlockRequest(reg_no_field.getText() + ".xml");
						}
						catch(Exception e)
						{
							e.printStackTrace();
							JOptionPane.showMessageDialog(jframe,RHErrors.getErrorDescription(unlockResponse));
							try
							{
								connection.logoutRequest();
							}
							catch(Exception ex)
							{
								e.printStackTrace();
							}
							new DoctorLogin();
							patientPrescriptionFormFrame.dispose();
						}
					}
				}
				else JOptionPane.showMessageDialog(jframe,RHErrors.getErrorDescription(lockResponse));
			}
		});

		next_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				/*
				* Display next report
				*/
				if(current_report_count<patientReport.getReports().size()-1)
				{
					setReport(++current_report_count);
					if(current_report_count == patientReport.getReports().size()-1)
						next_button.setEnabled(false);
					if(current_report_count>0)
						prev_button.setEnabled(true);
				}
			}
		});

		prev_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				/*
				* Display previous report
				*/
				if(current_report_count>0)
				{
					setReport(--current_report_count);
					if(current_report_count == 0)
						prev_button.setEnabled(false);
					if(current_report_count<patientReport.getReports().size()-1)
						next_button.setEnabled(true);
				}
			}
		});

		prescriptionButton.addActionListener(new ActionListener()
		{
			/*
			* Display currently open report in general prescription format
			*/
			public void actionPerformed(ActionEvent ae)
			{
				prescriptionButton.setEnabled(false);
				PrescriptionInformation info = new PrescriptionInformation();
				info.date = doctor_date_field.getText();
				info.doctor_name = doctor_name_field.getText();
				try
				{
					info.patient_image=((ImageIcon)picture.getIcon()).getImage();
				}
				catch(NullPointerException npe)
				{
					npe.printStackTrace();
				}
				// info.patient_image = picture.getIcon();
				// info.doctor_degree = 
				// info.doctor_hospital = 
				info.patient_regno = reg_no_field.getText();
				info.patient_name = name_field.getText();
				info.patient_sdw = sdw_of_field.getText();
				info.patient_age = age_field.getText();
				info.patient_gender = gender_field.getText();
				info.patient_address = address_area.getText();
				info.family_history = family_history_area.getText();
				info.medical_history = medical_history_area.getText();
				info.patient_phone = ph_no_field.getText();
				info.complaint = complaint_of_area.getText();
				info.on_examination = on_examination_area.getText();
				info.provisional_diagnosis = provisional_diagnosis_area.getText();
				info.final_diagnosis = final_diagnosis_area.getText();
				info.doctor_advice = advice_area.getText();
				info.doctor_medication = medication_area.getText();
				info.doctor_referal = referral_area.getText();
				info.doctor_diagnostic = diagnostic_test_area.getText();
				info.kiosk_coordinator_name = kiosk_coordinator_name_field.getText();
				new GeneralPrescription(info);
				prescriptionButton.setEnabled(true);
			}
		});

		submit_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				/*
				* If all fields are blank, then
				* 	show error message
				* else
				*	Update patient log
				*	Update doctor log
				*	Add prescription to patient file
				*	Set normal mode
				* end if
				*/
				if(provisional_diagnosis_area.getText().equals("") && final_diagnosis_area.getText().equals("") 
					&& advice_area.getText().equals("")	&& diagnostic_test_area.getText().equals("") && referral_area.getText().equals("")
					 && medication_area.getText().equals(""))
					JOptionPane.showMessageDialog(jframe,"Patient is not prescribed");
				else if(JOptionPane.showConfirmDialog(jframe,"Are you sure to submit?") == JOptionPane.OK_OPTION)
				{
					if(updatePatientLog() && updateDoctorLog())
					{
						addPrescriptionToReport();
						current_report_count = patientReport.getReports().size()-1;
						next_button.setEnabled(false);
						if(current_report_count == 0)
							prev_button.setEnabled(false);
						else prev_button.setEnabled(true);
						setReport(current_report_count);
						prev_button.setVisible(true);
						next_button.setVisible(true);
						prevDateLabel.setVisible(true);
						prevDateComboBox.setVisible(true);
						prevDateButton.setVisible(true);
						prescribeButton.setVisible(true);
						back_button.setVisible(true);
						refresh_button.setVisible(true);
						back2_button.setVisible(false);
						submit_button.setVisible(false);

						setDoctorPrescriptionEditable(false);
					}
				}
			}
		});

		medicationTimeRadioButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				setMedicationTimeButtonsEnabled(true);
			}
		});

		medicationInstructionRadioButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				setMedicationTimeButtonsEnabled(false);
			}
		});

		medicationTimeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if(medicationTimeField.getText().equals(""))
					medicationTimeField.setText((String)medicationTimeComboBox1.getSelectedItem()
						+(String)medicationTimeComboBox2.getSelectedItem());
				else
					medicationTimeField.setText(medicationTimeField.getText()+"/"+(String)medicationTimeComboBox1.getSelectedItem()
						+(String)medicationTimeComboBox2.getSelectedItem());
			}
		});

		medicationInstructionButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if(medicationInstructionField.getText().equals(""))
					medicationInstructionField.setText((String)medicationInstructionComboBox1.getSelectedItem()
						+" "+(String)medicationInstructionComboBox2.getSelectedItem());
				else
					medicationInstructionField.setText(medicationInstructionField.getText()
						+"/"+(String)medicationInstructionComboBox1.getSelectedItem()+" "
						+(String)medicationInstructionComboBox2.getSelectedItem());
			}
		});

		medicationSelectButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				/*
				* Simple string operations
				*/
				String instruct;
				if(medicationTimeRadioButton.isSelected())
					instruct = medicationTimeField.getText();
				else instruct = medicationInstructionField.getText();

				String medicine = (String)medicationTypeComboBox.getSelectedItem()+": "+(String)medicationNameComboBox.getSelectedItem()+", "
				+(String)medicationDoseComboBox.getSelectedItem()+" time(s) in a day("+instruct+") X "
				+(String)medicationDurationComboBox1.getSelectedItem()+" "+(String)medicationDurationComboBox2.getSelectedItem();

				if(medication_area.getText().equals(""))
					medication_area.setText(medicine);
				else medication_area.setText(medication_area.getText()+"\n"+medicine);

				medicationTimeField.setText("");
				medicationInstructionField.setText("");
			}
		});

		medicationResetButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				medicationTimeField.setText("");
				medicationInstructionField.setText("");
			}
		});

		prevDateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				/*
				* Set report to a selected date
				*/
				current_report_count = prevDateComboBox.getSelectedIndex();
				setReport(current_report_count);
				prev_button.setEnabled(current_report_count != 0);
				next_button.setEnabled(current_report_count != (patientReport.getReports().size() -1));
			}
		});

		
		/*
		* Add buttons and everything to frame
		*/
		patientPrescriptionFormFrame.add(form_label);
		patientPrescriptionFormFrame.add(picture);
		patientPrescriptionFormFrame.add(reg_no);
		patientPrescriptionFormFrame.add(reg_no_field);
		patientPrescriptionFormFrame.add(status);
		patientPrescriptionFormFrame.add(status_field);
		patientPrescriptionFormFrame.add(date);
		patientPrescriptionFormFrame.add(date_field);
		patientPrescriptionFormFrame.add(name);
		patientPrescriptionFormFrame.add(name_field);
		patientPrescriptionFormFrame.add(sdw_of);
		patientPrescriptionFormFrame.add(sdw_of_field);
		patientPrescriptionFormFrame.add(occupation);
		patientPrescriptionFormFrame.add(occupation_field);
		patientPrescriptionFormFrame.add(ph_no);
		patientPrescriptionFormFrame.add(ph_no_field);
		patientPrescriptionFormFrame.add(address);
		patientPrescriptionFormFrame.add(address_area);
		patientPrescriptionFormFrame.add(age);
		patientPrescriptionFormFrame.add(age_field);
		patientPrescriptionFormFrame.add(year);
		patientPrescriptionFormFrame.add(gender);
		patientPrescriptionFormFrame.add(gender_field);
		patientPrescriptionFormFrame.add(bloodGroupLabel);
		patientPrescriptionFormFrame.add(bloodGroupField);
		patientPrescriptionFormFrame.add(height);
		patientPrescriptionFormFrame.add(height_field);
		patientPrescriptionFormFrame.add(cm);
		patientPrescriptionFormFrame.add(weight);
		patientPrescriptionFormFrame.add(weight_field);
		patientPrescriptionFormFrame.add(kg);
		patientPrescriptionFormFrame.add(bmi);
		patientPrescriptionFormFrame.add(bmi_field);
		patientPrescriptionFormFrame.add(bp);
		patientPrescriptionFormFrame.add(bp_field1);
		patientPrescriptionFormFrame.add(bp_field2);
		patientPrescriptionFormFrame.add(spO2);
		patientPrescriptionFormFrame.add(spO2_field);
		patientPrescriptionFormFrame.add(percent);
		patientPrescriptionFormFrame.add(pulse);
		patientPrescriptionFormFrame.add(pulse_field);
		patientPrescriptionFormFrame.add(temperature);
		patientPrescriptionFormFrame.add(temperature_field);
		patientPrescriptionFormFrame.add(celcius);
		patientPrescriptionFormFrame.add(family_history);
		patientPrescriptionFormFrame.add(family_history_area);
		patientPrescriptionFormFrame.add(medical_history);
		patientPrescriptionFormFrame.add(medical_history_area);
		patientPrescriptionFormFrame.add(prev_diagnosis);
		patientPrescriptionFormFrame.add(prev_diagnosis_field);
		patientPrescriptionFormFrame.add(complaint_of);
		patientPrescriptionFormFrame.add(complaint_of_area);
		patientPrescriptionFormFrame.add(on_examination);
		patientPrescriptionFormFrame.add(on_examination_area);
		patientPrescriptionFormFrame.add(additionalReportsLabel);
		patientPrescriptionFormFrame.add(additionalReportsComboBox);
		patientPrescriptionFormFrame.add(additionalReportsButton);
		patientPrescriptionFormFrame.add(provisional_diagnosis);
		patientPrescriptionFormFrame.add(provisional_diagnosis_area);
		patientPrescriptionFormFrame.add(final_diagnosis);
		patientPrescriptionFormFrame.add(final_diagnosis_area);
		patientPrescriptionFormFrame.add(advice);
		patientPrescriptionFormFrame.add(advice_area);
		patientPrescriptionFormFrame.add(medication);
		patientPrescriptionFormFrame.add(medication_area);
		patientPrescriptionFormFrame.add(diagnostic_test);
		patientPrescriptionFormFrame.add(diagnostic_test_area);
		patientPrescriptionFormFrame.add(referral);
		patientPrescriptionFormFrame.add(referral_area);
		patientPrescriptionFormFrame.add(kiosk_coordinator);
		patientPrescriptionFormFrame.add(kiosk_coordinator_name);
		patientPrescriptionFormFrame.add(kiosk_coordinator_name_field);
		patientPrescriptionFormFrame.add(kiosk_coordinator_date);
		patientPrescriptionFormFrame.add(kiosk_coordinator_date_field);
		patientPrescriptionFormFrame.add(doctorLabel);
		patientPrescriptionFormFrame.add(doctor_name);
		patientPrescriptionFormFrame.add(doctor_name_field);
		patientPrescriptionFormFrame.add(doctor_date);
		patientPrescriptionFormFrame.add(doctor_date_field);

		patientPrescriptionFormFrame.add(back_button);
		patientPrescriptionFormFrame.add(refresh_button);
		patientPrescriptionFormFrame.add(back2_button);
		patientPrescriptionFormFrame.add(next_button);
		patientPrescriptionFormFrame.add(prev_button);
		patientPrescriptionFormFrame.add(prescribeButton);
		patientPrescriptionFormFrame.add(submit_button);
		patientPrescriptionFormFrame.add(pictureDownloadButton);
		patientPrescriptionFormFrame.add(prescriptionButton);

		patientPrescriptionFormFrame.add(prevDateLabel);
		patientPrescriptionFormFrame.add(prevDateComboBox);
		patientPrescriptionFormFrame.add(prevDateButton);

		medicationButtonGroup.add(medicationTimeRadioButton);
		medicationButtonGroup.add(medicationInstructionRadioButton);

		patientPrescriptionFormFrame.add(medicationTypeLabel);
		patientPrescriptionFormFrame.add(medicationTypeComboBox);
		patientPrescriptionFormFrame.add(medicationNameLabel);
		patientPrescriptionFormFrame.add(medicationNameComboBox);
		patientPrescriptionFormFrame.add(medicationTimeRadioButton);
		patientPrescriptionFormFrame.add(medicationTimeComboBox1);
		patientPrescriptionFormFrame.add(medicationTimeComboBox2);
		patientPrescriptionFormFrame.add(medicationTimeButton);
		patientPrescriptionFormFrame.add(medicationTimeField);
		patientPrescriptionFormFrame.add(medicationInstructionRadioButton);
		patientPrescriptionFormFrame.add(medicationInstructionComboBox1);
		patientPrescriptionFormFrame.add(medicationInstructionComboBox2);
		patientPrescriptionFormFrame.add(medicationInstructionButton);
		patientPrescriptionFormFrame.add(medicationInstructionField);
		patientPrescriptionFormFrame.add(medicationDoseLabel);
		patientPrescriptionFormFrame.add(medicationDoseComboBox);
		patientPrescriptionFormFrame.add(medicationDurationLabel);
		patientPrescriptionFormFrame.add(medicationDurationComboBox1);
		patientPrescriptionFormFrame.add(medicationDurationComboBox2);
		patientPrescriptionFormFrame.add(medicationSelectButton);
		patientPrescriptionFormFrame.add(medicationResetButton);

		medicationInstructionRadioButton.setSelected(true);

		/*
		* Initialize scroll panes
		*/
		on_examination_pane  =  new JScrollPane(on_examination_area);
		address_pane  =  new JScrollPane(address_area);
		family_history_pane  =  new JScrollPane(family_history_area);
		medical_history_pane  =  new JScrollPane(medical_history_area);
		complaint_of_pane  =  new JScrollPane(complaint_of_area);

		advice_pane  =  new JScrollPane(advice_area);
		medication_pane  =  new JScrollPane(medication_area);
		diagnostic_test_pane  =  new JScrollPane(diagnostic_test_area);
		provisional_diagnosis_pane  =  new JScrollPane(provisional_diagnosis_area);
		final_diagnosis_pane  =  new JScrollPane(final_diagnosis_area);
		referral_pane  =  new JScrollPane(referral_area);


		/*
		* Set positions of scroll panes
		*/
   		address_pane.setBounds(640,120,150,80);
   		family_history_pane.setBounds(100,210,300,90);
   		medical_history_pane.setBounds(490,210,300,90);
   		complaint_of_pane.setBounds(50,370,310,165);
   		on_examination_pane.setBounds(500,370,290,130);

   		provisional_diagnosis_pane.setBounds(830,80,250,95);
   		final_diagnosis_pane.setBounds(1100,80,250,95);
   		advice_pane.setBounds(830,200,250,145);
   		medication_pane.setBounds(830,370,250+270,120);
   		diagnostic_test_pane.setBounds(1100,200,250,60);
   		referral_pane.setBounds(1100,285,250,60);


   		/*
   		* Add scroll panes to corresponding text areas
   		*/
		patientPrescriptionFormFrame.add(on_examination_pane);
		patientPrescriptionFormFrame.add(address_pane);
		patientPrescriptionFormFrame.add(family_history_pane);
		patientPrescriptionFormFrame.add(medical_history_pane);
		patientPrescriptionFormFrame.add(complaint_of_pane);

		patientPrescriptionFormFrame.add(advice_pane);
		patientPrescriptionFormFrame.add(medication_pane);
		patientPrescriptionFormFrame.add(diagnostic_test_pane);
		patientPrescriptionFormFrame.add(provisional_diagnosis_pane);
		patientPrescriptionFormFrame.add(final_diagnosis_pane);
		patientPrescriptionFormFrame.add(referral_pane);

		/*
		* Initialize panels
		*/
		BasicDataPanel = new JPanel();
		HealthInfoPanel = new JPanel();
		DoctorPrescriptionPanel = new JPanel();
		KioskCoordinatorPanel = new JPanel();
		DoctorPanel = new JPanel();
		ButtonPanel = new JPanel();
		CommunicationPanel = new JPanel();

		/*
		* Set positions of panels
		*/
		BasicDataPanel.setBounds(0,55,810,250);
		HealthInfoPanel.setBounds(0,305,810,240);
		DoctorPrescriptionPanel.setBounds(810,55,1190,600);
		KioskCoordinatorPanel.setBounds(0,545,380,155);
		DoctorPanel.setBounds(380,545,430,155);
		ButtonPanel.setBounds(0,700,810,50);
		CommunicationPanel.setBounds(810,655,1190,800);

		/*
		* Set borders of panels
		*/
		BasicDataPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		HealthInfoPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		DoctorPrescriptionPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		KioskCoordinatorPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		DoctorPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		ButtonPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		CommunicationPanel.setBorder(BorderFactory.createRaisedBevelBorder());


		/*
		* Add panels to frame
		*/
		patientPrescriptionFormFrame.add(BasicDataPanel);
		patientPrescriptionFormFrame.add(HealthInfoPanel);
		patientPrescriptionFormFrame.add(DoctorPrescriptionPanel);
		patientPrescriptionFormFrame.add(KioskCoordinatorPanel);
		patientPrescriptionFormFrame.add(DoctorPanel);
		patientPrescriptionFormFrame.add(ButtonPanel);
		patientPrescriptionFormFrame.add(CommunicationPanel);

	//add JPanels
		Constants.JPANEL2.setBounds(0,0,2000,55);
		Constants.JPANEL1.setBounds(0,0,2000,1000);
		patientPrescriptionFormFrame.add(Constants.JPANEL2);
		// add(Constants.JPANEL1);

		Constants.JPANEL2.setBounds(0,0,2000,Constants.PANEL2_HEIGHT);
		Constants.JPANEL1.setBounds(0,0,2000,Constants.SIZE_Y);

		
		setDoctorPrescriptionEditable(false);
		setMedicationTimeButtonsEnabled(false);
		setPatientReport();

	}

	/**
	* Add prescription written by doctor to patient file
	*/
	private void addPrescriptionToReport()
	{
		/*
		* Add prescription to patient file
		* Unlock patient file
		*/
		DoctorPrescription docPrescription = new DoctorPrescription();
		docPrescription.setDoctorName( checkNullString(doctor_name_field.getText()) );
		docPrescription.setProvisionalDiagnosis( checkNullString(provisional_diagnosis_area.getText()) );
		docPrescription.setFinalDiagnosis( checkNullString(final_diagnosis_area.getText()) );
		docPrescription.setAdvice( checkNullString(advice_area.getText()) );
		docPrescription.setMedication( checkNullString(medication_area.getText()) );
		docPrescription.setDiagnosis( checkNullString(diagnostic_test_area.getText()) );
		docPrescription.setReferral( checkNullString(referral_area.getText()) );
		docPrescription.setPrescription_date( checkNullString(doctor_date_field.getText()) );
		docPrescription.setSignature( checkNullString(doctor.getDoctorSignature()));
		docPrescription.setRegistration_number( checkNullString(doctor.getDoctorRegistrationNumber()));

		patientReport.getReports().get(patientReport.getReports().size()-1).setDoctorPrescription(docPrescription);


		String fileName = Constants.dataFolder + "tempPatientReport.xml";
		File file  =  new File(fileName);
		try
		{
			JAXBContext jc = JAXBContext.newInstance(PatientReport.class);
			Marshaller jm = jc.createMarshaller();
			jm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
			jm.marshal(patientReport,file);
		}
		catch(JAXBException jaxbe)
		{
			jaxbe.printStackTrace();
		}
		int response = 0;
		try
		{
			response = connection.putRequest(fileName,reg_no_field.getText()+".xml");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		if(response < 0)
		{
			if(file.isFile())
				file.delete();
			if(response == -2)
				JOptionPane.showMessageDialog(patientPrescriptionFormFrame,"File does not exist");
			else
			{
				JOptionPane.showMessageDialog(patientPrescriptionFormFrame,RHErrors.getErrorDescription(response));
				try
				{
					connection.logoutRequest();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				for(File tempFile: new File(Constants.dataFolder).listFiles())
					tempFile.delete();
				new PatientSelect(connection,doctor);
				patientPrescriptionFormFrame.dispose();
			}
		}
		else
			file.delete();
		int unlockResponse = 0;
		try
		{
			unlockResponse = connection.unlockRequest(reg_no_field.getText() + ".xml");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(patientPrescriptionFormFrame,RHErrors.getErrorDescription(unlockResponse));
			try
			{
				connection.logoutRequest();
			}
			catch(Exception ex)
			{
				e.printStackTrace();
			}
			new DoctorLogin();
			patientPrescriptionFormFrame.dispose();
		}
	}

	/**
	* Enable or disable medication time label, button, textfield
	* @param enable if true, enable, else disable
	*/
	private void setMedicationTimeButtonsEnabled(boolean enable)
	{
		medicationTimeComboBox1.setEnabled(enable);
		medicationTimeComboBox2.setEnabled(enable);
		medicationTimeButton.setEnabled(enable);
		medicationTimeField.setEnabled(enable);

		medicationInstructionComboBox1.setEnabled(!enable);
		medicationInstructionComboBox2.setEnabled(!enable);
		medicationInstructionButton.setEnabled(!enable);
		medicationInstructionField.setEnabled(!enable);
	}

	/**
	* Set prescribe mode
	* @param edit Set editable or non-editable
	*/
	private void setDoctorPrescriptionEditable(boolean edit)
	{
		provisional_diagnosis_area.setEditable(edit);
		final_diagnosis_area.setEditable(edit);
		advice_area.setEditable(edit);
		diagnostic_test_area.setEditable(edit);
		referral_area.setEditable(edit);
		medication_area.setEditable(edit);

		medicationTypeLabel.setVisible(edit);
		medicationTypeComboBox.setVisible(edit);
		medicationNameLabel.setVisible(edit);
		medicationNameComboBox.setVisible(edit);
		medicationTimeRadioButton.setVisible(edit);
		medicationTimeComboBox1.setVisible(edit);
		medicationTimeComboBox2.setVisible(edit);
		medicationTimeButton.setVisible(edit);
		medicationTimeField.setVisible(edit);
		medicationInstructionRadioButton.setVisible(edit);
		medicationInstructionComboBox1.setVisible(edit);
		medicationInstructionComboBox2.setVisible(edit);
		medicationInstructionButton.setVisible(edit);
		medicationInstructionField.setVisible(edit);
		medicationDoseLabel.setVisible(edit);
		medicationDoseComboBox.setVisible(edit);
		medicationDurationLabel.setVisible(edit);
		medicationDurationComboBox1.setVisible(edit);
		medicationDurationComboBox2.setVisible(edit);
		medicationSelectButton.setVisible(edit);
		medicationResetButton.setVisible(edit);

		if(edit)
		{
			DoctorPrescriptionPanel.setBackground(Color.GREEN.darker().darker());
			DoctorPrescriptionPanel.setBounds(810,55,1190,600);
			CommunicationPanel.setBounds(810,655,1190,800);
			provisional_diagnosis.setForeground(Color.WHITE);
			final_diagnosis.setForeground(Color.WHITE);
			advice.setForeground(Color.WHITE);
			diagnostic_test.setForeground(Color.WHITE);
			referral.setForeground(Color.WHITE);
			medication.setForeground(Color.WHITE);
		}
		else
		{
			DoctorPrescriptionPanel.setBackground(Constants.JPANELCOLOR1);
			DoctorPrescriptionPanel.setBounds(810,55,1190,445);
			CommunicationPanel.setBounds(810,500,1190,800);
			provisional_diagnosis.setForeground(Color.BLACK);
			final_diagnosis.setForeground(Color.BLACK);
			advice.setForeground(Color.BLACK);
			diagnostic_test.setForeground(Color.BLACK);
			referral.setForeground(Color.BLACK);
			medication.setForeground(Color.BLACK);
		}
	}

	/**
	* Check if a string is null or without any character
	* @param str String to be checked
	* @return If str is not null then str, else null
	*/
    private String checkNullString(String str)
    {
    	if(str.equals(""))
    		return null;
    	else return str;
    }

    /**
    * Set patient complaint to report count
    * @param reportCount Report no.
    */
	private void setReport(int reportCount)
	{
		if(reportCount != -1)
		{
			Report report = patientReport.getReports().get(reportCount);
			weight_field.setText(report.getPatientComplaint().getWeight());
			bmi_field.setText(report.getPatientComplaint().getBmi());
			if(report.getPatientComplaint().getBp() != null)
			{
				String[] bpParts = report.getPatientComplaint().getBp().split("/");
				bp_field1.setText(bpParts[0]);
				bp_field2.setText(bpParts[1]);
			}
			pulse_field.setText(report.getPatientComplaint().getPulse());
			temperature_field.setText(report.getPatientComplaint().getTemperature());
			spO2_field.setText(report.getPatientComplaint().getSpo2());
			prev_diagnosis_field.setText(report.getPatientComplaint().getPrevDiagnosis());
			on_examination_area.setText(report.getPatientComplaint().getOtherResults());
			complaint_of_area.setText(report.getPatientComplaint().getcomplaint());
			kiosk_coordinator_name_field.setText(report.getPatientComplaint().getKioskCoordinatorName());
			kiosk_coordinator_date_field.setText(report.getPatientComplaint().getcomplaint_date());

			if(report.getPatientComplaint().getFileNames() != null && !report.getPatientComplaint().getFileNames().equals(""))
			{
				DefaultComboBoxModel<String> defaultComboBoxModel 
				= new DefaultComboBoxModel<String>(report.getPatientComplaint().getFileNames().split("\n"));
				additionalReportsComboBox.setModel(defaultComboBoxModel);
				additionalReportsButton.setEnabled(true);
				additionalReportsComboBox.setEnabled(true);
				additionalReportsLabel.setEnabled(true);
			}
			else
			{
				additionalReportsComboBox.removeAllItems();
				additionalReportsButton.setEnabled(false);
				additionalReportsComboBox.setEnabled(false);
				additionalReportsLabel.setEnabled(false);
			}

			if(report.getDoctorPrescription() != null)
			{
				doctor_name_field.setText(report.getDoctorPrescription().getDoctorName());
				doctor_date_field.setText(report.getDoctorPrescription().getPrescription_date());
				provisional_diagnosis_area.setText(report.getDoctorPrescription().getProvisionalDiagnosis());
				final_diagnosis_area.setText(report.getDoctorPrescription().getFinalDiagnosis());
				advice_area.setText(report.getDoctorPrescription().getAdvice());
				medication_area.setText(report.getDoctorPrescription().getMedication());
				diagnostic_test_area.setText(report.getDoctorPrescription().getDiagnosis());
				referral_area.setText(report.getDoctorPrescription().getReferral());
			}
			else
			{
				provisional_diagnosis_area.setText("");
				final_diagnosis_area.setText("");
				advice_area.setText("");
				medication_area.setText("");
				diagnostic_test_area.setText("");
				referral_area.setText("");
				doctor_name_field.setText("");
				doctor_date_field.setText("");
			}
		}
		if(doctor_name_field.getText().equals(""))
		{
			prescriptionButton.setVisible(false);
		}
		else
		{
			prescriptionButton.setVisible(true);
		}
	}

	/**
	* Update patient log file, i.e. remove the patient id from patient log since he/she is prescribed
	* @return True on success else negative error value
	*/
	private boolean updatePatientLog()
	{
		String localFileName = Constants.dataFolder + "log.xml",serverFileName = "Patient_"+kioskNumber+"_Log.xml";
		int lockResponse = 0;
		try
		{
			lockResponse = connection.lockRequest(serverFileName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(patientPrescriptionFormFrame,"Error in locking");
		}
		if(lockResponse >= 0)
		{
			File localFile = new File(localFileName);
			int receiveResponse = 0;
			try
			{
				receiveResponse = connection.getRequest(serverFileName,localFileName);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			if(receiveResponse >= 0)
			{
				try
				{
					JAXBContext jc = JAXBContext.newInstance(PatientLog.class);
					Unmarshaller um = jc.createUnmarshaller();
					PatientLog patientLog = (PatientLog)um.unmarshal(localFile);

					if(patientLog.getNormal().indexOf(reg_no_field.getText()) != -1)
						patientLog.getNormal().remove(reg_no_field.getText());
					else if(patientLog.getEmergency().indexOf(reg_no_field.getText()) != -1)
						patientLog.getEmergency().remove(reg_no_field.getText());

					Marshaller jm = jc.createMarshaller();
					jm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
					jm.marshal(patientLog,localFile);
				}
				catch(JAXBException jaxbe)
				{
					jaxbe.printStackTrace();
				}
				int sendResponse = 0;
				try
				{
					sendResponse = connection.putRequest(localFileName,serverFileName);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				if(sendResponse < 0)
				{
					localFile.delete();
					JOptionPane.showMessageDialog(patientPrescriptionFormFrame,RHErrors.getErrorDescription(sendResponse));
					try
					{
						connection.logoutRequest();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					for(File tempFile: new File(Constants.dataFolder).listFiles())
						tempFile.delete();
					new DoctorLogin();
					patientPrescriptionFormFrame.dispose();
					return false;
				}
				localFile.delete();
				try
				{
					connection.unlockRequest(serverFileName);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				return true;
			}
			else
			{
				if(localFile.isFile())
					localFile.delete();
				if(receiveResponse == -2)
					JOptionPane.showMessageDialog(patientPrescriptionFormFrame,"File does not exists");
				else
					JOptionPane.showMessageDialog(patientPrescriptionFormFrame,RHErrors.getErrorDescription(receiveResponse));
				try
				{
					connection.unlockRequest(serverFileName);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				return false;

			}
		}
		else
		{
			JOptionPane.showMessageDialog(patientPrescriptionFormFrame,RHErrors.getErrorDescription(lockResponse));
			return false;
		}
	}

	/**
	* Update doctor log, i.e. add the patient id to doctor's previously visited patient list.
	* @return True on success else negative error value
	*/
	private boolean updateDoctorLog()
	{
		String localFileName = Constants.dataFolder + "tempDoctor.xml",serverFileName = doctor.getDoctorId()+".xml";
		File localFile  =  new File(localFileName);

		int index = doctor.getPatientIdList().indexOf(reg_no_field.getText());
		if(index != -1)
		{
			doctor.getPatientIdList().remove(index);
			doctor.getPatientNameList().remove(index);
		}
		doctor.getPatientIdList().add(reg_no_field.getText());
		doctor.getPatientNameList().add(name_field.getText());
		try
		{	
			JAXBContext jc = JAXBContext.newInstance(Doctor.class);
			Marshaller jm = jc.createMarshaller();
			jm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
			jm.marshal(doctor,localFile);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		int sendResponse = 0;
		try
		{
			sendResponse = connection.putRequest(localFileName,serverFileName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		if(sendResponse < 0)
		{
			if(localFile.isFile())
				localFile.delete();
			JOptionPane.showMessageDialog(patientPrescriptionFormFrame,RHErrors.getErrorDescription(sendResponse));
			try
			{
				connection.logoutRequest();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			for(File tempFile: new File(Constants.dataFolder).listFiles())
				tempFile.delete();
			new DoctorLogin();
			patientPrescriptionFormFrame.dispose();
			return false;
		}
		localFile.delete();
		return true;
	}

	/**
	* Download patient file from server and parse it with JAXB parser
	* Handle error if any.
	*/
	private void getPatientReport(String PatientId)
	{
		String fileName = Constants.dataFolder + "tempPatientReport.xml";
		File file  =  new File(fileName);
		int response = 0;
		try
		{
			response = connection.getRequest(PatientId+".xml",fileName);
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
				Unmarshaller um = jc.createUnmarshaller();
				patientReport = (PatientReport)um.unmarshal(file);
			}
			catch(JAXBException jaxbe)
			{
				jaxbe.printStackTrace();
			}
			file.delete();
		}
		else
		{
			if(file.isFile())
				file.delete();
			if(response == -2)
				JOptionPane.showMessageDialog(patientPrescriptionFormFrame,"File does not exist");
			else
			{
				JOptionPane.showMessageDialog(patientPrescriptionFormFrame,RHErrors.getErrorDescription(response));
				for(File tempFile: new File(Constants.dataFolder).listFiles())
					tempFile.delete();
				// new PatientSelect(connection,doctor);
				// patientPrescriptionFormFrame.dispose();
			}
		}
	}

	/**
	* Set all patient data including patient basic data and recent doctor prescription
	*/
	private void setPatientReport()
	{
		reg_no_field.setText(patientReport.getPatientBasicData().getId());
		name_field.setText(patientReport.getPatientBasicData().getName());
		sdw_of_field.setText(patientReport.getPatientBasicData().getReference());
		occupation_field.setText(patientReport.getPatientBasicData().getOccupation());

		status_field.setText(patientReport.getPatientBasicData().getStatus());

		String imageFileName = patientReport.getPatientBasicData().getImage();

		if(status_field.getText().equals("New"))
		{
			if(imageFileName != null && !imageFileName.equals(""))
			{
				int response = 0;
				try
				{
					response = connection.getRequest(imageFileName , Constants.dataFolder + imageFileName);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				File imageFile = new File(Constants.dataFolder + imageFileName);
				if(response >= 0)
				{
					ImageIcon imageIcon = new ImageIcon(Constants.dataFolder + imageFileName); // load the image to a imageIcon
					int h = picture.getHeight();
					int w = picture.getWidth();

					Image image = imageIcon.getImage(); // transform it 
					Image newimg = image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
					imageIcon = new ImageIcon(newimg);
					picture.setIcon(imageIcon);
					imageFile.delete();
				}
				else
				{
					if(imageFile.isFile())
						imageFile.delete();
					pictureDownloadButton.setVisible(false);
					// JOptionPane.showMessageDialog(patientPrescriptionFormFrame,"No picture found!");
				}
			}
		}
		else if(picture.getIcon() == null && imageFileName != null && !imageFileName.equals(""))
			pictureDownloadButton.setVisible(true);
		date_field.setText(patientReport.getPatientBasicData().getDate());
		address_area.setText(patientReport.getPatientBasicData().getAddress());
		age_field.setText(patientReport.getPatientBasicData().getAge());
		ph_no_field.setText(patientReport.getPatientBasicData().getPhone());
		gender_field.setText(patientReport.getPatientBasicData().getGender());
		bloodGroupField.setText(patientReport.getPatientBasicData().getBloodGroup());
		height_field.setText(patientReport.getPatientBasicData().getHeight());
		family_history_area.setText(patientReport.getPatientBasicData().getFamilyhistory());
		medical_history_area.setText(patientReport.getPatientBasicData().getMedicalhistory());
		
		prevDateComboBox.removeAllItems();
		if(!patientReport.getReports().isEmpty())
		{
			current_report_count = patientReport.getReports().size()-1;
			next_button.setEnabled(false);
			// if(patientReport.getReports().size() > 1)
			// 	status_field.setText("Review");
			if(current_report_count == 0)
			{
				prev_button.setEnabled(false);
				prevDateLabel.setEnabled(false);
				prevDateComboBox.setEnabled(false);
				prevDateButton.setEnabled(false);
			}
			else
			{
				for(int i = 0; i < patientReport.getReports().size(); i++)
				{
					Report tempReport = patientReport.getReports().get(i);
					if(tempReport.getDoctorPrescription().getPrescription_date() != null)
						prevDateComboBox.addItem(tempReport.getDoctorPrescription().getPrescription_date());
				}
			}
			setReport(current_report_count);
		}
		else
		{
			next_button.setEnabled(false);
			prev_button.setEnabled(false);
		}
		if(prevDateComboBox.getItemCount() > 0)
			prevDateComboBox.setSelectedIndex(prevDateComboBox.getItemCount() - 1);
	}

	/**
	* Get medicine list
	* @return medicine list as String array
	*/
	private String[] getMedicineNameList()
	{
		String[] list = 
		{
			"Famodin",
			"Famotin",
			"Zinetac",
			"Aciloc",
			"Ranitidine",
			"Zoran",
			"SUCRASE",
			"SPARACID",
			"Levant",
			"Lupizole",
			"Protogut",
			"Pantocid",
			"Aciban",
			"Pantodac",
			"Zipant",
			"Aciban – DSR",
			"Zipant – DSR",
			"Zovanta –D",
			"Pan-D",
			"Esotrax",
			"Esoz",
			"Raciper ",
			"Esoz – D",
			"Raciper-D",
			"Veloz –D",
			"Acistal –D",
			"Ablet – D",
			"Visco gel",
			"Reflux Forte gel",
			"Tricaine MPS",
			"Epflux",
			"Mucaine",
			"DUOSPAN",
			"Colirid ",
			"Centwin – Tab/Sus",
			"Cyclopam",
			"Fri-spas",
			"Spasmindon ",
			"Spasmindon –DPS",
			"Meftal Spas – Tab/Sus",
			"Parapas",
			"Synalgesic",
			"Wygesic",
			"Sudhinol",
			"Darvocet",
			"Lupivon",
			"Baleno EZ (cap)",
			"Farizym Forte ",
			"Vitazyme Cap",
			"Lavazyme cap",
			"Engogen caps/Syr",
			"Starzyme",
			"VITAZYME LIQ",
			"Fabolife ",
			"Eubioz",
			"Gutrite",
			"Morease",
			"Colospa",
			"Mebrin –AZ ",
			"Liveril  caps/sus",
			"Neutrosec (Tablet)",
			"Sorbiline",
			"Dulcolax",
			"Softovac",
			"Duphalac",
			"Domstal",
			"Azinorm",
			"Gastrium",
			"Domperone ",
			"Azinorm-C",
			"Domstal-CZ",
			"Vernavo",
			"C-Dom",
			"Vertizine-D",
			"Stugil",
			"CIDO",
			"Kexid",
			"Novadom-C",
			"Cinzan",
			"Diziron",
			"Vergo",
			"Diligan",
			"CROCIN (500) /Sus",
			"CROCIN (650) /Sus",
			"CALPOL (500)",
			"CALPOL (650)",
			"PAINIL (100mg)",
			"FASTNAC-SR (200)",
			"FASTNAC",
			"ALOO",
			"ECOSPRIN (75)",
			"ECOSPRIN (150)",
			"ECOSPRIN (325)",
			"CV-SPRIN (75)",
			"CV-SPRIN (150)",
			"VOVERAN – D (50)",
			"OXALGIN –D(25)",
			"OXALGIN –SR (100) ",
			"VIVOGESIC PLUS",
			"OXALGIN-DP",
			"BIDNAC",
			"OXALGIN-DSP",
			"KINETO-DP",
			"IBUGESIC",
			"BRUFEN",
			"CALPOL PLUS",
			"IBUGESIC PLUS",
			"CADOLAC",
			"KETANOV",
			"MEFTAL",
			"ZYLORIC",
			"ZYCOLCHIN  ",
			"Genegesic -ZX",
			"Diclovar-CZ",
			"Zone-MR",
			"Aceclo-MR",
			"Arflur-MR",
			"Sprain-MR",
			"Spontrel",
			"Mofax",
			"Vivogesic-MR",
			"Flexon MR",
			"Calpol plus",
			"Zupar",
			"VOVERAN EMULGEL",
			"DICLONAC GEL",
			"RELAXYL SPRAY",
			"Dolonex",
			"Felcom",
			"Piricam",
			"Pirox",
			"Tobitil",
			"Feldex",
			"Pyrodex",
			"Imutrex",
			"Oncotrex",
			"Neotrexate",
			"Migril",
			"Vasograin",
			"Sibelium",
			"Flunarin",
			"NUROKIND-G",
			"NOVA-M",
			"NERVUP-PG",
			"Urimax-F",
			"Urimax-d",
			"Flavocip",
			"Contiflo-od",
			"AUGMENTIN",
			"AUGMENTIN DUO",
			"ADVENT",
			"MOXIKIND -CV",
			"DOXT",
			"TETRADOX",
			"CIPLOX",
			"CIFRAN",
			"CIPGEN (250)",
			"CIPGEN (500)",
			"LEVODAY",
			"GLEVO",
			"ZILEE ",
			"Flox – O (200)",
			"Oflin (200)",
			"Oflin (400)",
			"Zanocin (100)",
			"Zanocin (200)",
			"Zenflox(100,200,4000)/(sus)",
			"Zenflox(200)/(sus)",
			"Zenflox(4000)/(sus)",
			"NORFLOX",
			"NORBID",
			"UTIBID",
			"ALTACEF",
			"CEFTUM",
			"CETIL",
			"Cefspan",
			"Zenflox",
			"Fixx",
			"Fixx syrup",
			"Raxim",
			"Omnix",
			"Celitol syrup  (50 mg)",
			"Mahacef",
			"Cefinar",
			"Altacef /Sus",
			"AZITHREAL (tab/sus)",
			"Zady",
			"DALACIN-C",
			"DALCINEX",
			"Brakke",
			"Zenflox-OZ - Tab/sus",
			"Oflox –OZ  Tab/Susp",
			"O & O Syrup",
			"Zorno",
			"Fern",
			"Amebis Forte",
			"TDF forte",
			"Nidaquin – D",
			"Dialox ",
			"Tinidil",
			"Tiniba DF",
			"Entrolate",
			"Zoa forte",
			"Amibactin - DS",
			"AMICLINE PLUS",
			"ARISTOGYL PLUS",
			"DYRADE – M",
			"ALDEZOL-DF",
			"METRON – DF",
			"QUIGYL-DS",
			"ENTAMIZOLE FORTE",
			"METROGYL COMPOUND",
			"ZENTEL (400; Sus -200mg/5ml)",
			"ZENTEL (200mg/5ml)",
			"DEWORMIS",
			"HETRAZAN",
			"BANOCIDE",
			"Anticold",
			"Nasocare plus",
			"Nam Cold  DPS",
			"Starcet Cold",
			"Nam Cold – L",
			"Sinarest-LP",
			"Zeez cold",
			"Zucox kit",
			"Tibikit",
			"Rimactazid +Z",
			"Rifampicin with INH ",
			"Anticox",
			"Rimactazid",
			"Rimpazid ",
			"CX-3 (with Pyridoxine)",
			"R-cinex",
			"Loridin( 10)/ Sus",
			"Tirlor",
			"Lorfast-D",
			"Cetzine",
			"Cetrine",
			"CZ-3",
			"Starcet",
			"Teczine",
			"Vozet",
			"Trexydin",
			"Montair-LC",
			"Montair-LC-kid",
			"Xyzal –M",
			"Lexzin-MK",
			"Levocet-M",
			"RESPINOVA",
			"HERBODIL",
			"RESPIKID",
			"BENADRYL COUGH FORMULA",
			"ASCODEX",
			"SOVENTOL",
			"NOVACOUGH",
			"ASCORIL",
			"COSOME – A",
			"MUCARYL –AX",
			"BRONCOREX",
			"Soventus-D",
			"Sinarest Linctus",
			"Protussa",
			"Dilo-D",
			"Alex +",
			"Doxyfree - tab",
			"Doxyfree - syrup",
			"Doxovent",
			"Doxiba",
			"Deriphylline inj",
			"Deriphylline tab",
			"Levolin ",
			"Bricanyl",
			"Salbetol ",
			"BUDAMATE INHALER",
			"BUDEZ INHALER",
			"FLOHALE INHALER",
			"TIOMATE INHALER",
			"IPRAVENT",
			"VENTORLIN",
			"ASTHALIN",
			"SALBAIR",
			"SALBAIR-B  ",
			"SALBAIR-1",
			"BECORIDE FORTE",
			"MONTAIR  PLUS ",
			"MONOTRATE-OD",
			"INDERAL",
			"ANGISED ",
			"MONOCONTIN",
			"SORBITRATE",
			"TENOLOL",
			"ATEN",
			"AMLOKIND",
			"AMDEPIN",
			"AMLOGARD",
			"AMLOKIND-L",
			"LOSAR-A ",
			"AMLOPRES-L",
			"AMLODAC-L ",
			"AMDEPIN-AT ",
			"AMLOKIND-AT",
			"TENOLOL –AM ",
			"ENAM",
			"ENVAS",
			"CARDACE",
			"LOSAR (25)",
			"LOSAR (50)",
			"LOSAR (100)",
			"LOSAGARD (25)",
			"LOSAGARD (50)",
			"LOSAR-H",
			"TELMA",
			"TELSAR",
			"TELMA- H",
			"TELMIKIND- H",
			"TELISTA – H",
			"TELMA – AM",
			"TELMA –AM BL",
			"ATORVA",
			"TONACT ",
			"DUOCAD",
			"ZIVAST-ASP ",
			"ATROMAC-CV",
			"NOVASTAT",
			"ROSULIP",
			"SIMLO",
			"SIMVOTIN",
			"CLIP",
			"RHEONEX ",
			"TRANEVA",
			"CELTRANZ  ",
			"ALPRAX",
			"ALZOLAM",
			"ANXICALM",
			"ANZILUM",
			"ZOLDAC",
			"PREGCIUM",
			"SANDOCAL",
			"OSTOCALCIUM FORTE",
			"OMILCAL tab/ sus",
			"KALZANA",
			"BECADEXAMINE CAPSULE",
			"SURBEX GOLD",
			"EXERGE",
			"NUTRISAN",
			"SUPRADYN",
			"BECOSULES, ",
			"BECOZYME C FORTE ",
			"PYRICONTIN",
			"VITNEURIN",
			"OPTINEURON",
			"NEUROBION FORTE",
			"HAEM UP-Z ",
			"LIVOGEN – Z ",
			"DEXORANGE CAP ",
			"FESOVIT SPANSULE",
			"FERSOLATE",
			"VITCOFOL ",
			"ELTROXIN",
			"THYRONORM",
			"NEO-MERCAZOLE",
			"THYROCAB",
			"ANTITHYROX",
			"HUMAN ACTRAPID  (40U/100U/ML)",
			"ACTRAPID-HM(PENFILL 100U/ML)",
			"HUMAN MIXTARD (30)",
			"HUMAN MIXTARD (50)",
			"HUMAN MIXTARD (70)",
			"INSUMAN RAPID",
			"LANTUS",
			"ACAREX",
			"GLUCOBAY",
			"GLUCAR",
			"CLAZ-OD (30mg)",
			"CLAZ-OD (60mg)",
			"GLUTIDE-CR (30mg )",
			"GLUTIDE-CR (60mg )",
			"AMARYL (1 mg)",
			"AMARYL (2 mg)",
			"AMARYL (3 mg)",
			"EUGLIM (1 mg) ",
			"EUGLIM (2 mg) ",
			"EUGLIM (4 mg) ",
			"GLYNASE (2.5mg)",
			"GLYNASE (5mg)",
			"GLYNASE (10mg)",
			"Gluconorm - sr (500mg)",
			"Gluconorm - sr (1g)",
			"GLUFORMIN (500mg)",
			"GLUFORMIN-XL (500mg) ",
			"GLUFORMIN-XL (1g) ",
			"GLYCOMET (250mg)",
			"GLYCOMET (500mg)",
			"GLYCOMET (850mg)",
			"GLYCOMET (1g)",
			"GLYCOMET-SR (500mg)",
			"GLYCOMET-SR (850mg)",
			"GLYCOMET-SR (1g)",
			"GLYCIPHAGE (250mg)",
			"GLYCIPHAGE (500mg)",
			"GLYCIPHAGE (850mg)",
			"GLYCIPHAGE-SR (500mg)",
			"GLYCIPHAGE-SR (1g)",
			"VOGLITOR (0.2mg}",
			"VOGLITOR (0.3mg}",
			"GLUFORMIN-G1 (500mg  SR)",
			"GLUFORMIN-G1 (1mg)",
			"GLUFORMIN-G1 FORTE (1g SR + 1mg)",
			"GLUFORMIN-G2 (500mg  SR+2mg)",
			"GLUFORMIN-G2 FORTE (1g SR + 2mg)",
			"GLYCOMET- GP2 (500mg SR + 2mg)",
			"GLYCOMET- GP2 FORTE (1g + 2mg)",
			"GLYCOMET TRIO (500mg SR+1 mg+ 0.3 mg)",
			"JANUVIA",
			"ZOMELIS",
			"ZOMELIS-MET (50mg + 500mg & 50mg + 1g)",
			"BETNELAN",
			"BETNISOL",
			"DEXASONE",
			"DEXONA",
			"WYMESONE",
			"WYSOLONE",
			"FUCIDIN",
			"FUSIBACT – B (with betamethasone)",
			"GENTAMICIN SULPHATE",
			"TENOVATE-G (with clobitasol)",
			"ORAHEX-M",
			"B-BACT",
			"T- BACT",
			"BACTROBAN",
			"NEBASULF",
			"SILVER SULFADIAZINE",
			"DIBACT",
			"SILVEREX",
			"FURACIN",
			"CANDID",
			"MYCODERM-C ",
			"CANDID-B (with beclomethasone)",
			"ASCABIOL ",
			"SCABOMA",
			"NEDAX",
			"PAXIB",
			"TINADERM",
			"ACIVIR  CRM",
			"TRICASE  CRM",
			"FINTOP",
			"BUTOP",
			"BETNOVATE – N",
			"BETAMIL – N",
			"BETNOVATE – C",
			"GENTALENE - C",
			"ZOVATE",
			"BETNOVATE",
			"TENOVATE",
			"FLUCORT",
			"WHITFIELD – SF",
			"PARAXIN  DPS/OINTMENT",
			"VANMYCETIN  OPTICAP",
			"CIPLOX DPS",
			"CIPROBID DPS",
			"CIFRAN DPS",
			"ZANOCIN DPS",
			"ZENFLOX DPS",
			"ALBUCID 10",
			"ALBUCID 20",
			"ALBUCID 30"
		};
		Arrays.sort(list);
		return list;
	}
}