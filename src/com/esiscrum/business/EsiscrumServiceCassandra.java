package com.esiscrum.business;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Vector;


import com.esiscrum.jdbc.DataSource;
import com.esiscrum.jdbc.DatabaseSql;
import com.esiscrum.jdbc.DbCassandra;
import com.esiscrum.jdbc.DatabaseNoSql;
import com.esiscrum.jdbc.MySQLDataSource;
import com.esiscrum.models.BacklogItem;
import com.esiscrum.models.Burndown;
import com.esiscrum.models.Burndowninit;
import com.esiscrum.models.Chat;
import com.esiscrum.models.Membre;
import com.esiscrum.models.Project;
import com.esiscrum.models.Sprint;
import com.esiscrum.models.Task;

public class EsiscrumServiceCassandra implements EsiscrumService{

	private DatabaseSql db;
	public EsiscrumServiceCassandra() {
		DataSource ds = new MySQLDataSource("esiscrum");
		db = new DatabaseSql(ds);
	}

	@Override
	public List<Project> SelectAllProjects() {
		return mapProject(db.select("projet"));
	}

	
	public List<Project> mapProject(String data[][]) {
		if(data==null)return null;
		Vector<Project>res=new Vector<>();
		for (int i = 1; i < data.length; i++) {
			res.add(new Project(data[i][0],data[i][1],data[i][2]));

		}
		return res;
	}
	
	
	@Override
	public Project SelectProjectByid(String id) {
		return mapProject(db.select("projet","id",id)).get(0);
	}

	@Override
	public void InsertProject(Project p) {
		db.insert("projet",p.getId(), p.getNom(),p.getDescription());
		
	}

	@Override
	public void DeleteProject(Project p) {
		db.delete("projet", "id", p.getId());
		
	}


	@Override
	public void updateProject(Project p) {
		db.update("projet",p.getId(), p.getNom(),p.getDescription());
		
	}

	@Override
	public void UpdateCurrentProject(String user, String project) {
		db.update("currentprojet",user,project);
		
	}
	
	@Override
	public void InsertCurrentProject(String user, String project) {
		db.insert("currentprojet",user,project);
		
	}

	
	@Override
	public Project SelectCurrentProject(String user) {
		String data[][] =db.select("currentprojet","iduser",user);
		return SelectProjectByid(data[1][1]);
		
	}
	
	
	public List<Sprint> mapSprint(String data[][]) {
		if(data==null)return null;
		Vector<Sprint>res=new Vector<>();
		for (int i = 1; i < data.length; i++) {
			Sprint s = new Sprint(data[i][0],data[i][1],data[i][2],data[i][3],data[i][4],Integer.parseInt(data[i][5]),Integer.parseInt(data[i][6]),data[i][7],data[i][8],data[i][9]);
			s.setListbacklog(SelectBacklogsBysprint(s.getId()));
			res.add(s);
		
		}
		
		return res;
	}
	
	
	@Override
	public List<Sprint> SelectAllSprint() {
		return mapSprint(db.select("sprint"));  
	}

	@Override
	public List<Sprint> SelectSprintsByProject(String projet) {
		return mapSprint(db.select("sprint","project",projet));
	}
	
	@Override
	public Sprint SelectBySprintid(String id) {
		return mapSprint(db.select("sprint","id",id)).get(0);
	}

	@Override
	public void InsertSprint(Sprint item) {
		db.insert("sprint",item.getId(),item.getNom(),item.getDescription(),item.getStartdate(),item.getEnddate(),item.getTotalheure(),item.getCurrentheure(),
				item.getEtat(), item.getColor(),item.getProject());
		
		
		Burndowninit b = new Burndowninit(item.getId(), item.getStartdate(), item.getEnddate(), item.getTotalheure());
		insertBurndowninit(b);
				
	}

	@Override
	public void DeleteSprint(Sprint item) {
		db.delete("sprint", "id", item.getId());
		
	}

	@Override
	public void updateSprint(Sprint item) {
		
		db.update("sprint",item.getId(),item.getNom(),item.getDescription(),item.getStartdate(),item.getEnddate(),item.getTotalheure(),item.getCurrentheure(),
				item.getEtat(), item.getColor(),item.getProject());
		
		Burndowninit b = new Burndowninit(item.getId(), item.getStartdate(), item.getEnddate(), item.getTotalheure());
		updateBurndowninit(b);
		
		ResteAfaireBySprint(item);
		
		
	}

