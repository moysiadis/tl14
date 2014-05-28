package tl14;

import java.util.ArrayList;

public class AnalyseGame {//rules,legal moves,game writer
	
	private ArrayList<Integer> localPos;
	private ArrayList<Integer> awayPos;
	private boolean needLocalChange;
	private boolean needAwayChange;
	private String[] strArrays;
	
	public AnalyseGame(ArrayList<Integer> localPos,ArrayList<Integer> awayPos){
		this.awayPos= new ArrayList<Integer>(awayPos);
		this.localPos=new ArrayList<Integer>(localPos);		
	}
	
	/*public void setLocal(ArrayList<Integer> lc){
		localPos= new ArrayList<Integer>(lc);
	}*/
	
	public ArrayList<Integer> getLocal(){
		return localPos;
	}
	public ArrayList<Integer> getAway(){
		return awayPos;
	}
	public boolean NeedLocalChange(){
		if(needLocalChange){
			needLocalChange=false;
			return true;
		}else
			return false;
	}
	public boolean NeedAwayChange(){
		if(needAwayChange){
			needAwayChange=false;
			return true;
		}else
			return false;
	}
	
	public ArrayList<Integer> analyseMove(boolean pNo,String moveReceived){
		//pNo=false είναι κίνηση αντιπάλου, αλλιώς κίνηση local
		//move,<posNoFrom>-<posNoTo>,
		ArrayList<String> move=new ArrayList<String>();
		ArrayList<Integer> temp=new ArrayList<Integer>();
		
		if(pNo)
			temp=localPos;
		else
			temp=awayPos;
		
		move=cutString(moveReceived,",");		
		for(int i=0;i<move.size();i++){
			int value1= Integer.valueOf(String.valueOf(move.get(i).charAt(0)));
			int value2 = Integer.valueOf(String.valueOf(move.get(i).charAt(2)));
			
			if(move.get(i).charAt(0)=='o')//έλεγχος αν καίγεται πούλι
				moveHit(pNo,move.get(i).substring(2));
			
			if (!pNo) {//μετάφραση του αντίπαλου local σε τοπικό away				
				value1 = 25 - value1;
				value2 = (-value2);
			}
			
			temp.set(value1, localPos.get(value1)-1);
			temp.set(value1+value2, localPos.get(value2)+1);
			
			
			/*if(pNo){				
				if(move[i].charAt(0)=='o')//έλεγχος αν καίγεται πούλι
					moveHit(pNo,move[i].substring(1));
				else{
					int value1=Integer.valueOf(move[i].charAt(0));
					int value2=Integer.valueOf(move[i].charAt(2));
					localPos.set(value1, localPos.get(value1)-1);
					localPos.set(value1+value2, localPos.get(value2)+1);
				}
			}else{
				//μετάφραση του αντίπαλου local σε τοπικό away
				int value1=Integer.valueOf(move[i].charAt(0));
				int value2=Integer.valueOf(move[i].charAt(2));
				
				value1=25-value1;
				value2=(-value2);
				
			}*/
		}
		return temp;
		
		
		
		
	}
	
	public String setMoveForm(ArrayList<String> move){
		String toSend="move,";
		String hits="o-";
		ArrayList<String> temp=new ArrayList<String>();
		
		for(int i=0;i<=move.size();i++){
			temp.clear();
			temp=cutString(move.get(i),"-");
			toSend.concat(temp.get(0)+"-"+temp.get(1)+",");
			int posValue=Integer.valueOf(temp.get(0))+Integer.valueOf(temp.get(1));
			
			if(awayPos.get(posValue)==1){
				/*το μέρος αυτό διαγράφεται επειδή η ανάλογη διευθέτηση γίνεται στο analyseMove
				 * awayPos.set(posValue, 0);
				 *awayPos.set(25, awayPos.get(25)+1);
				 *needAwayChange=true;
				 */
				hits.concat(String.valueOf(posValue));
			}			
		}

		toSend.concat(hits);
		analyseMove(true,toSend);
		return toSend;
		
	}
	
	private void moveHit(boolean pNo,String hits){
		String[] temp;
		//ArrayList<Integer> tempList=new ArrayList<Integer>();
		temp=hits.split("-");
		
		if(pNo){
			needAwayChange=true;
			for(int i=0;i<=temp.length;i++){
				awayPos.set(25-Integer.valueOf(temp[i]), 0);
				awayPos.set(25,awayPos.get(25)+1);
			}
		}else{
			needLocalChange=true;
			for(int i=0;i<=temp.length;i++){
				localPos.set(i, 0);
				localPos.set(0,localPos.get(0)+1);
			}
		}

	}
	
	@SuppressWarnings("null")
	public int[] possibleMoves(int d1,int d2,int position){
		int[] move={0,0,0,0};
		int[] dice = null;
		boolean[] flags={false,false,false,false};
		boolean tempF=false;
		dice[0]=d1;
		dice[1]=d2;

		if(d1!=d2){
			for(int i=0;i<dice.length;i++){
				tempF=checkNextPos(position,dice[i]);
				if(tempF){
					flags[i]=tempF;
					move[i]=position+dice[i];
				}
			}
			if(flags[0] || flags[1]){
				tempF=checkNextPos(position,dice[0]+dice[1]);
				if(tempF){
					flags[3]=tempF;
					move[3]=position+dice[0]+dice[1];
				}
			}
		}else{
			tempF=checkNextPos(position,dice[0]);
			if(tempF){
				flags[0]=tempF;
				move[0]=position+dice[0];
			}
			int i=1;
			while(flags[i-1] && i<4){
				dice[0]+=dice[0];
				tempF=checkNextPos(position,dice[0]);
				if(tempF){
					flags[0]=tempF;
					move[0]=position+dice[0];
				}
				i++;
			}
		}	
		//////////////////////////////////

		return move;
	}
	
	private boolean checkNextPos(int pos,int dice){
		if(localPos.get(pos+dice)!=0 && awayPos.get(pos+dice)<2){
			return true;
		}else return false;
	}
	
	public ArrayList<String> analyseMsg(String serverMsg){
		
		ArrayList<String> Msg =new ArrayList<String>();
		
		if(serverMsg.startsWith("move")){
			Msg.add("move");
		}else{
			Msg=cutString(serverMsg,",");
		}
		
		return Msg;
	}
	
	private ArrayList<String> cutString(String temp,String regEx){
		
		ArrayList<String> mArray =new ArrayList<String>();
		
		for(int i=0;i<temp.split(regEx).length;i++){
			mArray.add(temp.split(regEx)[i]);
		}
		
		
		System.out.print("mArray "+temp.split(regEx)[0]+mArray);
		
		if(mArray.get(0).equals("move")){
			mArray.remove(0);
		}
		return mArray;
	}
}
