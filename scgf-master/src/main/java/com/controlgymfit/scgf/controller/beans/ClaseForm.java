package com.controlgymfit.scgf.controller.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.controlgymfit.scgf.controller.beans.generic.GenericForm;
import com.controlgymfit.scgf.modelo.entidad.Cliente;
import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.modelo.entidad.Clase;
import com.controlgymfit.scgf.modelo.entidad.Usuario;
import com.controlgymfit.scgf.util.enums.EstadoCatalogo;


/**
  * Modelo del repositorio de Membresias
  * @author JQ
  * @version 1.0 public class EmpresaForm extends GenericForm<EmpresaForm, Empresa>
 */

public class ClaseForm extends GenericForm<ClaseForm, Clase> {
	
	private Integer id;
    private String nombre;
    private String descripcion;
    private EstadoCatalogo estado;
  
	private Date fechaHoraInicio;
    private Date fechaHoraFin;
    private Integer capacidadMaxima;

    private Empresa empresa;
    private Usuario usuario;

    @ManyToMany(mappedBy = "clasesInscritas")
    private Set<Cliente> clientesInscritos = new HashSet<>();
	
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
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public EstadoCatalogo getEstado() {
		return estado;
	}
	public void setEstado(EstadoCatalogo estado) {
		this.estado = estado;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Date getFechaHoraInicio() {
		return fechaHoraInicio;
	}
	public void setFechaHoraInicio(Date fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}
	public Date getFechaHoraFin() {
		return fechaHoraFin;
	}
	public void setFechaHoraFin(Date fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
	}
	public Integer getCapacidadMaxima() {
		return capacidadMaxima;
	}
	public void setCapacidadMaxima(Integer capacidadMaxima) {
		this.capacidadMaxima = capacidadMaxima;
	}
	public Set<Cliente> getClientesInscritos() {
		return clientesInscritos;
	}
	public void setClientesInscritos(Set<Cliente> clientesInscritos) {
		this.clientesInscritos = clientesInscritos;
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
