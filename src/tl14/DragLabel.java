package tl14;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceListener;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.TransferHandler;

public class DragLabel extends JLabel implements Transferable{

	private DragSource dragSource;
	private DragGestureListener dgListener;
	private DragSourceListener dsListener;
	public static final DataFlavor labelFlavor = DataFlavor.imageFlavor;
	private DragLabel dl=this;
	private boolean ready;
	private MainFrame mf;
	
	
	protected static DataFlavor dragLabelFlavor =
			new DataFlavor(DragLabel.class, "chip");

	protected static DataFlavor[] supportedFlavors = {
			dragLabelFlavor,
			DataFlavor.stringFlavor,
	};

	public DragLabel(boolean team,MainFrame mf) {

		this.mf=mf;
		this.setTransferHandler(new TransferHandler("icon"));
		ImageIcon image1,image2;
		image1= new  ImageIcon("icons/whiteChip.gif");
		image2=new ImageIcon("icons/blueChip.gif");

		
		if(team)
			this.setIcon(image2);
		else
			this.setIcon(image1);

		this.dragSource = DragSource.getDefaultDragSource();
		this.dgListener = new DGListener();
		this.dsListener = new DSListener(mf);
		// component, action, listener
		this.dragSource.createDefaultDragGestureRecognizer(
				this, DnDConstants.ACTION_MOVE, this.dgListener );
	}
	
	
	///////////////////////////////////////////
	@Override
	public DataFlavor[] getTransferDataFlavors() { return supportedFlavors; }

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		if (flavor.equals(dragLabelFlavor) || 
				flavor.equals(DataFlavor.stringFlavor)) return true;
		return false;
	}

	@Override
	public Object getTransferData(DataFlavor flavor) 
			throws UnsupportedFlavorException
			{
		if (flavor.equals(dragLabelFlavor))
			return this;
		else if (flavor.equals(DataFlavor.stringFlavor)) 
			return this.toString();
		else 
			throw new UnsupportedFlavorException(flavor);
			}
//	///////////////////////////////////////////
	public void setReady(boolean t){
		ready=t;
	}
	
	public boolean getReady(){
		return ready;
	}

	class DGListener implements DragGestureListener, Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void dragGestureRecognized(DragGestureEvent event) {
			System.out.println("drag gesture");
			
			
			JComponent jC = (JComponent) event.getComponent();
	        Cursor cursor = null;
	        if(! (jC instanceof DragLabel))
	        	return;
	        
	        if (event.getDragAction() == DnDConstants.ACTION_MOVE) {
	            cursor = DragSource.DefaultMoveDrop;
	        }
	        event.startDrag(cursor, (Transferable) jC , dsListener);
			
		}

	}
}