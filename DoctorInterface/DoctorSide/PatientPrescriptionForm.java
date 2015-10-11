package DoctorSide;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import javax.swing.text.Utilities;
import java.text.SimpleDateFormat;
import javax.swing.text.View;
import sun.awt.image.ToolkitImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;
import javax.imageio.ImageIO;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
// import org.apache.commons.io.FileUtils;
// import com.github.sarxos.webcam.Webcam;
// import com.github.sarxos.webcam.WebcamPanel;
// import com.github.sarxos.webcam.WebcamResolution;
// import com.github.sarxos.webcam.WebcamDiscoveryService;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;


public class PatientPrescriptionForm extends JFrame //implements ActionListener
{
	private JLabel form_label, picture, reg_no, status, date, name, sdw_of, occupation, ph_no, address, age, year, gender, height, cm, bmi, bp, weight, kg, pulse, spO2,percent, temperature, celcius, family_history, medical_history, prev_diagnosis, complaint_of, on_examination, advice, medication, diagnostic_test, provisional_diagnosis, referral, final_diagnosis, kiosk_coordinator,kiosk_coordinator_name, kiosk_coordinator_date,doctorLabel, doctor_name,doctor_date;
	
	private JTextField reg_no_field, status_field, date_field, name_field, sdw_of_field, occupation_field, ph_no_field, age_field, gender_field, height_field, bmi_field, bp_field, weight_field, pulse_field, spO2_field, temperature_field, prev_diagnosis_field,final_diagnosis_field,kiosk_coordinator_name_field, kiosk_coordinator_date_field, doctor_name_field,doctor_date_field;
	
	private JTextArea address_area,  family_history_area, medical_history_area, complaint_of_area,  on_examination_area, advice_area, medication_area, diagnostic_test_area, provisional_diagnosis_area, final_diagnosis_area, referral_area,  kiosk_coordinator_area, doctor_area;
	
	private JScrollPane address_pane,family_history_pane, medical_history_pane, complaint_of_pane,  on_examination_pane, advice_pane, medication_pane, diagnostic_test_pane, provisional_diagnosis_pane, final_diagnosis_pane, referral_pane;
	private JButton refresh_button,pictureDownloadButton,back_button,back2_button,submit_button,next_button,prev_button,prescribeButton,prescriptionButton;
	private JPanel BasicDataPanel,HealthInfoPanel,DoctorPrescriptionPanel,KioskCoordinatorPanel,DoctorPanel,ButtonPanel,CommunicationPanel;
	private JLabel medicationTypeLabel,medicationNameLabel,medicationDoseLabel,medicationDurationLabel,additionalReportsLabel;
	private JComboBox<String> additionalReportsComboBox,medicationTypeComboBox,medicationNameComboBox,medicationDoseComboBox,medicationInstructionComboBox1,medicationInstructionComboBox2,medicationTimeComboBox1,medicationTimeComboBox2,medicationDurationComboBox1,medicationDurationComboBox2;
	private JTextField medicationTimeField,medicationInstructionField;
	private JButton medicationTimeButton,medicationInstructionButton,medicationSelectButton,medicationResetButton,additionalReportsButton;
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

//constructor

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

        back_button.setText("Back");
        refresh_button.setText("Refresh");
        pictureDownloadButton.setText("Download");
        back2_button.setText("Back");
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

