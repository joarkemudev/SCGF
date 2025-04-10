package com.controlgymfit.scgf.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.controlgymfit.scgf.modelo.entidad.Usuario;
import com.controlgymfit.scgf.modelo.entidad.Cliente;
import com.controlgymfit.scgf.controller.beans.BanderasCliente;
import com.controlgymfit.scgf.service.FacturaService;
import com.controlgymfit.scgf.util.enums.EstadoCatalogo;
import com.controlgymfit.scgf.service.ClienteService;


@Component
public class AccionesCliente {

	@Autowired
	public FacturaService facturaService;
	@Autowired
	public ClienteService clienteService;
	

	/**
	 * Valida Acciones disponibles para la SOlciitud de Gasto.
	 * 
	 * @param usuario
	 * @param cl
	 * @return
	 */
	public  BanderasCliente getBanderas(
			Usuario usuario,Cliente cl) {
		
		BanderasCliente b = new BanderasCliente();
		
		
		
		if (cl.getEstado() == EstadoCatalogo.ACTIVO) {
			if (validacionFacturas(cl)) {
				b.setAgregaFacturas(false);
			}
		}
		
		return b;
		
	}
	
	
	private boolean validacionFacturas(Cliente cl) {
	    //List<Factura> facturasCliente = facturaService.getFacturasDeCliente(estado, cl.getId());

	    //System.out.println("total cumulos: " + cumulosSolicitud.size()); // Mover el println aquí
	    return (Boolean) null;
	}

}
