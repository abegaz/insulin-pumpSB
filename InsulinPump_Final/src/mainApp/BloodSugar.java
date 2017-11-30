package mainApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BloodSugar {
	String url = "jdbc:mysql://localhost:3306/insulinpump_sb";
	String user = "root";
	String password = "";
	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRs = null;
	private int insulinCounter = 0, spikeCount = 0, stablCount = 0;
	private int offset = 0, spikeOff = 0, stablOff = 0;
	private int lastBS;
	private int secondlastBS;
	private int thirdlastBS;
	private int fourthlastBS;
    private int bsValue; // bs= BloodSugar

	//Constructor
	public BloodSugar(){
		this.bsValue = this.generateBS();
	}

	//method to create new measurement after using insulin
	public int[] injectInsulin(){
		int[] retVals = this.generateBS(bsValue, true);
		this.bsValue = retVals[0];
		return retVals;
	}

//	//method to create new measurement after consuming sugar
//	public int intakeSugar(){
//		this.bsValue += 50;
//		return this.bsValue;
//	}

	//Foods that work to quickly raise blood sugar
	public int intakeSpike(){
		this.spikeCount = 2;
		this.bsValue += 15;
		this.spikeOff = 35;
		return this.bsValue;
	}

	//Foods that act over longer time but not as strongly
	public int intakeStabl(){
		this.stablCount = 4; //affects over 5 cycles
		this.bsValue += 5; //initial offset, since usually it doesn't take 30 minutes for food to affect blood sugar
		this.stablOff = 20; //can be modified depending on standard serving size
		return this.bsValue;
	}

	//generic method to get insulin levels and create new data point
	public int getBS(boolean createsNew){
		if(createsNew)
			this.bsValue = this.generateBS(this.bsValue, false)[0];
		return this.bsValue;
	}
	//this method will only be invoked for very first BS measurement
	private int generateBS(){
		return (int)(Math.random()*151) + 50;
	}

	//overloading previous method. Used for any subsequent BS measurement
	private int[] generateBS(int prevBS, boolean withInjection){
		int bs;
		int[] returnVals = new int[2];
		if(withInjection){
			int units = (prevBS - 130) / 2;
			returnVals[1] = units;
			this.insulinCounter = 4;
			this.offset += units * -4;
		}

		//variable just serves to create a probability distribution of how much next BS value will deviate
		int dist = (int)(Math.random()*99);
		//90% probability of next BS being normal fluctuation:
		//{previous BS+(x | x<=25), previous BS-(x | x<=20)}
		if(dist<25)
			bs = (int)(Math.random()*25)+prevBS+offset+stablOff+spikeOff; //increasing => person ate a snack
		else if(dist<85)
			bs = prevBS-(int)(Math.random()*25)+offset+stablOff+spikeOff; //decrease due to normal bodily function

		//10% probability of next BS being a spike:
		//{previous BS+(x | x<=35), previous BS-(x | x<=25)}
		else if(dist<96)
			bs = (int)(int)(Math.random()*70)+prevBS+offset+stablOff+spikeOff; //person ate a lot/ food with high glycemic index
		else
			bs = prevBS-(int)(Math.random()*35)+offset+stablOff+spikeOff; //person may have exercised

		//if they consumed a stabilizer within the last 5 cycles
		if(stablCount!=0){
			stablCount--;
			if(stablCount==0)
				this.stablOff=0;
			else if(stablCount>2) //initially, more sugar gets absorbed into bloodstream
				this.stablOff*=1.5;
			else //after a while, sugar left decreases and so does amount absorbed
				this.stablOff/=1.5;
		}

		if(spikeCount != 0){
			spikeCount--;
			if(spikeCount == 0)
				this.spikeOff = 0;
			this.spikeOff /= 3;
		}

		if(insulinCounter != 0){
			insulinCounter--;
			if(insulinCounter==0)
				this.offset=0;
			//offset decreases as blood insulin levels decrease
			this.offset/=2;
		}
		returnVals[0] = bs;
		return returnVals;
	}



 public void uploadBSToDB()
 {
	 try {
			myConn = DriverManager.getConnection(url, user, password);
			myStmt = myConn.createStatement();
			String sql = "insert into info " + " (bloodSugar)"
					+ " values ('"+bsValue+"')";
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

 public int getlastBSfromDB() throws SQLException{
	  try {
	   myConn = DriverManager.getConnection(url, user , password);
	   myStmt = myConn.createStatement();
	   myRs = myStmt.executeQuery("SELECT * FROM info WHERE id IN (SELECT MAX(id) FROM info)");
	   while (myRs.next()) {
	   //System.out.println(myRs.getString("bloodSugar") + "  " + myRs.getString("time"));
	   lastBS = Integer.parseInt(myRs.getString("bloodSugar"));
	   }
	  }
	  catch (Exception exc) {
	   exc.printStackTrace();
	  }
	  finally {
	   if (myRs != null) {
	    myRs.close();
	   }

	   if (myStmt != null) {
	    myStmt.close();
	   }

	   if (myConn != null) {
	    myConn.close();
	   }

	  }
	  return lastBS;
	 }


 public int getlastBS() {
	return lastBS;
 }


public int get2ndlastBSfromDB() throws SQLException{
	  try {
	   myConn = DriverManager.getConnection(url, user , password);
	   myStmt = myConn.createStatement();
	   myRs = myStmt.executeQuery("SELECT * FROM info ORDER BY id DESC LIMIT 1,1");
	   while (myRs.next()) {
	   // System.out.println(myRs.getString("bloodSugar") + "  " + myRs.getString("time"));
	    secondlastBS = Integer.parseInt(myRs.getString("bloodSugar"));
	   }
	  }
	  catch (Exception exc) {
	   exc.printStackTrace();
	  }
	  finally {
	   if (myRs != null) {
	    myRs.close();
	   }

	   if (myStmt != null) {
	    myStmt.close();
	   }

	   if (myConn != null) {
	    myConn.close();
	   }
	  }
	  return secondlastBS;
	 }


public int get2ndlastBS() {
	return secondlastBS;
 }


public int get3rdlastBSfromDB() throws SQLException{
	  try {
	   myConn = DriverManager.getConnection(url, user , password);
	   myStmt = myConn.createStatement();
	   myRs = myStmt.executeQuery("SELECT * FROM info ORDER BY id DESC LIMIT 1,2");
	   while (myRs.next()) {
	   // System.out.println(myRs.getString("bloodSugar") + "  " + myRs.getString("time"));
	    thirdlastBS = Integer.parseInt(myRs.getString("bloodSugar"));
	   }
	  }
	  catch (Exception exc) {
	   exc.printStackTrace();
	  }
	  finally {
	   if (myRs != null) {
	    myRs.close();
	   }

	   if (myStmt != null) {
	    myStmt.close();
	   }

	   if (myConn != null) {
	    myConn.close();
	   }
	  }
		return thirdlastBS;
	 }


public int get3rdlastBS() {
	return thirdlastBS;
}


public int get4thlastBSfromDB() throws SQLException{
	  try {
	   myConn = DriverManager.getConnection(url, user , password);
	   myStmt = myConn.createStatement();
	   myRs = myStmt.executeQuery("SELECT * FROM info ORDER BY id DESC LIMIT 1,3");
	   while (myRs.next()) {
	   // System.out.println(myRs.getString("bloodSugar") + "  " + myRs.getString("time"));
	    fourthlastBS = Integer.parseInt(myRs.getString("bloodSugar"));
	   }
	  }
	  catch (Exception exc) {
	   exc.printStackTrace();
	  }
	  finally {
	   if (myRs != null) {
	    myRs.close();
	   }

	   if (myStmt != null) {
	    myStmt.close();
	   }

	   if (myConn != null) {
	    myConn.close();
	   }
	  }
	  return fourthlastBS;
	 }


public int get4thlastBS() {
	return fourthlastBS;
	}
}

