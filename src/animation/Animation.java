package animation;


import gui.WorldWindow;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;

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
	
	private double animationTime;
	private long tmo = 0;
	private long tailCount = 0;
	
	
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
            		if ( dt > 0 && dt < 2) {
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
            		double dTail = (now - tailCount)*0.000000001;
	            	if ( dTail > 0.2 ) {
	            		worldView.getUniverse().updateTail(animationTime);
	            		tailCount = now;
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
	
	
}