package org.ffo.navtool.carte;

import java.awt.Point;

import org.ffo.navtool.gps.PointGps;
import org.ffo.navtool.gps.PointXY;

/**
 * Project cylindrique equidistante. http://fr.wikipedia.org/wiki/Projection_cylindrique_%C3%A9quidistante
 * 
 * C'est une non projection !!
 * @author Fred
 *
 */
public class CylEquiProjection implements Projection {

	

	public PointXY getXY(PointGps gps) {  
		return new PointXY(gps.lon(), gps.getLatitude());
	}

	public Point getPixelPoint(PointGps gps, Point frame) {
		return getPixelPoint(getXY(gps),frame);
	}

	public Point getPixelPoint(PointXY pxy, Point frame) {
		Point pixel = new Point();
		//X : le zero de la frame correspond à -180
		//pixel.x = (int) ((pxy.getX() + 180) * frame.x/(2*180));
		
		//Y le sens est inversé et le zero de la frame correspond à 90
		//pixel.y = (int) ( - (pxy.getY() - 90 ) * frame.y/180);
		
		//X : le zero de la frame correspond à 0°
        pixel.x = (int) (pxy.getX() * frame.x/360);
        
        //Y le sens est inversé et le zero de la frame correspond à 0°
        pixel.y = (int) ( - (pxy.getY() ) * frame.y/180);
		
		return pixel;
	}

}
