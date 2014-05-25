package tl14;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class PopUpWindow extends JFrame{
	private JFrame frame;
	JLabel label1;
	ButtonGroup radio;
	JRadioButton rb1;
	JRadioButton rb2;
	Container contentPane1; 
	
	public PopUpWindow(){
		frame = new JFrame("Starting Game");
		
		int inset = 50;
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    frame.setBounds(inset, inset,
	              screenSize.width  - inset*2,
	              screenSize.height - inset*2);
	    contentPane1 = frame.getContentPane();
		
	}
	
	public String[] gameStart(){
		String[] details=null;
		/*
		label1 = new JLabel("Επιλογές παιχνιδιού");
		radio = new ButtonGroup();
		 rb1 = new JRadioButton("Offline game", true);
		 rb2 = new JRadioButton("Online game", false);
		frame.pack();
		
		radio.add(rb1);
		radio.add(rb2);
		contentPane1.setLayout(new BoxLayout(contentPane1, BoxLayout.Y_AXIS));
		contentPane1.add(label1);
		contentPane1.add(rb1);
		contentPane1.add(rb2);
		setVisible();
		rb1.setVisible(true);
		rb2.setVisible(true);
		*/
		
		
		
		
		return details;
	}
	
	
	public String[] connError(){
		String[] details=null;
		
		return details;
	}
	
	public String[] winScreen(){
		String[] details=null;
		
		return details;
	}
	
	public String[] isRepeat(){
		String[] details=null;
		
		return details;
	}
	
	
	
	public void setVisible(){
		frame.setVisible(true);
		label1.setVisible(true);

	}
}