package org.ffo.sail.windtool.routage;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.ffo.sail.windtool.Carte;
import org.ffo.sail.windtool.Cte;
import org.ffo.sail.windtool.GpsTool;
import org.ffo.sail.windtool.PointGps;
import org.ffo.sail.windtool.Trajectoire;
import org.ffo.sail.windtool.bateau.Polaire;
import org.ffo.sail.windtool.meteo.CaseVent;
import org.ffo.sail.windtool.meteo.Meteo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Routage {
	
	private static final Logger LOG = LoggerFactory.getLogger(Routage.class);
	
	/**
	 * Meteo utilisée pour cet objet de routage. 
	 */
	private Meteo meteo;
	
	
	/**
	 * Polaire de bateau utilisée pour ce routage. 
	 */
	private Polaire polaire;
	
	
	
	/**
	 * Calcule le temps de trajet pour le bateau représenté par la polaire entre le point A et le point B en suivant un geodesique (ligne droite).
	 * Les erreurs possibles sont :
	 * - point en dehors de la carte
	 * - fichier meteo incomplet en terme de temps
	 * @param pA point A
	 * @param pB point B
	 * @param carte carte
	 * @param dateDebut date de debut du trajet
	 * @param bateau polaire du bateau
	 * @return le temps en milliseconde.
	 */
	public long calculTemps(PointGps pA, PointGps pB, Carte carte, Date dateDebut, Polaire polaire) {
		Trajectoire trajet = GpsTool.getTrajectoirePlusCourte(pA, pB, carte);
		long temps = 0;
		long tempsIntermediaire;
		float cap;
		float vBateau;		
		float distance;
		//calcul du cap entre 2 points
		cap = GpsTool.getCap(pA, pB);
		
		GregorianCalendar dateCourante = new GregorianCalendar();
		dateCourante.setTime(dateDebut);
		for (int i = 0; i < trajet.getListePoints().size() -1; i++) {
			vBateau = getVitesseBateau(trajet.getListeCases().get(i), dateCourante.getTime(), cap);
			distance = GpsTool.distance(trajet.getListePoints().get(i), trajet.getListePoints().get(i+1));
			//TODO trouver le moment ou la viette change dans une case à cause de du changement de prévision
			tempsIntermediaire = Math.round(distance/vBateau);
			//Attention si
			
			
			dateCourante.add(Calendar.MILLISECOND, (int) tempsIntermediaire);
			temps += tempsIntermediaire;
			
		}
		
		return temps;
	}
	
	/**
	 * Retourne la vitesse d'un bateau en fonction de son cap, de sa position et la date de demande.
	 * @param p
	 * @param date
	 * @param cap
	 * @return vitesse en m/s
	 */
	public float getVitesseBateau(PointGps p, Date date, float cap) {
		CaseVent vent = meteo.getPrev(p, date);
		LOG.debug("Vitesse vent:{} m/s ", vent.getVitesse());
		LOG.debug("Angle vent:{}°", vent.getAngle());
		//TWA true wind Angle = vent angle bateau (convention = positif venant de tribord)
		float twa = cap - vent.getAngle();
		if (twa<-180) {
			twa = twa + 360;
		}
		if (cap>180) {
			twa = twa -360;
		}
		twa = 140;
		LOG.debug("=> TWA:{}°", twa);
		
		float vitesseBateauMS = polaire.getVitesseBateau(Math.abs(twa), (float) (vent.getVitesse()*Cte.MSenMILLEMARIN)) * Cte.MILLEMARINenMS;
		LOG.debug("VitesseBateau:{} m/s", vitesseBateauMS);
		
		return vitesseBateauMS;
		
	}

	public Meteo getMeteo() {
		return meteo;
	}

	public void setMeteo(Meteo meteo) {
		this.meteo = meteo;
	}

	public Polaire getPolaire() {
		return polaire;
	}

	public void setPolaire(Polaire polaire) {
		this.polaire = polaire;
	}

}
