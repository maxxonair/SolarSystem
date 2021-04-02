package environment;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.shape.Sphere;
import utils.Vec3;

public class Environment {

	private List<CelestialBody> Bodies;
	private double gravitationConstant = 6.67408E-1;
	Group environment;
	
	public Environment() {
		Bodies = new ArrayList<CelestialBody>();
		environment = new Group();
		reset();
	}
	
	public void addBody(CelestialBody body) {
		body.setEnvironment(this);
		Bodies.add(body);
		Sphere bodyElem = createSphere(body.getRadius());
		bodyElem.setTranslateX(body.getPosition().x);
		bodyElem.setTranslateY(body.getPosition().y);
		bodyElem.setTranslateZ(body.getPosition().z);
		environment.getChildren().add(bodyElem);
	}
	
	public void update(double dt) {
		for(int i=0;i<Bodies.size();i++) {
			Bodies.get(i).updateState(dt);
			environment.getChildren().get(i).setTranslateX(Bodies.get(i).getPosition().x);
			environment.getChildren().get(i).setTranslateY(Bodies.get(i).getPosition().y);
			environment.getChildren().get(i).setTranslateZ(Bodies.get(i).getPosition().z);
		}
	}
	
	public void reset() {
		for(int i=0;i<Bodies.size();i++) {
			Bodies.get(i).init();
		}
	}
	
	public void removeAllBodies() {
		Bodies.clear();	
		environment.getChildren().clear();
	}

	public List<CelestialBody> getBodies() {
		return Bodies;
	}

	public double getGravitationConstant() {
		return gravitationConstant;
	}

	public void setGravitationConstant(double gravitationConstant) {
		this.gravitationConstant = gravitationConstant;
	}
	
	public Sphere createSphere(double radius) {
		Sphere sphere = new Sphere();
		sphere.setRadius(radius);
		return sphere;
	}
	
	public Group get3dElements() {
		return environment;
	}
	
	public void updateTail(double time) {
		for(int i=0;i<Bodies.size();i++) {
			Bodies.get(i).getTail().addTailElement(time, Bodies.get(i).getPosition());
			Bodies.get(i).getTail().updateTail(time);
		}
	}
	
}
