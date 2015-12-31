package org.ffo.navtool.carte;

import java.awt.Point;

import org.ffo.navtool.gps.PointGps;
import org.ffo.navtool.gps.PointXY;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cet objet permet de manipuler des points avec la projection de mercator,
 * projection cylindriqie tangente à l'equateur
 * @author Fred
 *
 */
public class MercatorProjection implements Projection {
	
	public static float LIMIT = 84f;
	
	
	private static final Logger LOG = LoggerFactory
			.getLogger(MercatorProjection.class);
	
	/* (non-Javadoc)
	 * @see org.ffo.navtool.carte.Projection#getXY(org.ffo.navtool.gps.PointGps)
	 */
	public PointXY getXY(PointGps gps) {
		PointXY p = new PointXY();
		p.setX((float) Math.toRadians(gps.getLongitude()));
		//p.setY(((float) Math.atan(Math.sin(gps.getLatitude()))));
		//LOG.debug(gps.getLatitude() + ":" + Math.log(Math.tan(Math.PI/4+gps.getLatitude()/2)) );
		//p.setY( (float)  (Math.log(Math.tan(Math.PI/4 + Math.toRadians(gps.getLatitude())/2)))  );
		float lat = gps.getLatitude();
		if(lat > LIMIT) {
			lat = LIMIT;
		}
		if(lat < - LIMIT) {
			lat = - LIMIT;
		}
		p.setY( (float)  Math.log(Math.tan(Math.PI/4 + Math.toRadians(lat)/2)));	
		return p;
	}
	
	
	/* (non-Javadoc)
	 * @see org.ffo.navtool.carte.Projection#getPixelPoint(org.ffo.navtool.gps.PointGps, java.awt.Point)
	 */
	public Point getPixelPoint(PointGps gps, Point frame) {
		return getPixelPoint(getXY(gps),frame);
	}
	
	/* (non-Javadoc)
	 * @see org.ffo.navtool.carte.Projection#getPixelPoint(org.ffo.navtool.gps.PointXY, java.awt.Point)
	 */
	public Point getPixelPoint(PointXY pxy, Point frame) {
//		if(Math.abs(pxy.getY()) > 2.36) {
//			//return null;
//			LOG.debug(pxy.getY());
//		}
		Point pixel = new Point();
		//X : le zero de la frame correspond à -PI
		pixel.x = (int) ((pxy.getX() + Math.PI) * frame.x/(2*Math.PI));
		
		// le raport entre xet y est en fait PI est non le rapport de la frame
		double rapportXY = 6.2;
		double offsety = 3;
		//Y le sens est inversé et le zero de la frame correspond à 1
		pixel.y = (int) ( - (pxy.getY() - offsety ) * frame.y/rapportXY);
		return pixel;
	}

}
