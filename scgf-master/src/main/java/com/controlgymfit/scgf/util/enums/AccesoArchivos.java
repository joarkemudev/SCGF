package com.controlgymfit.scgf.util.enums;

import java.util.LinkedHashMap;

/**
 * Tipo de acceso a archivos del sistema.
 * 
 * @author MFS - Miguel Figueroa
 * @version 1.0
 *
 */
public enum AccesoArchivos {
	SIS("SISTEMA DE ARCHIVOS"), FTP("FTP");
	
	private String label;
	private AccesoArchivos(String label) {
		this.label = label;
	}
	
	
	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	/**
	 * Obtiene Todos los Acceso
	 * @return Regresa la lista de permisos disponibles.
	 */
	public static LinkedHashMap<String, String> getAccesos(){
		
		LinkedHashMap<String, String> datos = new LinkedHashMap<String, String>();
		// permisos estáticos (de acuerdo a funcionalidades sistema)
		for (AccesoArchivos p : AccesoArchivos.values()) {
			datos.put(p.name()+"", p.getLabel());
		}
		
		return datos;
	}
}
