package com.controlgymfit.scgf.util.enums;

import java.util.LinkedHashMap;

/**
 * Generos relacionados a acciones dentro del sistema. Para uso en la configuraciÓn de clientes
 * @author Sistemas
 * @version 1.0
 *
 */
public enum TipoGenero {
	
	MC("Masculino"),
	FM("Femenino"),
	OT("Otro"),
	
	;
	
	private String nombre;
	
	private TipoGenero(String nombre){
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
		for (TipoGenero p : TipoGenero.values()) {
			tipos.put(p.name(),  p.getNombre());
		}
		return tipos;
	}
	
}
