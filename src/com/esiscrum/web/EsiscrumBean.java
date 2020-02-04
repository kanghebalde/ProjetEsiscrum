package com.esiscrum.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.event.DragDropEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.esiscrum.business.AncienneCassandra;
import com.esiscrum.business.EsiscrumService;
import com.esiscrum.business.EsiscrumServiceCassandra;
import com.esiscrum.models.BacklogItem;
import com.esiscrum.models.Burndown;
import com.esiscrum.models.Burndowninit;
import com.esiscrum.models.Chat;
import com.esiscrum.models.Membre;
import com.esiscrum.models.Project;
import com.esiscrum.models.Session;
import com.esiscrum.models.Sprint;
import com.esiscrum.models.Task;

@ManagedBean
@ApplicationScoped
public class EsiscrumBean {

	public EsiscrumService service ;
	public ManagedBeanContext<Session> context;
	
	public Membre currentMembre;
	public Project currentProject;
	public Sprint currentSprint;
	public Chat currentMessage;

	public List<BacklogItem> listbacklog; 
	public List<Task> listTasks;
	public List<Membre> listMembre;
	public List<Sprint> listSprint;
	public List<Project> listproject;
	public List<Chat> listchat;
	public List<Integer> listdifficulte= new ArrayList<>(Arrays.asList(0,1,2,3,5,8,13,21,34,55,89,144,233,377,610,987));
	
	public BacklogItem backlogItem;
	public Task task;
	public Sprint sprintitem;
	public Project project;
	public Membre  membre;
	
	public BarChartModel barModel;
	public LineChartModel dateModel;
	
	public String utilisateur;
	public String ppcurrent;
	public String oldIdBacklog;
	public File file;
	public int idChat=0;
	public String messagez;
	public String mm;
	public boolean listRechercheIsEmpty;
	

	
	@PostConstruct
	public void init() {
		System.out.println("EsiscrumBean.init()");
		service = new EsiscrumServiceCassandra();
		//service = new AncienneCassandra();
		context = new ManagedBeanContext<Session>();
		initSession();
		
		//Backolgitem
		backlogItem = new BacklogItem();
		listTasks=service.SelectAllTask();
		listMembre=service.SelectAllMembre();
		listbacklog=service.SelectBacklogsByProject(currentProject.getId());
		backlogItem.setProject(currentProject.getId());
		task = new Task();
		task.setIdBacklog(backlogItem.getId());
		oldIdBacklog = "rien";
		//Backolgitem
		
		
		//Sprint
		listSprint=service.SelectSprintsByProject(currentProject.getId());
		sprintitem = new Sprint();
		//Sprint
		
		//taskboard 
		  if(listSprint.size()>0) {
			  currentSprint=listSprint.get(0);
			  //listbacklog = service.SelectBacklogsBysprint(currentSprint.getId());
		  }
		  else {
			  currentSprint=null;
			 // listbacklog = service.SelectAllBacklogItem();
		  }
		  
		 //taskboard
		  
		 //projet
		  
			project = new Project();
			listproject= service.SelectAllProjects();
		//projet
			
		//people

			membre=new Membre();
			
		//people
			
			
		//chat
			
			listchat=service.SelectAllChat();

			
			currentMessage = new Chat(Lastid(), currentMembre);
			messagez="";
			mm="";		
	
		 //chat
			
		//sprintchart
			createBarModels();
		//sprintchart
			
		//burndown
			createDateModel();
		//burndown
			
			
	}
	public EsiscrumBean() {
		System.out.println("########################@@@@@#############@@ EsiscrumBean");

	}
	
	
	
