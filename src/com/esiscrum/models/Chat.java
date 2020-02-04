package com.esiscrum.models;

import java.util.Date;
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter;
public class Chat {

	private int id;
	private Membre user;
	private String message;
	private String date;
	
	public Chat() {
		// TODO Auto-generated constructor stub
	}

	public Chat(int id, Membre user, String message, String date) {
		super();
		this.id = id;
		this.user = user;
		this.message = message;
		this.date = date;
	}

	public Chat(int id,Membre user, String message) {
		super();
		this.id = id;
		this.user = user;
		this.message = message;
	    LocalDateTime myDateObj = LocalDateTime.now(); 
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); 
	    String formattedDate = myDateObj.format(myFormatObj); 
	    //System.out.println("After formatting: " + formattedDate);
	    this.date = formattedDate;
	}
	
	
	public Chat(int id,Membre user) {
		super();
		this.id = id;
		this.user = user;
		this.message = "";
	    LocalDateTime myDateObj = LocalDateTime.now(); 
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); 
	    String formattedDate = myDateObj.format(myFormatObj); 
	    //System.out.println("After formatting: " + formattedDate);
	    this.date = formattedDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Membre getUser() {
		return user;
	}

	public void setUser(Membre user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Chat [id=" + id + ", user=" + user + ", message=" + message + ", date=" + date + "]";
	}
	
	

}
