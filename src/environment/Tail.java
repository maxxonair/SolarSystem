package environment;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import utils.Vec3;

public class Tail {

private List<TailElement> TailElements;
private double tailRadius 	= 40;
private double cutOffAge  	= 10;
private int MAX_TAIL_SIZE   = 500;
private Group tailGroup;

	public Tail() {
		// init tail list:
		TailElements = new ArrayList<TailElement>();
		// init tail group for 3d elements
		tailGroup = new Group();
	}
	
	public void addTailElement(double time, Vec3 position) {
		if (TailElements.size() <= MAX_TAIL_SIZE ) {
			TailElements.add(new TailElement(tailRadius, time, position, tailGroup));
		}
		updateTail(time) ;
	}
	
	public void updateTail(double time) {
		for (int i = TailElements.size()-1; i >= 0; i--) {
			TailElement tailE = TailElements.get(i);
			if ( (time - tailE.getTime()) > cutOffAge ) {
				tailGroup.getChildren().remove(tailE.getSphere());
				TailElements.remove(tailE);
			}
		}
	}
	
	public void clear() {
		if (this.TailElements.size() > 0 ) {
			for (int i = this.TailElements.size()-1; i >= 0; i--) {
				    this.tailGroup.getChildren().remove( i );
					this.TailElements.remove( i );
			}
		}
	}

	public Group getTailGroup() {
		return tailGroup;
	}
	
	
}