	public void initSession() {
		Session ss=this.context.getManagedBean("loginsession");
		currentMembre = service.SelectByMembreUsername(ss.username);
		currentProject = service.SelectCurrentProject(currentMembre.getId());
	}
	
	
	public void ChangeCurrentProject(ValueChangeEvent e) {
			String current = e.getNewValue().toString();
			currentProject = service.SelectProjectByid(current);
			service.UpdateCurrentProject(currentMembre.getId(), currentProject.getId());	
			listSprint=service.SelectSprintsByProject(currentProject.getId());
			listbacklog=service.SelectBacklogsByProject(currentProject.getId());
	}
	
	
	public void ChangeCurrentSprint(ValueChangeEvent e) {
		String current = e.getNewValue().toString();
		currentSprint = service.SelectBySprintid(current);
		listbacklog=service.SelectBacklogsByProject(currentProject.getId());
		createDateModel();
		createBarModel();
}	
	
	
	//-----------------------------------------------------------------------Backlog Item-------------------------------------------------------
	public void addBacklog() {
		//System.out.println("UserBean.addBacklog()");
		backlogItem.setEtat("todo");
		backlogItem.setUser(utilisateur);
		Membre m = service.SelectByMembreUsername(utilisateur);
		backlogItem.setUserPhoto(m.getImage());
		
		utilisateur="noone";
		listbacklog.add(backlogItem);


		
		service.InsertBacklogItem(backlogItem);
		EtatBacklog(backlogItem);
		backlogItem = new BacklogItem();
		backlogItem.setProject(currentProject.getId());
		task = new Task();
		task.setIdBacklog(backlogItem.getId());
		
		/*
		for (BacklogItem v : listbacklog) {
			//System.out.println(v);
		}
		
		*/
		oldIdBacklog = "rien";

		//listbacklog=service.SelectAllBacklogItem();
	}
	
	public void updateTask(Task t) {
		//System.out.println("UserBean.updateTask()");
		if(utilisateur.equals("noone")==false){
			t.setUser(utilisateur);
		}
		oldIdBacklog = "#" + t.getIdBacklog();
		EtatBacklog(getBacklogFromList(t.getIdBacklog()));

		
		service.updateTask(t);
		//listTasks=service.SelectAllTask();
		//listbacklog=service.SelectAllBacklogItem();
	}
	
	
	public void updateBacklog(BacklogItem b) {
		if(utilisateur.equals("noone")==false){
			b.setUser(utilisateur);
		}
		utilisateur="noone";
		for (BacklogItem v : listbacklog) {
			//System.out.println(v);
		}
		oldIdBacklog = "rien";
		EtatBacklog(b);
	}
	
	public void utilisateur(ValueChangeEvent e) {
		//System.out.println("LocalChangeListener: user" + e.getNewValue());
		backlogItem.setUser(e.getNewValue().toString());
	}
	
	public void addTask(String idBacklog) {
		if(backlogItem.getId().equals(idBacklog)) {
			
			if(utilisateur.equals("noone")==false){
				backlogItem.getTache().setUser(utilisateur);
			}
			
			backlogItem.NouvelleTach();
			
			backlogItem.setTachenom("");
			oldIdBacklog = "#" + "formaddbacklogitem";
			//System.out.println("task11 : " + backlogItem.getTache());
			EtatBacklog(backlogItem);
			service.InsertTask(backlogItem.getTache());
		}
		else {
			//System.out.println("UserBean.addTask() :  " + idBacklog);
			
			for (BacklogItem v : listbacklog) {
				//System.out.println(v);
			}
			for (BacklogItem bb : listbacklog) {
				if(bb.getId().equals(idBacklog)) {
					if(utilisateur.equals("noone")==false){
						bb.getTache().setUser(utilisateur);
					}
					
					bb.NouvelleTach();
					service.InsertTask(bb.getTache());
					//System.out.println("@@@@@@@@@@@@@@@@@@@@@ Tache : " + bb.getTache());
					
					bb.InitTache();
					
					EtatBacklog(bb);
					oldIdBacklog = "#" + bb.getId();
					
					
					

				}
			}
		}

		utilisateur="noone";
		listTasks= service.SelectAllTask();
	}
	
