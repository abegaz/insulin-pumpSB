package mainApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Model {
	String db_url = "jdbc:mysql://localhost:3306/demo";
	String user = "root";
	String password = "";
	Connection dbConn = null;
	Statement stmnt = null;
	BloodSugar userBS;
	
	public Model(){
		userBS = new BloodSugar();
	}
	

	//creates and uploads new datapoint for bloodsugar measurement
	public void newEntry(boolean withInsulin, boolean withSugar){
		try {
			int bs = 0;
			if(withInsulin)
				bs = userBS.injectInsulin();
			else if(withSugar)
				bs = userBS.intakeSugar();
			else
				bs = userBS.getBS(true);
			dbConn = DriverManager.getConnection(db_url, user, password);
			stmnt = dbConn.createStatement();
			String sql = "insert into info " + " (bloodSugar)"
							+ " values ('"+bs+"')";
			stmnt.executeUpdate(sql);
			System.out.println("Insert complete.");
		} 
		catch (Exception exc) {
			exc.printStackTrace();
		} 
		finally {
			if (stmnt != null) {
				try {
					stmnt.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (dbConn != null) {
				try {
					dbConn.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		 
	}

    private class BloodSugar{
    	private int bsValue=0;
    	private int insulinCounter = 0; //used for insulin injection. an injection will affect BS levels for 4 measurements
		private int offset = 0; //if insulin gets injected, all values will be offset by -16 initially
    	private String timeTaken;//potentially for future use
    	private boolean critical;//potentially for future use
    	
    	//Constructor
    	public BloodSugar(){
    		this.bsValue = this.generateBS();
    	}
    	
    	//method to create new measurement after using insulin
    	public int injectInsulin(){
    		this.bsValue = this.generateBS(bsValue, true);
    		return this.bsValue;
    	}
    	
    	//method to create new measurement after consuming sugar
    	public int intakeSugar(){
    		this.bsValue += 50;
    		return this.bsValue;
    	}
    	
    	//generic method to get insulin levels and create new data point
    	public int getBS(boolean createsNew){
    		if(createsNew)
    			this.bsValue = this.generateBS(this.bsValue, false);
    		return this.bsValue;
    	}
    	//this method will only be invoked for very first BS measurement
    	private int generateBS(){
    		return (int)(Math.random()*151) + 50;
    	}
    	
    	//overloading previous method. Used for any subsequent BS measurement
    	private int generateBS(int prevBS, boolean withInjection){
    		int bs;
    		if(withInjection){
    			this.insulinCounter = 4;
    			this.offset = -16;
    		}
    		//variable just serves to create a probability distribution of how much next BS value will deviate
    		int dist = (int)(Math.random()*99);
    		
    		//90% probability of next BS being normal fluctuation: 
    		//{previous BS+(x | x<=15), previous BS-(x | x<=10)}
    		if(dist<45)
    			bs = (int)(Math.random()*15)+prevBS+offset; //increasing => person ate something
    		else if(dist<90)
    			bs = prevBS-(int)(Math.random()*10)+offset; //decrease due to normal bodily function
    		
    		//10% probability of next BS being a spike: 
    		//{previous BS+(x | x<=30), previous BS-(x | x<=20)}
    		else if(dist<95)
    			bs = (int)(int)(Math.random()*30)+prevBS+offset; //person ate a lot/ food with high glycemic index
    		else
    			bs = prevBS-(int)(Math.random()*20)+offset; //person may have exercised 
    		
    		if(insulinCounter != 0){
    			insulinCounter--;
    			if(insulinCounter==0)
    				this.offset=0;
    			//offset decreases as blood insulin levels decrease
    			this.offset/=2;    	
    		}
    		return bs;
    	}	
    }
    
}
