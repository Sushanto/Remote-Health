package DoctorSide;

import javax.swing.SwingUtilities;
import java.io.File;

/**
* DoctorInterface, driver class
* @author Sushanto Halder
*/
public class DoctorInterface
{
	/**
	* Main method
	* @param args Command line arguments
	*/
	public static void main(String args[])
	{
		/*
		* If temporary data folder does not exists, create one
		*/
		if(!(new File(Constants.dataFolder)).exists())
			(new File(Constants.dataFolder)).mkdir();
		new DoctorInterface();
	}

	/**
	* Set LookAndFeel,
	* Open the DoctorLogin frame
	*/
	public DoctorInterface()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
            		for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            		{
                		if ("Nimbus".equals(info.getName()))
                		{
							javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    		break;
                		}
            		}
        		}
        		catch (ClassNotFoundException ex)
        		{
            		java.util.logging.Logger.getLogger(DoctorInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (InstantiationException ex)
        		{
            		java.util.logging.Logger.getLogger(DoctorInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (IllegalAccessException ex)
        		{
            		java.util.logging.Logger.getLogger(DoctorInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (javax.swing.UnsupportedLookAndFeelException ex)
        		{
            		java.util.logging.Logger.getLogger(DoctorInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
				new DoctorLogin();
			}
		});
	}
}
