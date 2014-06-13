package tl14;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class piece extends JComponent{
	private static final long serialVersionUID = 1L;
	int pos1,pos2,size;
	boolean team;
	
	
	public piece(int pos1,int pos2,boolean team){
		this.pos1=pos1;
		this.pos2=pos2;
		this.team=team;
		size=40;
	}
	
	@Override 
    public Dimension getPreferredSize()
    {
        return (new Dimension(size, size));
    }
	
	public void setPosition(int col,int lin){
		pos1=col;
		pos2=lin;
	}
	
	public int[] getPosition(){
		int[] temp={pos1,pos2};
		
		
		
		
		
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
        g2d.drawOval(pos[0], pos[1], size, size);
        g2d.fillOval(pos[0], pos[1], size, size);
	}
}