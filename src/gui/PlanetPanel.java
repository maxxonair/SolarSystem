package gui;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import utils.Formats;

public class PlanetPanel {

	private AnchorPane contentPanel;
	
	public PlanetPanel(WorldWindow worldView) {
		contentPanel = new AnchorPane();
		
		VBox content = new VBox(3);
		
		VBox sliderBoxes = new VBox(3);
		
	    Label planetMassLabel = new Label();
	    Slider planetMass = new Slider();
	    planetMass.setMin(1.0);
	    planetMass.setMax(5E6);
	    planetMass.setValue(worldView.getReferenceBody().getMass());
	    planetMassLabel.setText("Planet Mass: "+Formats.decform00.format(planetMass.getValue())+ " [kg]");
	    planetMass.setMajorTickUnit(1000);
	    planetMass.setShowTickMarks(false);
	    planetMass.valueProperty().addListener(e -> {
	    	worldView.getReferenceBody().setMass(planetMass.getValue());
	    	planetMassLabel.setText("Planet Mass: "+Formats.decform00.format(planetMass.getValue())+ " [kg]");
	    	worldView.getAnimation().propagator();
	    });	
	    
	    
	    Label sunMassLabel = new Label();
	    Slider sunMass = new Slider();
	    sunMass.setMin(5E9);
	    sunMass.setMax(5E11);
	    sunMass.setValue(worldView.getSunBody().getMass());
	    sunMassLabel.setText("Sun Mass: "+Formats.decform00.format(sunMass.getValue())+ " [kg]");
	    sunMass.setMajorTickUnit(1000000);
	    sunMass.setShowTickMarks(false);
	    sunMass.valueProperty().addListener(e -> {
	    	worldView.getSunBody().setMass(sunMass.getValue());
	    	sunMassLabel.setText("Sun Mass: "+Formats.decform00.format(sunMass.getValue())+ " [kg]");
	    	worldView.getAnimation().propagator();
	    });	
	    
	    Label planetVelYlabel = new Label();
	    Slider planetVelY = new Slider();
	    planetVelY.setMin(0.0);
	    planetVelY.setMax(3000);
	    planetVelY.setValue(worldView.getReferenceBody().getInitialVelocity().y);
	    planetVelYlabel.setText("Planet init vel Y: "+Formats.decform00.format(planetVelY.getValue())+ " [kg]");
	    planetVelY.setMajorTickUnit(50);
	    planetVelY.setShowTickMarks(false);
	    planetVelY.valueProperty().addListener(e -> {
	    	worldView.getReferenceBody().getInitialVelocity().setY(planetVelY.getValue());
	    	planetVelYlabel.setText("Planet init vel Y: "+Formats.decform00.format(planetVelY.getValue())+ " [kg]");
	    	worldView.getAnimation().propagator();
	    });	
		    
		sliderBoxes.setLayoutY(20);
		sliderBoxes.getChildren().add(sunMassLabel);
		sliderBoxes.getChildren().add(sunMass);
		sliderBoxes.getChildren().add(planetMassLabel);
		sliderBoxes.getChildren().add(planetMass);
		sliderBoxes.getChildren().add(planetVelYlabel);
		sliderBoxes.getChildren().add(planetVelY);
		
		content.setLayoutY(20);
		
		content.getChildren().add(sliderBoxes);
		
		contentPanel.getChildren().add(content);
	}
		
	@SuppressWarnings("exports")
	public AnchorPane getContentPanel() {
		return contentPanel;
	}
}
