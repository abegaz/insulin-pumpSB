package application;

import java.sql.SQLException;

public class Test {
 public static void main (String args[]) throws SQLException{
	BloodSugar bs1;
	
	bs1 = new BloodSugar();

	bs1.getlastBSfromDB();
	System.out.println(bs1.getlastBS());
	bs1.get2ndlastBSfromDB();
	System.out.println(bs1.get2ndlastBS());
	bs1.get3rdlastBSfromDB();
	System.out.println(bs1.get3rdlastBS());
	bs1.get4thlastBSfromDB();
	System.out.println(bs1.get4thlastBS());

	
 }
}