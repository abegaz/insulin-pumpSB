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
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {

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
					}
				});

			
			}},0,5000); //,first parameter is the delay before the FIRST measurement is taken (milliseconds)
				   //second parameter is how long before the next measurement is taken (milliseconds)
	}			   //second parameter set to 5 seconds (5000 milliseconds) for testing purposes
				   //to set the timer for 15 minutes use either 15*60*1000 OR 900000 for the second parameter

}
