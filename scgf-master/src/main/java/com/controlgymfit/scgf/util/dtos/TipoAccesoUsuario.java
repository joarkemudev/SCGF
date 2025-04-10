package com.controlgymfit.scgf.util.dtos;

import java.util.ArrayList;
import java.util.Collection;

import com.controlgymfit.scgf.util.enums.AccesoUsuario;
/**
 * Proporciona información del usuario para aplicar filtros.
 * @author JQ
 */
public class TipoAccesoUsuario {

	private AccesoUsuario tipoAcceso;
	private Collection<String> clavesCC = new ArrayList<String>();
	private Integer idArea;
	
		
	public AccesoUsuario getTipoAcceso() {
		return tipoAcceso;
	}
	public void setTipoAcceso(AccesoUsuario tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}
	public Collection<String> getClavesCC() {
		return clavesCC;
	}
	public void setClavesCC(Collection<String> clavesCC) {
		this.clavesCC = clavesCC;
	}
	public Integer getIdArea() {
		return idArea;
	}
	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}
	
}
