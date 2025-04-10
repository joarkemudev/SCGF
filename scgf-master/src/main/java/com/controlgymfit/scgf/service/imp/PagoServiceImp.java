package com.controlgymfit.scgf.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import com.controlgymfit.scgf.dao.PagoDao;
import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.modelo.entidad.Pago;
import com.controlgymfit.scgf.modelo.generic.IOperations;
import com.controlgymfit.scgf.service.PagoService;
import com.controlgymfit.scgf.service.generic.AbstractService;
import com.controlgymfit.scgf.service.generic.DuplicateValidator;
import com.controlgymfit.scgf.controller.beans.BusquedaConsultaPagoForm;
import com.controlgymfit.scgf.controller.beans.BusquedaPagoForm;

@Service
public class PagoServiceImp extends AbstractService<Pago> implements PagoService {

	@Autowired
	private PagoDao dao;

	public PagoServiceImp() {
		super();
	}

	@Override
	protected IOperations<Pago> getDao() {
		return dao;
	}

	@Override
	public void validateBeforeCreate(Pago entity, BindingResult result) {
		validateDuplicates(entity, result);
	}

	@Override
	public void validateBeforeUpdate(Pago entity, BindingResult result) {
		validateDuplicates(entity, result);
	}

	@Override
	public void validateBeforeDelete(Pago entity, BindingResult result) {
		validateDuplicates(entity, result);
	}
	
	/**
	 * Valida existencia de duplicados antes de guardar. Se basa en los campos: rol
	 * @param entity
	 * @param result
	 */
	private void validateDuplicates(Pago entity, BindingResult result){
		ArrayList<String[]> props = new ArrayList<String[]>();
		props.add(new String[]{"id"});
		DuplicateValidator<Pago> validator = new DuplicateValidator<Pago>(
				Pago.class, this, props);
		ValidationUtils.invokeValidator(validator, entity, result);		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.controlgymfit.scgf.service.PagoService#getFechaPago(com.controlgymfit.scgf.controller.beans.BusquedaPagoForm)
	 */
	@Override
	public List<Pago> getPagosFechas(Integer idEmpresa, BusquedaPagoForm busqueda) {
		return dao.getPagosFechas(idEmpresa, busqueda);
	}

	/*
	 * (non-Javadoc)
	 * @see com.segurosthona.scg.service.FacturaService#getFacturasDeSolicitud(java.lang.Integer)
	 */
	@Override
	public List<Pago> getPagosDeCliente(Integer idSolicitud) {
		return dao.getPagosDeCliente(idSolicitud);
	}

	@Override
	public boolean eliminaPagosDeCliente(Integer idSolicitud) {
		return dao.eliminaPagosDeCliente(idSolicitud);
	}
	
	@Override
	public List<Pago> getPagoByEmp(Integer idEmpresa) {
		return dao.getPagoByEmp(idEmpresa);
	}
	
	@Override
	public List<Pago> getPagosConsulta(Empresa empresa, BusquedaConsultaPagoForm bp) {
		return dao.getPagosConsulta(empresa,bp);
	}

}
