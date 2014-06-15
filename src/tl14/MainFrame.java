package tl14;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.*;
public class MainFrame extends JFrame{

	private JPanel panel;
	private JPanel panelG;
	private JPanel panelG1;
	private JPanel panelG2;
	private JPanel panelS1;
	private JPanel panelS2;

	private JLayeredPane lPanel; 
	private Game gm;
	private int[] lineStack=new int[26];
	private boolean noPlayer;
	private ArrayList<Integer> localPos;
	private ArrayList<Integer> awayPos;
	private ArrayList<JLabel> piecesA,piecesL;
	private boolean playAgain=false;
	private JFrame frame;
	private MouseListen ml;
	private ArrayList<String> conDetails;
	JButton button1;
	JButton button2; 
	JLabel label1;
	PopUpWindow puw;
	JPanel panel2;
	JPanel[][] placeHolder;
	JPanel[][] placeHolder2;
	JPanel[][] placeHolder3;
	JPanel[][] placeHolder4;
	JPanel[][] placeHolder5;
	JPanel[][] placeHolder6;
	String move;
	int xSize,ySize;
	public MainFrame(){
		//////////////////////////
		///////////////////////
		this.noPlayer=true;
		///////////////////////
		////////////////////////
		xSize=5;
		ySize=6;

		piecesA= new ArrayList<JLabel>();
		piecesL= new ArrayList<JLabel>();		

		createPieces();
		ml=new MouseListen(this);
		String path = "icons/boardBG.png";
		ImageIcon image1= new  ImageIcon(path);
		label1 = new JLabel();
		label1.setIcon(image1);

		frame = new JFrame("Backgammon");
		frame.setPreferredSize(new Dimension(745,680));
		//frame.setLayout(new BorderLayout());
		frame.setSize(740, 690);
		panel = new JPanel();
		panel2 = new JPanel();
		panelG=new JPanel();
		panelG1=new JPanel();
		panelG2=new JPanel();
		panelS2=new JPanel();
		panelS1=new JPanel();
		lPanel= frame.getLayeredPane();//new JLayeredPane();//

		//setting boardpanel
		panel2.setSize(730, 650);
		panel2.setLayout(new BorderLayout());
		panel2.add(label1);
		panel2.setOpaque(true);

		//setting chippanel
		//aristera,panw-katw/ width,height
		panel.setBounds(33, 40, 297, 200);
		panel.setLayout(new GridLayout(xSize, ySize));
		panel.setOpaque(false);


		//gia na exoume to ena panel katw apo to allo
		panelG.setBounds(33, 410, 297, 200);
		panelG.setLayout(new GridLayout(xSize, ySize));
		panelG.setOpaque(false);


		//gia na exoume to ena panel katw apo to allo
		panelG1.setBounds(391, 40, 297, 200);
		panelG1.setLayout(new GridLayout(xSize, ySize));
		panelG1.setOpaque(false);

		//gia na exoume to ena panel katw apo to allo
		panelG2.setBounds(391, 410, 297, 200);
		panelG2.setLayout(new GridLayout(xSize, ySize));
		panelG2.setOpaque(false);


		panelS1.setBounds(680, 40, 50, 200);
		panelS1.setLayout(new GridLayout(xSize, 1));
		panelS1.setOpaque(false);

		panelS2.setBounds(680, 410, 50, 200);
		panelS2.setLayout(new GridLayout(xSize, 1));
		panelS2.setOpaque(false);




		//placeholders for the gridlayout
		placeHolder=new JPanel[xSize][ySize];
		placeHolder2=new JPanel[xSize][ySize];
		placeHolder3=new JPanel[xSize][ySize];
		placeHolder4=new JPanel[xSize][ySize];
		placeHolder5=new JPanel[xSize][1];
		placeHolder6=new JPanel[xSize][1];
		for(int i = 0; i < xSize; i++) {
			for(int j = 0; j < ySize; j++) {
				placeHolder[i][j] = new JPanel();
				placeHolder[i][j].setOpaque(false);
				new DropPanel(placeHolder[i][j],this);
				//        		placeHolder[i][j].add(new JLabel("!"+1));
				placeHolder2[i][j] = new JPanel();
				placeHolder2[i][j].setOpaque(false);
				new DropPanel(placeHolder2[i][j],this);

				//        		placeHolder2[i][j].add(new JLabel("%"+2));
				placeHolder3[i][j] = new JPanel();
				placeHolder3[i][j].setOpaque(false);
				new DropPanel(placeHolder3[i][j],this);

				//        		placeHolder3[i][j].add(new JLabel("$"+3));
				placeHolder4[i][j] = new JPanel();
				placeHolder4[i][j].setOpaque(false);
				new DropPanel(placeHolder4[i][j],this);

				//        		placeHolder4[i][j].add(new JLabel("#"+4));

				panel.add(placeHolder[i][j]);
				panelG.add(placeHolder2[i][j]);
				panelG1.add(placeHolder3[i][j]);
				panelG2.add(placeHolder4[i][j]);
				if (j==0) {
					//gia thn extra sthlh otan vgainoun e3w ta poulia
					placeHolder5[i][0] = new JPanel();
					placeHolder5[i][0].setOpaque(false);
					new DropPanel(placeHolder5[i][j],this);

					//        			placeHolder5[i][0].add(new JLabel("@" + 5));
					placeHolder6[i][0] = new JPanel();
					placeHolder6[i][0].setOpaque(false);
					new DropPanel(placeHolder6[i][j],this);

					//					placeHolder6[i][0].add(new JLabel("&" + 6));
					panelS1.add(placeHolder5[i][0]);
					panelS2.add(placeHolder6[i][0]);
				}

			}
		}

		//adding panels to lPannel
		//Positions are specified with an int 
		//between -1 and (n - 1), where n is the number of components at the depth.
		lPanel.add(panel2,Integer.valueOf(0));
		lPanel.add(panel,Integer.valueOf(1),-1);
		lPanel.add(panelG,Integer.valueOf(1),0);
		lPanel.add(panelG1,Integer.valueOf(1),1);
		lPanel.add(panelG2,Integer.valueOf(1),2);
		lPanel.add(panelS1,Integer.valueOf(1),4);
		lPanel.add(panelS2,Integer.valueOf(1),3);



		frame.setLayeredPane(lPanel);
		//frame.add(lPanel);
		conDetails=new ArrayList<String>();
		frame.addMouseListener(ml);


//		frame.setEnabled(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		puw=new PopUpWindow(0,this);
		gm=new Game(1,11234 , "localhost", "George", "", this);
		this.drawGame();
		
	}	

