package com.controlgymfit.scgf.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import com.controlgymfit.scgf.controller.beans.UsuarioSelect;
import com.controlgymfit.scgf.dao.UsuarioDao;
import com.controlgymfit.scgf.modelo.entidad.Usuario;
import com.controlgymfit.scgf.modelo.generic.IOperations;
import com.controlgymfit.scgf.service.UsuarioService;
import com.controlgymfit.scgf.service.generic.AbstractService;
import com.controlgymfit.scgf.service.generic.DuplicateValidator;

@Service
public class UsuarioServiceImp extends AbstractService<Usuario> implements UsuarioService {

	@Autowired
	private UsuarioDao dao;

	public UsuarioServiceImp() {
		super();
	}

	@Override
	protected IOperations<Usuario> getDao() {
		return dao;
	}

	@Override
	public void validateBeforeCreate(Usuario entity, BindingResult result) {
		validateDuplicates(entity, result);
	}

	@Override
	public void validateBeforeUpdate(Usuario entity, BindingResult result) {
		validateDuplicates(entity, result);
	}

	@Override
	public void validateBeforeDelete(Usuario entity, BindingResult result) {
		validateDuplicates(entity, result);
	}
	
	/**
	 * Valida existencia de duplicados antes de guardar. Se basa en los campos: rol
	 * 
	 * @param entity
	 * @param result
	 */
	private void validateDuplicates(Usuario entity, BindingResult result){
		ArrayList<String[]> props = new ArrayList<String[]>();
		props.add(new String[]{"nombreAcceso"});
		DuplicateValidator<Usuario> validator = new DuplicateValidator<Usuario>(
				Usuario.class, this, props);
		ValidationUtils.invokeValidator(validator, entity, result);		
	}

	/*
	 * (non-Javadoc)
	 * @see com.controlgymfit.scgf.service.UsuarioService#getUsuario(java.lang.String)
	 */
	@Override
	public Usuario getUsuario(String correoElectronico) {
		return dao.getUsuario(correoElectronico);
	}

	/*
	 * (non-Javadoc)
	 * @see com.controlgymfit.scgf.service.UsuarioService#getUsuarioPorNombreAcceso(java.lang.String)
	 */
	@Override
	public Usuario getUsuarioPorNombreAcceso(String nombreAcceso) {
		return dao.getUsuarioPorNombreAcceso(nombreAcceso);
	}

	/*
	 * (non-Javadoc)
	 * @see com.controlgymfit.scgf.service.UsuarioService#getActivos()
	 */
	@Override
	public List<UsuarioSelect> getActivos() {
		return dao.getActivos();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.controlgymfit.scgf.service.UsuarioService#getActivos()
	 */
	@Override
	public List<UsuarioSelect> getUsuariosActivosByEmp(Integer idEmpresa) {
		return dao.getUsuariosActivosByEmp(idEmpresa);
	}

	@Override
	public List<Usuario> getUsuariosConPermiso(String permiso) {
		return dao.getUsuariosConPermiso(permiso);
	}
	
	@Override
	public Usuario getIdEmpesaUsuario(String userName) {
		return dao.getIdEmpesaUsuario(userName);
	}
}