	public void EtatBacklog(BacklogItem b) {
		List<Task> list = b.getTasks();
		int size= list.size();
		int finish=0;
		int heure=0;
		for (Task task : list) {
			if(task.getEtat().equals("done")) {
				finish=finish+task.getTotalHeure();
			}
			heure=heure+task.getTotalHeure();
		}
		int total=0;
		if(size>0) {
			float x = (float)finish/heure;
			float y = x*100;
			//System.out.println("xxx "+x);
			//System.out.println("yyy "+y);
			total=(int)y;
		}
		//System.out.println("fincish : "+ finish);
		//System.out.println("size : "+ size);
		//System.out.println("Toatlllllll : "+ total);
		b.setPrcProgression(total);
		if(total==100) {
			b.setEtat("done");
			b.setColor("success");
		}
		else if (AlltasksTodo(b)) {
			b.setEtat("todo");
			b.setColor("info");
		}
		else {
			b.setEtat("inprogress");
			b.setColor("primary");
		}
		b.setTotalheure(heure);
		service.updateBacklogItem(b);
		//listbacklog=service.SelectAllBacklogItem();
	}
	public boolean AlltasksTodo(BacklogItem b) {
		List<Task> list = b.getTasks();

		for (Task task : list) {
			if(task.getEtat().equals("todo")==false) {
				return false;
			}
		}
		return true;
	}

	public BacklogItem getBacklogFromList(String id) {
		for (BacklogItem v : listbacklog) {
			if(v.getId().equals(id)) {
				return v;
			}
		}
		return null;
	}


	//-----------------------------------------------------------------------Backlog Item-------------------------------------------------------

	//-----------------------------------------------------------------------Sprint-------------------------------------------------------

	
	  public void onbacklogdropedtolistbacklog(DragDropEvent event) {
		  //System.out.println("SprintBean.onbacklogdropedtolistbacklog()");
		  BacklogItem item = (BacklogItem) event.getData();
		  BacklogItem b = getBacklogFromlistbacklog(item);
		  
		  Sprint sprintdrag = getSprintFromlistSprint(getIdSprint((event.getDragId())));
		  if(b!=null) {
			  b.setSprint("none");
			  service.updateBacklogItem(b);
			  sprintdrag.removeBacklog(b);
			  sprintdrag.changeEtat();
			  service.updateSprint(sprintdrag);
		  }
		  //System.out.println(b);
		 // updateSprints();
		 // refresh=true;
	  }
	  
	  
	  
	  public void onbacklogdropedtosprint(DragDropEvent event) {
		 //System.out.println("SprintBean.onbacklogdropedtosprint()");
		  BacklogItem item = (BacklogItem) event.getData();
		  BacklogItem b = getBacklogFromlistbacklog(item);
	      //System.out.println("event.getDragId(): " + getIdSprint(event.getDragId()));
	      //System.out.println("(event.getDropId(): "+ getIdSprint(event.getDropId()));
		  
		  
		  
		  
		  if(getIdSprint(event.getDragId()).equals("none")){
			  Sprint sprintdrop = getSprintFromlistSprint(getIdSprint((event.getDropId())));
			  //System.out.println("####1######## sprintdrop + " +sprintdrop);
			  if(b!=null) {
				  b.setSprint(sprintdrop.getId());
				  sprintdrop.addBacklog(b);
				  sprintdrop.changeEtat();
				  ////System.out.println(sprintdrop);
				  ////System.out.println(b);
				  service.updateBacklogItem(b);
				  service.updateSprint(sprintdrop);
			  }
		  }
		  else {
			  Sprint sprintdrag = getSprintFromlistSprint(getIdSprint((event.getDragId())));
			  Sprint sprintdrop = getSprintFromlistSprint(getIdSprint((event.getDropId())));
			  ////System.out.println(sprintdrag);
			  ////System.out.println(sprintdrop);
			  if(b!=null) {
				  b.setSprint(sprintdrop.getId());
				  sprintdrag.removeBacklog(b);
				  sprintdrop.addBacklog(b);
				  sprintdrag.changeEtat();
				  sprintdrop.changeEtat();
				  service.updateSprint(sprintdrag);
				  service.updateSprint(sprintdrop);
				  service.updateBacklogItem(b);
			  } 
		  }
		  //System.out.println(b);
		//  updateSprints();
		  //refresh=true;
	  }
	  
