package com.controlgymfit.scgf.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import com.controlgymfit.scgf.dao.MembresiaDao;
import com.controlgymfit.scgf.modelo.entidad.Membresia;
import com.controlgymfit.scgf.modelo.generic.IOperations;
import com.controlgymfit.scgf.service.MembresiaService;
import com.controlgymfit.scgf.service.generic.AbstractService;
import com.controlgymfit.scgf.service.generic.DuplicateValidator;
import com.controlgymfit.scgf.util.enums.EstadoCatalogo;

@Service
public class MembresiaServiceImp extends AbstractService<Membresia> implements MembresiaService{
	
	@Autowired
	private MembresiaDao dao;
	
	public MembresiaServiceImp() {
		super();
	}
	
	@Override
	protected IOperations<Membresia> getDao() {
		return dao;
	}
	
	public void validateBeforeCreate(Membresia entity, BindingResult result) {
		validateDuplicates(entity, result);
	}
	
	public void validateBeforeUpdate(Membresia entity, BindingResult result) {
		validateDuplicates(entity, result);
	}
	
	public void validateBeforeDelete(Membresia entity, BindingResult result) {
		validateDuplicates(entity, result);
	}
	
	/**
	 * Valida existencia de duplicados antes de guardar. Se basa en los campos: nombre
	 * @param entity
	 * @param result
	 */
	private void validateDuplicates(Membresia entity, BindingResult result) {
		ArrayList<String[]> props = new ArrayList<String[]>();
		props.add(new String[]{"membresia"});
		DuplicateValidator<Membresia> validator = new DuplicateValidator<Membresia>(Membresia.class, this, props);
		ValidationUtils.invokeValidator(validator, entity, result);
	}
	
	@Override
	public Membresia getMembresias(Integer idMembresia) {
		return null;
	}
	
	@Override
	public Membresia getMembresiaByIdCliente(Integer idCliente) {
		return dao.getMembresiaByIdCliente(idCliente);
	}
	
	@Override
	public List<Membresia> getMembresiasActivas(EstadoCatalogo estado) {
		return dao.getMembresiasActivas(estado);
	}
	
	@Override
	public List<Membresia> getMembByEmp(Integer idEmpresa) {
		return dao.getMembByEmp(idEmpresa);
	}
	
	@Override
	public List<Membresia> tieneMembresiaInactiva(Integer idEmpresa, EstadoCatalogo estado) {
		return dao.existsByEmpresaIdAndEstado(idEmpresa, estado);
	}
	
	@Override
    public boolean tieneMembresiaActiva(Integer IdCliente, EstadoCatalogo estado) {
        return dao.existsByClienteIdAndEstado(IdCliente, estado);
    }
}
