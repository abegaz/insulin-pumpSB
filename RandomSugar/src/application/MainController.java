package application;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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





@FXML private Label myNum;

public void generateRandom(ActionEvent event) {
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
			}},0,5000); //,first parameter is the delay before the FIRST measurement is taken (milliseconds)
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


