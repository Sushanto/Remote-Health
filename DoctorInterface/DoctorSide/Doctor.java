package DoctorSide;

import java.io.*;
import java.net.*;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;


@XmlRootElement(name="Doctor")
@XmlType(propOrder={"doctorId","doctorName","doctorSignature","doctorRegistrationNumber","patientIdList","patientNameList"})
public class Doctor
{
	protected String doctorId,doctorName,doctorSignature,doctorRegistrationNumber;
	protected ArrayList<String> patientIdList,patientNameList;

	public Doctor()
	{
		patientIdList=new ArrayList<String>();
		patientNameList=new ArrayList<String>();
	}

	String getDoctorId()
	{
		return doctorId;
	}
	@XmlElement(name="DoctorId")
	void setDoctorId(String doctorId)
	{
		this.doctorId=doctorId;
	}

	String getDoctorName()
	{
		return doctorName;
	}
	@XmlElement(name="DoctorName")
	void setDoctorName(String doctorName)
	{
		this.doctorName=doctorName;
	}

	String getDoctorSignature()
	{
		return doctorSignature;
	}
	@XmlElement(name="DoctorSignature")
	void setDoctorSignature(String doctorSignature)
	{
		this.doctorSignature=doctorSignature;
	}

	String getDoctorRegistrationNumber()
	{
		return doctorRegistrationNumber;
	}
	@XmlElement(name="DoctorRegistrationNumber")
	void setDoctorRegistrationNumber(String doctorRegistrationNumber)
	{
		this.doctorRegistrationNumber=doctorRegistrationNumber;
	}

	ArrayList<String> getPatientIdList()
	{
		return patientIdList;
	}
	@XmlElement(name="PatientIdList")
	void setPatientIdList(ArrayList<String> patientIdList)
	{
		this.patientIdList=patientIdList;
	}

	ArrayList<String> getPatientNameList()
	{
		return patientNameList;
	}
	@XmlElement(name="PatientNameList")
	void setPatientNameList(ArrayList<String> patientNameList)
	{
		this.patientNameList=patientNameList;
	}

}