package tl14;

import java.awt.Point;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.io.Serializable;

import javax.swing.JPanel;

public class DSListener implements DragSourceListener,Serializable{

	private MainFrame mf;
	public DSListener(MainFrame mf){
		this.mf=mf;
	}
	@Override
	public void dragDropEnd(DragSourceDropEvent dsde) {
		// TODO Auto-generated method stub
		System.out.print("4"+dsde.getDropSuccess());
		
	}
	
	@Override
	public void dragEnter(DragSourceDragEvent dsde) {
		mf.getMoves();
		DragLabel d=(DragLabel) dsde.getSource();
		if(!d.getReady())
			return;
		Point p=dsde.getLocation();
		int pos=mf.getLine((JPanel) mf.getComp(p));
		mf.writeMove(pos,1);
		System.out.print("3: "+p);
	}

	@Override
	public void dragExit(DragSourceEvent dse) {
		// TODO Auto-generated method stub
		System.out.print("2");
	}

	@Override
	public void dragOver(DragSourceDragEvent dsde) {
		//System.out.print("1");
	}

	@Override
	public void dropActionChanged(DragSourceDragEvent dsde) {
		// TODO Auto-generated method stub
		System.out.print("5");
	}

}
