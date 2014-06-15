package tl14;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class MouseListen extends MouseAdapter{
	
	private MainFrame mf;
	private JComponent temp;
	
	
	public MouseListen(MainFrame mf){
		this.mf=mf;
	}
	
	@Override
    public void mouseDragged(MouseEvent e) {
		if(!mf.getTurn()){
			return;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!mf.getTurn()){
			return;
		}		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!mf.getTurn()){
			return;
		}		

	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
