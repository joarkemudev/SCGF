package com.controlgymfit.scgf.util.enums;

import java.util.LinkedHashMap;

/**
 * Tipo de Anexo
 * 
 * @author JQ
 * @version 1.0
 *
 */
public enum TipoDocumento {

	//Permisos
	PAGOS("PAGOS"), 
	FACTURAS("FACTURAS");
	
	private String label;		
	
	private TipoDocumento(String label){
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Obtiene lita de Tipos
	 * @return Regresa la lista.
	 */
	public static LinkedHashMap<String, String> getTipos(){
		
		LinkedHashMap<String, String> tipos = new LinkedHashMap<String, String>();
		// permisos estáticos (de acuerdo a funcionalidades sistema)
		for (TipoDocumento p : TipoDocumento.values()) {
			tipos.put(p.name(), p.getLabel());
		}
		
		return tipos;
	}
}
