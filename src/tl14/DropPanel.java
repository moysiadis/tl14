package tl14;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

import javax.swing.JPanel;

public class DropPanel extends DropTargetAdapter {

		private DropTarget dropTarget;
		private JPanel panel;
		private MainFrame mf;
		public DropPanel(JPanel panel,MainFrame mf) {
			this.panel = panel;
			this.mf=mf;
			dropTarget = new DropTarget(panel, DnDConstants.ACTION_MOVE, 
					this, true, null);
		}


		public void drop(DropTargetDropEvent event) {
			try {
				System.out.print("in\n");
				Transferable tr = event.getTransferable();
				DragLabel dl=(DragLabel) tr.getTransferData(DragLabel.dragLabelFlavor);
				if (dl.isDataFlavorSupported(DragLabel.dragLabelFlavor) ) {

					event.acceptDrop(DnDConstants.ACTION_MOVE);
					this.panel.add(dl);
					this.panel.validate();
					this.panel.repaint();
					System.out.print("in");
					mf.getLine(this.panel);
					event.dropComplete(true);
					return;
				}
				event.rejectDrop();
			} catch (Exception e) {
				e.printStackTrace();
				event.rejectDrop();
			}
		}
	
}
