package patientside;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import sun.awt.image.ToolkitImage;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;



/**
* GeneralPrescription class, creates frame for general prescription view
* @author Sushanto Halder
*/
public class GeneralPrescription
{
	JFrame generalPrescriptionFrame;
	private JLabel form_label,patient_picture_label, date1, date2, regno,regno_label,name1, name2,sdw_of_label,age1, age2, years, gender1, gender2, ph_no1, ph_no2, address, complaint1, advice, medication, diagnostic_test, provisional_diagnosis, referral, final_diagnosis, kiosk_coordinator,kiosk_coordinator_name1, kiosk_coordinator_name2,  kiosk_coordinator_date, doctor_name1, doctor_name2,doctor_sign,degree,hospital,doctor_ph_no1,doctor_ph_no2;
	
	private JTextArea address_area, complaint2,  family_history_area, medical_history_area, complaint_of_area,  on_examination_area, advice_area, medication_area, diagnostic_test_area, provisional_diagnosis_area, final_diagnosis_area, referral_area;
	
	private JPanel JPANEL1,JPANEL3,JPANEL4,JPANEL5,JPANEL6;
	private Font font = new Font("Serif",Font.BOLD,12);
	private String confirmMessage = "Are you sure?";
	
	/**
	* Creates the GUI
	* @param info PrescriptionInformation object
	*/
	protected GeneralPrescription(PrescriptionInformation info)
	{
		generalPrescriptionFrame = new JFrame();
		final JFrame jframe = generalPrescriptionFrame;
		generalPrescriptionFrame.setVisible(true);
		generalPrescriptionFrame.setSize(595,852);
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
		generalPrescriptionFrame.setBounds(200,10,595,846+190);
		generalPrescriptionFrame.setLayout(null);

		generalPrescriptionFrame.setBackground(Color.white);
		generalPrescriptionFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		generalPrescriptionFrame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{
				generalPrescriptionFrame.dispose();
			}
		});
        
        
        /*
        * Initialize labels and buttons
        */
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


        /*
        * Set background
        */
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

		/*
		* Set non editable
		*/
		address_area.setEditable(false);
		complaint2.setEditable(false);
		provisional_diagnosis_area.setEditable(false);
		final_diagnosis_area.setEditable(false);
		advice_area.setEditable(false);
		medication_area.setEditable(false);
		referral_area.setEditable(false);	  
		diagnostic_test_area.setEditable(false);

		/*
		* Set linewrap
		*/
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

		/**
		* scale and set picture
		*/
		try
		{
			BufferedImage newImg = ((ToolkitImage)(info.patient_image)).getBufferedImage();
			(new File(Constants.dataPath + "patient_Picture.jpg")).createNewFile();
			ImageIO.write(newImg, "jpg", new File(Constants.dataPath + "patient_Picture.jpg"));
			ImageIcon imageIcon  =  new ImageIcon(Constants.dataPath + "patient_Picture.jpg"); // load the image to a imageIcon
			int h = patient_picture_label.getHeight();
			int w = patient_picture_label.getWidth();
			Image image  =  imageIcon.getImage(); // transform it 
			Image newimg  =  image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			imageIcon  =  new ImageIcon(newimg);
			patient_picture_label.setIcon(imageIcon);
			(new File(Constants.dataPath + "patient_Picture.jpg")).delete();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			patient_picture_label.setText("No Image");
		}
		catch(NullPointerException npe)
		{
			npe.printStackTrace();
			patient_picture_label.setText("No Image");
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

		/*
		* Compute bounds of text areas to auto resize prescription
		*/

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
		

		generalPrescriptionFrame.add(patient_picture_label);
		generalPrescriptionFrame.add(form_label);
		generalPrescriptionFrame.add(date1);
		generalPrescriptionFrame.add(date2);
		generalPrescriptionFrame.add(doctor_name1);
		generalPrescriptionFrame.add(doctor_name2);
		generalPrescriptionFrame.add(doctor_sign);
		generalPrescriptionFrame.add(degree);
		generalPrescriptionFrame.add(hospital);
		generalPrescriptionFrame.add(doctor_ph_no1);
		generalPrescriptionFrame.add(doctor_ph_no2);
		generalPrescriptionFrame.add(regno);
		generalPrescriptionFrame.add(regno_label);
        generalPrescriptionFrame.add(name1);
        generalPrescriptionFrame.add(name2);
        generalPrescriptionFrame.add(sdw_of_label);
        generalPrescriptionFrame.add(age1);
        generalPrescriptionFrame.add(age2);
        generalPrescriptionFrame.add(years);
        generalPrescriptionFrame.add(gender1);
        generalPrescriptionFrame.add(gender2);
        generalPrescriptionFrame.add(ph_no1);
        generalPrescriptionFrame.add(ph_no2);
        generalPrescriptionFrame.add(address);
        generalPrescriptionFrame.add(address_area);
        generalPrescriptionFrame.add(complaint1);
        generalPrescriptionFrame.add(complaint2);
        generalPrescriptionFrame.add(provisional_diagnosis);
        generalPrescriptionFrame.add(provisional_diagnosis_area);
        generalPrescriptionFrame.add(final_diagnosis);
        generalPrescriptionFrame.add(final_diagnosis_area);
        generalPrescriptionFrame.add(advice);
        generalPrescriptionFrame.add(advice_area);
        generalPrescriptionFrame.add(medication);
        generalPrescriptionFrame.add(medication_area);
        generalPrescriptionFrame.add(referral);
        generalPrescriptionFrame.add(referral_area);
        generalPrescriptionFrame.add(diagnostic_test);
        generalPrescriptionFrame.add(diagnostic_test_area);
        generalPrescriptionFrame.add(kiosk_coordinator_name1);
        generalPrescriptionFrame.add(kiosk_coordinator_name2);

        //you dont have to touch generalPrescriptionFrame one
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

		generalPrescriptionFrame.add(JPANEL6);
		generalPrescriptionFrame.add(JPANEL5);
		generalPrescriptionFrame.add(JPANEL4);
		generalPrescriptionFrame.add(JPANEL3);
		// generalPrescriptionFrame.add(JPANEL2);
		generalPrescriptionFrame.add(JPANEL1);

		jframe.setSize(595,JPANEL6Y+150);

		try
		{
			PrinterJob pjob = PrinterJob.getPrinterJob();
			PageFormat preformat = pjob.defaultPage();
			preformat.setOrientation(PageFormat.LANDSCAPE);
			PageFormat postformat = pjob.pageDialog(preformat);
			//If user does not hit cancel then print.
			if (preformat != postformat) {
			    //Set print component
			    pjob.setPrintable(new Printer(generalPrescriptionFrame), postformat);
			    if (pjob.printDialog()) {
			        pjob.print();
			    }
			}
		}
		catch(PrinterException pe)
		{
			pe.printStackTrace();
		}
		generalPrescriptionFrame.dispose();
	}

	/**
	* Compute minimum height for a string to fit in a fixed width
	* @param str String of the textarea
	* @param width width of textarea
	* @return Minimum height needs to fit the string
	*/
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

	/**
	* Checks if a string is null or not
	* @param str String to be checked
	* @return if null, true, else false
	*/
	private boolean isNullString(String str)
    {
    	if(str.equals(""))
    		return true;
    	else return false;
    }
}