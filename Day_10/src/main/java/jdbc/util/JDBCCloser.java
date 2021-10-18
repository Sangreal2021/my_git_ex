package jdbc.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCCloser {
	
	public static void close(Connection obj) {
		if(obj!=null) {
			try {
				obj.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(Statement obj) {
		if(obj!=null) {
			try {
				obj.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(PreparedStatement obj) {
		if(obj!=null) {
			try {
				obj.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(ResultSet obj) {
		if(obj!=null) {
			try {
				obj.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
