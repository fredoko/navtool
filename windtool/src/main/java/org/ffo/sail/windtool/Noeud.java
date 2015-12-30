package org.ffo.sail.windtool;

/**
 * @author Fred
 *
 */
public class Noeud {
	
	
	
	/**
	 * degrès décimaux.
	 */
	private double latitude;
	
	/**
	 * Dégrès décimaux
	 */
	private double longitude;
	
	
	/**
	 * Vitesse en m/s
	 */ 
	private double vitesse;
	
	
	/**
	 * Angle du vent par rapport à la vertical, 0° = vent soufflant du nord au sud. Angle positif dans le sens des aiguilles d'une montre. 
	 */
	private int angleTWD;
	
	private Noeud parent;

}
