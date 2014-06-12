package tl14;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class PopUpWindow extends JFrame {
	ArrayList<String> details;
	boolean ready;
	private MainFrame mainWindow;
	private int mode;
	
	private JFrame frame;
	Container contentPane1; 
	JButton submitButton;
	JButton submitButton2;
	JButton submitButton3;
	JTextField text2;
	JTextField text1;
	JTextField text3;
	JTextField text4;
	JLabel label1;
	JLabel label2;
	JLabel label3;
	JLabel label4;
	
	public PopUpWindow(int mode,MainFrame inst){
		details=new ArrayList<String>();
		mainWindow=inst;
		this.mode=mode;

	    switch (mode){
	    case 0: {
	    	frame = new JFrame("Starting Game");
	    	gameStart();
	    	break;
	    }
	    case 1: {
	    	frame = new JFrame("Connection error");
	    	
	    	frame.setUndecorated(true);
	    	connError();
	    	break;
	    }
	    case 2: {
	    	frame = new JFrame("Game ended");
	    	
	    	frame.setUndecorated(true);
	    	winScreen();
	    	break;
	    	}
	    default: {
	    	frame = new JFrame("Play again");
	    	frame.setUndecorated(true);
	    	isRepeat();
	    }
	    }

	    
	    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void gameStart(){
		System.out.print("in\n");
		contentPane1 = frame.getContentPane();
		
		submitButton = new JButton("Submit");
		//frame.pack();
		text2=new JTextField("PlayerName");
		text1=new JTextField("OpponentName");
		text3=new JTextField("ServerName");
		text4=new JTextField("ServerPortNumber");
		label1=new JLabel("Insert player name:");
		label2=new JLabel("Insert opponent player name:");
		label3=new JLabel("Insert server name:");
		label4=new JLabel("Insert server's port number:");
		submitButton.addActionListener(new Action1());
		contentPane1.setLayout(new GridLayout());
		contentPane1.add(label1);
		contentPane1.add(text1);
		contentPane1.add(label2);
		contentPane1.add(text2);
		contentPane1.add(label3);
		contentPane1.add(text3);
		contentPane1.add(label4);
		contentPane1.add(text4);
		contentPane1.add(submitButton);
		System.out.print("out\n");
		setVisible();
	}
	
	
	public void connError(){
		label1=new JLabel("There was an error communicating with the server");
		label2=new JLabel("Insert new details, or try again");
		submitButton = new JButton("New details");
		submitButton2=new JButton("Try again");
		this.submitButton3=new JButton("Exit");
		this.submitButton.addActionListener(new Action1());
		this.submitButton2.addActionListener(new Action2());
		this.submitButton3.addActionListener(new Action3());
		this.contentPane1.add(label1);
		this.contentPane1.add(label2);
		this.contentPane1.add(this.submitButton);
		this.contentPane1.add(this.submitButton2);
		this.contentPane1.add(this.submitButton3);
		contentPane1.setLayout(new GridLayout());
		
	}
	
	public void winScreen(){
		label1=new JLabel("Game end");
		label2=new JLabel("Want to play again?");
		submitButton = new JButton("Play again");
		submitButton2=new JButton("Play in an other server");
		this.submitButton3=new JButton("Exit");
		this.submitButton.addActionListener(new Action1());
		this.submitButton2.addActionListener(new Action2());
		this.submitButton3.addActionListener(new Action3());
		this.contentPane1.add(label1);
		this.contentPane1.add(label2);
		this.contentPane1.add(this.submitButton);
		this.contentPane1.add(this.submitButton2);
		this.contentPane1.add(this.submitButton3);
		contentPane1.setLayout(new GridLayout());

	}
	
	public void isRepeat(){

		label1=new JLabel("Game end");
		label2=new JLabel("Want to play again?");
		submitButton = new JButton("Play again");
		submitButton2=new JButton("Play in an other server");
		this.submitButton3=new JButton("Exit");
		this.submitButton.addActionListener(new Action1());
		this.submitButton2.addActionListener(new Action2());
		this.submitButton3.addActionListener(new Action3());
		this.contentPane1.add(label1);
		this.contentPane1.add(label2);
		this.contentPane1.add(this.submitButton);
		this.contentPane1.add(this.submitButton2);
		this.contentPane1.add(this.submitButton3);
		contentPane1.setLayout(new GridLayout());
	}
	
	
	
	public void setVisible(){
		frame.setVisible(true);
		frame.toFront();
	}

	
	public class Action1 implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			details.add(Integer.toString(mode));
			if (mode==0) {
				details.add(text4.getText());
				details.add(text3.getText());
				details.add(text2.getText());
				details.add(text1.getText());
			}else if(mode==1){
				details.add("play");
			}else if(mode==2){
				details.add("play");
			}else if(mode==3){
				details.add("play");
			}
			
			setInVisible();
			mainWindow.userInput(mode);
		}
	}
	
	public class Action2 implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			details.add(Integer.toString(mode));
			if (mode==0) {
				System.out.print("text: "+text4.getText());
				details.add(text4.getText());
				details.add(text3.getText());
				details.add(text2.getText());
				details.add(text1.getText());
			}else if(mode==1){
				details.add("playnew");
			}else if(mode==2){
				details.add("playnew");
			}else if(mode==3){
				details.add("exit");
			}
			
			mainWindow.userInput(mode);
		}
	}
	
	public class Action3 implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			details.add(Integer.toString(mode));
			if(mode==1){
				details.add("exit");
			}else if(mode==2){
				details.add("exit");
			}
			
			mainWindow.userInput(mode);
		}
	}
	
	public int exitFrame(){
		frame.dispose();
		return 0;
	}
	public ArrayList<String> fetchResult(){
		return details;
	}
	
	private void setInVisible(){
		frame.setVisible(false);
	}

}