	protected PatientPrescriptionForm(DoctorClient myCon,Doctor doc,PatientReport pr,String kioskNum)
	{
	//initialize Form
		connection = myCon;
		patientReport = pr;
		doctor = doc;
		final JFrame jframe = this;

		kioskNumber = kioskNum;
		font = new Font("Century Schoolbook L", Font.BOLD, 14);

        setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setVisible(true);
		setLayout(null);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{
				if(JOptionPane.showConfirmDialog(jframe,confirmMessage) == JOptionPane.OK_OPTION)
				{
					for(File tempFile: new File(Constants.dataFolder).listFiles())
						tempFile.delete();
					dispose();
				}
			}
		});
		
		setTitle("PATIENT PRESCRIPTION FORM");
		getContentPane().setBackground(UIManager.getColor("Button.focus"));
		
		
		form_label = new JLabel("PATIENT PRESCRIPTION FORM");
		form_label.setFont(Constants.HEADERFONT);
		form_label.setForeground(Constants.HEADERCOLOR1);

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

    //initialize text fields
		reg_no_field = new JTextField();
		status_field = new JTextField();
		date_field = new JTextField();
		name_field = new JTextField();
		sdw_of_field = new JTextField();
		occupation_field = new JTextField();
		ph_no_field = new JTextField();
		age_field = new JTextField();
		gender_field = new JTextField();
		height_field = new JTextField();
		bmi_field = new JTextField();
		bp_field = new JTextField();
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

    //initialize JButtons
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

        AutoCompleteDecorator.decorate(medicationNameComboBox);


        setLanguage();

		reg_no_field.setEditable(false);
		status_field.setEditable(false); 
		date_field.setEditable(false);
		name_field.setEditable(false);
		sdw_of_field.setEditable(false);
		occupation_field.setEditable(false);
		ph_no_field.setEditable(false);
		age_field.setEditable(false);
		gender_field.setEditable(false);
		height_field.setEditable(false);

		bmi_field.setEditable(false);
		bp_field.setEditable(false);
		weight_field.setEditable(false);
		pulse_field.setEditable(false);
		spO2_field.setEditable(false);
		temperature_field.setEditable(false);
		prev_diagnosis_field.setEditable(false);

        kiosk_coordinator_name_field.setEditable(false);
        kiosk_coordinator_date_field.setEditable(false);
        doctor_name_field.setEditable(false);
		doctor_date_field.setEditable(false);


		back2_button.setVisible(false);
		submit_button.setVisible(false);
		prescriptionButton.setVisible(false);


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



		bmi_field.setBorder(BorderFactory.createLineBorder(Color.black));
		bp_field.setBorder(BorderFactory.createLineBorder(Color.black));
		pulse_field.setBorder(BorderFactory.createLineBorder(Color.black));
		temperature_field.setBorder(BorderFactory.createLineBorder(Color.black));
		spO2_field.setBorder(BorderFactory.createLineBorder(Color.black));
		prev_diagnosis_field.setBorder(BorderFactory.createLineBorder(Color.black));
		weight_field.setBorder(BorderFactory.createLineBorder(Color.black));

		complaint_of_area.setBorder(BorderFactory.createLineBorder(Color.black));
		on_examination_area.setBorder(BorderFactory.createLineBorder(Color.black));

		picture.setBorder(BorderFactory.createLineBorder(Color.black));

		address_area.setEditable(false);
		family_history_area.setEditable(false);
		medical_history_area.setEditable(false);
		complaint_of_area.setEditable(false);
		on_examination_area.setEditable(false);
		kiosk_coordinator_area.setEditable(false);
		doctor_area.setEditable(false);

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

		
		
		
		form_label.setBounds(300, 10, 1000, 40);
		picture.setBounds(10,60,140,140);
		reg_no.setBounds(160,60,65,20);
		reg_no_field.setBounds(220,60,105,27);
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
		gender.setBounds(390,180,60,20);
		gender_field.setBounds(450,180,60,27);
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
		bp_field.setBounds(290,340,80,20);
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
		additionalReportsComboBox.setBounds(500,510,150,25);
		additionalReportsButton.setBounds(660,510,130,25);

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

		medicationTypeLabel.setForeground(Color.WHITE);
		medicationNameLabel.setForeground(Color.WHITE);
		medicationTimeRadioButton.setForeground(Color.WHITE);
		medicationInstructionRadioButton.setForeground(Color.WHITE);
		medicationDoseLabel.setForeground(Color.WHITE);
		medicationDurationLabel.setForeground(Color.WHITE);


		back_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				for(File tempFile: new File(Constants.dataFolder).listFiles())
					tempFile.delete();
				new PatientSelect(connection,doctor);
				dispose();
			}
		});

		refresh_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				getPatientReport(patientReport.patientBasicData.getId());
				setPatientReport();
			}
		});

		pictureDownloadButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				String imageFileName = patientReport.patientBasicData.getImage();
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
					JOptionPane.showMessageDialog(jframe,RHErrors.getErrorDescription(response));
				}
			}
		});

		back2_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				setReport(current_report_count);
				setDoctorPrescriptionEditable(false);

				prev_button.setVisible(true);
				next_button.setVisible(true);
				prescribeButton.setVisible(true);
				back_button.setVisible(true);
				refresh_button.setVisible(true);
				back2_button.setVisible(false);
				submit_button.setVisible(false);
			}
		});

		additionalReportsButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
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
						dispose();
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
				if(patientReport.Reports.get(patientReport.Reports.size()-1).doctorPrescription.doctorName == null)
				{
					current_report_count = patientReport.Reports.size()-1;
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
					submit_button.setVisible(true);
					prescribeButton.setVisible(false);
					prescriptionButton.setVisible(false);
				}
				else
					JOptionPane.showMessageDialog(jframe,"Patient is already prescribed");
			}
		});

		next_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if(current_report_count<patientReport.Reports.size()-1)
				{
					setReport(++current_report_count);
					if(current_report_count == patientReport.Reports.size()-1)
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
				if(current_report_count>0)
				{
					setReport(--current_report_count);
					if(current_report_count == 0)
						prev_button.setEnabled(false);
					if(current_report_count<patientReport.Reports.size()-1)
						next_button.setEnabled(true);
				}
			}
		});

		prescriptionButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				Information info = new Information();
				info.date = doctor_date_field.getText();
				info.doctor_name = doctor_name_field.getText();
				info.patient_image=((ImageIcon)picture.getIcon()).getImage();
				// info.patient_image = picture.getIcon();
				// info.doctor_degree = 
				// info.doctor_hospital = 
				info.patient_regno = reg_no_field.getText();
				info.patient_name = name_field.getText();
				info.patient_sdw = sdw_of_field.getText();
				info.patient_age = age_field.getText();
				info.patient_gender = gender_field.getText();
				info.patient_address = address_area.getText();
				info.patient_phone = ph_no_field.getText();
				info.complaint = complaint_of_area.getText();
				info.provisional_diagnosis = provisional_diagnosis_area.getText();
				info.final_diagnosis = final_diagnosis_area.getText();
				info.doctor_advice = advice_area.getText();
				info.doctor_medication = medication_area.getText();
				info.doctor_referal = referral_area.getText();
				info.doctor_diagnostic = diagnostic_test_area.getText();
				info.kiosk_coordinator_name = kiosk_coordinator_name_field.getText();
				new Prescription_applet(info);
			}
		});

		submit_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if(provisional_diagnosis_area.getText().equals("") && final_diagnosis_area.getText().equals("") && advice_area.getText().equals("")
					&& diagnostic_test_area.getText().equals("") && referral_area.getText().equals("") && medication_area.getText().equals(""))
					JOptionPane.showMessageDialog(jframe,"Patient is not prescribed");
				else if(JOptionPane.showConfirmDialog(jframe,"Are you sure to submit?") == JOptionPane.OK_OPTION)
				{
					if(updatePatientLog() && updateDoctorLog())
					{
						addPrescriptionToReport();
						current_report_count = patientReport.Reports.size()-1;
						next_button.setEnabled(false);
						if(current_report_count == 0)
							prev_button.setEnabled(false);
						else prev_button.setEnabled(true);
						setReport(current_report_count);
						prev_button.setVisible(true);
						next_button.setVisible(true);
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
					medicationTimeField.setText((String)medicationTimeComboBox1.getSelectedItem()+(String)medicationTimeComboBox2.getSelectedItem());
				else
					medicationTimeField.setText(medicationTimeField.getText()+"/"+(String)medicationTimeComboBox1.getSelectedItem()+(String)medicationTimeComboBox2.getSelectedItem());
			}
		});

		medicationInstructionButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if(medicationInstructionField.getText().equals(""))
					medicationInstructionField.setText((String)medicationInstructionComboBox1.getSelectedItem()+" "+(String)medicationInstructionComboBox2.getSelectedItem());
				else
					medicationInstructionField.setText(medicationInstructionField.getText()+"/"+(String)medicationInstructionComboBox1.getSelectedItem()+" "+(String)medicationInstructionComboBox2.getSelectedItem());
			}
		});

		medicationSelectButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
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

		

		add(form_label);
		add(picture);
		add(reg_no);
		add(reg_no_field);
		add(status);
		add(status_field);
		add(date);
		add(date_field);
		add(name);
		add(name_field);
		add(sdw_of);
		add(sdw_of_field);
		add(occupation);
		add(occupation_field);
		add(ph_no);
		add(ph_no_field);
		add(address);
		add(address_area);
		add(age);
		add(age_field);
		add(year);
		add(gender);
		add(gender_field);
		add(height);
		add(height_field);
		add(cm);
		add(weight);
		add(weight_field);
		add(kg);
		add(bmi);
		add(bmi_field);
		add(bp);
		add(bp_field);
		add(spO2);
		add(spO2_field);
		add(percent);
		add(pulse);
		add(pulse_field);
		add(temperature);
		add(temperature_field);
		add(celcius);
		add(family_history);
		add(family_history_area);
		add(medical_history);
		add(medical_history_area);
		add(prev_diagnosis);
		add(prev_diagnosis_field);
		add(complaint_of);
		add(complaint_of_area);
		add(on_examination);
		add(on_examination_area);
		add(additionalReportsLabel);
		add(additionalReportsComboBox);
		add(additionalReportsButton);
		add(provisional_diagnosis);
		add(provisional_diagnosis_area);
		add(final_diagnosis);
		add(final_diagnosis_area);
		add(advice);
		add(advice_area);
		add(medication);
		add(medication_area);
		add(diagnostic_test);
		add(diagnostic_test_area);
		add(referral);
		add(referral_area);
		add(kiosk_coordinator);
		add(kiosk_coordinator_name);
		add(kiosk_coordinator_name_field);
		add(kiosk_coordinator_date);
		add(kiosk_coordinator_date_field);
		add(doctorLabel);
		add(doctor_name);
		add(doctor_name_field);
		add(doctor_date);
		add(doctor_date_field);

		add(back_button);
		add(refresh_button);
		add(back2_button);
		add(next_button);
		add(prev_button);
		add(prescribeButton);
		add(submit_button);
		add(pictureDownloadButton);
		add(prescriptionButton);

		medicationButtonGroup.add(medicationTimeRadioButton);
		medicationButtonGroup.add(medicationInstructionRadioButton);

		add(medicationTypeLabel);
		add(medicationTypeComboBox);
		add(medicationNameLabel);
		add(medicationNameComboBox);
		add(medicationTimeRadioButton);
		add(medicationTimeComboBox1);
		add(medicationTimeComboBox2);
		add(medicationTimeButton);
		add(medicationTimeField);
		add(medicationInstructionRadioButton);
		add(medicationInstructionComboBox1);
		add(medicationInstructionComboBox2);
		add(medicationInstructionButton);
		add(medicationInstructionField);
		add(medicationDoseLabel);
		add(medicationDoseComboBox);
		add(medicationDurationLabel);
		add(medicationDurationComboBox1);
		add(medicationDurationComboBox2);
		add(medicationSelectButton);
		add(medicationResetButton);

		medicationInstructionRadioButton.setSelected(true);

	//initialize JScrollPane
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


   	//add JScrollPane
		add(on_examination_pane);
		add(address_pane);
		add(family_history_pane);
		add(medical_history_pane);
		add(complaint_of_pane);

		add(advice_pane);
		add(medication_pane);
		add(diagnostic_test_pane);
		add(provisional_diagnosis_pane);
		add(final_diagnosis_pane);
		add(referral_pane);

		BasicDataPanel = new JPanel();
		HealthInfoPanel = new JPanel();
		DoctorPrescriptionPanel = new JPanel();
		KioskCoordinatorPanel = new JPanel();
		DoctorPanel = new JPanel();
		ButtonPanel = new JPanel();
		CommunicationPanel = new JPanel();

		BasicDataPanel.setBounds(0,55,810,250);
		HealthInfoPanel.setBounds(0,305,810,240);
		DoctorPrescriptionPanel.setBounds(810,55,1190,600);
		KioskCoordinatorPanel.setBounds(0,545,380,155);
		DoctorPanel.setBounds(380,545,430,155);
		ButtonPanel.setBounds(0,700,810,50);
		CommunicationPanel.setBounds(810,655,1190,800);

		BasicDataPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		HealthInfoPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		DoctorPrescriptionPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		KioskCoordinatorPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		DoctorPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		ButtonPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		CommunicationPanel.setBorder(BorderFactory.createRaisedBevelBorder());


		// videocamInitialize();

		add(BasicDataPanel);
		add(HealthInfoPanel);
		add(DoctorPrescriptionPanel);
		add(KioskCoordinatorPanel);
		add(DoctorPanel);
		add(ButtonPanel);
		add(CommunicationPanel);

	//add JPanels
		Constants.JPANEL2.setBounds(0,0,2000,55);
		Constants.JPANEL1.setBounds(0,0,2000,1000);
		add(Constants.JPANEL2);
		// add(Constants.JPANEL1);

		Constants.JPANEL2.setBounds(0,0,2000,Constants.PANEL2_HEIGHT);
		Constants.JPANEL1.setBounds(0,0,2000,Constants.SIZE_Y);

		
				// getPatientReport(patientReport.getId());
		setDoctorPrescriptionEditable(false);
		setMedicationTimeButtonsEnabled(false);
		setPatientReport();

	}

	private void addPrescriptionToReport()
	{
		DoctorPrescription docPrescription = new DoctorPrescription();
		docPrescription.doctorName = CheckNullString(doctor_name_field.getText());
		docPrescription.ProvisionalDiagnosis = CheckNullString(provisional_diagnosis_area.getText());
		docPrescription.FinalDiagnosis = CheckNullString(final_diagnosis_area.getText());
		docPrescription.Advice = CheckNullString(advice_area.getText());
		docPrescription.Medication = CheckNullString(medication_area.getText());
		docPrescription.Diagnosis = CheckNullString(diagnostic_test_area.getText());
		docPrescription.Referral = CheckNullString(referral_area.getText());
		docPrescription.Prescription_Date = CheckNullString(doctor_date_field.getText());

		patientReport.Reports.get(patientReport.Reports.size()-1).doctorPrescription = docPrescription;


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
				JOptionPane.showMessageDialog(this,"File does not exist");
			else
			{
				JOptionPane.showMessageDialog(this,RHErrors.getErrorDescription(response));
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
				dispose();
			}
		}
		else
			file.delete();
	}

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

