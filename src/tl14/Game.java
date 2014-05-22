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
	
	public Game(int gameMode,int portNo, String ipNo,String name1,String name2,MainFrame mf){
		//playerNo=pNo;
		conn=new Connection();
		gameCounter=0;
		setConnection(portNo, ipNo);
		this.name1=name1;
		this.name2=name2;
		this.mf=mf;
		
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
	
	public ArrayList<Integer> getNewPositions(){//��� ����� �� Frame ��� �� ��������� ��� ������ ��� ���������
		return awayPos;
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
		erNo=conn.Send(moveToSend);
		
		//erNo=1 �������� ��������
		//erNo=2 exception
		//erNo=3 sendMsg=null
		
		return receiveMsg(erNo);
	}
	
	public int getGameCounter(){
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
	
	
	private void sendServerDetails(){
		String temp;
		temp=name1;
		if(!name2.equals(""))
			temp.concat(name2);
		
		conn.Send(temp);		
	}
	
	public int[] receiveMsg(int  erNo){
		int[] errorType={0,0,0};
		String serverMsg="";
		String[] msgAnalysed=null;
		
		
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
			
			if (erNo==1) {
				///////////////////////////
				if (msgAnalysed.equals("move")) {
					awayPos = new ArrayList<Integer>(anG.analyseMove(false,
							serverMsg));
					if (anG.NeedLocalChange()) {
						localPos = new ArrayList<Integer>(anG.getLocal());
					}
					errorType[0] = 1;
				}
				///////////////////////////
			}else if(erNo==0){
				
				if(msgAnalysed[0].equals("dice")){
					//�� ��� �� ����� <dice,<1-6>,<1-6>>
					errorType[1]=d1=Integer.valueOf(msgAnalysed[1]);
					errorType[2]=d2=Integer.valueOf(msgAnalysed[2]);
					errorType[0]=2;
					
				}else if(msgAnalysed[0].equals("playerNo")){
					errorType[0]=3;
					errorType[1]=Integer.valueOf(msgAnalysed[1]);
					if(errorType[0]==1){
						playerNo=false;//�� errorType[0]=1 ���� � ������� ������ �������� 
					}
				}else if(msgAnalysed[0].equals("sendname")){
					sendServerDetails();
					errorType[0]=0;
				}
			}
			
		}else{
			System.out.println("Problem in receive");
		}
			
		gameCounter++;
		return errorType;//errorType=0-3 ��� ����� errors, 4-5 ����� ����� ������ errors
	}
}
