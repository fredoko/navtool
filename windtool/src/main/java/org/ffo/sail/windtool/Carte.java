package org.ffo.sail.windtool;

/**
 * Une carte est un rectangle sur une sphère délimité par des coordonnées GPS.
 * 
 * Noter que la latitude s'exprime positivement dans le nord
 * et la longitude vers l'est.
 * 
 * Une carte est définie par une précision qui détermine la taille des cases unitaires.
 * 
 * 
 * 
 * @author Fred
 *
 */
public class Carte {
	
	/**
	 * Precision en 1/degrés des cases de la carte (variant de 100 à 1)
	 * 1 correspond à 1°, 2 à 0.5°, 100 à 0.01°
	 */
	private int precision = 1;
	
	
	
	
	private float latitudeNord = 0.0F;
	
	private float latitudeSud = 0.0F;
	
	private float longitudeOuest = 0.0F;
	
	private float longitudeEst = 0.0F;

	
	
	public Carte(int precision, float latitudeNord, float latitudeSud,
			float longitudeOuest, float longitudeEst) {
		super();
		this.precision = precision;
		this.latitudeNord = latitudeNord;
		this.latitudeSud = latitudeSud;
		this.longitudeOuest = longitudeOuest;
		this.longitudeEst = longitudeEst;
	}
	
	public PointGps getCaseAdjacente(PointGps p, int dir) {		
		PointGps retour;
		if (dir == 2 || dir ==-2) {
			retour = new PointGps(p.getLatitude(), p.getLongitude() + 1/precision*dir/2);
		} else {
			retour = new PointGps(p.getLatitude() + 1/precision*dir, p.getLongitude());
		}
		
		return retour;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public float getLatitudeNord() {
		return latitudeNord;
	}

	public void setLatitudeNord(float latitudeNord) {
		this.latitudeNord = latitudeNord;
	}

	public float getLatitudeSud() {
		return latitudeSud;
	}

	public void setLatitudeSud(float latitudeSud) {
		this.latitudeSud = latitudeSud;
	}

	public float getLongitudeOuest() {
		return longitudeOuest;
	}

	public void setLongitudeOuest(float longitudeOuest) {
		this.longitudeOuest = longitudeOuest;
	}

	public float getLongitudeEst() {
		return longitudeEst;
	}

	public void setLongitudeEst(float longitudeEst) {
		this.longitudeEst = longitudeEst;
	}
	
	
	
	

}
