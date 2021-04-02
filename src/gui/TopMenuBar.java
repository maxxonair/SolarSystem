package gui;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class TopMenuBar {
	
	private  WorldWindow worldView;
	
	
	public TopMenuBar(WorldWindow view) {
	    worldView = view;

	}
    
	@SuppressWarnings("exports")
	public  MenuBar create() {
	    MenuBar menuBar = new MenuBar(); 
	    
	    Menu  menu = new Menu("Menu"); 
	    Menu file = new Menu("File"); 
	    Menu settings = new Menu("Settings"); 
	    Menu project = new Menu("Project"); 
	    Menu environment = new Menu("Environment");
	    Menu model = new Menu("Model");
	    
	    // add menu to menubar 
	    menuBar.getMenus().add(menu); 
	    menuBar.getMenus().add(file); 
	    menuBar.getMenus().add(settings); 
	    menuBar.getMenus().add(project); 
	    menuBar.getMenus().add(environment);
	    menuBar.getMenus().add(model);
	    
	    addMenuContent(menu);
	    addModelContent(model) ;
	    
	    return menuBar;
	}
	
	private void addMenuContent(Menu menu) {
		MenuItem openObjFile = new MenuItem("Quit SandBox");
		openObjFile.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        Platform.exit();
		        System.exit(0);
		    }
		});
		
		menu.getItems().add(openObjFile);
	}
	
	private void addModelContent(Menu menu) {
		

	}
	

}
