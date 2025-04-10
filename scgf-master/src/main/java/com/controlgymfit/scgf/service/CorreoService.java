package com.controlgymfit.scgf.service;

import com.controlgymfit.scgf.modelo.entidad.Correo;
import com.controlgymfit.scgf.service.generic.*;
import com.controlgymfit.scgf.util.enums.TipoActividad;

public interface CorreoService extends CrudService<Correo>{
	
	/**
	 * Regresa un correo de acuerdo al tipo de Actividad señalada.
	 * @param tipo	Tipo de Actividad
	 * @return Correo al que corresponde segun la actividad
	 */
	public Correo getCorreoPorActividad(TipoActividad tipo);

}
