package com.esiscrum.models;

public class Session {

	public String username;
	public String pwd;
	
	public Session(String username, String pwd) {
		super();
		this.username = username;
		this.pwd = pwd;
	}

	public Session() {
		this.username = "";
		this.pwd = "";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "Session [username=" + username + ", pwd=" + pwd + "]";
	}
	
	

}
