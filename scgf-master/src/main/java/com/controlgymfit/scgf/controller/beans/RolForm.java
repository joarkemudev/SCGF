package com.controlgymfit.scgf.controller.beans;

import java.util.HashSet;
import java.util.Set;

import com.controlgymfit.scgf.controller.beans.generic.GenericForm;
import com.controlgymfit.scgf.modelo.entidad.Rol;

public class RolForm extends GenericForm<RolForm, Rol>{

	private Integer id;
	private String nombre;
	private Set<String> permisos = new HashSet<String>(0);
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Set<String> getPermisos() {
		return permisos;
	}
	public void setPermisos(Set<String> permisos) {
		this.permisos = permisos;
	}
	
	
}
