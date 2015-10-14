package DoctorSide;

/**
* @author Sushanto Halder
*/

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
* PatientLog class, stores list of visited and waiting patient, XML annotated
*/
@XmlRootElement
@XmlType(propOrder={"Emergency","Normal"})
public class PatientLog
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