package patientside;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;
import java.util.Date;

@XmlRootElement
@XmlType(propOrder={"complaint","complaint_date",
	"weight","bmi","bp","pulse","temperature","spo2","otherResults","fileNames",
	"kioskCoordinatorName",
	"prevDiagnosis"
	})
public class PatientComplaint
{
	// String complaint;
	String complaint, complaint_date;
	String Weight,Bmi,Bp,Pulse,Temperature,Spo2,OtherResults,FileNames;
	String KioskCoordinatorName;
	String PrevDiagnosis;
		
	PatientComplaint()
	{
		SimpleDateFormat d=new SimpleDateFormat("yyyy-MM-dd");
		complaint_date=d.format(new Date());
	}
	
	String getcomplaint()
	{
		return complaint;
	}
	
	@XmlElement
	void setcomplaint(String complaint)
	{
		this.complaint=complaint;
	}
	
	String getcomplaint_date()
	{
		
		return complaint_date;
	}
	
	@XmlElement
	void setcomplaint_date(String complaint_date)
	{
		this.complaint_date=complaint_date;
	}

	String getWeight()
	{
		return Weight;
	}
	@XmlElement
	void setWeight(String Weight)
	{
		this.Weight=Weight;
	}

	String getBmi()
	{
		return Bmi;
	}
	@XmlElement
	void setBmi(String Bmi)
	{
		this.Bmi=Bmi;
	}

	String getBp()
	{
		return Bp;
	}
	@XmlElement
	void setBp(String Bp)
	{
		this.Bp=Bp;
	}

	String getPulse()
	{
		return Pulse;
	}
	@XmlElement
	void setPulse(String Pulse)
	{
		this.Pulse=Pulse;
	}

	String getTemperature()
	{
		return Temperature;
	}
	@XmlElement
	void setTemperature(String Temperature)
	{
		this.Temperature=Temperature;
	}

	String getSpo2()
	{
		return Spo2;
	}
	@XmlElement
	void setSpo2(String Spo2)
	{
		this.Spo2=Spo2;
	}

	String getOtherResults()
	{
		return OtherResults;
	}
	@XmlElement
	void setOtherResults(String OtherResults)
	{
		this.OtherResults=OtherResults;
	}

	String getFileNames()
	{
		return FileNames;
	}
	@XmlElement
	void setFileNames(String FileNames)
	{
		this.FileNames=FileNames;
	}

	String getKioskCoordinatorName()
	{
		return KioskCoordinatorName;
	}
	@XmlElement
	void setKioskCoordinatorName(String KioskCoordinatorName)
	{
		this.KioskCoordinatorName=KioskCoordinatorName;
	}

	// String getKioskCoordinatorPhno()
	// {
	// 	return KioskCoordinatorPhno;
	// }
	// @XmlElement
	// void setKioskCoordinatorPhno(String KioskCoordinatorPhno)
	// {
	// 	this.KioskCoordinatorPhno=KioskCoordinatorPhno;
	// }

	String getPrevDiagnosis()
	{
		return PrevDiagnosis;
	}
	@XmlElement
	void setPrevDiagnosis(String PrevDiagnosis)
	{
		this.PrevDiagnosis=PrevDiagnosis;
	}

}