package com.controlgymfit.scgf.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.controlgymfit.scgf.dao.RolDao;
import com.controlgymfit.scgf.dao.generic.AbstractHibernateDao;
import com.controlgymfit.scgf.modelo.entidad.Rol;

/**
 * Implementación del acceso a datos del repositorio de Roles
 * @author JQ
 * @version 1.0
 */
@Repository
public class RolDaoImp  extends AbstractHibernateDao<Rol> implements RolDao{

	public RolDaoImp() {
        super();
        setClazz(Rol.class);
    }

	@Override
	public List<Rol> getAllOrdenados() {
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" from Rol order by nombre");		
		try {
			Query query = getCurrentSession().createQuery(queryBuilder.toString());		
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return null;
	}
}
