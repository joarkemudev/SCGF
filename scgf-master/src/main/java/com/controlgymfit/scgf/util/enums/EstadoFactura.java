package com.controlgymfit.scgf.util.enums;
/**
 * Directores FTP del sistema.
 * @author JQ
 * @version 1.0
 *
 */
public enum EstadoFactura {

	//Permisos
	PENDIENTE("PENDIENTE"),
	ANULADA("ANULADA"),
	PAGADA("PAGADA");
	
	private String label;		
	
	private EstadoFactura(String label){
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	
}
