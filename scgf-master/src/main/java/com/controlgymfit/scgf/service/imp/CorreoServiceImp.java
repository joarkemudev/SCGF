package com.controlgymfit.scgf.service.imp;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import com.controlgymfit.scgf.dao.CorreoDao;
import com.controlgymfit.scgf.modelo.entidad.Correo;
import com.controlgymfit.scgf.modelo.generic.IOperations;
import com.controlgymfit.scgf.service.CorreoService;
import com.controlgymfit.scgf.service.generic.AbstractService;
import com.controlgymfit.scgf.service.generic.DuplicateValidator;
import com.controlgymfit.scgf.util.enums.TipoActividad;

@Service
public class CorreoServiceImp extends AbstractService<Correo> implements CorreoService {

	@Autowired
	private CorreoDao dao;

	public CorreoServiceImp() {
		super();
	}

	@Override
	protected IOperations<Correo> getDao() {
		return dao;
	}

	@Override
	public void validateBeforeCreate(Correo entity, BindingResult result) {
		validateDuplicates(entity, result);
	}

	@Override
	public void validateBeforeUpdate(Correo entity, BindingResult result) {
		validateDuplicates(entity, result);
	}

	@Override
	public void validateBeforeDelete(Correo entity, BindingResult result) {
		validateDuplicates(entity, result);
	}
	
	/**
	 * Valida existencia de duplicados antes de guardar. Se basa en los campos: rol
	 * @param entity
	 * @param result
	 */
	private void validateDuplicates(Correo entity, BindingResult result){
		ArrayList<String[]> props = new ArrayList<String[]>();
		props.add(new String[]{"tipoActividad"});
		DuplicateValidator<Correo> validator = new DuplicateValidator<Correo>(
				Correo.class, this, props);
		ValidationUtils.invokeValidator(validator, entity, result);		
	}

	/*
	 * (non-Javadoc)
	 * @see com.segurosthona.scg.service.CorreoService#getCorreoPorActividad(com.segurosthona.scg.util.enums.TipoActividad)
	 */
	@Override
	public Correo getCorreoPorActividad(TipoActividad tipo) {
		return dao.getCorreoPorActividad(tipo);
	}

}
