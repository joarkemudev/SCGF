package com.controlgymfit.scgf.dao;

import java.util.List;

import com.controlgymfit.scgf.modelo.entidad.Plan;
import com.controlgymfit.scgf.modelo.generic.IOperations;

/**
 * Acceso a datos del catálogo planes, implementa inteface genérica CRUD 
 * @author JQ
 * @version 1.0
 */
public interface PlanDao extends IOperations<Plan>{
	/**
	* Regresa lista de planes.
	*/
	public List<Plan> getPlanes(Integer id);
	
	/**
	 * Regresa lista de Planes segun la empresa.
	 */
	public List<Plan> getPlanesByEmp(Integer idEmpresa);
	
	/**
	 * Obtiene un Cumulo segun la solicitud de gasto.
	 * @return Lista de cuentas.
	 */
	public Plan getPlanByIdCliente(Integer idCliente);

}
