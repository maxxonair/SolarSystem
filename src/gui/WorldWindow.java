package gui;

import animation.Animation;
import environment.CelestialBody;
import environment.Environment;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import utils.EmptyButton;
import utils.Vec3;

public class WorldWindow extends Application {

	private PerspectiveCamera camera;
	
	private double DEFAULT_CAMERA_FOV = 40;
	// Third person camera activated:
	private boolean thirdPersonCamera = false; 
	
	private Vec3 DEFAULT_CAMERA_POSITION = new Vec3(-6125,-21075,-25050);
	private Vec3 DEFAULT_RELATIVE_CAMERA_POSITION = new Vec3(-650,-650,-1000);
	
	private int cameraControlIncrement = 25;
	private Vec3 cameraRelativePosition;
	private Vec3 cameraAbsolutePosition;
	private double cameraNearClip=.001;
	private double cameraFarClip = 1000;
	private Button cameraMinusX ;
	private Button cameraPlusX ;
	private Button cameraMinusY ;
	private Button cameraPlusY ;
	private Button cameraMinusZ ;
	private Button cameraPlusZ ;
	private HBox cameraControlGroup;
	private double cameraCtrlSensitivity = 0.1;
	private boolean  goNorth, goSouth, goEast, goWest, goForward, goBackward;
	
	private Group environment;
	private Group rootGroup;
	private Group tailGroup;
	private SubScene scene;
	
	private Rotate cameraRotX,cameraRotY;
	private double x0, y0;
	
	private double sceneHeight;
	private double sceneWidth;
	
	private AnchorPane anchorPane;
	private Vec3 environmentBackgroundColor = new Vec3(0.15,0.15,0.15);
	private double animationTime;
	private CelestialBody referenceBody;
	private CelestialBody sunBody;
	
	
	private Environment universe;
	private Animation animation;
	
	
	public WorldWindow() {
		
	}
	
