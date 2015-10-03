package patientside;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder={"employeeId","name","ph_no","address","country","state","gender", "password"})
public class Employee
{
	String EmployeeId,Name,Ph_no,Address,Country,State,Gender, Password;

	Employee()
	{

	}

	String getEmployeeId()
	{
		return EmployeeId;
	}

	@XmlElement
	void setEmployeeId(String EmployeeId)
	{
		this.EmployeeId=EmployeeId;
	}

	String getName()
	{
		return Name;
	}

	@XmlElement
	void setName(String Name)
	{
		this.Name=Name;
	}

	String getPh_no()
	{
		return Ph_no;
	}

	@XmlElement
	void setPh_no(String Ph_no)
	{
		this.Ph_no=Ph_no;
	}

	String getAddress()
	{
		return Address;
	}

	@XmlElement
	void setAddress(String Address)
	{
		this.Address=Address;
	}

	String getcountry()
	{
		return Country;
	}

	@XmlElement
	void setcountry(String Country)
	{
		this.Country=Country;
		}
		
	String getState()
	{
		return State;
	}

	@XmlElement
	void setState(String State)
	{
		this.State=State;
	}


	String getGender()
	{
		return Gender;
	}

	@XmlElement
	void setGender(String Gender)
	{
		this.Gender=Gender;
	}
	
	
	String getPassword()
	{
		return Password;
	}

	@XmlElement
	void setPassword(String Password)
	{
		this.Password=Password;
	}
	
/*	
	int getAge()
	{
		return Age;
	}

	@XmlElement
	void setAge(int Age)
	{
		this.Age=Age;
	} */
}
