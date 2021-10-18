package jdbc.dao;

//select 쿼리의 경우 다수개의 결과가 반환될 수 있으므로
//컬렉션을 사용하기 위한 import
import java.util.*;

//JDBC 처리를 수행하기 위해 필요한 클래스
import java.sql.*;

//연결 종료를 손쉽게 처리하기 위해..
import jdbc.util.*;

//특정 테이블에서 JDBC처리를 한후 저장하기 위해..
import model.*;

//member테이블을 대상으로 SQL문을 실행하여
//처리된 결과를 반환하는 클래스
//- select/insert/update/delete
public class BoardDAO {
	
	private Board getInstance(ResultSet rs) throws SQLException {
		//예외를 호출한쪽으로 던짐..-> 아래에서 처리됨
		Board obj = new Board(
				rs.getInt("id"),
				rs.getString("title"),
				rs.getString("content"),
				rs.getString("writer"),
				rs.getDate("regDate"),
				rs.getDate("lastUpdateTime"),
				rs.getInt("readCount")
				);
		return obj;
	}
	
	public int selectId(Connection conn, Board model) {
		int result = 0;
		String query = "select max(id) from board where writer=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, model.getWriter());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				//이제서야 result에 실제 객체 만들수있음
				result = rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCCloser.close(rs);
			JDBCCloser.close(pstmt);
		}
		return result;
	}
	
	public Board select(Connection conn, Board model) {
		Board result = null;
		String query = "select * from board where id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, model.getId());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				//이제서야 result에 실제 객체 만들수있음
				result = getInstance(rs);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCCloser.close(rs);
			JDBCCloser.close(pstmt);
		}
		return result;
	}
	
	public ArrayList<Board> select(Connection conn) {
		ArrayList<Board> result = new ArrayList<>();
		String query = "select * from board";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(getInstance(rs));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCCloser.close(rs);
			JDBCCloser.close(pstmt);
		}
		return result;
	}
	
	public int insert(Connection conn, Board model) {
		int result = 0;
		String query = "insert into board values (null,?,?,?,now(),null,0)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, model.getTitle());
			pstmt.setString(2, model.getContent());
			pstmt.setString(3, model.getWriter());
			
			result = pstmt.executeUpdate();//1이면 성공, 0이면 실패
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCCloser.close(pstmt);
		}
		return result;
	}
	//title,content만 수정가능
	public int update(Connection conn, Board model) {
		int result = 0;
		String query = "update board set title=?, content=?, lastUpdateTime=now() where id=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, model.getTitle());
			pstmt.setString(2, model.getContent());
			pstmt.setInt(3, model.getId());
			result = pstmt.executeUpdate();//1이면 성공, 0이면 실패
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCCloser.close(pstmt);
		}
		return result;
	}
	
	//패스워드를 수정하기 위한 모델객체를 사용하여 
	//특정회원의 패스워드를 수정하는 메소드
	public int updateReadCount(Connection conn, Board model) {
		int result = 0;
		String query = "update board set readCount=readCount+1 where id=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, model.getId());
			result = pstmt.executeUpdate();//1이면 성공, 0이면 실패
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCCloser.close(pstmt);
		}
		return result;
	}
	
	//특정회원의 정보를 삭제하는 메소드
	public int delete(Connection conn, Board model) {
		int result = 0;
		String query = "delete from board where id=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, model.getId());
			result = pstmt.executeUpdate();//1이면 성공, 0이면 실패
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCCloser.close(pstmt);
		}
		return result;
	}
}






