	@Override
	public void start(@SuppressWarnings("exports") Stage stage) throws Exception{
		//---------------------------------------------------------------------------
		// Model 
		//---------------------------------------------------------------------------
	    rootGroup = new Group();
	    tailGroup = new Group();
	    anchorPane = new AnchorPane();
	    animation = new Animation(this);
		//---------------------------------------------------------------------------
		// Camera
		//---------------------------------------------------------------------------
	    camera = new PerspectiveCamera();
		camera.setNearClip(cameraNearClip);
		camera.setFarClip(cameraFarClip);	
		camera.setFieldOfView(DEFAULT_CAMERA_FOV);
		
		Group cameraGroup = new Group();
		cameraGroup.getChildren().add(camera);

		
		cameraRotY = new Rotate(0, Rotate.Y_AXIS);
	    cameraRotX = new Rotate(-35, Rotate.X_AXIS);
		
		camera.getTransforms().addAll(cameraRotY,cameraRotX);
		camera.setTranslateX(DEFAULT_CAMERA_POSITION.x);
		camera.setTranslateY(DEFAULT_CAMERA_POSITION.y);
		camera.setTranslateZ(DEFAULT_CAMERA_POSITION.z);
		
		cameraAbsolutePosition = new Vec3();
		cameraRelativePosition = new Vec3();
		cameraAbsolutePosition.x = DEFAULT_CAMERA_POSITION.x ;
		cameraAbsolutePosition.y = DEFAULT_CAMERA_POSITION.y ;
		cameraAbsolutePosition.z = DEFAULT_CAMERA_POSITION.z ;
		cameraRelativePosition.x = DEFAULT_RELATIVE_CAMERA_POSITION.x ;
		cameraRelativePosition.y = DEFAULT_RELATIVE_CAMERA_POSITION.y ;
		cameraRelativePosition.z = DEFAULT_RELATIVE_CAMERA_POSITION.z ;
		
		
	    environment = new Group();
		
		rootGroup.getChildren().add(camera);
		rootGroup.getChildren().add(tailGroup);
		
		//---------------------------------------------------------------------------
		//  Add content 
		//---------------------------------------------------------------------------
		
		universe = new Environment();
		referenceBody = new CelestialBody(50,new Vec3(-9000,0,0), new Vec3(0,1600,0), this);
		referenceBody.setRadius(100);
		referenceBody.setColor(Color.BLUE);
	    sunBody = new CelestialBody(5E10 ,new Vec3(0,0,0), new Vec3(0,0,0), this);
		sunBody.setRadius(500);
		sunBody.setColor(Color.YELLOW);
		CelestialBody bodyC = new CelestialBody(5E8 ,new Vec3(6000,100,0), new Vec3(0,2200,0), this);
		bodyC.setRadius(250);
		
		universe.addBody(referenceBody);
		universe.addBody(sunBody);
		universe.addBody(bodyC);
		
		rootGroup.getChildren().add(universe.get3dElements());
		//---------------------------------------------------------------------------
		// Scene
		//---------------------------------------------------------------------------
		sceneHeight = 700;
		sceneWidth = 1300;
	    scene = new SubScene(rootGroup, sceneHeight, sceneWidth, true, SceneAntialiasing.BALANCED);
		scene.setFill(Color.color(environmentBackgroundColor.x,
								  environmentBackgroundColor.y,
								  environmentBackgroundColor.z));
		scene.setCamera(camera);	
		
		anchorPane.getChildren().add(scene);
		
		//---------------------------------------------------------------------------
		// Mouse and Keyboard Control
		//---------------------------------------------------------------------------
		// Set camera control 
        scene.setOnMousePressed(event
        		->{
            x0 = event.getSceneX();
            y0 = event.getSceneY();
            event.consume();
        });

        scene.setOnMouseDragged(event ->{
        	
        double xDiff = (event.getSceneX() - x0) * cameraCtrlSensitivity;
        double yDiff = -(event.getSceneY() - y0) * cameraCtrlSensitivity;
        
    	cameraRotY.setAngle(cameraRotY.getAngle() + xDiff);
    	cameraRotX.setAngle(cameraRotX.getAngle() + yDiff);

        x0 = event.getSceneX();
        y0 = event.getSceneY();

        });
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    goNorth = true; break;
                    case DOWN:  goSouth = true; break;
                    case LEFT:  goWest  = true; break;
                    case RIGHT: goEast  = true; break;
				default:
					break;
                }
            }
        });

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    goNorth = false; break;
                    case DOWN:  goSouth = false; break;
                    case LEFT:  goWest  = false; break;
                    case RIGHT: goEast  = false; break;
				default:
					break;
                }
            }
        });
        
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int dx = 0, dy = 0, dz = 0;

                if (goNorth) dy    -= cameraControlIncrement;
                if (goSouth) dy    += cameraControlIncrement;
                if (goEast)  dx    += cameraControlIncrement;
                if (goWest)  dx    -= cameraControlIncrement;
                if (goForward) dz  += cameraControlIncrement;
                if (goBackward) dz -= cameraControlIncrement;
                //if (running) { dz = -dy ; dy = 0 ; }
                
                if (thirdPersonCamera) {
                	cameraRelativePosition.x += dx;
                	cameraRelativePosition.y += dy;
                	cameraRelativePosition.z += dz;
                	moveCameraBy(dx, dy, dz);
                } else {
                	cameraAbsolutePosition.x += dx;
                	cameraAbsolutePosition.y += dy;
                	cameraAbsolutePosition.z += dz;
                	moveCameraBy(dx, dy, dz);
                }
            }
        };
        timer.start();
		
       //---------------------------------------------------------------------------
       // Camera control 
       //---------------------------------------------------------------------------
		
		 cameraMinusX = new Button("X-");
		 cameraPlusX = new Button("X+");
		
		 cameraMinusY = new Button("Y-");
		 cameraPlusY = new Button("Y+");
		
		 cameraMinusZ = new Button("Z-");
		 cameraPlusZ = new Button("Z+");
		 
		 int stdWidth = 40;
		 cameraMinusX.setMinSize(stdWidth, cameraMinusX.getHeight());
		 cameraPlusX.setMinSize(stdWidth, cameraPlusX.getHeight());
		 cameraMinusY.setMinSize(stdWidth, cameraMinusY.getHeight());
		 cameraPlusY.setMinSize(stdWidth, cameraPlusY.getHeight());
		 cameraMinusZ.setMinSize(stdWidth, cameraMinusZ.getHeight());
		 cameraPlusZ.setMinSize(stdWidth, cameraPlusZ.getHeight());
		 
 		String bstyle=String.format("-fx-background-color: transparent");
 		cameraMinusX.setStyle(bstyle);
		
		VBox cameraControlVertical1 = new VBox(4);
		VBox cameraControlVertical2 = new VBox(4);
		VBox cameraControlVertical3 = new VBox(5);
		
		cameraControlGroup = new HBox(3);
		
		cameraControlVertical1.getChildren().add(new EmptyButton(stdWidth));
		cameraControlVertical1.getChildren().add(new EmptyButton(stdWidth));
		cameraControlVertical1.getChildren().add(cameraMinusX);
		cameraControlVertical1.getChildren().add(new EmptyButton(stdWidth));
		
		cameraControlVertical2.getChildren().add(cameraMinusZ);
		cameraControlVertical2.getChildren().add(cameraMinusY);
		cameraControlVertical2.getChildren().add(cameraPlusY);
		cameraControlVertical2.getChildren().add(cameraPlusZ);
		
		cameraControlVertical3.getChildren().add(new EmptyButton(stdWidth));
		cameraControlVertical3.getChildren().add(new EmptyButton(stdWidth));
		cameraControlVertical3.getChildren().add(cameraPlusX);
		cameraControlVertical3.getChildren().add(new EmptyButton(stdWidth));
		
		cameraControlGroup.getChildren().add(cameraControlVertical1);
		cameraControlGroup.getChildren().add(cameraControlVertical2);
		cameraControlGroup.getChildren().add(cameraControlVertical3);
		
		cameraControlGroup.setLayoutY(650);
		cameraControlGroup.setLayoutX(40);
		
		
		cameraMinusX.pressedProperty().addListener((observable, wasPressed, pressed) -> {
	        if (pressed) {
	        	goWest = true;
	        } else {
	        	goWest = false;
	        }
	    });
		
		cameraPlusX.pressedProperty().addListener((observable, wasPressed, pressed) -> {
	        if (pressed) {
	        	goEast = true;
	        } else {
	        	goEast = false;
	        }
	    });
		
		cameraMinusY.pressedProperty().addListener((observable, wasPressed, pressed) -> {
	        if (pressed) {
	        	goNorth = true;
	        } else {
	        	goNorth = false;
	        }
	    });
		
		cameraPlusY.pressedProperty().addListener((observable, wasPressed, pressed) -> {
	        if (pressed) {
	        	goSouth = true;
	        } else {
	        	goSouth = false;
	        }
	    });
		
		cameraMinusZ.pressedProperty().addListener((observable, wasPressed, pressed) -> {
	        if (pressed) {
	        	goForward = true;
	        } else {
	        	goForward = false;
	        }
	    });
		
		cameraPlusZ.pressedProperty().addListener((observable, wasPressed, pressed) -> {
	        if (pressed) {
	        	goBackward = true;
	        } else {
	        	goBackward = false;
	        }
	    });
		anchorPane.getChildren().add(cameraControlGroup);
		
		
		animation.start();
		animation.stop();
	}
	

	public CelestialBody getSunBody() {
		return sunBody;
	}

	public Animation getAnimation() {
		return animation;
	}

	public AnchorPane getAnchorPane() {
		return anchorPane;
	}
	
	@SuppressWarnings("unused")
	private void moveCameraBy(int dx, int dy) {

        final double cx = camera.getBoundsInLocal().getWidth()  / 2;
        final double cy = camera.getBoundsInLocal().getHeight() / 2;

        double x = cx + camera.getLayoutX() + dx;
        double y = cy + camera.getLayoutY() + dy;

        moveCameraTo(x, y);
    }
    
    private void moveCameraBy(int dx, int dy, int dz) {

         double x =  camera.getTranslateX() + dx;
         double y =  camera.getTranslateY() + dy;
         double z =  camera.getTranslateZ() + dz;

         moveCameraTo(x, y, z);
     }
    
    public void moveCameraTo(double x, double y) {
        final double cx = camera.getBoundsInLocal().getWidth()  / 2;
        final double cy = camera.getBoundsInLocal().getHeight() / 2;

        camera.relocate(x - cx, y - cy);
        //updateHUD();
    }
    
    public void moveCameraTo(double x, double y, double z) {
        camera.setTranslateX(x);
        camera.setTranslateY(y);
        camera.setTranslateZ(z);
        if (thirdPersonCamera) {
        	cameraRelativePosition.x = x - referenceBody.getPosition().x ;
        	cameraRelativePosition.y = y - referenceBody.getPosition().y ;
        	cameraRelativePosition.z = z - referenceBody.getPosition().z ;
        } else {
        	cameraAbsolutePosition.x = x;
        	cameraAbsolutePosition.y = y;
        	cameraAbsolutePosition.z = z;
        }
        //updateHUD();
    }
    
    public double getSceneHeight() {
    	return sceneHeight;
    }

    public void setSceneHeight(double sceneHeight) {
    	this.sceneHeight = sceneHeight;
        scene.setHeight(sceneHeight);
        anchorPane.setPrefHeight(sceneHeight);
    }

    public double getSceneWidth() {
    	return sceneWidth;
    }
    
    public void setSceneWidth(double sceneWidth) {
    	this.sceneWidth = sceneWidth;
        scene.setWidth(sceneWidth);
        anchorPane.setPrefWidth(sceneWidth);
    }	


    public Vec3 getCameraPosition() {
    	Vec3 cameraPosition = new Vec3();
    	cameraPosition.x = camera.getTranslateX();
    	cameraPosition.y = camera.getTranslateY();
    	cameraPosition.z = camera.getTranslateZ();
    	return cameraPosition;
    }
    
    public void setThirdPersonCamera(boolean thirdPersonCamera) {
    	this.thirdPersonCamera = thirdPersonCamera;
    }


    public boolean isThirdPersonCamera() {
    	return thirdPersonCamera;
    }
    
    public void setCameraFoV(double FoV) {
    	camera.setFieldOfView(FoV);
    }
    
    public void setCameraToAbsoluteDefaultPosition() {
    	camera.setTranslateX(DEFAULT_CAMERA_POSITION.x);
    	camera.setTranslateY(DEFAULT_CAMERA_POSITION.y);
    	camera.setTranslateZ(DEFAULT_CAMERA_POSITION.z);
    	//updateHUD();
    }

    public void setCameraToLastAbsolutePosition() {
    	camera.setTranslateX(cameraAbsolutePosition.x);
    	camera.setTranslateY(cameraAbsolutePosition.y);
    	camera.setTranslateZ(cameraAbsolutePosition.z);
    	//updateHUD();
    }

    public void setCameraToLastRelativePosition() {
    	camera.setTranslateX(referenceBody.getPosition().x + cameraRelativePosition.x);
    	camera.setTranslateY(referenceBody.getPosition().y + cameraRelativePosition.y);
    	camera.setTranslateZ(referenceBody.getPosition().z + cameraRelativePosition.z);
    	//updateHUD();
    }

    public void setCameraToRelativeDefaultPostion() {
        camera.setTranslateX(referenceBody.getPosition().x + DEFAULT_RELATIVE_CAMERA_POSITION.x);
        camera.setTranslateY(referenceBody.getPosition().y + DEFAULT_RELATIVE_CAMERA_POSITION.y);
        camera.setTranslateZ(referenceBody.getPosition().z + DEFAULT_RELATIVE_CAMERA_POSITION.z);
        //updateHUD();
    }
    
    public int getCameraControlIncrement() {
    	return cameraControlIncrement;
    }

    public void setCameraControlIncrement(int cameraControlIncrement) {
    	this.cameraControlIncrement = cameraControlIncrement;
    }
    
    public double getCamerFoV() {
    	return camera.getFieldOfView();
    }
    
    public double getCameraNearClip() {
    	return cameraNearClip;
    }
    public void setCameraNearClip(double cameraNearClip) {
    	camera.setNearClip(cameraNearClip);
    	this.cameraNearClip = cameraNearClip;
    }
    public double getCameraFarClip() {
    	return cameraFarClip;
    }
    public void setCameraFarClip(double cameraFarClip) {
    	camera.setFarClip(cameraFarClip);
    	this.cameraFarClip = cameraFarClip;
    }

    public void setEnvironmentBackgroundColor(double r, double g, double b) {
    	scene.setFill(Color.color(r,g,b));
    	environmentBackgroundColor.x = r;
    	environmentBackgroundColor.y = g;
    	environmentBackgroundColor.z = b;
    }
    
    public Vec3 getEnvironmentBackgroundColor() {
    	return environmentBackgroundColor;
    }

	public double getAnimationTime() {
		return animationTime;
	}

	public void setAnimationTime(double animationTime) {
		this.animationTime = animationTime;
	}

	@SuppressWarnings("exports")
	public Environment getUniverse() {
		return universe;
	}

	@SuppressWarnings("exports")
	public Group getRootGroup() {
		return rootGroup;
	}

	@SuppressWarnings("exports")
	public Group getTailGroup() {
		return tailGroup;
	}
	
	public void clearTails() {
		tailGroup.getChildren().clear();
	}

	public CelestialBody getReferenceBody() {
		return referenceBody;
	}
    
	
}
