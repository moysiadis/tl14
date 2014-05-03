package tl14;

import java.io.IOException;
import java.util.ArrayList;


public class Game {
	
	private ArrayList<Integer> localPos;
	private ArrayList<Integer> awayPos;
	private int playerNo;
	private Connection conn;
	private AnalyseGame anG;
	private int d1,d2;
	private int gameCounter;
	
	public Game(int gameMode,int portNo, String ipNo){
		//playerNo=pNo;
		conn=new Connection();
		gameCounter=0;
		conn.setConnectionDetails(portNo, ipNo);
		
		for(int i=0;i<=25;i++)
		{
			localPos.add(0);
			awayPos.add(0);
		}
		InitializeGame(gameMode);
	}
	
	public int[] getDice(){
		int[] dice = {0,0};
		dice[0]=d1;
		dice[1]=d2;
		return dice;
	}
	
	private void InitializeGame(int gM){
		switch (gM) {
			case 1: //tables-portes
			{
				localPos.set(24, 2);
				awayPos.set(1, 2);
				localPos.set(6, 5);
				awayPos.set(19, 5);
				localPos.set(8, 3);
				awayPos.set(17, 3);
				localPos.set(13, 5);
				awayPos.set(12, 5);
				break;
			}
			default: System.out.println("Pick game");
			anG=new AnalyseGame(localPos,awayPos);	
			
		}
			
	}

	
	public ArrayList<Integer> getNewPositions(){//την καλεί το Frame για να σχεδιάσει την κίνηση του αντιπάλου
		return awayPos;
	}
	
	public int[] setMove(ArrayList<Integer> move){
		int erNo;
		
		//θέτουμε κίνηση ετοιμάζουμε String για τον σερβερ και στέλνουμε
		String moveToSend="";
		
		
		
		erNo=conn.Send(moveToSend);
		//erNo=1 στάλθηκε επιτυχώς
		//erNo=2 exception
		//erNo=3 sendMsg=null
		
		return receiveMsg(erNo);
	}
	
	public int getGamecounter(){
		return gameCounter;
	}
	
	public int Connect(){
		try {
			conn.connectToserver();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return 2;
		}
		
		
		return 1;
		
		
	}
	
	private int[] receiveMsg(int  erNo){
		int[] errorType={0,0,0};
		String serverMsg="";
		String msgAnalysed="";
		
		
		if (erNo!=2 && erNo!=3) {
			try {
				serverMsg = conn.Receive();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				errorType[0] = 5;
			}
		}else if(erNo==2)
			System.out.println("exception in send");
		else if(erNo==3)
			System.out.println("null in send");
		
		if(erNo==1 && errorType[0]!=5){
			if (!serverMsg.isEmpty() && !serverMsg.equals("fail") && serverMsg.startsWith("Move")) {
				awayPos=new ArrayList<Integer>(anG.analyseMove(serverMsg));
				errorType[0]=1;
			}else{
				errorType[0]=4;
			}
		}else if(erNo==0 && errorType[0]!=5){
			//απόκριση από τον σερβερ είτε για ζάρια ή σειρά παίκτη
			msgAnalysed.concat(anG.analyseMsg(serverMsg));
			
			if(msgAnalysed.startsWith("dice")){
				String[] parts=msgAnalysed.split(",");//το μνμ θα είναι <dice,<1-6>,<1-6>>
				d1=Integer.valueOf(parts[1]);
				d2=Integer.valueOf(parts[2]);
				errorType[0]=2;
				errorType[1]=d1;
				errorType[2]=d2;
				
			}else if(msgAnalysed.startsWith("playerNo")){
				String[] parts=msgAnalysed.split(",");
				errorType[0]=3;
				errorType[1]=Integer.valueOf(parts[1]);
			}
		}
			
		gameCounter++;
		return errorType;//errorType=1-3 δεν είναι errors, 4-5 έχουν γίνει κάποια errors
	}
}
