package com.controlgymfit.scgf.controller.beans;

import java.util.Date;

import com.controlgymfit.scgf.controller.beans.generic.GenericForm;
import com.controlgymfit.scgf.modelo.entidad.Parametros;
import com.controlgymfit.scgf.util.enums.AccesoArchivos;

public class ParametrosForm extends GenericForm<ParametrosForm, Parametros> {
	private Integer id;
	private AccesoArchivos tipoAcceso;
	private String urlArchivos;
	
	private String usuarioModifica;
	private Date fechaModifica;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public AccesoArchivos getTipoAcceso() {
		return tipoAcceso;
	}
	
	public void setTipoAcceso(AccesoArchivos tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}
	
	public String getUrlArchivos() {
		return urlArchivos;
	}
	
	public void setUrlArchivos(String urlArchivos) {
		this.urlArchivos = urlArchivos;
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
	
}

