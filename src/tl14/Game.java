package tl14;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


public class Game {
	
	private ArrayList<Integer> localPos;
	private ArrayList<Integer> awayPos;
	private boolean playerNo;
	private Connection conn;
	private AnalyseGame anG;
	private int d1,d2;
	private int gameCounter;
	private String name1,name2;//localplayer,awayPlayer
	private MainFrame mf;
	
	
	//gameMode,portNo,ip(String),localname,awayname,mainFrame)
	public Game(int gameMode,int portNo, String ipNo,String name1,String name2,MainFrame mf){
		//playerNo=pNo;
		conn=new Connection();
		gameCounter=0;
		setConnection(portNo, ipNo);
		this.name1=name1;
		this.name2=name2;
		this.mf=mf;
		localPos=new ArrayList<Integer>();
		awayPos=new ArrayList<Integer>();
		
		for(int i=0;i<=25;i++)
		{
			localPos.add(0);
			awayPos.add(0);
		}
		InitializeGame(gameMode);
		anG=new AnalyseGame(localPos,awayPos);
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
			
		}
			
	}

	public ArrayList<ArrayList<Integer>> PossibleMoves(){
		//�������� ��� ��� ������� ���������� ��� ������ ��� ������ ������ ����� ������ � ���
		//������� �� �� ���� ����� ���� �������� �� ������� � �������
		//������������ ���� ������� �� ������� ������,�� ��������� �� ������� ������� � ������� ���� -1
		
		ArrayList<Integer> moves=new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> temp=new ArrayList<ArrayList<Integer>>();
		
		for(int i=0;i<=localPos.size();i++){
			int[] moveArray={0,0,0,0};
			moves.clear();
			if (localPos.get(i)!=0) {//��� ������� ������ ��� ����� ������
				moveArray = anG.possibleMoves(d1, d2, i);
				if(!(moveArray[0]==0 && moveArray[1]==0)){
					for (int j = 0; j <= 4; j++) {
						moves.add(moveArray[i]);
					}
					moves.add(i);//���� ��������� ���� �������� ��� ��� ������ 
								 //��� ������ ������ �� ����� � ������
					temp.add(moves);
				}
			}
		}
		//////////////////////////////
		
		return temp;
	}
	
	public ArrayList<Integer> getAwayPositions(){//��� ����� �� Frame ��� �� ��������� ��� ������ ��� ���������
		return awayPos;
	}
	
	public ArrayList<Integer> getLocalPositions(){//��� ����� �� Frame ��� �� ��������� ��� ������ ��� ���������
		return localPos;
	}
	
	
	public void setConnection(int port,String ip){
		conn.setConnectionDetails(port, ip);
	}
	
	public int[] setMoves(ArrayList<String> moves){
		//��� MainFrame ���� ��� ���������� ��� �������� ������������� �� ��� ArrayList
		//��� ���������� ��� setMove, ���� ������� ��������� �� AnalyseMove ���� �� �����������
		//�� ArrayLists ��� ������� ���������� ��� ��� ��������� ��� ����������
		//��� �� Frame �� �������� �������� ��� ����� "<oldPosNo>-<newPosNo>"
		int erNo;

		
		
		//������� ������, ����������� String ��� ��� ������ ��� ���������
		String moveToSend="";
		moveToSend=anG.setMoveForm(moves);
		localPos=new ArrayList<Integer>(anG.getLocal());
		if(anG.NeedAwayChange()){
			awayPos=new ArrayList<Integer>(anG.getAway());
		}
		
		//erNo=1 �������� ��������
		//erNo=2 exception
		//erNo=3 sendMsg=null
		erNo=conn.Send(moveToSend);
		gameCounter++;
		
		if(isWinner()==1){
			int[] temp={7,1};
			return temp;
		}else if(isWinner()==2){
			int[] temp={7,2};
			return temp;
		}
		
		return receiveMsg(erNo);
	}
	
	public int isWinner(){
		if(localPos.get(25)==15)
			return 1;
		if(awayPos.get(25)==15)
			return 2;
		
		return 0;
	}
	
	
	
	public int getGameCounter(){
		return gameCounter;
	}
	
	public int Connect(){
		try {
			conn.connectToserver();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return -1;
		}
		
		
		return 0;
		
		
	}
	
	public void resetConn(){
		conn.Send("errorreset");
	}
	
	private void sendServerDetails(){
		String temp="";
		temp=("player,");
		
		temp=temp.concat(name1);
		if(!name2.equals(""))
			temp=temp.concat(name2);
		else
			temp=temp.concat(",-1");
			
		System.out.print("temp: "+temp+"\n");
		conn.Send(temp);		
	}
	
	public int[] receiveMsg(int  erNo){
		int[] errorType={0,0,0};
		String serverMsg="";
		ArrayList<String> msgAnalysed=new ArrayList<String>();
		
		
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
		
		
		if(errorType[0]!=5){
			
			msgAnalysed=anG.analyseMsg(serverMsg);
			System.out.println("++++msg: "+msgAnalysed.get(0)+"++++\n");
			
			
			if (erNo==1) {
				///////////////////////////
				if (msgAnalysed.get(0).equals("move")) {
					awayPos = new ArrayList<Integer>(anG.analyseMove(false,
							serverMsg));
					if (anG.NeedLocalChange()) {
						localPos = new ArrayList<Integer>(anG.getLocal());
					}
					errorType[0] = 1;
				}
				///////////////////////////
			}else if(erNo==0){
				
				if(msgAnalysed.get(0).equals("dice")){
					//�� ��� �� ����� <dice,<1-6>,<1-6>>
					errorType[1]=d1=Integer.valueOf(msgAnalysed.get(1));
					errorType[2]=d2=Integer.valueOf(msgAnalysed.get(2));
					errorType[0]=2;
					
				}else if(msgAnalysed.get(0).equals("playerno")){
					errorType[0]=3;
					errorType[1]=Integer.valueOf(msgAnalysed.get(1));
					if(errorType[0]==1){
						playerNo=false;//�� errorType[0]=1 ���� � ������� ������ �������� 
					}
				}else if(msgAnalysed.get(0).equals("sendname")){
					sendServerDetails();
					errorType[0]=0;
				}else if(msgAnalysed.get(0).equals("searchwait")){
					errorType[0]=6;
				}
			}
			
		}else{
			System.out.println("Problem in receive");
		}
			
		
		return errorType;//errorType=0-3+6 ��� ����� errors, 4,5 ����� ����� ������ errors
	}
	
	public void resetGame(){
		InitializeGame(1);
	}
	public void closeConnection(){
		conn.close();
	}
}
