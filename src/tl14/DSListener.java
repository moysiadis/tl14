package tl14;

import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.io.Serializable;

public class DSListener implements DragSourceListener,Serializable{

	@Override
	public void dragDropEnd(DragSourceDropEvent dsde) {
		// TODO Auto-generated method stub
		System.out.print("4"+dsde.getDropSuccess());
		
	}

	@Override
	public void dragEnter(DragSourceDragEvent dsde) {
		System.out.print("3\n");
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
