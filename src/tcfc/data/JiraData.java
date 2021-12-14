package tcfc.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class JiraData {
	
	private static boolean changed = false;
	
	public static boolean isChanged() {
		return changed;
	}
	
	public static void setChanged(boolean achanged) {
		changed = achanged;
	}
	
	private static Boolean quicksaveexist = false;
	
	public static boolean isQuickSaveExist() {
		return quicksaveexist;
	}
	
	public static void setQuickSaveExist(boolean aquicksaveexist) {
		quicksaveexist = aquicksaveexist;
	}
	
	public static final int tableLimit = 1000;
	
	private static String projectName ="";
	
	public static String getProjectName() {
		if (projectName.length() <= 0) {
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			JiraData.setProjectName(timeStamp+"_tcfm_untitled");
		}
		return projectName;
	}
	
	public static void setProjectName(String projectname) {
		projectName = projectname;
	}
	
	public static final String Priority[] = {"Default", "Low", "Medium", "High", "Highest"};
	
	public static final String Status[] = {"DEFAULT", "TO DO", "WORKING", "READY FOR REVIEW", "DONE"};
	
	public static final String columns[] = {"TCID", "Summary", "Action", "Priority", "Status", "Assignee", "Reporter",
			"Fix Version/s", "Description", "Comment", "Labels", "Data", "Expected Result",
			"Test Repository Path" };
	
	public static final String mandatory[] = {"TCID", "Summary", "Action", "Priority"}; 
	public static final String digitsonly[] = {"TCID"};
	public static final String isEnumValues[] = {"Status", "Priority" };
	
	public final static HashMap<String, Integer> columnLimits = new HashMap<String, Integer>() {/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		put("TCID", 3);
		put("Summary", 100);
		put("Action", 500);		
		put("Priority", 7);
		put("Status", 16);
		put("Assignee", 50);
		put("Reporter", 50);
		put("Fix Version/s", 50);
		put("Description", 200);
		put("Comment", 200);
		put("Labels", 50);
		put("Data", 200);
		put("Expected Result", 500);		
		put("Test Repository Path", 200);
	}};
	
	
	public final static HashMap<String, int[]> columnWidths = new HashMap<String, int[]>() {/**
		 * 
		 * minimum, preferred, maximum
		 */
		private static final long serialVersionUID = 1L;

	{
		put("TCID", new int[] {35,35,35});
		put("Summary", new int[] {100, 200, 800});
		put("Action", new int[] {100, 300, 2500});		
		put("Priority", new int[] {50, 150, 150});
		put("Status", new int[] {50, 100, 100});
		put("Assignee", new int[] {50, 150, 300});
		put("Reporter", new int[] {50, 150, 300});
		put("Fix Version/s", new int[] {50, 150, 300});
		put("Description", new int[] {100, 200, 800});
		put("Comment", new int[] {100, 200, 400});
		put("Labels", new int[] {50, 200, 400});
		put("Data", new int[] {100, 200, 400});
		put("Expected Result", new int[] {100, 200, 800});		
		put("Test Repository Path", new int[] {100, 200, 800});
	}};		
	
	
	public static HashMap<String, Boolean> columnSelected = new HashMap<String, Boolean>() {/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		put("TCID", true);
		put("Summary", true);
		put("Action", true);		
		put("Priority", true);
		put("Status", false);
		put("Assignee", false);
		put("Reporter", false);
		put("Fix Version/s", false);
		put("Description", false);
		put("Comment", false);
		put("Labels", false);
		put("Data", false);
		put("Expected Result", false);
		put("Pre-Conditions", false);
		put("Test Repository Path", false);
	}};
	
	public static HashMap<String, String> defaultValues = new HashMap<String, String>() {/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		put("Priority", "Medium");
		put("Status", "TO DO");

	}};
	


}