	  public BacklogItem getBacklogFromlistbacklog(BacklogItem b) {
			for (BacklogItem back : listbacklog) {
				if (back.getId().equals(b.getId())) return back;
			}	  
		  return null;
	  }
	  
	  
	  public Sprint getSprintFromlistSprint(String num) {
		  int index = Integer.parseInt(num);
		  return listSprint.get(index);
		//  return null;
	  }
	  
	  
	  public String getIdSprint(String source) {
			////System.out.println(source);

			if (source.contains("iditembacklog")){
				return "none";
			}
			StringTokenizer st = new StringTokenizer(source, ":");
			st.nextToken();
			st.nextToken();
			String  res = st.nextToken();
			
			return res;
			
			
			//return source;
		}
	  
	 public void updateSprints() {
				listbacklog=service.SelectBacklogsByProject(currentProject.getId());
				listSprint=service.SelectSprintsByProject(currentProject.getId());
			 for (Sprint sprint : listSprint) {
				 sprint.changeEtat();
				 service.updateSprint(sprint);
				 //System.out.println(sprint);
			}
		
		 }
		 
			
	  public List<BacklogItem> listbb() {
				////System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ listbb");
				//listbacklog=service.SelectBacklogsByProject(currentProject.getId());
				//listSprint=service.SelectSprintsByProject(currentProject.getId());
				return listbacklog;
	  }
	  
		public void addSprint() {
			sprintitem.setProject(currentProject.getId());
			listSprint.add(sprintitem);
			service.InsertSprint(sprintitem);
			//listbacklog=service.SelectBacklogsByProject(currentProject.getId());
			//listSprint=service.SelectSprintsByProject(currentProject.getId());
			sprintitem = new Sprint();
			
			
		}
		
		
		public void uploadSprint(Sprint s) {
			//System.out.println("SprintBean.uploadSprint()");
			s.changeEtat();
			service.updateSprint(s);
			//listbacklog=service.SelectBacklogsByProject(currentProject.getId());
			//listSprint=service.SelectSprintsByProject(currentProject.getId());
			/*
			listSprint.add(sprintitem);
			sprintitem = new Sprint();
			
			*/
		}
		
		
//-----------------------------------------------------------------------Sprint-------------------------------------------------------
		
//-----------------------------------------------------------------------TaskBoard-------------------------------------------------------
		

		  public void onTaskDroppedinprogress(DragDropEvent event) {
			  //System.out.println("TaskBoardBean.onTaskDroppedinprogress()");
		      Task item = (Task) event.getData();
		      EventTaskDrop(item,"inprogress");
		      currentSprint.changeEtat();
		      //System.out.println("event.getDragId(): " + getIdSprint2(event.getDragId()));
		      //System.out.println("(event.getDropId(): "+ getIdSprint2(event.getDropId()));
		  }
		  
		  public void onTaskDroppedtodo(DragDropEvent event) {
			  //System.out.println("TaskBoardBean.onTaskDroppedtodo()");
			  Task item = (Task) event.getData();
			  EventTaskDrop(item,"todo");
			  currentSprint.changeEtat();
		      //System.out.println("event.getDragId(): " + getIdSprint2(event.getDragId()));
		      //System.out.println("(event.getDropId(): "+ getIdSprint2(event.getDropId()));
		  }
		  
		  public void onTaskDroppeddone(DragDropEvent event) {
			  //System.out.println("TaskBoardBean.onTaskDroppeddone()");
			  Task item = (Task) event.getData();
			  EventTaskDrop(item,"done");
			  currentSprint.changeEtat();
		      //System.out.println("event.getDragId(): " + getIdSprint2(event.getDragId()));
		      //System.out.println("(event.getDropId(): "+ getIdSprint2(event.getDropId()));
		  }
		  

			public boolean Trouver(List<Task> list,String item) {
				for (Task s : list) {
					if(s.getId().equals(item)) return true;
				}
				return false;
			}
		  
			public Task TrouverTask(List<Task> list,String item) {
				for (Task s : list) {
					if(s.getId().equals(item)) return s;
				}
				return null;
			}

			public void EventTaskDrop(Task t,String etat) {
				
				for (BacklogItem b : listbacklog) {
					if(b.getId().equals(t.getIdBacklog())) {
					      Task tres = TrouverTask(b.getTasks(), t.getId());
					      if(tres!=null) {
					    	  tres.setEtat(etat);
					    	  service.updateTask(tres);
					    	  EtatBacklog(b);
					      }
					}
				}
				
				for (BacklogItem b : listbacklog) {
					//System.out.println(b);
				}
			}
			
			

