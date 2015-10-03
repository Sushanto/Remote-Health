package patientside;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;


@XmlRootElement(name="PatientReport")
@XmlType(propOrder={"patientBasicData","reports"})
public class PatientReport
{
	PatientBasicData patientBasicData;
	ArrayList<Report> Reports;

	PatientReport()
	{
		Reports=new ArrayList<Report>();
	}
	
	PatientBasicData getpatientBasicData()
	{
		return patientBasicData;
	}
	
	@XmlElement(name="PatientBasicData")
	void setpatientBasicData(PatientBasicData patientBasicData)
	{
		this.patientBasicData=patientBasicData;
	}
	
	
	ArrayList<Report> getReports()
	{
		return Reports;
	}

	@XmlElementWrapper(name="Reports")
	@XmlElement(name="Report")
	void setReports(ArrayList<Report> Reports)
	{
		this.Reports=Reports;
	}
}
