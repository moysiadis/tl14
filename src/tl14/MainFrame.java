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
	
	public MainFrame(){
		panel=new JPanel();
		//set graphics code
		//
		//
		//
		//
		
		
		
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
