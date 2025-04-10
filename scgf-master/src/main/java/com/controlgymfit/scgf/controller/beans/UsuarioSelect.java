package com.controlgymfit.scgf.controller.beans;

public class UsuarioSelect{

	private Integer id;
	private String nombreAcceso;
	private String nombre;
	
	public UsuarioSelect(Integer id, String nombreAcceso, String nombre) {
		super();
		this.id = id;
		this.nombreAcceso = nombreAcceso;
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public UsuarioSelect(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombreAcceso() {
		return nombreAcceso;
	}

	public void setNombreAcceso(String nombreAcceso) {
		this.nombreAcceso = nombreAcceso;
	}

	@Override
	public String toString() {
		return "UsuarioSelect [id=" + id + ", nombreAcceso=" + nombreAcceso
				+ ", nombre=" + nombre + "]";
	}
	
}
