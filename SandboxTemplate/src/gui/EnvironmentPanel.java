package gui;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import utils.Formats;

public class EnvironmentPanel {
	
	private AnchorPane contentPanel;
	
	
	public EnvironmentPanel(WorldWindow worldView) {
		
		contentPanel = new AnchorPane();
		
		VBox content = new VBox(3);
		
		VBox checkBoxes = new VBox(3);
		VBox sliderBoxes = new VBox(3);
		
	    ToggleGroup group = new ToggleGroup();
		

		
	    
		Label environmentSizeLabel = new Label();
		
	    
	    
	   
	    
	    Label environmentBrightnessLabel = new Label();
	    Slider environmentBrightness = new Slider();
	    environmentBrightness.setMin(0.0);
	    environmentBrightness.setMax(1);
	    environmentBrightness.setValue(worldView.getEnvironmentBackgroundColor().x);
	    environmentBrightnessLabel.setText("Environment Brightness: "+Formats.decform00.format(100 * environmentBrightness.getValue())+ " [percent]");
	    environmentBrightness.setMajorTickUnit(0.1);
	    environmentBrightness.setShowTickMarks(true);
	    environmentBrightness.valueProperty().addListener(e -> {
	    	worldView.setEnvironmentBackgroundColor(environmentBrightness.getValue(),environmentBrightness.getValue(),environmentBrightness.getValue());
	    	environmentBrightnessLabel.setText("Environment Brightness: "+Formats.decform00.format(100 * environmentBrightness.getValue())+ " [percent]");
	    });	
		

		
		sliderBoxes.setLayoutY(20);
		sliderBoxes.getChildren().add(environmentSizeLabel);
		sliderBoxes.getChildren().add(environmentBrightnessLabel);
		sliderBoxes.getChildren().add(environmentBrightness);
		
		content.setLayoutY(20);
		
		content.getChildren().add(checkBoxes);
		content.getChildren().add(sliderBoxes);
		
		contentPanel.getChildren().add(content);
	}

	@SuppressWarnings("exports")
	public AnchorPane getContentPanel() {
		return contentPanel;
	}
	

}
