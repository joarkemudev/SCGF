package com.controlgymfit.scgf.service.imp;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import com.controlgymfit.scgf.dao.ParametrosDao;
import com.controlgymfit.scgf.modelo.entidad.Parametros;
import com.controlgymfit.scgf.modelo.generic.IOperations;
import com.controlgymfit.scgf.service.ParametrosService;
import com.controlgymfit.scgf.service.generic.AbstractService;
import com.controlgymfit.scgf.service.generic.DuplicateValidator;

@Service
public class ParametrosServiceImp extends AbstractService<Parametros> implements ParametrosService {

	@Autowired
	private ParametrosDao dao;

	public ParametrosServiceImp() {
		super();
	}

	@Override
	protected IOperations<Parametros> getDao() {
		return dao;
	}

	@Override
	public void validateBeforeCreate(Parametros entity, BindingResult result) {
		validateDuplicates(entity, result);
	}

	@Override
	public void validateBeforeUpdate(Parametros entity, BindingResult result) {
		validateDuplicates(entity, result);
	}

	@Override
	public void validateBeforeDelete(Parametros entity, BindingResult result) {
		validateDuplicates(entity, result);
	}
	
	/**
	 * Valida existencia de duplicados antes de guardar. Se basa en los campos: rol
	 * 
	 * @param entity
	 * @param result
	 */
	private void validateDuplicates(Parametros entity, BindingResult result){
		ArrayList<String[]> props = new ArrayList<String[]>();
		props.add(new String[]{"id"});
		DuplicateValidator<Parametros> validator = new DuplicateValidator<Parametros>(
				Parametros.class, this, props);
		ValidationUtils.invokeValidator(validator, entity, result);		
	}

	
}
