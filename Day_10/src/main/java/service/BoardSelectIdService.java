package service;

import java.sql.*;
import java.util.*;
import jdbc.dao.*;
import model.*;

//특정 아이디에 회원가입 여부를 확인하는 서비스
public class BoardSelectIdService {
	
	private BoardDAO dao;
	public BoardSelectIdService() {
		this.dao = new BoardDAO();
	}
	
	public int service(Connection conn, Board model) {
		return this.dao.selectId(conn, model);
	} 
}
