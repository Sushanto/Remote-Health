package DoctorSide;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement
@XmlType(propOrder={"Emergency","Normal"})
class PatientLog
{
	@XmlElement
	ArrayList<String> Emergency=new ArrayList<String>();
	@XmlElement
	ArrayList<String> Normal =new  ArrayList<String>();

	ArrayList<String> getnormal()
	{
		return Normal;
	}

	void setnormal(ArrayList<String> normal)
	{
		Normal = normal;
	}

	ArrayList<String> getemergency()
	{
		return Emergency;
	}
	void setemergency(ArrayList<String> emergency)
	{
		Emergency = emergency;
	}
}