			private String getIdSprint2(String source) {
				
				StringTokenizer st = new StringTokenizer(source, ":");
				String  res = st.nextToken();
				while(res.startsWith("id")==false) {
					res = st.nextToken();
				}
				
				return res;
			}

		
	
		
//-----------------------------------------------------------------------TaskBoard-------------------------------------------------------
			
//-----------------------------------------------------------------------Projet-------------------------------------------------------
			
			
			public void addProject() {
				listproject.add(project);
				service.InsertProject(project);
				listproject= service.SelectAllProjects();
				project = new Project();
			}
			
			
			public void uploadProject(Project p) {
				service.updateProject(p);
				listproject= service.SelectAllProjects();
				//System.out.println("ProjectBean.uploadProject()");
			}
			
			public void changeproject(ValueChangeEvent e) {
				ppcurrent = e.getNewValue().toString();
				System.out.println("ProjectBean.changeproject()");
				System.out.println("Projet : " + ppcurrent);
			}
		
			
//-----------------------------------------------------------------------Projet-------------------------------------------------------
			
			
//-----------------------------------------------------------------------people-------------------------------------------------------
			public void addMembre() {
				listMembre.add(membre);
				service.InsertMembre(membre);
				listMembre = service.SelectAllMembre();
				membre=new Membre();
			}	
						
//-----------------------------------------------------------------------people-------------------------------------------------------
			
//-----------------------------------------------------------------------Chat-------------------------------------------------------
			
			
			public void newMessage() {

				listchat.add(currentMessage);
				service.InsertChat(currentMessage);
				listchat=service.SelectAllChat();
				currentMessage = new Chat(Lastid(), currentMembre);
			}
			
			public int Lastid() {
				return listchat.size() +1;
			}	
//-----------------------------------------------------------------------Chat-------------------------------------------------------
			
//-----------------------------------------------------------------------Sprintchart-------------------------------------------------------
			
		    private BarChartModel initBarModel() {
		    	if(currentSprint!=null) {
		    		
		    	
		    	String idcurrentsprint = currentSprint.getId();
		    	currentSprint=service.SelectBySprintid(idcurrentsprint);
		    	
		        BarChartModel model = new BarChartModel();

		        ChartSeries todo = new ChartSeries();
		        
		        todo.setLabel("Todo");
		        for (BacklogItem b : currentSprint.getListbacklog()) {
		        	todo.set(b.getNom(), TotalAsk(b, "todo"));
				}

		 
		        ChartSeries inprogress = new ChartSeries();
		        inprogress.setLabel("In progress");
		        for (BacklogItem b : currentSprint.getListbacklog()) {
		        	inprogress.set(b.getNom(), TotalAsk(b, "inprogress"));
				}
		        
		        
		        ChartSeries done = new ChartSeries();
		        done.setLabel("Done");
		        for (BacklogItem b : currentSprint.getListbacklog()) {
		        	done.set(b.getNom(), TotalAsk(b, "done"));
				}
		 
		        model.addSeries(todo);
		        model.addSeries(inprogress);
		        model.addSeries(done);

		        return model;
		    	}
		    	else return new BarChartModel();
		    }
		 
		    private void createBarModels() {
		        createBarModel();
		    
		    }
		    
			 
		    private void createBarModel() {
		    	//System.out.println("SprintCharBean.createBarModel()");
		    	if(currentSprint!=null) {
		        barModel = initBarModel();
		        barModel.setSeriesColors("02bcd4, 2196f3 , 4caf50");
		        barModel.setTitle(currentSprint.getNom());
		        barModel.setLegendPosition("ne");
		 
		        Axis xAxis = barModel.getAxis(AxisType.X);
		        xAxis.setLabel("Backlog item");
		 
		        Axis yAxis = barModel.getAxis(AxisType.Y);
		        yAxis.setLabel("% Tasks");
		        yAxis.setMin(0);
		        yAxis.setMax(100);
		        
		    	}
		    	else barModel = initBarModel();
		    }
		    
		    

		    
		    private int TotalAsk(BacklogItem b,String etat) {
		    	int res = 0;
		    	int current =0;
		    	for (Task t : b.getTasks()) {
					if(t.getEtat().equals(etat)) current++;
				}
		    	int size=b.getTasks().size();
				if(size>0) {
					float x = (float)current/size;
					float y = x*100;
					res=(int)y;
				}
		    	return res;
		    }
		    
