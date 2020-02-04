package com.esiscrum.models;

import java.util.List;
import java.util.Vector;



public class Sprint {
	
	private String id;
	private String nom;
	private String description;
	private String startdate;
	private String enddate;
	private int totalheure;
	private int currentheure;
	private String etat;
	private String color;
	private List<BacklogItem> listbacklog;
	private String project;
	
	public Sprint() {
		
		int nombreAleatoire = 1 + (int)(Math.random() * ((10000000 - 1) + 1));
		this.id = "Sprint" +nombreAleatoire;
		this.nom = "";
		this.description = "";
		this.startdate = "";
		this.enddate = "";
		this.totalheure = 0;
		this.currentheure = 0;
		this.etat = "inplanning";
		this.color = "info";
		this.listbacklog = new Vector<BacklogItem>();
	
	}
	
	

	
	
	public Sprint(String nom, String startdate, String enddate, int totalheure) {
		
		int nombreAleatoire = 1 + (int)(Math.random() * ((10000000 - 1) + 1));
		this.id = "Sprint" +nombreAleatoire;
		this.nom = nom;
		this.startdate = startdate;
		this.enddate = enddate;
		this.totalheure = totalheure;
		this.description = "";
		this.currentheure = 0;
		this.etat = "inplanning";
		this.color = "info";
		this.listbacklog = new Vector<BacklogItem>();
		
	}





	public Sprint(String id, String nom, String description, String startdate, String enddate, int totalheure,
			int currentheure, String etat, String color, List<BacklogItem> listbacklog) {
		super();
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.startdate = startdate;
		this.enddate = enddate;
		this.totalheure = totalheure;
		this.currentheure = currentheure;
		this.etat = etat;
		this.color = color;
		this.listbacklog = listbacklog;
		
	}

	public Sprint(String id, String nom, String description, String startdate, String enddate, int totalheure,
			int currentheure, String etat, String color) {
		super();
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.startdate = startdate;
		this.enddate = enddate;
		this.totalheure = totalheure;
		this.currentheure = currentheure;
		this.etat = etat;
		this.color = color;
		this.listbacklog = new Vector<BacklogItem>();
	
	}





	public Sprint(String id, String nom, String description, String startdate, String enddate, int totalheure,
			int currentheure, String etat, String color, String project) {
		super();
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.startdate = startdate;
		this.enddate = enddate;
		this.totalheure = totalheure;
		this.currentheure = currentheure;
		this.etat = etat;
		this.color = color;
		this.project = project;
		this.listbacklog = new Vector<BacklogItem>();
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





	public String getStartdate() {
		return startdate;
	}





	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}





	public String getEnddate() {
		return enddate;
	}





	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}





	public int getTotalheure() {
		return totalheure;
	}





	public void setTotalheure(int totalheure) {
		this.totalheure = totalheure;
	}





	public int getCurrentheure() {
		return currentheure;
	}





	public void setCurrentheure(int currentheure) {
		this.currentheure = currentheure;
	}





	public String getEtat() {
		return etat;
	}





	public void setEtat(String etat) {
		this.etat = etat;
	}





	public String getColor() {
		return color;
	}





	public void setColor(String color) {
		this.color = color;
	}





	public List<BacklogItem> getListbacklog() {
		return listbacklog;
	}





	public void setListbacklog(List<BacklogItem> listbacklog) {
		this.listbacklog = listbacklog;
	}
	
	
	
	public void addBacklog(BacklogItem b) {
		listbacklog.add(b);
	}
	
	public void removeBacklog(BacklogItem b) {
		listbacklog.remove(b);
	}
	
	
	
	public void changeEtat() {
		int total = 0;
		
		for (BacklogItem b : listbacklog) {
			total+=b.getTotalheure();
		}
		setCurrentheure(total);
		if(total==getTotalheure()) {
			setEtat("inprogress");
			setColor("primary");
		}
		else if (total<getTotalheure() && total!=0){
			setEtat("inprogress");
			setColor("primary");
		}
		else if (total>getTotalheure() && total!=0){
			setEtat("inprogress");
			setColor("danger");
		}
		else if (total==0){
			setEtat("inplanning");
			setColor("info");
		}
		
		
		if(AllBacklogDone()) {
			setEtat("completed");
			setColor("success");
		}
	}
	
	public boolean AllBacklogDone() {
		boolean done=false;
		for (BacklogItem b : listbacklog) {
			if(b.getEtat().equals("done")) done=true;
			else done=false;
		}
		return done;
	}
	
	
	public void startSprint() {
		setEtat("inprogress");
		setColor("primary");
	}





	public String getProject() {
		return project;
	}





	public void setProject(String project) {
		this.project = project;
	}





	@Override
	public String toString() {
		return "Sprint [id=" + id + ", nom=" + nom + ", description=" + description + ", startdate=" + startdate
				+ ", enddate=" + enddate + ", totalheure=" + totalheure + ", currentheure=" + currentheure + ", etat="
				+ etat + ", color=" + color + ", listbacklog=" + listbacklog + "]";
	}
	
	

}
