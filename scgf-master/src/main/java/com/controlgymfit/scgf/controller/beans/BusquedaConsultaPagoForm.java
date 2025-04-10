package com.controlgymfit.scgf.controller.beans;

import java.util.Date;

import com.controlgymfit.scgf.util.enums.MetodoPago;
import com.controlgymfit.scgf.util.enums.TipoDocumento;

/**
 * Campos de búsqueda de la Solicitud.
 * @author JQ
 *
 */
public class BusquedaConsultaPagoForm{
	
	private Integer idEmpresa;
	private TipoDocumento tipoDocumento;	
	private MetodoPago metodoPago;
	private Integer idCliente;
	private Date fi;
	private Date ff;

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public MetodoPago getMetodoPago() {
		return metodoPago;
	}
	
	public void setMetodoPago(MetodoPago metodoPago) {
		this.metodoPago = metodoPago;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public Date getFi() {
		return fi;
	}

	public void setFi(Date fi) {
		this.fi = fi;
	}

	public Date getFf() {
		return ff;
	}

	public void setFf(Date ff) {
		this.ff = ff;
	}

	@Override
	public String toString() {
		return "BusquedaConsultaPagosForm [tipoDocumento=" + tipoDocumento+ "metodoPago=" + metodoPago+ "idEmpresa=" + idEmpresa
				+ ", idCliente=" + idCliente  + ", fi=" + fi + ", ff=" + ff + "]";
	}
	
}
