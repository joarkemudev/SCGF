package com.controlgymfit.scgf.dao;

import java.util.Date;
import java.util.List;

import com.controlgymfit.scgf.modelo.entidad.InscritosClase;
import com.controlgymfit.scgf.modelo.generic.IOperations;

/**
 * Acceso a datos del catálogo Clases, implementa inteface genérica CRUD 
 * @author JQ
 * @version 1.0
 */
public interface InscritosClaseDao extends IOperations<InscritosClase>{
	/**
	* Regresa lista de Membresias.
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
