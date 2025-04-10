/**
 * 
 */
package com.controlgymfit.scgf.modelo.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.controlgymfit.scgf.modelo.generic.GenericModel;
import com.controlgymfit.scgf.util.enums.TipoActividad;

/**
 * Modelo del repositorio de los Correos disponibles 
 * @author Jorkemu
 * @version 1.0
 *
 */
public class Correo extends GenericModel<Correo> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private TipoActividad tipoActividad;
	private String asunto;
	private String titulo;
	private String contenido;	
		
	private String usuarioAlta;
	private Date fechaAlta;
	private String usuarioModifica;
	private Date fechaModifica;
	
	
	//Auxiliar para el envío del correo:
	private List<String> to = new ArrayList<String>();

	
	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public Integer getId() {
		return id;
	}
	
	public TipoActividad getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(TipoActividad tipoActividad) {
		this.tipoActividad = tipoActividad;
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
}
