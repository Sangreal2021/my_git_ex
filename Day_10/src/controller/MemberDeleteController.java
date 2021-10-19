package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;

import jdbc.util.*;
import service.*;
import model.*;

@WebServlet("/member/delete.tje")
public class MemberDeleteController extends HttpServlet {
	private static final String formPage = "/WEB-INF/view/form/member/delete.jsp";
	private static final String submitPage = "/WEB-INF/view/submit/member/delete.jsp";
	
	private MemberDeleteService deleteService = new MemberDeleteService();
	
	// /auth/member/delete URL에 대한 GET 방식의 요청을 처리하는 메소드
	// - 링크를 클릭하여 요청을 수행하는 경우에 처리되는 메소드
	// - main.jsp 페이지에서 회원가입 링크를 클릭하는 경우 실행
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// GET 방식의 요청일 경우 컨트롤러(서블릿)는 어떠한 처리없이 
		// 외부에서는 접근이 차단된 WEB-INF 내부의 JSP 파일로 이동
		request.getRequestDispatcher(formPage).forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 회원탈퇴에 필요한 정보를 추출
		Member model = new Member();
		model.setId(request.getParameter("id"));
		model.setPassword(request.getParameter("password"));
		
		Connection conn = JDBCConnectionCreator.getConnection();
		
		model = this.deleteService.service(conn, model);
		if(model != null) {
			//회원정보 삭제를 처리
			//1. 세션을 제거
			HttpSession session = request.getSession();
			session.invalidate();
			//2. 탈퇴 회원의 이름정보를 모델에 저장(출력시 사용)
			model.setName(model.getName());
		}
		JDBCCloser.close(conn);
		
		request.setAttribute("result", model != null);
		request.setAttribute("model", model);
		request.getRequestDispatcher(submitPage).forward(request, response);
	}

}








