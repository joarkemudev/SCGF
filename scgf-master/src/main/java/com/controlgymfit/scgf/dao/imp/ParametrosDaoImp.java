package com.controlgymfit.scgf.dao.imp;

import org.springframework.stereotype.Repository;

import com.controlgymfit.scgf.dao.ParametrosDao;
import com.controlgymfit.scgf.dao.generic.AbstractHibernateDao;
import com.controlgymfit.scgf.modelo.entidad.Parametros;


@Repository
public class ParametrosDaoImp  extends AbstractHibernateDao<Parametros> implements ParametrosDao{

	public ParametrosDaoImp() {
        super();
        setClazz(Parametros.class);
    }	
}
