package com.controlgymfit.scgf.modelo.entidad;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.controlgymfit.scgf.util.enums.EstadoCatalogo;
import com.controlgymfit.scgf.modelo.generic.GenericModel;

/**
  * Modelo del repositorio de Membresias
  * @author Joarkemu
  * @version 1.0
 */

public class Membresia extends GenericModel<Membresia> implements Serializable{
	
	private static final long serialVersionUID = 1L; 
	private Integer id;
	private Empresa empresa;
	private Cliente cliente;
	private Plan plan;
	@Enumerated(EnumType.ORDINAL)
	private EstadoCatalogo estado;
	
	private Date fechaInicio;
	private Date fechaFin;
	
	private String usuarioAlta;
	private Date fechaAlta;
	private String usuarioModifica;
	private Date fechaModifica;
	
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
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Plan getPlan() {
		return plan;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	public EstadoCatalogo getEstado() {
		return estado;
	}
	public void setEstado(EstadoCatalogo estado) {
		this.estado = estado;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
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
	
	@Override
	public String toString() {
		return "Membresia [id=" + id + ", Empresa=" + empresa + ", Cliente=" + cliente + ", Plan=" + plan + ", estado=" + estado 
				+ ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + "]";
	}
	
}
