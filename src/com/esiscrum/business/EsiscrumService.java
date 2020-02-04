package com.esiscrum.business;

import java.util.List;

import com.esiscrum.models.BacklogItem;
import com.esiscrum.models.Burndown;
import com.esiscrum.models.Burndowninit;
import com.esiscrum.models.Chat;
import com.esiscrum.models.Membre;
import com.esiscrum.models.Project;
import com.esiscrum.models.Sprint;
import com.esiscrum.models.Task;

public interface EsiscrumService {

	
	public List<Project> SelectAllProjects();
	public Project SelectProjectByid(String id);
	public void InsertProject(Project p);
	public void DeleteProject(Project p);
	public void updateProject(Project item);
	
	public void InsertCurrentProject(String user,String project);
	public void UpdateCurrentProject(String user,String project);
	public Project SelectCurrentProject(String user);
	
	public List<Sprint> SelectAllSprint();
	public Sprint SelectBySprintid(String id);
	public void InsertSprint(Sprint item);
	public void DeleteSprint(Sprint item);
	public void updateSprint(Sprint item);
	public List<Sprint> SelectSprintsByProject(String projet);
	
	public List<BacklogItem> SelectAllBacklogItem();
	public BacklogItem SelectByBacklogItemid(String id);
	public void InsertBacklogItem(BacklogItem item);
	public void DeleteBacklogItem(BacklogItem item);
	public void updateBacklogItem(BacklogItem item);
	public List<BacklogItem> SelectBacklogsByProject(String projet);
	public List<BacklogItem> SelectBacklogsBysprint(String sprint);
	
	public List<Task> SelectAllTask();
	public List<Task> SelectTaskByBacklog(String idBacklog);
	public Task SelectByTaskid(String id);
	public void InsertTask(Task item);
	public void DeleteTask(Task item);
	public void updateTask(Task item);
	
	public List<Membre> SelectAllMembre();
	public Membre SelectByMembreid(String id);
	public Membre SelectByMembreUsername(String id);
	public void InsertMembre(Membre item);
	public void DeleteMembre(Membre item);
	public void updateMembre(Membre item);
	
	public List<Chat> SelectAllChat();
	public Chat SelectByChatid(String id);
	public void InsertChat(Chat item);
	public void DeleteChat (Chat item);
	public void updateChat(Chat item);
	
	
	public void insertBurndown(Burndown b);
	public List<Burndown> SelectBurnDownBySprint(String s);
	
	
	public void insertBurndowninit(Burndowninit b);
	public void updateBurndowninit(Burndowninit b);
	public Burndowninit SelectBurnDownInitBySprint(String s);
	
	
}
