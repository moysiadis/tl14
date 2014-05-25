package tl14;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class MainFrame extends JFrame{
	
	private JPanel panel;
	private Game gm;
	private boolean noPlayer;
	private ArrayList<Integer> localPos;
	private ArrayList<Integer> awayPos;
	boolean playAgain=false;
	private JFrame frame;
	JButton button1;
	JButton button2; 
	JLabel label1;
	JLabel label2;
	JLabel label3;
	PopUpWindow puw;
	
	public MainFrame(){
		
		panel=new JPanel();
		// graphics code
		frame = new JFrame("Backgammon");
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		Container mainPane = frame.getContentPane();
        button1 = new JButton("Start Game");
        button2 = new JButton("Roll");        
        String path = "boardBG.png";
        ImageIcon image1= new  ImageIcon(path);
        
        label1 = new JLabel(image1);
        label2 = new JLabel("");
        label3 = new JLabel("");
        panel1.add(label3);
        panel1.add(label2);
        panel1.add(button2);
        panel1.add(button1);
        mainPane.setLayout(new BorderLayout());
        panel2.add(label1);
        panel1.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel1.setLayout(new GridLayout());
        mainPane.add(panel1, BorderLayout.NORTH);
        mainPane.add(panel2, BorderLayout.CENTER);
        mainPane.setVisible(true);
        panel1.setVisible(true);
        panel2.setVisible(true);
        button1.setVisible(true);
        button2.setVisible(true);
        label1.setVisible(true);
        label2.setVisible(true);
        label3.setVisible(true);
        frame.pack();
        frame.setVisible(true);
		/////////////////////////////////
		puw=new PopUpWindow();
		
		
		startGame();
		
		
		
		
		
	}
	
	
	private void startGame(){
		//Το popUp επιστρέφει  portNo, ip,το όνομα του παίκτη, και το όνομα του αντιπάλου αν υπάρχει
			String[] temp=callPopUp(0);
			int[] noTemp=null;
			boolean flag=true;
			
			
			gm=new Game(1,Integer.valueOf(temp[0]),temp[1],temp[2],temp[3],this);
			while(gm.Connect()!=1){
				temp= callPopUp(1);
				if(temp[0].equals("details")){
					gm.setConnection(Integer.valueOf(temp[1]), temp[3]);
				}
			}

			
			do {
				gm.resetGame();
				flag=true;
				noTemp = gm.receiveMsg(0);
				if (noTemp[0] != 0) {
					flag = false;
					System.out.println("Error sending name");
				}
				
				
				if (flag) {
					///////////////////////normal flow
					noTemp = gm.receiveMsg(0);
					if (noTemp[0] == 3) {
						if (noTemp[1] == 0)//αν είναι 0 τότε ο παίκτης παίζει πρώτος
							noPlayer = true;

						drawGame();
						noTemp = gm.receiveMsg(0);
					} else {
						flag = false;
						this.callPopUp(1);
					}
				}
				while (flag) { //κινήσεις μέσα στο παιχνίδι
					if (noTemp[0] == 2)
						setGraphicDice(noTemp[1], noTemp[2]);
					if (noTemp[0] == 7) {
						callPopUp(3);
						break;
					}
					if (noTemp[0] == 4 || noTemp[0] == 5) {
						callPopUp(1);
						break;
					}

					if (gm.getGameCounter() % 2 == 0 && noTemp[0] != 1) {
						if (noPlayer) {
							noTemp = gm.setMoves(getGraphicMove());
							noPlayer = true;//για να εκτελείται κάθε επόμενη φορά μετά την πρώτη
						}
					} else {
						if (noTemp[0] == 1) {
							this.drawGame();
						}
						noTemp = gm.receiveMsg(0);
					}
				}
				///////////////////////
				
				this.callPopUp(3);
			} while (playAgain);
			
			
		
	}
	
	
	public String[] callPopUp(int mode){
		//καλούμε το popUp με κάποιον κωδικό:
		//0: για παράθυρο έναρξης και επιστρέφει τα στοιχεία σύνδεσης
		//1: για connection error
		//2: τελος με νίκη
		//3: ερώτηση για επανάληψη παιχνιδιού, σε θετική απάντηση να αλλάξει σε true η playAgain
		String[] temp=null;
		
		if(mode==0){
			temp=puw.gameStart();
		}else if(mode==1){
			puw.connError();
		}else if(mode==2){
			puw.winScreen();
		}else if(mode==3){
			temp=puw.isRepeat();
			if(temp[0].equals("true"))
				playAgain=true;
		}
		
		return temp;
	}
	
	private ArrayList<String> getGraphicMove(){//περιμένει τον παίκτη να κάνει την κίνηση του
		ArrayList<String> move=null;
		ArrayList<ArrayList<Integer>> pMoves;
		pMoves=new ArrayList<ArrayList<Integer>>(gm.PossibleMoves());
		
		
		return move;
 	}
	
	private void setGraphicDice(int d1,int d2){
		
	}
	
	private void drawGame(){//σχεδιάζει τις θέσεις των πιονιών στο τάβλι
		awayPos=gm.getAwayPositions();
		localPos=gm.getLocalPositions();
		
		
		
		
	}
	
	
}
