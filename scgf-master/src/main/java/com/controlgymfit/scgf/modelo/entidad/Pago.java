/**
 * 
 */
package com.controlgymfit.scgf.modelo.entidad;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.controlgymfit.scgf.modelo.generic.GenericModel;
import com.controlgymfit.scgf.util.enums.MetodoPago;

/**
 * Modelo del repositorio de las Facturas de un Cliente.
 * 
 * @author JQ
 * @version 1.0
 *
 */
public class Pago extends GenericModel<Pago> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Integer idEmpresa;
	private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "idFactura", nullable = false)
    private Factura factura;
	private Double montoPagado;
	private Date fechaPago;
	private MetodoPago metodoPago;

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
	public Factura getFactura() {
		return factura;
	}
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	public Double getMontoPagado() {
		return montoPagado;
	}
	public void setMontoPagado(Double montoPagado) {
		this.montoPagado = montoPagado;
	}
	public Date getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	public MetodoPago getMetodoPago() {
		return metodoPago;
	}
	public void setMetodoPago(MetodoPago metodoPago) {
		this.metodoPago = metodoPago;
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
		return "Pago [id=" + id + ", idEmpresa=" + idEmpresa + ", cliente=" + cliente + ", factura=" + factura 
				+ ", montoPagado=" + montoPagado + ", fechaPago=" + fechaPago + ", metodoPago=" + metodoPago 
				+ ", usuarioAlta=" + usuarioAlta + ", fechaAlta=" + fechaAlta + ", usuarioModifica="
				+ usuarioModifica + ", fechaModifica=" + fechaModifica + "]";
	}
}