//neccessary methods

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


    private String CheckNullString(String str)
    {
    	if(str.equals(""))
    		return null;
    	else return str;
    }

	private void setReport(int report_count)
	{
		if(report_count != -1)
		{
			Report report = patientReport.Reports.get(report_count);
			weight_field.setText(report.patientComplaint.getWeight());
			bmi_field.setText(report.patientComplaint.getBmi());
			bp_field.setText(report.patientComplaint.getBp());
			pulse_field.setText(report.patientComplaint.getPulse());
			temperature_field.setText(report.patientComplaint.getTemperature());
			spO2_field.setText(report.patientComplaint.getSpo2());
			prev_diagnosis_field.setText(report.patientComplaint.getPrevDiagnosis());
			on_examination_area.setText(report.patientComplaint.getOtherResults());
			complaint_of_area.setText(report.patientComplaint.getcomplaint());
			kiosk_coordinator_name_field.setText(report.patientComplaint.getKioskCoordinatorName());
			kiosk_coordinator_date_field.setText(report.patientComplaint.getcomplaint_date());

			if(report.patientComplaint.getFileNames() != null)
			{
				DefaultComboBoxModel<String> defaultComboBoxModel = new DefaultComboBoxModel<String>(report.patientComplaint.getFileNames().split("\n"));
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

			if(report.doctorPrescription != null)
			{
				doctor_name_field.setText(report.doctorPrescription.getdoctorName());
				doctor_date_field.setText(report.doctorPrescription.getPrescription_date());
				provisional_diagnosis_area.setText(report.doctorPrescription.getProvisionalDiagnosis());
				final_diagnosis_area.setText(report.doctorPrescription.getFinalDiagnosis());
				advice_area.setText(report.doctorPrescription.getAdvice());
				medication_area.setText(report.doctorPrescription.getMedication());
				diagnostic_test_area.setText(report.doctorPrescription.getDiagnosis());
				referral_area.setText(report.doctorPrescription.getReferral());
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

	private boolean updatePatientLog()
	{
		String localFileName = Constants.dataFolder + "log.xml",serverFileName = "Patient_"+kioskNumber+"_Log.xml";
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

				if(patientLog.Normal.indexOf(reg_no_field.getText()) != -1)
					patientLog.Normal.remove(reg_no_field.getText());
				else if(patientLog.Emergency.indexOf(reg_no_field.getText()) != -1)
					patientLog.Emergency.remove(reg_no_field.getText());

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
				JOptionPane.showMessageDialog(this,RHErrors.getErrorDescription(sendResponse));
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
				dispose();
				return false;
			}
			localFile.delete();
			return true;
		}
		else
		{
			if(localFile.isFile())
				localFile.delete();
			if(receiveResponse == -2)
				JOptionPane.showMessageDialog(this,"File does not exists");
			else
				JOptionPane.showMessageDialog(this,RHErrors.getErrorDescription(receiveResponse));
			return false;
		}
	}

	private boolean updateDoctorLog()
	{
		String localFileName = Constants.dataFolder + "tempDoctor.xml",serverFileName = doctor.doctorId+".xml";
		File localFile  =  new File(localFileName);

		int index = doctor.patientIdList.indexOf(reg_no_field.getText());
		if(index != -1)
		{
			doctor.patientIdList.remove(index);
			doctor.patientNameList.remove(index);
		}
		doctor.patientIdList.add(reg_no_field.getText());
		doctor.patientNameList.add(name_field.getText());
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
			JOptionPane.showMessageDialog(this,RHErrors.getErrorDescription(sendResponse));
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
			dispose();
			return false;
		}
		localFile.delete();
		return true;
	}

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
				JOptionPane.showMessageDialog(this,"File does not exist");
			else
			{
				JOptionPane.showMessageDialog(this,RHErrors.getErrorDescription(response));
				for(File tempFile: new File(Constants.dataFolder).listFiles())
					tempFile.delete();
				new PatientSelect(connection,doctor);
				dispose();
			}
		}
	}

	private void setPatientReport()
	{
		reg_no_field.setText(patientReport.patientBasicData.getId());
		name_field.setText(patientReport.patientBasicData.getName());
		sdw_of_field.setText(patientReport.patientBasicData.getReference());
		occupation_field.setText(patientReport.patientBasicData.getOccupation());

		status_field.setText(patientReport.patientBasicData.getStatus());

		String imageFileName = patientReport.patientBasicData.getImage();

		if(status_field.getText().equals("New"))
		{
			if(imageFileName != null)
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
					JOptionPane.showMessageDialog(this,RHErrors.getErrorDescription(response));
				}
			}
		}
		else if(picture.getIcon() == null && imageFileName != null)
			pictureDownloadButton.setVisible(true);
		date_field.setText(patientReport.patientBasicData.getDate());
		address_area.setText(patientReport.patientBasicData.getAddress());
		age_field.setText(patientReport.patientBasicData.getAge());
		ph_no_field.setText(patientReport.patientBasicData.getPhone());
		gender_field.setText(patientReport.patientBasicData.getGender());
		height_field.setText(patientReport.patientBasicData.getHeight());
		family_history_area.setText(patientReport.patientBasicData.getFamilyhistory());
		medical_history_area.setText(patientReport.patientBasicData.getMedicalhistory());

		if(!patientReport.Reports.isEmpty())
		{
			current_report_count = patientReport.Reports.size()-1;
			next_button.setEnabled(false);
			status_field.setText("Review");
			if(current_report_count == 0)
			{
				prev_button.setEnabled(false);
			}
			setReport(current_report_count);
		}
		else
		{
			next_button.setEnabled(false);
			prev_button.setEnabled(false);
		}
	}

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


