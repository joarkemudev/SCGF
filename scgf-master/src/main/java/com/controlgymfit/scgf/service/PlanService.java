package com.controlgymfit.scgf.service;

import java.util.List;

import com.controlgymfit.scgf.modelo.entidad.Plan;
import com.controlgymfit.scgf.service.generic.CrudService;

public interface PlanService extends CrudService<Plan>{
	
	/**
	 * Regresa lista de Planes.
	 */
	public List<Plan> getPlanes(Integer id);
	
	/**
	 * Regresa lista de Planes segun la empresa.
	 */
	public List<Plan> getPlanesByEmp(Integer idEmpresa);
	
	/**
	 * Obtiene un Plan segun la el socio.
	 * @return Plan.
	 */
	public Plan getPlanByIdCliente(Integer idCliente);
}
