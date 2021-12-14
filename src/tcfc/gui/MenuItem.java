package tcfc.gui;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import tcfc.data.JiraData;

public class MenuItem extends JMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MenuItem(String title, String icon) {
		setText(title);
		this.setIcon(new ImageIcon("img/" + icon + ".png"));
	}

	protected ActionListener openProjects(Frame frame) {
		ActionListener openProjects = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				File output = new File(Paths.get("").toAbsolutePath().toString() + "/output");
				if (!output.exists()) {
					output.mkdirs();
				}

				try {
					Desktop.getDesktop().open(new File(Paths.get("").toAbsolutePath().toString() + "/output"));
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frame, "Failed to open the Projects Location.", "TCFM",
							JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (IllegalArgumentException e2) {
					JOptionPane.showMessageDialog(frame, "Location not found.", "TCFM", JOptionPane.ERROR_MESSAGE);
					e2.printStackTrace();
				}
			}

		};
		return openProjects;
	}

	protected ActionListener openFile(Table datatable, Frame frame) {
		ActionListener openFile = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean emptyline = null;				
				File output = new File(Paths.get("").toAbsolutePath().toString() + "/output");
				if (!output.exists()) {
					output.mkdirs();
				}				
				JFileChooser fc = new JFileChooser(Paths.get("").toAbsolutePath().toString() + "/output");
				fc.setDialogTitle("Choose location.");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("csv", "CSV");
				fc.setFileFilter(filter);
				fc.setAcceptAllFileFilterUsed(false);
				int returnValue = fc.showOpenDialog(frame);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File inputFile = fc.getSelectedFile();
					FileReader reader = null;
					try {
						reader = new FileReader(inputFile);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(frame, "Failed to open the file.\nFile not found.", "TCFM",
								JOptionPane.ERROR_MESSAGE);
						System.exit(1);
					}

					BufferedReader infile = new BufferedReader(reader);
					String line = "";
					String[] columnnames = new String[100];
					String[] nextline = new String[100];
					HashMap<String, String> tempData = new HashMap<String, String>();

					try {
						boolean done = false;
						boolean firstline = true;
						int importedrows = 0;

						while (!done) {
							line = infile.readLine();
							if (line == null || line.length() == 0) {
								done = true;
								if (firstline == false) {
									JOptionPane.showMessageDialog(frame,
											"Number of rows imported: " + importedrows + ".", "TCFM",
											JOptionPane.INFORMATION_MESSAGE);
								} else {
									JOptionPane.showMessageDialog(frame,
											"No matching data was found.\\nCheck the column names in the file.", "TCFM",
											JOptionPane.INFORMATION_MESSAGE);
								}
								break;
							} else {

								if (firstline) {
									columnnames = line.split(";", 100);
									firstline = false;
								} else {

									if (Arrays.asList(columnnames).stream()
											.anyMatch(element -> Arrays.asList(JiraData.columns).contains(element))) {
										nextline = line.split(";", 100);
										for (int i = 0; i < columnnames.length; i++) {
											tempData.put(columnnames[i], nextline[i]);
										}

										if (datatable.getRowCount() < 10000) {
											Vector<String> newrow = new Vector<>();
											for (int i = 0; i < JiraData.columns.length; i++) {

												if (tempData.get(JiraData.columns[i]) != null) {

													if (tempData.get(JiraData.columns[i])
															.length() > JiraData.columnLimits
																	.get(JiraData.columns[i])) {
														tempData.put(JiraData.columns[i],
																tempData.get(JiraData.columns[i]).substring(0,
																		JiraData.columnLimits
																				.get(JiraData.columns[i])));
													}

													if (Arrays.asList(JiraData.digitsonly)
															.contains(JiraData.columns[i])) {
														newrow.add(tempData.get(JiraData.columns[i])
																.replaceAll("[^0-9]", ""));
													} else {
														if (Arrays.asList(JiraData.isEnumValues)
																.contains(JiraData.columns[i])) {
															switch (JiraData.columns[i]) {
															case "Status":
																if (tempData.get(JiraData.columns[i]).length() < 1) {
																	newrow.add(JiraData.Status[0]);
																} else {
																	String input = tempData.get(JiraData.columns[i])
																			.toUpperCase();
																	if (!Arrays.stream(JiraData.Status)
																			.anyMatch(input::equals)) {
																		newrow.add(JiraData.Status[0]);
																	} else {
																		newrow.add(input);
																	}
																}
																break;
															case "Priority":
																if (tempData.get(JiraData.columns[i]).length() < 1) {
																	newrow.add(JiraData.Priority[0]);
																} else {
																	String input = tempData.get(JiraData.columns[i])
																			.substring(0, 1).toUpperCase()
																			+ tempData.get(JiraData.columns[i])
																					.substring(1).toLowerCase();
																	if (!Arrays.stream(JiraData.Priority)
																			.anyMatch(input::equals)) {
																		newrow.add(JiraData.Priority[0]);
																	} else {
																		newrow.add(input);
																	}
																}
																break;
															default:
																break;
															}
														} else {
															newrow.add(tempData.get(JiraData.columns[i]));
														}

													}

												} else {
													newrow.add("");
												}

											}

											if (emptyline == null) {
												int input = JOptionPane.showConfirmDialog(frame,
														"Do You want to import empty rows?", "TCFM",
														JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

												switch (input) {
												case 0:
													emptyline = true;
													break;
												case 1:
													emptyline = false;
													break;
												default:
													break;
												}
											}

											DefaultTableModel model = (DefaultTableModel) datatable.getModel();
											
											if (model.getRowCount()>999) {
												JOptionPane.showMessageDialog(frame,
														"The row limit is 1000.\nThe next data will not be imported.", "TCFM",
														JOptionPane.INFORMATION_MESSAGE);
												break;
											}

											if (emptyline == true) {
												model.addRow(newrow);
												importedrows++;												

											} else {
												if (line.replace(";","").length()<1){
													continue;
												} else {
													model.addRow(newrow);
													importedrows++;
												}
											}
											


										} else {
											JOptionPane.showMessageDialog(frame,
													"The maximum number of rows is 10.000.", "TCFM",
													JOptionPane.INFORMATION_MESSAGE);
										}
									} else {
										JOptionPane.showMessageDialog(frame,
												"No matching data was found.\nCheck the column names in the file.",
												"TCFM", JOptionPane.INFORMATION_MESSAGE);
										break;
									}

								}
							}
						}

					} catch (IOException e2) {
						JOptionPane.showMessageDialog(frame, "Failed to open the file.", "TCFM",
								JOptionPane.ERROR_MESSAGE);
						System.exit(1);
					}
					JiraData.setChanged(true);
				}
			}

		};
		return openFile;
	}

	protected ActionListener saveAs(Table datatable, Frame frame) {
		ActionListener saveAs = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				fc.setDialogTitle("Choose location.");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("csv", "CSV");
				fc.setFileFilter(filter);
				int returnValue = fc.showSaveDialog(frame);
				if (returnValue == JFileChooser.APPROVE_OPTION) {

					String destination = "";

					if (fc.getSelectedFile().toString().endsWith(".csv")) {
						destination = fc.getSelectedFile().toString();
					} else {
						destination = fc.getSelectedFile().toString() + ".csv";
					}

					if (new File(destination).exists()) {
						int input = JOptionPane.showConfirmDialog(frame,
								"The file already exists, do you want to replace it?", "TCFM",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						switch (input) {
						case 0:
							if (toCSV(datatable, destination)) {
								JOptionPane.showMessageDialog(frame, "File has been saved successfully.", "TCFM",
										JOptionPane.INFORMATION_MESSAGE);
								JiraData.setChanged(false);
							} else {
								JOptionPane.showMessageDialog(frame, "Failed to save the file.", "TCFM",
										JOptionPane.ERROR_MESSAGE);
							}
							break;

						case 1:
							break;

						default:
							break;
						}
					} else {
						if (toCSV(datatable, destination)) {
							JOptionPane.showMessageDialog(frame, "File has been saved successfully.", "TCFM",
									JOptionPane.INFORMATION_MESSAGE);
							JiraData.setChanged(false);
						} else {
							JOptionPane.showMessageDialog(frame, "Failed to save the file.", "TCFM",
									JOptionPane.ERROR_MESSAGE);
						}
					}

				}

			}

		};
		return saveAs;
	}

	protected ActionListener quickSave(Table datatable, Frame frame) {
		ActionListener quickSave = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
								
				if(new File(Paths.get("").toAbsolutePath().toString() + "/output/" + JiraData.getProjectName() + ".csv").exists() && JiraData.isQuickSaveExist()==false) {
					
					int input = JOptionPane.showConfirmDialog(frame,
							"The file already exists, do you want to overwrite it?", "TCFM",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					switch (input) {
					case 0:
						frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
						
						File output = new File(Paths.get("").toAbsolutePath().toString() + "/output");
						if (!output.exists()) {
							output.mkdirs();
						}
						
						if (toCSV(datatable,
								Paths.get("").toAbsolutePath().toString() + "/output/" + JiraData.getProjectName() + ".csv")) {
							JiraData.setChanged(false);
							frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						} else {
							frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
							JOptionPane.showMessageDialog(frame, "Failed to save the file.", "TCFM", JOptionPane.ERROR_MESSAGE);
						}
						
						JiraData.setQuickSaveExist(true);
						break;

					case 1:
						break;

					default:
						break;
					}
				} else {
					frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
					
					File output = new File(Paths.get("").toAbsolutePath().toString() + "/output");
					if (!output.exists()) {
						output.mkdirs();
					}
					
					if (toCSV(datatable,
							Paths.get("").toAbsolutePath().toString() + "/output/" + JiraData.getProjectName() + ".csv")) {
						JiraData.setChanged(false);
						frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					} else {
						frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						JOptionPane.showMessageDialog(frame, "Failed to save the file.", "TCFM", JOptionPane.ERROR_MESSAGE);
					}
					
					JiraData.setQuickSaveExist(true);
				}
				
			}

		};
		return quickSave;
	}

	private boolean toCSV(Table datatable, String destination) {

		try {
			TableModel model = datatable.getModel();

			FileWriter outputfile = new FileWriter(new File(destination));

			String delimeter = ";";

			for (int i = 0; i < model.getColumnCount(); i++) {

				if (i == 0) {
					delimeter = "";
				} else {
					delimeter = ";";
				}

				if (JiraData.columnSelected.get(model.getColumnName(i)) == false) {
					continue;
				} else {

					outputfile.write(delimeter + model.getColumnName(i));

				}

			}

			outputfile.write("\n");

			for (int i = 0; i < model.getRowCount(); i++) {

				for (int j = 0; j < model.getColumnCount(); j++) {

					if (j == 0) {
						delimeter = "";
					} else {
						delimeter = ";";
					}

					if (JiraData.columnSelected.get(model.getColumnName(j)) == false) {
						continue;
					} else {

						if (JiraData.defaultValues.containsKey(model.getColumnName(j))
								&& model.getValueAt(i, j).toString().toLowerCase().equals("default")) {
							outputfile.write(delimeter + JiraData.defaultValues.get(model.getColumnName(j)));
						} else {
							outputfile.write(delimeter + model.getValueAt(i, j).toString());
						}

					}

				}

				outputfile.write("\n");
			}

			outputfile.close();
			return true;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