class Information
{
	protected String date,doctor_name,doctor_degree,doctor_hospital;
	protected String patient_regno,patient_name,patient_sdw,patient_address,patient_age,patient_gender,patient_phone;
	protected String complaint,provisional_diagnosis,final_diagnosis;
	protected String doctor_advice,doctor_medication,doctor_diagnostic,doctor_referal;
	protected String kiosk_coordinator_name;
	protected Image patient_image;
}

class Prescription_applet extends JFrame
{
	private JLabel form_label,patient_picture_label, date1, date2, regno,regno_label,name1, name2,sdw_of_label,age1, age2, years, gender1, gender2, ph_no1, ph_no2, address, complaint1, advice, medication, diagnostic_test, provisional_diagnosis, referral, final_diagnosis, kiosk_coordinator,kiosk_coordinator_name1, kiosk_coordinator_name2,  kiosk_coordinator_date, doctor_name1, doctor_name2,doctor_sign,degree,hospital,doctor_ph_no1,doctor_ph_no2;
	
	private JTextArea address_area, complaint2,  family_history_area, medical_history_area, complaint_of_area,  on_examination_area, advice_area, medication_area, diagnostic_test_area, provisional_diagnosis_area, final_diagnosis_area, referral_area;
	
	private JPanel JPANEL1,JPANEL3,JPANEL4,JPANEL5,JPANEL6;
	private Font font = new Font("Serif",Font.BOLD,12);
	private String confirmMessage = "Are you sure?";
	
	
	protected Prescription_applet(Information info)
	{
		
		
		final JFrame jframe = this;
		setVisible(true);
		setSize(595,852);
		form_label = new JLabel("RURAL HEALTH KIOSK PRESCRIPTION");
		form_label.setForeground(Color.WHITE);
		form_label.setBackground(Color.white);
       	form_label.setFont(new Font("Serif", Font.BOLD, 15));	
       	
       	patient_picture_label = new JLabel();
       	date1 = new JLabel("Date:");
        date2 = new JLabel("-date-");
               	
        //DOCTOR LABELS
        	
        doctor_name1 = new JLabel("Dr.");
        doctor_name2 = new JLabel("-name-");
        degree = new JLabel("-Degree-");
        hospital = new JLabel("-Hospital-");
        doctor_sign = new JLabel("Signature : ");
        doctor_ph_no1 = new JLabel("Ph.No.:");
        doctor_ph_no2 = new JLabel("-number-");
		jframe.setBounds(200,10,595,846+190);
		setLayout(null);

		setBackground(Color.white);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{
				// for(File tempFile: new File(Constants.dataFolder + "").listFiles())
					// tempFile.delete();
				dispose();
			}
		});
        
        
        //PATIENT LABELS
        regno = new JLabel("Reg No.:");
        regno_label = new JLabel();
        name1 = new JLabel("Name :");
        name2 = new JLabel("-name-");
        sdw_of_label = new JLabel();
        age1 = new JLabel("Age :");
        age2 = new JLabel("-age-");
        years = new JLabel("years");
        gender1 = new JLabel("Gender:");
        gender2 = new JLabel("-m/f-");
     	ph_no1 = new JLabel("Ph.No. :");
        ph_no2 = new JLabel("-number-");
        address = new JLabel("Address :");
		complaint1 = new JLabel("Complaint of :");
		provisional_diagnosis = new JLabel("Provisional Diagnosis");
		final_diagnosis = new JLabel("Final Diagnosis :");
		advice = new JLabel("Advice :");
		medication = new JLabel("Medication :");
	    referral = new JLabel("Referral :"); 
        diagnostic_test = new JLabel("Diagnostic Test :");
        kiosk_coordinator = new JLabel("Kiosk Coordinator :");
        kiosk_coordinator_name1 = new JLabel("Kiosk Coordinator Name :");
        kiosk_coordinator_name2 = new JLabel("-name-");       	


        address_area = new JTextArea("-Address-");
		complaint2 = new JTextArea("-complaint-");
		provisional_diagnosis_area = new JTextArea("-provisional diagnosis-");
		final_diagnosis_area = new JTextArea("-final diagnosis-");
		advice_area = new JTextArea("-advice-");
		medication_area = new JTextArea("-medication-");
        referral_area = new JTextArea();
        diagnostic_test_area = new JTextArea();


		address_area.setBackground(new Color(0, 0, 0, 0));
		complaint2.setBackground(new Color(0, 0, 0, 0));
		provisional_diagnosis_area.setBackground(new Color(0, 0, 0, 0));
		final_diagnosis_area.setBackground(new Color(0, 0, 0, 0));
		advice_area.setBackground(new Color(0, 0, 0, 0));
		medication_area.setBackground(new Color(0, 0, 0, 0));
		referral_area.setBackground(new Color(0, 0, 0, 0));
		diagnostic_test_area.setBackground(new Color(0, 0, 0, 0));

		date1.setForeground(Color.WHITE);
		date2.setForeground(Color.WHITE);

		address_area.setEditable(false);
		complaint2.setEditable(false);
		provisional_diagnosis_area.setEditable(false);
		final_diagnosis_area.setEditable(false);
		advice_area.setEditable(false);
		medication_area.setEditable(false);
		referral_area.setEditable(false);	  
		diagnostic_test_area.setEditable(false);

		complaint2.setLineWrap(true);
		provisional_diagnosis_area.setLineWrap(true);
		final_diagnosis_area.setLineWrap(true);
		advice_area.setLineWrap(true);
		medication_area.setLineWrap(true);
		referral_area.setLineWrap(true);	  
		diagnostic_test_area.setLineWrap(true);

		complaint2.setWrapStyleWord(true);
		provisional_diagnosis_area.setWrapStyleWord(true);
		final_diagnosis_area.setWrapStyleWord(true);
		advice_area.setWrapStyleWord(true);
		medication_area.setWrapStyleWord(true);
		referral_area.setWrapStyleWord(true);	  
		diagnostic_test_area.setWrapStyleWord(true);

		address_area.setBorder(null);

		doctor_name1.setFont(font);
		degree.setFont(font);
		hospital.setFont(font);

		date1.setFont(font);
		regno.setFont(font);
		name1.setFont(font);
		sdw_of_label.setFont(font);
		age1.setFont(font);
		years.setFont(font);
		gender1.setFont(font);
		ph_no1.setFont(font);
		doctor_sign.setFont(font);
		address.setFont(font);
		complaint1.setFont(font);
		provisional_diagnosis.setFont(font);
		final_diagnosis.setFont(font);
		advice.setFont(font);
		medication.setFont(font);
		referral.setFont(font);
		diagnostic_test.setFont(font);

		kiosk_coordinator.setFont(font);
		kiosk_coordinator_name1.setFont(font);
		// kiosk_coordinator_date.setFont(font);

		patient_picture_label.setBounds(10,45,100,100);
		form_label.setBounds(100,5,400,30);
		date1.setBounds(470,20,40,15);
		date2.setBounds(510,20,80,15);
		//DOCTOR BOUNDS

		//PATIENT BOUNDS
		regno.setBounds(120,45,100,15);
		regno_label.setBounds(180,45,100,15);
		name1.setBounds(120,65,100,15);
		name2.setBounds(180,65,200,15);
		sdw_of_label.setBounds(320,65,200,15);
		age1.setBounds(120,85,40,15);
		age2.setBounds(180,85,50,15);
		years.setBounds(200,85,40,15);
		gender1.setBounds(120,105,70,15);
		gender2.setBounds(180,105,70,15);
		ph_no1.setBounds(120,125,70,15);
		ph_no2.setBounds(180,125,100,15);
		address.setBounds(320,85,70,15);
		address_area.setBounds(390,85,150,60);

		patient_picture_label.setBorder(new LineBorder(Color.black, 1));


		try
		{
			BufferedImage newImg = ((ToolkitImage)(info.patient_image)).getBufferedImage();
			(new File(Constants.dataFolder + "patient_Picture.jpg")).createNewFile();
			ImageIO.write(newImg, "jpg", new File(Constants.dataFolder + "patient_Picture.jpg"));
			ImageIcon imageIcon  =  new ImageIcon(Constants.dataFolder + "patient_Picture.jpg"); // load the image to a imageIcon
			int h = patient_picture_label.getHeight();
			int w = patient_picture_label.getWidth();
			Image image  =  imageIcon.getImage(); // transform it 
			Image newimg  =  image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			imageIcon  =  new ImageIcon(newimg);
			patient_picture_label.setIcon(imageIcon);
			(new File(Constants.dataFolder + "patient_Picture.jpg")).delete();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		// patient_picture_label.setIcon(info.patient_image);

		// patient_picture_label.setIcon(new ImageIcon(info.patient_image));
		date2.setText(info.date);
		regno_label.setText(info.patient_regno);
		doctor_name2.setText(info.doctor_name);
		degree.setText(info.doctor_degree);
		hospital.setText(info.doctor_hospital);
		name2.setText(info.patient_name);
		sdw_of_label.setText(info.patient_sdw);
		address_area.setText(info.patient_address);
		age2.setText(info.patient_age);
		gender2.setText(info.patient_gender);
		ph_no2.setText(info.patient_phone);

		complaint2.setText(info.complaint);
		provisional_diagnosis_area.setText(info.provisional_diagnosis);
		final_diagnosis_area.setText(info.final_diagnosis);
		advice_area.setText(info.doctor_advice);
		medication_area.setText(info.doctor_medication);
		referral_area.setText(info.doctor_referal);
		diagnostic_test_area.setText(info.doctor_diagnostic);

		kiosk_coordinator_name2.setText(info.kiosk_coordinator_name);

		boolean extraPage = false;

		int LEFTSIDECOLUMN = 45;
		int LEFTSIDEX = 10;
		int LEFTSIDEW = 250;

		int RIGHTSIDECOLUMN = 48;
		int RIGHTSIDEX = 280;
		int RIGHTSIDEW = 300;

		int JPANEL6Y = 630;

		int ComplaintLabelY = 160;
		int ComplaintLabelH = 15;
		int ComplaintAreaY = ComplaintLabelY+ComplaintLabelH+5;
		int ComplaintAreaH = getHeight(complaint2.getText(),LEFTSIDECOLUMN);

		int ProvisionalDiagnosisLabelY = ComplaintAreaY+ComplaintAreaH+5;
		int ProvisionalDiagnosisLabelH = 15;
		int ProvisionalDiagnosisAreaY = ProvisionalDiagnosisLabelY+ProvisionalDiagnosisLabelH+5;
		int ProvisionalDiagnosisAreaH = getHeight(provisional_diagnosis_area.getText(),LEFTSIDECOLUMN);

		if(isNullString(provisional_diagnosis_area.getText()))
		{
			provisional_diagnosis.setVisible(false);
			provisional_diagnosis_area.setVisible(false);

			ProvisionalDiagnosisLabelH = 0;
			ProvisionalDiagnosisAreaY = ProvisionalDiagnosisLabelY+ProvisionalDiagnosisLabelH+5;
			ProvisionalDiagnosisAreaH = -10;
		}

		int FinalDiagnosisLabelY = ProvisionalDiagnosisAreaY+ProvisionalDiagnosisAreaH+5;
		int FinalDiagnosisLabelH = 15;
		int FinalDiagnosisAreaY = FinalDiagnosisLabelY+FinalDiagnosisLabelH+5;
		int FinalDiagnosisAreaH = getHeight(final_diagnosis_area.getText(),LEFTSIDECOLUMN);

		int ReferralLabelY = FinalDiagnosisAreaY+FinalDiagnosisAreaH+5;
		int ReferralLabelH = 15;
		int ReferralAreaY = ReferralLabelY+ReferralLabelH+5;
		int ReferralAreaH = getHeight(referral_area.getText(),LEFTSIDECOLUMN);
		int ReferralX = LEFTSIDEX;
		int ReferralW = LEFTSIDEW;

		if(isNullString(referral_area.getText()))
		{
			referral.setVisible(false);
			referral_area.setVisible(false);

			ReferralLabelH = 0;
			ReferralAreaY = ReferralLabelY+ReferralLabelH+5;
			ReferralAreaH = -10;
		}

		int AdviceLabelY = 160;
		int AdviceLabelH = 15;
		int AdviceAreaY = AdviceLabelY+AdviceLabelH+5;
		int AdviceAreaH = getHeight(advice_area.getText(),RIGHTSIDECOLUMN);

		if(isNullString(advice_area.getText()))
		{
			advice.setVisible(false);
			advice_area.setVisible(false);

			AdviceLabelH = 0;
			AdviceAreaY = AdviceLabelY+AdviceLabelH+5;
			AdviceAreaH = -10;
		}

		int MedicationLabelY = AdviceAreaY+AdviceAreaH+5;
		int MedicationLabelH = 15;
		int MedicationAreaY = MedicationLabelY+MedicationLabelH+5;
		int MedicationAreaH = getHeight(medication_area.getText(),RIGHTSIDECOLUMN);

		int DiagnosticLabelY = MedicationAreaY+MedicationAreaH+5;
		int DiagnosticLabelH = 15;
		int DiagnosticAreaY = DiagnosticLabelY+DiagnosticLabelH+5;
		int DiagnosticAreaH = getHeight(info.doctor_diagnostic,RIGHTSIDECOLUMN);
		int DiagnosticX = RIGHTSIDEX;
		int DiagnosticW = RIGHTSIDEW;

		if(isNullString(diagnostic_test_area.getText()))
		{
			diagnostic_test.setVisible(false);
			diagnostic_test_area.setVisible(false);

			DiagnosticLabelH = 0;
			DiagnosticAreaY = DiagnosticLabelY+DiagnosticLabelH+5;
			DiagnosticAreaH = 0;
		}

		if(DiagnosticAreaY+DiagnosticAreaH+5>ReferralAreaY+ReferralAreaH+DiagnosticLabelH+DiagnosticAreaH)
		{
			DiagnosticLabelY = ReferralAreaY+ReferralAreaH+5;
			DiagnosticAreaY = DiagnosticLabelY+DiagnosticLabelH+5;

			DiagnosticX = LEFTSIDEX;
			DiagnosticW = LEFTSIDEW;
		}
		else if(ReferralAreaY+ReferralAreaH+5>DiagnosticAreaY+DiagnosticAreaH+ReferralLabelH+ReferralAreaH)
		{
			ReferralLabelY = DiagnosticAreaY+DiagnosticAreaH+5;
			ReferralAreaY = ReferralLabelY+ReferralLabelH+5;

			ReferralX = RIGHTSIDEX;
			ReferralW = RIGHTSIDEW;
		}

		int[] data = {JPANEL6Y,DiagnosticAreaY+DiagnosticAreaH,ReferralAreaY+ReferralAreaH,MedicationAreaY+MedicationAreaH,FinalDiagnosisAreaY+FinalDiagnosisAreaH};
		Arrays.sort(data);
		JPANEL6Y = data[4]+10;

		// setSize(595,950);

		// setLocationRelativeTo(null);

		//COMPLAINT OF BOUNDS
		complaint1.setBounds(LEFTSIDEX,ComplaintLabelY,200,ComplaintLabelH);
		complaint2.setBounds(LEFTSIDEX,ComplaintAreaY,LEFTSIDEW,ComplaintAreaH);
		//DIAGNOSIS BOUNDS
		provisional_diagnosis.setBounds(LEFTSIDEX,ProvisionalDiagnosisLabelY,200,ProvisionalDiagnosisLabelH);
		provisional_diagnosis_area.setBounds(LEFTSIDEX,ProvisionalDiagnosisAreaY,LEFTSIDEW,ProvisionalDiagnosisAreaH);

		final_diagnosis.setBounds(LEFTSIDEX,FinalDiagnosisLabelY,200,FinalDiagnosisLabelH);
		final_diagnosis_area.setBounds(LEFTSIDEX,FinalDiagnosisAreaY,LEFTSIDEW,FinalDiagnosisAreaH);

		referral.setBounds(ReferralX,ReferralLabelY,200,ReferralLabelH);
		referral_area.setBounds(ReferralX,ReferralAreaY,ReferralW,ReferralAreaH);
		//ADVICE BOUNDS
		advice.setBounds(RIGHTSIDEX,AdviceLabelY,200,AdviceLabelH);
		advice_area.setBounds(RIGHTSIDEX,AdviceAreaY,RIGHTSIDEW,AdviceAreaH);
		//MEDICATION BOUNDS
		medication.setBounds(RIGHTSIDEX,MedicationLabelY,200,MedicationLabelH);
		medication_area.setBounds(RIGHTSIDEX,MedicationAreaY,RIGHTSIDEW,MedicationAreaH);
		//REFERRAL
		diagnostic_test.setBounds(DiagnosticX,DiagnosticLabelY,200,DiagnosticLabelH);
		diagnostic_test_area.setBounds(DiagnosticX,DiagnosticAreaY,DiagnosticW,DiagnosticAreaH);
		//DIAGNOSTIC TEST

		// getRow(info.doctor_medication,200);
		

		add(patient_picture_label);
		add(form_label);
		add(date1);
		add(date2);
		add(doctor_name1);
		add(doctor_name2);
		add(doctor_sign);
		add(degree);
		add(hospital);
		add(doctor_ph_no1);
		add(doctor_ph_no2);
		add(regno);
		add(regno_label);
        add(name1);
        add(name2);
        add(sdw_of_label);
        add(age1);
        add(age2);
        add(years);
        add(gender1);
        add(gender2);
        add(ph_no1);
        add(ph_no2);
        add(address);
        add(address_area);
        add(complaint1);
        add(complaint2);
        add(provisional_diagnosis);
        add(provisional_diagnosis_area);
        add(final_diagnosis);
        add(final_diagnosis_area);
        add(advice);
        add(advice_area);
        add(medication);
        add(medication_area);
        add(referral);
        add(referral_area);
        add(diagnostic_test);
        add(diagnostic_test_area);
        add(kiosk_coordinator_name1);
        add(kiosk_coordinator_name2);

        //you dont have to touch this one
        JPANEL1 = new JPanel();
		// JPANEL2 = new JPanel();
		JPANEL3 = new JPanel();
		JPANEL4 = new JPanel();
		JPANEL5 = new JPanel();
		JPANEL6 = new JPanel();

		JPANEL1.setLayout(new BorderLayout());
		// JPANEL2.setLayout(new BorderLayout());
		JPANEL3.setLayout(new BorderLayout());
		JPANEL4.setLayout(new BorderLayout());
		JPANEL5.setLayout(new BorderLayout());
		JPANEL6.setLayout(new BorderLayout());
		
		doctor_name1.setBounds(280,JPANEL6Y+10,25,15);
		doctor_name2.setBounds(305,JPANEL6Y+10,200,15);
		degree.setBounds(280,JPANEL6Y+30,250,15);
		// hospital.setBounds(10,80,250,15);
		// doctor_ph_no1.setBounds(280,40,50,15);
		// doctor_ph_no2.setBounds(330,40,100,15);	
		doctor_sign.setBounds(280,JPANEL6Y+50,100,30);

		//KIOSK COORDINATOR
		kiosk_coordinator_name1.setBounds(10,JPANEL6Y+10,200,15);
		kiosk_coordinator_name2.setBounds(10,JPANEL6Y+30,200,15);
	       
		JPANEL1.setBounds(0,0,595,35);
		// JPANEL2.setBounds(0,35,625,110);
		JPANEL3.setBounds(0,35,595,120);
		JPANEL4.setBounds(0,150,270,JPANEL6Y-145);
		JPANEL5.setBounds(265,150,330,JPANEL6Y-145);
		JPANEL6.setBounds(0,JPANEL6Y,595,120);


		JPANEL1.setBackground(Color.green.darker().darker());
		// JPANEL2.setBackground(Color.white);
		JPANEL3.setBackground(Color.white);
		JPANEL4.setBackground(Color.white);
		JPANEL5.setBackground(Color.white);
		JPANEL6.setBackground(Color.white);
		
		JPANEL1.setBorder(BorderFactory.createRaisedBevelBorder());
		// JPANEL2.setBorder(new LineBorder(Color.black, 5));
		JPANEL3.setBorder(new LineBorder(Color.black, 5));
		JPANEL4.setBorder(new LineBorder(Color.black, 5));
		JPANEL5.setBorder(new LineBorder(Color.black, 5));
		JPANEL6.setBorder(new LineBorder(Color.black, 5));

		add(JPANEL6);
		add(JPANEL5);
		add(JPANEL4);
		add(JPANEL3);
		// add(JPANEL2);
		add(JPANEL1);

		jframe.setSize(595,JPANEL6Y+150);
		// jframe.pack();

		// kiosk_coordinator_name1.setBounds(10,10,100,25);

		// try
		// {
		// 	PrinterJob pjob  =  PrinterJob.getPrinterJob();
		// 	PageFormat preformat  =  pjob.defaultPage();
		// 	preformat.setOrientation(PageFormat.LANDSCAPE);
		// 	PageFormat postformat  =  pjob.pageDialog(preformat);
		// 	//If user does not hit cancel then print.
		// 	if (preformat  !=  postformat) {
		// 	    //Set print component
		// 	    pjob.setPrintable(new Printer(this), postformat);
		// 	    if (pjob.printDialog()) {
		// 	        pjob.print();
		// 	    }
		// 	}
		// }
		// catch(PrinterException pe)
		// {
		// 	pe.printStackTrace();
		// }
		// dispose();
	}

	private int getHeight(String str,int width)
	{
		String arr[] = str.split("\n");
		for(int i = 0;i<arr.length;i++)
		{
			int extraLine = arr[i].length()/width;
			for(int j = 0;j<extraLine;j++)
				str = str+"\nab";
		}
		str += "\n";
		JTextArea tempTextArea = new JTextArea(str);
		return (int)tempTextArea.getPreferredSize().getHeight();
	}

	private boolean isNullString(String str)
    {
    	if(str.equals(""))
    		return true;
    	else return false;
    }
}