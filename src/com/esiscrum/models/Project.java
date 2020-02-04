package com.esiscrum.models;

public class Project {

	private String id;
	private String nom;
	private String description;
	
	public Project() {
		int nombreAleatoire = 1 + (int)(Math.random() * ((10000000 - 1) + 1));
		this.id = "Project" +nombreAleatoire;
		this.nom = "";
		this.description = "";
	}

	public Project(String id, String nom, String description) {
		super();
		this.id = id;
		this.nom = nom;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", nom=" + nom + ", description=" + description + "]";
	}
	
	
	

}
