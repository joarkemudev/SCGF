package com.controlgymfit.scgf.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import com.controlgymfit.scgf.dao.FacturaDao;
import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.modelo.entidad.Factura;
import com.controlgymfit.scgf.modelo.generic.IOperations;
import com.controlgymfit.scgf.service.FacturaService;
import com.controlgymfit.scgf.service.generic.AbstractService;
import com.controlgymfit.scgf.service.generic.DuplicateValidator;
import com.controlgymfit.scgf.util.enums.EstadoFactura;
import com.controlgymfit.scgf.controller.beans.BusquedaConsultaPagoForm;

@Service
public class FacturaServiceImp extends AbstractService<Factura> implements FacturaService {

	@Autowired
	private FacturaDao dao;

	public FacturaServiceImp() {
		super();
	}

	@Override
	protected IOperations<Factura> getDao() {
		return dao;
	}

	@Override
	public void validateBeforeCreate(Factura entity, BindingResult result) {
		validateDuplicates(entity, result);
	}

	@Override
	public void validateBeforeUpdate(Factura entity, BindingResult result) {
		validateDuplicates(entity, result);
	}

	@Override
	public void validateBeforeDelete(Factura entity, BindingResult result) {
		validateDuplicates(entity, result);
	}
	
	/**
	 * Valida existencia de duplicados antes de guardar. Se basa en los campos: rol
	 * @param entity
	 * @param result
	 */
	private void validateDuplicates(Factura entity, BindingResult result){
		ArrayList<String[]> props = new ArrayList<String[]>();
		props.add(new String[]{"uuid"});
		DuplicateValidator<Factura> validator = new DuplicateValidator<Factura>(
				Factura.class, this, props);
		ValidationUtils.invokeValidator(validator, entity, result);		
	}

	/*
	 * (non-Javadoc)
	 * @see com.segurosthona.scg.service.FacturaService#existeUuid(java.lang.String)
	 */
	@Override
	public Factura existeUuid() {
		return dao.existeUuid();
	}
	
	@Override
	public List<Factura> getFacturas(Integer id) {
		return dao.getFacturas(id);
	}

	@Override
	public List<Factura> getFacturasByEmp(Integer idEmpresa) {
		return dao.getFacturasByEmp(idEmpresa);
	}

	/*
	 * (non-Javadoc)
	 * @see com.segurosthona.scg.service.FacturaService#getFacturasDeSolicitud(java.lang.Integer)
	 */
	@Override
	public List<Factura> getFacturasDeCliente(EstadoFactura estado, Integer idSolicitud) {
		return dao.getFacturasDeCliente(estado, idSolicitud);
	}

	@Override
	public boolean eliminaFacturasDeCliente(Integer idSolicitud) {
		return dao.eliminaFacturasDeCliente(idSolicitud);
	}

	@Override
	public List<Factura> getFacturasConsulta(Integer idEmpresa, BusquedaConsultaPagoForm bp) {
		return dao.getFacturasConsulta(idEmpresa, bp);
	}
}
