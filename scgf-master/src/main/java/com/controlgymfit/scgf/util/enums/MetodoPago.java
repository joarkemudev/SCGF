package com.controlgymfit.scgf.util.enums;

import java.util.LinkedHashMap;

/**
 * Directores FTP del sistema.
 * @author JQ
 * @version 1.0
 *
 */
public enum MetodoPago {

	//Permisos
	TARJETA("TARJETA"),
	EFECTIVO("EFECTIVO"),
	TRANSFERENCIA("TRANSFERENCIA"),
	;
	
	private String nombre;
	
	private MetodoPago(String nombre){
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
	public static LinkedHashMap<String, String> getMetodos(){
		
		LinkedHashMap<String, String> metodos = new LinkedHashMap<String, String>();
		// permisos estáticos (de acuerdo a funcionalidades sistema)
		for (MetodoPago p : MetodoPago.values()) {
			metodos.put(p.name(),  p.getNombre());
		}
		return metodos;
	}
	
}
