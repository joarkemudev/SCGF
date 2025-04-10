package com.controlgymfit.scgf.dao.imp;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.controlgymfit.scgf.dao.InscritosClaseDao;
import com.controlgymfit.scgf.dao.generic.AbstractHibernateDao;
import com.controlgymfit.scgf.modelo.entidad.InscritosClase;

/**
 * Implementación del acceso a datos del repositorio de Clases
 * @author JQ
 * @version 1.0
 */
@Repository
public class InscritosClaseDaoImp extends AbstractHibernateDao<InscritosClase> implements InscritosClaseDao{
	 
	public InscritosClaseDaoImp() {
		super();
		setClazz(InscritosClase.class);
	}

	@Override
	public InscritosClase getInscritosClases(Integer idInscritosClase) {
		InscritosClase result = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from InscritosClase where id= :idInscritosClase");
		try {
			Query query = getCurrentSession().createQuery(queryBuilder.toString());
			query.setParameter("InscritosClase", idInscritosClase);
			result = (((org.hibernate.Query) query).list().size()>0?(InscritosClase)query.list().get(0):null);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		return result;
	}
	
	@Override
	public boolean existeInscripcion(Integer claseId, Integer clienteId) {
	    boolean exists = false;
	    StringBuilder queryBuilder = new StringBuilder();
	    queryBuilder.append("SELECT COUNT(i) FROM InscritosClase i " +
                "WHERE i.clase.id = :claseId AND i.cliente.id = :clienteId");

	    try {
	        Query query = getCurrentSession().createQuery(queryBuilder.toString());
	        query.setParameter("claseId", claseId);
	        query.setParameter("clienteId", clienteId);

	        Long count = (Long) query.uniqueResult();
	        exists = (count != null && count > 0);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return exists;
	}
	
	@Override
	public boolean existeSolapamientoHorarioSocio(Integer idCliente, Date fechaHoraInicio, Date fechaHoraFin) {
	    boolean exists = false;
	    StringBuilder queryBuilder = new StringBuilder();
	    queryBuilder.append("SELECT COUNT(ic) FROM InscritosClase ic WHERE ic.cliente.id = :idCliente AND (" +
	    	       " (ic.clase.fechaHoraInicio < :fechaHoraFin AND ic.clase.fechaHoraFin > :fechaHoraInicio))");
	    try {
	        Query query = getCurrentSession().createQuery(queryBuilder.toString());
	        query.setParameter("idCliente", idCliente);
	        query.setParameter("fechaHoraInicio", fechaHoraInicio);
	        query.setParameter("fechaHoraFin", fechaHoraFin);

	        Long count = (Long) query.uniqueResult();
	        exists = (count != null && count > 0);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return exists;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.controlgymfit.scgf.dao.InscritosClaseDao#getFacturasDeSolicitud(java.lang.Integer)
	 */
	@Override
	public List<InscritosClase> getInscritosDeClase(Integer idClase) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("from InscritosClase where idClase = :idClase "); 
		Query query = getCurrentSession().createQuery(queryString.toString());
		query.setParameter("idClase", idClase);
		return query.list();
	}
}
