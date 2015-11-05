package patientside;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
* Report: Contains patient complaint and doctor prescription, XML annotated
* @author Sushanto Halder
*/
@XmlRootElement(name = "Reports")
@XmlType(propOrder = {"patientComplaint","doctorPrescription"})
public class Report
{
	private PatientComplaint patientComplaint = new PatientComplaint();
	private DoctorPrescription doctorPrescription = new DoctorPrescription();
	
	/**
	* Get complaints and other datas of patient
	* @return PatientComplaint object
	*/
	protected PatientComplaint getPatientComplaint()
	{
		return patientComplaint;
	}
	/**
	* Set complaint and other datas related to complaint of patient
	* @param patientComplaint A PatientComplaint object
	*/
	@XmlElement
	protected void setPatientComplaint(PatientComplaint patientComplaint)
	{
		this.patientComplaint = patientComplaint;
	}
	/**
	* Get prescription provided by doctor
	* @return DoctorPrescription object
	*/
	protected DoctorPrescription getDoctorPrescription()
	{
		return doctorPrescription;
	}
	/**
	* Set doctor prescription
	* @param doctorPrescription A DoctorPrescription object
	*/
	@XmlElement
	protected void setDoctorPrescription(DoctorPrescription doctorPrescription)
	{
		this.doctorPrescription=doctorPrescription;
	}
}