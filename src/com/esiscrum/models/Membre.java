package com.esiscrum.models;

public class Membre {

	private String id;
	private String nom;
	private String prenom;
	private String username;
	private String pwd;
	private String email;
	private String role;
	private String image;
	
	public Membre() {
		
		int nombreAleatoire = 1 + (int)(Math.random() * ((10000000 - 1) + 1));
		this.id = "Membre" +nombreAleatoire;
		this.nom = "";
		this.prenom = "";
		this.username = "";
		this.pwd = "";
		this.email = "";
		this.role = "membre";
		this.image = "assets/images/placeholder.jpg";
	}

	public Membre(String id, String nom, String prenom, String username, String pwd, String email, String role,
			String image) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.username = username;
		this.pwd = pwd;
		this.email = email;
		this.role = role;
		this.image = image;
	}

	public Membre(String username, String image) {
		super();
		this.username = username;
		this.image = image;
	}
	
	public Membre( String prenom,String nom,String username, String image) {
		super();
		this.username = username;
		this.nom = nom;
		this.prenom = prenom;
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Membre [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", username=" + username + ", pwd=" + pwd
				+ ", email=" + email + ", role=" + role + ", image=" + image + "]";
	}

	
	

}
