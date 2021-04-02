package environment;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;

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
		environment.getChildren().add(body.getSphere());
	}
	
	public void update(double dt) {
		for(int i=0;i<Bodies.size();i++) {
			Bodies.get(i).updateState(dt);
			environment.getChildren().get(i).setTranslateX(Bodies.get(i).getPosition().x);
			environment.getChildren().get(i).setTranslateY(Bodies.get(i).getPosition().y);
			environment.getChildren().get(i).setTranslateZ(Bodies.get(i).getPosition().z);
		}
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

	
	public Group get3dElements() {
		return environment;
	}
	
	public void updateTail(double time) {
		for(int i=0;i<Bodies.size();i++) {
			Bodies.get(i).getTail().addTailElement(time, Bodies.get(i).getPosition());
			Bodies.get(i).getTail().updateTail(time);
		}
	}
	
	public void removeTail() {
		for(int i=0;i<Bodies.size();i++) {
			Bodies.get(i).getTail().clear();
		}
	}
	
}
