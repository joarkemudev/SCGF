package com.controlgymfit.scgf.modelo.entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.controlgymfit.scgf.util.enums.EstadoCatalogo;
import com.controlgymfit.scgf.modelo.generic.GenericModel;

/**
  * Modelo del repositorio de Membresias
  * @author Joarkemu
  * @version 1.0
 */

public class Clase extends GenericModel<Clase> implements Serializable{
	
	private static final long serialVersionUID = 1L; 
	private Integer id;
    private String nombre;
    private String descripcion;
    private EstadoCatalogo estado;
    private Empresa empresa;
    private Usuario usuario;
    
    private Date fechaHoraInicio;
    private Date fechaHoraFin;
    private Integer capacidadMaxima;

    @ManyToMany(mappedBy = "clasesInscritas")
    private Set<Cliente> clientesInscritos = new HashSet<>();
	
	private String usuarioAlta;
	private Date fechaAlta;
	private String usuarioModifica;
	private Date fechaModifica;
	
    @OneToMany(mappedBy = "clase")
    private Set<InscritosClase> inscripciones = new HashSet<>();
    
    // Método para verificar disponibilidad
    public boolean tieneCuposDisponibles() {
        return inscripciones.size() < capacidadMaxima;
    }
    
	public Set<InscritosClase> getInscripciones() {
		return inscripciones;
	}

	public void setInscripciones(Set<InscritosClase> inscripciones) {
		this.inscripciones = inscripciones;
	}

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
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	
	@Override
	public String toString() {
		return "Clase [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", fechaHoraInicio=" + fechaHoraInicio 
				+ ", fechaHoraFin=" + fechaHoraFin + ", capacidadMaxima=" + capacidadMaxima + "]";

	}
	
}
