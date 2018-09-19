package ir.SearchEngine.GeocacheSearchEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCHandler {

	
	private static final String url = "jdbc:postgresql://localhost:5432/geocache-search";
	private static final String user = "postgres";
	private static final String password = "geocache";
	private Connection con;
	
	
	public JDBCHandler() {
		try {
			Connection con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con = null;
		}
	}
	
	public void postLog(String ip, String waypoint, String query, int time) {
		String sql = "INSERT INTO Logs (ip, waypoint, query, time) VALUES (?,?,?,?);";
		if(con != null) {
			try {
				PreparedStatement pStmt = con.prepareStatement(sql);
				pStmt.setString(1, ip);
				pStmt.setString(2,  waypoint);
				pStmt.setString(3, query);
				pStmt.setInt(4, time);
				pStmt.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.err.println("Connection object is null");
		}
		
	}
}
