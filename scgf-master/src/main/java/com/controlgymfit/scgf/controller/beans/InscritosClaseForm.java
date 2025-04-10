package com.controlgymfit.scgf.controller.beans;

import java.util.Date;

import com.controlgymfit.scgf.modelo.entidad.Cliente;
import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.modelo.entidad.Clase;
import com.controlgymfit.scgf.modelo.entidad.InscritosClase;
import com.controlgymfit.scgf.controller.beans.generic.GenericForm;


/**
  * Modelo del repositorio de Membresias
  * @author Joarkemu
  * @version 1.0
 */

public class InscritosClaseForm extends GenericForm<InscritosClaseForm, InscritosClase>{

	private Integer id;
    private Clase clase;
    
    private Empresa empresa;
    private Cliente cliente;
    
    private Date fechaInscripcion;
    private Boolean asistencia;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Clase getClase() {
		return clase;
	}
	public void setClase(Clase clase) {
		this.clase = clase;
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
	public Date getFechaInscripcion() {
		return fechaInscripcion;
	}
	public void setFechaInscripcion(Date fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}
	public Boolean getAsistencia() {
		return asistencia;
	}
	public void setAsistencia(Boolean asistencia) {
		this.asistencia = asistencia;
	}
	
	@Override
	public String toString() {
		return "InscritosClase [id=" + id + ", empresa=" + empresa + ", cliente=" + cliente + ", clase=" + clase 
				+ ", fechaInscripcion=" + fechaInscripcion + ", asistencia=" + asistencia + "]";

	}
	
}
