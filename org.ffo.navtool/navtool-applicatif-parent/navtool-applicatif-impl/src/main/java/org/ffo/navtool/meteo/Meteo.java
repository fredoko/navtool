package org.ffo.navtool.meteo;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.ffo.navtool.gps.PointGps;

/**
 * Cet objet enveloppe les différentes prévision et retourne a un instant T la bonne prévision.
 * 
 * Cet objet est censé être mis à jour au fil de l'eau lorsque de nouveau fichier meteo arrive.
 * Les prévisions sont stockée sous forme d'un tree pour une recherche par date facilitée, permet de gérer les fichiers meteo ou les intervalle de prévision
 * plus ou mons important. 
 * @author Fred
 *
 */
public class Meteo {
	
	/**
	 * Fichier meteo indexés sur leur date de debut de prévision.
	 */
	TreeMap<Date, PrevisionVent> arbrePrevision = new TreeMap<Date, PrevisionVent>();
	
	
	/**
	 * @param pt
	 * @param d
	 * @return
	 */
	public CaseVent getPrev(PointGps pt, Date d) {
		Map.Entry<Date, PrevisionVent> entry = arbrePrevision.floorEntry(d);
		return entry.getValue().getCase(pt);
	}
	
	/**
	 * Ajouter la prevision en paramètre, remplace la précédente si elle existe déjà.
	 * @param prev prevision
	 */
	public void ajouterPrevision(PrevisionVent prev) {
		arbrePrevision.put(prev.getDatePrevisionDebut(), prev);
	}
	
	

}
