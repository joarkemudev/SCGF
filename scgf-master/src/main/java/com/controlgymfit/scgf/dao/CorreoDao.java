package com.controlgymfit.scgf.dao;

import com.controlgymfit.scgf.modelo.entidad.Correo;
import com.controlgymfit.scgf.modelo.generic.IOperations;
import com.controlgymfit.scgf.util.enums.TipoActividad;

/**
 * Acceso a datos del catálogo de Correos, implementa inteface genérica CRUD 
 * @author JQ
 * @version 1.0
 */
public interface CorreoDao extends IOperations<Correo>{

	/**
	 * Regresa un correo de acuerdo al tipo de Actividad señalada.
	 * @param tipo	Tipo de Actividad
	 * @return Correo al que corresponde segun la actividad
	 */
	public Correo getCorreoPorActividad(TipoActividad tipo);
}
