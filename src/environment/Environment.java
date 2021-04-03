package environment;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;

public class Environment {

	private List<CelestialBody> Bodies;
	private double gravitationConstant = 6.67408E-1;
	private Group environment;
	private Group tailGroup;
	
	public Environment() {
		this.Bodies = new ArrayList<CelestialBody>();
		this.environment = new Group();
		this.tailGroup = new Group();
		reset();
	}
	
	public void addBody(CelestialBody body) {
		body.setEnvironment(this);
		this.Bodies.add(body);
		this.environment.getChildren().add(body.getSphere());
		this.tailGroup.getChildren().add(body.getTail().getTailGroup());
	}
	
	public void update(double dt) {
		for(int i=0;i<Bodies.size();i++) {
			Bodies.get(i).updateState(dt);
		}
		updateModelPosition();
	}
	
	public void propUpdate(double dt) {
		for(int i=0;i<Bodies.size();i++) {
			Bodies.get(i).updateState(dt);
		}
	}
	
	public void reset() {
		for(int i=0;i<Bodies.size();i++) {
			Bodies.get(i).init();
		}
		updateModelPosition();
	}
	
	private void updateModelPosition() {
		for(int i=0;i<Bodies.size();i++) {
			environment.getChildren().get(i).setTranslateX(Bodies.get(i).getPosition().x);
			environment.getChildren().get(i).setTranslateY(Bodies.get(i).getPosition().y);
			environment.getChildren().get(i).setTranslateZ(Bodies.get(i).getPosition().z);
		}
	}
	
	public void removeAllBodies() {
		Bodies.clear();	
		environment.getChildren().clear();
		tailGroup.getChildren().clear();
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

	
	public Group get3dElements() {
		return environment;
	}
	
	public void updateTail(double time) {
		for(CelestialBody body : this.Bodies) {
			body.getTail().addTailElement(time, body.getPosition());
		}
	}
	
	public void removeTails() {
		for(int i=0;i<Bodies.size();i++) {
			Bodies.get(i).getTail().clear();
		}
	}

	public Group getTailGroup() {
		return tailGroup;
	}
	
	
}
