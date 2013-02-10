package org.ffo.navtool.carte;

import java.awt.Point;

import org.ffo.navtool.gps.PointGps;
import org.ffo.navtool.gps.PointXY;

public interface Projection {

	/**
	 * On part du principe que la carte est centr�e en lon=0 et lat=0, 
	 * Aucun ratio n'est appliqu� sur le r�sultat, ainsi les x varient de 0 � 2PI
	 * les y varies de -1 � 1 
	 * 
	 * @return
	 */
	public abstract PointXY getXY(PointGps gps);

	/**
	 * Calcule le point en pixel d'apr�s le poing gps
	 * @param gps
	 * @param frame
	 * @return
	 */
	public abstract Point getPixelPoint(PointGps gps, Point frame);

	/**
	 * Calcule le point en pixel d'apr�s le poing gps.
	 * on ignore les points dont la latitude est sup�rieur au PI/2 (comprend pas pourquoi on obtient une projection 37rad pour 90�)
	 * @param gps
	 * @param frame
	 * @return
	 */
	public abstract Point getPixelPoint(PointXY pxy, Point frame);

}