package com.esiscrum.web;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;



public class ManagedBeanContext<T> {

	public ManagedBeanContext() {
		// TODO Auto-generated constructor stub
	}
	
	public T getManagedBean(String name) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ELResolver resolver = context.getELContext().getELResolver();
		T bean = (T)resolver.getValue(context.getELContext(), null, name);
		return bean;
	}

}
