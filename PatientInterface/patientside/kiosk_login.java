package patientside;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.xml.bind.*;
import java.io.*;
import java.net.*;

public class kiosk_login extends JFrame
{
	public static void main(String args[])
	{
		if(!(new File("tempFolder")).exists())
			(new File("tempFolder")).mkdir();
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				// try {
    //                 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    //             } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
    //     }

				try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {

                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegisterNewPatient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterNewPatient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterNewPatient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterNewPatient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
				new login_applet();
			}
		});
	}

	kiosk_login()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new login_applet();
			}
		});
	}
}


class login_applet extends JFrame
{
	Constants Constant=new Constants();
	JTextField useridBox;
	JPasswordField passwordBox;
	JLabel useridLabel,passwordLabel,errorLabel,signupLabel,showPasswordLabel,frameLabel,languageLabel;
	JPanel jpanel1,jpanel2;
	JButton signinButton,languageSaveButton;
	JCheckBox showPassword;
	JComboBox languageComboBox;
	String Language,confirmMessage,networkErrorMessage;

	void setLanguage(String str)
	{
		if(str.equals("বাংলা"))
		{
			frameLabel.setText("কিয়স্ক লগইন");
			useridLabel.setText("ইউসার-আইডি: ");
			passwordLabel.setText("পাসওয়ার্ড: ");
			showPasswordLabel.setText("পাসওয়ার্ড দেখাও");
			errorLabel.setText("অবৈধ ইউসার-আইডি বা পাসওয়ার্ড*");
			signinButton.setText("সাইন ইন");
			languageSaveButton.setText("সংরক্ষণ করুন");
			signupLabel.setText("নতুন ইউজার এখানে সাইন আপ করুন");
			languageLabel.setText("ভাষা নির্বাচন করুন");

			useridLabel.setFont(Constant.BENGALILABELFONT);
			passwordLabel.setFont(Constant.BENGALILABELFONT);
			showPasswordLabel.setFont(Constant.BENGALILABELFONT);
			errorLabel.setFont(Constant.BENGALILABELFONT);
			signupLabel.setFont(Constant.BENGALILABELFONT);
			signinButton.setFont(Constant.BENGALIBUTTONFONT);
			languageSaveButton.setFont(Constant.BENGALIBUTTONFONT);
			languageLabel.setFont(Constant.BENGALILABELFONT);

			confirmMessage="আপনি কি নিশ্চিত?";
			networkErrorMessage="নেটওয়ার্ক সমস্যা! পরে আবার চেষ্টা করুন";
		}
		else if(str.equals("English"))
		{
			frameLabel.setText("KIOSK LOGIN");
			useridLabel.setText("User-ID: ");
			passwordLabel.setText("Password: ");
			showPasswordLabel.setText("Show Password");
			errorLabel.setText("Invalid user-ID or password*");
			signinButton.setText("Sign in");
			languageSaveButton.setText("Save");
			signupLabel.setText("New user sign up here");
			languageLabel.setText("Change Language");

			useridLabel.setFont(Constant.SMALLLABELFONT);
			passwordLabel.setFont(Constant.SMALLLABELFONT);
			showPasswordLabel.setFont(Constant.SMALLLABELFONT);
			errorLabel.setFont(Constant.SMALLLABELFONT);
			signupLabel.setFont(Constant.SMALLLABELFONT);
			signinButton.setFont(Constant.SMALLBUTTONFONT);
			languageSaveButton.setFont(Constant.SMALLBUTTONFONT);
			languageLabel.setFont(Constant.SMALLLABELFONT);

			confirmMessage="Are you sure?";
			networkErrorMessage="Connection error! Try again later!";
		}
	}



