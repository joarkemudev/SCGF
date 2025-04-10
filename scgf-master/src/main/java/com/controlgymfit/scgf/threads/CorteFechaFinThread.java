package com.controlgymfit.scgf.threads;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.controlgymfit.scgf.modelo.entidad.Membresia;
import com.controlgymfit.scgf.modelo.entidad.Correo;
import com.controlgymfit.scgf.service.MembresiaService;

import com.controlgymfit.scgf.service.CorreoService;
import com.controlgymfit.scgf.service.EmailService;
import com.controlgymfit.scgf.util.enums.EstadoCatalogo;
import com.controlgymfit.scgf.util.enums.TipoActividad;

/**
 * Realiza el Corte de Sol Ant en Segundo Plano 
 * @author Joarkemu
 *
 */
public class CorteFechaFinThread extends Thread {
	
	private MembresiaService membresiaService;
	private CorreoService correoService;
	private EmailService emailService;
	
	public CorteFechaFinThread(
			MembresiaService membresiaService, 
			CorreoService correoService,
			EmailService emailService
			
			){
		this.membresiaService = membresiaService;
		this.correoService = correoService;
		this.emailService = emailService;
		
	}
	
	@Override
	public void run() {
	    List<Membresia> membresias = membresiaService.getMembresiasActivas(EstadoCatalogo.ACTIVO);
	    SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
	    
	    for(Membresia memb : membresias) {
	        try {
	            Date fechaActual = new Date();
	            Date fechaFin = memb.getFechaFin();
	            
	            if (fechaFin == null) continue;
	            
	            // Normalizar fechas (eliminar horas, minutos, segundos)
	            Date hoy = removeTime(fechaActual);
	            Date fechaVencimiento = removeTime(fechaFin);
	            
	            // Calcular fecha exacta de 3 días antes del vencimiento
	            Date tresDiasAntes = addDays(fechaVencimiento, -3);
	            
	            System.out.println("Hoy: " + hoy + " | 3 días antes vencimiento: " + tresDiasAntes + " | Fecha Vencimiento: " + fechaVencimiento);
	            
	            if (fechaVencimiento.before(hoy)) {
	                // Membresía vencida
	                memb.setEstado(EstadoCatalogo.INACTIVO);
	                enviarCorreoVencimiento(memb, sdf);
	            } 
	            else if (hoy.equals(tresDiasAntes)) {
	                // Exactamente 3 días antes del vencimiento
	                enviarCorreoRecordatorio(memb, sdf);
	            }
	            
	            membresiaService.update(memb);
	        } catch (Exception e) {
	            System.err.println("Error procesando membresía ID: " + memb.getId());
	            e.printStackTrace();
	        }
	    }
	}

	// Métodos auxiliares
	private Date removeTime(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    return cal.getTime();
	}

	private Date addDays(Date date, int days) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, days);
	    return cal.getTime();
	}

	private void enviarCorreoVencimiento(Membresia memb, SimpleDateFormat sdf) {
	    Correo correo = correoService.getCorreoPorActividad(TipoActividad.FECHA_FIN_VEN);
	    if(correo != null) {
	        enviarCorreo(memb, correo, sdf);
	    }
	}

	private void enviarCorreoRecordatorio(Membresia memb, SimpleDateFormat sdf) {
	    Correo correo = correoService.getCorreoPorActividad(TipoActividad.FECHA_FIN_REV_3);
	    if(correo != null) {
	        enviarCorreo(memb, correo, sdf);
	    }
	}

	private void enviarCorreo(Membresia memb, Correo correo, SimpleDateFormat sdf) {
	    try {
	        Membresia membr = membresiaService.findOne(memb.getId());
	        ArrayList<String> destinatarios = new ArrayList<>();
	        destinatarios.add(membr.getCliente().getCorreoElectronico());
	        correo.setTo(destinatarios);
	        
	        HashMap<String, Object> map = new HashMap<>();
	        map.put("{nombreGym}", membr.getEmpresa().getNombre());
	        map.put("{socio}", membr.getCliente().getNombre());
	        map.put("{nombreSocio}", membr.getCliente().getNombre() + " " + membr.getCliente().getPrimerApellido());
	        map.put("{valorPlan}", "$" + membr.getCliente().getPlan().getPrecio());
	        map.put("{fechaInicio}", sdf.format(membr.getFechaInicio()));
	        map.put("{estadoMonitoreo}", membr.getEstado().name());
	        map.put("{fechaFin}", sdf.format(membr.getFechaFin()));
	        map.put("{tipoPlan}", membr.getCliente().getPlan().getNombre());
	        map.put("{responsableGym}", membr.getEmpresa().getResponsable());
	        map.put("{correoGym}", membr.getEmpresa().getCorreoElectronico());
	        map.put("{telefonoGym}", membr.getEmpresa().getTelefono());
	        
	        emailService.envioCorreo(correo, map);
	    } catch (Exception e) {
	        System.err.println("Error enviando correo para membresía ID: " + memb.getId());
	        e.printStackTrace();
	    }
	}
}
