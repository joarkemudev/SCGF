package com.controlgymfit.scgf.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.controlgymfit.scgf.dao.MembresiaDao;
import com.controlgymfit.scgf.dao.generic.AbstractHibernateDao;
import com.controlgymfit.scgf.modelo.entidad.Membresia;
import com.controlgymfit.scgf.util.enums.EstadoCatalogo;

/**
 * Implementación del acceso a datos del repositorio de Membresiaas
 * @author JQ
 * @version 1.0
 */
@Repository
public class MembresiaDaoImp extends AbstractHibernateDao<Membresia> implements MembresiaDao{
	 
	public MembresiaDaoImp() {
		super();
		setClazz(Membresia.class);
	}

	@Override
	public Membresia getMembresias(Integer idMembresia) {
		Membresia result = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Membresia where id= :idMembresia");
		try {
			Query query = getCurrentSession().createQuery(queryBuilder.toString());
			query.setParameter("idMembresia", idMembresia);
			result = (((org.hibernate.Query) query).list().size()>0?(Membresia)query.list().get(0):null);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		return result;
	}
	
	
	@Override
	public Membresia getMembresiaByIdCliente(Integer idCliente) {
		Membresia result = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Membresia where idCliente=:idCliente ");		
		try {
			Query query = getCurrentSession()
					.createQuery(queryBuilder.toString());
			query.setParameter("idCliente",idCliente);
			
			result =   (query.list().size()>0?(Membresia)query.list().get(0):null);


		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.segurosthona.scg.dao.SolicitudGastoDao#getSolicitudesEnEstado(java.lang.String, com.segurosthona.scg.util.enums.EstadoSolicitud)
	 */
	@Override
	public List<Membresia> getMembresiasActivas(EstadoCatalogo estado) {
		List<Membresia> result = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("FROM Membresia where estado= :estado ");		
		try {
			Query query = getCurrentSession().createQuery(queryBuilder.toString());
			
			query.setParameter("estado", estado);
			
			result =   query.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return result;
	}
	
	@Override
	public List<Membresia> getMembByEmp(Integer idEmpresa) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" from Membresia where idEmpresa=:idEmpresa ");		
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
	public List<Membresia> existsByEmpresaIdAndEstado(Integer idEmpresa, EstadoCatalogo estado) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" from Membresia where idEmpresa=:idEmpresa  and estado = :estado ");		
		try {
			Query query = getCurrentSession().createQuery(queryBuilder.toString());	
			query.setParameter("idEmpresa",idEmpresa);
			query.setParameter("estado", estado);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}
	
	@Override
	public boolean existsByClienteIdAndEstado(Integer idCliente, EstadoCatalogo estado) {
	    boolean exists = false;
	    StringBuilder queryBuilder = new StringBuilder();
	    queryBuilder.append("select count(m) from Membresia m where m.cliente.id = :idCliente and m.estado = :estado");

	    try {
	        Query query = getCurrentSession().createQuery(queryBuilder.toString());
	        query.setParameter("idCliente", idCliente);
	        query.setParameter("estado", estado);

	        Long count = (Long) query.uniqueResult();
	        exists = (count != null && count > 0);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return exists;
	}

}
