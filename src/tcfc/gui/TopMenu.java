package tcfc.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;

public class TopMenu extends JMenuBar{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TopMenu(Table datatable, Frame frame, DefaultPanel dp) {
		JMenu file = new JMenu("File");
		add(file);
		
		MenuItem openfile = new MenuItem("Open file", "openfile_icon");
		openfile.addActionListener(openfile.openFile(datatable, frame));
		openfile.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.ALT_MASK));
		
		MenuItem openprojects = new MenuItem("Open projects", "openprojects_icon_tp");
		openprojects.addActionListener(openprojects.openProjects(frame));
		openprojects.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_P, ActionEvent.ALT_MASK));
		
		MenuItem save = new MenuItem("Save", "save_icon_tp");
		save.addActionListener(save.quickSave(datatable, frame));
		save.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_S, ActionEvent.ALT_MASK));
		
		MenuItem saveas = new MenuItem("Save as...", "saveas_icon_tp");
		saveas.addActionListener(saveas.saveAs(datatable, frame));
		saveas.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_E, ActionEvent.ALT_MASK));
		
		file.add(openfile);
		file.add(openprojects);
		file.add(save);
		file.add(saveas);
		
		Button getListeners = new Button();
		
		JMenu actions = new JMenu("Actions");
		add(actions);
		
		MenuItem add = new MenuItem("Add", "add_icon_tp");
		add.addActionListener(getListeners.addAction(datatable, frame, dp));
		add.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_A, ActionEvent.ALT_MASK));
		actions.add(add);
		
		MenuItem clone = new MenuItem("Clone", "clone_icon_tp");
		clone.addActionListener(getListeners.cloneAction(datatable, frame));
		clone.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_C, ActionEvent.ALT_MASK));
		actions.add(clone);
		
		MenuItem delete = new MenuItem("Delete", "delete_icon_tp");
		delete.addActionListener(getListeners.deleteAction(datatable, frame));
		delete.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_D, ActionEvent.ALT_MASK));
		actions.add(delete);
		
		MenuItem deleteall = new MenuItem("Delete all", "deleteall_icon_tp");
		deleteall.addActionListener(getListeners.deleteAllAction(datatable, frame));
		deleteall.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_L, ActionEvent.ALT_MASK));
		actions.add(deleteall);
		
		
		//JMenu help = new JMenu("Help");
		//add(help);
		
		//MenuItem helpitem = new MenuItem("Instruction", "help_icon_tp");
		//helpitem.setAccelerator(KeyStroke.getKeyStroke(
		//        KeyEvent.VK_H, ActionEvent.ALT_MASK));
		//help.add(helpitem);
		
	}

}
