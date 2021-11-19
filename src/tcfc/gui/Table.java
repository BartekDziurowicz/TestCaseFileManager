package tcfc.gui;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import tcfc.data.JiraData;

public class Table extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[][] data = {};


	private JTextField cellLimits(final int limit, boolean onlydigits) {
		JTextField limitedtf = new JTextField();
		PlainDocument document = (PlainDocument) limitedtf.getDocument();
		document.setDocumentFilter(new DocumentFilter() {

			@Override
			public void insertString(FilterBypass fb, int offset, String str, AttributeSet a)
					throws BadLocationException {

				if ((fb.getDocument().getLength() + str.length()) <= limit) {
					if (onlydigits == true) {
						super.insertString(fb, offset, str.replaceAll("[^0-9]", ""), a);
					} else {
						super.insertString(fb, offset, str.replaceAll(";", ""), a);
					}

				}
			}

			public void replace(FilterBypass fb, int offset, int length, String str, AttributeSet a)
					throws BadLocationException {

				if ((fb.getDocument().getLength() + str.length() - length) <= limit) {
					if (onlydigits == true) {
						super.replace(fb, offset, length, str.replaceAll("[^0-9]", ""), a);
					} else {
						super.replace(fb, offset, length, str.replaceAll(";", ""), a);
					}
				}
			}

		});
		return limitedtf;
	}

	public Table(Frame frame) {
		DefaultTableModel model = new DefaultTableModel(data, JiraData.columns);

		setModel(model);
		setAutoCreateRowSorter(true);
		setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		for (int i = 0; i < JiraData.columns.length; i++) {

			boolean digits;

			if (Arrays.asList(JiraData.digitsonly).contains(JiraData.columns[i])) {
				digits = true;
			} else {
				digits = false;
			}

			getColumnModel().getColumn(i).setCellEditor(
					new DefaultCellEditor(cellLimits(JiraData.columnLimits.get(getColumnName(i)), digits)));

			if (Arrays.asList(JiraData.mandatory).contains(JiraData.columns[i])) {
				//getColumnModel().getColumn(i).setMinWidth(200);
				//getColumnModel().getColumn(i).setPreferredWidth(200);
				//if (JiraData.columns[i].contains("TCID")) {
				//	getColumnModel().getColumn(i).setMinWidth(35);
				//	getColumnModel().getColumn(i).setMaxWidth(35);
				//	getColumnModel().getColumn(i).setPreferredWidth(35);
				//}
				//if (JiraData.columns[i].contains("Priority")) {
				//	getColumnModel().getColumn(i).setMinWidth(100);
				//	getColumnModel().getColumn(i).setPreferredWidth(100);
				//}
				getColumnModel().getColumn(i).setMinWidth(JiraData.columnWidths.get(JiraData.columns[i])[0]);
				getColumnModel().getColumn(i).setMaxWidth(JiraData.columnWidths.get(JiraData.columns[i])[2]);
				getColumnModel().getColumn(i).setPreferredWidth(JiraData.columnWidths.get(JiraData.columns[i])[1]);
			} else {
				getColumnModel().getColumn(i).setMinWidth(0);
				getColumnModel().getColumn(i).setMaxWidth(0);
				getColumnModel().getColumn(i).setPreferredWidth(0);
			}

		}

		addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {

				if (evt.getNewValue() == null) {
					String columnname = getColumnName(getSelectedColumn());
					if (Arrays.asList(JiraData.isEnumValues).contains(columnname)) {
						String currentstring = getValueAt(getSelectedRow(), getSelectedColumn()).toString();

						switch (columnname) {

						case "Priority":

							if (currentstring.length() < 1) {
								setValueAt(JiraData.Priority[0], getSelectedRow(), getSelectedColumn());
								JOptionPane.showMessageDialog(frame, "Wrong input. Only 'Priority' values are allowed.",
										"TCFM", JOptionPane.INFORMATION_MESSAGE);
							} else {
								currentstring = currentstring.substring(0, 1).toUpperCase()
										+ currentstring.substring(1).toLowerCase();
								if (!Arrays.stream(JiraData.Priority).anyMatch(currentstring::equals)) {
									setValueAt(JiraData.Priority[0], getSelectedRow(), getSelectedColumn());
									JOptionPane.showMessageDialog(frame,
											"Wrong input. Only 'Priority' values are allowed.", "TCFM",
											JOptionPane.INFORMATION_MESSAGE);
								} else {

									setValueAt(currentstring, getSelectedRow(), getSelectedColumn());
								}
							}
							break;

						case "Status":
							if (currentstring.length() < 1) {
								setValueAt(JiraData.Status[0], getSelectedRow(), getSelectedColumn());
								JOptionPane.showMessageDialog(frame, "Wrong input. Only 'Status' values are allowed.",
										"TCFM", JOptionPane.INFORMATION_MESSAGE);
							} else {
								currentstring = currentstring.toUpperCase();
								if (!Arrays.stream(JiraData.Status).anyMatch(currentstring::equals)) {
									setValueAt(JiraData.Status[0], getSelectedRow(), getSelectedColumn());
									JOptionPane.showMessageDialog(frame,
											"Wrong input. Only 'Status' values are allowed.", "TCFM",
											JOptionPane.INFORMATION_MESSAGE);
								} else {
									setValueAt(currentstring, getSelectedRow(), getSelectedColumn());
								}
							}
							break;

						default:
							break;

						}

					}
				}
				

			}

		});

		addMouseListener(new MouseListener() {

			int rowselected = 0;

			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				rowselected = getSelectedRow();

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) getModel();

				try {
					model.moveRow(rowselected, rowselected, rowAtPoint(e.getPoint()));
				} catch (ArrayIndexOutOfBoundsException exception) {

				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

		});

		
		
		getColumnModel().addColumnModelListener(new TableColumnModelListener() {

			@Override
			public void columnAdded(TableColumnModelEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void columnRemoved(TableColumnModelEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void columnMoved(TableColumnModelEvent e) {
				JiraData.setChanged(true);
			}

			@Override
			public void columnMarginChanged(ChangeEvent e) {				
				
				if (getPreferredSize().width < getWidth()) {
					setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				} else {
					setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				}				
				
				JiraData.setChanged(true);
			}

			@Override
			public void columnSelectionChanged(ListSelectionEvent e) {

			}

		});
	}

}
