package environment;

import gui.WorldWindow;
import utils.Vec3;

public class CelestialBody {
	
	private double mass;
	private double radius;
	private Environment environment;
	private Vec3 velocity;
	private Vec3 position;
	private Vec3 initialVelocity;
	private Vec3 initialPosition;
	private Tail tail;
	
	public CelestialBody(double mass, Vec3 initialPosition, Vec3 initialVelocity, WorldWindow worldWindow) {
		
		this.tail = new Tail(worldWindow);
		this.mass = mass;
		
		this.initialPosition = new Vec3();
		this.initialVelocity = new Vec3();
		this.velocity = new Vec3();
		this.position = new Vec3();
		
		this.initialPosition.x = initialPosition.x;
		this.initialPosition.y = initialPosition.y;
		this.initialPosition.z = initialPosition.z;
		
		this.initialVelocity.x = initialVelocity.x;
		this.initialVelocity.y = initialVelocity.y;
		this.initialVelocity.z = initialVelocity.z;
		
		this.radius = 0;
		
		init();
	}
	
	public void init() {
		this.velocity.x = initialVelocity.x;
		this.velocity.y = initialVelocity.y;
		this.velocity.z = initialVelocity.z;
		
		this.position.x = initialPosition.x;
		this.position.y = initialPosition.y;
		this.position.z = initialPosition.z;
	}
	
	
	public void updateState(double dt) {
		// Update Velocity 
		for (CelestialBody body : environment.getBodies()) {
			if ( body != this) {
				Vec3 vec = new Vec3();
				vec.x = body.getPosition().x;
				vec.y = body.getPosition().y;
				vec.z = body.getPosition().z;
				vec.substractVec(this.position);
				double sqrtDst =  vec.getSize() * vec.getSize();
				vec.normalize();
				Vec3 acc = new Vec3();
				acc.x = vec.x * environment.getGravitationConstant() * body.getMass() / sqrtDst;
				acc.y = vec.y * environment.getGravitationConstant() * body.getMass() / sqrtDst;
				acc.z = vec.z * environment.getGravitationConstant() * body.getMass() / sqrtDst;

				velocity.x += acc.x * dt;
				velocity.y += acc.y * dt;
				velocity.z += acc.z * dt;	
			}
		}		
		// Update Position
	    position.x += velocity.x * dt;
		position.y += velocity.y * dt;
		position.z += velocity.z * dt;	
		
		// Debug
		
							
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public Vec3 getVelocity() {
		return new Vec3(velocity.x,velocity.y,velocity.z);
	}

	public Vec3 getPosition() {
		return new Vec3(position.x,position.y,position.z);
	}

	public double getMass() {
		return mass;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public Tail getTail() {
		return tail;
	}	
	
	
}
