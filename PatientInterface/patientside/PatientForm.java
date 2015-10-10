package patientside;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
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
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;
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
// import com.github.sarxos.webcam.Webcam;
// import com.github.sarxos.webcam.WebcamPanel;
// import com.github.sarxos.webcam.WebcamResolution;
// import com.github.sarxos.webcam.WebcamDiscoveryService;


public class PatientForm
{
	PatientForm(Connection myCon,PatientReport pr,Employee e)
	{
		final Connection connection = myCon;
		final PatientReport patientReport = pr;
		final Employee emp = e;
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new Form(connection,patientReport,emp);
			}
		});
	}
}

class Form extends JFrame //implements ActionListener
{
	private JLabel form_label, picture, reg_no, status, date,emergency, name, sdw_of, occupation, ph_no, address, age, year, gender, height, cm, bmi, bp, weight, kg, pulse, spO2,percent, temperature, celcius, family_history, medical_history, prev_diagnosis, complaint_of, on_examination,anemia,edema,jaundice, advice, medication, diagnostic_test, provisional_diagnosis, referral, final_diagnosis, kiosk_coordinator,kiosk_coordinator_name,kiosk_coordinator_sign, kiosk_coordinator_date,doctor, doctor_name,doctor_sign,doctor_date;
	
	private JTextField reg_no_field, status_field, date_field, name_field, sdw_of_field, occupation_field, ph_no_field, age_field, gender_field, height_field, bmi_field, bp_field, weight_field, pulse_field, spO2_field, temperature_field, prev_diagnosis_field,final_diagnosis_field,kiosk_coordinator_name_field, kiosk_coordinator_date_field, doctor_name_field,doctor_date_field;
	
	private JTextArea address_area,  family_history_area, medical_history_area, complaint_of_area,  on_examination_area, filename_area,advice_area, medication_area, diagnostic_test_area, provisional_diagnosis_area, final_diagnosis_area, referral_area,  kiosk_coordinator_area, doctor_area;
	
	private JScrollPane address_pane,family_history_pane, medical_history_pane, complaint_of_pane,  on_examination_pane,filename_pane, advice_pane, medication_pane, diagnostic_test_pane, provisional_diagnosis_pane, final_diagnosis_pane, referral_pane;
	private JButton refresh_button,back_button,back2_button,submit_button,next_button,prev_button,newcomplaint_button,choose_button,print_button,patientBasicDataEdit_button,patientBasicDataSave_button,patientBasicDataCancel_button,changePicture_button,addFile_button,cancelFile_button,patientComplaintEdit_button,patientComplaintSave_button,patientComplaintCancel_button;
	private JCheckBox emergency_box,anemia_box,edema_box,jaundice_box;
	private JPanel BasicDataPanel,HealthInfoPanel,DoctorPrescriptionPanel,KioskCoordinatorPanel,DoctorPanel,ButtonPanel,CommunicationPanel;
	private PatientReport patientReport;
	private Employee employee;
	private Font font;
	private int current_report_count = -1;
	private boolean PatientBasicDataEditMode = false;
	private int imageSet = 0;
	private String imgstr;
	private String confirmMessage,networkErrorMessage,newComplaintErrorMessage,imageString = null;
	private ArrayList < File >  selectedFiles = new ArrayList < File > ();
	private final Connection connection;

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

	private void setLanguage(String str)
	{
		if(str.equals("Bengali"))
		{
		    reg_no.setText("রেজি. নং.:");
		    status.setText("অবস্থা :");
		    date.setText("রেজিস্ট্রেশন তারিখ :");
		    name.setText("নাম :");
		    sdw_of.setText("পুত্র/কন্যা/স্ত্রী :");
		    occupation.setText("পেশা :");
		    ph_no.setText("ফোন নম্বর :");
		    address.setText("ঠিকানা :");
		    age.setText("বয়স :");
		    year.setText("বছর");
		    gender.setText("লিঙ্গ :");
		    height.setText("উচ্চতা :");
		    cm.setText("সেমি");
		    bmi.setText("BMI:");
		    bp.setText("BP:");
		    weight.setText("ওজন :");
		    kg.setText("কেজি");
		    pulse.setText("Pulse :");
		    spO2.setText("spO2:");
		    percent.setText("%");
		    temperature.setText("তাপমাত্রা :");
		    celcius.setText("C");

		    family_history.setText("<html>পারিবারিক<br>তথ্য&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp:</html>:");
		    medical_history.setText("<html>চিকিৎসার<br>তথ্য&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp:</html>");
		    prev_diagnosis.setText("রোগী কোনো পূর্ববর্তী প্রেসক্রিপশন এনেছে?");
		    complaint_of.setText("সমস্যা :");
		    emergency.setText("গুরূতর");
		    anemia.setText("Anemia");
		    edema.setText("Edema");
		    jaundice.setText("Jaundice");
		    on_examination.setText("পরীক্ষার পর :");
		    advice.setText("পরামর্শ :");
		    medication.setText("ঔষধ :");
		    diagnostic_test.setText("স্বাস্থ্য পরীক্ষা :");
		    provisional_diagnosis.setText("প্রাথমিক পরীক্ষা :");
		    referral.setText("নির্দেশ :");
		    final_diagnosis.setText("অন্তিম পরীক্ষা :");
		    kiosk_coordinator.setText("স্বাস্থ্য সহকারী :");
		    kiosk_coordinator_name.setText("নাম :");
		    kiosk_coordinator_sign.setText("স্বাক্ষর :");
		    kiosk_coordinator_date.setText("তারিখ :");
		    doctor.setText("ডাক্তার :");
		    doctor_name.setText("নাম :");
		    doctor_sign.setText("স্বাক্ষর :");
		    doctor_date.setText("তারিখ :");

		//set font
		    reg_no.setFont(Constants.BENGALILABELFONT);
		    status.setFont(Constants.BENGALILABELFONT);
		    date.setFont(Constants.BENGALILABELFONT);
		    emergency.setFont(Constants.BENGALILABELFONT);
		    name.setFont(Constants.BENGALILABELFONT);
		    sdw_of.setFont(Constants.BENGALILABELFONT);
		    occupation.setFont(Constants.BENGALILABELFONT);
		    ph_no.setFont(Constants.BENGALILABELFONT);
		    address.setFont(Constants.BENGALILABELFONT);
		    age.setFont(Constants.BENGALILABELFONT);
		    year.setFont(Constants.BENGALILABELFONT);
		    gender.setFont(Constants.BENGALILABELFONT);
		    height.setFont(Constants.BENGALILABELFONT);
		    cm.setFont(Constants.BENGALILABELFONT);
		    bmi.setFont(Constants.SMALLLABELFONT);
		    bp.setFont(Constants.SMALLLABELFONT);
		    weight.setFont(Constants.BENGALILABELFONT);
		    kg.setFont(Constants.BENGALILABELFONT);
		    pulse.setFont(Constants.SMALLLABELFONT);
		    spO2.setFont(Constants.SMALLLABELFONT);
		    percent.setFont(Constants.SMALLLABELFONT);
		    temperature.setFont(Constants.BENGALILABELFONT);
		    celcius.setFont(Constants.SMALLLABELFONT);

		    family_history.setFont(Constants.BENGALILABELFONT);
		    medical_history.setFont(Constants.BENGALILABELFONT);
		    prev_diagnosis.setFont(Constants.BENGALILABELFONT);
		    complaint_of.setFont(Constants.BENGALILABELFONT);
		    anemia.setFont(Constants.BENGALILABELFONT);
		    edema.setFont(Constants.BENGALILABELFONT);
		    jaundice.setFont(Constants.BENGALILABELFONT);
		    on_examination.setFont(Constants.BENGALILABELFONT);
		    advice.setFont(Constants.BENGALILABELFONT);
		    medication.setFont(Constants.BENGALILABELFONT);
		    diagnostic_test.setFont(Constants.BENGALILABELFONT);
		    provisional_diagnosis.setFont(Constants.BENGALILABELFONT);
		    referral.setFont(Constants.BENGALILABELFONT);
		    final_diagnosis.setFont(Constants.BENGALILABELFONT);
		    kiosk_coordinator.setFont(Constants.BENGALILABELFONT);
		    kiosk_coordinator_name.setFont(Constants.BENGALILABELFONT);
		    kiosk_coordinator_sign.setFont(Constants.BENGALILABELFONT);
		    kiosk_coordinator_date.setFont(Constants.BENGALILABELFONT);
		    doctor.setFont(Constants.BENGALILABELFONT);
		    doctor_name.setFont(Constants.BENGALILABELFONT);
		    doctor_sign.setFont(Constants.BENGALILABELFONT);
		    doctor_date.setFont(Constants.BENGALILABELFONT);

            back_button.setText("ফেরত");
            refresh_button.setText("রিফ্রেশ");
            back2_button.setText("ফেরত");
            submit_button.setText("জমা দিন");
            next_button.setText("পরবর্তী");
            prev_button.setText("পূর্ববর্তী");
            newcomplaint_button.setText("নতুন সমস্যা");
            choose_button.setText("নির্বাচন");
            print_button.setText("প্রিন্ট করুন");
	        patientBasicDataEdit_button.setText("লিখুন");
	        patientBasicDataSave_button.setText("নিশ্চিত");
	        patientBasicDataCancel_button.setText("বাতিল");
	        changePicture_button.setText("ছবি পরিবর্তন");
	        addFile_button.setText("ফাইল আপলোড");
	        cancelFile_button.setText("বাতিল");
	        patientComplaintEdit_button.setText("লিখুন");
	        patientComplaintSave_button.setText("নিশ্চিত");
	        patientComplaintCancel_button.setText("বাতিল");

            back_button.setFont(Constants.BENGALIBUTTONFONT);
            refresh_button.setFont(Constants.BENGALIBUTTONFONT);
            back2_button.setFont(Constants.BENGALIBUTTONFONT);
            submit_button.setFont(Constants.BENGALIBUTTONFONT);
            next_button.setFont(Constants.BENGALIBUTTONFONT);
            prev_button.setFont(Constants.BENGALIBUTTONFONT);
            newcomplaint_button.setFont(Constants.BENGALIBUTTONFONT);
    		choose_button.setFont(Constants.BENGALIBUTTONFONT);
            print_button.setFont(Constants.BENGALIBUTTONFONT);
            patientBasicDataEdit_button.setFont(Constants.BENGALIBUTTONFONT);
            patientBasicDataSave_button.setFont(Constants.BENGALIBUTTONFONT);
            patientBasicDataCancel_button.setFont(Constants.BENGALIBUTTONFONT);
            changePicture_button.setFont(Constants.BENGALIBUTTONFONT);
            addFile_button.setFont(Constants.BENGALIBUTTONFONT);
            cancelFile_button.setFont(Constants.BENGALIBUTTONFONT);
            patientComplaintEdit_button.setFont(Constants.BENGALIBUTTONFONT);
            patientComplaintSave_button.setFont(Constants.BENGALIBUTTONFONT);
            patientComplaintCancel_button.setFont(Constants.BENGALIBUTTONFONT);

			confirmMessage = "আপনি কি নিশ্চিত?";
			networkErrorMessage = "নেটওয়ার্ক সমস্যা! পরে আবার চেষ্টা করুন";
			newComplaintErrorMessage = "পূর্ববর্তী অভিযোগ এখনো সমাধান হয়নি";
		}
		else if(str.equals("English"))
		{
		    reg_no.setText("Reg. no.:");
		    status.setText("Status:");
		    date.setText("Registration Date :");
		    emergency.setText("Emergency");
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
		    anemia.setText("Anemia");
		    edema.setText("Edema");
		    jaundice.setText("Jaundice");
		    on_examination.setText("On Examintion :");
		    advice.setText("Advice :");
		    medication.setText("Medication :");
		    diagnostic_test.setText("Diagnostic Test :");
		    provisional_diagnosis.setText("Provisional Diagnosis :");
		    referral.setText("Referral :");
		    final_diagnosis.setText("Final Diagnosis :");
		    kiosk_coordinator.setText("Health Asistant :");
		    kiosk_coordinator_name.setText("Name :");
		    kiosk_coordinator_sign.setText("Signature :");
		    kiosk_coordinator_date.setText("Date :");
		    doctor.setText("Doctor :");
		    doctor_name.setText("Name :");
		    doctor_sign.setText("Signature :");
		    doctor_date.setText("Date :");

		//set font
		    reg_no.setFont(Constants.SMALLLABELFONT);
		    status.setFont(Constants.SMALLLABELFONT);
		    date.setFont(Constants.SMALLLABELFONT);
		    emergency.setFont(Constants.SMALLLABELFONT);
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
		    anemia.setFont(Constants.SMALLLABELFONT);
		    edema.setFont(Constants.SMALLLABELFONT);
		    jaundice.setFont(Constants.SMALLLABELFONT);
		    on_examination.setFont(Constants.SMALLLABELFONT);
		    advice.setFont(Constants.SMALLLABELFONT);
		    medication.setFont(Constants.SMALLLABELFONT);
		    diagnostic_test.setFont(Constants.SMALLLABELFONT);
		    provisional_diagnosis.setFont(Constants.SMALLLABELFONT);
		    referral.setFont(Constants.SMALLLABELFONT);
		    final_diagnosis.setFont(Constants.SMALLLABELFONT);
		    kiosk_coordinator.setFont(Constants.SMALLLABELFONT);
		    kiosk_coordinator_name.setFont(Constants.SMALLLABELFONT);
		    kiosk_coordinator_sign.setFont(Constants.SMALLLABELFONT);
		    kiosk_coordinator_date.setFont(Constants.SMALLLABELFONT);
		    doctor.setFont(Constants.SMALLLABELFONT);
		    doctor_name.setFont(Constants.SMALLLABELFONT);
		    doctor_sign.setFont(Constants.SMALLLABELFONT);
		    doctor_date.setFont(Constants.SMALLLABELFONT);

            back_button.setText("Back");
            refresh_button.setText("Refresh");
            back2_button.setText("Back");
            submit_button.setText("Submit");
            next_button.setText("Next");
            prev_button.setText("Prev");
            newcomplaint_button.setText("New Complaint");
            choose_button.setText("Choose");
            print_button.setText("Print");
	        patientBasicDataEdit_button.setText("Edit");
	        patientBasicDataSave_button.setText("Save");
	        patientBasicDataCancel_button.setText("Cancel");
	        changePicture_button.setText("Change picture");
	        addFile_button.setText("Add File");
	        cancelFile_button.setText("Cancel File");
	        patientComplaintEdit_button.setText("Edit");
	        patientComplaintSave_button.setText("Save");
	        patientComplaintCancel_button.setText("Cancel");


            back_button.setFont(Constants.SMALLBUTTONFONT);
            refresh_button.setFont(Constants.SMALLBUTTONFONT);
            back2_button.setFont(Constants.SMALLBUTTONFONT);
            submit_button.setFont(Constants.SMALLBUTTONFONT);
            next_button.setFont(Constants.SMALLBUTTONFONT);
            prev_button.setFont(Constants.SMALLBUTTONFONT);
            newcomplaint_button.setFont(Constants.SMALLBUTTONFONT);
    		choose_button.setFont(Constants.SMALLBUTTONFONT);
            print_button.setFont(Constants.SMALLBUTTONFONT);
            patientBasicDataEdit_button.setFont(Constants.SMALLBUTTONFONT);
            patientBasicDataSave_button.setFont(Constants.SMALLBUTTONFONT);
            patientBasicDataCancel_button.setFont(Constants.SMALLBUTTONFONT);
            changePicture_button.setFont(Constants.SMALLBUTTONFONT);
            addFile_button.setFont(Constants.SMALLBUTTONFONT);
            cancelFile_button.setFont(Constants.SMALLBUTTONFONT);
            patientComplaintEdit_button.setFont(Constants.SMALLBUTTONFONT);
            patientComplaintSave_button.setFont(Constants.SMALLBUTTONFONT);
            patientComplaintCancel_button.setFont(Constants.SMALLBUTTONFONT);


			confirmMessage = "Are you sure?";
			networkErrorMessage = "Connection error! Try again later!";
			newComplaintErrorMessage = "Previous Complaint has not solved yet!";
		}
	}

