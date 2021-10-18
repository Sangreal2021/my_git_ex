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
public class AttachesDAO {
	
	private Attaches getInstance(ResultSet rs) throws SQLException {
		//예외를 호출한쪽으로 던짐..-> 아래에서 처리됨
		Attaches obj = new Attaches(
				rs.getInt("id"),
				rs.getString("path"),
				rs.getInt("board_id")
				);
		return obj;
	} 
	//첨부파일은 다수개를 가져오는것이므로 ArrayList형식만...
	public ArrayList<Attaches> select(Connection conn, Attaches model) {
		ArrayList<Attaches> result = new ArrayList<>();
		String query = "select * from attaches where board_id=? order by id asc";
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
	
	public int insert(Connection conn, Attaches model) {
		int result = 0;
		String query = "insert into attaches values (null,?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, model.getPath());
			pstmt.setInt(2, model.getBoard_id());
			
			result = pstmt.executeUpdate();//1이면 성공, 0이면 실패
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCCloser.close(pstmt);
		}
		return result;
	}

	//특정회원의 정보를 삭제하는 메소드
	public int delete(Connection conn, Attaches model) {
		int result = 0;
		String query = "delete from attaches where id=?";
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
	//특정 게시글에 해당하는걸 모두 삭제
	public int deleteByBoard(Connection conn, Attaches model) {
		int result = 0;
		String query = "delete from attaches where board_id=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, model.getBoard_id());
			result = pstmt.executeUpdate();//1이면 성공, 0이면 실패
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCCloser.close(pstmt);
		}
		return result;
	}
}






















