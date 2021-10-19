package service;

import java.sql.*;
import java.util.*;
import jdbc.dao.*;
import model.*;

//특정 아이디에 회원가입 여부를 확인하는 서비스
public class AttachesSelectAllService {
	
	private AttachesDAO dao;
	public AttachesSelectAllService() {
		this.dao = new AttachesDAO();
	}
	
	public ArrayList<Attaches> service(Connection conn, Attaches model) {
		return this.dao.select(conn, model);
	} 
}
