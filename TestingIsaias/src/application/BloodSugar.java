package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class BloodSugar {
	String url = "jdbc:mysql://localhost:3306/demo";
	String user = "root";
	String password = "";
	Connection myConn = null;
	Statement myStmt = null;

  
    private int bs; // bs= BloodSugar
  
  public BloodSugar() {
	  
	  random();
	  
  }


  public void random() {
	bs = (int)(Math.random()*151) + 50;
	
  }

  public int getBs() {
	return bs;
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
  

 
}
