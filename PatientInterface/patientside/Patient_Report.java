package patientside;

import javax.xml.bind.annotation.*;

import javax.xml.bind.*;

import java.util.ArrayList;


@XmlRootElement(name="Patient_Report")
@XmlType(propOrder={"patientBasicData","reports"})
public class Patient_Report
{
	PatientBasicData patientBasicData;
	ArrayList<Report> Reports;

	Patient_Report()
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
