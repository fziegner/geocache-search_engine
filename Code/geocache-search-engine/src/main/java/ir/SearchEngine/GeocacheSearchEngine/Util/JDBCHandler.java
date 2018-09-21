package ir.SearchEngine.GeocacheSearchEngine.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCHandler {

	
	private static final String url = "jdbc:postgresql://localhost:5432/postgres";
	private static final String user = "postgres";
	private static final String password = "geocache";
	private Connection con;
	
	
	public JDBCHandler() {
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void postLog(String ip, String waypoint, String query, int time) {
		String sql = "INSERT INTO Logs (ip, waypoint, query, time) VALUES (?,?,?,?);";
		
			try {
				PreparedStatement pStmt = con.prepareStatement(sql);
				pStmt.setString(1, ip);
				pStmt.setString(2,  waypoint);
				pStmt.setString(3, query);
				pStmt.setInt(4, time);
				pStmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
}
