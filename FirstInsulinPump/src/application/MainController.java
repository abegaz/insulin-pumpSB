package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ResourceBundle;
import java.net.URL;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;




public class MainController implements Initializable {
	
	String url = "jdbc:mysql://localhost:3306/demo";
	String user = "root";
	String password = "";
	Connection myConn = null;
	Statement myStmt = null;
    BloodSugar bs1 = new BloodSugar();
	
	

    @FXML
    private LineChart<String, Number> LineChart;
    //XYChart.Series<?, ?> series;
    
    @FXML   
    Label lbl;		//Mouse

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    
    
    /*
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	series = new XYChart.Series<>();    	
    	series.setName("Core 0");
    	
    	
    	Thread th = new Thread(new SugarLevelReader());
    	th.start();
    }
    
    
    class SugarLevelReader implements Runnable { //using https://www.youtube.com/watch?v=tqBlpH_3AgM&t=99s
    	@Override
    	public void run(){
    		try {
    			while (true) {
    				int ctr = 1;
    				
    				Pattern p = Pattern.compile("[+]...");
    	    		
    	    		Process proc = Runtime.getRuntime().exec("url");
    	    		BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
    	    		
    	    		String line = "";
    	    		int coreNumber = 0;
    	    		
    	    		while ((line = reader.readLine()) != null) {
    	    			Matcher m = p.matcher(line);
    	    			if(m.find())
    	    			{
    	    				System.out.println("Match Found = " + m.group().substring(1));
    	    				Double temp = Double.parseDouble(m.group().substring(1));
    	    				switch (coreNumber) {
    	    				case 0: series.getData().add(new XYChart.Data<>(ctr, temp));
    	    				break;  	    				
    	    				
    	    				}
    	    				
    	    			}
    	    		}
    	    		ctr++;
    	    		System.out.println("------------------------------");
    	    		Thread.sleep(2000); //loading the data in miliseconds
    	    	}
    		}
    			catch (Exception ex) {
    				Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
    				}
    		}
    	}
    */

    
    @SuppressWarnings("unchecked")
	@Override
    public void initialize(URL url, ResourceBundle rb) {
    	XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
    	try {
			series.getData().add(new XYChart.Data<String, Number>("5", bs1.getlastBSfromDB()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			series.getData().add(new XYChart.Data<String, Number>("8", bs1.get2ndlastBSfromDB()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			series.getData().add(new XYChart.Data<String, Number>("11", bs1.get3rdlastBSfromDB()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			series.getData().add(new XYChart.Data<String, Number>("14", bs1.get4thlastBSfromDB()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	LineChart.getData().addAll(series);
    	
    	
    	EventHandler<MouseEvent> mouseSensor = 
    	        (MouseEvent event) -> {
    	            ((Node)(event.getSource())).setCursor(Cursor.HAND);
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
    	
    
    }
}
