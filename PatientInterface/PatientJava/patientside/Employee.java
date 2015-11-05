package patientside;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
* Stores employee information
* @author Sushanto Halder
*/
@XmlRootElement
@XmlType(propOrder={"employeeId","name","ph_no","address","country","state","gender", "password"})
public class Employee
{
	private String EmployeeId,Name,Ph_no,Address,Country,State,Gender, Password;

	/**
	* Get employee's ID
	* @return Employee ID
	*/
	protected String getEmployeeId()
	{
		return EmployeeId;
	}

	/**
	* Set employee's ID
	* @param EmployeeId ID to be set
	*/
	@XmlElement
	protected void setEmployeeId(String EmployeeId)
	{
		this.EmployeeId=EmployeeId;
	}

	/**
	* Get employee's name
	* @return Employee's name
	*/
	protected String getName()
	{
		return Name;
	}
	/**
	* Set employee name
	* @param Name Name of the employee
	*/
	@XmlElement
	protected void setName(String Name)
	{
		this.Name=Name;
	}

	/**
	* Get employee's phone no
	* @return Employee's phone no
	*/
	protected String getPh_no()
	{
		return Ph_no;
	}

	/**
	* Set employee's phone no
	* @param Ph_no Phone no of employee
	*/
	@XmlElement
	protected void setPh_no(String Ph_no)
	{
		this.Ph_no=Ph_no;
	}

	/**
	* Get employee's address
	* @return Employee's address
	*/
	protected String getAddress()
	{
		return Address;
	}

	/**
	* Set employee's address
	* @param Address Employee's address
	*/
	@XmlElement
	protected void setAddress(String Address)
	{
		this.Address=Address;
	}

	/**
	* Get employee's country
	* @return Country of the employee
	*/
	protected String getcountry()
	{
		return Country;
	}

	/**
	* Set employee's country
	* @param Country Country of the employee
	*/
	@XmlElement
	protected void setcountry(String Country)
	{
		this.Country=Country;
	}
	
	/**
	* Get employee's state
	* @return State of the employee
	*/	
	protected String getState()
	{
		return State;
	}

	/**
	* Set employee's state
	* @param State State of the employee
	*/
	@XmlElement
	protected void setState(String State)
	{
		this.State=State;
	}

	/**
	* Get gender of the employee
	* @return Gender of the employee
	*/
	protected String getGender()
	{
		return Gender;
	}

	/**
	* Set gender of the employee
	* @param Gender Gender to be set
	*/
	@XmlElement
	protected void setGender(String Gender)
	{
		this.Gender=Gender;
	}
	
	/**
	* Get password of the employee
	* @return Employee's password
	*/
	protected String getPassword()
	{
		return Password;
	}
	/**
	* Set password of the employee
	* @param Password New password to be set
	*/
	@XmlElement
	protected void setPassword(String Password)
	{
		this.Password=Password;
	}
}
