package service;

import java.sql.*;
import java.util.*;
import jdbc.dao.*;
import model.*;

//특정 아이디에 회원가입 여부를 확인하는 서비스
public class MemberSelectOneService {
	
	private MemberDAO dao;
	public MemberSelectOneService() {
		this.dao = new MemberDAO();
	}
	
	public Member service(Connection conn, Member model) {
		return this.dao.select(conn, model);
	} 
}
