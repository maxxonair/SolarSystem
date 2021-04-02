package environment;

import gui.WorldWindow;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import utils.Vec3;

public class TailElement {

	private double time;
	public Sphere sphere;
	private Color TAIL_COLOR = Color.CYAN;
	
	public TailElement(double radius, double time, Vec3 position, WorldWindow worldWindow) {
		sphere = createSphere(radius);
		sphere.setTranslateX(position.x);
		sphere.setTranslateY(position.y);
		sphere.setTranslateZ(position.z);
		worldWindow.getTailGroup().getChildren().add(sphere);
		this.time = time; 
	}
	
	public Sphere createSphere(double radius) {
		Sphere sphere = new Sphere();
		PhongMaterial phong = new PhongMaterial();
		phong.setDiffuseColor(TAIL_COLOR);
		sphere.setMaterial(phong);
		sphere.setRadius(radius);
		return sphere;
	}

	public double getTime() {
		return time;
	}
	
	
}
