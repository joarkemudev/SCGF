package com.controlgymfit.scgf.dao.imp;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.controlgymfit.scgf.controller.beans.BusquedaPagoForm;
import com.controlgymfit.scgf.dao.generic.AbstractHibernateDao;
import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.modelo.entidad.Pago;
import com.controlgymfit.scgf.util.enums.MetodoPago;
import com.controlgymfit.scgf.util.enums.TipoDocumento;
import com.controlgymfit.scgf.controller.beans.BusquedaConsultaPagoForm;
import com.controlgymfit.scgf.dao.PagoDao;

/**
 * Implementación del acceso a datos del repositorio de Facturas
 * @author JQ
 * @version 1.0
 *
 */
@Repository
public class PagoDaoImp extends AbstractHibernateDao<Pago> implements PagoDao {
	public PagoDaoImp() {
		super();
		setClazz(Pago.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.controlgymfit.scgf.dao.PagoDao#getPagosFechas(java.util
	 * .Date, java.util.Date)
	 */
	@Override
	public List<Pago> getPagosFechas(Integer idEmpresa, BusquedaPagoForm busqueda) {
		List<Pago> result = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Pago where idEmpresa=:idEmpresa AND fechaPago >= :f1 and fechaPago <= :f2");
		try {
			Query query = getCurrentSession().createQuery(
					queryBuilder.toString());
			
			//Agrega 1 día para abir el intervalo.
			Calendar cal = Calendar.getInstance();
			cal.setTime(busqueda.getF2());
			cal.add(Calendar.DATE, 1);
			query.setParameter("idEmpresa",idEmpresa);
			query.setParameter("f1", busqueda.getF1());
			query.setParameter("f2", cal.getTime());

			result = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.segurosthona.scg.dao.FacturaDao#getFacturasDeSolicitud(java.lang.Integer)
	 */
	@Override
	public List<Pago> getPagosDeCliente(Integer idCliente) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("from Pago where idCliente = :idCliente");
		Query query = getCurrentSession().createQuery(queryString.toString());
		query.setParameter("idCliente", idCliente);
		return query.list();
	}

	@Override
	public boolean eliminaPagosDeCliente(Integer idCliente) {
		Query query = getCurrentSession().createQuery("delete from Pago where idCliente=" + idCliente);
		query.executeUpdate();
		return true;
	}	
	
	@Override
	public List<Pago> getPagoByEmp(Integer idEmpresa) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" from Pago where idEmpresa=:idEmpresa ");		
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
	public List<Pago> getPagosConsulta(Empresa empresa, BusquedaConsultaPagoForm bp) {
		Map<String, Object> params = new HashMap<String, Object>();
		//Procesa filtros:
		String query = getFiltros(bp, params);		
		StringBuilder queryString = new StringBuilder();
		queryString.append("from Pago ");
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
	 * @param ba Filtros
	 * @param params Parametros para agregar después
	 * @return	Query de acuerdo a filtros.
	 */
	private String getFiltros(BusquedaConsultaPagoForm bp, Map<String, Object> params) {
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
	        appendCondition(query, "fechaPago >= :fi");
	        params.put("fi", bp.getFi());
	    }
	    if (bp.getFf() != null) {
	        appendCondition(query, "fechaPago <= :ff");
	        params.put("ff", bp.getFf());
	    }
	    if (bp.getMetodoPago() != null && 
	        (bp.getMetodoPago().equals(MetodoPago.EFECTIVO) || 
	         bp.getMetodoPago().equals(MetodoPago.TARJETA) || 
	         bp.getMetodoPago().equals(MetodoPago.TRANSFERENCIA))) {

	        appendCondition(query, "metodoPago = :metodoPago");
	        params.put("metodoPago", bp.getMetodoPago());
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
