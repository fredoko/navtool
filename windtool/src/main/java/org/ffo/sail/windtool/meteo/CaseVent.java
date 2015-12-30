package org.ffo.sail.windtool.meteo;

import org.ffo.sail.windtool.PointGps;

/**
 * Objet représentant une case de vent : vitesse et direction.
 * 0° symbolise un vent allant du nord au sud. L'angle varie positivement dans le sens des auguille d'une montre.
 * Ainsi un vent ouest->Est est de 90°
 * Unité = degré
 * @author Fred
 *
 */
public class CaseVent {
	
	
	/**
	 * Vitesse en m/s
	 */
	private float vitesse;
	
	
	/**
	 * angle en degré. 
	 */
	private float angle;

	
	private PointGps pointGps;
	
	
	
	public CaseVent() {
		super();
	}

	
	public float getVitesse() {
		return vitesse;
	}

	public void setVitesse(float vitesse) {
		this.vitesse = vitesse;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public PointGps getPointGps() {
		return pointGps;
	}

	public void setPointGps(PointGps pointGps) {
		this.pointGps = pointGps;
	}
	
	
	

}
