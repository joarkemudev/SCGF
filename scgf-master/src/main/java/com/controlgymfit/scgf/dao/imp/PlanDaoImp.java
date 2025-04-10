package com.controlgymfit.scgf.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.controlgymfit.scgf.dao.PlanDao;
import com.controlgymfit.scgf.dao.generic.AbstractHibernateDao;
import com.controlgymfit.scgf.modelo.entidad.Cliente;
import com.controlgymfit.scgf.modelo.entidad.Plan;

/**
 * Implementación del acceso a datos del repositorio de Planes
 * @author JQs
 * @version 1.0
 */
@Repository
public class PlanDaoImp extends AbstractHibernateDao<Plan> implements PlanDao{
	 
	public PlanDaoImp() {
		super();
		setClazz(Plan.class);
	}

	@Override
	public List<Plan> getPlanes(Integer id) {
		StringBuilder queryx = new StringBuilder("from Plan where id = :id ");
		Query query = getCurrentSession().createQuery(queryx.toString());
		query.setParameter("id", id);
		
		return query.list();
	}
	
	@Override
	public List<Plan> getPlanesByEmp(Integer idEmpresa) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" from Plan where idEmpresa=:idEmpresa ");		
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
	public Plan getPlanByIdCliente(Integer idCliente) {
		Plan result = null;
		StringBuilder queryBuilder = new StringBuilder();	
		queryBuilder.append("from Plan where id=:idCliente ");		
		try {
			Query query = getCurrentSession().createQuery(queryBuilder.toString());
			query.setParameter("idCliente",idCliente);
			result =   (query.list().size()>0?(Plan)query.list().get(0):null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return result;
	}
}
