package tcfc.gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class SplitPane extends JSplitPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean firststart = true;
	
	
	public SplitPane(SplitPane controlsplit, ScrollPane defaultpanelscroll) { //ScrollPane tablescroll
		setLeftComponent(controlsplit);
		setRightComponent(defaultpanelscroll);
		setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		setOneTouchExpandable(true);
		setDividerSize(10);
		setEnabled(false);
		
		BasicSplitPaneUI ui = (BasicSplitPaneUI) getUI();
		BasicSplitPaneDivider divider = ui.getDivider();

		JButton leftarrow = (JButton) divider.getComponent(0);
		leftarrow.setEnabled(false);
		JButton rightarrow = (JButton) divider.getComponent(1);
		leftarrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				leftarrow.setEnabled(false);
				rightarrow.setEnabled(true);
			}
		});
		rightarrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				leftarrow.setEnabled(true);
				rightarrow.setEnabled(false);
			}
		});
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (firststart == true) {
					setDividerLocation(0.70);
					firststart = false;
				}					
					setResizeWeight(1);				
			}
		});
	}
	
	public SplitPane(ScrollPane tablescroll, ControlPanel controlpanel) {
		setOrientation(JSplitPane.VERTICAL_SPLIT);
		setTopComponent(tablescroll);
		setBottomComponent(controlpanel);
		setEnabled(false);
		setOneTouchExpandable(false);
		setDividerSize(1);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (firststart == true) {
					setDividerLocation(0.92);
					firststart = false;
				}					
					setResizeWeight(1);				
			}
		});
	}


}
