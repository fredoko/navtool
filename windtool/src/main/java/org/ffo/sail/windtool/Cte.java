package org.ffo.sail.windtool;

public class Cte {
	
	
	/**
	 * Mille marin en km
	 */
	public static final float MILLEMARINenKM = 1.842F;
	

	public static final float RAYON_TERRE = 6371000.0F;
	
	
	public static final float DEGenRAD = (float) Math.PI / 180;

	public static final float MSenKM = 3.6F;
	
	/**
	 * 
	 */
	public static final float KMenMILLEMARIN = 1/MILLEMARINenKM;
	
	
	/**
	 * Coefficient de transformation de km/h en m/s
	 */
	public static final float KMenMS = 1/MSenKM;
	
	
	/**
	 * Coefficient de transformation de mille marin en MS.
	 */
	public static final float MSenMILLEMARIN = MSenKM * KMenMILLEMARIN;
	
	/**
	 * Coefficient de transformation de mille marin en MS.
	 */
	public static final float MILLEMARINenMS = 1/MSenMILLEMARIN;

	

}