	public Form(Connection myCon,PatientReport pr,Employee e)
	{
	//initialize Form
		connection = myCon;
		patientReport = pr;
		employee = e;
		final JFrame jframe = this;
		// final String PatientId = temp;
		font = new Font("Century Schoolbook L", Font.BOLD, 14);

		
	
		//horizontalLine = new Line2D.Float(490, 40, 1000, 40);
       	        //verticalLine = new Line2D.Float(810,0,810,490);
                             
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		setVisible(true);
		
		//setUndecorated(true);
		
		setLayout(null);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{
				if(JOptionPane.showConfirmDialog(jframe,confirmMessage) == JOptionPane.OK_OPTION)
				{
					// if(webcam != null)
     //                    webcam.getDevice().dispose();
					dispose();
                    // try
                    // {
                    //     socket.close();
                    // }
                    // catch(Exception ex)
                    // {
                    //     ex.printStackTrace();
                    // }
                    // if(ri != null)
                    // {
                    //     ri.running = false;
                    //     // ri.t.suspend();
                    //     ri = null;
                    // }
                    // if(si != null)
                    // {
                    //     si.running = false;
                    //     // si.t.suspend();
                    //     si = null;
                    // }
                    // System.exit(0);
				}
			}
		});
		
		setTitle("RURAL HEALTH KIOSK PRESCRIPTION");
		getContentPane().setBackground(UIManager.getColor("Button.focus"));
		
		
		form_label = new JLabel("RURAL HEALTH KIOSK PRESCRIPTION");
		form_label.setFont(Constants.HEADERFONT);
		form_label.setForeground(Constants.HEADERCOLOR1);
		// form_label.setForeground(Color.blue);
		// form_label.setBackground(Color.blue);

  //initialize JLabels
        picture = new JLabel("");
        reg_no = new JLabel("রেজি. নং.");
        status = new JLabel("অবস্থা");
        date = new JLabel("রেজিস্ট্রেশন তারিখ:");
        emergency = new JLabel("গুরূতর");
        name = new JLabel("নাম:");
        sdw_of = new JLabel("পুত্র/কন্যা/স্ত্রী:");
        occupation = new JLabel("পেশা:");
        ph_no = new JLabel("ফোন নম্বর:");
        address = new JLabel("ঠিকানা:");
        age = new JLabel("বয়স:");
        year = new JLabel("বছর");
        gender = new JLabel("লিঙ্গ:");
        height = new JLabel("উচ্চতা:");
        cm = new JLabel("সেমি");
        bmi = new JLabel("BMI:");
        bp = new JLabel("BP:");
        weight = new JLabel("ওজন:");
        kg = new JLabel("কেজি");
        pulse = new JLabel("Pulse:");
        spO2 = new JLabel("spO2:");
        percent = new JLabel("%");
        temperature = new JLabel("তাপমাত্রা:");
        celcius = new JLabel("C");

        family_history = new JLabel("পারিবারিক তথ্য:");
        medical_history = new JLabel("চিকিৎসার তথ্য:");
        prev_diagnosis = new JLabel("রোগী কোনো পূর্ববর্তী প্রেসক্রিপশন এনেছে?");
        complaint_of = new JLabel("সমস্যা:");
        anemia = new JLabel("Anemia");
        edema = new JLabel("Edema");
        jaundice = new JLabel("Jaundice");
        on_examination = new JLabel("পরীক্ষার পর:");
        advice = new JLabel("পরামর্শ:");
        medication = new JLabel("ঔষধ:");
        diagnostic_test = new JLabel("স্বাস্থ্য পরীক্ষা:");
        provisional_diagnosis = new JLabel("প্রাথমিক পরীক্ষা");
        referral = new JLabel("নির্দেশ:");
        final_diagnosis = new JLabel("অন্তিম পরীক্ষা:");
        kiosk_coordinator = new JLabel("স্বাস্থ্য সহকারী:");
        kiosk_coordinator_name = new JLabel("নাম:");
        kiosk_coordinator_sign = new JLabel("স্বাক্ষর:");
        kiosk_coordinator_date = new JLabel("তারিখ:");
        doctor = new JLabel("ডাক্তার:");
        doctor_name = new JLabel("নাম:");
        doctor_sign = new JLabel("স্বাক্ষর:");
        doctor_date = new JLabel("তারিখ:");

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
        back_button = new JButton("ফেরত");
        refresh_button = new JButton("রিফ্রেশ");
        back2_button = new JButton("ফেরত");
        submit_button = new JButton("জমা দিন");
        next_button = new JButton("পরবর্তী");
        prev_button = new JButton("পূর্ববর্তী");
        newcomplaint_button = new JButton("নতুন সমস্যা");
        choose_button = new JButton("নির্বাচন");
        print_button = new JButton("প্রিন্ট করুন");
        patientBasicDataEdit_button = new JButton("লিখুন");
        patientBasicDataSave_button = new JButton("নিশ্চিত");
        patientBasicDataCancel_button = new JButton("বাতিল");
        changePicture_button = new JButton("ছবি পরিবর্তন");
        addFile_button = new JButton();
        cancelFile_button = new JButton();
        patientComplaintEdit_button = new JButton();
        patientComplaintSave_button = new JButton();
        patientComplaintCancel_button = new JButton();

        anemia_box = new JCheckBox();
        edema_box = new JCheckBox();
        jaundice_box = new JCheckBox();
        emergency_box = new JCheckBox();

        setLanguage(Constants.language);


    //set Editable
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

	//set visible
		emergency.setVisible(false);
		anemia.setVisible(false);
		edema.setVisible(false);
		jaundice.setVisible(false);
		emergency_box.setVisible(false);
		anemia_box.setVisible(false);
		edema_box.setVisible(false);
		jaundice_box.setVisible(false);
		back2_button.setVisible(false);
		addFile_button.setVisible(false);
		cancelFile_button.setVisible(false);
		submit_button.setVisible(false);
		choose_button.setVisible(false);
		print_button.setVisible(false);
		patientBasicDataSave_button.setVisible(false);
		patientBasicDataCancel_button.setVisible(false);
		changePicture_button.setVisible(false);
		patientComplaintEdit_button.setVisible(false);
		patientComplaintSave_button.setVisible(false);
		patientComplaintCancel_button.setVisible(false);

	//initialize JTextArea
		address_area = new JTextArea();
		family_history_area = new JTextArea();
		medical_history_area = new JTextArea();
		complaint_of_area = new JTextArea();
		on_examination_area = new JTextArea();
		filename_area = new JTextArea();
		advice_area = new JTextArea();
		medication_area = new JTextArea();
		diagnostic_test_area = new JTextArea();
		provisional_diagnosis_area = new JTextArea();
		final_diagnosis_area = new JTextArea();
		referral_area = new JTextArea();
		kiosk_coordinator_area = new JTextArea();
		doctor_area = new JTextArea();


	//set Border
		bmi_field.setBorder(BorderFactory.createLineBorder(Color.black));
		bp_field.setBorder(BorderFactory.createLineBorder(Color.black));
		pulse_field.setBorder(BorderFactory.createLineBorder(Color.black));
		temperature_field.setBorder(BorderFactory.createLineBorder(Color.black));
		spO2_field.setBorder(BorderFactory.createLineBorder(Color.black));
		prev_diagnosis_field.setBorder(BorderFactory.createLineBorder(Color.black));
		weight_field.setBorder(BorderFactory.createLineBorder(Color.black));

		complaint_of_area.setBorder(BorderFactory.createLineBorder(Color.black));
		on_examination_area.setBorder(BorderFactory.createLineBorder(Color.black));

		filename_area.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)));

		filename_area.setBackground(new Color(0,0,0,0));
		picture.setBorder(BorderFactory.createLineBorder(Color.black));


	//Text area set Editable
		address_area.setEditable(false);
		family_history_area.setEditable(false);
		medical_history_area.setEditable(false);
		complaint_of_area.setEditable(false);
		on_examination_area.setEditable(false);
		filename_area.setEditable(false);
		advice_area.setEditable(false);
		medication_area.setEditable(false);
		diagnostic_test_area.setEditable(false);
		provisional_diagnosis_area.setEditable(false);
		final_diagnosis_area.setEditable(false);
		referral_area.setEditable(false);
		kiosk_coordinator_area.setEditable(false);
		doctor_area.setEditable(false);

	//set Linewrap
		address_area.setLineWrap(true);
		// medication_area.setLineWrap(true);
		provisional_diagnosis_area.setLineWrap(true);
		final_diagnosis_area.setLineWrap(true);
		advice_area.setLineWrap(true);
		family_history_area.setLineWrap(true);
		medical_history_area.setLineWrap(true);
		diagnostic_test_area.setLineWrap(true);
		referral_area.setLineWrap(true);
		complaint_of_area.setLineWrap(true);
		on_examination_area.setLineWrap(true);

		
		
		
		
	// SETBOUNDS
		form_label.setBounds(300, 10, 1000, 40);
		picture.setBounds(10,60,140,140);
		reg_no.setBounds(160,60,65,20);
		reg_no_field.setBounds(220,60,105,27);
		status.setBounds(330,60,60,20);
		status_field.setBounds(380,60,60,27);
		date.setBounds(450,60,140,20);
		date_field.setBounds(580,60,90,27);
		emergency_box.setBounds(670,310,20,20);
		emergency.setBounds(700,310,90,20);
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
		complaint_of_area.setBounds(50,370,310,140);
		on_examination.setBounds(380,370,120,20);
		on_examination_area.setBounds(500,370,290,70);
		anemia_box.setBounds(380,390,20,20);
		anemia.setBounds(400,390,70,20);
		edema_box.setBounds(380,410,20,20);
		edema.setBounds(400,410,70,20);
		jaundice_box.setBounds(380,430,20,20);
		jaundice.setBounds(400,430,70,20);
		filename_area.setBounds(500,450,290,90);

		provisional_diagnosis.setBounds(835,60,170,20);
		provisional_diagnosis_area.setBounds(830,80,250,95);
		final_diagnosis.setBounds(1105,60,170,20);
		final_diagnosis_area.setBounds(1100,80,250,95);
		advice.setBounds(835,180,70,20);
		advice_area.setBounds(830,200,250,120);
		medication.setBounds(1105,180,100,20);
		medication_area.setBounds(1100,200,250,120);
		diagnostic_test.setBounds(835,330,150,20);
		diagnostic_test_area.setBounds(830,350,250,80);
		referral.setBounds(1105,330,150,20);
		referral_area.setBounds(1100,350,250,80);

		/*kiosk_coordinator, doctor;*/
		kiosk_coordinator.setBounds(10,550,360,20);
		kiosk_coordinator_name.setBounds(10,570,50,20);
		kiosk_coordinator_name_field.setBounds(60,570,200,23);
		kiosk_coordinator_sign.setBounds(10,590,350,20);
		kiosk_coordinator_date.setBounds(10,670,50,20);
		kiosk_coordinator_date_field.setBounds(60,670,100,23);
		doctor.setBounds(390,550,130,20);
		doctor_name.setBounds(390,570,50,20);
		doctor_name_field.setBounds(450,570,200,23);
		doctor_sign.setBounds(390,590,300,20);
		doctor_date.setBounds(390,670,50,20);
		doctor_date_field.setBounds(450,670,100,23);
		
		back_button.setBounds(10,15,80,25);
		refresh_button.setBounds(100,15,100,25);
		back2_button.setBounds(10,15,80,25);
		next_button.setBounds(690,710,100,25);
		prev_button.setBounds(10,710,100,25);
		newcomplaint_button.setBounds(1200,15,150,25);
		addFile_button.setBounds(380,460,120,25);
		cancelFile_button.setBounds(380,490,120,25);
		submit_button.setBounds(690,710,100,25);
		choose_button.setBounds(50,510,80,30);
		print_button.setBounds(240,710,100,25);
		patientBasicDataEdit_button.setBounds(680,60,100,25);
		patientBasicDataSave_button.setBounds(670,60,60,25);
		patientBasicDataCancel_button.setBounds(730,60,80,25);
		changePicture_button.setBounds(12,170,135,25);
		patientComplaintEdit_button.setBounds(680,310,100,25);
		patientComplaintSave_button.setBounds(630,310,70,25);
		patientComplaintCancel_button.setBounds(700,310,80,25);


	//set actionListener
		back_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				// (new File(Constants.dataPath + "tempPatientReport.xml")).delete();
				new PatientLogin(connection,employee);
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

		back2_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				setReport(current_report_count);
				prev_button.setVisible(true);
				next_button.setVisible(true);
				newcomplaint_button.setVisible(true);
				back_button.setVisible(true);
				refresh_button.setVisible(true);
				// patientComplaintEdit_button.setVisible(true);
				patientBasicDataEdit_button.setVisible(true);
				emergency.setVisible(false);
				emergency_box.setVisible(false);
				anemia.setVisible(false);
				anemia_box.setVisible(false);
				edema.setVisible(false);
				edema_box.setVisible(false);
				jaundice.setVisible(false);
				jaundice_box.setVisible(false);
				back2_button.setVisible(false);
				submit_button.setVisible(false);
				addFile_button.setVisible(false);
				cancelFile_button.setVisible(false);
				choose_button.setVisible(false);

				bmi_field.setEditable(false);
				bp_field.setEditable(false);
				weight_field.setEditable(false);
				pulse_field.setEditable(false);
				spO2_field.setEditable(false);
				temperature_field.setEditable(false);
				prev_diagnosis_field.setEditable(false);
				complaint_of_area.setEditable(false);
				on_examination_area.setEditable(false);
				filename_area.setEditable(false);

				emergency_box.setSelected(false);
				anemia_box.setSelected(false);
				edema_box.setSelected(false);
				jaundice_box.setSelected(false);

				HealthInfoPanel.setBackground(Constants.JPANELCOLOR1);
				weight.setForeground(Color.BLACK);
				kg.setForeground(Color.BLACK);
				bmi.setForeground(Color.BLACK);
				bp.setForeground(Color.BLACK);
				pulse.setForeground(Color.BLACK);
				temperature.setForeground(Color.BLACK);
				celcius.setForeground(Color.BLACK);
				spO2.setForeground(Color.BLACK);
				percent.setForeground(Color.BLACK);

				prev_diagnosis.setForeground(Color.BLACK);
				complaint_of.setForeground(Color.BLACK);
				on_examination.setForeground(Color.BLACK);
				provisional_diagnosis.setForeground(Color.BLACK);
				final_diagnosis.setForeground(Color.BLACK);
			}
		});

		newcomplaint_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				int size = patientReport.Reports.size();
				if(size == 0 || patientReport.Reports.get(size-1).doctorPrescription.getdoctorName() != null)
					newComplaintAction();
				else JOptionPane.showMessageDialog(jframe,newComplaintErrorMessage);
			}
		});

		next_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if(current_report_count < patientReport.Reports.size()-1)
				{
					setReport(++ current_report_count);
					if(current_report_count == patientReport.Reports.size()-1)
						next_button.setEnabled(false);
					if(current_report_count > 0)
						prev_button.setEnabled(true);
				}
			}
		});

		prev_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if(current_report_count > 0)
				{
					setReport(--current_report_count);
					if(current_report_count == 0)
						prev_button.setEnabled(false);
					if(current_report_count < patientReport.Reports.size()-1)
						next_button.setEnabled(true);
				}
			}
		});

		choose_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					getPatientComplaint();
				}
				catch(IOException ioe)
				{
					ioe.printStackTrace();
				}
			}
		});

		print_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				Information info = new Information();
				info.date = doctor_date_field.getText();
				info.doctor_name = doctor_name_field.getText();
				info.patient_image = ((ImageIcon)picture.getIcon()).getImage();
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
				JFrame FRAME_TO_PRINT = new Prescription_applet(info);
			}
		});

		addFile_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setMultiSelectionEnabled(true);
				int return_value = fileChooser.showOpenDialog(addFile_button);
				if(return_value == JFileChooser.APPROVE_OPTION)
				{
					File[] tempFiles = fileChooser.getSelectedFiles();
					for(int i = 0;i < tempFiles.length;i++)
					{
						filename_area.setText(filename_area.getText() + tempFiles[i].getName() + "\n");
						selectedFiles.add(tempFiles[i]);
					}
				}
			}
		});

		cancelFile_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				selectedFiles = null;
				selectedFiles = new ArrayList < File > ();
				filename_area.setText("");
			}
		});

		submit_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if(validatePatientComplaint())
				{
					if(update_log())
					{
						addComplaintToFile();
						current_report_count = patientReport.Reports.size()-1;
						next_button.setEnabled(false);
						if(current_report_count == 0)
							prev_button.setEnabled(false);
						else prev_button.setEnabled(true);
						setReport(current_report_count);
						prev_button.setVisible(true);
						next_button.setVisible(true);
						newcomplaint_button.setVisible(true);
						back_button.setVisible(true);
						refresh_button.setVisible(true);
						// patientComplaintEdit_button.setVisible(true);
						patientBasicDataEdit_button.setVisible(true);
						emergency_box.setVisible(false);
						emergency.setVisible(false);
						anemia.setVisible(false);
						anemia_box.setVisible(false);
						edema.setVisible(false);
						edema_box.setVisible(false);
						jaundice.setVisible(false);
						jaundice_box.setVisible(false);
						back2_button.setVisible(false);
						submit_button.setVisible(false);
						choose_button.setVisible(false);
						addFile_button.setVisible(false);
						cancelFile_button.setVisible(false);

						bmi_field.setEditable(false);
						bp_field.setEditable(false);
						weight_field.setEditable(false);
						pulse_field.setEditable(false);
						spO2_field.setEditable(false);
						temperature_field.setEditable(false);
						prev_diagnosis_field.setEditable(false);
						complaint_of_area.setEditable(false);
						on_examination_area.setEditable(false);
						filename_area.setEditable(false);

						emergency_box.setSelected(false);
						anemia_box.setSelected(false);
						edema_box.setSelected(false);
						jaundice_box.setSelected(false);

						HealthInfoPanel.setBackground(Constants.JPANELCOLOR1);
						weight.setForeground(Color.BLACK);
						kg.setForeground(Color.BLACK);
						bmi.setForeground(Color.BLACK);
						bp.setForeground(Color.BLACK);
						pulse.setForeground(Color.BLACK);
						temperature.setForeground(Color.BLACK);
						celcius.setForeground(Color.BLACK);
						spO2.setForeground(Color.BLACK);
						percent.setForeground(Color.BLACK);

						prev_diagnosis.setForeground(Color.BLACK);
						complaint_of.setForeground(Color.BLACK);
						on_examination.setForeground(Color.BLACK);
					}
				}
			}
		});

		patientBasicDataEdit_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				reg_no.setForeground(Color.WHITE);
				status.setForeground(Color.WHITE);
				date.setForeground(Color.WHITE);
				name.setForeground(Color.WHITE);
				sdw_of.setForeground(Color.WHITE);
				occupation.setForeground(Color.WHITE);
				ph_no.setForeground(Color.WHITE);
				address.setForeground(Color.WHITE);
				age.setForeground(Color.WHITE);
				year.setForeground(Color.WHITE);
				height.setForeground(Color.WHITE);
				cm.setForeground(Color.WHITE);
				gender.setForeground(Color.WHITE);
				family_history.setForeground(Color.WHITE);
				medical_history.setForeground(Color.WHITE);

				BasicDataPanel.setBackground(Color.GREEN.darker().darker());

				PatientBasicDataEditMode = true;
				SetPatientBasicDataEditable(PatientBasicDataEditMode);
				patientBasicDataEdit_button.setVisible(false);
				patientBasicDataSave_button.setVisible(true);
				patientBasicDataCancel_button.setVisible(true);
				changePicture_button.setVisible(true);

				back_button.setVisible(false);
				refresh_button.setVisible(false);
				prev_button.setVisible(false);
				next_button.setVisible(false);
				newcomplaint_button.setVisible(false);
				print_button.setVisible(false);
				patientComplaintEdit_button.setVisible(false);
				imageSet = 0;
			}
		});

		patientBasicDataSave_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if(validatePatientBasicData())
				{
					reg_no.setForeground(Color.BLACK);
					status.setForeground(Color.BLACK);
					date.setForeground(Color.BLACK);
					name.setForeground(Color.BLACK);
					sdw_of.setForeground(Color.BLACK);
					occupation.setForeground(Color.BLACK);
					ph_no.setForeground(Color.BLACK);
					address.setForeground(Color.BLACK);
					age.setForeground(Color.BLACK);
					year.setForeground(Color.BLACK);
					height.setForeground(Color.BLACK);
					cm.setForeground(Color.BLACK);
					gender.setForeground(Color.BLACK);
					family_history.setForeground(Color.BLACK);
					medical_history.setForeground(Color.BLACK);

					BasicDataPanel.setBackground(Constants.JPANELCOLOR1);

					PatientBasicDataEditMode = false;
					SetPatientBasicDataEditable(PatientBasicDataEditMode);
					patientBasicDataEdit_button.setVisible(true);
					patientBasicDataSave_button.setVisible(false);
					patientBasicDataCancel_button.setVisible(false);
					changePicture_button.setVisible(false);

					back_button.setVisible(true);
					refresh_button.setVisible(true);
					prev_button.setVisible(true);
					next_button.setVisible(true);
					newcomplaint_button.setVisible(true);
					print_button.setVisible(true);
					patientComplaintEdit_button.setVisible(true);

					int response = connection.receiveFromServer(patientReport.patientBasicData.getId() + ".xml",Constants.dataPath + "tempPatientReport.xml");
					File file = new File(Constants.dataPath + "tempPatientReport.xml");
					if(response  >= 0)
					{
						try
						{
							JAXBContext jc = JAXBContext.newInstance(PatientReport.class);
							Unmarshaller um = jc.createUnmarshaller();
							patientReport = (PatientReport)um.unmarshal(file);
							file.delete();
						}
						catch(Exception e)
						{
							if(file.isFile())
								file.delete();
							e.printStackTrace();
						}

						encodeToString();
						patientReport.patientBasicData.setName(name_field.getText());
						patientReport.patientBasicData.setReference(sdw_of_field.getText());
						patientReport.patientBasicData.setOccupation(occupation_field.getText());
						patientReport.patientBasicData.setPhone(ph_no_field.getText());
						patientReport.patientBasicData.setAddress(address_area.getText());
						patientReport.patientBasicData.setAge(age_field.getText());
						patientReport.patientBasicData.setHeight(height_field.getText());
						patientReport.patientBasicData.setFamilyhistory(family_history_area.getText());
						patientReport.patientBasicData.setMedicalhistory(medical_history_area.getText());
						if(imageString != null)
							patientReport.patientBasicData.setImage(imageString);
					}
					else
					{
						if(file.isFile())
							file.delete();
						JOptionPane.showMessageDialog(jframe,RHErrors.getErrorDescription(response));
					}

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
						file.delete();
					}
					response = connection.sendToServer(Constants.dataPath + "tempPatientReport.xml",Constants.finalDataPath + reg_no_field.getText() + ".xml");
					if(response < 0)
					{
						JOptionPane.showMessageDialog(jframe,RHErrors.getErrorDescription(response));
					}
					if(file.isFile())
						file.delete();

					// getPatientReport(patientReport.patientBasicData.getId());
					setPatientReport();
					imageSet = 0;
					if(new File("patient_Picture.jpg").isFile())
						new File("patient_Picture.jpg").delete();
				}
			}
		});

		patientBasicDataCancel_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				imageSet = 0;
				reg_no.setForeground(Color.BLACK);
				status.setForeground(Color.BLACK);
				date.setForeground(Color.BLACK);
				name.setForeground(Color.BLACK);
				sdw_of.setForeground(Color.BLACK);
				occupation.setForeground(Color.BLACK);
				ph_no.setForeground(Color.BLACK);
				address.setForeground(Color.BLACK);
				age.setForeground(Color.BLACK);
				year.setForeground(Color.BLACK);
				height.setForeground(Color.BLACK);
				cm.setForeground(Color.BLACK);
				gender.setForeground(Color.BLACK);
				family_history.setForeground(Color.BLACK);
				medical_history.setForeground(Color.BLACK);

				BasicDataPanel.setBackground(Constants.JPANELCOLOR1);

				PatientBasicDataEditMode = false;
				SetPatientBasicDataEditable(PatientBasicDataEditMode);
				patientBasicDataEdit_button.setVisible(true);
				patientBasicDataSave_button.setVisible(false);
				patientBasicDataCancel_button.setVisible(false);
				changePicture_button.setVisible(false);


				back_button.setVisible(true);
				refresh_button.setVisible(true);
				prev_button.setVisible(true);
				next_button.setVisible(true);
				newcomplaint_button.setVisible(true);
				print_button.setVisible(true);
				patientComplaintEdit_button.setVisible(true);

				
				getPatientReport(patientReport.patientBasicData.getId());
				setPatientReport();
				imageSet = 0;
				if(new File("patient_Picture.jpg").isFile())
					new File("patient_Picture.jpg").delete();
			}
		});
		
		changePicture_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					Process ps = Runtime.getRuntime().exec("java"/* -cp PatientApp.jar */ + " webTake.myCam");
					ps.waitFor();
				}
				catch(IOException ioe)
				{
					ioe.printStackTrace();
				}
				catch(InterruptedException ie)
				{
					ie.printStackTrace();
				}
				if(new File("patient_Picture.jpg").exists())
				{
					
					ImageIcon imageIcon = new ImageIcon("patient_Picture.jpg"); // load the image to a imageIcon
					int h = picture.getHeight();
					int w = picture.getWidth();
				
				
					Image image = imageIcon.getImage(); // transform it 
					Image newimg = image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
					imageIcon = new ImageIcon(newimg);

				
					picture.setIcon(imageIcon);
					imageSet = 1;
					// deleteImage();
				}
				else System.out.println("no image");
			}
		});

		patientComplaintEdit_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				HealthInfoPanel.setBackground(Color.GREEN.darker().darker());
				weight.setForeground(Color.WHITE);
				kg.setForeground(Color.WHITE);
				bmi.setForeground(Color.WHITE);
				bp.setForeground(Color.WHITE);
				pulse.setForeground(Color.WHITE);
				temperature.setForeground(Color.WHITE);
				celcius.setForeground(Color.WHITE);
				spO2.setForeground(Color.WHITE);
				percent.setForeground(Color.WHITE);

				prev_diagnosis.setForeground(Color.WHITE);
				complaint_of.setForeground(Color.WHITE);
				on_examination.setForeground(Color.WHITE);

				bmi_field.setEditable(true);
				bp_field.setEditable(true);
				weight_field.setEditable(true);
				pulse_field.setEditable(true);
				spO2_field.setEditable(true);
				temperature_field.setEditable(true);
				prev_diagnosis_field.setEditable(true);
				complaint_of_area.setEditable(true);
				on_examination_area.setEditable(true);
				filename_area.setEditable(false);

				back_button.setVisible(false);
				refresh_button.setVisible(false);

				prev_button.setVisible(false);
				next_button.setVisible(false);
				addFile_button.setVisible(true);
				cancelFile_button.setVisible(false);
				newcomplaint_button.setVisible(false);
				choose_button.setVisible(true);
				print_button.setVisible(false);
				patientBasicDataEdit_button.setVisible(false);

				patientComplaintEdit_button.setVisible(false);
				patientComplaintSave_button.setVisible(true);
				patientComplaintCancel_button.setVisible(true);
			}
		});

		patientComplaintSave_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if(validatePatientComplaint())
				{
					int response = connection.receiveFromServer(patientReport.patientBasicData.getId() + ".xml",Constants.dataPath + "tempPatientReport.xml");
					File file = new File(Constants.dataPath + "tempPatientReport.xml");
					if(response >= 0)
					{
						try
						{
							JAXBContext jc = JAXBContext.newInstance(PatientReport.class);
							Unmarshaller um = jc.createUnmarshaller();
							patientReport = (PatientReport)um.unmarshal(file);
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						file.delete();
					}
					else 
					{
						if(file.isFile())
							file.delete();
						if(response == -2)
							JOptionPane.showMessageDialog(jframe,"File does not exist");
						else
						{
							JOptionPane.showMessageDialog(jframe,RHErrors.getErrorDescription(response));
							new KioskLogin();
							dispose();
						}
					}

					Report report = patientReport.Reports.get(current_report_count);
					report.patientComplaint.setcomplaint(complaint_of_area.getText());
					report.patientComplaint.setPrevDiagnosis(prev_diagnosis_field.getText());
					report.patientComplaint.setWeight(weight_field.getText());
					report.patientComplaint.setBmi(bmi_field.getText());
					report.patientComplaint.setBp(bp_field.getText());
					report.patientComplaint.setPulse(pulse_field.getText());
					report.patientComplaint.setTemperature(temperature_field.getText());
					report.patientComplaint.setSpo2(spO2_field.getText());
					report.patientComplaint.setOtherResults(on_examination_area.getText());

					boolean FileSendingComplete = true;
					for(int i = 0;i < selectedFiles.size();i++)
					{
						if(connection.sendToServer(selectedFiles.get(i).getPath(),Constants.finalDataPath + selectedFiles.get(i).getName()) < 0)
						{
							FileSendingComplete = false;
							JOptionPane.showMessageDialog(jframe,"File upload failed : " + selectedFiles.get(i).getName());
						}
					}
					selectedFiles = null;
					selectedFiles = new ArrayList < File > ();

					if(FileSendingComplete)
						report.patientComplaint.setFileNames(filename_area.getText());

					patientReport.Reports.set(current_report_count,report);

					try
					{
						JAXBContext jc = JAXBContext.newInstance(PatientReport.class);
						Marshaller jm = jc.createMarshaller();
						jm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
						jm.marshal(patientReport,file);
					}
					catch(Exception e)
					{
						if(file.isFile())
							file.delete();
						e.printStackTrace();
					}
					int sendResponse;
					if((sendResponse = connection.sendToServer(Constants.dataPath + "tempPatientReport.xml",Constants.finalDataPath + reg_no_field.getText() + ".xml")) < 0)
					{
						JOptionPane.showMessageDialog(jframe,RHErrors.getErrorDescription(sendResponse));
						new KioskLogin();
						dispose();
					}
					file.delete();


					HealthInfoPanel.setBackground(Constants.JPANELCOLOR1);
					weight.setForeground(Color.BLACK);
					kg.setForeground(Color.BLACK);
					bmi.setForeground(Color.BLACK);
					bp.setForeground(Color.BLACK);
					pulse.setForeground(Color.BLACK);
					temperature.setForeground(Color.BLACK);
					celcius.setForeground(Color.BLACK);
					spO2.setForeground(Color.BLACK);
					percent.setForeground(Color.BLACK);

					prev_diagnosis.setForeground(Color.BLACK);
					complaint_of.setForeground(Color.BLACK);
					on_examination.setForeground(Color.BLACK);

					bmi_field.setEditable(false);
					bp_field.setEditable(false);
					weight_field.setEditable(false);
					pulse_field.setEditable(false);
					spO2_field.setEditable(false);
					temperature_field.setEditable(false);
					prev_diagnosis_field.setEditable(false);
					complaint_of_area.setEditable(false);
					on_examination_area.setEditable(false);

					back_button.setVisible(true);
					refresh_button.setVisible(true);

					prev_button.setVisible(true);
					next_button.setVisible(true);
					addFile_button.setVisible(false);
					newcomplaint_button.setVisible(true);
					choose_button.setVisible(false);
					print_button.setVisible(false);
					patientBasicDataEdit_button.setVisible(true);

					patientComplaintEdit_button.setVisible(true);
					patientComplaintSave_button.setVisible(false);
					patientComplaintCancel_button.setVisible(false);

					setReport(current_report_count);
				}
			}
		});

		patientComplaintCancel_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				HealthInfoPanel.setBackground(Constants.JPANELCOLOR1);
				weight.setForeground(Color.BLACK);
				kg.setForeground(Color.BLACK);
				bmi.setForeground(Color.BLACK);
				bp.setForeground(Color.BLACK);
				pulse.setForeground(Color.BLACK);
				temperature.setForeground(Color.BLACK);
				celcius.setForeground(Color.BLACK);
				spO2.setForeground(Color.BLACK);
				percent.setForeground(Color.BLACK);

				prev_diagnosis.setForeground(Color.BLACK);
				complaint_of.setForeground(Color.BLACK);
				on_examination.setForeground(Color.BLACK);

				bmi_field.setEditable(false);
				bp_field.setEditable(false);
				weight_field.setEditable(false);
				pulse_field.setEditable(false);
				spO2_field.setEditable(false);
				temperature_field.setEditable(false);
				prev_diagnosis_field.setEditable(false);
				complaint_of_area.setEditable(false);
				on_examination_area.setEditable(false);

				back_button.setVisible(true);
				refresh_button.setVisible(true);

				prev_button.setVisible(true);
				next_button.setVisible(true);
				addFile_button.setVisible(false);
				newcomplaint_button.setVisible(true);
				choose_button.setVisible(false);
				print_button.setVisible(false);
				patientBasicDataEdit_button.setVisible(true);

				patientComplaintEdit_button.setVisible(true);
				patientComplaintSave_button.setVisible(false);
				patientComplaintCancel_button.setVisible(false);

				selectedFiles = null;
				selectedFiles = new ArrayList < File > ();

				setReport(current_report_count);
			}
		});
		
		
	//add components
		add(changePicture_button);

		add(form_label);
		add(picture);
		add(reg_no);
		add(reg_no_field);
		add(status);
		add(status_field);
		add(date);
		add(date_field);
		add(emergency);
		add(emergency_box);
		add(anemia);
		add(anemia_box);
		add(edema);
		add(edema_box);
		add(jaundice);
		add(jaundice_box);
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
		add(filename_area);
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
		add(kiosk_coordinator_sign);
		add(kiosk_coordinator_date);
		add(kiosk_coordinator_date_field);
		add(doctor);
		add(doctor_name);
		add(doctor_name_field);
		add(doctor_sign);
		add(doctor_date);
		add(doctor_date_field);

		add(back_button);
		add(refresh_button);
		add(back2_button);
		add(next_button);
		add(prev_button);
		add(newcomplaint_button);
		add(addFile_button);
		add(cancelFile_button);
		add(submit_button);
		add(choose_button);
		add(print_button);
		add(patientBasicDataEdit_button);
		add(patientBasicDataSave_button);
		add(patientBasicDataCancel_button);
		add(patientComplaintEdit_button);
		add(patientComplaintSave_button);
		add(patientComplaintCancel_button);
		// getContentPane().add(address_pane);

	//initialize JScrollPane
		on_examination_pane = new JScrollPane(on_examination_area);
		filename_pane = new JScrollPane(filename_area);
		address_pane = new JScrollPane(address_area);
		family_history_pane = new JScrollPane(family_history_area);
		medical_history_pane = new JScrollPane(medical_history_area);
		complaint_of_pane = new JScrollPane(complaint_of_area);
		advice_pane = new JScrollPane(advice_area);
		medication_pane = new JScrollPane(medication_area);
		diagnostic_test_pane = new JScrollPane(diagnostic_test_area);
		provisional_diagnosis_pane = new JScrollPane(provisional_diagnosis_area);
		final_diagnosis_pane = new JScrollPane(final_diagnosis_area);
		referral_pane = new JScrollPane(referral_area);


   		address_pane.setBounds(640,120,150,80);
   		family_history_pane.setBounds(100,210,300,90);
   		medical_history_pane.setBounds(490,210,300,90);
   		complaint_of_pane.setBounds(50,370,310,140);
   		on_examination_pane.setBounds(500,370,290,70);
   		filename_pane.setBounds(500,450,290,90);

   		provisional_diagnosis_pane.setBounds(830,80,250,95);
   		final_diagnosis_pane.setBounds(1100,80,250,95);
   		advice_pane.setBounds(830,200,250,120);
   		medication_pane.setBounds(1100,200,250,120);
   		diagnostic_test_pane.setBounds(830,350,250,80);
   		referral_pane.setBounds(1100,350,250,80);


   	//add JScrollPane
		add(on_examination_pane);
		add(filename_pane);
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
		DoctorPrescriptionPanel.setBounds(810,55,1190,395);
		KioskCoordinatorPanel.setBounds(0,545,380,155);
		DoctorPanel.setBounds(380,545,430,155);
		ButtonPanel.setBounds(0,700,810,50);
		CommunicationPanel.setBounds(810,450,1190,800);

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
		setPatientReport();

	}

	private boolean validatePatientComplaint()
	{
		boolean weightcheck = !weight_field.getText().matches(".*[a-zA-Z]+.*");
		boolean bmicheck = !bmi_field.getText().matches(".*[a-zA-Z]+.*");
		boolean bpcheck = !bp_field.getText().matches(".*[a-zA-Z]+.*");
		boolean pulsecheck = !pulse_field.getText().matches(".*[a-zA-Z]+.*");
		boolean temperaturecheck = !temperature_field.getText().matches(".*[a-zA-Z]+.*");
		boolean spO2check = !spO2_field.getText().matches(".*[a-zA-Z]+.*");
		if(weightcheck)
			weight_field.setBorder(BorderFactory.createLineBorder(Color.black));
		else weight_field.setBorder(BorderFactory.createLineBorder(Color.red));
		if(bmicheck)
			bmi_field.setBorder(BorderFactory.createLineBorder(Color.black));
		else bmi_field.setBorder(BorderFactory.createLineBorder(Color.red));
		if(bpcheck)
			bp_field.setBorder(BorderFactory.createLineBorder(Color.black));
		else bp_field.setBorder(BorderFactory.createLineBorder(Color.red));
		if(pulsecheck)
			pulse_field.setBorder(BorderFactory.createLineBorder(Color.black));
		else pulse_field.setBorder(BorderFactory.createLineBorder(Color.red));
		if(temperaturecheck)
			temperature_field.setBorder(BorderFactory.createLineBorder(Color.black));
		else temperature_field.setBorder(BorderFactory.createLineBorder(Color.red));
		if(spO2check)
			spO2_field.setBorder(BorderFactory.createLineBorder(Color.black));
		else spO2_field.setBorder(BorderFactory.createLineBorder(Color.red));

		return (weightcheck & bmicheck & bpcheck & pulsecheck & temperaturecheck & spO2check);
	}

	private boolean validatePatientBasicData()
	{
		boolean nameCheck = !name_field.getText().matches(".*[0-9]+.*");
		boolean sdwCheck = !sdw_of_field.getText().matches(".*[0-9]+.*");
		boolean occupationCheck = !occupation_field.getText().matches(".*[0-9]+.*");
		boolean genderCheck = !gender_field.getText().matches(".*[0-9]+.*");
		boolean phoneCheck = !ph_no_field.getText().matches(".*[a-zA-Z]+.*");
		boolean ageCheck = !age_field.getText().matches(".*[a-zA-Z]+.*");
		boolean heightCheck = !height_field.getText().matches(".*[a-zA-Z]+.*");

		if(nameCheck)
			name_field.setBorder(BorderFactory.createLineBorder(Color.black));
		else
			name_field.setBorder(BorderFactory.createLineBorder(Color.red));
		if(sdwCheck)
			sdw_of_field.setBorder(BorderFactory.createLineBorder(Color.black));
		else
			sdw_of_field.setBorder(BorderFactory.createLineBorder(Color.red));
		if(occupationCheck)
			occupation_field.setBorder(BorderFactory.createLineBorder(Color.black));
		else
			occupation_field.setBorder(BorderFactory.createLineBorder(Color.red));
		if(genderCheck)
			gender_field.setBorder(BorderFactory.createLineBorder(Color.black));
		else
			gender_field.setBorder(BorderFactory.createLineBorder(Color.red));
		if(phoneCheck)
			ph_no_field.setBorder(BorderFactory.createLineBorder(Color.black));
		else
			ph_no_field.setBorder(BorderFactory.createLineBorder(Color.red));
		if(ageCheck)
			age_field.setBorder(BorderFactory.createLineBorder(Color.black));
		else
			age_field.setBorder(BorderFactory.createLineBorder(Color.red));
		if(heightCheck)
			height_field.setBorder(BorderFactory.createLineBorder(Color.black));
		else
			height_field.setBorder(BorderFactory.createLineBorder(Color.red));

		return (nameCheck & sdwCheck & occupationCheck & genderCheck & phoneCheck & ageCheck & heightCheck);
	}

	private  void encodeToString() {

		BufferedImage image = null;
        try
        {
            image = ImageIO.read(new File("patient_Picture.jpg"));
        }
        catch(IOException e)
        {

        }
        
        String type = "jpg";

        imageString = null;
        if(imageSet != 0)
        {
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();

	        try
	        {
	            ImageIO.write(image, type, bos);
	            byte[] imageBytes = bos.toByteArray();

	            BASE64Encoder encoder = new BASE64Encoder();
	            imageString = encoder.encode(imageBytes);

	            bos.close();
	        }
	        catch (IOException e)
	        {
	           e.printStackTrace();
	        }
	    }
	}

