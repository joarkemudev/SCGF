package com.controlgymfit.scgf.modelo.entidad;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.controlgymfit.scgf.modelo.generic.GenericModel;

/**
  * Modelo del repositorio de Membresias
  * @author Joarkemu
  * @version 1.0
 */

public class InscritosClase extends GenericModel<InscritosClase> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
    @ManyToOne
    @JoinColumn(name = "idClase", nullable = false)
    private Clase clase;
    
    private Empresa empresa;
    
    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;
    
    private Date fechaInscripcion;
    private Boolean asistencia;
    
    public InscritosClase() {
	}
	
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
