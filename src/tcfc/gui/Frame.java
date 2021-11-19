package tcfc.gui;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import tcfc.data.JiraData;

public class Frame extends JFrame implements WindowListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void setVisibility() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setVisible(true);
			}
		});
	}
	
	public Frame() {
		this.setTitle("Test Case File Manager");
		this.setIconImage(new ImageIcon("img/test.png").getImage());
		this.setMinimumSize(new Dimension(1280,720));	
		this.setMaximumSize(new Dimension(1920,1080));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {

		if (JiraData.isChanged() == true) {
			int input = JOptionPane.showConfirmDialog(this, "The changes were not saved.\nDo You want to save changes before exiting?", "TCFM", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			switch (input){
			case 0: break;
			case 1: System.exit(0); break;
			default: break;
			}
		} else {
			System.exit(0);
		}		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
