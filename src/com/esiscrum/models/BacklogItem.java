package com.esiscrum.models;

import java.util.List;
import java.util.Vector;

import com.esiscrum.business.EsiscrumService;
import com.esiscrum.business.EsiscrumServiceMysqls;
import com.esiscrum.business.EsiscrumServiceCassandra;


public class BacklogItem {

	private String id;
	private String nom;
	private String user;
	private String datelimite;
	private String  sprint;
	private String description;
	private int totalheure;
	private String etat;
	private List<Task> tasks;
	private int prcProgression;
	private int difficulte;
	private Task tache;
	
	private String userPhoto; 
	private String color;
	
	private String tachenom;
	private String tacheid;
	private String tacheuser;
	private String tachedescription;
	private int tachetotalheure;
	
	private String project;
	
	public EsiscrumService service ;
	
	public BacklogItem() {
		service = new EsiscrumServiceCassandra();
		this.nom="";
		int nombreAleatoire = 1 + (int)(Math.random() * ((10000000 - 1) + 1));
		this.id = "Backlog" +nombreAleatoire;
		this.user = "";
		this.datelimite = "";
		this.sprint = "none";
		this.description = "";
		this.totalheure = 0;
		this.etat = "inprogress";
		this.tasks = new Vector<Task>();
		this.prcProgression = 0;
		this.difficulte = 0;
		this.tachenom="";
		this.tachedescription="";
		this.tacheuser="";
	    this.tachetotalheure=0;
	    
		this.tache=new Task(this.id); 
		this.tacheid=this.tache.getId();
		this.color="info";
		this.project="";
	}

	

	public BacklogItem(String id, String nom, String user, String datelimite, String sprint, String description,
			int totalheure, String etat, Vector<Task> tasks, int prcProgression, int difficulte) {
		super();
		service = new EsiscrumServiceCassandra();
		this.id = id;
		this.nom = nom;
		this.user = user;
		this.datelimite = datelimite;
		this.sprint = sprint;
		this.description = description;
		this.totalheure = totalheure;
		this.etat = etat;
		this.tasks = tasks;
		this.prcProgression = prcProgression;
		this.difficulte = difficulte;
		this.tachenom="";
		this.tachedescription="";
		this.tacheuser="";
	    this.tachetotalheure=0;
		this.tache=new Task(this.id); 
		this.tacheid=this.tache.getId();
		this.color="info";
		this.project="";
		
	}
	



	public BacklogItem(String id, String nom, String user, String datelimite, String sprint, String description,
			int totalheure, String etat,String userPhoto) {
		super();
		service = new EsiscrumServiceCassandra();
		this.id = id;
		this.nom = nom;
		this.user = user;
		this.datelimite = datelimite;
		this.sprint = sprint;
		this.description = description;
		this.totalheure = totalheure;
		this.etat = etat;
		this.tachenom="";
		this.tachedescription="";
		this.tacheuser="";
	    this.tachetotalheure=0;
		this.tache=new Task(this.id); 
		this.tasks = new Vector<Task>();
		this.tacheid=this.tache.getId();
		this.userPhoto=userPhoto;
		this.color="info";
		this.project="";
		
		
	}



	public BacklogItem(String id, String nom, String user, String datelimite, String sprint, String description,
			int totalheure, String etat, int prcProgression, int difficulte, String userPhoto, String color,
			String project) {
		
		super();
		service = new EsiscrumServiceCassandra();
		this.id = id;
		this.nom = nom;
		this.user = user;
		this.datelimite = datelimite;
		this.sprint = sprint;
		this.description = description;
		this.totalheure = totalheure;
		this.etat = etat;
		this.prcProgression = prcProgression;
		this.difficulte = difficulte;
		this.userPhoto = userPhoto;
		this.color = color;
		this.project = project;
		this.tachenom="";
		this.tachedescription="";
		this.tacheuser="";
	    this.tachetotalheure=0;
		this.tache=new Task(this.id); 
		this.tasks = new Vector<Task>();
		this.tacheid=this.tache.getId();
	}
	
