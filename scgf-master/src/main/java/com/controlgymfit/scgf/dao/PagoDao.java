package com.controlgymfit.scgf.dao;

import java.util.List;

import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.modelo.entidad.Pago;
import com.controlgymfit.scgf.modelo.generic.IOperations;
import com.controlgymfit.scgf.controller.beans.BusquedaConsultaPagoForm;
import com.controlgymfit.scgf.controller.beans.BusquedaPagoForm;

/**
 * Acceso a datos del catálogo de Facturas, implementa inteface genérica CRUD 
 * @author JG
 * @version 1.0
 */
public interface PagoDao extends IOperations<Pago>{
	
	/**
	 * Obtiene Lista de Pagos de acuerdo a los parámetros de fecha
	 * @param 	f1	Fecha inicial
	 * @param	f2	Fecha final
	 * @return	Lista de Pagos. 
	 */
	public List<Pago> getPagosFechas(Integer idEmpresa, BusquedaPagoForm busqueda);
	
	/**
	 * Realiza la búsqueda de Pagos.
	 * @param bc	Filtros de búsqueda	
	 * @return	Lista de pagos.
	 */
	public List<Pago> getPagosConsulta(Empresa empresa, BusquedaConsultaPagoForm bp);
	
	/**
	 * Obtiene una lista de facturas del Cliente
	 * @param idCliente identificador único del Cliente
	 * @return	Lista de Facturas requeridas.
	 */
	public List<Pago> getPagosDeCliente(Integer idCliente);
	

	/**
	 * Elimina las facturas asociadas a una solicitud de gasto.
	 * @param idSolicitud	Identificador único del Cliente
	 * @return	true si fue eliminada, false de lo contrario.
	 */
	public boolean eliminaPagosDeCliente(Integer idCliente);
	
	/**
	 * Regresa lista de Membresias segun la empresa.
	 */
	public List<Pago> getPagoByEmp(Integer idEmpresa);
	
}
