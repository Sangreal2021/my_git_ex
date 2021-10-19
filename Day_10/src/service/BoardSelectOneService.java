package service;

import java.sql.*;
import java.util.*;
import jdbc.dao.*;
import model.*;

public class BoardSelectOneService {
	
	private BoardDAO dao;
	public BoardSelectOneService() {
		this.dao = new BoardDAO();
	}
	
	public Board service(Connection conn, Board model) {
		return this.dao.select(conn, model);
	} 
}
