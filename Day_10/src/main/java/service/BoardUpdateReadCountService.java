package service;

import java.sql.*;
import java.util.*;
import jdbc.dao.*;
import model.*;

//조회수 확인 서비스
public class BoardUpdateReadCountService {
	
	private BoardDAO dao;
	public BoardUpdateReadCountService() {
		this.dao = new BoardDAO();
	}
	
	public boolean service(Connection conn, Board model) {
		return this.dao.updateReadCount(conn, model)==1;
	} 
}
