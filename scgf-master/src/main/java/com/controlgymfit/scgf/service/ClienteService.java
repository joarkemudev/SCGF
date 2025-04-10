package com.controlgymfit.scgf.service;

import java.util.List;

import com.controlgymfit.scgf.modelo.entidad.Cliente;
import com.controlgymfit.scgf.modelo.entidad.Usuario;
import com.controlgymfit.scgf.service.generic.*;
import com.controlgymfit.scgf.controller.beans.Archivo;
import com.controlgymfit.scgf.controller.beans.Mensaje;

public interface ClienteService extends CrudService<Cliente>{
	
	/**
	 * Regresa lista de clientes.
	 */
	public Cliente getClientes(Integer idCliente);
	
	/**
	 * Regresa lista de Clientes segun la empresa.
	 */
	public List<Cliente> getClientesByEmp(Integer idEmpresa);
	
	/**
	 * Obtiene al cliente de acuerdo al correo electrónico
	 * @param nombreAcceso	del cliente
	 * @return	cliente 
	 */
	//public Cliente getClientePorNumIde(String N);
	
	/**
	 * Ejecuta la carga masiva de clientes a través de un archivo de Excel
	 * @param cm	formulario que contiene los datos de importación
	 * @return	Mensaje informativo de las acciones.
	 */
	public Mensaje cargaMasivaClientes(Archivo cm);

	/**
	 * True o False si el cliente existe.
	 */
	boolean existe(String numIdentificacion);
	
	/**
	 * Obtiene al cliente de acuerdo al correo electrónico.
	 */
	public Cliente getIdEmpresaCliente(String correoElectronico);
}
