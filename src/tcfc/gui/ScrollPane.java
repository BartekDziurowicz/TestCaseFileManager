package tcfc.gui;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ScrollPane extends JScrollPane{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ScrollPane(DefaultPanel df){		
		this.getViewport().add(df);
		this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
	}
	
	public ScrollPane(Table dt){		
		this.getViewport().add(dt);		
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				dt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);				
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

}
