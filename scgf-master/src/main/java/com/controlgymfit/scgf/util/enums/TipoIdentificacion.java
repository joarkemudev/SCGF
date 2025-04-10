package com.controlgymfit.scgf.util.enums;

import java.util.LinkedHashMap;

/**
 * Tipos de identificacion relacionados a acciones dentro del sistema. Para uso en la configuraciÓn de clientes
 * 
 * @author Sistemas
 * @version 1.0
 *
 */
public enum TipoIdentificacion {
	
	CE("Cédula de Extranjeria"),
	CC("Cédula de Ciudadanía"),
	TI("Tarjeta de Identidad"),
	PP("Pasaporte"),
	
	;
	
	private String nombre;
	
	private TipoIdentificacion(String nombre){
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Obtiene tipos de identificacion
	 * @return Regresa la lista de tipos de identificacion disponibles.
	 */
	public static LinkedHashMap<String, String> getTipos(){
		
		LinkedHashMap<String, String> tipos = new LinkedHashMap<String, String>();
		// permisos estáticos (de acuerdo a funcionalidades sistema)
		for (TipoIdentificacion p : TipoIdentificacion.values()) {
			tipos.put(p.name(),  p.getNombre());
		}
		return tipos;
	}
	
}
