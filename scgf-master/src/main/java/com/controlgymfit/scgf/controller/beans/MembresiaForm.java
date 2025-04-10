package com.controlgymfit.scgf.controller.beans;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.controlgymfit.scgf.controller.beans.generic.GenericForm;
import com.controlgymfit.scgf.modelo.entidad.Cliente;
import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.modelo.entidad.Membresia;
import com.controlgymfit.scgf.modelo.entidad.Plan;
import com.controlgymfit.scgf.util.enums.EstadoCatalogo;

/**
  * Modelo del repositorio de Membresias
  * @author JQ
  * @version 1.0 public class EmpresaForm extends GenericForm<EmpresaForm, Empresa>
 */

public class MembresiaForm extends GenericForm<MembresiaForm, Membresia> {
	
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
}
