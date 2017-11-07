package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BloodSugar {
	String url = "jdbc:mysql://localhost:3306/demo";
	String user = "root";
	String password = "";
	Connection myConn = null;
	Statement myStmt = null;
	private int bs; // bs= BloodSugar
 public BloodSugar(int bloodSugar){
	 bs = bloodSugar;
 }
 public int getBs(){
	 return bs;
 }

 public void uploadBs(){
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
