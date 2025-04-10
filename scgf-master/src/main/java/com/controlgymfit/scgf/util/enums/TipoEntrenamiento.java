package com.controlgymfit.scgf.util.enums;

import java.util.LinkedHashMap;

/**
 * Tipos de Entrenaminetos relacionados a acciones dentro del sistema. Para uso en la configuraciÓn de planes
 * @author Sistemas
 * @version 1.0
 *
 */
public enum TipoEntrenamiento {
	
	ENTRE_MENSUAL("ENTRENAMIENTO MENSUAL"),
	ENTRE_SEMANAL("ENTRENAMIENTO SEMANAL"),
	ENTRE_DIARIO("ENTRENAMIENTO DIARIO"),
	
	;
	
	private String nombre;
	
	private TipoEntrenamiento(String nombre){
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
		for (TipoEntrenamiento p : TipoEntrenamiento.values()) {
			tipos.put(p.name(),  p.getNombre());
		}
		return tipos;
	}
	
}
