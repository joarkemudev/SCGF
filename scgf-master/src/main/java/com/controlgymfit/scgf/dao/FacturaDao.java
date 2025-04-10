package com.controlgymfit.scgf.dao;

import java.util.List;

import com.controlgymfit.scgf.controller.beans.BusquedaConsultaPagoForm;
import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.modelo.entidad.Factura;
import com.controlgymfit.scgf.modelo.generic.IOperations;
import com.controlgymfit.scgf.util.enums.EstadoFactura;

/**
 * Acceso a datos del catálogo de Facturas, implementa inteface genérica CRUD 
 * @author JG
 * @version 1.0
 */
public interface FacturaDao extends IOperations<Factura>{
	
	/**
	* Regresa lista de Facturas.
	*/
	public List<Factura> getFacturas(Integer id);
	
	/**
	 * Regresa lista de Facturas segun la empresa.
	 */
	public List<Factura> getFacturasByEmp(Integer idEmpresa);

	/**
	 * Verifica si la factura ya había sido cargada al sistema (Bd)
	 * @param uuid	identificador único de la factura fiscal
	 * @return	true si ya existe en el sistema, false de lo contrario.
	 */
	public Factura existeUuid();
	
	/**
	 * Obtiene una lista de facturas del Cliente
	 * @param idCliente identificador único del Cliente
	 * @return	Lista de Facturas requeridas.
	 */
	public List<Factura> getFacturasDeCliente(EstadoFactura estado, Integer idCliente);
	
	/**
	 * Obtiene una lista de facturas
	 * 
	 * @param bc	campos de busqueda. 
	 * @return	Lista de Facturas.
	 */
	public List<Factura> getFacturasConsulta(Integer idEmpresa, BusquedaConsultaPagoForm bp);

	/**
	 * Elimina las facturas asociadas a una solicitud de gasto.
	 * @param idSolicitud	Identificador único del Cliente
	 * @return	true si fue eliminada, false de lo contrario.
	 */
	public boolean eliminaFacturasDeCliente(Integer idCliente);
	
}
