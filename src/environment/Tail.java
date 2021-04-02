package environment;

import java.util.ArrayList;
import java.util.List;

import gui.WorldWindow;
import utils.Vec3;

public class Tail {

List<TailElement> TailElements;
double tailRadius = 40;
double cutOffAge  = 5;
private int maxTailSize = 500;
WorldWindow worldWindow;

	public Tail(WorldWindow worldWindow) {
		TailElements = new ArrayList<TailElement>();
		this.worldWindow = worldWindow;
	}
	
	public void addTailElement(double time, Vec3 position) {
		if (TailElements.size() <= maxTailSize ) {
			TailElements.add(new TailElement(tailRadius, time, position, worldWindow));
		}
	}
	
	public void updateTail(double time) {
		for (int i = TailElements.size(); i >= 0; i--) {
			TailElement tailE = TailElements.get(i);
			if ((time - tailE.getTime()) > cutOffAge ) {
				worldWindow.getTailGroup().getChildren().remove(tailE.getSphere());
				TailElements.remove(tailE);
			}
		}
	}
	
	public void clear() {
		for (int i = TailElements.size(); i >= 0; i--) {
			try {
				TailElement tailE = TailElements.get(i);
				worldWindow.getTailGroup().getChildren().remove(tailE.getSphere());
				TailElements.remove(tailE);
			} catch (Exception exp ) {
				
			}
		}
	}
}
