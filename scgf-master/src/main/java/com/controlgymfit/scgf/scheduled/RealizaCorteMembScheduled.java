package com.controlgymfit.scgf.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.controlgymfit.scgf.service.CorreoService;
import com.controlgymfit.scgf.service.MembresiaService;
import com.controlgymfit.scgf.threads.CorteFechaFinThread;
import com.controlgymfit.scgf.service.EmailService;

/**
 * Realiza el corte de las Membresias con estado activo.
 * @author JQ
 */
@Configuration
@EnableScheduling
public class RealizaCorteMembScheduled {
	@Autowired
	private MembresiaService membresiaService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private CorreoService correoService;
	
	@Scheduled(cron = "0 0 8 * * *")
	//@Scheduled(fixedRate = 60000) // Programa la ejecución cada minuto (60000 milisegundos)
	public void realizaCorteMemb(){
		CorteFechaFinThread corte = new CorteFechaFinThread(membresiaService, correoService, emailService);
		System.out.println("Método programado ejecutado");
		corte.start();
	}
	
}


//
///**
//* Realiza el corte de las Solicitudes con estatus Anticipo
//* @author Joarkemu 
//*
//*/
//@Configuration
//@EnableScheduling
//public class RealizaCorteMembSchedule {
//	@Autowired
//  private MembresiaService membresiaService;
//	@Autowired
//	private EmailService emailService;
//	@Autowired
//	private CorreoService correoService;
//	
//	@Scheduled(cron = "0 * * * * ?") //Ejecutar todos los días a las 00:00:00 (fixedRate = 60000, cron = "0 0 0 * * ?") 
//	
//	public void realizaCorteMemb(){
//		//CorteFechaFinThread corte = new CorteFechaFinThread(membresiaService, correoService, emailService);
//		System.out.println("Método programado ejecutado");
//		//corte.start();
//	}
//}

