package ServerCode;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultCaret;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class LocalServerController
{
	private JFrame localServerControllerFrame;
	private JButton localServerStartButton,localServerStopButton,clearButton;
	private JTextArea localServerOutputArea;
	private JLabel frameLabel;
	private JPanel localServerPanel,outerPanel;
	private JScrollPane localServerOutputPane;
	private Font font;
	private Process localServerProcess;
	private OutputCollector outputCollector;
	private ErrorCollector errorCollector;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yy_HH_mm_ss");
	private String workingPath = "", logPath = "";

	protected LocalServerController()
	{
		readInfo();
		localServerControllerFrame = new JFrame();
		final JFrame jframe = localServerControllerFrame;
		
		font = new Font("Serif",Font.BOLD,12);
		localServerControllerFrame.setBounds(100,0,1200,700);
		localServerControllerFrame.setResizable(false);
		localServerControllerFrame.setTitle("LOCAL SERVER");
		localServerControllerFrame.setVisible(true);
		localServerControllerFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// localServerControllerFrame.setBackground(Color.GREEN.darker().darker());
		localServerControllerFrame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{
				if(JOptionPane.showConfirmDialog(jframe,"Are you sure?") == JOptionPane.OK_OPTION)
				{
					try
					{
						outputCollector.interrupt();
						errorCollector.interrupt();
						localServerProcess.destroy();
						writeToLogFile();
					}
					catch(NullPointerException npe)
					{
						npe.printStackTrace();
					}
					localServerControllerFrame.dispose();
                    System.exit(0);
				}
			}
		});

		localServerPanel = new JPanel();
		outerPanel = new JPanel();
		frameLabel = new JLabel("LOCAL SERVER");

		localServerStartButton = new JButton("START");
		localServerStopButton = new JButton("STOP");
		clearButton = new JButton("CLEAR");

		localServerOutputArea = new JTextArea();

		localServerPanel.setBounds(10,10,1180,680);
		frameLabel.setBounds(430,0,400,100);
		localServerStartButton.setBounds(960,35,100,25);
		localServerStopButton.setBounds(960,35,100,25);
		clearButton.setBounds(1070,35,100,25);
		localServerOutputArea.setBounds(20,80,1160,600);

		localServerOutputArea.setEditable(false);

		frameLabel.setForeground(Color.WHITE);
		localServerOutputArea.setForeground(Color.WHITE);
		localServerOutputArea.setBackground(Color.MAGENTA.darker().darker().darker().darker().darker());
		localServerPanel.setBackground(Color.YELLOW.darker());

		frameLabel.setFont(new Font("Serif",Font.BOLD,34));
		localServerStartButton.setFont(font);
		localServerStopButton.setFont(font);
		clearButton.setFont(font);

		localServerPanel.setBorder(BorderFactory.createRaisedBevelBorder());

		DefaultCaret caret = (DefaultCaret) localServerOutputArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);


		localServerStartButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
		        try
		        {
					localServerStartButton.setVisible(false);
					localServerStopButton.setVisible(true);
					localServerProcess = Runtime.getRuntime().exec("java " + "-cp " + workingPath + "LocalServer.jar "/**/ + "ServerCode.LocalServer");
					outputCollector = new OutputCollector(localServerProcess);
					errorCollector = new ErrorCollector(localServerProcess);
					outputCollector.start();
					errorCollector.start();
			    }
			    catch(IOException ioe)
			    {
			    	ioe.printStackTrace();
			    }
			}
		});


		localServerStopButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				outputCollector.interrupt();
				errorCollector.interrupt();
				localServerStartButton.setVisible(true);
				localServerStopButton.setVisible(false);
				localServerProcess.destroy();
			}
		});


		clearButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				writeToLogFile();
				localServerOutputArea.setText("");
			}
		});

		localServerControllerFrame.add(frameLabel);
		localServerControllerFrame.add(localServerStartButton);
		localServerControllerFrame.add(localServerStopButton);
		localServerControllerFrame.add(clearButton);
		localServerControllerFrame.add(frameLabel);
		localServerControllerFrame.add(localServerOutputArea);

		localServerOutputPane = new JScrollPane(localServerOutputArea);
		localServerOutputPane.setBounds(20,80,1160,600);
		localServerControllerFrame.add(localServerOutputPane);

		localServerControllerFrame.add(localServerPanel);
		localServerControllerFrame.add(outerPanel);
	}

	private void writeToLogFile()
	{
		if(localServerOutputArea.getText().equals(""))
			return;
		try
		{
			String fileName = logPath + "LocalServerLog_" + dateFormat.format(new Date()) + ".log";
			File logFile = new File(fileName);
			String log = localServerOutputArea.getText();
			logFile.createNewFile();
			FileWriter fwriter = new FileWriter(logFile);
			PrintWriter printWriter = new PrintWriter(fwriter);
			printWriter.print(log);
			fwriter.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	private void readInfo()
	{
		try
		{
			FileReader fReader = new FileReader(new File("KioskMetadata.cfg"));
			BufferedReader bReader = new BufferedReader(fReader);
			System.out.println(dateFormat.format(new Date()) + "   " + "LocalServerController\t> Kiosk Information reading....");

			String line;
			while((line=bReader.readLine())!=null)
			{
				String[] tokens = line.split("=");
				switch(tokens[0])
				{
					case "WORKING_FOLDER":
						workingPath = tokens[1];
						break;
					case "LOG_FOLDER":
						logPath = tokens[1];
						if(!(new File(logPath).exists()))
							new File(logPath).mkdirs();
						break;
				}
			}
			bReader.close();
			fReader.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	class OutputCollector extends Thread
	{
		Process subProcess;
		protected OutputCollector(Process subProcess)
		{
			this.subProcess = subProcess;
		}

		public void run()
		{
			try
			{
				InputStream in = subProcess.getInputStream();
		        BufferedReader br = new BufferedReader(new InputStreamReader(in));
		        String str;
		        while((str = br.readLine()) != null)
		        {
		        	if(localServerOutputArea.getText().equals(""))
		        		localServerOutputArea.setText(str);
		        	else localServerOutputArea.setText(localServerOutputArea.getText() + "\n" + str);
		        	// localServerOutputArea.setText(str + "\n" + localServerOutputArea.getText());
		        }
		    }
		    catch(IOException ioe)
		    {
		    	ioe.printStackTrace();
		    }
		}


		/**
		* Finalize method, called by garbage collector
		*/
		protected void finalize()
		{
			System.out.println("Garbage Collected: OutputCollector(thread)");
		}
	}

	class ErrorCollector extends Thread
	{
		Process subProcess;
		protected ErrorCollector(Process subProcess)
		{
			this.subProcess = subProcess;
		}

		public void run()
		{
			try
			{
				InputStream err = subProcess.getErrorStream();
		        BufferedReader berr = new BufferedReader(new InputStreamReader(err));
		        String str;
		        while((str = berr.readLine()) != null)
		        {
		        	if(localServerOutputArea.getText().equals(""))
		        		localServerOutputArea.setText(str);
		        	else localServerOutputArea.setText(localServerOutputArea.getText() + "\n" + str);
		        	// localServerOutputArea.setText(str + "\n" + localServerOutputArea.getText());
		        }
		    }
		    catch(IOException ioe)
		    {
		    	ioe.printStackTrace();
		    }
		}


		/**
		* Finalize method, called by garbage collector
		*/
		protected void finalize()
		{
			System.out.println("Garbage Collected: ErrorCollector(thread)");
		}
	}
}