	private void createPieces(){


		for(int i=0;i<15;i++){
			//			JLabel c=new JLabel("");
			//			c.setTransferHandler(new TransferHandler("new"));
			//			
			piecesA.add(new DragLabel(true,ml));
			piecesL.add(new DragLabel(false,ml));
		}
		for(int i=0;i<26;i++){
			lineStack[i]=0;
		}
	}

	protected int getLine(JPanel jp){
		int temp = -1;
		boolean flag=false;
		for(int i=0;i<this.xSize;i++){
			for(int j=0;j<this.ySize;j++){
				if(placeHolder[i][j]==jp){
					temp= j+13;
					flag=true;
					break;
				}
				if(placeHolder2[i][j]==jp){
					temp= 12-j;
					flag=true;
					break;
				}
				if(placeHolder3[i][j]==jp){
					temp= j+19;
					flag=true;
					break;
				}
				if(placeHolder4[i][j]==jp){
					temp= 6-i;
					flag=true;
					break;
				}
				if (j==0) {
					if (placeHolder5[i][0] == jp) {
						temp = 25;
						flag = true;
						break;
					}
					if (placeHolder6[i][0] == jp) {
						temp = 0;
						flag = true;
						break;
					}
				}
			}
			if(flag)
				break;
		}
		return temp;
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
		int count1=14;
		int count2=14;
		int pos=0,rpos=4;
		//		boolean flag=false;

		for(int i=0;i<awayPos.size();i++){
			int temp=lineStack[i]=awayPos.get(i);

			rpos=5;

			while(temp!=0){
				if((lineStack[i]-5)==temp){
					rpos=0;
				}

				//adding labels in the right placeholders
				if(i==0){
					pos=0;
					placeHolder6[rpos][pos].add(piecesA.get(count1));
					placeHolder6[rpos][pos].validate();
					placeHolder6[rpos][pos].repaint();
				}else if(i>0 && i<7){
					pos=6-i;
					int tpos=4-((5-rpos)%5);
					placeHolder4[tpos][pos].add(piecesA.get(count1));
					placeHolder4[tpos][pos].validate();
					placeHolder4[tpos][pos].repaint();
				}else if(i>6 && i<13){
					pos=12-i;
					int tpos=rpos%5;
					placeHolder2[tpos][pos].add(piecesA.get(count1));
					placeHolder2[tpos][pos].validate();
					placeHolder2[tpos][pos].repaint();
				}else if(i>12 && i<19){
					pos=5-(18-i);
					int tpos=(5-rpos)%5;
					placeHolder[tpos][pos].add(piecesA.get(count1));
					placeHolder[tpos][pos].validate();
					placeHolder[tpos][pos].repaint();
				}else if(i>18 && i<25){
					pos=5-(24-i);
					int tpos=(5-rpos)%5;
					placeHolder3[tpos][pos].add(piecesA.get(count1));
					placeHolder3[tpos][pos].validate();
					placeHolder3[tpos][pos].repaint();
				}else if(i==25){
					pos=0;
					placeHolder5[rpos][pos].add(piecesA.get(count1));
					placeHolder5[rpos][pos].validate();
					placeHolder5[rpos][pos].repaint();
				}

				count1--;
				temp--;	
				rpos--;
			}
			temp=localPos.get(i);

			if(lineStack[i]==0)
				lineStack[i]=temp;

			rpos=5;

			while(temp!=0){
				if((lineStack[i]-5)==temp){
					rpos=0;
				}

				//adding labels in the right placeholders
				if(i==0){
					pos=0;
					placeHolder6[rpos][pos].add(piecesL.get(count2));
					placeHolder6[rpos][pos].validate();
					placeHolder6[rpos][pos].repaint();
				}else if(i>0 && i<7){
					pos=6-i;
					int tpos=4-((5-rpos)%5);
					placeHolder4[tpos][pos].add(piecesL.get(count2));
					placeHolder4[tpos][pos].validate();
					placeHolder4[tpos][pos].repaint();
				}else if(i>6 && i<13){
					pos=12-i;
					int tpos=4-((5-rpos)%5);
					placeHolder2[tpos][pos].add(piecesL.get(count2));
					placeHolder2[tpos][pos].validate();
					placeHolder2[tpos][pos].repaint();
				}else if(i>12 && i<19){
					pos=5-(18-i);
					int tpos=(5-rpos)%5;
					placeHolder[tpos][pos].add(piecesL.get(count2));
					placeHolder[tpos][pos].validate();
					placeHolder[tpos][pos].repaint();
				}else if(i>18 && i<25){
					pos=5-(24-i);
					int tpos=(5-rpos)%5;
					placeHolder3[tpos][pos].add(piecesL.get(count2));
					placeHolder3[tpos][pos].validate();
					placeHolder3[tpos][pos].repaint();
				}else if(i==25){
					pos=0;
					placeHolder5[rpos][pos].add(piecesL.get(count2));
					placeHolder5[rpos][pos].validate();
					placeHolder5[rpos][pos].repaint();
				}

				count2--;
				temp--;	
				rpos--;
			}
		}
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

	
}