package com.esiscrum.models;

public class Burndowninit {

	private String sprint;
	private String datedebut;
	private String datefin;
	private int totaleheure;
	
	public Burndowninit() {
		// TODO Auto-generated constructor stub
	}

	public Burndowninit(String sprint, String datedebut, String datefin, int totaleheure) {
		super();
		this.sprint = sprint;
		this.datedebut = datedebut;
		this.datefin = datefin;
		this.totaleheure = totaleheure;
	}

	public String getSprint() {
		return sprint;
	}

	public void setSprint(String sprint) {
		this.sprint = sprint;
	}

	public String getDatedebut() {
		return datedebut;
	}

	public void setDatedebut(String datedebut) {
		this.datedebut = datedebut;
	}

	public String getDatefin() {
		return datefin;
	}

	public void setDatefin(String datefin) {
		this.datefin = datefin;
	}

	public int getTotaleheure() {
		return totaleheure;
	}

	public void setTotaleheure(int totaleheure) {
		this.totaleheure = totaleheure;
	}

	@Override
	public String toString() {
		return "Burndowninit [sprint=" + sprint + ", datedebut=" + datedebut + ", datefin=" + datefin + ", totaleheure="
				+ totaleheure + "]";
	}

	
}
