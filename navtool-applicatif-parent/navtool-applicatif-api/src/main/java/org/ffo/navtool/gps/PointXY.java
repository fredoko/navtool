package org.ffo.navtool.gps;

/**
 * Point contenant � la fois les coordonn�es GPS et la coordonn�e xy de la
 * projection en plan
 * 
 * @author Fred
 * 
 */
public class PointXY {

	private float x;
	private float y;

	public PointXY() {
		super();
	}

	public PointXY(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}
