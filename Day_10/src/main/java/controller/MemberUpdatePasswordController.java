package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.util.*;
//클라이언트의 요청에 포함된 정보를 추출하여 객체화하기 위한 클래스 import
// - 모델 클래스
import model.*;

@WebServlet("/member/updatePassword.tje")
public class MemberUpdatePasswordController extends HttpServlet {
	private static final String formPage = "/WEB-INF/view/form/member/updatePassword.jsp";
	private static final String submitPage = "/WEB-INF/view/submit/member/updatePassword.jsp";
	
	private static String jdbcUrl;
	private static String jdbcUser;
	private static String jdbcPassword;
	
	//init 치고 어시스트-> 매개변수있는 메소드 선택
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		jdbcUrl = config.getServletContext().getInitParameter("JDBC_URL");
		jdbcUser = config.getServletContext().getInitParameter("JDBC_USER");
		jdbcPassword = config.getServletContext().getInitParameter("JDBC_PASSWORD");
	}
	
	//회원비번정보수정 링크를 클릭해서 요청하는 경우 실행되는 doGet 메소드
	//- Get방식의 호출을 처리(링크를 클릭해서 접속 / 직접 URL을 입력해서 접속)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//회원정보 수정 페이지로 이동
		//- WEB-INF 내부에 저장된 리소스는 외부의 접근이 차단(redirect는 불가능..)
		//- WEB-INF 내부에 저장된 리소스는 포워딩을 통해서 접근
		//	(보안을 강화/예기치 못한 예외를 방지 - 정해진 프로세스(서블릿)에 따라서 실행)
		request.getRequestDispatcher(formPage).forward(request, response);
	}
	
	//회원패스워드정보 페이지에서 정보를 입력한 후 회원정보 수정 버튼을 클릭해서 요청하는 경우 실행되는 doPost메소드
	//- post방식의 호출을 처리
	//- form 태그 내부의 submit버튼을 클릭해서 접속
	//- JS(JQuery) 등을 사용하여 API 기반의 POST 방식으로 접속
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트의 입력정보를 사용하여 모델 클래스의 객체를 생성
		Member model = new Member(
				//hidden 타입으로 전달된 ID정보를 request객체로부터 추출
				request.getParameter("id"),
				request.getParameter("password"),
				null,0,null,null);
		
		request.setAttribute("model", model);
		//변경할 패스워드 정보
		String newPw1 = request.getParameter("new_pw_1");
		String newPw2 = request.getParameter("new_pw_2");
		
		//새로운 패스워드가 검증 패스워드와 불일치 한다면 입력페이지로 이동
		if(!newPw1.equals(newPw2)) {
			request.setAttribute("notMatch", 1);
			doGet(request, response);
			return;
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		int result = 0;
		try {
			conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
			// 수정되기 전의 회원정보 검색
			query = "select * from member where id = ? and password=?";
			pstmt = conn.prepareStatement(query);
			// 첫번째 ?자리에 model이 가지고있는 getId를 넣어줌
			pstmt.setString(1, model.getId());
			pstmt.setString(2, model.getPassword());
			
			rs = pstmt.executeQuery();
			
			if(!rs.next()) {
				//기존 패스워드의 정보와 불일치 하는 경우
				request.setAttribute("notMatch", 2);
				doGet(request, response);
				return;
			}
				
			//새로운 패스워드 정보로 회원정보를 수정하는 과정
			JDBCCloser.close(pstmt);
			
			query = "update member set password=? where id=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newPw1);
			pstmt.setString(2, model.getId());
			
			//update 쿼리의 수행결과가 정수로 반환
			//- 수정성공 : 1, 실패 : 0
			result = pstmt.executeUpdate();
			
			//회원패스워드 수정의 성공 여부를 request영역에 저장
			request.setAttribute("updated", result==1);
			//회원패스워드 수정의 결과를 확인하기 위한 JSP 페이지로 포워딩
			request.getRequestDispatcher(submitPage).forward(request, response);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JDBCCloser.close(conn);
			JDBCCloser.close(pstmt);
			JDBCCloser.close(rs);
//			System.out.println("JDBC object closed!");
		}
	}
}











