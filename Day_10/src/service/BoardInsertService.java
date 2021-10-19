package service;

import java.sql.*;
import java.util.*;
import jdbc.dao.*;
import model.*;

public class BoardInsertService {
	
	private BoardDAO dao;
	public BoardInsertService() {
		this.dao = new BoardDAO();
	}
	
	public boolean service(Connection conn, Board model) {
		//결과가 1이면 insert 성공!!
		return this.dao.insert(conn, model) == 1;
	} 
}
