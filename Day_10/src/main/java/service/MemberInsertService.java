package service;

import java.sql.*;
import java.util.*;
import jdbc.dao.*;
import model.*;

public class MemberInsertService {
	
	private MemberDAO dao;
	public MemberInsertService() {
		this.dao = new MemberDAO();
	}
	
	public boolean service(Connection conn, Member model) {
		//결과가 1이면 insert 성공!!
		return this.dao.insert(conn, model) == 1;
	} 
}
