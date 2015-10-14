package DoctorSide;
/**
* @author Sushanto Halder
*/

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
* DoctorPrescription class, contains prescription provided by doctor, XML annotated
*/
@XmlRootElement
@XmlType(propOrder={"doctorName","prescription_date","provisionalDiagnosis","finalDiagnosis","advice","medication","diagnosis","referral","signature","registration_number"})
public class DoctorPrescription
{
	String doctorName, ProvisionalDiagnosis,FinalDiagnosis,Advice, Medication, Diagnosis, Prescription_Date,Referral,Signature,Registration_number;
	
	DoctorPrescription()
	{
		// DateFormat d=new SimpleDateFormat("yyyy-MM-dd");
		// Prescription_Date=d.format(new Date().toString());
	}
	
	String getdoctorName()
	{
		return doctorName;
	}
	
	@XmlElement
	void setdoctorName(String doctorName)
	{
		this.doctorName=doctorName;
	}

	String getProvisionalDiagnosis()
	{
		return ProvisionalDiagnosis;
	}
	@XmlElement
	void setProvisionalDiagnosis(String ProvisionalDiagnosis)
	{
		this.ProvisionalDiagnosis=ProvisionalDiagnosis;
	}

	String getFinalDiagnosis()
	{
		return FinalDiagnosis;
	}
	@XmlElement
	void setFinalDiagnosis(String FinalDiagnosis)
	{
		this.FinalDiagnosis=FinalDiagnosis;
	}
	
	String getAdvice()
	{
		return Advice;
	}
	
	@XmlElement
	void setAdvice(String Advice)
	{
		this.Advice=Advice;
	}
	
	String getMedication()
	{
		return Medication;
	}
	
	@XmlElement
	void setMedication(String Medication)
	{
		this.Medication=Medication;
	}
	
	String getDiagnosis()
	{
		return Diagnosis;
	}
	
	@XmlElement
	void setDiagnosis(String Diagnosis)
	{
		this.Diagnosis=Diagnosis;
	}
	
	String getPrescription_date()
	{
		return Prescription_Date;
	}
	
	@XmlElement
	void setPrescription_date(String Prescription_date)
	{
		this.Prescription_Date=Prescription_date;
	}

	String getReferral()
	{
		return Referral;
	}
	@XmlElement
	void setReferral(String Referral)
	{
		this.Referral=Referral;
	}

	String getSignature()
	{
		return Signature;
	}
	@XmlElement
	void setSignature(String Signature)
	{
		this.Signature=Signature;
	}

	String getRegistration_number()
	{
		return Registration_number;
	}
	@XmlElement
	void setRegistration_number(String Registration_number)
	{
		this.Registration_number=Registration_number;
	}
}