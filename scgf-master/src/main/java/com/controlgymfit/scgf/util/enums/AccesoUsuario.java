package com.controlgymfit.scgf.util.enums;

import java.util.LinkedHashMap;

/**
 * Tipo de acceso a solicitudes y partidas del sistema.
 * 
 * @author Sistemas
 * @version 1.0
 *
 */
public enum AccesoUsuario {
	TODAS("TODAS LAS SOLICITUDES Y PARTIDAS PRESUPUESTALES"), SU_AREA("SOLO LAS DE SU AREA");
	
	private String label;
	private AccesoUsuario(String label) {
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
		for (AccesoUsuario p : AccesoUsuario.values()) {
			datos.put(p.name()+"", p.getLabel());
		}
		
		return datos;
	}
}