			public void ChangeCurrentSprint2(ValueChangeEvent e) {
				String current = e.getNewValue().toString();
				currentSprint = service.SelectBySprintid(current);
				createBarModel();
		}
			
			
//-----------------------------------------------------------------------Sprintchart-------------------------------------------------------
			
//-----------------------------------------------------------------------Burndown-------------------------------------------------------	
		    private void createDateModel() {
		    	if(currentSprint!=null) {
		    		
	
		    	String idcurrentsprint = currentSprint.getId();
		    	currentSprint=service.SelectBySprintid(idcurrentsprint);
		    	
		        dateModel = new LineChartModel();
		        LineChartSeries series1 = new LineChartSeries();
		        series1.setLabel("Series 1");
		        
		        Burndowninit burndowninit = service.SelectBurnDownInitBySprint(currentSprint.getId());
		        
		        
		       // Burndowninit burndowninit = new Burndowninit("Sprint4559718", "07-05-2019", "23-05-2019", 30);
		        series1.set(burndowninit.getDatedebut(), burndowninit.getTotaleheure());
		        //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ listburndown");
		        //System.out.println("burndowninit.getDatedebut() : "+burndowninit.getDatedebut()+"burndowninit.getDatefin : "+burndowninit.getDatefin()+"burndowninit.getTotaleheure() : "+burndowninit.getTotaleheure());
		        
		       
		        List<Burndown> listburndown = service.SelectBurnDownBySprint(currentSprint.getId());
		        //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ listburndown");
		        for (Burndown burndown : listburndown) {
		        	series1.set(burndown.getDatemodif(), burndown.getResteafaire());
		        	//System.out.println("burndown.getDatemodif() : "+burndown.getDatemodif()+"burndown.getResteafaire() : "+burndown.getResteafaire());
				}
		 
		 
		       // series1.set("18-05-2019", 20);
		        
		        
		        LineChartSeries series2 = new LineChartSeries();
		        series2.setLabel("Series 2");
		 
		        series2.set(burndowninit.getDatedebut(), burndowninit.getTotaleheure());
		        series2.set(burndowninit.getDatefin(), 0);
		        
		 
		        dateModel.addSeries(series1);
		        dateModel.addSeries(series2);
		 
		        dateModel.setTitle("Zoom for Detail");
		        dateModel.setShowPointLabels(true);
		        dateModel.setZoom(true);
		        
		        dateModel.setSeriesColors("4caf50,ff0000");
		        
		        dateModel.getAxis(AxisType.Y).setLabel("heure");
		        
		        dateModel.getAxis(AxisType.Y).setMin(0);
		        dateModel.getAxis(AxisType.Y).setMax(burndowninit.getTotaleheure());
		        
		        DateAxis axis = new DateAxis("Dates");
		       
		        axis.setMin(burndowninit.getDatedebut());
		        axis.setMax(burndowninit.getDatefin());
		        axis.setTickFormat("%d-%m-%Y");
		 
		        dateModel.getAxes().put(AxisType.X, axis);
		      
		        
		    	}
		    	else dateModel = new LineChartModel();
		        
		    }

			public void ChangeCurrentSprint3(ValueChangeEvent e) {
				String current = e.getNewValue().toString();
				currentSprint = service.SelectBySprintid(current);
				createDateModel();
		}
			
			//-----------------------------------------------------------------------Burndown-------------------------------------------------------	


			//-----------------------------------------------------------------------Getter and setter-------------------------------------------------------
			public EsiscrumService getService() {
				return service;
			}



			public void setService(EsiscrumService service) {
				this.service = service;
			}



			public ManagedBeanContext<Session> getContext() {
				return context;
			}



			public void setContext(ManagedBeanContext<Session> context) {
				this.context = context;
			}



			public Membre getCurrentMembre() {
				return currentMembre;
			}



