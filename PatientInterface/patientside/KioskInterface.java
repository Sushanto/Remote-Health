package patientside;

import javax.swing.SwingUtilities;
import java.io.File;

/**
*Drivier class
*@author Sushanto Halder
*/

public class KioskInterface
{
	/**
	* Main method
	* @param args Unused
	*/
	public static void main(String args[])
	{
		if(!(new File(Constants.dataPath)).exists())
			(new File(Constants.dataPath)).mkdir();
		new KioskInterface();
	}

	/**
	*Contructor, called by main method
	*/
	public KioskInterface()
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
            		java.util.logging.Logger.getLogger(KioskInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (InstantiationException ex)
        		{
            		java.util.logging.Logger.getLogger(KioskInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (IllegalAccessException ex)
        		{
            		java.util.logging.Logger.getLogger(KioskInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (javax.swing.UnsupportedLookAndFeelException ex)
        		{
            		java.util.logging.Logger.getLogger(KioskInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}

				new KioskLogin();
			}
		});
	}
}