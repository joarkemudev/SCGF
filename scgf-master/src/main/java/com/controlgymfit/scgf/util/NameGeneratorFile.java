package com.controlgymfit.scgf.util;

import java.util.Random;

public class NameGeneratorFile {

	public static String generateFolioNumber(int numeroOrig) {
		String res = "";
		if (numeroOrig >= 1000)
			res = "_" + numeroOrig;
		if (numeroOrig >= 100)
			res = "_0" + numeroOrig;
		if (numeroOrig >= 10)
			res = "_00" + numeroOrig;
		if (numeroOrig >= 1)
			res = "_000" + numeroOrig;
		return res;
	}

	
	public static String generateNameStringFile(String serieString) {
		
		 char n;
	        Random rnd = new Random();
	        String cadena ="AnexoGasto";
	    
	        
	        for (int i = 0; i < 9; i++) {
	            n = (char) (rnd.nextDouble() * 26.0 + 65.0);
	           
	            cadena += n;
	        }
	        
		return cadena+serieString;
		
	} 
	
	public static String generateNameStringFilePartida(String serieString) {
		
		 char n;
	        Random rnd = new Random();
	        String cadena ="AnexoGastoPartida";
	    
	        
	        for (int i = 0; i < 9; i++) {
	            n = (char) (rnd.nextDouble() * 26.0 + 65.0);
	           
	            cadena += n;
	        }
	        
		return cadena+serieString;
		
	} 
	
	
}
