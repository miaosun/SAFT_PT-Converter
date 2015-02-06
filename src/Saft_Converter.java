import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class Saft_Converter {

	public static void main(String[] args) throws IOException {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {		
				JFrame frame = new MainFrame("SAFT PT Converter");
				frame.setSize(400, 400);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}	
		});
		
	}
	
}
