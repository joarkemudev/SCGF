package com.controlgymfit.scgf.controller.beans;

import java.util.Date;

import com.controlgymfit.scgf.controller.beans.generic.GenericForm;
import com.controlgymfit.scgf.modelo.entidad.Rol;
import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.modelo.entidad.Usuario;
import com.controlgymfit.scgf.util.enums.EstadoCatalogo;

public class UsuarioForm extends GenericForm<UsuarioForm, Usuario>{

	private Integer id;
	private Empresa empresa;
	private String nombreAcceso;
	private EstadoCatalogo estado;
	private String claveAcceso;
	private String claveAcceso2;
	private String nombre;
	private String correoElectronico;
	private Rol	rol;
	private Boolean cambioClave;
	private String usuarioAlta;
	private Date fechaAlta;
	private String usuarioModifica;
	private Date fechaModifica;
	
	public UsuarioForm(){}

	public UsuarioForm(EstadoCatalogo estado){
		this.estado = estado;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public String getNombreAcceso() {
		return nombreAcceso;
	}
	public void setNombreAcceso(String nombreAcceso) {
		this.nombreAcceso = nombreAcceso;
	}
	public EstadoCatalogo getEstado() {
		return estado;
	}
	public void setEstado(EstadoCatalogo estado) {
		this.estado = estado;
	}
	public String getClaveAcceso() {
		return claveAcceso;
	}
	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	public Boolean getCambioClave() {
		return cambioClave;
	}
	public void setCambioClave(Boolean cambioClave) {
		this.cambioClave = cambioClave;
	}
	public String getUsuarioAlta() {
		return usuarioAlta;
	}
	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getUsuarioModifica() {
		return usuarioModifica;
	}
	public void setUsuarioModifica(String usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
	}
	public Date getFechaModifica() {
		return fechaModifica;
	}
	public void setFechaModifica(Date fechaModifica) {
		this.fechaModifica = fechaModifica;
	}
	public String getClaveAcceso2() {
		return claveAcceso2;
	}
	public void setClaveAcceso2(String claveAcceso2) {
		this.claveAcceso2 = claveAcceso2;
	}

}
