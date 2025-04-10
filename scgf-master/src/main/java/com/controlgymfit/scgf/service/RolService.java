package com.controlgymfit.scgf.service;

import java.util.List;

import com.controlgymfit.scgf.modelo.entidad.Rol;
import com.controlgymfit.scgf.service.generic.*;

public interface RolService extends CrudService<Rol>{

	public List<Rol> getAllOrdenados();
}
