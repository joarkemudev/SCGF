package com.controlgymfit.scgf.controller.beans;

import java.util.Date;

import com.controlgymfit.scgf.controller.beans.generic.GenericForm;
import com.controlgymfit.scgf.modelo.entidad.Correo;
import com.controlgymfit.scgf.util.enums.TipoActividad;

/**
 * Formulario de Correo electrónico.
 * @author JQ
 *
 */
public class CorreoForm extends GenericForm<CorreoForm, Correo>{

	private Integer id;
	
	private TipoActividad tipoActividad;
	private String asunto;
	private String titulo;
	private String contenido;	
		
	private String usuarioAlta;
	private Date fechaAlta;
	private String usuarioModifica;
	private Date fechaModifica;
	
	public Integer getId() {
		return id;
	}
	public TipoActividad getTipoActividad() {
		return tipoActividad;
	}
	public String getAsunto() {
		return asunto;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getContenido() {
		return contenido;
	}
	public String getUsuarioAlta() {
		return usuarioAlta;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public String getUsuarioModifica() {
		return usuarioModifica;
	}
	public Date getFechaModifica() {
		return fechaModifica;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setTipoActividad(TipoActividad tipoActividad) {
		this.tipoActividad = tipoActividad;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public void setUsuarioModifica(String usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
	}
	public void setFechaModifica(Date fechaModifica) {
		this.fechaModifica = fechaModifica;
	}
	@Override
	public String toString() {
		return "CorreoForm [id=" + id + ", tipoActividad=" + tipoActividad
				+ ", asunto=" + asunto + ", titulo=" + titulo + ", contenido="
				+ contenido + ", usuarioAlta=" + usuarioAlta + ", fechaAlta="
				+ fechaAlta + ", usuarioModifica=" + usuarioModifica
				+ ", fechaModifica=" + fechaModifica + "]";
	}
	
}
