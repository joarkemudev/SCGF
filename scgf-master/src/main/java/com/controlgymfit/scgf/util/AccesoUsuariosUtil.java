package com.controlgymfit.scgf.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.controlgymfit.scgf.modelo.entidad.Usuario;
import com.controlgymfit.scgf.service.UsuarioService;
import com.controlgymfit.scgf.util.dtos.TipoAccesoUsuario;

@Component
public class AccesoUsuariosUtil {

	@Autowired
	public UsuarioService usuarioService;

	/**
	 * Valida Acciones disponibles para la SOlciitud de Gasto.
	 * 
	 * @param usuario
	 * @param sol
	 * @param listPartidas
	 * @param listAnexos
	 * @return
	 */
	public  TipoAccesoUsuario getTipoAcceso(String nombreAcceso) {
		TipoAccesoUsuario ta = new TipoAccesoUsuario();
		Usuario user = usuarioService.getUsuarioPorNombreAcceso(nombreAcceso);
//		if(user != null){
//			ta.setIdArea(user.getArea().getId());
//			ta.setTipoAcceso(user.getTipoAcceso());
//		
//			return ta;	
//		}
		return null;
	}
	
}
