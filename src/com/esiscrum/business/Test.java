package com.esiscrum.business;

import java.util.List;

import com.esiscrum.models.BacklogItem;
import com.esiscrum.models.Membre;
import com.esiscrum.models.Project;
import com.esiscrum.models.Session;
import com.esiscrum.models.Sprint;
import com.esiscrum.models.Task;
import com.esiscrum.web.ManagedBeanContext;

public class Test {

	public List<BacklogItem> listbacklog; 
	public BacklogItem backlogItem;
	public List<Task> listTasks;
	public List<Membre> listMembre;
	public List<Sprint> listsprint;
	public Task task;
	public String utilisateur;
	public String oldIdBacklog;
	
	public Membre currentMembre;
	private ManagedBeanContext<Session> context;
	public Project currentProject;


	public EsiscrumService service ;
	
	
	public Test() {
		exp03();
	}

	public static void main(String[] args) {
		new Test();

	}
	
	void exp01() {
		BacklogItem b = new BacklogItem();
		Object row = b.getPrcProgression();
		//System.out.println(row.getClass());
		System.out.println(row.getClass().getTypeName());
		
		
		//java.lang.Integer
		//class java.lang.Integer
	}
	
	public void initSession() {
		Session ss=this.context.getManagedBean("loginsession");
		currentMembre = service.SelectByMembreUsername(ss.username);
		currentProject = service.SelectCurrentProject(currentMembre.getId());
	}
	
	
	void exp02() {
		service = new EsiscrumServiceCassandra();
		context = new ManagedBeanContext<Session>();
		//initSession();
		//backlogItem = new BacklogItem();
		//listTasks=service.SelectAllTask();
		listMembre=service.SelectAllMembre();
		for (Membre membre : listMembre) {
			System.out.println(membre);
		}
		//System.out.println("&&&&&&&&&&&&&&&&&&& currentProject.getId()" + currentProject.getId());
		//listsprint=service.SelectSprintsByProject("Projet1");
		
		
		listbacklog=service.SelectAllBacklogItem();
		for (BacklogItem b : listbacklog) {
			System.out.println(b);
		}
	
	}
	
	void exp03() {
		service = new EsiscrumServiceCassandra();
		context = new ManagedBeanContext<Session>();
		//initSession();
		//backlogItem = new BacklogItem();
		//listTasks=service.SelectAllTask();

		
		
		Task t = new Task("Task812123", "Backlog269013", "mamadou", "Task 1", "ee", 123, "todo", "assets/images/mamadou.png");
		System.out.println();
		service.InsertTask(t);
	}

}
