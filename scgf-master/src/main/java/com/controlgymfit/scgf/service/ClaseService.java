package com.controlgymfit.scgf.service;

import java.util.Date;
import java.util.List;

import com.controlgymfit.scgf.modelo.entidad.Clase;
import com.controlgymfit.scgf.service.generic.CrudService;

public interface ClaseService extends CrudService<Clase>{
	
	/**
	 * Regresa lista de Clases.
	 */
	public Clase getClases(Integer idClase);
	
	/**
	 * Obtiene una lista de Clases por idCliente.
	 * @return	Lista de  Clases por idCliente.
	 */
	public Clase getClaseByIdUsuario(Integer idUsuario);
	
	/**
	 * Regresa lista de Clases segun la empresa.
	 */
	public List<Clase> getClaseByEmp(Integer idEmpresa);
	
	/**
	 * Regresa lista de Clases activas segun la empresa.
	 */
	public List<Clase> getClasesDisponiblesByEmp(Integer idEmpresa);
	
//	/**
//	 * Regresa lista de Clases.
//	 */
    void inscribirSocio(Integer claseId, Integer socioId);
    
	/**
	* Regresa verdadero o falso si existe solapamiento.
	*/
	boolean existeSolapamientoHorario(Integer idEmpresa, Date fechaHoraInicio, Date fechaHoraFin);
}
