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
		//�� popUp ����������  portNo, ip,�� ����� ��� ������, ��� �� ����� ��� ��������� �� �������
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
						if (noTemp[1] == 0)//�� ����� 0 ���� � ������� ������ ������
							noPlayer = true;

						drawGame();
						noTemp = gm.receiveMsg(0);
					} else {
						flag = false;
						this.callPopUp(1);
					}
				}
				while (flag) { //�������� ���� ��� ��������
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
							noPlayer = true;//��� �� ���������� ���� ������� ���� ���� ��� �����
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
		//������� �� popUp �� ������� ������:
		//0: ��� �������� ������� ��� ���������� �� �������� ��������
		//1: ��� connection error
		//2: ����� �� ����
		//3: ������� ��� ��������� ����������, �� ������ �������� �� ������� �� true � playAgain
		String[] temp=null;
		
		
		
		
		
		
		
		
		
		return temp;
	}
	
	private ArrayList<String> getGraphicMove(){//��������� ��� ������ �� ����� ��� ������ ���
		ArrayList<String> move=null;
		ArrayList<ArrayList<Integer>> pMoves;
		pMoves=new ArrayList<ArrayList<Integer>>(gm.PossibleMoves());
		
		
		return move;
 	}
	
	private void setGraphicDice(int d1,int d2){
		
	}
	
	private void drawGame(){//��������� ��� ������ ��� ������� ��� �����
		awayPos=gm.getAwayPositions();
		localPos=gm.getLocalPositions();
		
		
		
		
	}
	
	
}
