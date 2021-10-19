package service;

import java.sql.Connection;

import jdbc.util.*;
import jdbc.dao.*;
import model.*;

public class MemberDeleteService {
	private MemberDAO memberDAO = new MemberDAO();
	
	public Member service (Connection conn, Member member) {
		Member search = this.memberDAO.selectOne(conn, member.getId());
		if(search != null && search.getPassword().equals(member.getPassword())) {
			//패스워드가 일치했기 때문에 삭제 가능!
			int result = this.memberDAO.delete(conn, member);
			if(result == 0) {search = null;}
		}else {
			search = null;
		}
		return search;
	}

}
