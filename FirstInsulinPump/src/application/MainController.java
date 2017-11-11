package application;

import java.util.Date;

import java.util.Timer;
import java.util.TimerTask;

import java.text.SimpleDateFormat;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;



public class MainController {
    @FXML
    private Button awesome;
    @FXML
    private AnchorPane navList;
    @FXML
    private Button exitButton;
    @FXML
    private Button consumeSugar;
    @FXML
    private Label myNum1;
    @FXML
    private AnchorPane navList2;
    @FXML
    private Button exitButton2;
    @FXML
    private Button insulinReservoir;
    @FXML
    private LineChart<String, Number> LineChart;
    //XYChart.Series<?, ?> series;
    
    @FXML   
    Label lbl;		//Mouse

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    @FXML
    private BloodSugar bs1;


    @FXML private Label myNum;
    
private int index = 0;

XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();


@SuppressWarnings("unchecked")
public void generateRandom(ActionEvent event) {


	LineChart.getData().addAll(series);
	
	EventHandler<MouseEvent> mouseSensor = 
	        (MouseEvent e) -> {
	            ((Node)(e.getSource())).setCursor(Cursor.HAND);
	};
	series.getNode().setOnMouseEntered(mouseSensor);
    series.getNode().setOnMouseExited(mouseSensor);
    //series.getData().setOnMouseEntered(mouseSensor);
    //series.getData().setOnMouseExited(mouseSensor);
	
	for(final XYChart.Data<String, Number> data : series.getData()) {  //mouse event
		data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				lbl.setText("Blood Sugar Level was checked at: " + data.getXValue() + "\nBlood Sugar Level: " + String.valueOf(data.getYValue()));
				Tooltip.install(data.getNode(), new Tooltip("Blood Sugar Level was checked at: " + data.getXValue() + "\nBlood Sugar Level: " + String.valueOf(data.getYValue())));
			}
			
		});
	}
	
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			public void run(){
				Platform.runLater(new Runnable(){ //What allows the UI to be modified
					
					
					public void run(){
						
						BloodSugar bs1;
							bs1 = new BloodSugar();
							bs1.random();	
							System.out.println(bs1.getBs());
							bs1.uploadBSToDB();
							myNum.setText(Integer.toString(bs1.getBs()));
							
							TranslateTransition openNav=new TranslateTransition(new Duration(350), navList);
					        openNav.setToY(0);
					        TranslateTransition closeNav=new TranslateTransition(new Duration(350), navList);
					
							if(bs1.getBs()  <= 100) {
								navList.getTranslateY();
					            openNav.play();
					            } else {
					                closeNav.setToY(-(navList.getHeight()));
					                closeNav.play();
					            }
							
     
			
						    Date dNow = new Date( );
						    SimpleDateFormat ft = 
						    new SimpleDateFormat ("hh:mm:ss a");
							
						    //-------code for red points-------
						    
						    if(bs1.getBs() >= 130){
								series.getData().add(new LineChart.Data<String,Number>(ft.format(dNow), bs1.getBs()));
								XYChart.Data<String, Number> dataPoint = series.getData().get(index);
								Node lineSymbol = dataPoint.getNode().lookup(".chart-line-symbol");
								lineSymbol.setStyle("-fx-background-color: #ff0000, #ffffff; -fx-background-insets: 0, 2;\n" + 
							               "    -fx-background-radius: 3px;\n" +
							               "    -fx-padding: 4px;");
								
								Tooltip.install(dataPoint.getNode(), new Tooltip("Insulin injected at " + ft.format(dNow)));
								dataPoint.getNode().setOnMouseEntered(event -> dataPoint.getNode().getStyleClass().add("onHover"));
								dataPoint.getNode().setOnMouseExited(event -> dataPoint.getNode().getStyleClass().remove("onHover"));
						    }
							else if(bs1.getBs() < 130){
								series.getData().add(new LineChart.Data<String,Number>(ft.format(dNow), bs1.getBs()));
							}
						    index++;
						    //-------end code for red points-------
						    					
						    
						    
						    consumeSugar.setOnAction(new EventHandler<ActionEvent>() {
							    @Override public void handle(ActionEvent e) {
							    	
							    	myNum.setText(Integer.toString(bs1.getBs() + 50));
							    	closeNav.setToY(-(navList.getHeight()));
					                closeNav.play();
							    }
							});
							exitButton.setOnAction(new EventHandler<ActionEvent>() {
							    @Override public void handle(ActionEvent e) {
							    	closeNav.setToY(-(navList.getHeight()));
					                closeNav.play();
							    }
							});
					}
				});
			}},5000,5000); //,first parameter is the delay before the FIRST measurement is taken (milliseconds)
				   //second parameter is how long before the next measurement is taken (milliseconds)
				   //second parameter set to 5 seconds (5000 milliseconds) for testing purposes
				   //to set the timer for 15 minutes use either 15*60*1000 OR 900000 for the second parameter
		
	}

public void generateInsulin(ActionEvent event) {
	int insulinLevel = (int)(Math.random()*151) + 50;
	myNum1.setText(Integer.toString(insulinLevel));
	
	
	//Animation for low blood sugar
    TranslateTransition openNav2=new TranslateTransition(new Duration(350), navList2);
    openNav2.setToY(0);
    TranslateTransition closeNav2=new TranslateTransition(new Duration(350), navList2);
	
    if(insulinLevel  <= 100) {
		navList2.getTranslateY();
        openNav2.play();
        } else {
            closeNav2.setToY(-(navList2.getHeight()));
            closeNav2.play();
        }
	//End of animation

	exitButton2.setOnAction(new EventHandler<ActionEvent>() {
	    @Override public void handle(ActionEvent e) {
	    	closeNav2.setToY(-(navList2.getHeight()));
            closeNav2.play();
	    }
	});
	
	insulinReservoir.setOnAction(new EventHandler<ActionEvent>() {
	    @Override public void handle(ActionEvent e) {
	    	
	    	myNum1.setText(Integer.toString(insulinLevel + 50));
	    	closeNav2.setToY(-(navList2.getHeight()));
            closeNav2.play();
	    }
	});	
	
}



}
