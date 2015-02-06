import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainFrame(String title) {
		setTitle(title);

		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());

		JTextArea text = new JTextArea();
		text.setEditable(false);
		text.setText("Thanks for using, any problem contact the author: Miao Sun\nWechat: 261125225\nEmail: miaosun88@gmail.com\n");
		container.add(text, BorderLayout.NORTH);
		
		//
		// choose file area
		//
		JPanel filePanel = new JPanel();
		JButton chooseButton = new JButton("Choose file");
		chooseButton.setToolTipText("Choose the saft pt file to convert");
		filePanel.add(chooseButton);
		
		container.add(filePanel, BorderLayout.CENTER);

		chooseButton.addActionListener(new ChooseFileActionListener());
		
		//
		// message
		//
		JTextArea msg = new JTextArea();
		msg.setEditable(false);
		container.add(msg, BorderLayout.SOUTH);

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
			JOptionPane.showMessageDialog(rootPane, "SAFT PT Converter ver 1.0\nThanks for using, enjoy!\nAny problem contact the author\nMiao Sun: m.sun@cranfield.ac.uk");
		}

	}
	
	/**
	 *  When the "About" menu option is clicked, some useful information will show up in a new dialog window.
	 */
	public class ChooseFileActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}

	}
	
	
	public void processFile() {
		
	}
}
