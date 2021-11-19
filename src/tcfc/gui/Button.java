package tcfc.gui;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import tcfc.data.JiraData;


public class Button extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public Button() {
		
	}
	
	public Button(String name, String icon, Table datatable, int width) {
		setIcon(new ImageIcon("img/" + icon + ".png"));
		setText(name);
		setMargin(new Insets(0, 0, 0, 0));
		setPreferredSize(new Dimension(width, 40));
	}
	
	protected ActionListener deleteAction(Table datatable, Frame frame) {
		ActionListener deleteAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (datatable.isRowSelected(datatable.getSelectedRow())==true) {
					int row = datatable.getSelectedRow();
					DefaultTableModel model = (DefaultTableModel) datatable.getModel();
					model.removeRow( row );
					JiraData.setChanged(true);
					if (datatable.getRowCount()>0 && row != 0) {
						datatable.setRowSelectionInterval(row-1, row-1);
					}
				} else {
					JOptionPane.showMessageDialog(frame, "No data selected.", "TCFM", JOptionPane.INFORMATION_MESSAGE);
				}

			}
		};
		return deleteAction;
	}
	
	protected ActionListener deleteAllAction(Table datatable, Frame frame) {
		ActionListener deleteAllAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (datatable.getRowCount()>0) {
					int input = JOptionPane.showConfirmDialog(frame, "All unsaved data will be deleted.\nDo you want to continue??", "TCFM", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					switch (input){
					case 0: 
						DefaultTableModel model = (DefaultTableModel) datatable.getModel();
						int rows = datatable.getRowCount()-1;
						for (int i = 0; i <= rows; i++) {
							model.removeRow(rows-i);
						}
						JiraData.setChanged(true);
						break;
					case 1: break;
					default: break;
					}
				} else {
					JOptionPane.showMessageDialog(frame, "No data to delete.", "TCFM", JOptionPane.INFORMATION_MESSAGE);
				}				

			}
		};
		return deleteAllAction;
	}
	
	protected ActionListener addAction(Table datatable, Frame frame, DefaultPanel dp) {
		ActionListener addAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (datatable.getRowCount()<JiraData.tableLimit) {
					DefaultTableModel model = (DefaultTableModel) datatable.getModel();
					model.addRow(dp.getNewRow());
					datatable.setRowSelectionInterval(datatable.getRowCount()-1, datatable.getRowCount()-1);
					JiraData.setChanged(true);
				} else {
					JOptionPane.showMessageDialog(frame, "The maximum number of rows is "+JiraData.tableLimit+".", "TCFM", JOptionPane.INFORMATION_MESSAGE);
				}

			}
		};
		return addAction;
	}
	
	protected ActionListener cloneAction(Table datatable, Frame frame) {
		ActionListener cloneAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (datatable.getSelectionModel().isSelectionEmpty()==true) {
					JOptionPane.showMessageDialog(frame, "No row is selected.\nSelect row to clone first.", "TCFM", JOptionPane.INFORMATION_MESSAGE);
				} else {
					DefaultTableModel model = (DefaultTableModel) datatable.getModel();
					
					@SuppressWarnings("unchecked")
					Vector<Object> clonerow = new Vector<Object>(model.getDataVector().elementAt(datatable.getSelectedRow()));
					
					model.insertRow(datatable.getSelectedRow()+1, clonerow);
					datatable.setRowSelectionInterval(datatable.getSelectedRow()+1, datatable.getSelectedRow()+1);
					JiraData.setChanged(true);
				}			

			}
		};
		return cloneAction;
	}
	

}
