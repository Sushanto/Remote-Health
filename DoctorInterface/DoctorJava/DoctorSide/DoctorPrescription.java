package DoctorSide;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
* DoctorPrescription: Contains prescription provided by doctor, XML annotated
* @author Sushanto Halder
*/
@XmlRootElement
@XmlType(propOrder={"doctorName","prescription_date","provisionalDiagnosis","finalDiagnosis","advice","medication","diagnosis","referral","signature","registration_number"})
public class DoctorPrescription
{
	private String doctorName, ProvisionalDiagnosis,FinalDiagnosis,Advice, Medication, Diagnosis, Prescription_Date,Referral,Signature,Registration_number;
	/**
	* Get doctor's name
	* @return Name of doctor
	*/
	protected String getDoctorName()
	{
		return doctorName;
	}
	/**
	* Set name of doctor
	* @param doctorName Name of doctor
	*/
	@XmlElement
	protected void setDoctorName(String doctorName)
	{
		this.doctorName=doctorName;
	}
	/**
	* Get provisional diagnosis 
	* @return String, provisional diagnosis
	*/
	protected String getProvisionalDiagnosis()
	{
		return ProvisionalDiagnosis;
	}
	/**
	* Set provisional diagnosis
	* @param provisionalDiagnosis Provisional diagnosis provided by doctor
	*/
	@XmlElement
	protected void setProvisionalDiagnosis(String provisionalDiagnosis)
	{
		this.ProvisionalDiagnosis=provisionalDiagnosis;
	}
	/**
	* Get final diagnosis
	* @return String, final diagnosis, provided by doctor
	*/
	protected String getFinalDiagnosis()
	{
		return FinalDiagnosis;
	}
	/**
	* Set final diagnosis
	* @param finalDiagnosis Final diagnosis provided by doctor
	*/
	@XmlElement
	protected void setFinalDiagnosis(String finalDiagnosis)
	{
		this.FinalDiagnosis=finalDiagnosis;
	}
	/**
	* Get advice string
	* @return Advice as string
	*/
	protected String getAdvice()
	{
		return Advice;
	}
	/**
	* Set advice of doctor
	* @param advice Advice provided by doctor
	*/
	@XmlElement
	protected void setAdvice(String advice)
	{
		this.Advice=advice;
	}
	/**
	* Get medication provided by doctor
	* @return Medication from doctor
	*/
	protected String getMedication()
	{
		return Medication;
	}
	/**
	* Set medication
	* @param medication Medication provided by doctor
	*/
	@XmlElement
	protected void setMedication(String medication)
	{
		this.Medication=medication;
	}
	/**
	* Get diagnosis by doctor
	* @return Diagnosis report by doctor
	*/
	protected String getDiagnosis()
	{
		return Diagnosis;
	}
	/**
	* Set diagnosis report by doctor
	* @param diagnosis Diagnosis report provided by doctor
	*/
	@XmlElement
	protected void setDiagnosis(String diagnosis)
	{
		this.Diagnosis=diagnosis;
	}
	/**
	* Get date of prescription
	* @return Date of prescription as string
	*/
	protected String getPrescription_date()
	{
		return Prescription_Date;
	}
	/**
	* Set date of prescription
	* @param prescriptionDate Date of prescription
	*/
	@XmlElement
	protected void setPrescription_date(String prescriptionDate)
	{
		this.Prescription_Date=prescriptionDate;
	}
	/**
	* Get referral
	* @return Referral as a string
	*/
	protected String getReferral()
	{
		return Referral;
	}
	/**
	* Set referral provided by doctor
	* @param referral Referral provided by doctor
	*/
	@XmlElement
	protected void setReferral(String referral)
	{
		this.Referral=referral;
	}
	/**
	* Get signature of doctor
	* @return Signature of doctor as encoded string
	*/
	protected String getSignature()
	{
		return Signature;
	}
	/**
	* Set signature of doctor
	* @param signature Signature of doctor as encoded string
	*/
	@XmlElement
	protected void setSignature(String signature)
	{
		this.Signature=signature;
	}
	/**
	* Get registration number of doctor
	* @return Registration number of doctor
	*/
	protected String getRegistration_number()
	{
		return Registration_number;
	}
	/**
	* Set registration number of doctor
	* @param registrationNumber Registration no of doctor
	*/
	@XmlElement
	protected void setRegistration_number(String registrationNumber)
	{
		this.Registration_number=registrationNumber;
	}
}