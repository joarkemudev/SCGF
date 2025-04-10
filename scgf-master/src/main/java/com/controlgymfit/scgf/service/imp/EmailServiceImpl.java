package com.controlgymfit.scgf.service.imp;

import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.controlgymfit.scgf.modelo.entidad.Correo;
import com.controlgymfit.scgf.service.EmailService;
import com.controlgymfit.scgf.threads.EnvioCorreoThread;

/**
 * Implementación del Servicio de envío de correo.
 * @author JQ
 * @version 1.0
 */
@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	private JavaMailSender mailSender;
	@Resource
	private ApplicationContext applicationContext;
	@Resource
	private ServletContext context;

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public boolean envioCorreo(Correo correo, HashMap<String, Object> mapa) {
		
		EnvioCorreoThread envio = new EnvioCorreoThread(correo, mapa, context, mailSender);
		envio.start();
		return true;
	}
}
