package com.controlgymfit.scgf.service.imp;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.controlgymfit.scgf.controller.beans.security.AppUser;
import com.controlgymfit.scgf.dao.UsuarioDao;
import com.controlgymfit.scgf.modelo.entidad.Usuario;
import com.controlgymfit.scgf.service.UsuarioService;
import com.controlgymfit.scgf.util.enums.EstadoCatalogo;

@Service("userDetailsServicecontrolgym")
public class UserDetailServiceImpl implements UserDetailsService {

	@Resource
	private UsuarioDao usuarioDao;
	@Resource
	private UsuarioService usuarioService;	
	
	/**
	 * Metodo para acceder al usuario.
	 */
	public UserDetails loadUserByUsername(String username){
		
		/*
		 Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		 authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return new AppUser("usuarioPrueba", "123", true, true, true, true,  authorities);
		*/
		System.out.println("loadUserByUsername : " + username);
		Usuario colaborador = usuarioDao.getUsuarioPorNombreAcceso(username);
		System.out.println("colaborador: "+colaborador);

		if (colaborador == null){
			throw new UsernameNotFoundException("Login erroneo.");
		}	   
	   
	    boolean enabled = true;
	    boolean accountNonExpired = true;
	    boolean credentialsNonExpired = true;
	    boolean accountNonLocked = true;
	    
	    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	    /*
	    Iterator<Permiso> permisos = colaborador.getPermisos().iterator();
	    while (permisos.hasNext()){
	    	Permiso nextPer = permisos.next();
	    	authorities.add(new SimpleGrantedAuthority(nextPer.getTipoPermiso().getClave()));
	    }
	    */
	   
	    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		   
	    // accede a los permisos configurados en el perfil local
	    //Iterator<String> permisos = colaborador.getRol().getPermisos().iterator();
//	    while (permisos.hasNext()){
//	    	String nextPer = permisos.next();
//	    	authorities.add(new SimpleGrantedAuthority(nextPer));
//	    }	
	    
	    AppUser user = new AppUser(username, "popolopo", enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	    //user.setRol(colaborador.getRol());
	    user.setNombreCompleto(colaborador.getNombre());
	    user.setMail(colaborador.getCorreoElectronico());
	    user.setIdUsuario(colaborador.getId());
	   
	    user.setActivo(colaborador.getEstado()==EstadoCatalogo.ACTIVO?true:false);
	    System.out.println("user: "+user);

	    return user;
	    	   
	}

}
