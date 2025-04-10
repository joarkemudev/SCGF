package com.controlgymfit.scgf.service;
import java.util.List;

import com.controlgymfit.scgf.controller.beans.BusquedaPagoForm;
import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.modelo.entidad.Pago;
import com.controlgymfit.scgf.service.generic.CrudService;
import com.controlgymfit.scgf.controller.beans.BusquedaConsultaPagoForm;

public interface PagoService extends CrudService<Pago>{

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
	 * @param idCliente	identificador único del Cliente
	 * @return	Lista de Facturas requeridas.
	 */
	public List<Pago> getPagosDeCliente(Integer idCliente);
	

	/**
	 * Elimina las facturas asociadas a un Cliente.
	 * @param idCliente	Identificador único del Cliente
	 * @return	true si fue eliminada, false de lo contrario.
	 */
	public boolean eliminaPagosDeCliente(Integer idCliente);
	
	/**
	 * Regresa lista de Pagos segun la empresa.
	 */
	public List<Pago> getPagoByEmp(Integer idEmpresa);
	
}
