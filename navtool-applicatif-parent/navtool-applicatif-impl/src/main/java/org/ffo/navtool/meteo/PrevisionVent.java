package org.ffo.navtool.meteo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ffo.navtool.gps.Carte;
import org.ffo.navtool.gps.PointGps;

/**
 * Contient les pr�visions de vents pour une date donn�e (datePrevisionDebut) r�alis�e � une date donn�e (dateCreationPrevision).
 * Cette pr�vision est valable un certain temps.
 * 
 * L'index de la map est la concatenation latitude et longitude, multipli� par la precision pour obtenir un entier.
 * Ainsi pour une carte de pr�cision 10 soit (0.1�). la case vent [45.2�, -152.1�] est index�e par 452:-1521 
 * 
 * 
 *  C'est la pr�cision de la carte sousjacente qui determine la taille de la grille.
 *   
 * 
 * @author Fred
 *
 */
public class PrevisionVent {
	
	private Map<String, CaseVent> listeCase = new HashMap<String, CaseVent>();
	
	private Carte carte;
	
	/**
	 * Date � laquelle la pr�vision a �t� faite
	 */
	private Date dateCreationPrevision;
	
	/**
	 * Date de d�but de la pr�vision.
	 */
	private Date datePrevisionDebut;
	
	/**
	 * pr�riode de validit� en heures
	 */
	private int  periodeValidite;
	
	
	
	/**
	 * DEtermine la case vent correspondante au point gps. Ce get calcule la bonne case selon la pr�cision de la carte.
	 * @param pointGps
	 * @return
	 */
	public CaseVent getCase(PointGps pointGps) {
		//float lat = pointGps.getLatitude()*carte.getPrecision();
		int lat = Math.round(pointGps.getLatitude()*carte.getPrecision());
		int lng = Math.round(pointGps.getLongitude()*carte.getPrecision());
		return listeCase.get(lat + ":" + lng);		
	}
	
	public CaseVent getCase(float latf, float lonf) {
		//float lat = pointGps.getLatitude()*carte.getPrecision();
		int lat = Math.round(latf*carte.getPrecision());
		int lng = Math.round(lonf*carte.getPrecision());
		return listeCase.get(lat + ":" + lng);		
	}
	
	/**
	 * Retourne la case adjacent selon la direction
	 * @param pointGps
	 * @param direction, 1 nord, 2 nor
	 * @return
	 */
	public CaseVent getCase(PointGps pointGps, int direction) {
		
		return listeCase.get(pointGps);	
	}
	
	
	public void addCaseVent(CaseVent caseVent, float lat, float lon) {
		listeCase.put(Math.round(lat*carte.getPrecision()) + ":" + Math.round(lon*carte.getPrecision()) , caseVent);
	}

	public Map<String, CaseVent> getListeCase() {
		return listeCase;
	}

	public void setListeCase(Map<String, CaseVent> listeCase) {
		this.listeCase = listeCase;
	}

	public Carte getCarte() {
		return carte;
	}

	public void setCarte(Carte carte) {
		this.carte = carte;
	}

	public Date getDateCreationPrevision() {
		return dateCreationPrevision;
	}

	public void setDateCreationPrevision(Date dateCreationPrevision) {
		this.dateCreationPrevision = dateCreationPrevision;
	}

	public Date getDatePrevisionDebut() {
		return datePrevisionDebut;
	}

	public void setDatePrevisionDebut(Date datePrevisionDebut) {
		this.datePrevisionDebut = datePrevisionDebut;
	}

	public int getPeriodeValidite() {
		return periodeValidite;
	}

	public void setPeriodeValidite(int periodeValidite) {
		this.periodeValidite = periodeValidite;
	}
	
	
	
	

}
