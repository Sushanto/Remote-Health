package patientside;

import java.io.File;
import java.io.IOException;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.awt.Dimension;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.WebcamDiscoveryService;

/**
* Capture image from default webcam
* @author Sourav Maji
*/
public class CaptureImage
{
    private JFrame captureImageFrame;
    private Webcam webcam;

    /**
    * Capture image and save the image as the provided file name
    * @param fileName Name of the file of the picture to be save
    */
    protected CaptureImage(String fileName)
    {
        captureImageFrame = new JFrame();
        final JFrame frame=captureImageFrame;
    	captureImageFrame.setTitle("Kiosk Enterprise");
    	captureImageFrame.setBounds(350, 50, 598, 475);
        captureImageFrame.setResizable(false);
    	captureImageFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        captureImageFrame.setLayout(null);
        captureImageFrame.setVisible(true);

        captureImageFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if(JOptionPane.showConfirmDialog(frame,"Are you sure?") == JOptionPane.YES_OPTION)
        		{
                    webcam.close();
                    WebcamDiscoveryService webcamDiscoveryService = Webcam.getDiscoveryService();
                    webcamDiscoveryService.stop();
                    captureImageFrame.dispose();
        		}
            }
        });
	
        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        Dimension size = WebcamResolution.VGA.getSize();
        
        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setFillArea(true);
        
        webcamPanel.setBounds(10, 10, 572, 400);
        webcamPanel.setMirrored(false);
        webcamPanel.setPreferredSize(size);
        captureImageFrame.add(webcamPanel);
      		
    	JButton btnCapture = new JButton("CAPTURE");
    	btnCapture.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
        		BufferedImage img=webcam.getImage();
                try
                {
                    ImageIO.write(img, "jpg",new File(fileName));
                }
                catch(IOException e)
                {
                    System.out.println("error happen\n");
                }
        		System.out.println("Picture Taken\n");
        		try
                {
        			Thread.sleep(5);
			
        			webcam.close();

                    WebcamDiscoveryService webcamDiscoveryService = Webcam.getDiscoveryService();
                    webcamDiscoveryService.stop();
        			captureImageFrame.dispose();
	       	    }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }				
        	}
        });
		btnCapture.setBounds(250, 415, 117, 25);
		captureImageFrame.add(btnCapture);
    }


    public static void main(String args[])
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
            java.util.logging.Logger.getLogger(CaptureImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(CaptureImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(CaptureImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(CaptureImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        EventQueue.invokeLater(new Runnable()
        {
            @Override
        	public void run()
            {
        		try
                {
					CaptureImage window = new CaptureImage(args[0]);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }          
}