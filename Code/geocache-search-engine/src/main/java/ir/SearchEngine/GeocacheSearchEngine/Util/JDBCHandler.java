package ir.SearchEngine.GeocacheSearchEngine.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class JDBCHandler {

	
	private static final String url = "jdbc:postgresql://localhost:5432/postgres";
	private static final String user = "postgres";
	private static final String password = "geocache";
		
	public JDBCHandler() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("PostgreSQL Driver not found");
			e.printStackTrace();
		}
	}

	public void postLog(String ip, String waypoint, String query, Timestamp stamp) {
		String sql = "INSERT INTO Logs (ip, waypoint, query, queryTime) VALUES (?,?,?,?);";
		
		try {
			Connection con = DriverManager.getConnection(url, user, password);
			PreparedStatement pStmt = con.prepareStatement(sql);
			pStmt.setString(1, ip);
			pStmt.setString(2,  waypoint);
			pStmt.setString(3, query);
			pStmt.setTimestamp(4, stamp);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}