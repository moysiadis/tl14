package tl14;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class chip extends JComponent{

	private static final long serialVersionUID = 1L;
	private JLabel c;

	public chip(boolean team){
		String path; 
		if(team)
			path = "icons/whiteChip";
		else
			path = "icons/blueChip";

		ImageIcon image1= new  ImageIcon(path);
		c = new JLabel();
		c.setIcon(image1);
	}
	
	public JLabel getChip(){
		return c;
	}
	
}
