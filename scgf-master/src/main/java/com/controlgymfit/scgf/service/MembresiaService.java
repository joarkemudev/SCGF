package com.controlgymfit.scgf.service;

import java.util.List;

import com.controlgymfit.scgf.modelo.entidad.Membresia;
import com.controlgymfit.scgf.service.generic.CrudService;
import com.controlgymfit.scgf.util.enums.EstadoCatalogo;

public interface MembresiaService extends CrudService<Membresia>{
	
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
	 * Regresa lista de Membresias segun la empresa.
	 */
	public List<Membresia> tieneMembresiaInactiva(Integer idEmpresa, EstadoCatalogo estado);
	
	/**
	 * Verifica si el cliente ya tiene una membresía activa en la base de datos..
	 */
	public boolean tieneMembresiaActiva(Integer idCliente, EstadoCatalogo estado);
}
