package tl14;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.*;

public class MainFrame extends JFrame{
	
	private JPanel panel;
	private JLayeredPane lPanel; 
	private Game gm;
	private int[] lineStack=new int[26];
	private boolean noPlayer;
	private ArrayList<Integer> localPos;
	private ArrayList<Integer> awayPos;
	private ArrayList<piece> piecesA,piecesL;
	private boolean playAgain=false;
	private JFrame frame;
	private MouseListen ml;
	private ArrayList<String> conDetails;
	JButton button1;
	JButton button2; 
	JLabel label1;
	JLabel label2;
	JLabel label3;
	PopUpWindow puw;
	JPanel panel2;
	JPanel panel1;
	
	public MainFrame(){
		
		
       // piece p1 = new piece(200,200,false);
		piecesA= new ArrayList<piece>();
		piecesL= new ArrayList<piece>();
		createPieces();
        String path = "boardBG.png";
		ImageIcon image1= new  ImageIcon(path);
		label1 = new JLabel();
        label1.setIcon(image1);
        
		frame = new JFrame("Backgammon");
       // frame.setPreferredSize(new Dimension(745,680));
		frame.setLayout(new BorderLayout());
        frame.setSize(740, 690);
		panel = new JPanel();
        panel2 = new JPanel();
        lPanel= frame.getLayeredPane();//new JLayeredPane();//
        
        
        panel2.setSize(730, 650);
        panel2.setLayout(new BorderLayout());
        panel2.add(label1);
        panel2.setOpaque(true);

        panel.setSize(730, 650);
        panel.setLayout(getLayout());
        //panel.add(p1);
        panel.setOpaque(false);
        
        lPanel.add(panel2,Integer.valueOf(1));
        lPanel.add(panel,Integer.valueOf(2));
		
        frame.setLayeredPane(lPanel);
        //frame.add(lPanel);
        ml=new MouseListen(this);
		conDetails=new ArrayList<String>();
		frame.addMouseListener(ml);

		//frame.setEnabled(false);
        frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//puw=new PopUpWindow(0,this);
		//gm=new Game(1,11234 , "localhost", "George", "", this);
		//this.drawGame();
	}
	private void createPieces(){
		for(int i=0;i<15;i++){
			piecesA.add(new piece(0,0,false));
			piecesL.add(new piece(0,0,true));
		}
		for(int i=0;i<26;i++){
			lineStack[i]=0;
		}
	}
	
	private void startGame(ArrayList<String> userInput){
		//Το popUp επιστρέφει  portNo, ip,το όνομα του παίκτη, και το όνομα του αντιπάλου αν υπάρχει
		int[] noTemp=null;
		boolean flag=true;	
		int error = -1;
		ArrayList<String> temp=new ArrayList<String>(userInput);
		conDetails=(ArrayList<String>)userInput.clone();
			
			
			gm=new Game(1,Integer.valueOf(temp.get(0)),temp.get(1),temp.get(2),temp.get(3),this);
			if(gm.Connect()!=-1){
				error=0;
			}else{
				gm.resetGame();
				noTemp = gm.receiveMsg(0);
				if (noTemp[0] != 0) {
					System.out.println("Error sending name");
					error=1;
				}else{
					do {
						noTemp = gm.receiveMsg(0);
						if (noTemp[0] == 3) {
							if (noTemp[1] == 0)//αν είναι 0 τότε ο παίκτης παίζει πρώτος
								noPlayer = true;
							drawGame();
							noTemp = gm.receiveMsg(0);
						} else if (noTemp[0] == 6) {
							continue;
						} else {
							error = 1;
						}
						///////////////////////
					} while (noTemp[0]!=3);
					this.gameFlow(noTemp);
				}
			}
			if(error!=-1)
				puw=new PopUpWindow(error,this);
		
	}
	
