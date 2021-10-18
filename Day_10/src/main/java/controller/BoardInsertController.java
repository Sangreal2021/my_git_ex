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

@WebServlet("/auth/board/insert.tje")
@MultipartConfig(
		location = "C:\\attaches",
		fileSizeThreshold = 1024*1024,
		maxFileSize = 1024*1024*50, //50메가
		maxRequestSize = 1024*1024*50*5 //50매가 5개까지
)
public class BoardInsertController extends HttpServlet {
	private static final String formPage = "/WEB-INF/view/form/board/insert.jsp";
	private static final String submitPage = "/WEB-INF/view/submit/board/insert.jsp";
	
	private BoardInsertService boardInsertService;
	private AttachesInsertService attachesInsertService;
	private BoardSelectIdService boardSelectIdService;
	
	//init 치고 어시스트-> 매개변수있는 메소드 선택
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		//컨트롤러에서 사용할 서비스 클래스의 객체 생성
		this.boardInsertService = new BoardInsertService();
		this.attachesInsertService = new AttachesInsertService();
		this.boardSelectIdService = new BoardSelectIdService();
	}
	
	//회원가입 링크를 클릭해서 요청하는 경우 실행되는 doGet 메소드
	//- Get방식의 호출을 처리(링크를 클릭해서 접속 / 직접 URL을 입력해서 접속)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(formPage).forward(request, response);
	}
	
	//회원가입 페이지에서 정보를 입력한 후 회원가입 버튼을 클릭해서 요청하는 경우 실행되는 doPost메소드
	//- post방식의 호출을 처리
	//- form 태그 내부의 submit버튼을 클릭해서 접속
	//- JS(JQuery) 등을 사용하여 API 기반의 POST 방식으로 접속
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트의 입력정보를 사용하여 모델 클래스의 객체를 생성
		Board model = new Board(
				0,
				request.getParameter("title"),
				request.getParameter("content"),
				request.getParameter("writer"),
				null,
				null,
				0);
		
		ArrayList<Attaches> files = new ArrayList<>(); 
		
		request.setAttribute("model", model);
		request.setAttribute("files", files);
		//업로드된 파일을 저장하는 코드
		List<Part> parts = (List<Part>) request.getParts();		
		UploadUtil uploadUtil = UploadUtil.create(getServletContext());
		for(Part part : parts) {
//			System.out.printf("파라미터 명 : %s, contentType : %s, size : %d bytes \n", 
//				part.getName(), part.getContentType(), part.getSize());

			// attaches로 들어온 Part가 아니면 스킵
			if(!part.getName().equals("attaches")) continue; 
			//업로드 된 파일 이름이 없으면 스킵
			if(part.getSubmittedFileName().equals("")) continue; 
			
			String path = uploadUtil.createFilePath();
			
			Attaches a = new Attaches();
			a.setPath(path + "/" + part.getSubmittedFileName());
			files.add(a);	
			
//			System.out.println(a.getPath());
				
			uploadUtil.saveFiles(part, path); 
		}
		
		//서비스 클래스를 사용하여 DB에 연동
		Connection conn = JDBCConnectionCreator.getConnection();
		//게시글,첨부파일 둘중 하나라도 실패하면 commit안하겠음
		try {
			conn.setAutoCommit(false);
			
			//게시글 입력
			if(!this.boardInsertService.service(conn, model)) {
				throw new Exception("게시글 입력에서 문제가 발생!!");
			}
			int lastId = this.boardSelectIdService.service(conn, model);
			
			//첨부파일 입력
			for(Attaches a : files) {
				a.setBoard_id(lastId);
				if(!this.attachesInsertService.service(conn, a)) {
					throw new Exception("첨부파일 입력에서 문제가 발생!!");
				}
			}
			request.setAttribute("inserted", true);
			conn.commit();
		} catch (Exception e) {
			try {
				//문제 발생시 rollback
				System.out.println(e.getMessage());
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			JDBCCloser.close(conn);
		}
		//게시글 입력결과 확인 페이지로 이동
		request.getRequestDispatcher(submitPage).forward(request, response);
		
		/*
		//클라이언트가 입력한 정보를 저장하고 있는 객체를 request영역에 저장
		//- 1. 회원가입에 성공시, 정보를 출력하는 용도
		//- 2. 중복된 아이디가 입력되는 경우, 재입력을 수월하게 처리하기 위한 용도
		request.setAttribute("model", model);
		
		//JDBC 커넥션 객체를 유틸 클래스를 정의하여 예외처리 없이 생성!
		Connection conn = JDBCConnectionCreator.getConnection();
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
		
		//JDBC 트랜잭션 처리
		//- 기본적으로 커밋 작업을 자동을오 처리됨
		//- 아래와 같이 setAutoCommit 메소드의 매개변수를 false로
		//	지정하는 경우 반드시 커넥션 객체를 종료하기 전에
		//	commit() -> 현재까지 수행한 모든 작업을 실제 DB에 반영O
		//	rollback() -> 현재까지 수행한 모든 작업을 실제 DB에 반영X
		//	메소드를 호출해야함
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		conn.rollback();
		
		JDBCCloser.close(conn);
		*/
	}
}











