/**
 * 
 */
package com.controlgymfit.scgf.modelo.entidad;

import java.io.Serializable;
import java.util.Date;

import com.controlgymfit.scgf.modelo.generic.GenericModel;
import com.controlgymfit.scgf.util.enums.EstadoFactura;
//import com.controlgym.scgf.util.enums.TipoFactura;

/**
 * Modelo del repositorio de las Facturas de un Cliente.
 * 
 * @author JQ
 * @version 1.0
 *
 */
public class Factura extends GenericModel<Factura> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private EstadoFactura estado;
	private Integer idEmpresa;
	private Cliente cliente;
	
	private String uuid;
	private String detalle;
	private Date fechaEmision;
	private Double subTotal;
	private String ivaAplicable;
	private Double valorIva;
	private Double montoPagado;
	private Double total;	
	
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
	public EstadoFactura getEstado() {
		return estado;
	}
	public void setEstado(EstadoFactura estado) {
		this.estado = estado;
	}
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public Date getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public Double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}
	public String getIvaAplicable() {
		return ivaAplicable;
	}
	public void setIvaAplicable(String ivaAplicable) {
		this.ivaAplicable = ivaAplicable;
	}
	public Double getValorIva() {
		return valorIva;
	}
	public void setValorIva(Double valorIva) {
		this.valorIva = valorIva;
	}
	public Double getMontoPagado() {
		return montoPagado;
	}
	public void setMontoPagado(Double montoPagado) {
		this.montoPagado = montoPagado;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
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
