package controller;

import java.io.IOException;
import java.sql.*;
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
//서비스 클래스 import
import service.*;

@WebServlet("/member/insert.tje")
public class MemberInsertController extends HttpServlet {
	private static final String formPage = "/WEB-INF/view/form/member/insert.jsp";
	private static final String submitPage = "/WEB-INF/view/submit/member/insert.jsp";
	
	private MemberSelectOneService selectOneService;
	private MemberInsertService insertService;
	
	private static String jdbcUrl;
	private static String jdbcUser;
	private static String jdbcPassword;
	
	//init 치고 어시스트-> 매개변수있는 메소드 선택
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		//컨트롤러에서 사용할 서비스 클래스의 객체 생성
		this.selectOneService = new MemberSelectOneService();
		this.insertService = new MemberInsertService();
		
		jdbcUrl = config.getServletContext().getInitParameter("JDBC_URL");
		jdbcUser = config.getServletContext().getInitParameter("JDBC_USER");
		jdbcPassword = config.getServletContext().getInitParameter("JDBC_PASSWORD");
	}
	
	//회원가입 링크를 클릭해서 요청하는 경우 실행되는 doGet 메소드
	//- Get방식의 호출을 처리(링크를 클릭해서 접속 / 직접 URL을 입력해서 접속)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//회원가입 페이지로 이동
		//- WEB-INF 내부에 저장된 리소스는 외부의 접근이 차단(redirect는 불가능..)
		//- WEB-INF 내부에 저장된 리소스는 포워딩을 통해서 접근
		//	(보안을 강화/예기치 못한 예외를 방지 - 정해진 프로세스(서블릿)에 따라서 실행)
		request.getRequestDispatcher(formPage).forward(request, response);
	}
	
	//회원가입 페이지에서 정보를 입력한 후 회원가입 버튼을 클릭해서 요청하는 경우 실행되는 doPost메소드
	//- post방식의 호출을 처리
	//- form 태그 내부의 submit버튼을 클릭해서 접속
	//- JS(JQuery) 등을 사용하여 API 기반의 POST 방식으로 접속
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트의 입력정보를 사용하여 모델 클래스의 객체를 생성
		Member model = new Member(
				request.getParameter("id"),
				request.getParameter("password"),
				request.getParameter("name"),
				Integer.parseInt(request.getParameter("age")),
				request.getParameter("phone"),
				null);
		
		//클라이언트가 입력한 정보를 저장하고 있는 객체를 request영역에 저장
		//- 1. 회원가입에 성공시, 정보를 출력하는 용도
		//- 2. 중복된 아이디가 입력되는 경우, 재입력을 수월하게 처리하기 위한 용도
		request.setAttribute("model", model);
		
		//JDBC 커넥션 객체를 유틸 클래스를 정의하여 예외처리 없이 생성!
		Connection conn = JDBCConnectionCreator.getConnection();
		
		// JDBC 트랜잭션 처리
		// - 기본적으로 커밋 작업을 자동으로 처리됨
		// - 아래와 같이 setAutoCommit 메소드의 매개변수를 false로
		//   지정하는 경우 반드시 커넥션 객체를 종료하기 전에
		//   commit() -> 현재까지 수행한 모든 작업을 실제 데이터베이스에 반영
		//   rollback()  -> 현재까지 수행한 모든 작업을 실제 데이터베이스에 반영하지 않음
		//   메소드를 호출해야함
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//회원가입에 사용할 ID의 회원이 기존에 존재하는지 확인
		Member checkMember = this.selectOneService.service(conn, model);
		if(checkMember != null) {
			//동일한 ID로 가입된 회원이 존재하는 경우
			request.setAttribute("duplicated", true);
			request.getRequestDispatcher(formPage).forward(request, response);
			return;
		}
		
		//회원가입의 성공 여부를 request 영역에 저장
		request.setAttribute("inserted", this.insertService.service(conn, model));
		//회원가입의 결과를 확인하기 위한 JSP 페이지로 포워딩
		request.getRequestDispatcher(submitPage).forward(request, response);
		
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//형재까지 수행한 모든 작업을 실제 DB에 반영X
//		conn.rollback();
		JDBCCloser.close(conn);
	}
}











