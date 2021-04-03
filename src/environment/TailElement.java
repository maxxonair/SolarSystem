package environment;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import utils.Vec3;

public class TailElement {

	private double time;
	private Sphere sphere;
	private Color TAIL_COLOR = Color.RED;
	
	public TailElement(double radius, double time, Vec3 position, Group tailGroup) {
		sphere = createSphere(radius);
		sphere.setTranslateX(position.x);
		sphere.setTranslateY(position.y);
		sphere.setTranslateZ(position.z);
		tailGroup.getChildren().add(sphere);
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

	public Sphere getSphere() {
		return sphere;
	}
	
}
