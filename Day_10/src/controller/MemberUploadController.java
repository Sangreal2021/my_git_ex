package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

@WebServlet("/member/upload.tje")
@MultipartConfig(
	location = "C:attaches",
	fileSizeThreshold = 1024*1024,
	maxFileSize = 1024*1024*50, //50메가
	maxRequestSize = 1024*1024*50*5 //50메가 5개까지
)
public class MemberUploadController extends HttpServlet {
	private static final String formPage = "/WEB-INF/view/form/member/upload.jsp";
	private static final String submitPage = "/WEB-INF/view/submit/member/upload.jsp";
	
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
		request.setCharacterEncoding("UTF-8");
		
		UploadUtil uploadUtil = UploadUtil.create(request.getServletContext());
		Part p = request.getPart("file");
		String fileName = p.getSubmittedFileName();
		uploadUtil.saveFiles(p, uploadUtil.createFilePath());
		 
		/*
		List<Part> parts = (List<Part>) request.getParts();
		
		for(Part part : parts) {
			System.out.printf("파라미터 명 : %s, contentType :  %s,  size : %d bytes \n", 
				part.getName(), part.getContentType(), part.getSize());



			
			if(!part.getName().equals("file")) continue; // file로 들어온 Part가 아니면 스킵
			if(part.getSubmittedFileName().equals("")) continue; //업로드 된 파일 이름이 없으면 스킵
			
			String fileName = part.getSubmittedFileName();
			
			uploadUtil.saveFiles(part, uploadUtil.createFilePath()); 
		}
		*/
	}
}











