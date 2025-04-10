package com.controlgymfit.scgf.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import com.controlgymfit.scgf.dao.InscritosClaseDao;
import com.controlgymfit.scgf.modelo.entidad.InscritosClase;
import com.controlgymfit.scgf.modelo.generic.IOperations;
import com.controlgymfit.scgf.service.InscritosClaseService;
import com.controlgymfit.scgf.service.generic.AbstractService;
import com.controlgymfit.scgf.service.generic.DuplicateValidator;

@Service
public class InscritosClaseServiceImp extends AbstractService<InscritosClase> implements InscritosClaseService{
	
	@Autowired
	private InscritosClaseDao dao;
	
	public InscritosClaseServiceImp() {
		super();
	}
	
	@Override
	protected IOperations<InscritosClase> getDao() {
		return dao;
	}
	
	public void validateBeforeCreate(InscritosClase entity, BindingResult result) {
		validateDuplicates(entity, result);
	}
	
	public void validateBeforeUpdate(InscritosClase entity, BindingResult result) {
		validateDuplicates(entity, result);
	}
	
	public void validateBeforeDelete(InscritosClase entity, BindingResult result) {
		validateDuplicates(entity, result);
	}
	
	/**
	 * Valida existencia de duplicados antes de guardar. Se basa en los campos: nombre
	 * @param entity
	 * @param result
	 */
	private void validateDuplicates(InscritosClase entity, BindingResult result) {
		ArrayList<String[]> props = new ArrayList<String[]>();
		props.add(new String[]{"idCliente"});
		DuplicateValidator<InscritosClase> validator = new DuplicateValidator<InscritosClase>(InscritosClase.class, this, props);
		ValidationUtils.invokeValidator(validator, entity, result);
	}
	
	@Override
	public InscritosClase getInscritosClases(Integer idInscritosClase) {
		return null;
	}
	
	@Override
    public boolean existeInscripcion(Integer claseId, Integer clienteId) {
        return dao.existeInscripcion(claseId, clienteId);
    }
	
	@Override
    public boolean existeSolapamientoHorarioSocio(Integer idCliente, Date fechaHoraInicio, Date fechaHoraFin) {
        return dao.existeSolapamientoHorarioSocio(idCliente, fechaHoraInicio, fechaHoraFin);
    }
	
	@Override
	public List<InscritosClase> getInscritosDeClase(Integer idClase){
		return dao.getInscritosDeClase(idClase);
	}
	
}