	public List<BacklogItem> mapBacklog(String data[][]) {
		if(data==null)return null;
		Vector<BacklogItem>res=new Vector<>();
		for (int i = 1; i < data.length; i++) {
			BacklogItem b = new BacklogItem(data[i][0], data[i][1], data[i][2], data[i][3], data[i][4] ,Integer.parseInt(data[i][5]),Integer.parseInt(data[i][6]),
					Integer.parseInt(data[i][7]),Integer.parseInt(data[i][8]),data[i][9],data[i][10],data[i][11], data[i][12], data[i][13]);
			b.setTasks(SelectTaskByBacklog(b.getId()));
			res.add(b);
		
		
		}
		
		return res;
	}
	
	@Override
	public List<BacklogItem> SelectAllBacklogItem() {
		return mapBacklog(db.select("backlogitem")); 
	}
	 


	@Override
	public List<BacklogItem> SelectBacklogsByProject(String projet) {
		
		return mapBacklog(db.select("backlogitem","project",projet));
	}
	
	@Override
	public List<BacklogItem> SelectBacklogsBysprint(String sprint) {
		
		return mapBacklog(db.select("backlogitem","sprint",sprint));
	}
	
	
	@Override
	public BacklogItem SelectByBacklogItemid(String id) {

		return mapBacklog(db.select("backlogitem","id",id)).get(0);
	}

	@Override
	public void InsertBacklogItem(BacklogItem item) {

		db.insert("backlogitem",item.getId(), item.getNom(), item.getDescription(),item.getUser(),item.getDatelimite(),item.getPrcProgression(),item.getDifficulte(),
				item.getTotalheure(), item.getTachetotalheure(), item.getEtat(), item.getColor(), item.getProject(), item.getSprint(), item.getUserPhoto());
		
	}

	@Override
	public void DeleteBacklogItem(BacklogItem item) {

		db.delete("backlogitem", "id", item.getId());
		
	}

	@Override
	public void updateBacklogItem(BacklogItem item) {

		db.update("backlogitem",item.getId(), item.getNom(), item.getDescription(),item.getUser(),item.getDatelimite(),item.getPrcProgression(),item.getDifficulte(),
				item.getTotalheure(), item.getTachetotalheure(), item.getEtat(), item.getColor(), item.getProject(), item.getSprint(), item.getUserPhoto());
		
		if(item.getSprint().equals("none")==false) {
			Sprint s = SelectBySprintid(item.getSprint());
			ResteAfaireBySprint(s);
		}

	}
	
	public List<Task> mapTask(String data[][]) {
		if(data==null)return null;
		Vector<Task>res=new Vector<>();
		for (int i = 1; i < data.length; i++) {
			res.add(new Task(data[i][0], data[i][1], data[i][2],data[i][3],Integer.parseInt(data[i][4]), data[i][5] , data[i][6], data[i][7]));
		}
		return res;

	}

	@Override
	public List<Task> SelectAllTask() {

		return mapTask(db.select("task")); 
	}
	
	@Override
	public List<Task> SelectTaskByBacklog(String idBacklog){

		return mapTask(db.select("task","idBacklog",idBacklog)); 
	}
	

	@Override
	public Task SelectByTaskid(String id) {

		return mapTask(db.select("task","id",id)).get(0);
	}

	@Override
	public void InsertTask(Task item) {

		db.insert("task",item.getId(),item.getNom(),item.getDescription(),item.getUser(),item.getTotalHeure(),
				item.getEtat(),item.getImageUser(),item.getIdBacklog());
		
	}

	@Override
	public void DeleteTask(Task item) {

		db.delete("task", "id", item.getId());
		
	}

	@Override
	public void updateTask(Task item) {

		db.update("task",item.getId(),item.getNom(),item.getDescription(),item.getUser(),item.getTotalHeure(),
				item.getEtat(),item.getImageUser(),item.getIdBacklog());
		
	}
	
	public List<Membre> mapMembre(String data[][]) {
		if(data==null)return null;
		Vector<Membre>res=new Vector<>();
		for (int i = 1; i < data.length; i++) {
			res.add(new Membre(data[i][0], data[i][1], data[i][2], data[i][3], data[i][4],data[i][5], data[i][6], data[i][7]));
		}

		return res;

	}

	@Override
	public List<Membre> SelectAllMembre() {

		return mapMembre(db.select("membre")); 
	}

	@Override
	public Membre SelectByMembreid(String id) {

		return mapMembre(db.select("membre","id",id)).get(0);
	}
	
	@Override
	public Membre SelectByMembreUsername(String id) {

		return mapMembre(db.select("membre","username",id)).get(0);
	}

	@Override
	public void InsertMembre(Membre item) {

		db.insert("membre",item.getId(),item.getNom(),item.getPrenom(),item.getUsername(),item.getPwd(),item.getEmail(),item.getRole(),item.getImage());
	}

	@Override
	public void DeleteMembre(Membre item) {

		db.delete("membre", "id", item.getId());
		
	}

