package DoctorSide;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
* Stores patient's basic data, XML annotated
* @author Sushanto Halder
*/
@XmlRootElement(name="PatientBasicData")
@XmlType(propOrder={"id","date","name","image","reference","gender","age","phone","address","occupation","status","height",
"familyhistory","medicalhistory"})

public class PatientBasicData{
	private String name,phone,address,id,gender,image,
	reference,age,occupation,status,height,
	familyhistory,medicalhistory,date;

	@XmlElement
	public String getName()
	{
		return name;
	}
	public void setName(String s)
	{
		this.name=s;
	}

	public String getImage()
	{
		return image;
	}
	public void setImage(String s)
	{
		this.image=s;
	}

	public String getDate()
	{
		return date;
	}
	public void setDate(String s)
	{
		this.date=s;
	}	

	public String getId()
	{
		return id;
	}
	public void setId(String s)
	{
		this.id=s;
	}


	public String getReference()
	{
		return reference;
	}
	public void setReference(String s)
	{
		this.reference=s;
	}

	public String getGender()
	{
		return gender;
	}
	public void setGender(String s)
	{
		this.gender=s;
	}


	public String getAge()
	{
		return age;
	}
	public void setAge(String s)
	{
		this.age=s;
	}
	
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String s)
	{
		this.address=s;
	}


	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String s)
	{
		this.phone=s;
	}
	

	public String getOccupation()
	{
		return occupation;
	}
	public void setOccupation(String s)
	{
		this.occupation=s;
	}

	public String getStatus()
	{
		return status;
	}
	public void setStatus(String s)
	{
		this.status=s;
	}

	public String getHeight()
	{
		return height;
	}
	public void setHeight(String s)
	{
		this.height=s;
	}


	public String getFamilyhistory()
	{
		return familyhistory;
	}
	public void setFamilyhistory(String s)
	{
		this.familyhistory=s;
	}
	

	public String getMedicalhistory()
	{
		return medicalhistory;
	}
	public void setMedicalhistory(String s)
	{
		this.medicalhistory=s;
	}

	

}