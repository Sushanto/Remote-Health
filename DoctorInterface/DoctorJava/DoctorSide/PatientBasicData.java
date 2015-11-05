package DoctorSide;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
* PatientBasicData: Stores patient's basic data, XML annotated
* @author Sushanto Halder
*/
@XmlRootElement(name="PatientBasicData")
@XmlType(propOrder={"id","date","name","bloodGroup","image","reference","gender","age","phone","address","occupation","status","height",
"familyhistory","medicalhistory"})

public class PatientBasicData{
	private String name,bloodGroup,phone,address,id,gender,image,
	reference,age,occupation,status,height,
	familyhistory,medicalhistory,date;

	/**
	* Get patient's name
	* @return Patient's name
	*/
	protected String getName()
	{
		return name;
	}
	/**
	* Set patient's name
	* @param name Name of patient
	*/
	@XmlElement
	protected void setName(String name)
	{
		this.name=name;
	}

	/**
	* Get patient's bloodGroup
	* @return Patient's bloodGroup
	*/
	protected String getBloodGroup()
	{
		return bloodGroup;
	}
	/**
	* Set patient's bloodGroup
	* @param bloodGroup BloodGroup of patient
	*/
	@XmlElement
	protected void setBloodGroup(String bloodGroup)
	{
		this.bloodGroup=bloodGroup;
	}

	/**
	* Get patient's Image file name
	* @return Name of the patient's image file
	*/
	protected String getImage()
	{
		return image;
	}
	/**
	* Set image file name of patient
	* @param image Image file name of patient
	*/
	@XmlElement
	protected void setImage(String image)
	{
		this.image=image;
	}

	/**
	* Get patient's registration date
	* @return Patient's registration date
	*/
	protected String getDate()
	{
		return date;
	}
	/**
	* Set patient's registration date
	* @param date Registration date
	*/
	@XmlElement
	protected void setDate(String date)
	{
		this.date=date;
	}	

	/**
	* Get patient's ID
	* @return Patient's ID
	*/
	protected String getId()
	{
		return id;
	}
	/**
	* Set patient's ID
	* @param id Patient's ID
	*/
	@XmlElement
	protected void setId(String id)
	{
		this.id=id;
	}

	/**
	* Get reference of patient
	* @return Patient's reference
	*/
	protected String getReference()
	{
		return reference;
	}
	/**
	* Set reference of patient
	* @param reference Reference of patient
	*/
	@XmlElement
	protected void setReference(String reference)
	{
		this.reference=reference;
	}
	/**
	* Get gender of patient
	* @return Gender of patient
	*/
	protected String getGender()
	{
		return gender;
	}
	/**
	* Set gender of patient
	* @param gender Gender of patient
	*/
	@XmlElement
	protected void setGender(String gender)
	{
		this.gender=gender;
	}

	/**
	* Get patient's age
	* @return Age of patient
	*/
	protected String getAge()
	{
		return age;
	}
	/**
	* Set patient's age
	* @param age Age of patient
	*/
	@XmlElement
	protected void setAge(String age)
	{
		this.age=age;
	}
	/**
	* Get address of patient
	* @return Address of patient
	*/
	protected String getAddress()
	{
		return address;
	}
	/**
	* Set address of patient
	* @param address Address of patient
	*/
	@XmlElement
	protected void setAddress(String address)
	{
		this.address=address;
	}

	/**
	* Get patient's phone no
	* @return Patient's phone no
	*/
	protected String getPhone()
	{
		return phone;
	}
	/**
	* Set patient's phone no
	* @param phone Phone no of patient
	*/
	@XmlElement
	protected void setPhone(String phone)
	{
		this.phone=phone;
	}
	
	/**
	* Get patient's occupation
	* @return Patient's occupation
	*/
	protected String getOccupation()
	{
		return occupation;
	}
	/**
	* Set patient's occupation
	* @param occupation Occupation of patient
	*/
	@XmlElement
	protected void setOccupation(String occupation)
	{
		this.occupation=occupation;
	}

	/**
	* Get patient's status, new or revisited
	* @return Patient's status
	*/
	protected String getStatus()
	{
		return status;
	}
	/**
	* Set patient's status
	* @param status Status of patient, new or revisited
	*/
	@XmlElement
	protected void setStatus(String status)
	{
		this.status=status;
	}

	/**
	* Get patient's height
	* @return Patient's height
	*/
	protected String getHeight()
	{
		return height;
	}
	/**
	* Set patient's height
	* @param height Height of patient
	*/
	@XmlElement
	protected void setHeight(String height)
	{
		this.height=height;
	}

	/**
	* Get family history of patient
	* @return Patient's family history
	*/
	protected String getFamilyhistory()
	{
		return familyhistory;
	}
	/**
	* Set family history of patient
	* @param familyHistory Family history of patient
	*/
	@XmlElement
	protected void setFamilyhistory(String familyHistory)
	{
		this.familyhistory=familyHistory;
	}
	
	/**
	* Get medical history of patient
	* @return Patient's medical history
	*/
	protected String getMedicalhistory()
	{
		return medicalhistory;
	}
	/**
	* Set medical history of patient
	* @param medicalHistory Medical history of patient
	*/
	@XmlElement
	protected void setMedicalhistory(String medicalHistory)
	{
		this.medicalhistory=medicalHistory;
	}

	

}