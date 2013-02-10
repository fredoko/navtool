package org.ffo.navtool.carte;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Date;
import java.util.List;

import javax.swing.JPanel;

import org.ffo.navtool.gps.KmlPolygons;
import org.ffo.navtool.gps.PointGps;
import org.ffo.navtool.gps.PointXY;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JCanvas extends JPanel {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(JCanvas.class);
	
	private static final Logger LOGJ = LoggerFactory.getLogger("JSON");
	
	//public static Point frameSize = new Point(1280,720);
	public Point frameSize = new Point(1920,1080);
	
	//public static String oceanKmlFile = "C:/travail/JeuVoile/worldsataset/ne_50m_ocean/ne_50m_ocean.kml";
	//public static String oceanKmlFile = "C:/travail/JeuVoile/worldsataset/ne_110m_ocean/ne_110m_ocean.kml";
	
	public static String oceanKmlFile = "C:/travail/JeuVoile/worldsataset/ne_50m_land/ne_50m_land.kml";
	
	
	private KmlCountryParser parser;
	private Projection mproj;
	
	public void paint(Graphics g) {
//		Color c = g.getColor();
//		g.setColor(Color.RED);
//		g.fillRect(10,10,80,80);
//		g.setColor(Color.BLUE);
//		g.fillOval(150,50,80,80);
//		g.setColor(c);
		Date debut = new Date();
		dessinerOcean(g);
		LOG.debug("Temps de dessin:{} ms", new Date().getTime() - debut.getTime());
		//dessinerParalleleEtMeridien(g);
	}
	
	public JCanvas(Point size) {
		super();
		this.frameSize = size;
		parser = new KmlCountryParser();
		//mproj = new MercatorProjection();
		mproj = new CylEquiProjection();
		Date debut = new Date();
		parser.parse(oceanKmlFile);
		LOG.debug("Temps de parsing du fichier KML:{}", new Date().getTime() - debut.getTime());

	}
	
	public void dessinerParalleleEtMeridien(Graphics g) {
		int inc = 5;
		PointGps deb;
		PointGps fin;
		Point pixDeb;
		Point pixFin;
		for (int i = -180 + inc; i <= 180; i += inc) {
			deb = new PointGps(-90,i);
			fin = new PointGps(90,i);
			pixDeb = mproj.getPixelPoint(deb, frameSize);
			pixFin = mproj.getPixelPoint(fin, frameSize);
			g.setColor(Color.GRAY);
			g.drawLine(pixDeb.x, pixDeb.y, pixFin.x, pixFin.y);
		}
		
		for (int i = -90 + inc; i < 90; i += inc) {
			deb = new PointGps(i,-180);
			fin = new PointGps(i,180);
			pixDeb = mproj.getPixelPoint(deb, frameSize);
			pixFin = mproj.getPixelPoint(fin, frameSize);
			g.setColor(Color.GRAY);
			g.drawLine(pixDeb.x, pixDeb.y, pixFin.x, pixFin.y);
		}
	}
	
	public void dessinerOcean(Graphics g) {
		
		List<KmlPolygons> liste = parser.getPolygons();
		//parcours de tous les pays ou placemark du fichier
		for (KmlPolygons pol : liste) {
			
			//slection sur le code du placemrk (en général code ISO, mais ici on n'a que PA1)
			//if(pol.getCode().equals("PA1") || pol.getCode().equals("PA2")) {				
				Point pt;
				PointXY ptxy;
				int[] xs;
				int[] ys;
				//parcours de tous les polygones puis de chaque point
				for(List<PointGps> polygon : pol.getPolygons()) {
					xs = new int[polygon.size()];
					ys = new int[polygon.size()];
					for (int i = 0; i < polygon.size(); i++) {						
						ptxy = mproj.getXY(polygon.get(i));	
						pt = mproj.getPixelPoint(ptxy, frameSize);
						
						xs[i] = pt.x;
						ys[i] = pt.y;
						
					}
					g.setColor(Color.LIGHT_GRAY);
					g.fillPolygon(xs, ys, polygon.size());
					//g.drawPolygon(xs, ys, polygon.size());
					
				}				
			}
		//}
		
	}
	

}
