package tcfc.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.DocumentFilter;

import tcfc.data.JiraData;



public class TextArea extends JTextArea{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FocusListener setProjectTitleFromTA() {
		FocusListener setProjectTitleFromTA = new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				setForeground(Color.RED);				
			}

			@Override
			public void focusLost(FocusEvent e) {
				setForeground(Color.DARK_GRAY);
				if (!JiraData.getProjectName().equals(getText())) {
					JiraData.setProjectName(getText());				
					JiraData.setChanged(true);
				}
			}
			
		};
		return setProjectTitleFromTA;
	}
	
	
	
	protected ActionListener setProjectTitle() {
		ActionListener setProjectTitle = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JiraData.setProjectName(getText());
				JiraData.setChanged(true);
				JiraData.setQuickSaveExist(false);
			}


			
		};
		return setProjectTitle;
	}
	
	public TextArea(int limit, boolean onlyDigits) {
		DefaultStyledDocument doc = new DefaultStyledDocument();
		doc.setDocumentFilter(new DocumentSizeFilter(limit, onlyDigits));
	}
	
	public TextArea(int rows, int limit, boolean isMandatory, boolean onlyDigits) {	
		DefaultStyledDocument doc = new DefaultStyledDocument();
		doc.setDocumentFilter(new DocumentSizeFilter(limit, onlyDigits));
		setDocument(doc);
		//this.revalidate();
		setLineWrap(true);
		setWrapStyleWord(true);		
		setRows(rows);		
		setMaximumSize(new Dimension(330,1000));
		Border border;
		if(isMandatory == true) {
			border = BorderFactory.createLineBorder(Color.RED);
		} else {
			border = BorderFactory.createLineBorder(new Color(191, 191, 191));
		}
		setBorder(BorderFactory.createCompoundBorder(border, 
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		setFont(new Font ("Arial", Font.PLAIN, 10));
		setAlignmentX(LEFT_ALIGNMENT);
		this.getText().length();
		this.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (isEnabled()==false) {
					setBackground(new Color(240,240,240));
				} else {			
					setBackground(Color.WHITE);
				}				
			}
			
		});		

			}

}

class DocumentSizeFilter extends DocumentFilter {
	  int len;
	  boolean numeric;
	  public DocumentSizeFilter(int max_Chars, boolean onlyDigits) {
	    len = max_Chars;
	    numeric = onlyDigits;
	  }
	  public void insertString(FilterBypass fb, int offset, String str,
	      AttributeSet a) throws BadLocationException {
		  
	    if ((fb.getDocument().getLength() + str.length()) <= len){
	    	if (numeric == true) {
	    		super.insertString(fb, offset, str.replaceAll("[^0-9]", ""), a);
	    	} else {
	    		super.insertString(fb, offset, str.replaceAll(";", ""), a);
	    	}
	      
	    }
	  }
	  public void replace(FilterBypass fb, int offset, int length, String str,
	      AttributeSet a) throws BadLocationException {
		  //str.replaceAll("[^0-9.]", "");
	    if ((fb.getDocument().getLength() + str.length() - length) <= len){
	    	if (numeric == true) {
	    		super.replace(fb, offset, length, str.replaceAll("[^0-9]", ""), a);
	    	} else {
	    		super.replace(fb, offset, length, str.replaceAll(";", ""), a);
	    	}	      	      
	    }
	  }
	}