//neccessary methods

	private void SetPatientBasicDataEditable(boolean EDITABLE)
	{
		name_field.setEditable(EDITABLE);
		sdw_of_field.setEditable(EDITABLE);
		occupation_field.setEditable(EDITABLE);
		ph_no_field.setEditable(EDITABLE);
		address_area.setEditable(EDITABLE);
		age_field.setEditable(EDITABLE);
		height_field.setEditable(EDITABLE);
		gender_field.setEditable(EDITABLE);
		family_history_area.setEditable(EDITABLE);
		medical_history_area.setEditable(EDITABLE);
	}


	private BufferedImage decodeToImage(String imageString)
	{

        BufferedImage image = null;
        byte[] imageByte;
        try
        {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        return image;
    }

    private String CheckNullString(String str)
    {
    	if(str.equals(""))
    		return null;
    	else return str;
    }

	private void addComplaintToFile()
	{
		if(anemia_box.isSelected())
			on_examination_area.setText(on_examination_area.getText() + "Anemia\n");
		if(edema_box.isSelected())
			on_examination_area.setText(on_examination_area.getText() + "Edema\n");
		if(jaundice_box.isSelected())
			on_examination_area.setText(on_examination_area.getText() + "Jaundice\n");
		PatientComplaint patientComplaint = new PatientComplaint();
		patientComplaint.complaint = CheckNullString(complaint_of_area.getText());
		patientComplaint.complaint_date = CheckNullString(kiosk_coordinator_date_field.getText());
		patientComplaint.Weight = CheckNullString(weight_field.getText());
		patientComplaint.Bmi = CheckNullString(bmi_field.getText());
		patientComplaint.Bp = CheckNullString(bp_field.getText());
		patientComplaint.Spo2 = CheckNullString(spO2_field.getText());
		patientComplaint.Pulse = CheckNullString(pulse_field.getText());
		patientComplaint.Temperature = CheckNullString(temperature_field.getText());
		patientComplaint.OtherResults = CheckNullString(on_examination_area.getText());
		patientComplaint.KioskCoordinatorName = CheckNullString(kiosk_coordinator_name_field.getText());
		patientComplaint.PrevDiagnosis = CheckNullString(prev_diagnosis_field.getText());

		boolean FileSendingComplete = true;
		for(int i = 0;i < selectedFiles.size();i++)
		{
			try
			{
				Thread.sleep(2000);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			if(connection.sendToServer(selectedFiles.get(i).getPath(),Constants.finalDataPath + selectedFiles.get(i).getName()) < 0)
			{
				FileSendingComplete = false;
				JOptionPane.showMessageDialog(this,"File upload failed : " + selectedFiles.get(i).getName());
			}
			else System.out.println("File sent: " + selectedFiles.get(i).getPath());
		}
		selectedFiles = null;
		selectedFiles = new ArrayList < File > ();

		if(FileSendingComplete)
			patientComplaint.FileNames = CheckNullString(filename_area.getText());

		Report report = new Report();
		report.patientComplaint = patientComplaint;

		patientReport.Reports.add(report);
		patientReport.patientBasicData.setStatus(status_field.getText());

		try
		{
			JAXBContext jc = JAXBContext.newInstance(PatientReport.class);
			Marshaller jm = jc.createMarshaller();
			jm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
			jm.marshal(patientReport,new File(Constants.dataPath + "tempPatientReport.xml"));
			Thread.sleep(2000);

			int sendResponse;
			if((sendResponse = connection.sendToServer(Constants.dataPath + "tempPatientReport.xml",Constants.finalDataPath + reg_no_field.getText() + ".xml")) < 0)
			{
				JOptionPane.showMessageDialog(this,RHErrors.getErrorDescription(sendResponse));
				(new File(Constants.dataPath + "tempPatientReport.xml")).delete();
				new PatientLogin(connection,employee);
				dispose();
			}
			else
				(new File(Constants.dataPath + "tempPatientReport.xml")).delete();
		}
		catch(Exception jaxbe)
		{
			jaxbe.printStackTrace();
			(new File(Constants.dataPath + "tempPatientReport.xml")).delete();
		}
	}

	private void getPatientComplaint()
	throws IOException
	{
		Process ps = Runtime.getRuntime().exec("java "/*-cp PatientApp.jar*/ + "projecttrialv5.PatientBasicInfo");
		try
		{
			ps.waitFor();
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}
		InputStream in = ps.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String str;
        String problem = new String();
        try
        {
	        while((str = br.readLine()) != null)
	        {
	        	String str2 = str;
	        	if(str2.startsWith("Killed child window for") || str2.startsWith("File successfully dumped for patient") || str.equals("All DONE!!! EXITING !!!"))
	        		break;
	        	problem = problem + str + "\n";
	        }
	    }
	    catch(IOException ioe)
	    {
	    	ioe.printStackTrace();
	    }
    	complaint_of_area.setText(complaint_of_area.getText() + problem);
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
			filename_area.setText(report.patientComplaint.getFileNames());
			complaint_of_area.setText(report.patientComplaint.getcomplaint());
			kiosk_coordinator_name_field.setText(report.patientComplaint.getKioskCoordinatorName());
			kiosk_coordinator_date_field.setText(report.patientComplaint.getcomplaint_date());

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
			print_button.setVisible(false);
			patientComplaintEdit_button.setVisible(true);
		}
		else
		{
			print_button.setVisible(true);
			patientComplaintEdit_button.setVisible(false);
		}
	}

	private boolean update_log()
	{
		int response = connection.receiveFromServer("Patient_" + Constants.kioskNo + "_Log.xml",Constants.dataPath + "log.xml");
		File file = new File(Constants.dataPath + "log.xml");
		if(response  >= 0)
		{
			try
			{
				JAXBContext jc = JAXBContext.newInstance(PatientLog.class);
				Unmarshaller um = jc.createUnmarshaller();
				PatientLog patientLog = (PatientLog)um.unmarshal(file);

				if(emergency_box.isSelected())
					patientLog.Emergency.add(reg_no_field.getText());
				else patientLog.Normal.add(reg_no_field.getText());

				Marshaller jm = jc.createMarshaller();
				jm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
				jm.marshal(patientLog,file);
				if(connection.sendToServer(Constants.dataPath + "log.xml",Constants.finalDataPath + "Patient_" + Constants.kioskNo + "_Log.xml") < 0)
				{
					file.delete();
					JOptionPane.showMessageDialog(this,networkErrorMessage);
					return false;
				}
				file.delete();
				return true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
			
		}
		else
		{
			if(file.isFile())
				file.delete();
			JOptionPane.showMessageDialog(this,RHErrors.getErrorDescription(response));
			return false;
		}
	}

	private void getPatientReport(String PatientId)
	{
		int response = connection.receiveFromServer(PatientId + ".xml",Constants.dataPath + "tempPatientReport.xml");
		File file = new File(Constants.dataPath + "tempPatientReport.xml");
		if(response >= 0)
		{
			try
			{
				JAXBContext jc = JAXBContext.newInstance(PatientReport.class);
				Unmarshaller um = jc.createUnmarshaller();
				patientReport = (PatientReport)um.unmarshal(file);
			}
			catch(Exception e)
			{
				e.printStackTrace();
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
				new PatientLogin(connection,employee);
				dispose();
			}
		}
	}

	private void setPatientReport()
	{
		try
		{
			reg_no_field.setText(patientReport.patientBasicData.getId());
			name_field.setText(patientReport.patientBasicData.getName());
			sdw_of_field.setText(patientReport.patientBasicData.getReference());
			occupation_field.setText(patientReport.patientBasicData.getOccupation());

			//image
			if(patientReport.patientBasicData.getImage() != null && patientReport.patientBasicData.getImage() != "")
			{
				imgstr = patientReport.patientBasicData.getImage();
				BufferedImage newImg;
				newImg = this.decodeToImage(imgstr);
				(new File(Constants.dataPath + "patient_Picture.jpg")).createNewFile();
	    		ImageIO.write(newImg, "jpg", new File(Constants.dataPath + "patient_Picture.jpg"));
	    		ImageIcon imageIcon = new ImageIcon(Constants.dataPath + "patient_Picture.jpg"); // load the image to a imageIcon
				int h = picture.getHeight();
				int w = picture.getWidth();
				Image image = imageIcon.getImage(); // transform it 
				Image newimg = image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
				imageIcon = new ImageIcon(newimg);
				picture.setIcon(imageIcon);
				(new File(Constants.dataPath + "patient_Picture.jpg")).delete();
			}

			status_field.setText(patientReport.patientBasicData.getStatus());
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
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	private void newComplaintAction()
	{
		int size = patientReport.Reports.size();
		if(size != 0)
		{
			Report report = patientReport.Reports.get(size-1);
			weight_field.setText(report.patientComplaint.getWeight());
			bmi_field.setText(report.patientComplaint.getBmi());
			bp_field.setText(report.patientComplaint.getBp());
			pulse_field.setText(report.patientComplaint.getPulse());
			temperature_field.setText(report.patientComplaint.getTemperature());
			spO2_field.setText(report.patientComplaint.getSpo2());
		}

		provisional_diagnosis_area.setText("");
		final_diagnosis_area.setText("");
		advice_area.setText("");
		medication_area.setText("");
		diagnostic_test_area.setText("");
		referral_area.setText("");
		doctor_name_field.setText("");
		doctor_date_field.setText("");

		
		prev_diagnosis_field.setText("");
		complaint_of_area.setText("");
		on_examination_area.setText("");
		filename_area.setText("");

		bmi_field.setEditable(true);
		bp_field.setEditable(true);
		weight_field.setEditable(true);
		pulse_field.setEditable(true);
		spO2_field.setEditable(true);
		temperature_field.setEditable(true);
		prev_diagnosis_field.setEditable(true);
		complaint_of_area.setEditable(true);
		on_examination_area.setEditable(true);
		filename_area.setEditable(false);

		HealthInfoPanel.setBackground(Color.GREEN.darker().darker());
		weight.setForeground(Color.WHITE);
		kg.setForeground(Color.WHITE);
		bmi.setForeground(Color.WHITE);
		bp.setForeground(Color.WHITE);
		pulse.setForeground(Color.WHITE);
		temperature.setForeground(Color.WHITE);
		celcius.setForeground(Color.WHITE);
		spO2.setForeground(Color.WHITE);
		percent.setForeground(Color.WHITE);
		emergency.setForeground(Color.WHITE);
		emergency_box.setForeground(Color.WHITE);
		anemia.setForeground(Color.WHITE);
		anemia_box.setForeground(Color.WHITE);
		edema.setForeground(Color.WHITE);
		edema_box.setForeground(Color.WHITE);
		jaundice.setForeground(Color.WHITE);
		jaundice_box.setForeground(Color.WHITE);


		prev_diagnosis.setForeground(Color.WHITE);
		complaint_of.setForeground(Color.WHITE);
		on_examination.setForeground(Color.WHITE);


		kiosk_coordinator_name_field.setText(employee.getName());

		SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy");
		kiosk_coordinator_date_field.setText(d.format(new Date()));


		back_button.setVisible(false);
		refresh_button.setVisible(false);
		patientComplaintEdit_button.setVisible(false);
		patientBasicDataEdit_button.setVisible(false);
		emergency.setVisible(true);
		anemia.setVisible(true);
		anemia_box.setVisible(true);
		edema.setVisible(true);
		edema_box.setVisible(true);
		jaundice.setVisible(true);
		jaundice_box.setVisible(true);
		emergency_box.setVisible(true);

		back2_button.setVisible(true);
		prev_button.setVisible(false);
		next_button.setVisible(false);
		submit_button.setVisible(true);
		addFile_button.setVisible(true);
		cancelFile_button.setVisible(true);
		newcomplaint_button.setVisible(false);
		choose_button.setVisible(true);
		print_button.setVisible(false);
	}

/**********************************************************************************************************************************/
/******************************************************VIDEO CHAT******************************************************************/
/**********************************************************************************************************************************/
/*

	private void videocamInitialize()
	{
		JFrame jframe = this;
		try
        {   
        	socket = new Socket(InetAddress.getByName(Constants.SERVER),Constants.PORT);
            bin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pwriter = new PrintWriter(socket.getOutputStream(),true);
            pwriter.println("Kiosk_" + Constants.kioskNo);
            pwriter.println("VIDEO_CLIENT");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        videocam_label = new JLabel();
        videocam_label.setBounds(830,470,380,260);
        // videocam_label.setBackground(Color.RED);
        // videocam_label.setForeground(Color.RED);
        videocam_label.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        callend_button = new JButton("End call");
        callend_button.setBounds(1220,700,100,25);
        callend_button.setVisible(false);

        callend_button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                si.running = false;
                si = null;
                pwriter.println("CALL_END");
                try
                {
                	Thread.sleep(100);
                }
                catch(Exception e)
                {
                	e.printStackTrace();
                }
                videocam_label.setIcon(null);
                setEngageMode(false);
            }
        });


        add(videocam_label);
       	add(callend_button);

        ri = new RecieveImage();
        ri.running = true;
        ri.start();
	}

    private void setEngageMode(boolean ENGAGE)
    {
        callend_button.setVisible(ENGAGE);
        ri.engage = ENGAGE;
        if(ENGAGE)
        {
            webcam  =  Webcam.getDefault();
            webcam.setViewSize(WebcamResolution.VGA.getSize());
            Dimension size  =  WebcamResolution.VGA.getSize();
            webcamPanel = null;
            webcamPanel  =  new WebcamPanel(webcam);
            webcam.open();
            
            webcamPanel.setFillArea(true);
            
            webcamPanel.setBounds(1220, 580, 100, 100);
            webcamPanel.setMirrored(true);
            webcamPanel.setPreferredSize(size);
            add(webcamPanel);
        }
        else
        {
           webcam.close();
           WebcamDiscoveryService webcamDiscoveryService = Webcam.getDiscoveryService();
           webcamDiscoveryService.stop();
        }
        webcamPanel.setVisible(ENGAGE);
    }

	class SendImage extends Thread
    {
        Thread t = this;
        boolean running = false;
        public void run()
        {
            try
            {
                while(running)
                {
                    pwriter.println(getimage());
                    pwriter.println("END_OF_IMAGE");
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        private  String getimage()
        {
            try
            {
                // webcam.open();
                BufferedImage image = webcam.getImage();
                String imageString  =  null;
                ByteArrayOutputStream bos  =  new ByteArrayOutputStream();
            
                ImageIO.write(image, "jpg", bos);
                byte[] imageBytes  =  bos.toByteArray();

                BASE64Encoder encoder  =  new BASE64Encoder();
                imageString  =  encoder.encode(imageBytes);

                bos.close();
                return imageString;
            }
            catch (Exception e)
            {
               e.printStackTrace();
               return null;
            }
        }
    }

    class RecieveImage extends Thread
    {
        public boolean running = true,engage = false;
        Thread t = this;


        public void run()
        {
            try
            {
                while(running)
                {
                    String str = "";
                    str = bin.readLine();
                    if(str.equals("INCOMING") && !engage)
                    {
                        if(JOptionPane.showConfirmDialog(videocam_label,"Incoming call! Answer?") =  = JOptionPane.YES_OPTION)
                        {
                            pwriter.println("ACCEPT_CALL");
                            setEngageMode(true);
                            si = new SendImage();
                            si.running = true;
                            si.start();
                            String str2 = "",img = "";
                            while(engage)
                            {
                                try
                                {
                                    str2 = bin.readLine();
                                    if(str2.equals("END_OF_IMAGE"))
                                    {
                                        setImage(img);
                                        img = "";
                                    }
                                    else if(str2.equals("CALL_END"))
                                    {
                                        if(si! = null)
                                        {
                                            si.running = false;
                                            si = null;
                                        }
                                        setEngageMode(false);
                                        videocam_label.setIcon(null);
                                        System.out.println(Thread.currentThread().getId() + " recieved : " + str2);
                                    }
                                    else img +  = str2;
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        }
                        else pwriter.println("DECLINE_CALL");
                        System.out.println(Thread.currentThread().getId() + " recieved : " + str);
                    }
                    else if(str.equals("ACCEPT_CALL"))
                    {
                        setEngageMode(true);
                        si = new SendImage();
                        si.running = true;
                        si.start();
                        // img = "";
                        System.out.println(Thread.currentThread().getId() + " recieved : " + str);
                        String img = "",str2 = "";
                        
                        while(engage)
                        {
                            try
                            {
                                str2 = bin.readLine();
                                if(str2.equals("END_OF_IMAGE"))
                                {
                                    setImage(img);
                                    img = "";
                                }
                                else if(str2.equals("CALL_END"))
                                {
                                    if(si! = null)
                                    {
                                        si.running = false;
                                        si = null;
                                    }
                                    setEngageMode(false);
                                    videocam_label.setIcon(null);
                                    System.out.println(Thread.currentThread().getId() + " recieved : " + str2);
                                }
                                else img +  = str2;
                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                    else if(str.equals("DECLINE_CALL"))
                    {
                        setEngageMode(false);
                        System.out.println(Thread.currentThread().getId() + " recieved : " + str);
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        public void setImage(String imageString)
        {

            BufferedImage bufferedemage  =  null;
            byte[] imageByte;
            try
            {
                BASE64Decoder decoder  =  new BASE64Decoder();
                imageByte  =  decoder.decodeBuffer(imageString);
                ByteArrayInputStream bis  =  new ByteArrayInputStream(imageByte);
                bufferedemage  =  ImageIO.read(bis);
                bis.close();
                
                // (new File(Constants.dataPath + "patient_Picture.jpg")).createNewFile();
                // ImageIO.write(bufferedemage, "jpg", new File(Constants.dataPath + "patient_Picture.jpg"));
                // ImageIcon imageIcon  =  new ImageIcon(Constants.dataPath + "patient_Picture.jpg"); // load the image to a imageIcon
                int h = videocam_label.getHeight();
                int w = videocam_label.getWidth();
                Image image  =  Toolkit.getDefaultToolkit().createImage(bufferedemage.getSource());
                Image newimg  =  image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
                ImageIcon imageIcon  =  new ImageIcon(newimg);
                videocam_label.setIcon(imageIcon);
                // (new File(Constants.dataPath + "patient_Picture.jpg")).delete();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }*/


/**********************************************************************************************************************************/
/****************************************************VIDEO CHAT END****************************************************************/
/**********************************************************************************************************************************/
}


class Information
{
	public String date,doctor_name,doctor_degree,doctor_hospital;
	public String patient_regno,patient_name,patient_sdw,patient_address,patient_age,patient_gender,patient_phone;
	public String complaint,provisional_diagnosis,final_diagnosis;
	public String doctor_advice,doctor_medication,doctor_diagnostic,doctor_referal;
	public String kiosk_coordinator_name;
	public Image patient_image;
}

class Printer implements Printable
{
	Component comp;

	public Printer(Component comp)
	{
		this.comp = comp;
	}

	@Override
	public int print(Graphics g,PageFormat format,int page_index)
	throws PrinterException
	{
		if(page_index > 0)
			return Printable.NO_SUCH_PAGE;

		Dimension dim = comp.getSize();
		double cheight = dim.getHeight();
		double cwidth = dim.getWidth();

		double pheight = format.getImageableHeight();
		double pwidth = format.getImageableWidth();

		double Xstart = format.getImageableX();
		double Ystart = format.getImageableY();

		double Xratio = pwidth/cwidth;
		double Yratio = pheight/cheight;

		Graphics2D g2 = (Graphics2D)g;
		g2.translate(Xstart,Ystart);
		g2.scale(Xratio,Yratio);
		comp.paint(g2);
		return Printable.PAGE_EXISTS;
	}
}

class Prescription_applet extends JFrame
{
	public JLabel form_label,patient_picture_label, date1, date2, regno,regno_label,name1, name2,sdw_of_label,age1, age2, years, gender1, gender2, ph_no1, ph_no2, address, complaint1, advice, medication, diagnostic_test, provisional_diagnosis, referral, final_diagnosis, kiosk_coordinator,kiosk_coordinator_name1, kiosk_coordinator_name2, kiosk_coordinator_sign, kiosk_coordinator_date, doctor_name1, doctor_name2,doctor_sign,degree,hospital,doctor_ph_no1,doctor_ph_no2;
	
	JTextArea address_area, complaint2,  family_history_area, medical_history_area, complaint_of_area,  on_examination_area, advice_area, medication_area, diagnostic_test_area, provisional_diagnosis_area, final_diagnosis_area, referral_area;
	
	JPanel JPANEL1,JPANEL3,JPANEL4,JPANEL5,JPANEL6;
	Font font = new Font("Serif",Font.BOLD,12);
	String confirmMessage = "Are you sure?";
	
	
	public Prescription_applet(Information info)
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
		jframe.setBounds(200,0,595,846 + 190);
		setLayout(null);

		setBackground(Color.white);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{
				if(JOptionPane.showConfirmDialog(jframe,confirmMessage) == JOptionPane.OK_OPTION)
				{
					dispose();
				}
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
       	kiosk_coordinator_sign = new JLabel("Signature :");   				
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
			(new File(Constants.dataPath + "patient_Picture.jpg")).createNewFile();
			ImageIO.write(newImg, "jpg", new File(Constants.dataPath + "patient_Picture.jpg"));
			ImageIcon imageIcon = new ImageIcon(Constants.dataPath + "patient_Picture.jpg"); // load the image to a imageIcon
			int h = patient_picture_label.getHeight();
			int w = patient_picture_label.getWidth();
			Image image = imageIcon.getImage(); // transform it 
			Image newimg = image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			imageIcon = new ImageIcon(newimg);
			patient_picture_label.setIcon(imageIcon);
			(new File(Constants.dataPath + "patient_Picture.jpg")).delete();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}

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
		int ComplaintAreaY = ComplaintLabelY + ComplaintLabelH + 5;
		int ComplaintAreaH = getHeight(complaint2.getText(),LEFTSIDECOLUMN);

		int ProvisionalDiagnosisLabelY = ComplaintAreaY + ComplaintAreaH + 5;
		int ProvisionalDiagnosisLabelH = 15;
		int ProvisionalDiagnosisAreaY = ProvisionalDiagnosisLabelY + ProvisionalDiagnosisLabelH + 5;
		int ProvisionalDiagnosisAreaH = getHeight(provisional_diagnosis_area.getText(),LEFTSIDECOLUMN);

		if(isNullString(provisional_diagnosis_area.getText()))
		{
			provisional_diagnosis.setVisible(false);
			provisional_diagnosis_area.setVisible(false);

			ProvisionalDiagnosisLabelH = 0;
			ProvisionalDiagnosisAreaY = ProvisionalDiagnosisLabelY + ProvisionalDiagnosisLabelH + 5;
			ProvisionalDiagnosisAreaH = -10;
		}

		int FinalDiagnosisLabelY = ProvisionalDiagnosisAreaY + ProvisionalDiagnosisAreaH + 5;
		int FinalDiagnosisLabelH = 15;
		int FinalDiagnosisAreaY = FinalDiagnosisLabelY + FinalDiagnosisLabelH + 5;
		int FinalDiagnosisAreaH = getHeight(final_diagnosis_area.getText(),LEFTSIDECOLUMN);

		int ReferralLabelY = FinalDiagnosisAreaY + FinalDiagnosisAreaH + 5;
		int ReferralLabelH = 15;
		int ReferralAreaY = ReferralLabelY + ReferralLabelH + 5;
		int ReferralAreaH = getHeight(referral_area.getText(),LEFTSIDECOLUMN);
		int ReferralX = LEFTSIDEX;
		int ReferralW = LEFTSIDEW;

		if(isNullString(referral_area.getText()))
		{
			referral.setVisible(false);
			referral_area.setVisible(false);

			ReferralLabelH = 0;
			ReferralAreaY = ReferralLabelY + ReferralLabelH + 5;
			ReferralAreaH = -10;
		}

		int AdviceLabelY = 160;
		int AdviceLabelH = 15;
		int AdviceAreaY = AdviceLabelY + AdviceLabelH + 5;
		int AdviceAreaH = getHeight(advice_area.getText(),RIGHTSIDECOLUMN);

		if(isNullString(advice_area.getText()))
		{
			advice.setVisible(false);
			advice_area.setVisible(false);

			AdviceLabelH = 0;
			AdviceAreaY = AdviceLabelY + AdviceLabelH + 5;
			AdviceAreaH = -10;
		}

		int MedicationLabelY = AdviceAreaY + AdviceAreaH + 5;
		int MedicationLabelH = 15;
		int MedicationAreaY = MedicationLabelY + MedicationLabelH + 5;
		int MedicationAreaH = getHeight(medication_area.getText(),RIGHTSIDECOLUMN);

		int DiagnosticLabelY = MedicationAreaY + MedicationAreaH + 5;
		int DiagnosticLabelH = 15;
		int DiagnosticAreaY = DiagnosticLabelY + DiagnosticLabelH + 5;
		int DiagnosticAreaH = getHeight(info.doctor_diagnostic,RIGHTSIDECOLUMN);
		int DiagnosticX = RIGHTSIDEX;
		int DiagnosticW = RIGHTSIDEW;

		if(isNullString(diagnostic_test_area.getText()))
		{
			diagnostic_test.setVisible(false);
			diagnostic_test_area.setVisible(false);

			DiagnosticLabelH = 0;
			DiagnosticAreaY = DiagnosticLabelY + DiagnosticLabelH + 5;
			DiagnosticAreaH = 0;
		}

		if(DiagnosticAreaY + DiagnosticAreaH + 5 > ReferralAreaY + ReferralAreaH + DiagnosticLabelH + DiagnosticAreaH)
		{
			DiagnosticLabelY = ReferralAreaY + ReferralAreaH + 5;
			DiagnosticAreaY = DiagnosticLabelY + DiagnosticLabelH + 5;

			DiagnosticX = LEFTSIDEX;
			DiagnosticW = LEFTSIDEW;
		}
		else if(ReferralAreaY + ReferralAreaH + 5 > DiagnosticAreaY + DiagnosticAreaH + ReferralLabelH + ReferralAreaH)
		{
			ReferralLabelY = DiagnosticAreaY + DiagnosticAreaH + 5;
			ReferralAreaY = ReferralLabelY + ReferralLabelH + 5;

			ReferralX = RIGHTSIDEX;
			ReferralW = RIGHTSIDEW;
		}

		int[] data = {JPANEL6Y,DiagnosticAreaY + DiagnosticAreaH,ReferralAreaY + ReferralAreaH,MedicationAreaY + MedicationAreaH,FinalDiagnosisAreaY + FinalDiagnosisAreaH};
		Arrays.sort(data);
		JPANEL6Y = data[4] + 10;

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
        add(kiosk_coordinator_sign);

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
		
		doctor_name1.setBounds(280,JPANEL6Y + 10,25,15);
		doctor_name2.setBounds(305,JPANEL6Y + 10,200,15);
		degree.setBounds(280,JPANEL6Y + 30,250,15);
		// hospital.setBounds(10,80,250,15);
		// doctor_ph_no1.setBounds(280,40,50,15);
		// doctor_ph_no2.setBounds(330,40,100,15);	
		doctor_sign.setBounds(280,JPANEL6Y + 50,100,30);

		//KIOSK COORDINATOR
		kiosk_coordinator_name1.setBounds(10,JPANEL6Y + 10,200,15);
		kiosk_coordinator_name2.setBounds(10,JPANEL6Y + 30,200,15);
		kiosk_coordinator_sign.setBounds(10,JPANEL6Y + 50,100,30);
	       
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

		jframe.setSize(595,JPANEL6Y + 150);
		// jframe.pack();

		// kiosk_coordinator_name1.setBounds(10,10,100,25);

		try
		{
			PrinterJob pjob = PrinterJob.getPrinterJob();
			PageFormat preformat = pjob.defaultPage();
			preformat.setOrientation(PageFormat.LANDSCAPE);
			PageFormat postformat = pjob.pageDialog(preformat);
			//If user does not hit cancel then print.
			if (preformat != postformat) {
			    //Set print component
			    pjob.setPrintable(new Printer(this), postformat);
			    if (pjob.printDialog()) {
			        pjob.print();
			    }
			}
		}
		catch(PrinterException pe)
		{
			pe.printStackTrace();
		}
		dispose();
	}

	private int getHeight(String str,int width)
	{
		String arr[] = str.split("\n");
		for(int i = 0;i < arr.length;i++)
		{
			int extraLine = arr[i].length()/width;
			for(int j = 0;j < extraLine;j++)
				str = str + "\nab";
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


@XmlRootElement
@XmlType(propOrder = {"Emergency","Normal"})
class PatientLog
{
	@XmlElement
	ArrayList < String >  Emergency = new ArrayList < String > ();
	@XmlElement
	ArrayList < String >  Normal = new  ArrayList < String > ();

	ArrayList < String >  getnormal()
	{
		return Normal;
	}

	void setnormal(ArrayList < String >  normal)
	{
		Normal = normal;
	}

	ArrayList < String >  getemergency()
	{
		return Emergency;
	}
	void setemergency(ArrayList < String >  emergency)
	{
		Emergency = emergency;
	}
}