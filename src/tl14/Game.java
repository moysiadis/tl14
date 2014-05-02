package tl14;

import java.io.IOException;
import java.util.ArrayList;


public class Game {
	
	private ArrayList<Integer> localPos;
	private ArrayList<Integer> awayPos;
	private int playerNo;
	private Connection conn;
	
	public Game(int pNo,int gameMode,int portNo, String ipNo){
		playerNo=pNo;
		conn=new Connection();
		
		conn.setConnectionDetails(portNo, ipNo);

		
		
		for(int i=0;i<=25;i++)
		{
			localPos.add(0);
			awayPos.add(0);
		}
		InitializeGame(gameMode);
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
				
			
		}
			
	}

	private void analyseMsg(){//αναλύει το μήνυμα
		
	}
	
	
	
	public int setMove(ArrayList<Integer> move){
		
		//θέτουμε κίνηση ετοιμάζουμε String για τον σερβερ και στέλνουμε
		String moveToSend="";
		//
		//
		//
		
		//
		
		
		conn.Send(moveToSend);
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
}
