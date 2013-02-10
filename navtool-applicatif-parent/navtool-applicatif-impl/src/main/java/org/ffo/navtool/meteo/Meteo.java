package org.ffo.navtool.meteo;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.ffo.navtool.gps.PointGps;

/**
 * Cet objet enveloppe les diff�rentes pr�vision et retourne a un instant T la bonne pr�vision.
 * 
 * Cet objet est cens� �tre mis � jour au fil de l'eau lorsque de nouveau fichier meteo arrive.
 * Les pr�visions sont stock�e sous forme d'un tree pour une recherche par date facilit�e, permet de g�rer les fichiers meteo ou les intervalle de pr�vision
 * plus ou mons important. 
 * @author Fred
 *
 */
public class Meteo {
	
	/**
	 * Fichier meteo index�s sur leur date de debut de pr�vision.
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
	 * Ajouter la prevision en param�tre, remplace la pr�c�dente si elle existe d�j�.
	 * @param prev prevision
	 */
	public void ajouterPrevision(PrevisionVent prev) {
		arbrePrevision.put(prev.getDatePrevisionDebut(), prev);
	}
	
	

}
