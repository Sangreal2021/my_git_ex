package service;

import java.sql.*;
import java.util.*;
import jdbc.dao.*;
import model.*;

public class AttachesInsertService {
	
	private AttachesDAO dao;
	public AttachesInsertService() {
		this.dao = new AttachesDAO();
	}
	
	public boolean service(Connection conn, Attaches model) {
		//결과가 1이면 insert 성공!!
		return this.dao.insert(conn, model) == 1;
	} 
}
