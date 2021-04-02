package environment;

import java.util.ArrayList;
import java.util.List;

import gui.WorldWindow;
import utils.Vec3;

public class Tail {

List<TailElement> TailElements;
double tailRadius = 20;
double cutOffAge = 12;
WorldWindow worldWindow;

	public Tail(WorldWindow worldWindow) {
		TailElements = new ArrayList<TailElement>();
		this.worldWindow = worldWindow;
	}
	
	public void addTailElement(double time, Vec3 position) {
		TailElements.add(new TailElement(tailRadius, time, position, worldWindow));
	}
	
	public void updateTail(double time) {
		for (TailElement tailE : TailElements) {
			if ((time - tailE.getTime()) > cutOffAge ) {
				worldWindow.getTailGroup().getChildren().remove(tailE.sphere);
				TailElements.remove(tailE);
			}
		}
	}
	
	public void clear() {
		for (TailElement tailE : TailElements) {
			worldWindow.getTailGroup().getChildren().remove(tailE.sphere);
			TailElements.remove(tailE);
		}
	}
}
