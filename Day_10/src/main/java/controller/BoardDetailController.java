package controller;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import jdbc.util.*;
//클라이언트의 요청에 포함된 정보를 추출하여 객체화하기 위한 클래스 import
// - 모델 클래스
import model.*;
//서비스 클래스 import
import service.*;

@WebServlet("/auth/board/detail.tje")
public class BoardDetailController extends HttpServlet {
	private static final String formPage = "/WEB-INF/view/form/board/detail.jsp";
//	private static final String submitPage = "/WEB-INF/view/submit/board/insert.jsp";
	
	private BoardSelectOneService boardSelectOneService;
	private BoardUpdateReadCountService boardUpdateReadCountService;
	
	//init 치고 어시스트-> 매개변수있는 메소드 선택
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		//컨트롤러에서 사용할 서비스 클래스의 객체 생성
		this.boardSelectOneService = new BoardSelectOneService();
		this.boardUpdateReadCountService = new BoardUpdateReadCountService();
	}
	
	//회원가입 링크를 클릭해서 요청하는 경우 실행되는 doGet 메소드
	//- Get방식의 호출을 처리(링크를 클릭해서 접속 / 직접 URL을 입력해서 접속)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = JDBCConnectionCreator.getConnection();
		
		Board model = new Board();
		model.setId(Integer.parseInt(request.getParameter("id")));
		model = this.boardSelectOneService.service(conn, model);
		
		//조회수 증가
		if(this.boardUpdateReadCountService.service(conn, model)) {
			model.setReadCount(model.getReadCount()+1);
		}
		request.setAttribute("model", model);
		
		JDBCCloser.close(conn);
		
		request.getRequestDispatcher(formPage).forward(request, response);
	}
	
}











