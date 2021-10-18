package service;

import java.sql.*;
import java.util.*;
import jdbc.dao.*;
import model.*;

//특정 아이디에 회원가입 여부를 확인하는 서비스
public class BoardSelectAllService {
	
	private BoardDAO dao;
	public BoardSelectAllService() {
		this.dao = new BoardDAO();
	}
	
	public ArrayList<Board> service(Connection conn) {
		return this.dao.select(conn);
	} 
}
