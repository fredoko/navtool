package org.ffo.navtool.carte;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ffo.navtool.gps.KmlPolygons;
import org.ffo.navtool.gps.PointGps;
import org.ffo.navtool.gps.PointXY;

/**
 * Cette classe permet de chercher de polygons en fonction de position geographique selon un niveau de zoom.
 * La requête contient toujours 2 critères :
 * - niveau de zoom
 * - point central
 * 
 * 
 * @author Fred
 *
 */
public class PolygonsRequester {
	
	
	private HashMap<ZoomLevel, KmlCountryParser> landDefs  = new HashMap<ZoomLevel, KmlCountryParser>();
	
	public PolygonsRequester() {
		
	}
	
	public void addLandDef(ZoomLevel zoom, KmlCountryParser land) {
		landDefs.put(zoom, land);
	}

	
	
	/**
	 * Retourne les polygons pour la zone et le zoom donné. Applique la projection passé en paramètre.
	 * @param zoom
	 * @param gps
	 * @param proj
	 * @return
	 */
	public List<List<Point>> getPolygons(ZoomLevel zoom, PointGps gps, Projection proj) {		
		//La zone limite est d�limit�e par la valeur du zoom et le point GPS 
		//Le point GPS est "snapp�" au centre de la zone de fa�on � ce que les carr�s retourn�e soint toujours les m�mes (pour pour pouvoir les mettre en cash)
		//On consid�re que la carte est quadrill�e de cases de la largeur du zoom avec comme pointGps (0,0) comme centre de case de base
		// donc par exemple sur les x, et pour un zoom de 5 miles, les limites de case sont tous les 5�  � partir de 2.5�
		//C'est equivantel � un arrondi � l'entier appliqu� a diff�rente echelle
		float lon = (float) Math.rint(gps.getLongitude() / zoom.getTailleCase()) * zoom.getTailleCase();
		float lat = (float) Math.rint(gps.getLatitude() / zoom.getTailleCase()) * zoom.getTailleCase();
		PointGps snap = new PointGps(lat, lon);
		
		return _getPolygons(zoom, snap, proj);
	}
	
	/**
	 * Méthode cacheable
	 * @param zoom
	 * @param gps
	 * @param proj
	 * @return
	 */
	private List<List<Point>> _getPolygons(ZoomLevel zoom, PointGps centre, Projection proj) {
		List<List<Point>> retour = new ArrayList<List<Point>>();
		List<KmlPolygons> polys = landDefs.get(zoom).getPolygons();
		Point pt;
		PointXY ptxy;
		for (KmlPolygons pol : polys) {
			for(List<PointGps> polygon : pol.getPolygons()) {
				for (int i = 0; i < polygon.size(); i++) {
					if (isPointInside(zoom, centre, polygon.get(i))) {
						pt = proj.getPixelPoint(polygon.get(i), frameSize);
					}
					ptxy = proj.getXY(polygon.get(i));	
					pt = proj.getPixelPoint(ptxy, frameSize);
					
					xs[i] = pt.x;
					ys[i] = pt.y;
					
				}
			}	
		}
		
		
		return retour;
	}
		
	
	/**
	 * @param zoom
	 * @param centre
	 * @param gps
	 * @return
	 */
	public boolean isPointInside(ZoomLevel zoom, PointGps centre, PointGps gps) {
		boolean retour = gps.getLatitude()> centre.getLatitude() - zoom.getTailleCase()
				&& gps.getLatitude()<= centre.getLatitude() + zoom.getTailleCase()
				&& gps.getLongitude()> centre.getLongitude() - zoom.getTailleCase()
				&& gps.getLongitude()<= centre.getLongitude() - zoom.getTailleCase();
		return retour;
	}

}
