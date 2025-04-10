package com.controlgymfit.scgf.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.controlgymfit.scgf.dao.ClienteDao;
import com.controlgymfit.scgf.dao.generic.AbstractHibernateDao;
import com.controlgymfit.scgf.modelo.entidad.Cliente;

/**
 * Implementación del acceso a datos del repositorio de Clientes
 * @author JQ
 * @version 1.0
 */
@Repository
public class ClienteDaoImp  extends AbstractHibernateDao<Cliente> implements ClienteDao{

	public ClienteDaoImp() {
        super();
        setClazz(Cliente.class);
    }

	/*
	 * (non-Javadoc)
	 * @see com.controlgymfit.scgf.dao.CorreoDao#getCorreoPorActividad(com.controlgymfit.scgf.util.enums.TipoActividad)
	 */
	@Override
	public  Cliente getClientes(Integer idCliente) {
		Cliente result = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from  Cliente where id= :idCliente");		
		try {
			Query query = getCurrentSession().createQuery(queryBuilder.toString());
			
			query.setParameter("id", idCliente);
			
			result =   (query.list().size()>0?(Cliente)query.list().get(0):null);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return result;
	}
	
	@Override
	public List<Cliente> getClientesByEmp(Integer idEmpresa) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" from Cliente where idEmpresa=:idEmpresa ");		
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
	public boolean existe(String numIdentificacion) {
		String queryx = "from Cliente where numIdentificacion =:numIdentificacion";
		Query query = getCurrentSession().createQuery(queryx);
		query.setParameter("numIdentificacion", numIdentificacion);
		
		return (query.list().size()>0?true:false);
	}
	
	@Override
	public Cliente getIdEmpresaCliente(String correoElectronico) {
		Cliente result = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Cliente where correoElectronico =:correoElectronico ");
		try {
			Query query = getCurrentSession().createQuery(queryBuilder.toString());
			query.setParameter("correoElectronico", correoElectronico);
			result = (query.list().size()>0?(Cliente)query.list().get(0):null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return result;
	}
	
}
