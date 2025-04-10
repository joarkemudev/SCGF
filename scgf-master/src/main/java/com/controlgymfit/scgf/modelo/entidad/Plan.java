package com.controlgymfit.scgf.modelo.entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.controlgymfit.scgf.modelo.generic.GenericModel;
import com.controlgymfit.scgf.util.enums.TipoEntrenamiento;
import com.controlgymfit.scgf.util.enums.EstadoCatalogo;

/**
 * Modelo del repositorio de planes 
 * @author Sistemas
 * @version 1.0
 */
public class Plan extends GenericModel<Plan> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	
	@OneToMany(mappedBy = "plan")
	private List<Cliente> clientes;
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
	
    @Transient
    private Integer cantidadSocios;

	public Integer getCantidadSocios() {
		return cantidadSocios;
	}
	public void setCantidadSocios(Integer cantidadSocios) {
		this.cantidadSocios = cantidadSocios;
	}
	public List<Cliente> getClientes() {
		return clientes;
	}
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
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
	
	@Override
	public String toString() {
		return "Plan [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", duracion=" + duracion +", empresa=" + empresa
				+ ", duracionCantidad=" + duracionCantidad + ", precio=" + precio + ", accesoClases=" + accesoClases  + ", estado=" + estado
				+ ", invitadosPermitidos=" + invitadosPermitidos + ", usuarioAlta=" + usuarioAlta + ", fechaAlta=" + fechaAlta + ", usuarioModifica="
				+ usuarioModifica + ", fechaModifica=" + fechaModifica + "]";
	}
}
