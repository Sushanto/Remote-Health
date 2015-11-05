package DoctorSide;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlType;

/**
* PatientLog: Stores list of visited and waiting patient, XML annotated
* @author Sushanto Halder
*/
@XmlRootElement
@XmlType(propOrder={"Emergency","Normal"})
public class PatientLog
{
	@XmlList
	private ArrayList<String> Emergency = new ArrayList<String>();
	@XmlList
	private ArrayList<String> Normal = new  ArrayList<String>();

	/**
	* Get normal patient list
	* @return Arraylist of normal patient
	*/
	protected ArrayList<String> getNormal()
	{
		return Normal;
	}
	/**
	* Set normal patient list
	* @param normal ArrayList of normal patients
	*/
	protected void setNormal(ArrayList<String> normal)
	{
		Normal = normal;
	}

	/**
	* Get emergency patient list
	* @return ArrayList of emergency patient
	*/
	protected ArrayList<String> getEmergency()
	{
		return Emergency;
	}
	/**
	* Set emergency patient list
	* @param emergency Emergency patient list
	*/
	protected void setEmergency(ArrayList<String> emergency)
	{
		Emergency = emergency;
	}
}