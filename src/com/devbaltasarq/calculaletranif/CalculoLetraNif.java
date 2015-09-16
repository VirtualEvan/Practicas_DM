package com.devbaltasarq.calculaletranif;

public class CalculoLetraNif {

	 public static final String NIF_STRING_ASOCIATION = "TRWAGMYFPDXBNJZSQVHLCKE";
	 
	  /**
	   * Devuelve la letra correspondiente al NIF
	   * @param dni dni del que calcular la letra del NIF, como un int
	   * @return La letra del nif, como un char.
	   */
	  public static char calculaLetraNif(int dni) {
	    return NIF_STRING_ASOCIATION.charAt( dni % 23 );
	  }
}
