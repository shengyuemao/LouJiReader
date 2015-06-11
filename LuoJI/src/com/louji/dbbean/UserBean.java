package com.louji.dbbean;


public class UserBean extends BaseBean {

	private int uid;
	private String username;
	private String password;
	private String email;
	private String phone;
	private String photo;
	private String authinfo;
	private String info;
	
	public static String getTable(){
		return "think_louji_user";
	}

	public UserBean() {
		super();
		this.TABLE = "think_louji_user";
	}
	
	

	public int getUid() {
		return uid;
	}



	public void setUid(int uid) {
		this.uid = uid;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getAuthinfo() {
		return authinfo;
	}

	public void setAuthinfo(String authinfo) {
		this.authinfo = authinfo;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