	public void gameFlow(int[] temp){
		int[] noTemp=temp;
		int error=-1;
		
		if (noTemp[0] == 7) {
			error=3;
		}else if (noTemp[0] == 4 || noTemp[0] == 5) {
			error=1;
		}else if (noTemp[0]==1 || noTemp[0]==2) {
			//previous if(gm.getGameCounter() % 2 == 0 && noTemp[0]!=1)
			if (!noPlayer) {
				//gm.setMoves(getGraphicMove());
				//noPlayer = true;//για να εκτελείται κάθε επόμενη φορά μετά την πρώτη
				if (noTemp[0] == 1){
					this.drawGame();
					noPlayer=true;
				}else{
					this.setGraphicDice(noTemp[1], noTemp[2]);
					noTemp = gm.receiveMsg(0);
					gameFlow(noTemp);
				}
			}
		}
		
		if(error!=-1)
			puw=new PopUpWindow(error,this);
		
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
		
		for(int i=0;i<awayPos.size();i++){
			int temp=lineStack[i]=awayPos.get(i);
			while(temp!=0){
				piecesA.get(i).setPosition(i, temp);
				temp--;
			}
			temp=localPos.get(i);
			while(temp!=0){
				piecesA.get(i).setPosition(i, temp);
				temp--;
			}
		}
		
		for(int i=0;i<15;i++){
			panel.add(piecesA.get(i));
			panel.add(piecesL.get(i));
		}
		//frame.repaint();
	}
	
	public void userInput(int mode){
		//καλούμε το popUp με κάποιον κωδικό:
		//0: για παράθυρο έναρξης και επιστρέφει τα στοιχεία σύνδεσης
		//1: για connection error
		//2: τελος με νίκη
		//3: ερώτηση για επανάληψη παιχνιδιού, σε θετική απάντηση να αλλάξει σε true η playAgain
		ArrayList<String> temp=new ArrayList<String>(puw.fetchResult());
		frame.setEnabled(true);
		puw.exitFrame();
		switch(Integer.valueOf(temp.get(0))){
		case 0: {
			temp.remove(0);
//			if(){
//				puw=new PopUpWindow(0,this);
//				break;
//			}
			this.startGame(temp);
			break;
		}
		case 1:{
			temp.remove(0);
			if(temp.get(0).equals("play"))
				this.startGame(conDetails);
			else if(temp.get(0).equals("playnew"))
				puw=new PopUpWindow(0,this);
			else
				exitProgram();
			break;
		}
		case 2: {
			temp.remove(0);
			if(temp.get(0).equals("play"))
				this.startGame(conDetails);
			else if(temp.get(0).equals("playnew"))
				puw=new PopUpWindow(0,this);
			else
				exitProgram();
			break;
		}
		case 3:{
			temp.remove(0);
			if(temp.get(0).equals("play")){
				conDetails.set(3, "");
				this.startGame(conDetails);
				}
			else if(temp.get(0).equals("playnew"))
				puw=new PopUpWindow(0,this);
			else
				exitProgram();
			break;
		}
		default: System.out.print("we'll see");
		}
	}
	
	public boolean getTurn(){
		return noPlayer;
	}
	
	public int exitProgram(){
		if(gm!=null){
			gm.closeConnection();
		}
		//puw.exitFrame();
		frame.dispose();
		return 0;
	}
	
	
	class piece extends JComponent{
		private static final long serialVersionUID = 1L;
		int pos1,pos2;
		boolean team;
		
		
		public piece(int pos1,int pos2,boolean team){
			this.pos1=pos1;
			this.pos2=pos2;
			this.team=team;
		}
		
		@Override 
	    public Dimension getPreferredSize()
	    {
	        return (new Dimension(40, 40));
	    }
		
		public void setPosition(int col,int lin){
			pos1=col;
			pos2=lin;
			//repaint();
		}
		
		public int[] getPosition(){
			int[] temp={pos1,pos2};
			////////////////
			////////
			//
			/////////////
			////////////
			return temp;
		}
		
		@Override
		public void paint(Graphics g){
			Graphics2D g2d = (Graphics2D) g;
			
	        if(team)
	        	g2d.setPaint(Color.BLUE);
	        else
	        	g2d.setPaint(Color.WHITE);

	        int[] pos=getPosition();
	        g2d.drawOval(pos[0], pos[1], 20, 20);
	        g2d.fillOval(pos[0], pos[1], 20, 20);
		}
	}
}
