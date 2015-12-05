
package patientside;
import javax.swing.SwingUtilities;

import java.io.*;



public class test
{
	public static void main(String args[])
	{
		new test();
	}


	/**
	* Set look and feel
	*/
	public test()
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
            		java.util.logging.Logger.getLogger(KioskInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (InstantiationException ex)
        		{
            		java.util.logging.Logger.getLogger(KioskInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (IllegalAccessException ex)
        		{
            		java.util.logging.Logger.getLogger(KioskInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (javax.swing.UnsupportedLookAndFeelException ex)
        		{
            		java.util.logging.Logger.getLogger(KioskInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}


        		PrescriptionInformation pi = new PrescriptionInformation();

        		pi.date = "date"; pi.doctor_name = "name"; pi.doctor_degree = "gree"; pi.doctor_hospital = "ital";

        		pi.kiosk_coordinator_name = "name";
        		pi.patient_regno = "egno"; pi.patient_name = "name"; pi.patient_sdw = "_sdw"; pi.patient_address = "ress"; pi.patient_age = "_age"; pi.patient_gender = "nder"; pi.patient_phone = "hone";
        		// pi.complaint = "cmplnf"; pi.provisional_diagnosis = "pfd"; pi.final_diagnosis = "ffd";
        		// pi.doctor_advice = "vifce"; pi.doctor_medication = "tfion"; pi.doctor_diagnostic = "sftic"; pi.doctor_referal = "eral"; pi.doctorRegistrationNo = "onNo";
        		// pi.on_examination = "s\n\n\nd";
        		pi.family_history = "rgrgergersgregsergesgsergsergsregsergsergwererewer";


        		pi.complaint = "cmpln\n\n\n\n\n\nf"; pi.provisional_diagnosis = "p\n\n\n\n\n\nfd"; pi.final_diagnosis = "f\n\n\n\n\n\nfd";
        		pi.doctor_advice = "vi\n\n\n\n\n\nfce"; pi.doctor_medication = "t\n\n\n\n\n\nfion"; pi.doctor_diagnostic = "s\n\n\n\n\n\nftic"; pi.doctor_referal = "eral"; pi.doctorRegistrationNo = "onNo";
        		pi.on_examination = "s\n\n\n\n\n\n\n\n\nd";
        		
        		// pi.medical_history = "d";

        		GeneralPrescription gp = new GeneralPrescription(pi);
			}
		});
	}
}