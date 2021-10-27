package milestone1;

import java.sql.*;

import javax.swing.JOptionPane;

public class SQLite {
	
	Connection c = null;
	Statement s = null;
	ResultSet rs= null;
	PreparedStatement pst = null;
	
	void ConnectDB(){
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("JDBC:sqlite:MilestoneDB.db");
			
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null,"Error !!");
			
		}
	}
	
	void CloseDB() {
		try {
			c.close();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Error !!");
		}
	}

}
