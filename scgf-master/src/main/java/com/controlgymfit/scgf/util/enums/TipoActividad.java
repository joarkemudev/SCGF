package com.controlgymfit.scgf.util.enums;

import java.util.LinkedHashMap;

/**
 * Parentescos de Beneficiarios
 * @author MFS - Sistemas
 * @version 1.0
 *
 */
public enum TipoActividad {
	
	//Para el monitoreo de la solicitud de anticipo.
	
	BIENVENIDA_SOCIO("Mensaje de bienvenida al nuevo socio !"),
	ACT_FECHA_MEMB("Mensaje actualización de la membresía !"),
	FECHA_FIN_VEN("Mensaje fecha fin vencida, no está vigente la membresía !"),
	FECHA_FIN_REV_3("Mensaje aviso 3 días naturales antes de la fecha fin de la membresía !");
	
	
	private String nombre;
	
	private TipoActividad(String nombre){
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Obtiene permisos
	 * @return Regresa la lista de TipoProveedor
	 */
	public static LinkedHashMap<String, String> getTipos(){
		
		LinkedHashMap<String, String> tipos = new LinkedHashMap<String, String>();
		// permisos estáticos (de acuerdo a funcionalidades sistema)
		for (TipoActividad p : TipoActividad.values()) {
			tipos.put(p.name(),  p.getNombre());
		}
		return tipos;
	}
	
}
