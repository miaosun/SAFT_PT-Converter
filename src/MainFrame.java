import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	JButton chooseButton;
	JButton convertButton;
	JTextArea msg;
	//Create a file chooser
	JFileChooser fc = new JFileChooser();
	File file;
	String filename;
	File path;

	public MainFrame(String title) {
		setTitle(title);

		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());

		JTextArea text = new JTextArea();
		text.setEditable(false);
		text.setText("Thanks for using, copyright reserved.\nAuthor: Miao Sun\nWechat: 261125225\nEmail: miaosun88@gmail.com\n\t\tWarning:\nCan only be used for SAPT PT XML files, don't try other files!\n");
		container.add(text, BorderLayout.NORTH);

		//
		// choose file area + convert file
		//
		JPanel filePanel = new JPanel();
		chooseButton = new JButton("Choose file...");
		convertButton = new JButton("Convert");

		chooseButton.setToolTipText("Choose the saft pt file to convert");
		convertButton.setToolTipText("Convert the selected saft pt file");
		filePanel.add(chooseButton);
		filePanel.add(convertButton);

		container.add(filePanel, BorderLayout.CENTER);		
		chooseButton.addActionListener(new ChooseFileActionListener());
		convertButton.addActionListener(new convertActionListener());

		//
		// message
		//
		msg = new JTextArea(13,10);
		msg.setEditable(false);

		JScrollPane msgPane = new JScrollPane(msg);

		container.add(msgPane, BorderLayout.SOUTH);

		// Menu bar
		JMenuBar menuBar = new JMenuBar();

		//
		// File Menu, option with load data from file or save data to file, exit program
		//
		JMenu fileMenu = new JMenu("File");
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);

		//
		//action listener for file menu options
		//
		exitMenuItem.addActionListener(new ExitActionListener());

		menuBar.add(fileMenu);

		//
		// Option menu item with options to change look and feel of the program
		//
		setupLookAndFeelMenu(menuBar);

		//
		// Help Menu, with "About" menu item
		//
		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new AboutActionListener());
		helpMenu.add(aboutMenuItem);

		menuBar.add(helpMenu);

		this.setJMenuBar(menuBar);

		this.pack();

	}


	/**
	 *  GIVEN:  Look And Feel
	 *
	 *  Builds a menu based on the installed look and feels.  This method loops
	 *  through each installed look and feel and creates a menu item w/ the name
	 *  of the look and feel.  We also keep track of the class name by setting the
	 *  action command for the menu item.  This allows the associated listener to
	 *  determine the class name to load for the look and feel.  <p>  
	 *
	 *  More info on look and feels see the "LookAndFeelListener" at the bottom of the file.
	 *
	 */
	protected void setupLookAndFeelMenu(JMenuBar theMenuBar) {

		UIManager.LookAndFeelInfo[] lookAndFeelInfo = UIManager.getInstalledLookAndFeels();
		JMenu lookAndFeelMenu = new JMenu("Options");
		JMenuItem anItem = null;
		LookAndFeelListener myListener = new LookAndFeelListener();

		try {
			for (int i=0; i < lookAndFeelInfo.length; i++) {
				anItem = new JMenuItem(lookAndFeelInfo[i].getName() + " Look and Feel");
				anItem.setActionCommand(lookAndFeelInfo[i].getClassName());
				anItem.addActionListener(myListener);

				lookAndFeelMenu.add(anItem);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		theMenuBar.add(lookAndFeelMenu);
	}

	/**
	 *  Listener class to set the look and feel at run time.
	 *
	 *  Changing the look and feel is so simple :-)  Just call the
	 *  method:
	 *
	 *  <code>
	 *     UIManager.setLookAndFeel(<class name of look and feel>);
	 *  </code>
	 *
	 *
	 *  The available look and feels are:
	 *
	 * <pre>
	 *
	 *    NAME             CLASS NAME
	 *    --------         -----------
	 *    Metal            javax.swing.plaf.metal.MetalLookAndFeel
	 *    Windows          com.sun.java.swing.plaf.windows.WindowsLookAndFeel
	 *    Motif            com.sun.java.swing.plaf.motif.MotifLookAndFeel
	 *
	 *  </pre>
	 *
	 *  There is also an additional Mac look and feel that you can download
	 *  from Sun's web site.
	 *
	 */
	class LookAndFeelListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {

			// get the class name to load
			String className = event.getActionCommand();		

			try {
				// set the look and feel
				UIManager.setLookAndFeel(className);

				// update our component tree w/ new look and feel
				SwingUtilities.updateComponentTreeUI(MainFrame.this);				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 *  When the "Exit" menu option is clicked, the application will be closed.
	 */
	public class ExitActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			dispose();
			System.exit(0);
		}

	}

	/**
	 *  When the "About" menu option is clicked, some useful information will show up in a new dialog window.
	 */
	public class AboutActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(rootPane, "SAFT PT Converter ver 1.0\nThanks for using, enjoy!\nCopyright reserved.\nAuthor: Miao Sun\nWechat: 261125225\nEmail: miaosun88@gmail.com");
		}

	}

	/**
	 *  When the "About" menu option is clicked, some useful information will show up in a new dialog window.
	 */
	public class ChooseFileActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			int returnVal = fc.showOpenDialog(MainFrame.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file = fc.getSelectedFile();
				path = fc.getCurrentDirectory();
				filename = file.getName();
				String file_extension = filename.substring(filename.length()-4);

				if(!file_extension.equals(".xml") && !file_extension.equals(".XML"))
					JOptionPane.showMessageDialog(rootPane, "Error: must be SAFT PT XML file!");
				else
					msg.append("Opening: " + file.getName() + ".\n");
			} else {
				msg.append("Open command cancelled by user.\n");
			}
			msg.setCaretPosition(msg.getDocument().getLength());
		}
	}


	public class convertActionListener implements ActionListener {
		//Scanner sc = new Scanner(System.in);

		//System.out.println("Please insert the file name to be converted: ");
		//String filename = sc.nextLine();
		@Override
		public void actionPerformed(ActionEvent e) {
			filename = file.getName();
			String file_extension = filename.substring(filename.length()-4);

			if(!file_extension.equals(".xml") && !file_extension.equals(".XML"))
				JOptionPane.showMessageDialog(rootPane, "Error: Select a SAFT PT XML file first!");
			else
			{
				try {
					String line = "";
					String line2 = "";

					BufferedReader fromFile0 = new BufferedReader(new FileReader(file));
					BufferedReader fromFile = new BufferedReader(new FileReader(file));

					double sum = 0.0;
					while((line = fromFile0.readLine()) != null)
					{
						if(line.contains("<GrossTotal>"))
						{
							line2 = line.substring(line.indexOf("al>")+3, line.indexOf("</Gr"));

							sum += Double.parseDouble(line2);
						}
					}

					fromFile0.close();

					FileWriter xmlWriter = new FileWriter(path+"/NEW_"+filename, false);

					double price = 0.0;
					while((line = fromFile.readLine()) != null)
					{
						if(line.contains("<TaxPayable>"))
						{
							xmlWriter.write("                    <TaxPayable>0.0000</TaxPayable>\n");
						}
						else if(line.contains("<TotalCredit>0</TotalCredit>"))
						{
							xmlWriter.write(line+"\n");
						}
						else if(line.contains("<TotalCredit>"))
						{
							xmlWriter.write("            <TotalCredit>"+new DecimalFormat("#0.0000").format(sum)+"</TotalCredit>\n");
						}
						else if(line.contains("<UnitPrice>"))
						{
							line2 = line.substring(line.indexOf("ce>")+3, line.indexOf("</Uni"));
							price = Double.parseDouble(line2) * 1.23;
							line2 = new DecimalFormat("#0.00").format(price);
							price = Double.parseDouble(line2);
							xmlWriter.write("                    <UnitPrice>"+new DecimalFormat("#0.0000").format(price)+"</UnitPrice>\n");
						}
						else if(line.contains("<CreditAmount>"))
						{
							line2 = line.substring(line.indexOf("nt>")+3, line.indexOf("</Cre"));
							price = Double.parseDouble(line2) * 1.23;
							line2 = new DecimalFormat("#0.00").format(price);
							price = Double.parseDouble(line2);
							xmlWriter.write("                    <CreditAmount>"+new DecimalFormat("#0.0000").format(price)+"</CreditAmount>\n");
						}
						else if(line.contains("<NetTotal>"))
						{
							//line = fromFile.readLine();
						}
						else if(line.contains("<GrossTotal>"))
						{
							line2 = line.substring(line.indexOf("al>")+3, line.indexOf("</Gr"));
							xmlWriter.write("                    <NetTotal>"+line2+"</NetTotal>\n");
							xmlWriter.write(line+"\n");
						}
						else
							xmlWriter.write(line+"\n");
					}

					fromFile.close();
					xmlWriter.close();
					msg.append("New_"+filename + " generated successfully!\n");

				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(rootPane, "No such file or directory! Try again!");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(rootPane, e1);
				}
			}
		}
	}
}
