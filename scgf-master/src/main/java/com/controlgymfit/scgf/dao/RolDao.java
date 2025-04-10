package com.controlgymfit.scgf.dao;

import java.util.List;

import com.controlgymfit.scgf.modelo.entidad.Rol;
import com.controlgymfit.scgf.modelo.generic.IOperations;

/**
 * Acceso a datos del catálogo de roles, implementa inteface genérica CRUD 
 * @author JQ
 * @version 1.0
 */
public interface RolDao extends IOperations<Rol>{
	public List<Rol> getAllOrdenados();
}
