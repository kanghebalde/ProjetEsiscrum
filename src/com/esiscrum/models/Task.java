package com.esiscrum.models;

public class Task {

	private String id;
	private String nom;
	private String Description;
	private String user;
	private int totalHeure;
	private String etat;
	private String imageUser;
	private String idBacklog;
	
	public Task() {
		int nombreAleatoire = 1 + (int)(Math.random() * ((10000000 - 1) + 1));
		this.id = "Task" +nombreAleatoire;
		this.user = "";
		this.nom="tttt";
		Description = "";
		this.totalHeure = 0;
		this.etat = "todo";
		this.idBacklog="";
		this.imageUser="";
		//System.out.println("Task.Task() : " + this.id);
	}

	public Task(String idBack) {
		int nombreAleatoire = 1 + (int)(Math.random() * ((10000000 - 1) + 1));
		this.id = "Task" +nombreAleatoire;
		this.user = "";
		this.nom="tttt";
		Description = "";
		this.totalHeure = 0;
		this.etat = "todo";
		this.idBacklog=idBack;
		this.imageUser="";
		//System.out.println("Task.Task() : " + this.id);
	}


	



	public Task(String id, String idBacklog, String user, String nom, String description, int totalHeure, String etat,
			String imageUser) {
		super();
		this.id = id;
		this.idBacklog = idBacklog;
		this.user = user;
		this.nom = nom;
		Description = description;
		this.totalHeure = totalHeure;
		this.etat = etat;
		this.imageUser = imageUser;
	}
	
	

	public Task(String id, String nom, String description, String user, int totalHeure, String etat, String imageUser,
			String idBacklog) {
		super();
		this.id = id;
		this.nom = nom;
		Description = description;
		this.user = user;
		this.totalHeure = totalHeure;
		this.etat = etat;
		this.imageUser = imageUser;
		this.idBacklog = idBacklog;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public int getTotalHeure() {
		return totalHeure;
	}
	public void setTotalHeure(int totalHeure) {
		this.totalHeure = totalHeure;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}



	public String getIdBacklog() {
		return idBacklog;
	}



	public void setIdBacklog(String idBacklog) {
		this.idBacklog = idBacklog;
	}



	public String getImageUser() {
		return imageUser;
	}

	public void setImageUser(String imageUser) {
		this.imageUser = imageUser;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", idBacklog=" + idBacklog + ", user=" + user + ", nom=" + nom + ", Description="
				+ Description + ", totalHeure=" + totalHeure + ", etat=" + etat + "]";
	}


}
