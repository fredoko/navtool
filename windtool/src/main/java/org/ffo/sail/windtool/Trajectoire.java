package org.ffo.sail.windtool;

import java.util.ArrayList;
import java.util.List;

/**
 * Ensemblede point GPS formant une ligne (brisée) à la surface de la terre.
 * @author Fred
 *
 */
public class Trajectoire {
	
	/**
	 * Liste des points constituant le trajet  
	 */
	private List<PointGps> listePoints = new ArrayList<PointGps>();
	
	/**
	 * Si adapté, liste des cases traversées référencées par leur centre. 
	 */
	private List<PointGps> listeCases = new ArrayList<PointGps>();
	
	private double longueur = 0.0;
	private boolean aMaj = true;
	
	
	public Trajectoire(PointGps premierPoint) {
		listePoints.add(premierPoint);
	}
	
	public void addPoint(PointGps point)  {
		listePoints.add(point);
		aMaj = true;
	}
	
	public void addCase(PointGps point)  {
		listeCases.add(point);
		aMaj = true;
	}
	
	public double getLongeur() {
		if (aMaj) {
			longueur = GpsTool.distance(listePoints);
			aMaj = false;
		}
		return longueur;
	}

	public List<PointGps> getListePoints() {
		return listePoints;
	}

	public void setListePoints(List<PointGps> listePoints) {
		this.listePoints = listePoints;
	}

	public double getLongueur() {
		return longueur;
	}

	public void setLongueur(double longueur) {
		this.longueur = longueur;
	}

	public List<PointGps> getListeCases() {
		return listeCases;
	}

	public void setListeCases(List<PointGps> listeCases) {
		this.listeCases = listeCases;
	}
	
	

}
