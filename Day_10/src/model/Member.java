package model;

import java.util.Date;

//DB의 테이블과 연동되는 모델 클래스의 선언
//- board DB의 member테이블의 레코드를 저장할 수 있는 클래스
//- 모델 클래스는 되도록 순수 자바의 기능(Java SE)만을 사용하여 구현합니다.
//	(오픈소스 프레임워크를 사용하는 경우 POJO원칙을 준수하기 때문에.. 변경이 많아짐)

public class Member {
	private String id;
	private String password;
	private String name;
	private int age;
	private String phone;
	private Date regDate;
	
	public Member() {}

	public Member(String id, String password, String name, int age, String phone, Date regDate) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.age = age;
		this.phone = phone;
		this.regDate = regDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	
}


