	public BacklogItem(String id, String nom, String description, String user, String datelimite,int prcProgression, int difficulte, 
			int totalheure,int tachetotalheure, String etat,String color,	String project,String sprint,String userPhoto) {
		
		super();
		service = new EsiscrumServiceCassandra();
		this.id = id;
		this.nom = nom;
		this.user = user;
		this.datelimite = datelimite;
		this.sprint = sprint;
		this.description = description;
		this.totalheure = totalheure;
		this.etat = etat;
		this.prcProgression = prcProgression;
		this.tachetotalheure=0;
		this.difficulte = difficulte;
		this.userPhoto = userPhoto;
		this.color = color;
		this.project = project;
		this.tachenom="";
		this.tachedescription="";
		this.tacheuser="";
	    this.tachetotalheure=0;
		this.tache=new Task(this.id); 
		this.tasks = new Vector<Task>();
		this.tacheid=this.tache.getId();
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDatelimite() {
		return datelimite;
	}

	public void setDatelimite(String datelimite) {
		this.datelimite = datelimite;
	}

	public String getSprint() {
		return sprint;
	}

	public void setSprint(String sprint) {
		this.sprint = sprint;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTotalheure() {
		return totalheure;
	}

	public void setTotalheure(int totalheure) {
		this.totalheure = totalheure;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	
	public void addTask(Task t) {
		tasks.add(t);
	}
	
	
	public void NouvelleTach() {
		tache.setDescription(tachedescription);
		tache.setNom(tachenom);
		tache.setTotalHeure(tachetotalheure);
	//	tache.setUser(tacheuser);
		tache.setEtat("todo");
		System.out.println("BacklogItem.NouvelleTach() : " + tache);
		Membre m = service.SelectByMembreUsername(tache.getUser());
		tache.setImageUser(m.getImage());
		tasks.add(tache);

	}
	
	
	public void InitTache() {
		this.tachenom="";
		this.tachedescription="";
		this.tacheuser="";
	    this.tachetotalheure=0;
		this.tache=new Task(this.id);
		
		this.tacheid=this.tache.getId();
	}
	
	public int getPrcProgression() {
		return prcProgression;
	}



	public void setPrcProgression(int prcProgression) {
		this.prcProgression = prcProgression;
	}



	public int getDifficulte() {
		return difficulte;
	}



	public void setDifficulte(int difficulte) {
		this.difficulte = difficulte;
	}



	public Task getTache() {
		return tache;
	}



	public void setTache(Task tache) {
		this.tache = tache;
	}



	public String getTachenom() {
		return tachenom;
	}



	public void setTachenom(String tachenom) {
		this.tachenom = tachenom;
	}



	public String getTacheuser() {
		return tacheuser;
	}



	public void setTacheuser(String tacheuser) {
		this.tacheuser = tacheuser;
	}



	public String getTachedescription() {
		return tachedescription;
	}



	public void setTachedescription(String tachedescription) {
		this.tachedescription = tachedescription;
	}



	public int getTachetotalheure() {
		return tachetotalheure;
	}



	public void setTachetotalheure(int tachetotalheure) {
		this.tachetotalheure = tachetotalheure;
	}



	public String getTacheid() {
		return tacheid;
	}



	public void setTacheid(String tacheid) {
		this.tacheid = tacheid;
	}



	public String getUserPhoto() {
		return userPhoto;
	}



	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}



	public String getColor() {
		return color;
	}



	public void setColor(String color) {
		this.color = color;
	}



	
	
	public String getProject() {
		return project;
	}



	public void setProject(String project) {
		this.project = project;
	}



	@Override
	public String toString() {
		return "BacklogItem [id=" + id + ", nom=" + nom + ", user=" + user + ", datelimite=" + datelimite + ", sprint="
				+ sprint + ", description=" + description + ", totalheure=" + totalheure + ", etat=" + etat + ", tasks="
				+ tasks + "]";
	}
	
	


}