	@Override
	public void updateMembre(Membre item) {

		db.update("membre",item.getId(),item.getNom(),item.getPrenom(),item.getUsername(),item.getPwd(),item.getEmail(),item.getRole(),item.getImage());
		
	}

	public List<Chat> mapMessage(String data[][]) {
		if(data==null)return null;
		Vector<Chat>res=new Vector<>();
		for (int i = 1; i < data.length; i++) {
			Membre m1= SelectByMembreUsername(data[i][2]);
			res.add(new Chat(Integer.parseInt(data[i][0]),m1,data[i][1],data[i][4]));
			
		}

		return res;

	}
	@Override
	public List<Chat> SelectAllChat() {
		return mapMessage(db.select("message")); 
	}

	@Override
	public Chat SelectByChatid(String id) {
		return mapMessage(db.select("message","id",id)).get(0);
	}

	@Override
	public void InsertChat(Chat item) {
		db.insert("message", item.getId(),item.getMessage(),item.getUser().getUsername(),item.getUser().getImage(),item.getDate());
		
	}

	@Override
	public void DeleteChat(Chat item) {
		db.delete("message", "id", item.getId());
		
	}

	@Override
	public void updateChat(Chat item) {
		db.update("message", item.getId(),item.getMessage(),item.getUser().getUsername(),item.getUser().getImage(),item.getDate());
		
	}

	public List<Burndown> mapBurndown(String data[][]) {
		if(data==null)return null;
		Vector<Burndown>res=new Vector<>();
		for (int i = 1; i < data.length; i++) {
			
			res.add(new Burndown(data[i][0],data[i][1],Integer.parseInt(data[i][2])));
			
		}

		return res;

	}

	@Override
	public void insertBurndown(Burndown b) {
		List<Burndown> listbb = SelectBurnDownBySprint(b.getSprint()); 
		boolean tutu = false;
		for (Burndown burndown : listbb) {
			if(burndown.getDatemodif().equals(b.getDatemodif())) tutu=true;
		}
		if (tutu) db.update("burndown", b.getSprint(),b.getDatemodif(),b.getResteafaire());
		else db.insert("burndown", b.getSprint(),b.getDatemodif(),b.getResteafaire());
		
	}

	@Override
	public List<Burndown> SelectBurnDownBySprint(String s) {
		
		return mapBurndown(db.select("burndown", "sprint", s));
	}


	public Burndown ResteAfaireBySprint(Sprint s) {
	   if(s.getEtat().equals("completed")) {
		    LocalDateTime myDateObj = LocalDateTime.now(); 
		    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		    String formattedDate = myDateObj.format(myFormatObj); 
		   
		    Burndown b = new Burndown(s.getId(), formattedDate, 0);
			insertBurndown(b);
			return b;
	   }
	   else {
		   
	   
	   int total=s.getTotalheure();
	   List<BacklogItem> list = SelectBacklogsBysprint(s.getId());
	   for (BacklogItem backlogItem : list) {
		   if(backlogItem.getEtat().equals("done")) {
			   total=total-backlogItem.getTotalheure();
		   }
		   else if(backlogItem.getEtat().equals("inprogress")) {
			   
				float x = (float)backlogItem.getPrcProgression()/100;
				float y = x*backlogItem.getTotalheure();
				//System.out.println("xxx "+x);
				//System.out.println("yyy "+y);
				int nb=(int)y;
			   
			  // int nb = (int)(backlogItem.getTotalheure()*(backlogItem.getPrcProgression()/100));
			   total=total-nb;
		   }
	   }
	    LocalDateTime myDateObj = LocalDateTime.now(); 
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
	    String formattedDate = myDateObj.format(myFormatObj); 
	   
	    Burndown b = new Burndown(s.getId(), formattedDate, total);
	   insertBurndown(b);
	   return b;
	   }
	}

	@Override
	public void insertBurndowninit(Burndowninit b) {
		db.insert("burndowninit", b.getSprint(),b.getDatedebut(),b.getDatefin(),b.getTotaleheure());
		
	}

	@Override
	public void updateBurndowninit(Burndowninit b) {
		db.update("burndowninit", b.getSprint(),b.getDatedebut(),b.getDatefin(),b.getTotaleheure());
		
	}

	@Override
	public Burndowninit SelectBurnDownInitBySprint(String s) {
		
		return mapBurndownInit(db.select("burndowninit", "sprint", s));
	}

	public Burndowninit mapBurndownInit(String data[][]) {
		if(data==null)return null;
		for (int i = 1; i < data.length; i++) {
			Burndowninit b = new Burndowninit(data[i][0],data[i][1],data[i][2],Integer.parseInt(data[i][3]));
			return b;
		}
		return null;
	}





}
