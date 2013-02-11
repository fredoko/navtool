package org.ffo.navtool.gps;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Fred
 *
 */
@XmlRootElement
public class PointGps implements Serializable {
	
	
	/**
	 * degrés décimaux.
	 */
	private float latitude;
	
	/**
	 * Dégrés décimaux
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(latitude);
		result = prime * result + Float.floatToIntBits(longitude);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PointGps other = (PointGps) obj;
		if (Float.floatToIntBits(latitude) != Float
				.floatToIntBits(other.latitude))
			return false;
		if (Float.floatToIntBits(longitude) != Float
				.floatToIntBits(other.longitude))
			return false;
		return true;
	}

	
	 
	

}
