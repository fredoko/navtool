package org.ffo.navtool.gps;

import java.util.ArrayList;
import java.util.List;


/**
 * Représente une liste de polygones, correpond a la balise placemark qui décrit en général un pays. Un pays peut être constitués de différents polygones.
 * @author Fred
 *
 */
public class KmlPolygons {
	
	private PointGps referencePoint;
	
	private List<List<PointGps>> polygons;
	
	private String name;
	
	private String code;
	
	
	
	/**
	 * Ajout un point au dernier polygon
	 * @param gps
	 * @param newPolygon
	 */
	public void add(PointGps gps, boolean newPolygon) {
		if (polygons == null)  {
			polygons = new ArrayList<List<PointGps>>();
		}
		if (newPolygon || polygons.size() == 0) {
			polygons.add(new ArrayList<PointGps>());
		}		
		polygons.get(polygons.size()-1).add(gps);
	}

	/**
	 * Ajout un point au dernier polygon
	 * @param gps
	 * @param newPolygon
	 */
	public void add(PointGps gps) {
		if (polygons == null)  {
			polygons = new ArrayList<List<PointGps>>();
		}
		if (polygons.size() == 0) {
			polygons.add(new ArrayList<PointGps>());
		}		
		polygons.get(polygons.size()-1).add(gps);
	}
	
	public void newPolygon() {
		if (polygons == null)  {
			polygons = new ArrayList<List<PointGps>>();
		}
		polygons.add(new ArrayList<PointGps>());
	}
	
	public PointGps getReferencePoint() {
		return referencePoint;
	}

	public void setReferencePoint(PointGps capitalPosition) {
		this.referencePoint = capitalPosition;
	}
	
	
	

	public List<List<PointGps>> getPolygons() {
		return polygons;
	}

	public void setPolygons(List<List<PointGps>> polygons) {
		this.polygons = polygons;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
	
	

}
