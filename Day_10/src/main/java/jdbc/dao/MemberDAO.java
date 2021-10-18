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
public class MemberDAO {
	
	private Member getInstance(ResultSet rs) throws SQLException {
		//예외를 호출한쪽으로 던짐..-> 아래에서 처리됨
		Member obj = new Member(
				rs.getString("id"),
				rs.getString("password"),
				rs.getString("name"),
				rs.getInt("age"),
				rs.getString("phone"),
				rs.getDate("regDate")
				);
		return obj;
	} 
	
	//DAO 클래스의 메소드 선언시, Connection 객체를 전달받는 이유
	//- 트랜잭션 처리를 위해서
	//- 트랜잭션 : 다수개의 SQL을 하나의 작업으로 수행하는 개념
	//			   ALL or Nothing 
	//			   (계좌 이체시 사용..)
	
	//특정아이디의 회원정보를 검색하여
	//모델 객체를 반환하는 메소드
	public Member select(Connection conn, Member model) {
		Member result = null;
		String query = "select * from member where id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, model.getId());
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
	//모든 회원정보를 검색하여
	//모델 객체의 리스트를 반환하는 메소드
	public ArrayList<Member> select(Connection conn) {
		ArrayList<Member> result = new ArrayList<>();
		String query = "select * from member";
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
	
	//회원 정보를 저장하고 있는 모델객체를 사용하여 
	//회원 정보를 DB에 저장하는 메소드
	//1-> 저장됨, 0->문제생김
	public int insert(Connection conn, Member model) {
		int result = 0;
		String query = "insert into member values (?,?,?,?,?,now())";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, model.getId());
			pstmt.setString(2, model.getPassword());
			pstmt.setString(3, model.getName());
			pstmt.setInt(4, model.getAge());
			pstmt.setString(5, model.getPhone());
			result = pstmt.executeUpdate();//1이면 성공, 0이면 실패
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCCloser.close(pstmt);
		}
		return result;
	}
	
	//수정하기 위한 회원 정보를 저장하고 있는 모델객체를 사용하여
	//회원 정보를 수정하는 메소드
	public int update(Connection conn, Member model) {
		int result = 0;
		String query = "update member set name=?, age=?, phone=? where id=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, model.getName());
			pstmt.setInt(2, model.getAge());
			pstmt.setString(3, model.getPhone());
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
	public int update(Connection conn, Member oldModel, Member newModel) {
		int result = 0;
		String query = "update member set password=? where id=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newModel.getPassword());
			pstmt.setString(2, oldModel.getId());
			result = pstmt.executeUpdate();//1이면 성공, 0이면 실패
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCCloser.close(pstmt);
		}
		return result;
	}
	
	//특정회원의 정보를 삭제하는 메소드
	public int delete(Connection conn, Member model) {
		int result = 0;
		String query = "delete from member where id=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, model.getId());
			result = pstmt.executeUpdate();//1이면 성공, 0이면 실패
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCCloser.close(pstmt);
		}
		return result;
	}
}






















