package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BloodSugar {
	String url = "jdbc:mysql://localhost:3306/demo";
	String user = "root";
	String password = "";
	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRs = null;
	private int lastBS;
	private int secondlastBS;
	private int thirdlastBS;
	private int fourthlastBS;
    private static int bs; // bs= BloodSugar
  
  public BloodSugar() {
	  
	  random();
	  
  }


  public void random() {
	bs = (int)(Math.random()*151) + 50;
	
  }

  public int getBs() {
	return bs;
  }
  
  public static void setBs(int n){
	  bs = n;
  }
  
  

 public void uploadBSToDB()
 {
	 try {
			myConn = DriverManager.getConnection(url, user, password);
			myStmt = myConn.createStatement();
			String sql = "insert into info " + " (bloodSugar)"
					+ " values ('"+bs+"')";
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
	   // System.out.println(myRs.getString("bloodSugar") + "  " + myRs.getString("time"));
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
