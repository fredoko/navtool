package org.ffo.sail.windtool;

/**
 * @author Fred
 *
 */
public class PointGps {
	
	
	/**
	 * degrès décimaux.
	 */
	private float latitude;
	
	/**
	 * Dégrès décimaux
	 */
	private float longitude;
	
	/**
	 * Bidouille pour stocker une information de direction. 1: nord, 2:est, -1:sud, -2 : ouest
	 */
	private int direction;
	
	

	public PointGps(float latitude, float longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float lat() {
		return latitude;
	}

	public float lon() {
		return longitude;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	
	 
	

}
