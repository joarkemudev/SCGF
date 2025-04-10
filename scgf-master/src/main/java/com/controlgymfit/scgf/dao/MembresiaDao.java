package com.controlgymfit.scgf.dao;

import java.util.List;

import com.controlgymfit.scgf.modelo.entidad.Membresia;
import com.controlgymfit.scgf.modelo.generic.IOperations;
import com.controlgymfit.scgf.util.enums.EstadoCatalogo;

/**
 * Acceso a datos del catálogo planes, implementa inteface genérica CRUD 
 * @author JQ
 * @version 1.0
 */
public interface MembresiaDao extends IOperations<Membresia>{
	/**
	* Regresa lista de Membresias.
	*/
	public Membresia getMembresias(Integer idMembresia);
	
	/**
	 * Obtiene una lista de Membresias por idCliente.
	 * @return	Lista de  Membresias por idCliente.
	 */
	public Membresia getMembresiaByIdCliente(Integer idCliente);
	
	/**
	 * Obtiene una lista de membresias en algun estado.
	 * @return
	 */
	public List<Membresia> getMembresiasActivas(EstadoCatalogo estado);
	
	/**
	 * Regresa lista de Membresias segun la empresa.
	 */
	public List<Membresia> getMembByEmp(Integer idEmpresa);
	
	/**
	 * Verifica si el cliente ya tiene una membresía activa en la base de datos..
	 */
	public List<Membresia> existsByEmpresaIdAndEstado(Integer idEmpresa, EstadoCatalogo estado);
	
	/**
	 * Verifica si el cliente ya tiene una membresía activa en la base de datos..
	 */
	boolean existsByClienteIdAndEstado(Integer IdCliente, EstadoCatalogo estado);
}
