package tcfc.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class ControlPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int totaltc, totalac, maxnumber, empty;
	private JLabel tc, ac, mn, em;
	
	public ControlPanel(Table datatable, Frame frame, DefaultPanel dp) {
		setLayout(new GridLayout(1,3));
		
		JPanel left = new JPanel();
		left.setLayout(new FlowLayout(FlowLayout.LEFT));
		Button delete = new Button(" Delete","delete_icon",datatable, 77);
		delete.setName("delete");
		delete.addActionListener(delete.deleteAction(datatable, frame));
		
		Button deleteall = new Button(" Delete All","deleteall_icon",datatable, 90);
		deleteall.addActionListener(deleteall.deleteAllAction(datatable, frame));
		left.add(deleteall);
		left.add(delete);
		add(left);
		
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(2,2));
		tc = new JLabel("", SwingConstants.CENTER);
		ac = new JLabel("", SwingConstants.CENTER);
		mn = new JLabel("", SwingConstants.CENTER);
		em = new JLabel("", SwingConstants.CENTER);
		center.add(tc);
		center.add(ac);
		center.add(mn);
		center.add(em);
		add(center);
		
		
		JPanel right = new JPanel();
		right.setLayout(new FlowLayout(FlowLayout.RIGHT));
		Button clone = new Button(" Clone", "clone_icon", datatable, 72);
		clone.addActionListener(clone.cloneAction(datatable, frame));
		right.add(clone);
		
		Button add = new Button(" Add row","add_icon",datatable, 85);
		add.addActionListener(add.addAction(datatable, frame, dp));
		right.add(add);
		add(right);
		
		refreshLabelInfo();

		datatable.getModel().addTableModelListener(tableChangesRevalidate(datatable));
	}
	
	private void refreshLabelInfo() {
		tc.setText("Test Cases: "+totaltc);
		ac.setText("Actions: "+totalac);
		em.setText("Empty TCID: "+empty);
		mn.setText("Max. Number: "+maxnumber);
	}
	

	private TableModelListener tableChangesRevalidate(Table datatable) {
		TableModelListener tableChangesRevalidate = new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						
						totaltc = 0; maxnumber = 0; empty = 0;						
						totalac = datatable.getRowCount();
						if (totalac == 0) {
							refreshLabelInfo();
						} else {
							
							int columnnumber = 0;
							while (!datatable.getColumnName(columnnumber).equals("TCID")) {
								columnnumber++;
							}
							
							Set<Integer> tcidset = new TreeSet<>();
							
							for (int i=0;i<datatable.getRowCount();i++) {
								String tcidvalue = datatable.getValueAt(i, columnnumber).toString();
								if (tcidvalue.isEmpty() || tcidvalue.length()==0 || tcidvalue.chars().allMatch( Character::isDigit )==false) {
									empty++;
									tcidset.add(0);
								} else {
									tcidset.add(Integer.valueOf(tcidvalue));
								}
				
							}
							
							totaltc = tcidset.size();
							if(tcidset.size()>0) {
								maxnumber = Collections.max(tcidset);
							} else {
								maxnumber = 0;
							}						
							
							refreshLabelInfo();
							
						}						
					}
				});	
				
			}
			
		};
		return tableChangesRevalidate;
	}

}
