package com.controlgymfit.scgf.service;

import java.util.Date;
import java.util.List;

import com.controlgymfit.scgf.modelo.entidad.InscritosClase;
import com.controlgymfit.scgf.service.generic.CrudService;

public interface InscritosClaseService extends CrudService<InscritosClase>{
	
	/**
	 * Regresa lista de Clases.
	 */
	public InscritosClase getInscritosClases(Integer idInscritosClase);
	
	/**
	* Regresa verdadero o falso si existe enscripcion.
	*/
	boolean existeInscripcion(Integer claseId, Integer clienteId);
	
	/**
	* Regresa verdadero o falso si existe solapamiento.
	*/
	boolean existeSolapamientoHorarioSocio(Integer idCliente, Date fechaHoraInicio, Date fechaHoraFin);
	
	/**
	 * Obtiene una lista de Clientes Inscritos.
	 * @return Lista de Clientes Inscritos.
	 */
	public List<InscritosClase> getInscritosDeClase(Integer idClase);
}
