package com.controlgymfit.scgf.dao;

import java.util.List;
import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.modelo.generic.IOperations;


/**
 * Acceso a datos del catálogo de Empresas, implementa inteface genérica CRUD 
 * @author JQ
 * @version 1.0
 */
public interface EmpresaDao extends IOperations<Empresa>{

	/**
	 * Regresa lista de Empresas por Id.
	 */
	public Empresa getEmpresas(Integer idEmpresa);
	
	/**
	 * Regresa lista de Empresas ordenadas.
	 */
	public List<Empresa> getAllOrdenados();
}
