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
	 * @param canvasSize taille du canvas d'affichage.
	 * @return
	 */
	public List<List<Point>> getPolygons(ZoomLevel zoom, PointGps gps, Projection proj, Point canvasSize) {		
		//La zone limite est délimitée par la valeur du zoom et le point GPS 
		//Le point GPS est "snappé" au centre de la zone de façon à ce que les carrés retournés soient toujours les m�mes (pour pour pouvoir les mettre en cash)
		//On considère que la carte est quadrillée de cases de la largeur du zoom avec comme pointGps (0,0) comme centre de case de base
		// donc par exemple sur les x, et pour un zoom de 5 miles, les limites de case sont tous les 5° à partir de 2.5°
		//C'est equivantel à un arrondi à l'entier appliqué a différente echelle
		float lon = (float) Math.rint(gps.getLongitude() / zoom.getTailleCase()) * zoom.getTailleCase();
		float lat = (float) Math.rint(gps.getLatitude() / zoom.getTailleCase()) * zoom.getTailleCase();
		PointGps snap = new PointGps(lat, lon);
		
		return _getPolygons(zoom, snap, proj, canvasSize);
	}
	
	/**
	 * Méthode cacheable
	 * @param zoom
	 * @param gps
	 * @param proj
	 * @return
	 */
	private List<List<Point>> _getPolygons(ZoomLevel zoom, PointGps centre, Projection proj, Point canvasSize) {
		List<List<Point>> retour = new ArrayList<List<Point>>();
		List<KmlPolygons> polys = landDefs.get(zoom).getPolygons();
		Point pt;
		PointXY ptxy;
		//Pour toutes les listes de polygones
		for (KmlPolygons listePolys : polys) {
		    // Pour tous les polygones de la liste
			for(List<PointGps> polygon : listePolys.getPolygons()) {
			    //Pour chaque points du polygon
				for (int i = 0; i < polygon.size(); i++) {
				    //Si le point est à l'intérieur alors on le garde
				    // mais il faut calculer le point d'intersection avec la limite du carré (défini dans zoom)
					if (isPointInside(zoom, centre, polygon.get(i))) {
						pt = proj.getPixelPoint(polygon.get(i), canvasSize);
					}
					ptxy = proj.getXY(polygon.get(i));	
					pt = proj.getPixelPoint(ptxy, canvasSize);
					
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