			public void setCurrentMembre(Membre currentMembre) {
				this.currentMembre = currentMembre;
			}



			public Project getCurrentProject() {
				return currentProject;
			}



			public void setCurrentProject(Project currentProject) {
				this.currentProject = currentProject;
			}



			public Sprint getCurrentSprint() {
				return currentSprint;
			}



			public void setCurrentSprint(Sprint currentSprint) {
				this.currentSprint = currentSprint;
			}



			public Chat getCurrentMessage() {
				return currentMessage;
			}



			public void setCurrentMessage(Chat currentMessage) {
				this.currentMessage = currentMessage;
			}



			public List<BacklogItem> getListbacklog() {
				return listbacklog;
			}



			public void setListbacklog(List<BacklogItem> listbacklog) {
				this.listbacklog = listbacklog;
			}



			public List<Task> getListTasks() {
				return listTasks;
			}



			public void setListTasks(List<Task> listTasks) {
				this.listTasks = listTasks;
			}



			public List<Membre> getListMembre() {
				return listMembre;
			}



			public void setListMembre(List<Membre> listMembre) {
				this.listMembre = listMembre;
			}



			public List<Sprint> getListSprint() {
				return listSprint;
			}



			public void setListSprint(List<Sprint> listSprint) {
				this.listSprint = listSprint;
			}



			public List<Project> getListproject() {
				return listproject;
			}



			public void setListproject(List<Project> listproject) {
				this.listproject = listproject;
			}



			public List<Chat> getListchat() {
				return listchat;
			}



			public void setListchat(List<Chat> listchat) {
				this.listchat = listchat;
			}



			public BacklogItem getBacklogItem() {
				return backlogItem;
			}



			public void setBacklogItem(BacklogItem backlogItem) {
				this.backlogItem = backlogItem;
			}



			public Task getTask() {
				return task;
			}



			public void setTask(Task task) {
				this.task = task;
			}



			public Sprint getSprintitem() {
				return sprintitem;
			}



			public void setSprintitem(Sprint sprintitem) {
				this.sprintitem = sprintitem;
			}



			public Project getProject() {
				return project;
			}



			public void setProject(Project project) {
				this.project = project;
			}



			public Membre getMembre() {
				return membre;
			}



			public void setMembre(Membre membre) {
				this.membre = membre;
			}



			public BarChartModel getBarModel() {
				return barModel;
			}



			public void setBarModel(BarChartModel barModel) {
				this.barModel = barModel;
			}



			public LineChartModel getDateModel() {
				return dateModel;
			}



			public void setDateModel(LineChartModel dateModel) {
				this.dateModel = dateModel;
			}



			public String getUtilisateur() {
				return utilisateur;
			}



			public void setUtilisateur(String utilisateur) {
				this.utilisateur = utilisateur;
			}



			public String getPpcurrent() {
				return ppcurrent;
			}



			public void setPpcurrent(String ppcurrent) {
				this.ppcurrent = ppcurrent;
			}



			public String getOldIdBacklog() {
				return oldIdBacklog;
			}



			public void setOldIdBacklog(String oldIdBacklog) {
				this.oldIdBacklog = oldIdBacklog;
			}



			public File getFile() {
				return file;
			}



			public void setFile(File file) {
				this.file = file;
			}



			public int getIdChat() {
				return idChat;
			}



			public void setIdChat(int idChat) {
				this.idChat = idChat;
			}



			public String getMessagez() {
				return messagez;
			}



			public void setMessagez(String messagez) {
				this.messagez = messagez;
			}



			public String getMm() {
				return mm;
			}



			public void setMm(String mm) {
				this.mm = mm;
			}



			public boolean isListRechercheIsEmpty() {
				return listRechercheIsEmpty;
			}



			public void setListRechercheIsEmpty(boolean listRechercheIsEmpty) {
				this.listRechercheIsEmpty = listRechercheIsEmpty;
			}
			public List<Integer> getListdifficulte() {
				return listdifficulte;
			}
			public void setListdifficulte(List<Integer> listdifficulte) {
				this.listdifficulte = listdifficulte;
			}
			
			

			
			
			
//-----------------------------------------------------------------------Getter and setter-------------------------------------------------------
			
			
			
			
}
