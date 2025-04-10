package com.controlgymfit.scgf.dao;

import java.util.Date;
import java.util.List;

import com.controlgymfit.scgf.modelo.entidad.Clase;
import com.controlgymfit.scgf.modelo.generic.IOperations;

/**
 * Acceso a datos del catálogo Clases, implementa inteface genérica CRUD 
 * @author JQ
 * @version 1.0
 */
public interface ClaseDao extends IOperations<Clase>{
	/**
	* Regresa lista de Membresias.
	*/
	public Clase getClases(Integer idClase);
	
	/**
	 * Obtiene una lista de Clases por idCliente.
	 * @return	Lista de Clases por idCliente.
	 */
	public Clase getClaseByIdUsuario(Integer idUsuario);
	
	/**
	 * Regresa lista de Clases segun la empresa.
	 */
	public List<Clase> getClaseByEmp(Integer idEmpresa);
	
	/**
	 * Regresa lista de Clases activas segun la empresa.
	 */
	public List<Clase> getClasesDisponiblesByEmp(Integer empresaId);
	
	boolean existeSolapamientoHorario(Integer idEmpresa , Date horaInicio, Date horaFin);
}
