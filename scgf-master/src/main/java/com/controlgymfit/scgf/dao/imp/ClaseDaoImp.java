package com.controlgymfit.scgf.dao.imp;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.controlgymfit.scgf.dao.ClaseDao;
import com.controlgymfit.scgf.dao.generic.AbstractHibernateDao;
import com.controlgymfit.scgf.modelo.entidad.Clase;

/**
 * Implementación del acceso a datos del repositorio de Clases
 * @author JQ
 * @version 1.0
 */
@Repository
public class ClaseDaoImp extends AbstractHibernateDao<Clase> implements ClaseDao{
	 
	public ClaseDaoImp() {
		super();
		setClazz(Clase.class);
	}

	@Override
	public Clase getClases(Integer idClase) {
		Clase result = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Clase where id= :idClase");
		try {
			Query query = getCurrentSession().createQuery(queryBuilder.toString());
			query.setParameter("idClase", idClase);
			result = (((org.hibernate.Query) query).list().size()>0?(Clase)query.list().get(0):null);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		return result;
	}
	
	@Override
	public Clase getClaseByIdUsuario(Integer idUsuario) {
		Clase result = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Clase where idUsuario=:idUsuario ");		
		try {
			Query query = getCurrentSession()
					.createQuery(queryBuilder.toString());
			query.setParameter("idUsuario",idUsuario);
			
			result =   (query.list().size()>0?(Clase)query.list().get(0):null);


		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return result;
	}
	
	@Override
	public List<Clase> getClaseByEmp(Integer idEmpresa) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" from Clase where idEmpresa=:idEmpresa ");		
		try {
			Query query = getCurrentSession().createQuery(queryBuilder.toString());	
			query.setParameter("idEmpresa",idEmpresa);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}
	
	@Override
	public List<Clase> getClasesDisponiblesByEmp(Integer idEmpresa) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" FROM Clase c WHERE c.empresa.id = :empresaId "+
				"AND c.estado = 'ACTIVO' " +
				"AND SIZE(c.clientesInscritos) < c.capacidadMaxima"
				);		
		try {
			Query query = getCurrentSession().createQuery(queryBuilder.toString());	
			query.setParameter("idEmpresa",idEmpresa);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}
	
	@Override
	public boolean existeSolapamientoHorario(Integer idEmpresa, Date fechaHoraInicio, Date fechaHoraFin) {
	    boolean exists = false;
	    StringBuilder queryBuilder = new StringBuilder();
	    queryBuilder.append("SELECT COUNT(c) FROM Clase c WHERE c.empresa.id = :idEmpresa AND (" +
	    	       " (c.fechaHoraInicio < :fechaHoraFin AND c.fechaHoraFin > :fechaHoraInicio))");
	    try {
	        Query query = getCurrentSession().createQuery(queryBuilder.toString());
	        query.setParameter("idEmpresa", idEmpresa);
	        query.setParameter("fechaHoraInicio", fechaHoraInicio);
	        query.setParameter("fechaHoraFin", fechaHoraFin);

	        Long count = (Long) query.uniqueResult();
	        exists = (count != null && count > 0);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return exists;
	}
}
