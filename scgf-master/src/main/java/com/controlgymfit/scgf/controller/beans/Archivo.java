package com.controlgymfit.scgf.controller.beans;

import org.springframework.web.multipart.MultipartFile;

/**
 * Contiene un archivo para su carga al sistema.
 * 
 * @author JQ
 *
 */
public class Archivo {
	
	private MultipartFile archivo;

	public MultipartFile getArchivo() {
		return archivo;
	}

	public void setArchivo(MultipartFile archivo) {
		this.archivo = archivo;
	}	
	
}
