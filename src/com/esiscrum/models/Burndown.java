package com.esiscrum.models;

public class Burndown {
	
	private String sprint;
	private String datemodif;
	private int resteafaire;
	
	public Burndown() {
		// TODO Auto-generated constructor stub
	}

	public Burndown(String sprint, String datemodif, int resteafaire) {
		super();
		this.sprint = sprint;
		this.datemodif = datemodif;
		this.resteafaire = resteafaire;
	}

	public String getSprint() {
		return sprint;
	}

	public void setSprint(String sprint) {
		this.sprint = sprint;
	}

	public String getDatemodif() {
		return datemodif;
	}

	public void setDatemodif(String datemodif) {
		this.datemodif = datemodif;
	}

	public int getResteafaire() {
		return resteafaire;
	}

	public void setResteafaire(int resteafaire) {
		this.resteafaire = resteafaire;
	}

	@Override
	public String toString() {
		return "Burndown [sprint=" + sprint + ", datemodif=" + datemodif + ", resteafaire=" + resteafaire + "]";
	}
	
	

}
