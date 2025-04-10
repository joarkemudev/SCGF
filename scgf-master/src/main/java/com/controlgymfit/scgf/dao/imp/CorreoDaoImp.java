package com.controlgymfit.scgf.dao.imp;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.controlgymfit.scgf.dao.CorreoDao;
import com.controlgymfit.scgf.dao.generic.AbstractHibernateDao;
import com.controlgymfit.scgf.modelo.entidad.Correo;
import com.controlgymfit.scgf.util.enums.TipoActividad;

/**
 * Implementación del acceso a datos del repositorio de Correos
 * @author JQ
 * @version 1.0
 */
@Repository
public class CorreoDaoImp  extends AbstractHibernateDao<Correo> implements CorreoDao{

	public CorreoDaoImp() {
        super();
        setClazz(Correo.class);
    }

	/*
	 * (non-Javadoc)
	 * @see com.segurosthona.scg.dao.CorreoDao#getCorreoPorActividad(com.segurosthona.scg.util.enums.TipoActividad)
	 */
	@Override
	public Correo getCorreoPorActividad(TipoActividad tipo) {
		Correo result = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Correo where tipoActividad= :tipoActividad");		
		try {
			Query query = getCurrentSession()
					.createQuery(queryBuilder.toString());
			
			query.setParameter("tipoActividad", tipo);
			
			result =   (query.list().size()>0?(Correo)query.list().get(0):null);


		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return result;
	}
	
}
