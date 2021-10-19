package jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionCreator {
	/*
	private static final String jdbc_url = "jdbc:mysql://localhost:3306/board?serverTimezone=Asia/Seoul";
	private static final String jdbc_user = "root";
	private static final String jdbc_password = "1234";
	*/
	public static Connection getConnection() {
		Connection conn = null;
		try {
			//jdbc:apache:commons:dbcp:를 JDBCDriverLoader에서 따오고..board만 추가
			//board 커넥션 풀에 저장된 커넥션 객체를 반환하는 코드
			//- 풀에 커넥션 객체가 여유가 있는 경우 그중 하나를 반환하고
			//- 만약 풀에 커넥션 객체가 존재하지 않는 경우 새로운 커넥션객체를 생성
			//- 커넥션 풀의 설정을 상황에 따라 적절하게 설정해야함!!
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:board");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
