package com.controlgymfit.scgf.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import com.controlgymfit.scgf.dao.ClaseDao;
import com.controlgymfit.scgf.dao.ClienteDao;
import com.controlgymfit.scgf.modelo.entidad.Clase;
import com.controlgymfit.scgf.modelo.entidad.Cliente;
import com.controlgymfit.scgf.modelo.generic.IOperations;
import com.controlgymfit.scgf.service.ClaseService;
import com.controlgymfit.scgf.service.generic.AbstractService;
import com.controlgymfit.scgf.service.generic.DuplicateValidator;

@Service
public class ClaseServiceImp extends AbstractService<Clase> implements ClaseService{
	
	@Autowired
	private ClaseDao dao;
	@Autowired
	private ClienteDao daoCli;
	
	public ClaseServiceImp() {
		super();
	}
	
	@Override
	protected IOperations<Clase> getDao() {
		return dao;
	}
	
	public void validateBeforeCreate(Clase entity, BindingResult result) {
		validateDuplicates(entity, result);
	}
	
	public void validateBeforeUpdate(Clase entity, BindingResult result) {
		validateDuplicates(entity, result);
	}
	
	public void validateBeforeDelete(Clase entity, BindingResult result) {
		validateDuplicates(entity, result);
	}
	
	/**
	 * Valida existencia de duplicados antes de guardar. Se basa en los campos: nombre
	 * @param entity
	 * @param result
	 */
	private void validateDuplicates(Clase entity, BindingResult result) {
		ArrayList<String[]> props = new ArrayList<String[]>();
		props.add(new String[]{"nombre"});
		DuplicateValidator<Clase> validator = new DuplicateValidator<Clase>(Clase.class, this, props);
		ValidationUtils.invokeValidator(validator, entity, result);
	}
	
	@Override
	public Clase getClases(Integer idClase) {
		return null;
	}
	
	@Override
	public Clase getClaseByIdUsuario(Integer idUsuario) {
		return dao.getClaseByIdUsuario(idUsuario);
	}
	
	@Override
	public List<Clase> getClaseByEmp(Integer idEmpresa) {
		return dao.getClaseByEmp(idEmpresa);
	}
	
	@Override
	public List<Clase> getClasesDisponiblesByEmp(Integer idEmpresa) {
		return dao.getClaseByEmp(idEmpresa);
	}
	
//    @Override
//    public boolean hayCupoDisponible(Integer claseId) {
//        Clase clase = dao.getClases(claseId);
//        return clase != null && 
//               clase.getClientesInscritos().size() < clase.getCapacidadMaxima();
//    }
    
    @Override
    public void inscribirSocio(Integer claseId, Integer socioId) {
        Clase clase = dao.getClases(claseId);
        Cliente socio = daoCli.getClientes(socioId);
        
        // Validaciones usando solo getters
        if (clase == null || socio == null) {
            throw new RuntimeException("Clase o socio no encontrado");
        }
        
        if (clase.getClientesInscritos().size() >= clase.getCapacidadMaxima()) {
            throw new RuntimeException("No hay cupos disponibles");
        }
        
//        if (estaInscrito(claseId, socioId)) {
//            throw new RuntimeException("El socio ya está inscrito en esta clase");
//        }
        
//        // Validar solapamiento de horarios
//        boolean tieneSolapamiento = socio.getClasesInscritas().stream()
//            .anyMatch(c -> 
//                c.getFechaHoraInicio().before(clase.getFechaHoraFin()) && 
//                c.getFechaHoraFin().after(clase.getFechaHoraInicio()));
//        
//        if (tieneSolapamiento) {
//            throw new RuntimeException("El socio tiene otra clase en este horario");
//        }
        
//        // Inscripción usando getters y setters existentes
//        Set<Cliente> inscritos = clase.getClientesInscritos();
//        inscritos.add(socio);
//        clase.setClientesInscritos(inscritos);
        
        dao.update(clase);
    }
    
	@Override
    public boolean existeSolapamientoHorario(Integer idEmpresa, Date fechaHoraInicio, Date fechaHoraFin) {
        return dao.existeSolapamientoHorario(idEmpresa, fechaHoraInicio, fechaHoraFin);
    }
}
