package tcfc.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;


import tcfc.data.JiraData;

public class DefaultPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CheckBox summary, status, priority, assignee, reporter, fix_version, description, comment, labels, action, data, expected_result, repo_path, tcid;
	private TextArea sumTxArea, assTxArea, repTxArea, fvTxArea, desTxArea, comTxArea, labTxArea, actionTxArea, datTxArea, exrTxArea, rpaTxArea, tcidTxArea, titleTxArea;
	private JComboBox<?> statuscb, prioritycb;	

	
	private Border getBorder(String title) {
		Color bordercolor = new Color(15, 115, 132);
		Border border = BorderFactory.createLineBorder(bordercolor);		
		border = BorderFactory.createTitledBorder(border, title);
		((javax.swing.border.TitledBorder) border).setTitleColor(bordercolor);
		((javax.swing.border.TitledBorder) border).setTitleFont(new Font("Arial", Font.BOLD, 14));
		border = new CompoundBorder(border, BorderFactory.createEmptyBorder(1, 3, 8, 3));
		return border;
	}
	
	private JPanel topicPanel(String title) {
		JPanel topicpanel = new JPanel();
		topicpanel.setLayout(new BoxLayout(topicpanel, BoxLayout.Y_AXIS));	
		topicpanel.setBorder(getBorder(title));
		return topicpanel;
	}
	
	@SuppressWarnings("unused")
	private String getSelected(JComboBox<?> cb) {
		String selected = cb.getSelectedItem().toString();
		if (selected.equals("None") || selected.equals("NONE")) {
			selected = "";}
		return selected;
	}
	
	protected Vector<String> getNewRow(){
		Vector<String> newrow = new Vector<>();
		newrow.add(tcidTxArea.getText());
		newrow.add(sumTxArea.getText());
		newrow.add(actionTxArea.getText());		
		newrow.add(getSelected(prioritycb));
		newrow.add(getSelected(statuscb));
		newrow.add(assTxArea.getText());
		newrow.add(repTxArea.getText());
		newrow.add(fvTxArea.getText());
		newrow.add(desTxArea.getText());
		newrow.add(comTxArea.getText());
		newrow.add(labTxArea.getText());		
		newrow.add(datTxArea.getText());
		newrow.add(exrTxArea.getText());
		newrow.add(rpaTxArea.getText());		
		return newrow;
	}	

	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DefaultPanel(Table datatable){
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		//**************************************************************************************************
		
		JPanel titlepanel = topicPanel("Project name");
		titleTxArea = new TextArea(1, 100, false, false);
		titleTxArea.addFocusListener(titleTxArea.setProjectTitleFromTA());
		titlepanel.add(titleTxArea);
		
		add(titlepanel);
		add(Box.createVerticalStrut(10));
		
		//**************************************************************************************************
		
		JPanel mandatoryfieldspanel = topicPanel("Mandatory Columns");
		
		tcidTxArea = new TextArea(1, JiraData.columnLimits.get("TCID"), true, true);
		tcid = new CheckBox("Test Case Identifier", true, false, tcidTxArea, datatable, "TCID");
	
		mandatoryfieldspanel.add(tcid);
		mandatoryfieldspanel.add(tcidTxArea);
		
		sumTxArea = new TextArea(2, JiraData.columnLimits.get("Summary"), true, false);				
		summary = new CheckBox("Summary", true, false, sumTxArea, datatable, "Summary");	
		mandatoryfieldspanel.add(summary);
		mandatoryfieldspanel.add(sumTxArea);
		
		actionTxArea = new TextArea(5, JiraData.columnLimits.get("Action"), true, false);		
		action = new CheckBox("Action", true, false, actionTxArea, datatable, "Action");			
		mandatoryfieldspanel.add(action);
		mandatoryfieldspanel.add(actionTxArea);
		
		prioritycb = new JComboBox(JiraData.Priority);
		priority = new CheckBox("Priority", true, false, prioritycb, datatable, "Priority");
		prioritycb.setBorder(BorderFactory.createLineBorder(Color.RED,1));
		mandatoryfieldspanel.add(priority);
		prioritycb.setAlignmentX(LEFT_ALIGNMENT);
		mandatoryfieldspanel.add(prioritycb);
		
		add(mandatoryfieldspanel);
		add(Box.createVerticalStrut(10));		
		
		JPanel jirafieldspanel = topicPanel("Jira Fields");
		
		statuscb = new JComboBox(JiraData.Status);
		statuscb.setAlignmentX(LEFT_ALIGNMENT);
		status = new CheckBox("Status", false, true, statuscb, datatable, "Status");		
		jirafieldspanel.add(status);
		jirafieldspanel.add(statuscb);
				
		
		assTxArea = new TextArea(1, JiraData.columnLimits.get("Assignee"), false, false);
		assignee = new CheckBox("Assignee", false, true, assTxArea, datatable, "Assignee");
		jirafieldspanel.add(assignee);
		jirafieldspanel.add(assTxArea);
		
		repTxArea = new TextArea(1, JiraData.columnLimits.get("Reporter"), false, false);
		reporter = new CheckBox("Reporter", false, true, repTxArea, datatable, "Reporter");
		jirafieldspanel.add(reporter);
		jirafieldspanel.add(repTxArea);
		
		fvTxArea = new TextArea(1, JiraData.columnLimits.get("Fix Version/s"), false, false);
		fix_version = new CheckBox("Fix Version/s", false, true, fvTxArea, datatable, "Fix Version/s");
		jirafieldspanel.add(fix_version);
		jirafieldspanel.add(fvTxArea);
				
		desTxArea = new TextArea(2, JiraData.columnLimits.get("Description"), false, false);
		description = new CheckBox("Description", false, true, desTxArea, datatable, "Description");
		jirafieldspanel.add(description);
		jirafieldspanel.add(desTxArea);
		
		comTxArea = new TextArea(2, JiraData.columnLimits.get("Comment"), false, false);
		comment = new CheckBox("Comment", false, true, comTxArea, datatable, "Comment");
		jirafieldspanel.add(comment);
		jirafieldspanel.add(comTxArea);
		
		labTxArea = new TextArea(1, JiraData.columnLimits.get("Labels"), false, false);
		labels = new CheckBox("Labels", false, true, labTxArea, datatable, "Labels");
		jirafieldspanel.add(labels);
		jirafieldspanel.add(labTxArea);	
		
		add(jirafieldspanel);
		add(Box.createVerticalStrut(10));
		
		//**************************************************************************************************
		
		JPanel testcustompanel = topicPanel("Test Case Custom Fields");		
		
		datTxArea = new TextArea(1, JiraData.columnLimits.get("Data"), false, false);
		data = new CheckBox("Data", false, true, datTxArea, datatable, "Data");		
		testcustompanel.add(data);
		testcustompanel.add(datTxArea);
		
		exrTxArea = new TextArea(3, JiraData.columnLimits.get("Expected Result"), false, false);		
		expected_result = new CheckBox("Expected Result", false, true, exrTxArea, datatable, "Expected Result");		
		testcustompanel.add(expected_result);
		testcustompanel.add(exrTxArea);
		
		add(testcustompanel);
		add(Box.createVerticalStrut(10));		

		//**************************************************************************************************
		
		JPanel xrayfieldspanel = topicPanel("Xray Test Fields");		
		
		rpaTxArea = new TextArea(1, JiraData.columnLimits.get("Test Repository Path"), false, false);		
		repo_path = new CheckBox("Test Repository Path", false, true, rpaTxArea, datatable, "Test Repository Path");			
		xrayfieldspanel.add(repo_path);
		xrayfieldspanel.add(rpaTxArea);
		
		add(xrayfieldspanel);		

			
	}
}
