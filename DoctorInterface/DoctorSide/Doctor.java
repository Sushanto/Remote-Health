package DoctorSide;

/**
* @author Sushanto Halder
*/

import java.io.*;
import java.net.*;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;

/**
* Stores Doctor's information, XML annotated
*/
@XmlRootElement(name="Doctor")
@XmlType(propOrder={"doctorId","doctorName","doctorSignature","doctorRegistrationNumber","patientIdList","patientNameList"})
public class Doctor
{
	private String doctorId,doctorName,doctorSignature,doctorRegistrationNumber;
	private ArrayList<String> patientIdList,patientNameList;

	/**
	* Initialize patientIdList and patientNameList
	*/
	protected Doctor()
	{
		patientIdList=new ArrayList<String>();
		patientNameList=new ArrayList<String>();
	}

	/**
	* Get doctor ID as string
	* @return Doctor-ID
	*/
	protected String getDoctorId()
	{
		return doctorId;
	}

	/**
	* Set doctor's ID
	* @param doctorId Doctor's ID
	*/
	@XmlElement(name="DoctorId")
	protected void setDoctorId(String doctorId)
	{
		this.doctorId=doctorId;
	}

	/**
	* Get doctor's name as string
	* @return Doctor's name
	*/
	protected String getDoctorName()
	{
		return doctorName;
	}
	/**
	* Set doctor's name
	* @param doctorName Doctor's name
	*/
	@XmlElement(name="DoctorName")
	protected void setDoctorName(String doctorName)
	{
		this.doctorName=doctorName;
	}

	/**
	* Get doctor's signature as string
	* @return Doctor signature
	*/
	protected String getDoctorSignature()
	{
		return doctorSignature;
	}
	/**
	* Set doctor's signature
	* @param doctorSignature Doctor signature
	*/
	@XmlElement(name="DoctorSignature")
	protected void setDoctorSignature(String doctorSignature)
	{
		this.doctorSignature=doctorSignature;
	}

	/**
	* Get doctor's registration number as string
	* @return Doctor's registration number
	*/
	protected String getDoctorRegistrationNumber()
	{
		return doctorRegistrationNumber;
	}
	/**
	* Set doctor registration number
	* @param doctorRegistrationNumber Doctor's registration number
	*/
	@XmlElement(name="DoctorRegistrationNumber")
	protected void setDoctorRegistrationNumber(String doctorRegistrationNumber)
	{
		this.doctorRegistrationNumber=doctorRegistrationNumber;
	}

	/**
	* Get list of patient's ID prescribed by doctor
	* @return List of patient's ID prescribed by doctor as ArrayList
	*/
	protected ArrayList<String> getPatientIdList()
	{
		return patientIdList;
	}
	/**
	* Set doctor patient-ID list
	* @param patientIdList List of patient's ID prescribed by the doctor
	*/
	@XmlElement(name="PatientIdList")
	protected void setPatientIdList(ArrayList<String> patientIdList)
	{
		this.patientIdList=patientIdList;
	}

	/**
	* Get list of patient's name prescribed by doctor
	* @return List of patient's name prescribed by doctor as ArrayList
	*/
	protected ArrayList<String> getPatientNameList()
	{
		return patientNameList;
	}
	/**
	* Set doctor patient-name list
	* @param patientNameList List of patient's name prescribed by the doctor
	*/
	@XmlElement(name="PatientNameList")
	protected void setPatientNameList(ArrayList<String> patientNameList)
	{
		this.patientNameList=patientNameList;
	}

}