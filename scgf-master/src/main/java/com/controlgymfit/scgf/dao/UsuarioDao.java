package com.controlgymfit.scgf.dao;

import java.util.List;

import com.controlgymfit.scgf.controller.beans.UsuarioSelect;
import com.controlgymfit.scgf.modelo.entidad.Usuario;
import com.controlgymfit.scgf.modelo.generic.IOperations;

/**
 * Acceso a datos del catálogo de usuarios, implementa inteface genérica CRUD 
 * @author JQ
 * @version 1.0
 */
public interface UsuarioDao extends IOperations<Usuario>{

	/**
	 * Obtiene al usuario de acuerdo al correo electrónico
	 * @param correoElectronico	correo electrónico del usuario
	 * @return	Usuario 
	 */
	public Usuario getUsuario(String correoElectronico);
	
	/**
	 * Obtiene al usuario de acuerdo al correo electrónico
	 * @param nombreAcceso	del usuario
	 * @return	Usuario 
	 */
	public Usuario getUsuarioPorNombreAcceso(String nombreAcceso);
	
	
	/**
	 * Obtiene una lista de Usuarios activos.
	 * @return	Lista de Usuarios Activos.
	 */
	public List<UsuarioSelect> getActivos();
	
	/**
	 * Regresa lista de Usuarios segun la empresa.
	 */
	public List<UsuarioSelect> getUsuariosActivosByEmp(Integer idEmpresa);
	
	/**
	 * Obtiene una lista de usuarios con un permiso determinado.
	 * @param permiso	permiso del usuario.
	 * @return	Lista de usuarios  
	 */
	public List<Usuario> getUsuariosConPermiso(String permiso);
	
	/**
	 * Obtiene el id de la empresa del Usuario.
	 */
	public Usuario getIdEmpesaUsuario(String userName);
}
