package patientside;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;

/**
* PatientReport class, stores all the reports of a patient, XML annotated
* @author Sushanto Halder
*/
@XmlRootElement(name="PatientReport")
@XmlType(propOrder={"patientBasicData","reports"})
public class PatientReport
{
	private PatientBasicData patientBasicData;
	private ArrayList<Report> Reports;

	/**
	* Initialize Reports arraylist
	*/
	protected PatientReport()
	{
		Reports=new ArrayList<Report>();
	}
	
	/**
	* Get basic data of patient
	* @return PatientBasicData object
	*/
	protected PatientBasicData getPatientBasicData()
	{
		return patientBasicData;
	}
	/**
	* Set basicdata of patient
	* @param patientBasicData Object of PatientBasicData, contains basic data of patient
	*/
	@XmlElement(name="PatientBasicData")
	protected void setPatientBasicData(PatientBasicData patientBasicData)
	{
		this.patientBasicData=patientBasicData;
	}
	
	/**
	* Get report of patient which contains complaint and prescription
	* @return ArrayList of report see DoctorSide.Report
	*/
	protected ArrayList<Report> getReports()
	{
		return Reports;
	}
	/**
	* Set report of patient
	* @param Reports Arraylist of report, see DoctorSide.Report
	*/
	@XmlElementWrapper(name="Reports")
	@XmlElement(name="Report")
	protected void setReports(ArrayList<Report> Reports)
	{
		this.Reports=Reports;
	}
}
