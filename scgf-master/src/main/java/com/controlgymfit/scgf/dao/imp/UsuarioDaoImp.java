package com.controlgymfit.scgf.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.controlgymfit.scgf.controller.beans.UsuarioSelect;
import com.controlgymfit.scgf.dao.UsuarioDao;
import com.controlgymfit.scgf.dao.generic.AbstractHibernateDao;
import com.controlgymfit.scgf.modelo.entidad.Plan;
import com.controlgymfit.scgf.modelo.entidad.Usuario;
import com.controlgymfit.scgf.util.enums.EstadoCatalogo;

/**
 * Implementación del acceso a datos del repositorio de Usuarios
 * @author JQ
 * @version 1.0
 */
@Repository
public class UsuarioDaoImp  extends AbstractHibernateDao<Usuario> implements UsuarioDao{

	public UsuarioDaoImp() {
        super();
        setClazz(Usuario.class);
    }

	/*
	 * (non-Javadoc)
	 * @see com.controlgymfit.scgf.dao.UsuarioDao#getUsuario(java.lang.String)
	 */
	@Override
	public Usuario getUsuario(String correoElectronico) {
		
		Usuario result = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Usuario where correoElectronico= :correoElectronico");		
		try {
			Query query = getCurrentSession()
					.createQuery(queryBuilder.toString());
			
			query.setParameter("correoElectronico", correoElectronico);
			
			result =   (query.list().size()>0?(Usuario)query.list().get(0):null);


		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.controlgymfit.scgf.dao.UsuarioDao#getUsuarioPorNombreAcceso(java.lang.String)
	 */
	@Override
	public Usuario getUsuarioPorNombreAcceso(String nombreAcceso) {
		Usuario result = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Usuario where nombreAcceso= :nombreAcceso");		
		try {
			Query query = getCurrentSession()
					.createQuery(queryBuilder.toString());
			
			query.setParameter("nombreAcceso", nombreAcceso);
			
			result =   (query.list().size()>0?(Usuario)query.list().get(0):null);


		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.controlgymfit.scgf.dao.UsuarioDao#getActivos()
	 */
	@Override
	public List<UsuarioSelect> getActivos() {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select new com.controlgymfit.scgf.controller.beans.UsuarioSelect(u.id, u.nombreAcceso, u.nombre) from Usuario u where estado= :estado order by nombre");		
		try {
			Query query = getCurrentSession().createQuery(queryBuilder.toString());		
			query.setParameter("estado", EstadoCatalogo.ACTIVO);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.controlgymfit.scgf.dao.UsuarioDao#getActivos()
	 */
	@Override
	public List<UsuarioSelect> getUsuariosActivosByEmp(Integer idEmpresa) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select new com.controlgymfit.scgf.controller.beans.UsuarioSelect(u.id, u.nombreAcceso, u.nombre) from Usuario u where estado= :estado and idEmpresa= :idEmpresa order by nombre");		
		try {
			Query query = getCurrentSession().createQuery(queryBuilder.toString());		
			query.setParameter("estado", EstadoCatalogo.ACTIVO);
			query.setParameter("idEmpresa", idEmpresa);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.controlgymfit.scgf.dao.UsuarioDao#getUsuariosConPermiso(java.lang.String)
	 */
	@Override
	public List<Usuario> getUsuariosConPermiso(String permiso) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Usuario where estado = :estado and  :permiso in elements(rol.permisos) ");		
		try {
			Query query = getCurrentSession().createQuery(queryBuilder.toString());		
			query.setParameter("estado", EstadoCatalogo.ACTIVO);
			query.setParameter("permiso", permiso);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@Override
	public Usuario getIdEmpesaUsuario(String userName) {
		Usuario result = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Usuario where nombreAcceso=:userName ");		
		try {
			Query query = getCurrentSession().createQuery(queryBuilder.toString());
			query.setParameter("userName",userName);	
			result =   (query.list().size()>0?(Usuario)query.list().get(0):null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return result;
	}
}
