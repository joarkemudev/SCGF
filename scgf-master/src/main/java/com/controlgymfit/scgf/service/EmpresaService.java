package com.controlgymfit.scgf.service;

import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.service.generic.*;
import java.util.List;

public interface EmpresaService extends CrudService<Empresa>{
	
	/**
	 * Regresa lista de Empresas por id.
	 */
	public Empresa getEmpresas(Integer idEmpresa);
	
	/**
	 * Regresa lista de Empresas ordenadas.
	 */
	public List<Empresa> getAllOrdenados();

}
