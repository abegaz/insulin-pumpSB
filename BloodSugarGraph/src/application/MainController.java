package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ResourceBundle;
import java.net.URL;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;




public class MainController implements Initializable {
	
	String url = "jdbc:mysql://localhost:3306/demo";
	String user = "root";
	String password = "";
	Connection myConn = null;
	Statement myStmt = null;
	
	

    @FXML
    private LineChart<Number, Number> LineChart;
    XYChart.Series<Number, Number> series;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    
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
    
    
    
  /*  @Override
    public void initialize(URL url, ResourceBundle rb) {
    	XYChart.Series series = new XYChart.Series<>();
    	series.getData().add(new XYChart.Data("5", 55));
    	series.getData().add(new XYChart.Data("5", 55));
    	
    	XYChart.Series series2 = new XYChart.Series<>();
    	series2.getData().add(new XYChart.Data("10", 88));
    	series2.getData().add(new XYChart.Data("10", 150));
    	
    	LineChart.getData().addAll(series,series2);
    }*/
}
