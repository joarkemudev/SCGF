package com.controlgymfit.scgf.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailService {
	
	/**
	 * Este metodo carga el usuario para ser validado por el sistema.
	 * @param username el username.
	 * @return Los detalles del usuario.
	 */
	public UserDetails loadUserByUsername(String username);

}
