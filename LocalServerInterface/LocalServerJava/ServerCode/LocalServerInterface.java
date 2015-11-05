package ServerCode;

import javax.swing.SwingUtilities;
import java.io.File;

/**
* Drivier class
* @author Sushanto Halder
*/

public class LocalServerInterface
{
	/**
	* Main method
	* @param args Unused
	*/
	public static void main(String args[])
	{
		new LocalServerInterface();
	}

	/**
	* Set look and feel
	*/
	public LocalServerInterface()
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
            		java.util.logging.Logger.getLogger(LocalServerInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (InstantiationException ex)
        		{
            		java.util.logging.Logger.getLogger(LocalServerInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (IllegalAccessException ex)
        		{
            		java.util.logging.Logger.getLogger(LocalServerInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (javax.swing.UnsupportedLookAndFeelException ex)
        		{
            		java.util.logging.Logger.getLogger(LocalServerInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}

				new LocalServerController();
			}
		});
	}
}