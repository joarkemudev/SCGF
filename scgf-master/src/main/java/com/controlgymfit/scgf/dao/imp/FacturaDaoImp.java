package com.controlgymfit.scgf.dao.imp;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.controlgymfit.scgf.dao.FacturaDao;
import com.controlgymfit.scgf.dao.generic.AbstractHibernateDao;
import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.modelo.entidad.Factura;
import com.controlgymfit.scgf.util.enums.EstadoFactura;
import com.controlgymfit.scgf.util.enums.MetodoPago;
import com.controlgymfit.scgf.controller.beans.BusquedaConsultaPagoForm;

/**
 * Implementación del acceso a datos del repositorio de Facturas
 * @author JQ
 * @version 1.0
 *
 */
@Repository
public class FacturaDaoImp extends AbstractHibernateDao<Factura> implements FacturaDao {
	public FacturaDaoImp() {
		super();
		setClazz(Factura.class);
	}
	
	@Override
	public List<Factura> getFacturas(Integer id) {
		StringBuilder queryx = new StringBuilder("from Factura where id = :id ");
		Query query = getCurrentSession().createQuery(queryx.toString());
		query.setParameter("id", id);
		
		return query.list();
	}
	
	@Override
	public List<Factura> getFacturasByEmp(Integer idEmpresa) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" from Factura where idEmpresa=:idEmpresa ");		
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

	/*
	 * (non-Javadoc)
	 * @see com.segurosthona.scg.dao.FacturaDao#existeUuid(java.lang.String)
	 */
	@Override
	public Factura existeUuid() {
	    StringBuilder queryString = new StringBuilder();
	    queryString.append("from Factura order by id desc");
	    Query query = getCurrentSession().createQuery(queryString.toString());
	    query.setMaxResults(1); // Limita el resultado a solo 1 registro
	    return (Factura) query.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.segurosthona.scg.dao.FacturaDao#getFacturasDeSolicitud(java.lang.Integer)
	 */
	@Override
	public List<Factura> getFacturasDeCliente(EstadoFactura estado, Integer idCliente) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("from Factura where idCliente = :idCliente AND estado = :estado "); 
		Query query = getCurrentSession().createQuery(queryString.toString());
		query.setParameter("idCliente", idCliente);
		query.setParameter("estado", estado);
		return query.list();
	}

	@Override
	public boolean eliminaFacturasDeCliente(Integer idCliente) {
		Query query = getCurrentSession().createQuery("delete from Factura where idCliente=" + idCliente);
		query.executeUpdate();
		return true;
	}	
	
	@Override
	public List<Factura> getFacturasConsulta(Integer idEmpresa, BusquedaConsultaPagoForm bc) {
	
		Map<String, Object> params = new HashMap<String, Object>();
		//Procesa filtros:
		String query = getFiltros(bc, params);		
		StringBuilder queryString = new StringBuilder();
		queryString.append("from Factura ");
		queryString.append(query);
		
		Query queryx = getCurrentSession().createQuery(queryString.toString());
		for (String p : queryx.getNamedParameters()) {			
			if(params.get(p) instanceof List){
				queryx.setParameterList(p, (Collection) params.get(p));
			}else{
				queryx.setParameter(p, params.get(p));
			}
		}	
		
		return queryx.list();
	}
	
	/**
	 * Genera una query de acuerdo a ciertos filtros.
	 * 
	 * @param ba	Filtros
	 * @param params	Parametros para agregar después
	 * @return	Query de acuerdo a filtros.
	 */
	private String getFiltros (BusquedaConsultaPagoForm bp, Map<String, Object> params){
	    StringBuilder query = new StringBuilder();

	    if (bp.getIdEmpresa() != null) {
	    	appendCondition(query, "idEmpresa = :idEmpresa");
	        params.put("idEmpresa", bp.getIdEmpresa());
		}
	    if (bp.getIdCliente() != null) {
	        appendCondition(query, "idCliente = :idCliente");
	        params.put("idCliente", bp.getIdCliente());
	    }
	    if (bp.getFi() != null) {
	        appendCondition(query, "fechaAlta >= :fi");
	        params.put("fi", bp.getFi());
	    }
	    if (bp.getFf() != null) {
	        appendCondition(query, "fechaAlta <= :ff");
	        params.put("ff", bp.getFf());
	    }

	    return query.toString();
	}

	private void appendCondition(StringBuilder query, String condition) {
	    if (query.length() == 0) {
	        query.append(" where ");
	    } else {
	        query.append(" and ");
	    }
	    query.append(condition);
	}
			
}
