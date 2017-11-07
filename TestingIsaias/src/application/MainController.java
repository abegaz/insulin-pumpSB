package application;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
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
	    private AnchorPane navList2;
	    @FXML
	    private Button exitButton2;
	    @FXML
	    private Button insulinReservoir;
	    @FXML
	    private Label myNum1;
	    
	

	String url = "jdbc:mysql://localhost:3306/demo";
	String user = "root";
	String password = "";
	Connection myConn = null;
	Statement myStmt = null;
	Timestamp ts = new Timestamp(System.currentTimeMillis()); 
	@FXML private Label myNum;
	public void generateRandom(ActionEvent event) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			public void run(){
				Platform.runLater(new Runnable(){ //What allows the UI to be modified
					public void run(){
						Random rand = new Random();
						int bloodSugar = rand.nextInt(151)+ 50;
						myNum.setText(Integer.toString(bloodSugar));
						
						
						//Animation for low blood sugar
				        TranslateTransition openNav=new TranslateTransition(new Duration(350), navList);
				        openNav.setToY(0);
				        TranslateTransition closeNav=new TranslateTransition(new Duration(350), navList);
				
						if(bloodSugar  <= 100) {
							navList.getTranslateY();
				            openNav.play();
				            } else {
				                closeNav.setToY(-(navList.getHeight()));
				                closeNav.play();
				            }
						// End of animation
					    // Exit button to close alert box
						exitButton.setOnAction(new EventHandler<ActionEvent>() {
						    @Override public void handle(ActionEvent e) {
						    	closeNav.setToY(-(navList.getHeight()));
				                closeNav.play();
						    }
						});
						
						
						try {	
							myConn = DriverManager.getConnection(url, user, password);
							myStmt = myConn.createStatement();
							String sql = "insert into info " + " (bloodSugar,time)"
									+ " values ('"+bloodSugar+"', '"+ts+"')";
							myStmt.executeUpdate(sql);
							System.out.println("Insert complete.");
							} catch (Exception exc) {
								exc.printStackTrace();
							} finally {
								if (myStmt != null) {
									try {
										myStmt.close();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								if (myConn != null) {
									try {
										myConn.close();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
						
					}
						// Clicking 'Consume Sugar' will add 50
						consumeSugar.setOnAction(new EventHandler<ActionEvent>() {
						    @Override public void handle(ActionEvent e) {
						    	
						    	myNum.setText(Integer.toString(bloodSugar + 50));
						    	closeNav.setToY(-(navList.getHeight()));
				                closeNav.play();
						    }
						});	
						
					}
				});

			
			}},0,20000); //,first parameter is the delay before the FIRST measurement is taken (milliseconds)
				   //second parameter is how long before the next measurement is taken (milliseconds)
				   //second parameter set to 5 seconds (5000 milliseconds) for testing purposes
				   //to set the timer for 15 minutes use either 15*60*1000 OR 900000 for the second parameter
	
	}

	
	


// Test code for testing insulin reservoir, creating alert box, rng # generator, and refill box 
	
	
 public void generateInsulin(ActionEvent event) {
	
	Random rand2 = new Random();
	int insulinLevel = rand2.nextInt(131)+ 50;
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
