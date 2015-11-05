package patientside;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
* PatientComplaint: Stores patient's complaint, XML annotated
* @author Sushanto Halder
*/
@XmlRootElement
@XmlType(propOrder={"complaint","complaint_date",
	"weight","bmi","bp","pulse","temperature","spo2","otherResults","fileNames",
	"kioskCoordinatorName",
	"prevDiagnosis"
	})
public class PatientComplaint
{
	private String complaint, complaint_date;
	private String Weight,Bmi,Bp,Pulse,Temperature,Spo2,OtherResults,FileNames;
	private String KioskCoordinatorName;
	private String PrevDiagnosis;
	
	/**
	* Initilize date to today's date
	*/
	protected PatientComplaint()
	{
		SimpleDateFormat d=new SimpleDateFormat("yyyy-MM-dd");
		complaint_date=d.format(new Date());
	}
	/**
	* Get complaint of patient
	* @return Complaint of patient
	*/
	protected String getcomplaint()
	{
		return complaint;
	}
	/**
	* Set complaint of patient
	* @param complaint Complaint of patient
	*/
	@XmlElement
	protected void setcomplaint(String complaint)
	{
		this.complaint=complaint;
	}
	/**
	* Get date of complaint
	* @return Date of complaint
	*/
	protected String getcomplaint_date()
	{
		
		return complaint_date;
	}
	/**
	* Set date of complaint
	* @param complaint_date Date of complaint
	*/
	@XmlElement
	protected void setcomplaint_date(String complaint_date)
	{
		this.complaint_date=complaint_date;
	}
	/**
	* Get weight of patient
	* @return Weight of patient
	*/
	protected String getWeight()
	{
		return Weight;
	}
	/**
	* Set weight of patient
	* @param Weight Weight of patient
	*/
	@XmlElement
	protected void setWeight(String Weight)
	{
		this.Weight=Weight;
	}
	/**
	* Get BMI of patient
	* @return BMI of patient
	*/
	protected String getBmi()
	{
		return Bmi;
	}
	/**
	* Set BMI of patient
	* @param Bmi BMI of patient
	*/
	@XmlElement
	protected void setBmi(String Bmi)
	{
		this.Bmi=Bmi;
	}
	/**
	* Get BP of patient
	* @return BP of patient
	*/
	protected String getBp()
	{
		return Bp;
	}
	/**
	* Set BP of patient
	* @param Bp BP of patient
	*/
	@XmlElement
	protected void setBp(String Bp)
	{
		this.Bp=Bp;
	}
	/**
	* Get pulse of patient
	* @return Pulse of patient
	*/
	protected String getPulse()
	{
		return Pulse;
	}
	/**
	* Set pulse of patient
	* @param Pulse Pulse of patient
	*/
	@XmlElement
	protected void setPulse(String Pulse)
	{
		this.Pulse=Pulse;
	}
	/**
	* Get temperature of patient
	* @return Temperature of patient
	*/
	protected String getTemperature()
	{
		return Temperature;
	}
	/**
	* Set temperature of patient
	* @param Temperature Temperature of patient
	*/
	@XmlElement
	protected void setTemperature(String Temperature)
	{
		this.Temperature=Temperature;
	}
	/**
	* Get spo2 of patient
	* @return Spo2 of patient
	*/
	protected String getSpo2()
	{
		return Spo2;
	}
	/**
	* Set spo2 of patient
	* @param Spo2 Spo2 of patient
	*/
	@XmlElement
	protected void setSpo2(String Spo2)
	{
		this.Spo2=Spo2;
	}
	/**
	* Get OtherResults of patient like animea, edema etc
	* @return On examination part of patient
	*/
	protected String getOtherResults()
	{
		return OtherResults;
	}
	/**
	* Set on examination part of patient
	* @param OtherResults On examination field of patient
	*/
	@XmlElement
	protected void setOtherResults(String OtherResults)
	{
		this.OtherResults=OtherResults;
	}
	/**
	* Get additional report file names
	* @return Additional file names
	*/
	protected String getFileNames()
	{
		return FileNames;
	}
	/**
	* Set additional report file names
	* @param FileNames Additional report file names
	*/
	@XmlElement
	protected void setFileNames(String FileNames)
	{
		this.FileNames=FileNames;
	}
	/**
	* Get kiosk coordinator name
	* @return Name of kiosk coordinator name
	*/
	protected String getKioskCoordinatorName()
	{
		return KioskCoordinatorName;
	}
	/**
	* Set name of kiosk coordinator
	* @param KioskCoordinatorName Name of kiosk coordinator
	*/
	@XmlElement
	protected void setKioskCoordinatorName(String KioskCoordinatorName)
	{
		this.KioskCoordinatorName=KioskCoordinatorName;
	}
	/**
	* Get previous diagnosis
	* @return Previous diagnosis of patient
	*/
	protected String getPrevDiagnosis()
	{
		return PrevDiagnosis;
	}
	/**
	* Set previous diagnosis of patient
	* @param PrevDiagnosis Previous diagnosis of patient
	*/
	@XmlElement
	protected void setPrevDiagnosis(String PrevDiagnosis)
	{
		this.PrevDiagnosis=PrevDiagnosis;
	}

}