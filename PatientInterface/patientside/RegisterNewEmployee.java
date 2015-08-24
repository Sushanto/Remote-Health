package patientside;

import javax.swing.*;

import java.awt.*;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import javax.xml.bind.*;
import javax.xml.bind.annotation.*;

 

class Registration extends JFrame implements ActionListener
  { 
    Constants Constant=new Constants();
    final JFrame jframe=this;
    JLabel form_label,id_label,empid_label, name_label, gender_label,address_label, pass_label, confirm_pass_label, country_label, state_label, ph_no_label;
    JTextField name_field,country_field, state_field, ph_no_field;  
    JTextArea address_area;
    JRadioButton female_check, male_check;    
    ButtonGroup bG;
    JButton submit_button, clear_button, back_button;
    JPasswordField pass_field, pass_confirm_field;
    String EmployeeId,KioskNumber;
    private int countId;

    public void createId()
    {
        try
        {
        	if(Constant.RecieveFromServer("Server/EmployeeInfo/Employee_"+KioskNumber+"_IdCount.abc","tempFolder/EmployeeIdCount.abc"))
        	{
	            BufferedReader bin=new BufferedReader(new FileReader("tempFolder/EmployeeIdCount.abc"));
	            countId=Integer.parseInt(bin.readLine());
	            bin.close();
	            String temp="Employee_"+countId;
	            EmployeeId=temp;
	            countId++;
	        }
	        else
	        {
	        	JOptionPane.showMessageDialog(jframe,"Connection error. Try again later");
	        	new kiosk_login();
	        	dispose();
	        }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void IncrementEmployeeIdCount()
    {
        try
        {
        	BufferedWriter bout=new BufferedWriter(new FileWriter("tempFolder/EmployeeIdCount.abc"));
        	bout.write(String.valueOf(countId));
        	bout.close();
        	if(!Constant.SendToServer("tempFolder/EmployeeIdCount.abc","Server/EmployeeInfo/Employee_"+KioskNumber+"_IdCount.abc"))
	        {
	        	(new File("tempFolder/EmployeeIdCount.abc")).delete();
	        	JOptionPane.showMessageDialog(jframe,"Connection error. Try again later");
	        	new kiosk_login();
	        	dispose();
	        }
	        (new File("tempFolder/EmployeeIdCount.abc")).delete();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            (new File("tempFolder/EmployeeIdCount.abc")).delete();
        }
    }

    Registration()
    {
        KioskNumber=Constant.getKioskNumber();
        createId();
        setVisible(true);
        setSize(Constant.SIZE_X,Constant.SIZE_Y);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent we)
            {
                if(JOptionPane.showConfirmDialog(jframe,"Are you sure?")==JOptionPane.OK_OPTION)
                {
                    if(!(new File("tempFolder")).exists())
                        (new File("tempFolder")).delete();
                    dispose();
                }
            }
        });
        setTitle("REGISTRATION FORM");
 
        form_label = new JLabel("REGISTRATION FORM");
        form_label.setForeground(Constant.HEADERCOLOR1);
        // form_label.setBackground(Color.white);
        form_label.setFont(new Font("Serif", Font.BOLD, 20));


        id_label = new JLabel("Employee ID: ");
        empid_label = new JLabel(EmployeeId);
        name_label = new JLabel("Name:");
        gender_label=new JLabel("Gender:");
        address_label=new JLabel("Address:");
        pass_label = new JLabel("Create Passowrd:");
        confirm_pass_label = new JLabel("Confirm Password:");
        country_label = new JLabel("Country:");
        state_label = new JLabel("State:");
        ph_no_label = new JLabel("Phone No:"); 
        name_field= new JTextField();
        female_check=new JRadioButton("FEMALE");
        male_check=new JRadioButton("MALE");
	
    	bG=new ButtonGroup();
    	bG.add(female_check);
    	bG.add(male_check);


        male_check.setBackground(Constant.JPANELCOLOR1);
        female_check.setBackground(Constant.JPANELCOLOR1);

    	address_area=new JTextArea();
    	address_area.setBorder(BorderFactory.createLineBorder(Color.black));
	
        pass_field = new JPasswordField();
        pass_confirm_field = new JPasswordField();
        country_field = new JTextField();
        state_field = new JTextField();
        ph_no_field = new JTextField();
 
        submit_button = new JButton("Submit");
        clear_button = new JButton("Clear");
        back_button = new JButton("Back");

        submit_button.addActionListener(this);
        clear_button.addActionListener(this);
        back_button.addActionListener(this);

        form_label.setBounds(200, 30, 500, 30);
        id_label.setBounds(80, 130, 200, 30);
        name_label.setBounds(80, 160, 200, 30);
    	gender_label.setBounds(80, 200, 200, 30);
    	address_label.setBounds(80,280,200,30);
        pass_label.setBounds(80, 360, 200, 30);
        confirm_pass_label.setBounds(80, 400, 200, 30);
        country_label.setBounds(80, 440, 200, 30);
        state_label.setBounds(80, 480, 200, 30);
        ph_no_label.setBounds(80, 520, 200, 30);
        empid_label.setBounds(300, 120, 200, 30);
        name_field.setBounds(300, 160, 200, 30);
    	female_check.setBounds(300, 200, 200, 30);
    	male_check.setBounds(300, 230, 200, 30);
    	address_area.setBounds(300,280,200,70);
        pass_field.setBounds(300, 360, 200, 30);
        pass_confirm_field.setBounds(300, 400, 200, 30);
        country_field.setBounds(300, 440, 200, 30);
        state_field.setBounds(300, 480, 200, 30);
        ph_no_field.setBounds(300, 520, 200, 30);
        submit_button.setBounds(480, 560, 100, 30);
        clear_button.setBounds(480, 600, 100, 30);
        back_button.setBounds(80,590,100,30);

        form_label.setFont(Constant.HEADERFONT);
 

        add(form_label);
        add(id_label);
        add(empid_label);
        add(name_label);
        add(name_field);
    	add(gender_label);
    	add(female_check);
    	add(male_check);
    	add(address_label);
    	add(address_area);
        add(pass_label);
        add(pass_field);
        add(confirm_pass_label);
        add(pass_confirm_field);
        add(country_label);
        add(country_field);
        add(state_label);
        add(state_field);
        add(ph_no_label);
        add(ph_no_field);
        add(submit_button);
        add(clear_button);
        add(back_button);

        add(Constant.JPANEL2);
        add(Constant.JPANEL1);
    }

 

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == submit_button)
        {
            int x = 0;
            String s1 = name_field.getText();
            String s2 = empid_label.getText();
            String gen="";
            if(female_check.isSelected())
            gen="FEMALE";
            if(male_check.isSelected())
            gen="MALE";
            
            
            String addr=address_area.getText();
            char[] s3 = pass_field.getPassword();
            char[] s4 = pass_confirm_field.getPassword(); 
            String s8 = new String(s3);
            String s9 = new String(s4);

            String s5 = country_field.getText();
            String s6 = state_field.getText();
            String s7 = ph_no_field.getText();
            if (s8.equals(s9) && notNumber(s1) && ((female_check.isSelected() && !male_check.isSelected() ) ||(!female_check.isSelected() && male_check.isSelected() ) ) && addr.length()!=0 && s5.length()!=0 && notNumber(s5) && s6.length()!=0 && notNumber(s6) && s7.length()==10 && onlyNumber(s7))
           {
    			Employee emp=new Employee();
    			emp.setName(s1);
    			emp.setEmployeeId(EmployeeId);
    			emp.setGender(gen);
    			emp.setAddress(addr);
    			emp.setcountry(s5);
    			emp.setState(s6);
    			emp.setPh_no(s7);
    			emp.setPassword(s8);
			
    			try
    			{
    				File file=new File("tempFolder/tempEmployee.xml");
    				JAXBContext jc=JAXBContext.newInstance(Employee.class);
    				Marshaller jm=jc.createMarshaller();
                    jm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
    				jm.marshal(emp,file);
    				if(Constant.SendToServer("tempFolder/tempEmployee.xml","Server/EmployeeInfo/"+EmployeeId+".xml"))
    				{
    					JOptionPane.showMessageDialog(jframe, "Data Saved Successfully");
    					IncrementEmployeeIdCount();

    				}
    				else
    				{
    					JOptionPane.showMessageDialog(jframe,"Connection Error, try again later");
    				}
    				if(file.isFile())
	    				file.delete();
	    			new kiosk_login();
	    			dispose();
    			}
    			catch(JAXBException exc)
    			{
    				exc.printStackTrace();
    			}
            }
            else
            {
        		if(!notNumber(s1))
                    JOptionPane.showMessageDialog(submit_button, "Name should not contain any numbers!");
        		else if(female_check.isSelected() && male_check.isSelected())
                    JOptionPane.showMessageDialog(submit_button, "Only one gender to be selected!");
        		else if(!female_check.isSelected() && !male_check.isSelected())
                    JOptionPane.showMessageDialog(submit_button, "Select Gender!");
        		else if(s5.length()==0 || !notNumber(s5))
                    JOptionPane.showMessageDialog(submit_button, "Incorrect Country Entry!");
        		else if(s6.length()==0 || !notNumber(s6))
                    JOptionPane.showMessageDialog(submit_button, "Incorrect State Entry!");
        		else if(!onlyNumber(s7))
                    JOptionPane.showMessageDialog(submit_button, "Phone number should contain only numbers!");
        		else if(s7.length()!=10 )
                    JOptionPane.showMessageDialog(submit_button, "Incorrect Phone number!");
        		else
                    JOptionPane.showMessageDialog(submit_button, "Password Does Not Match");
            }
 
        }
        else if (e.getSource() == clear_button)
        {

            name_field.setText("");
            female_check.setSelected(false);
            male_check.setSelected(false);
            address_area.setText("");
            pass_field.setText("");
            pass_confirm_field.setText("");
            country_field.setText("");
            state_field.setText("");
            ph_no_field.setText("");
        }
        else  if(e.getSource() == back_button)
        {
        	(new File("tempFolder/EmployeeIdCount.abc")).delete();
            new kiosk_login();
            dispose();
        }
    }

	public static boolean valid_email(String n)
    {
    	if (n.contains("@") && (n.contains(".com")||n.contains(".co.in")||n.contains(".in")))
            return true;
    	else 
            return false;
	}	

	boolean notNumber(String n)
	{
    	if (n.matches("[0-9]+") && n.length() > 2)
            return false;
    	else
            return true;
	}
	
	
	boolean onlyNumber(String n)
	{
       	if (n.matches("[0-9]+") && n.length() > 2)
            return true;
    	else 
            return false;
    }
}

public class RegisterNewEmployee
{
    RegisterNewEmployee()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new Registration();
            }
        });
    }

    public static void main(String args[])
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new Registration();
            }
        });
    }
}