package com.controlgymfit.scgf.controller.beans;

import java.util.Date;

import com.controlgymfit.scgf.controller.beans.generic.GenericForm;
import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.modelo.entidad.Plan;
import com.controlgymfit.scgf.util.enums.EstadoCatalogo;
import com.controlgymfit.scgf.util.enums.TipoEntrenamiento;

public class PlanForm extends GenericForm<PlanForm, Plan> {
	
	private Integer id;
	private Empresa empresa;
	private String nombre;
	private String descripcion;
	private TipoEntrenamiento duracion;
	private Integer duracionCantidad;
	
	private Double precio;
	private Boolean accesoClases;
	private Integer invitadosPermitidos;
	private EstadoCatalogo estado;
	
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
	public TipoEntrenamiento getDuracion() {
		return duracion;
	}
	public void setDuracion(TipoEntrenamiento duracion) {
		this.duracion = duracion;
	}
	public Integer getDuracionCantidad() {
		return duracionCantidad;
	}
	public void setDuracionCantidad(Integer duracionCantidad) {
		this.duracionCantidad = duracionCantidad;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Boolean getAccesoClases() {
		return accesoClases;
	}
	public void setAccesoClases(Boolean accesoClases) {
		this.accesoClases = accesoClases;
	}

	public Integer getInvitadosPermitidos() {
		return invitadosPermitidos;
	}
	public void setInvitadosPermitidos(Integer invitadosPermitidos) {
		this.invitadosPermitidos = invitadosPermitidos;
	}
	public EstadoCatalogo getEstado() {
		return estado;
	}
	public void setEstado(EstadoCatalogo estado) {
		this.estado = estado;
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
