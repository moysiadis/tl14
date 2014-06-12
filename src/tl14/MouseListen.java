package tl14;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseListen implements MouseListener{
	
	private MainFrame mf;
	
	public MouseListen(MainFrame mf){
		this.mf=mf;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(!mf.getTurn()){
			return;
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if(!mf.getTurn()){
			return;
		}
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if(!mf.getTurn()){
			return;
		}		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(!mf.getTurn()){
			return;
		}		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(!mf.getTurn()){
			return;
		}		
	}

}
