package com.controlgymfit.scgf.threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.controlgymfit.scgf.modelo.entidad.Correo;
import com.controlgymfit.scgf.util.MensajeCorreo;

/**
 * Se encarga de enviar un correo electrónico en segundo plano 
 * @author JQ
 * @version 1.0
 */
public class EnvioCorreoThread extends Thread{
	
	private ServletContext context;
	private JavaMailSender mailSender;
	private Correo correo;
	private HashMap<String, Object> mapa;
	
	public EnvioCorreoThread(Correo correo, HashMap<String, Object> mapa, 
			ServletContext context, JavaMailSender mailSender){
		this.correo=correo;
		this.mapa = mapa;
		this.context=context;
		this.mailSender=mailSender;
		
	}
	
	@Override
	public void run(){
		
		MensajeCorreo mensaje = new MensajeCorreo();
		mensaje.setAsunto(correo.getAsunto());

		//System.out.println(context.getRealPath(""));
		
		String msg = "";
		msg = getCorreo(context.getResourceAsStream("WEB-INF/correos/correo1.txt"));
		msg = msg.replace("{titulo}", correo.getTitulo());
		msg = msg.replace("{contenido}", correo.getContenido());

		// Procesa variables del mapa.
		Iterator<Entry<String, Object>> it = mapa.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> pair = (Map.Entry<String, Object>) it.next();
			msg = msg.replace(pair.getKey(), pair.getValue().toString());
			it.remove(); // avoids a ConcurrentModificationException
		}

		//prepareAndSendEmail(msg, correo.getTo().get(0));
		// ORIGINAL
		mensaje.setBody(msg);
		mensaje.setTo(correo.getTo());

		enviar(mensaje);

	}
	
	 public final void prepareAndSendEmail(String htmlMessage, String toMailId) {

	        //Spring Framework JavaMailSenderImplementation    
	        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	        mailSender.setHost("smtp.gmail.com"); // Host de Gmail
	        mailSender.setPort(587); // Puerto para STARTTLS
	        mailSender.setProtocol("smtp");
	        
	     // Configura las credenciales de Gmail
	        mailSender.setUsername("joarkemi@gmail.com");
	        mailSender.setPassword("gaqdodnfibcekfeg");
	        
	        Properties mailProps = mailSender.getJavaMailProperties();
	        mailProps.put("mail.transport.protocol", "smtp");
	        mailProps.put("mail.smtp.auth", "true");
	        mailProps.put("mail.smtp.starttls.enable", "true");
	        mailProps.put("mail.smtp.starttls.required", "true");
	        mailProps.put("mail.smtp.ssl.enable", "false"); // Cambiar a true si usas SSL
	        mailProps.put("mail.debug", "true");

	        //preparing Multimedia Message and sending
	        MimeMessage mimeMessage = mailSender.createMimeMessage();
	        try {
	            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	            helper.setTo(toMailId);
	            helper.setSubject("I achieved the Email with Java 7 and Spring");
	            helper.setText(htmlMessage, true);//setting the html page and passing argument true for 'text/html'

	            //Checking the internet connection and therefore sending the email
	            
	            mailSender.send(mimeMessage);
	           
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }

	    }

	/**
	 * Envía el correo electrónico
	 * @param to Destinatarios
	 * @param subject Titulo del correo
	 * @param body Cuerpo del correo.
	 */
	private void enviar(final MensajeCorreo mensaje) {
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipients(Message.RecipientType.TO,
						getDestinatarios(mensaje.getTo()));
				mimeMessage.setFrom(new InternetAddress("joarkemu@gmail.com")); //Comentar si no funciona....

				mimeMessage.setSubject(mensaje.getAsunto());

				MimeBodyPart mbp = new MimeBodyPart();
				mbp.setText(mensaje.getBody(), "UTF-8", "html");
				Multipart mp = new MimeMultipart();
				mp.addBodyPart(mbp);
				mimeMessage.setContent(mp);
			}
		};
		System.out.println("MAIL SENDER:" + mailSender);
		
		mailSender.send(messagePreparator);
	}

	/**
	 * Obtiene cuentas de correos
	 * @param correos Contiene los correos
	 * @return Arreglo de direcciones de correo electrónico.
	 */
	private InternetAddress[] getDestinatarios(List<String> correos) {
		final ArrayList<InternetAddress> array = new ArrayList<InternetAddress>();

		for (String correo : correos) {

			try {
				array.add(new InternetAddress(correo));
			} catch (AddressException e) {
				e.printStackTrace();
			}
		}
		InternetAddress[] direcciones = new InternetAddress[array.size()];
		for (int i = 0; i < direcciones.length; i++) {
			direcciones[i] = array.get(i);
		}
		return direcciones;
	}

	/**
	 * Obtiene el cuerpo de los correos.
	 * @param filename
	 * @return
	 */
//	private static String getCorreo(InputStream input) {
//	    if (input == null) {
//	        return "Sin contenido disponible"; // O algún mensaje predeterminado como "Sin contenido disponible"
//	    }
//
//	    StringWriter writer = new StringWriter();
//	    try {
//	        IOUtils.copy(input, writer, "UTF-8");
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	    return writer.toString();
//	}

	private static String getCorreo(InputStream input) {
		
	    if (input == null) {
	    	return "Sin contenido disponible"; // O algún mensaje predeterminado como "Sin contenido disponible"
	    }
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(input, writer, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String theString = writer.toString();
		return theString;
	}
}
