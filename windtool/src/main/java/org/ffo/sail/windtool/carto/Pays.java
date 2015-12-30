package org.ffo.sail.windtool.carto;

import java.util.ArrayList;
import java.util.List;

import org.ffo.sail.windtool.PointGps;

public class Pays {
	
	private PointGps capitalPosition;
	
	private List<List<PointGps>> polygons;
	
	private String name;
	
	private String iso3;
	
	
	
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
	
	public PointGps getCapitalPosition() {
		return capitalPosition;
	}

	public void setCapitalPosition(PointGps capitalPosition) {
		this.capitalPosition = capitalPosition;
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

	public String getIso3() {
		return iso3;
	}

	public void setIso3(String iso3) {
		this.iso3 = iso3;
	}
	
	

}
