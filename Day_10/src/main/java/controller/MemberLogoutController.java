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
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Response;

import jdbc.util.*;
//클라이언트의 요청에 포함된 정보를 추출하여 객체화하기 위한 클래스 import
// - 모델 클래스
import model.*;

@WebServlet("/member/logout.tje")
public class MemberLogoutController extends HttpServlet {
	private static final String formPage = "/WEB-INF/view/form/member/logout.jsp";
	
	
	//로그아웃 링크를 클릭해서 요청하는 경우 실행되는 doGet 메소드
	//- Get방식의 호출을 처리(링크를 클릭해서 접속 / 직접 URL을 입력해서 접속)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null || (session != null && session.getAttribute("isLogin")==null)) {
			response.sendRedirect(request.getContextPath());
			//밑으로 못내려가게..명시적으로 return
			return;
		}
		
		//세션내부에 로그인에 관계된 정보만을 보관하고 있는 경우
		//세션 자체를 삭제해서 로그인 기록을 제거
//		request.getSession().invalidate();
		
		//세션에 저장된 로그인에 관계된 속성만을 삭제
		request.getSession().removeAttribute("isLogin");
		
		//로그아웃 페이지로 이동
		//- WEB-INF 내부에 저장된 리소스는 외부의 접근이 차단(redirect는 불가능..)
		//- WEB-INF 내부에 저장된 리소스는 포워딩을 통해서 접근
		//	(보안을 강화/예기치 못한 예외를 방지 - 정해진 프로세스(서블릿)에 따라서 실행)
		request.getRequestDispatcher(formPage).forward(request, response);
	}
}











