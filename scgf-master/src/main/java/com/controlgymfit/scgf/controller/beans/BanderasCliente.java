package com.controlgymfit.scgf.controller.beans;

/**
 * Banderas de Acciones disponibles para lós Clientes. 
 * @author JQ
 *
 */
public class BanderasCliente {

	//Acciones de Registro
		private boolean agregaFacturas; //stateButtonAddPartidas;
		
		public BanderasCliente(){
			this.agregaFacturas=false;
			
		}

		public boolean isAgregaFacturas() {
			return agregaFacturas;
		}
		
		public void setAgregaFacturas(boolean agregaFacturas) {
			this.agregaFacturas = agregaFacturas;
		}
		
		@Override
		public String toString() {
			return "BanderasCliente [agregaFacturas=" + agregaFacturas 
					+ "]";
		}

}
