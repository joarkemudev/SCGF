package com.controlgymfit.scgf.service;

import java.util.HashMap;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.controlgymfit.scgf.modelo.entidad.Correo;


@Service
public interface EmailService {

public void setMailSender(JavaMailSender mailSender);
	
	/**
	 * Envía un correo electrónico
	 * @param correo Información del correo electrónico.
	 * @param mapa	Lista de variables que se van a convertir.
	 * @return	True si se envió, false de lo contrario.
	 */
	public boolean envioCorreo(Correo correo,  HashMap<String, Object> mapa);
}

