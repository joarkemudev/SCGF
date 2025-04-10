package com.controlgymfit.scgf.dao;

import java.util.List;

import com.controlgymfit.scgf.modelo.entidad.Cliente;
import com.controlgymfit.scgf.modelo.generic.IOperations;

/**
 * Acceso a datos del catálogo de Cliente, implementa inteface genérica CRUD 
 * @author JQ
 * @version 1.0
 */
public interface ClienteDao extends IOperations<Cliente>{

	/**
	* Regresa lista de clientes.
	*/
	public Cliente getClientes(Integer idCliente);
	
	/**
	 * Regresa lista de Clientes segun la empresa.
	 */
	public List<Cliente> getClientesByEmp(Integer idEmpresa);
	
	/**
	 * Verifca si existe el cliente por ese numIdentificacion.
	 * @param numIdentificacion
	 * @return
	 */
	public boolean existe(String numIdentificacion);
	
	/**
	 * Obtiene el id de la empresa del correo Electronico.
	 */
	public Cliente getIdEmpresaCliente(String correoElectronico);
}
