package tl14;

import java.util.ArrayList;

public class AnalyseGame {//rules,legal moves,game writer
	
	private ArrayList<Integer> localPos;
	private ArrayList<Integer> awayPos;
	private ArrayList<String> stringCut;
	
	
	public AnalyseGame(ArrayList<Integer> localPos,ArrayList<Integer> awayPos){
		this.awayPos= new ArrayList<Integer>(awayPos);
		this.localPos=new ArrayList<Integer>(localPos);
		stringCut=new ArrayList<String>();		
	}
	
	public ArrayList<Integer> analyseMove(String moveReceived){
		String move;
		move=moveReceived;
		
	}
	
	public ArrayList<Integer> possibleMoves(int d1,int d2){
		ArrayList<String> posMoves=new ArrayList<String>();
	}
	
	public String analyseMsg(String serverMsg){
		String Msg="";
		//
		//
		//
		//
		return Msg;
	}

}
