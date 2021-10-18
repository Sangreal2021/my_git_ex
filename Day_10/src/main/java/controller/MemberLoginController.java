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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;

import jdbc.util.*;
//클라이언트의 요청에 포함된 정보를 추출하여 객체화하기 위한 클래스 import
// - 모델 클래스
import model.*;

@WebServlet("/member/login.tje")
public class MemberLoginController extends HttpServlet {
	private static final String formPage = "/WEB-INF/view/form/member/login.jsp";
	private static final String submitPage = "/WEB-INF/view/submit/member/login.jsp";
	
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
	
	//로그인 링크를 클릭해서 요청하는 경우 실행되는 doGet 메소드
	//- Get방식의 호출을 처리(링크를 클릭해서 접속 / 직접 URL을 입력해서 접속)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		for(int i=0; i<cookies.length; i++) {
			String name = cookies[i].getName();
			String value = cookies[i].getValue();
			
			if(name.equals("saved_id")) {
				request.setAttribute("saved_id", value);
				request.setAttribute("saved_checked", "checked");
			}
		}
		
		//로그인 페이지로 이동
		//- WEB-INF 내부에 저장된 리소스는 외부의 접근이 차단(redirect는 불가능..)
		//- WEB-INF 내부에 저장된 리소스는 포워딩을 통해서 접근
		//	(보안을 강화/예기치 못한 예외를 방지 - 정해진 프로세스(서블릿)에 따라서 실행)
		request.getRequestDispatcher(formPage).forward(request, response);
	}
	
	//로그인 페이지에서 정보를 입력한 후 로그인 버튼을 클릭해서 요청하는 경우 실행되는 doPost메소드
	//- post방식의 호출을 처리
	//- form 태그 내부의 submit버튼을 클릭해서 접속
	//- JS(JQuery) 등을 사용하여 API 기반의 POST 방식으로 접속
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트의 입력정보를 사용하여 모델 클래스의 객체를 생성
		Member model = new Member(
				request.getParameter("id"),
				request.getParameter("password"),
				null, 0, null, null);
		String strIsSaveId = request.getParameter("isSaveId");
		boolean isSaveId = false;
		if(strIsSaveId != null) {
			isSaveId = true;
		}
		
		Cookie cookie = new Cookie("saved_id", model.getId());
		if(!isSaveId) {
			//클라이언트의 쿠키를 지워라!
			cookie.setMaxAge(0);
		}
		response.addCookie(cookie);
		
		//클라이언트가 입력한 정보를 저장하고 있는 객체를 request영역에 저장
		//- 1. 회원가입에 성공시, 정보를 출력하는 용도
		//- 2. 중복된 아이디가 입력되는 경우, 재입력을 수월하게 처리하기 위한 용도
		request.setAttribute("model", model);
		request.setAttribute("isSaveId", isSaveId);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		
		try {
			conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
			
			// 로그인 확인 쿼리
			query = "select * from member where id = ? and password=?";
			pstmt = conn.prepareStatement(query);
			// 첫번째 ?자리에 model이 가지고있는 getId를 넣어줌
			pstmt.setString(1, model.getId());
			pstmt.setString(2, model.getPassword());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				//로그인 성공
				//- DB의 회원정보를 사용하여 모델 객체를 수정가능함
				model.setName(rs.getString("name"));
				model.setAge(rs.getInt("age"));
				model.setPhone(rs.getString("phone"));
				model.setRegDate(rs.getDate("regDate"));
				
//				request.setAttribute("isLogin", model.getId());
				request.getSession().setAttribute("isLogin", model.getId());
				request.getRequestDispatcher(submitPage).forward(request, response);
			}else {
				//로그인 실패
				//다시 doGet을 요청해라..
				response.sendRedirect(request.getContextPath()+"/member/login.tje?isFailed=true");
			}
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











