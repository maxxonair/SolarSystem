package gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import utils.Vec3;

public class WorldWindow extends Application {

	private PerspectiveCamera camera;
	
	private double DEFAULT_CAMERA_FOV = 40;
	// Third person camera activated:
	private boolean thirdPersonCamera = false; 
	
	private Vec3 DEFAULT_CAMERA_POSITION = new Vec3(-6125,-21075,-25050);
	private Vec3 DEFAULT_RELATIVE_CAMERA_POSITION = new Vec3(-650,-650,-1000);
	
	private int cameraControlIncrement = 25;
	private HBox cameraControlGroup;
	private Vec3 cameraRelativePosition;
	private Vec3 cameraAbsolutePosition;
	private double cameraNearClip=.001;
	private double cameraFarClip = 1000;
	
	private Group environment;
	private Group grid;
	private Group rootGroup;
	private Group referenceGroup;
	private SubScene scene;
	
	private Rotate rotateX,rotateY;
	
	private double sceneHeight;
	private double sceneWidth;
	
	private AnchorPane anchorPane;
	private Vec3 environmentBackgroundColor = new Vec3(0.15,0.15,0.15);
	
	public WorldWindow() {
		
	}
	
	@Override
	public void start(@SuppressWarnings("exports") Stage stage) throws Exception{
		//---------------------------------------------------------------------------
		// Model 
		//---------------------------------------------------------------------------
	    rootGroup = new Group();
	    anchorPane = new AnchorPane();
		//---------------------------------------------------------------------------
		// Camera
		//---------------------------------------------------------------------------
	    camera = new PerspectiveCamera();
		camera.setNearClip(cameraNearClip);
		camera.setFarClip(cameraFarClip);	
		camera.setFieldOfView(DEFAULT_CAMERA_FOV);
		
		Group cameraGroup = new Group();
		cameraGroup.getChildren().add(camera);

		camera.getTransforms().addAll(
                rotateY = new Rotate(0, Rotate.Y_AXIS),
                rotateX = new Rotate(-35, Rotate.X_AXIS)
        );
		camera.setTranslateX(DEFAULT_CAMERA_POSITION.x);
		camera.setTranslateY(DEFAULT_CAMERA_POSITION.y);
		camera.setTranslateZ(DEFAULT_CAMERA_POSITION.z);
		
	    environment = new Group();
	    referenceGroup = new Group();
		
		rootGroup.getChildren().add(environment);
		rootGroup.getChildren().add(camera);
		
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
        	cameraRelativePosition.x = x - referenceGroup.getTranslateX() ;
        	cameraRelativePosition.y = y - referenceGroup.getTranslateY() ;
        	cameraRelativePosition.z = z - referenceGroup.getTranslateZ() ;
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
    	camera.setTranslateX(referenceGroup.getTranslateX() + cameraRelativePosition.x);
    	camera.setTranslateY(referenceGroup.getTranslateY() + cameraRelativePosition.y);
    	camera.setTranslateZ(referenceGroup.getTranslateZ() + cameraRelativePosition.z);
    	//updateHUD();
    }

    public void setCameraToRelativeDefaultPostion() {
        camera.setTranslateX(referenceGroup.getTranslateX()+DEFAULT_RELATIVE_CAMERA_POSITION.x);
        camera.setTranslateY(referenceGroup.getTranslateY()+DEFAULT_RELATIVE_CAMERA_POSITION.y);
        camera.setTranslateZ(referenceGroup.getTranslateZ()+DEFAULT_RELATIVE_CAMERA_POSITION.z);
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
}
