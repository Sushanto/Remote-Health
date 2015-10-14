package DoctorSide;
/**
* @author Sushanto Halder
*/

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
* Report class, contains patient complaint and doctor prescription, XML annotated
*/
@XmlRootElement(name="Reports")
@XmlType(propOrder={"patientComplaint","doctorPrescription"})
public class Report
{
	PatientComplaint patientComplaint=new PatientComplaint();
	DoctorPrescription doctorPrescription=new DoctorPrescription();
	
	
	PatientComplaint getpatientComplaint()
	{
		return patientComplaint;
	}
	
	@XmlElement
	void setpatientComplaint(PatientComplaint patientComplaint)
	{
		this.patientComplaint=patientComplaint;
	}
	
	DoctorPrescription getdoctorPrescription()
	{
		return doctorPrescription;
	}
	
	@XmlElement
	void setdoctorPrescription(DoctorPrescription doctorPrescription)
	{
		this.doctorPrescription=doctorPrescription;
	}
}