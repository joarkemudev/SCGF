package com.controlgymfit.scgf.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import com.controlgymfit.scgf.dao.PlanDao;
import com.controlgymfit.scgf.modelo.entidad.Plan;
import com.controlgymfit.scgf.modelo.generic.IOperations;
import com.controlgymfit.scgf.service.PlanService;
import com.controlgymfit.scgf.service.generic.AbstractService;
import com.controlgymfit.scgf.service.generic.DuplicateValidator;

@Service
public class PlanServiceImp extends AbstractService<Plan> implements PlanService{
	
	@Autowired
	private PlanDao dao;
	
	public PlanServiceImp() {
		super();
	}
	
	@Override
	protected IOperations<Plan> getDao() {
		return dao;
	}
	
	public void validateBeforeCreate(Plan entity, BindingResult result) {
		validateDuplicates(entity, result);
	}
	
	public void validateBeforeUpdate(Plan entity, BindingResult result) {
		validateDuplicates(entity, result);
	}
	
	public void validateBeforeDelete(Plan entity, BindingResult result) {
		validateDuplicates(entity, result);
	}
	
	/**
	 * Valida existencia de duplicados antes de guardar. Se basa en los campos: nombre
	 * @param entity
	 * @param result
	 */
	private void validateDuplicates(Plan entity, BindingResult result) {
		ArrayList<String[]> props = new ArrayList<String[]>();
		props.add(new String[]{"nombre"});
		DuplicateValidator<Plan> validator = new DuplicateValidator<Plan>(Plan.class, this, props);
		ValidationUtils.invokeValidator(validator, entity, result);
	}
	
	@Override
	public List<Plan> getPlanes(Integer id) {
		return dao.getPlanes(id);
	}

	@Override
	public List<Plan> getPlanesByEmp(Integer idEmpresa) {
		return dao.getPlanesByEmp(idEmpresa);
	}
	
	@Override
	public Plan getPlanByIdCliente(Integer idCliente){
		
		return dao.getPlanByIdCliente(idCliente);
	}
}
