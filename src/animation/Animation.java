package animation;


import gui.WorldWindow;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import utils.Vec3;

public class Animation {


	
	@SuppressWarnings("unused")
	private WorldWindow worldView;
	
	private AnimationTimer animationTimer;
    private Timeline timeline;
	
	private boolean animation_stop;
	private boolean animation_running;
	private boolean animation_pause;
	
	//private Integer frame;
	private long startTime;
	private boolean timeFlag=false;
	
	private double animationTime 	= 0;
	private long tmo 				= 0;
	private long tailCount 			= 0;
	private double tailFrequency 	= 0.3;
	private boolean showTail 		= true;
	
	
	public Animation(WorldWindow worldView) {
		this.worldView = worldView;
		animation_stop = true;
		animation_running=false;
		animation_pause=false;
		animationTime=0;
		
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
		
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	animationTime = 0;
            	if ( !timeFlag ) {
            		startTime = now;
            		timeFlag =true;
            		tmo = 0;
            	} else {
            		animationTime = (now - startTime)*0.000000001;
            	}
            	try {
            		
            		// Generate interpolated state
	            	//SequenceSet set = interpolateData(animationTime, animationFile, animationFrame);
	            	//SequenceSet set = returnData(animationTime, animationFile, animationFrame);
	            	// Update model position
            		double dt = (now - tmo)*0.000000001;
            		if ( dt > 0 && dt < 1) {
            			worldView.getUniverse().update(dt);
            		} else {
            			System.out.println("Time step error: dt = "+ dt);
            		}
	            	// Update model attitude
	            	// TBD
            		// Update Camera 
            		if ( worldView.isThirdPersonCamera() ) {
            			worldView. setCameraToLastRelativePosition();
            		}
	            	// Update model HUD info
	            	//worldView.setAnimationTime(animationTime);
	            	//worldView.updateModelAttitude(set.attitude);
	            	// Update Tail 
            		if (showTail) {
	            		double dTail = (now - tailCount)*0.000000001;
		            	if ( dTail > tailFrequency ) {
		            		worldView.getUniverse().updateTail(animationTime);
		            		tailCount = now;
		            	} 
            		}
	            	
            	} catch (Exception exp ) {
            		
            	}
            	tmo = now;
            }
        };
        
	}
	
	public void start() {
		animationTimer.start();
		animation_stop = false;
		animation_running=true;
		animation_pause=false;
		worldView.clearTails();
	}
	
	public void stop() {
		animationTimer.stop();
		animation_stop = true;
		animation_running=false;
		animation_pause=false;
		timeFlag = false;
		worldView.clearTails();
	}
	
	public void pause() {
		
	}

	public boolean isAnimation_stop() {
		return animation_stop;
	}

	public boolean isAnimation_running() {
		return animation_running;
	}

	public boolean isAnimation_pause() {
		return animation_pause;
	}
	
	public void propagator() {
		if (animation_stop && !animation_running) {
			worldView.getUniverse().removeTail();
			double dt = 0.01;
			double dtShow = 0.5;
			double dmo =0;
			double tmax = 10;
			for( double time = 0 ; time<=tmax ; time += dt) {
				worldView.getUniverse().propUpdate(dt);
				if ( (time - dmo ) > dtShow ) {
					Vec3 pos = worldView.getReferenceBody().getPosition();
					if (worldView.getReferenceBody().checkIntersect()) {
						break;
					} else {
						worldView.getReferenceBody().getTail().addTailElement(time, pos);
					}
					dmo = time;
				}
			}
			worldView.getUniverse().reset();
		}
	}
	
	
}