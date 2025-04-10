package com.controlgymfit.scgf.controller.beans;

import java.util.Date;

import com.controlgymfit.scgf.controller.beans.generic.GenericForm;
import com.controlgymfit.scgf.modelo.entidad.Usuario;

/**
 * Campos de búsqueda de datos del pago.
 * @author JQ
 */
public class BusquedaPagoForm extends GenericForm<BusquedaPagoForm, Usuario>{
	private String tipo;
	private Date f1;
	private Date f2;
	
	public BusquedaPagoForm(){
		f1 = new Date();
		f2 = new Date();
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getF1() {
		return f1;
	}

	public Date getF2() {
		return f2;
	}

	public void setF1(Date f1) {
		this.f1 = f1;
	}

	public void setF2(Date f2) {
		this.f2 = f2;
	}

	@Override
	public String toString() {
		return "BusquedaPagoForm [f1=" + f1 + ", f2=" + f2 + ", tipo=" + tipo + "]";
	}
	
}
