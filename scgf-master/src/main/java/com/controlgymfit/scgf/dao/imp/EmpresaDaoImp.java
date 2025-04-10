package com.controlgymfit.scgf.dao.imp;

import org.hibernate.Query;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.controlgymfit.scgf.dao.EmpresaDao;
import com.controlgymfit.scgf.dao.generic.AbstractHibernateDao;
import com.controlgymfit.scgf.modelo.entidad.Empresa;

/**
 * Implementación del acceso a datos del repositorio de Empresas
 * @author JQ
 * @version 1.0
 */
@Repository
public class EmpresaDaoImp  extends AbstractHibernateDao<Empresa> implements EmpresaDao{

	public EmpresaDaoImp() {
        super();
        setClazz( Empresa.class);
    }

	/*
	 * (non-Javadoc)
	 * @see com.controlgymfit.scgf.dao.EmpresaDao#getCorreoPorActividad(com.controlgymfit.scgf.util.enums.TipoActividad)
	 */
	@Override
	public  Empresa getEmpresas(Integer idEmpresa) {
		Empresa result = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Empresa where id= :idEmpresa");		
		try {
			Query query = getCurrentSession().createQuery(queryBuilder.toString());
			
			query.setParameter("id", idEmpresa);
			
			result =   (query.list().size()>0?(Empresa)query.list().get(0):null);


		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return result;
	}
	
	@Override
	public List<Empresa> getAllOrdenados(){
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Empresa order by nombre");
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