	@SuppressWarnings("unchecked")
	login_applet()
	{
		/*set frame*/
		final JFrame jframe=this;
		setTitle("KIOSK LOGIN");
		setSize(Constant.SIZE_X,Constant.SIZE_Y);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{
				if(JOptionPane.showConfirmDialog(jframe,confirmMessage)==JOptionPane.OK_OPTION)
				{
					// if(!(new File("tempFolder")).exists())
					// 	(new File("tempFolder")).delete();
                    System.exit(0);
					dispose();
				}
			}
		});

		frameLabel=new JLabel();
		useridBox=new JTextField();
		passwordBox=new JPasswordField();
		useridLabel=new JLabel();
		passwordLabel=new JLabel();
		showPassword=new JCheckBox();
		showPasswordLabel=new JLabel();
		errorLabel=new JLabel();
		signinButton=new JButton();
		languageSaveButton=new JButton();
		signupLabel=new JLabel();
		languageLabel=new JLabel();

		String str[]={"বাংলা","English"};
		languageComboBox=new JComboBox(str);

		setLanguage(Language=Constant.readCurrentLanguage());


		frameLabel.setBounds(350,10,400,100);
		useridBox.setBounds(350,260,200,30);
		passwordBox.setBounds(350,320,200,30);
		useridLabel.setBounds(270,260,100,30);
		passwordLabel.setBounds(270,320,100,30);
		showPassword.setBounds(385,350,20,20);
		showPasswordLabel.setBounds(410,350,110,20);
		errorLabel.setBounds(350,370,310,15);
		signinButton.setBounds(400,385,100,30);
		signupLabel.setBounds(350,425,310,15);
		languageLabel.setBounds(20,650,150,30);
		languageComboBox.setBounds(20,650,100,30);
		languageSaveButton.setBounds(130,650,120,30);


		

		frameLabel.setFont(Constant.HEADERFONT);
		passwordBox.setEchoChar('*');
		// showPasswordLabel.setFont(new Font(showPasswordLabel.getFont().getName(),Font.BOLD,11));
		// errorLabel.setFont(new Font(errorLabel.getFont().getName(),Font.PLAIN,11));
		// signupLabel.setFont(new Font(signupLabel.getFont().getName(),Font.PLAIN,11));

		frameLabel.setForeground(Constant.HEADERCOLOR1);
		useridLabel.setForeground(Constant.LABELCOLOR1);
		passwordLabel.setForeground(Constant.LABELCOLOR1);
		showPassword.setBackground(Constant.JPANELCOLOR1);
		showPasswordLabel.setForeground(Constant.LABELCOLOR2);
		errorLabel.setForeground(Constant.WARNINGCOLOR);
		signupLabel.setForeground(Constant.LABELCOLOR3);
		languageLabel.setForeground(Constant.LABELCOLOR3);

		errorLabel.setVisible(false);
		languageComboBox.setVisible(false);
		languageSaveButton.setVisible(false);


		showPassword.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent ie)
			{
				if(ie.getStateChange()==ItemEvent.SELECTED)
					passwordBox.setEchoChar((char)0);
				else passwordBox.setEchoChar('*');
			}
		});

		signinButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				String username=useridBox.getText();
				String password=new String(passwordBox.getPassword());
				if(check(username,password))
				{
					String filename="tempFolder/tempEmployee.xml";
					errorLabel.setVisible(false);
					new patient_login();
					dispose();
				}
				else
				{
					errorLabel.setVisible(true);
					useridBox.setText(null);
					passwordBox.setText(null);
				}
			}
		});

		signupLabel.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent ae)
			{

				new RegisterNewEmployee();
				dispose();
			}

			public void mouseEntered(MouseEvent ae)
			{
				signupLabel.setForeground(Constant.MOUSEENTER);
			}
			public void mouseExited(MouseEvent ae)
			{
				signupLabel.setForeground(Constant.LABELCOLOR3);
			}
		});

		languageLabel.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent ae)
			{
				languageLabel.setVisible(false);
				languageComboBox.setVisible(true);
				languageSaveButton.setVisible(true);
				if(Language.equals("বাংলা"))
					languageComboBox.setSelectedIndex(0);
				else if(Language.equals("English"))
					languageComboBox.setSelectedIndex(1);
			}

			public void mouseEntered(MouseEvent ae)
			{
				languageLabel.setForeground(Constant.MOUSEENTER);
			}
			public void mouseExited(MouseEvent ae)
			{
				languageLabel.setForeground(Constant.LABELCOLOR3);
			}
		});

		languageSaveButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				languageSaveButton.setVisible(false);
				languageComboBox.setVisible(false);
				languageLabel.setVisible(true);

				Language=(String)languageComboBox.getSelectedItem();
				try
				{
					File file=new File("tempFolder/Language.abc");
					if(!file.isFile())
						file.createNewFile();
					FileOutputStream fout=new FileOutputStream(file);
					PrintWriter pwriter=new PrintWriter(fout);
					pwriter.println(Language);
					pwriter.close();
					fout.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				setLanguage(Language);
			}
		});

		add(frameLabel);
		add(useridBox);
		add(passwordBox);
		add(useridLabel);
		add(passwordLabel);
		add(showPassword);
		add(showPasswordLabel);
		add(errorLabel);
		add(signinButton);
		add(signupLabel);
		add(languageLabel);
		add(languageComboBox);
		add(languageSaveButton);

		add(Constant.JPANEL2);
		add(Constant.JPANEL1);
	}

	boolean check(String user,String pw)
	{
		String username=user;
		String password=pw;
		String ServerFile="Server/EmployeeInfo/"+username+".xml";
		String LocalFile="tempFolder/tempEmployee.xml";
		Employee emp;
		if(Constant.RecieveFromServer(ServerFile,LocalFile))
		{
			try
			{
				File file=new File(LocalFile);
				JAXBContext jc=JAXBContext.newInstance(Employee.class);
				Unmarshaller jum=jc.createUnmarshaller();
				emp=(Employee)jum.unmarshal(file);
				file.delete();
				file=new File("tempFolder/tempEmployee.abc");
				file.createNewFile();
				FileWriter fw=new FileWriter(file);
				BufferedWriter bw=new BufferedWriter(fw);
				bw.write(emp.getEmployeeId());
				bw.close();
				fw.close();
				if(password.equals(emp.getPassword()))
					return true;
				else
				{
					file.delete();
					return false;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, networkErrorMessage);
				return false;
			}
		}
		else return false;
	}
}