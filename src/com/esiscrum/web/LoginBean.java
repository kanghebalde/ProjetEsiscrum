package com.esiscrum.web;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;


import com.esiscrum.business.EsiscrumService;
import com.esiscrum.business.EsiscrumServiceMysqls;
import com.esiscrum.models.Membre;
import com.esiscrum.models.Session;

@ManagedBean
@ApplicationScoped
public class LoginBean {

	public String username;
	public String pwd;
	public EsiscrumService service ;
	public List<Membre> listMembre;
	
	private ManagedBeanContext<Session> context;
	
	public LoginBean() {
		System.out.println("LoginBean.LoginBean()");
		//service = new EsiscrumServiceDefault();
		//listMembre = service.SelectAllMembre();
		username="";
		pwd="";
	   context = new ManagedBeanContext<Session>();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public EsiscrumService getService() {
		return service;
	}

	public void setService(EsiscrumService service) {
		this.service = service;
	}

	public List<Membre> getListMembre() {
		return listMembre;
	}

	public void setListMembre(List<Membre> listMembre) {
		this.listMembre = listMembre;
	}
	
	public String loginss() {
		/*
		for (Membre membre : listMembre) {
			Session ss=this.context.getManagedBean("loginsession");
			if(membre.getUsername().equals(ss.username) && membre.getPwd().equals(ss.pwd)) {
				System.out.println("ss.username" + ss.username + "pwd" +ss.pwd);
				return "project.xhtml";
			}
		}
		return "login.xhtml";
		
	*/
		
		System.out.println("LoginBean.login() lufluglgluglglugliugliugluig");
		Session ss=this.context.getManagedBean("loginsession");
		System.out.println("ss.username   " + ss.username + "     pwd      " +ss.pwd);
		return "project";
		
	}
	
	public void mamadou() {
		System.out.println("LoginBean.mamadou()");
		System.out.println("username" + username + "pwd" +pwd);
		
	}


}
