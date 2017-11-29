package mainApp;

import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import mainApp.BloodSugar;

import java.net.URL;
import java.text.SimpleDateFormat;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;



public class Controller implements Initializable {
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
    private AnchorPane navList3;
    @FXML
    private Button exitButton2;
    @FXML
    private Button exitButton3;
    @FXML
    private Button insulinReservoir;
    @FXML
    private LineChart<String, Number> LineChart;
    @FXML
    private Button foodButton;
    @FXML
    private Image btnImage;
    //XYChart.Series<?, ?> series;
    @FXML
    private ProgressBar levels;
    
    @FXML   
    Label lbl;      //Mouse

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    @FXML
    private BloodSugar bs1 = new BloodSugar();


    @FXML private Label myNum;

    private int index = 0;
    private double insulinLevel = 100;
    private int unitsInj = 0;
    boolean insulinFlag = false; //set to true when bs levels are too high
XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();


	@FXML // Stabilizer dropdown
	private ComboBox<String> combobox;
    
	@FXML // Spike dropdown
	private ComboBox<String> combobox1;
	
	@FXML
	private Label label;

	@FXML 
	private Button buttonAction;
	@FXML
	private Button buttonAction1; 
	//List of stabilizers and spikes
	ObservableList<String> Stabilizer = FXCollections.observableArrayList(
			"","Cake","Cookie","Donut","Burger"
			);
	ObservableList<String> Spike = FXCollections.observableArrayList(
			"","Test1","Test2","Test3","Test4"
			);
	@Override //connects the list to the combobox(dropdown)
	public void initialize(URL url, ResourceBundle rb) {
	combobox.setItems(Stabilizer);
	combobox1.setItems(Spike);
}

  



@SuppressWarnings("unchecked")
public void generateRandom(ActionEvent event) {
    
    LineChart.getData().addAll(series);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            public void run(){
                Platform.runLater(new Runnable(){ //What allows the UI to be modified
                    
                    
                    public void run(){
                        
                            if(insulinFlag){
                                bs1.injectInsulin();
                                insulinFlag=false;
                            }
                            else
                                bs1.getBS(true);
                            	bs1.uploadBSToDB();
                            	myNum.setText(Integer.toString(bs1.getBS(false)));
                            
                            
                            // This is the transition for the low sugar alert
                            TranslateTransition openNav=new TranslateTransition(new Duration(350), navList);
                            openNav.setToY(0);
                            TranslateTransition closeNav=new TranslateTransition(new Duration(350), navList);
                            
                            // This is the transition for the insulin alert
                            TranslateTransition openNav2=new TranslateTransition(new Duration(350), navList2);
                            openNav2.setToY(0);
                            TranslateTransition closeNav2=new TranslateTransition(new Duration(350), navList2);
                            
                            
                            // This is the transition for the food menu
                            TranslateTransition openNav3=new TranslateTransition(new Duration(350), navList3);
                            openNav3.setToY(0);
                            TranslateTransition closeNav3=new TranslateTransition(new Duration(350), navList3);
                                
                            levels.setProgress(insulinLevel/100.0);
                            if(bs1.getBS(false)  <= 70) {
                                navList.getTranslateY();
                                openNav.play();
                                } else {
                                    closeNav.setToY(-(navList.getHeight()));
                                    closeNav.play();
                                }
                            
                            
                            Date dNow = new Date( );
                            SimpleDateFormat ft = 
                            new SimpleDateFormat ("hh:mm:ss a");
                            //series.getData().add(new LineChart.Data<String,Number>(ft.format(dNow), bs1.getBs()));
 
                            //-------code for hover and bs display-------//
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
                            //-------end code for hover and bs display-------//
                            
                            //-------code for red points-------//
                            myNum1.setText(Double.toString(insulinLevel));
                            if(bs1.getBS(false) >= 130 && bs1.getBS(false)<180){
                                series.getData().add(new LineChart.Data<String,Number>(ft.format(dNow), bs1.getBS(false)));
                                XYChart.Data<String, Number> dataPoint = series.getData().get(index);
                                Node lineSymbol = dataPoint.getNode().lookup(".chart-line-symbol");
                                lineSymbol.setStyle("-fx-background-color: #ff0000, #ffffff; -fx-background-insets: 0, 2;\n" + 
                                           "    -fx-background-radius: 3px;\n" +
                                           "    -fx-padding: 4px;");
                                insulinFlag = true;
                                unitsInj = 10;
                                insulinLevel = insulinLevel - unitsInj;
                                myNum.setText(Integer.toString(bs1.getBS(false)));
                                myNum1.setText((Double.toString(insulinLevel))+ " Units");
                                Tooltip.install(dataPoint.getNode(), new Tooltip(unitsInj + " Units of insulin injected at " + ft.format(dNow)));
                                dataPoint.getNode().setOnMouseEntered(event -> dataPoint.getNode().getStyleClass().add("onHover"));
                                dataPoint.getNode().setOnMouseExited(event -> dataPoint.getNode().getStyleClass().remove("onHover"));
                                
                                
                                
                            }
                            else if(bs1.getBS(false) >= 180){
                                series.getData().add(new LineChart.Data<String,Number>(ft.format(dNow), bs1.getBS(false)));
                                XYChart.Data<String, Number> dataPoint = series.getData().get(index);
                                Node lineSymbol = dataPoint.getNode().lookup(".chart-line-symbol");
                                lineSymbol.setStyle("-fx-background-color: #ff0000, #ffffff; -fx-background-insets: 0, 2;\n" + 
                                           "    -fx-background-radius: 3px;\n" +
                                           "    -fx-padding: 4px;");
            
                                unitsInj = 20;
                                insulinLevel = insulinLevel - unitsInj;
                                myNum.setText(Integer.toString(bs1.injectInsulin()));
                                insulinFlag = true;
                                myNum1.setText((Double.toString(insulinLevel))+ " Units");
                                Tooltip.install(dataPoint.getNode(), new Tooltip(unitsInj + " Units of insulin injected at " + ft.format(dNow)));
                                dataPoint.getNode().setOnMouseEntered(event -> dataPoint.getNode().getStyleClass().add("onHover"));
                                dataPoint.getNode().setOnMouseExited(event -> dataPoint.getNode().getStyleClass().remove("onHover"));
                            }
                            
                            else{
                                series.getData().add(new LineChart.Data<String,Number>(ft.format(dNow), bs1.getBS(false)));
                            }
                            index++;
                            //-------end code for red points-------//
                            myNum1.setText((Double.toString(insulinLevel))+ " Units");
                            if(insulinLevel  <= 60) {
                                navList2.getTranslateY();
                                openNav2.play();
                                } else {
                                    closeNav2.setToY(-(navList2.getHeight()));
                                    closeNav2.play();
                                }   
                            
                            insulinReservoir.setOnAction(new EventHandler<ActionEvent>() {
                                @Override public void handle(ActionEvent e) {
                                    insulinLevel=100;
                                    myNum1.setText((Double.toString(insulinLevel))+ " Units");
                                    closeNav2.setToY(-(navList2.getHeight()));
                                    closeNav2.play();
                                    levels.setProgress(insulinLevel/100.0);
                                }
                            });
                            buttonAction.setOnAction(new EventHandler<ActionEvent>() {
                                @Override public void handle(ActionEvent e) {
                                	label.setText(combobox.getValue());
                                	
                                	// when donut is chosen it's equivalent to choosing consumeSugar button
                                    // can use an algorithm for the statement
                                    if(combobox.getValue() == "Donut") {
                                    	myNum.setText(Integer.toString(bs1.intakeSugar()));
                                        closeNav.setToY(-(navList.getHeight()));
                                        closeNav.play();
                                    }
                                    else if(combobox.getValue() == "Cake") {
                                    	myNum.setText(Integer.toString(bs1.intakeSugar()));
                                        closeNav.setToY(-(navList.getHeight()));
                                        closeNav.play();
                                    }
                                    else if(combobox.getValue() == "Cookie") {
                                    	myNum.setText(Integer.toString(bs1.intakeSugar()));
                                        closeNav.setToY(-(navList.getHeight()));
                                        closeNav.play();
                                    }
                                    else if(combobox.getValue() == "Burger") {
                                    	myNum.setText(Integer.toString(bs1.intakeStabl()));
                                        closeNav.setToY(-(navList.getHeight()));
                                        closeNav.play();
                                    } else {
                                    	closeNav.setToY(-(navList.getHeight()));
                                        closeNav.play();
                                    }
                                }
                            });
                            
                            
                            foodButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override public void handle(ActionEvent e) {
                                    navList3.getTranslateY();
                                    openNav3.play();
                                }
                            });
                                                
                           /* consumeSugar.setOnAction(new EventHandler<ActionEvent>() {
                                @Override public void handle(ActionEvent e) {
                                    
                                    myNum.setText(Integer.toString(bs1.intakeSugar()));
                                    closeNav.setToY(-(navList.getHeight()));
                                    closeNav.play();
                                }
                            }); */
                            exitButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override public void handle(ActionEvent e) {
                                    closeNav.setToY(-(navList.getHeight()));
                                    closeNav.play();
                                }
                            });
                            
                                
                             //End of animation
                            exitButton2.setOnAction(new EventHandler<ActionEvent>() {
                                 @Override public void handle(ActionEvent e) {
                                    closeNav2.setToY(-(navList2.getHeight()));
                                    closeNav2.play();
                                }
                            });
                            exitButton3.setOnAction(new EventHandler<ActionEvent>() {
                                 @Override public void handle(ActionEvent e) {
                                    closeNav3.setToY(-(navList3.getHeight()));
                                    closeNav3.play();
                                }
                            });
                            
                            // updates the progressbar(insulin level bar)
                            levels.setProgress(insulinLevel/100.0);
                            
                            
                            
                            
                            // resets the combobox so that next time it appears it says "Select One"
                            // instead of the last item chosen
                            combobox.setValue(null);    
                            combobox1.setValue(null);
 }
                    
                });
            }},1000,15000); //,first parameter is the delay before the FIRST measurement is taken (milliseconds)
                   //second parameter is how long before the next measurement is taken (milliseconds)
                   //second parameter set to 5 seconds (5000 milliseconds) for testing purposes
                   //to set the timer for 15 minutes use either 15*60*1000 OR 900000 for the second parameter
}

}



