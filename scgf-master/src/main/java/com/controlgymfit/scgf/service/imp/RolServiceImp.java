package com.controlgymfit.scgf.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import com.controlgymfit.scgf.dao.RolDao;
import com.controlgymfit.scgf.modelo.entidad.Rol;
import com.controlgymfit.scgf.modelo.generic.IOperations;
import com.controlgymfit.scgf.service.RolService;
import com.controlgymfit.scgf.service.generic.AbstractService;
import com.controlgymfit.scgf.service.generic.DuplicateValidator;

@Service
public class RolServiceImp extends AbstractService<Rol> implements RolService {

	@Autowired
	private RolDao dao;

	public RolServiceImp() {
		super();
	}

	@Override
	protected IOperations<Rol> getDao() {
		return dao;
	}

	@Override
	public void validateBeforeCreate(Rol entity, BindingResult result) {
		validateDuplicates(entity, result);
	}

	@Override
	public void validateBeforeUpdate(Rol entity, BindingResult result) {
		validateDuplicates(entity, result);
	}

	@Override
	public void validateBeforeDelete(Rol entity, BindingResult result) {
		validateDuplicates(entity, result);
	}
	
	/**
	 * Valida existencia de duplicados antes de guardar. Se basa en los campos: rol
	 * 
	 * @param entity
	 * @param result
	 */
	private void validateDuplicates(Rol entity, BindingResult result){
		ArrayList<String[]> props = new ArrayList<String[]>();
		props.add(new String[]{"nombre"});
		DuplicateValidator<Rol> validator = new DuplicateValidator<Rol>(
				Rol.class, this, props);
		ValidationUtils.invokeValidator(validator, entity, result);		
	}

	@Override
	public List<Rol> getAllOrdenados() {
		return dao.getAllOrdenados();
	}
	
}
