package tcfc.run;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import tcfc.data.JiraData;
import tcfc.gui.ControlPanel;
import tcfc.gui.DefaultPanel;
import tcfc.gui.Footer;
import tcfc.gui.Frame;
import tcfc.gui.ScrollPane;
import tcfc.gui.SplitPane;
import tcfc.gui.Table;
import tcfc.gui.TopMenu;

public class Start {

	private Frame mainframe;
	private TopMenu topmenu;
	private Footer footer;
	private SplitPane splitpane, controlsplit;
	private Table datatable;
	private DefaultPanel defaultlpanel;
	private ScrollPane defaultpanelscroll, tablescroll;
	private ControlPanel controlpanel;

	private void Run() {

		mainframe = new Frame();
		datatable = new Table(mainframe);

		tablescroll = new ScrollPane(datatable);

		defaultlpanel = new DefaultPanel(datatable);
		topmenu = new TopMenu(datatable, mainframe, defaultlpanel);
		mainframe.getContentPane().add(topmenu, BorderLayout.NORTH);

		controlpanel = new ControlPanel(datatable, mainframe, defaultlpanel);

		defaultpanelscroll = new ScrollPane(defaultlpanel);

		controlsplit = new SplitPane(tablescroll, controlpanel);

		splitpane = new SplitPane(controlsplit, defaultpanelscroll);
		mainframe.getContentPane().add(splitpane, BorderLayout.CENTER);

		footer = new Footer();
		mainframe.getContentPane().add(footer, BorderLayout.SOUTH);

		datatable.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				mainframe.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				mainframe.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

		checkOutputDirectory();
		JiraData.setChanged(false);
		mainframe.setVisibility();
	}

	private void checkOutputDirectory() {
		File output = new File(Paths.get("").toAbsolutePath().toString() + "/output");
		if (!output.exists()) {
			output.mkdirs();
		}
	}

	public static void main(String[] args) {

		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}

		Start start = new Start();
		start.Run();

	}

}
