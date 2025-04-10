package com.controlgymfit.scgf.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Mensaje de Correo electrónico.
 * @author Joarkemu
 *
 */
public class MensajeCorreo {

	private List<String> to;

	private String asunto;
	private String body;
	
	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}
	
	public void setTo(String to){
		this.to = new ArrayList<String>();
		this.to.add(to);
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "MensajeCorreo [to=" + to + ", asunto=" + asunto + ", body="
				+ body + "]";
	}

}
