package DoctorSide;

import java.io.*;
import javax.xml.bind.*;


public class Testprogram
{
	public static void main(String args[]) throws Exception
	{
		Doctor doctor=new Doctor();
		doctor.doctorName="Sushanto Halder";
		doctor.doctorSignature="Signature";
		doctor.doctorRegistrationNumber="Regi No.";
		doctor.patientIdList.add("Patient_01_01");
		doctor.patientIdList.add("Patient_01_02");
		doctor.patientIdList.add("Patient_01_03");

		doctor.patientNameList.add("PatientName01");
		doctor.patientNameList.add("PatientName02");
		doctor.patientNameList.add("PatientName03");

		JAXBContext jc=JAXBContext.newInstance(Doctor.class);
		Marshaller jm=jc.createMarshaller();
		jm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
		jm.marshal(doctor,new File("temp.xml"));
		// Unmarshaller jum=jc.createUnmarshaller();
		// Doctor doctor=(Doctor)jum.unmarshal(new File("temp.xml"));

		// System.out.println(doctor.doctorName);
		// System.out.println(doctor.doctorSignature);
		// System.out.println(doctor.doctorRegistrationNumber);

		// for(int i=0;i<doctor.patientIdList.size();i++)
		// 	System.out.println(doctor.patientIdList.get(i));
		// for(int i=0;i<doctor.patientNameList.size();i++)
		// 	System.out.println(doctor.patientNameList.get(i));

	}
}