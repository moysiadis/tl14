package tl14;
import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame{
	
	private JPanel panel;
	private Game gm;
	
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

			gm=new Game(1,Integer.valueOf(temp[0]),temp[1],temp[2],temp[3],this);
			while(gm.Connect()!=1){
				temp= callPopUp(1);
				if(temp[0].equals("details")){
					gm.setConnection(Integer.valueOf(temp[1]), temp[3]);
				}
			}
			
			gm.receiveMsg(0);
			
			
		
	}
	
	
	public String[] callPopUp(int mode){
		//������� �� popUp �� ������� ������:
		//0: ��� �������� ������� ��� ���������� �� �������� ��������
		//1: ��� connection error
		//2: ����
		String[] temp=null;
		
		
		return temp;
	}
}
