package tcfc.gui;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import tcfc.data.ProgramData;

public class Footer extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Footer(){
		setLayout(new GridLayout(1,2));
		setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		add(new JLabel(ProgramData.getAuthor(), SwingConstants.LEFT));
		add(new JLabel(ProgramData.getVersion(), SwingConstants.RIGHT));
	}

}
