/**
 * 
 */
package com.controlgymfit.scgf.util;

import java.util.Random;

public class Randomizer {

	
	private static Random rnd = new Random();

	public static int getNumRandom() {
		return rnd.nextInt();
	}

	public static String getNumSRandom() {
		return String.valueOf(rnd.nextInt());
	}

}
