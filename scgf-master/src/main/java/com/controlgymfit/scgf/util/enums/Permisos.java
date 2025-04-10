package com.controlgymfit.scgf.util.enums;

import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * Permisos relacionados a acciones dentro del sistema. Para uso en la configuraciÓn de roles y asignaciÓn del usuario
 * 
 * @author Sistemas
 * @version 1.0
 *
 */
public enum Permisos {
	
	CAMBIA_CLAVE("Cambia Contraseña"),

	CONSULTA_ROLES("Consulta de Roles"),
	ALTA_ROLES("Alta de Roles"),
	EDITA_ROLES("Edición de Roles"),
	ELIMINA_ROLES("Eliminación de Roles"),
	
	CONSULTA_PLANES("Consulta de Planes"),
	ALTA_PLANES("Alta de Planes"),
	EDITA_PLANES("Edición de Planes"),
	ELIMINA_PLANES("Eliminación de Planes"),
	
	CONSULTA_CLIENTES("Consulta de Clientes"),
	ALTA_CLIENTES("Alta de Clientes"),
	EDITA_CLIENTES("Edición de Clientes"),
	ELIMINA_CLIENTES("Eliminación de Clientes"),
	
	CONSULTA_CORREOS("Consulta de Correos"),
	ALTA_CORREOS("Alta de Correos"),
	EDITA_CORREOS("Edición de Correos"),
	ELIMINA_CORREOS("Eliminación de Correos"),
	
	CONSULTA_USUARIOS("Consulta de Usuarios"),
	ALTA_USUARIOS("Alta de Usuarios"),
	EDITA_USUARIOS("Edición de Usuarios"),
	ELIMINA_USUARIOS("Eliminación de Usuarios"),
	
	CONSULTA_EMPRESAS("Consulta de Empresas"),
	ALTA_EMPRESAS("Alta de Empresas"),
	EDITA_EMPRESAS("Edición de Empresas"),
	ELIMINA_EMPRESAS("Eliminación de Empresas"),
	
	CONSULTA_MEMBRESIAS("Consulta de Membresías"),
	ALTA_MEMBRESIAS("Alta de Membresías"),
	EDITA_MEMBRESIAS("Edición de Membresías"),
	ELIMINA_MEMBRESIAS("Eliminación de Membresías"),
	
	CONSULTA_CLASES("Consulta de Clases"),
	ALTA_CLASES("Alta de Clases"),
	EDITA_CLASES("Edición de Clases"),
	ELIMINA_CLASES("Eliminación de Clases"),
	
	ELIMINA_INSCRITOS("Eliminación de Inscritos a Clases"),
	
	CONSULTA_PAGOS("Consulta de Pagos"),
	ALTA_PAGOS("Alta de Pagos"),
	EDITA_PAGOS("Edición de Pagos"),
	ELIMINA_PAGOS("Eliminación de Pagos"),
	
	;
	
	private String label;		
	
	private Permisos(String label){
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Obtiene permisos
	 * @return Regresa la lista de permisos disponibles.
	 */
	public static LinkedHashMap<String, String> getPermisos(){
		
		LinkedHashMap<String, String> tipopermisos = new LinkedHashMap<String, String>();
		// permisos estáticos (de acuerdo a funcionalidades sistema)
		for (Permisos p : Permisos.values()) {
			tipopermisos.put(p.name(), p.getLabel());
		}
		//Ordenamos alfabéticamente.}
		 TreeMap<String, String> copy = new TreeMap<>(tipopermisos);
		 tipopermisos.clear();
		 tipopermisos.putAll(copy);
		 
		return tipopermisos;
	}
	
}
