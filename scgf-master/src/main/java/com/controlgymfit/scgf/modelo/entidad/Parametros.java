package com.controlgymfit.scgf.modelo.entidad;

import java.io.Serializable;
import java.util.Date;

import com.controlgymfit.scgf.modelo.generic.GenericModel;
import com.controlgymfit.scgf.util.enums.AccesoArchivos;

/**
 * Modelo de los parámetros del sistema.
 * 
 * @author Sistemas
 * @version 1.0
 *
 */
public class Parametros extends GenericModel<Parametros> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private AccesoArchivos tipoAcceso;
	private String urlArchivos;
	
	private String usuarioModifica;
	private Date fechaModifica;
	
	//[MFS 20211202] Variable que determina el bloqueo del sistema.
	private Boolean bloqueo;

	public Boolean getBloqueo() {
		return bloqueo;
	}

	public void setBloqueo(Boolean bloqueo) {
		this.bloqueo = bloqueo;
	}

	public Parametros(){
	
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

	@Override
	public String toString() {
		return "Parametros [id=" + id + ", tipoAcceso=" + tipoAcceso
				+ ", urlArchivos=" + urlArchivos + ", usuarioModifica="
				+ usuarioModifica + ", fechaModifica=" + fechaModifica + "]";
	}
	
}
