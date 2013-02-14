package org.ffo.navtool.carte;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JPanel;

import org.ffo.navtool.gps.KmlPolygons;
import org.ffo.navtool.gps.PointGps;
import org.ffo.navtool.gps.PointXY;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Json {

	private static final Logger LOG = LoggerFactory.getLogger(Json.class);

	private static final Logger LOGJ = LoggerFactory.getLogger("JSON");

	// public static Point frameSize = new Point(1280,720);
	public Point frameSize = new Point(7680, 4320);

	// public static String oceanKmlFile =
	// "C:/travail/JeuVoile/worldsataset/ne_50m_ocean/ne_50m_ocean.kml";
	// public static String oceanKmlFile =
	// "C:/travail/JeuVoile/worldsataset/ne_110m_ocean/ne_110m_ocean.kml";

	public static String oceanKmlFile = "C:/dev/frederic/JeuVoile/worldsataset/ne_50m_land/ne_50m_land.kml";

	private KmlCountryParser parser;
	private Projection mproj;
	private List<String> landList;
	private List<String> traceList;
	
	public static void main(String[] args) {
		Json json = new Json(new Point(19200, 10800));
		json.paint();
		for (String string : json.landList) {
			LOGJ.debug("\"" + string + "\",");	
		}
		for (String string : json.landList) {
			LOGJ.debug("\"" + string + "\",");	
		}
		
	}

	public void paint() {
		landList = new ArrayList<String>();
		traceList = new ArrayList<String>();
		Date debut = new Date();
		dessinerOcean();
		LOG.debug("Temps de dessin:{} ms",
				new Date().getTime() - debut.getTime());
		// dessinerParalleleEtMeridien();
	}

	public Json(Point size) {
		this.frameSize = size;
		parser = new KmlCountryParser();
		// mproj = new MercatorProjection();
		mproj = new CylEquiProjection();
		Date debut = new Date();
		parser.parse(oceanKmlFile);
		LOG.debug("Temps de parsing du fichier KML:{}", new Date().getTime()
				- debut.getTime());

	}

	public void dessinerParalleleEtMeridien() {
		int inc = 5;
		PointGps deb;
		PointGps fin;
		Point pixDeb;
		Point pixFin;
		for (int i = -180 + inc; i <= 180; i += inc) {
			deb = new PointGps(-90, i);
			fin = new PointGps(90, i);
			pixDeb = mproj.getPixelPoint(deb, frameSize);
			pixFin = mproj.getPixelPoint(fin, frameSize);
			traceList.add("M" + pixDeb.x + "," + pixDeb.y + " L" + pixFin.x
					+ "," + pixFin.y);

		}

		for (int i = -90 + inc; i < 90; i += inc) {
			deb = new PointGps(i, -180);
			fin = new PointGps(i, 180);
			pixDeb = mproj.getPixelPoint(deb, frameSize);
			pixFin = mproj.getPixelPoint(fin, frameSize);
			traceList.add("M" + pixDeb.x + "," + pixDeb.y + " L" + pixFin.x
					+ "," + pixFin.y);
		}
	}

	public void dessinerOcean() {

		List<KmlPolygons> liste = parser.getPolygons();
		// parcours de tous les pays ou placemark du fichier
		for (KmlPolygons pol : liste) {
			Point pt;
			PointXY ptxy;
			String path;
			// parcours de tous les polygones puis de chaque point
			for (List<PointGps> polygon : pol.getPolygons()) {
				path = null;
				for (int i = 0; i < polygon.size(); i++) {
					ptxy = mproj.getXY(polygon.get(i));
					pt = mproj.getPixelPoint(ptxy, frameSize);
					if (path == null) {
						path = "M" + pt.x + "," + pt.y;
					} else {
						path += " L" + pt.x + "," + pt.y;
					}
				}
				path += " Z";
				landList.add(path);
			}
		}

	}

}
