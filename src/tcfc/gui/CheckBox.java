package tcfc.gui;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

import tcfc.data.JiraData;

public class CheckBox extends JCheckBox{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CheckBox(String title, boolean selected, boolean enabled, Component co, Table datatable, String columnname) {
		
		setText(title);
		setName(columnname);
		setSelected(selected);
		setEnabled(enabled);
		if (enabled == false) {
			co.setEnabled(true);
		} else {
			co.setEnabled(false);
		}
		addItemListener(new ItemListener() {
		      public void itemStateChanged(ItemEvent e) {
		    	
		    	int columnnumber = datatable.getColumnModel().getColumnIndex(columnname);
		    	  
		        if(isSelected()==true) {
		        	co.setEnabled(true);
		        	
					datatable.getColumnModel().getColumn(columnnumber).setMaxWidth(JiraData.columnWidths.get(columnname)[2]);
					datatable.getColumnModel().getColumn(columnnumber).setPreferredWidth(JiraData.columnWidths.get(columnname)[1]);
					datatable.getColumnModel().getColumn(columnnumber).setMinWidth(JiraData.columnWidths.get(columnname)[0]);
					JiraData.columnSelected.replace(getName(), true);
					
		        } else {
		        	co.setEnabled(false);
		        	datatable.getColumnModel().getColumn(columnnumber).setMinWidth(0);
					datatable.getColumnModel().getColumn(columnnumber).setMaxWidth(0);
		        	datatable.getColumnModel().getColumn(columnnumber).setPreferredWidth(0);
		        	JiraData.columnSelected.replace(getName(), false);
		        }
		      }
		    });
		
	}
}
