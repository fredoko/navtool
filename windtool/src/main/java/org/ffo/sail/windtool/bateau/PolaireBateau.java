package org.ffo.sail.windtool.bateau;

import java.util.HashMap;
import java.util.Map;

/**
 * Représente la polaire d'un bateau,
 * L'objet est de retourner une vitesse selon une vitesse de vent et un angle de bateau par rapport au vent.
 * Si la polaire est incomplète elle doit être interpolée. Dans le cas des angles, elle est interpolée à la construction,
 * dans le cas des vitesses l'interpolation est faite à la demande puisque ce sont des valeurs potentiellement réelle.
 * 
 * Afin de faciliter la recherche, on entre d'abord par l'angle et ensuite on cherche l'intervalle de vitesse contenant la valeur demandée.
 *  les valeurs de vitesse sont classées par ordre croissant.
 * @author Fred
 *
 */
public class PolaireBateau {
	
	private Map<Integer, BaWsBs[]> mapAngle = new HashMap<Integer, BaWsBs[]>();
	
	
	
	public double getVitesseBateau(int angle, double vitesseVent) {
		BaWsBs[] polaireVitesse = mapAngle.get(angle);
		int cpt = 0;		
		while (cpt < polaireVitesse.length && polaireVitesse[cpt].windSpeed <= vitesseVent) {
			cpt++;
		}
		if (cpt == 0) {
			return polaireVitesse[0].boatSpeed;
		} else if (cpt >= polaireVitesse.length) {
			return polaireVitesse[polaireVitesse.length-1].boatSpeed;
		} else {
			System.out.println(polaireVitesse[cpt].windSpeed);
			double ratioInterpolation = (polaireVitesse[cpt].windSpeed - polaireVitesse[cpt-1].windSpeed) /  (vitesseVent - polaireVitesse[cpt-1].windSpeed);
			return (polaireVitesse[cpt].boatSpeed - polaireVitesse[cpt-1].boatSpeed)/ ratioInterpolation + polaireVitesse[cpt-1].boatSpeed;
		}
	}
	
	class BaWsBs {
		double windSpeed;
		int boatAngle;
		double boatSpeed;
		int indexVoile;
		public BaWsBs(double windSpeed, int boatAngle, double boatSpeed,
				int indexVoile) {
			super();
			this.windSpeed = windSpeed;
			this.boatAngle = boatAngle;
			this.boatSpeed = boatSpeed;
			this.indexVoile = indexVoile;
		}	
		
	}
	
	public void addAngle(int angle, double[] vitesseVent, double[] vitesseBateau, int[] numeroVoile) {
		BaWsBs[] polaireVitesse = new BaWsBs[vitesseVent.length];
		for (int i = 0; i < vitesseVent.length; i++) {
			polaireVitesse[i] = new BaWsBs(vitesseVent[i], angle, vitesseBateau[i], numeroVoile[i]); 
		}
		//TODO faire interpolation angle si manque des valeurs
		mapAngle.put(angle, polaireVitesse);
	}
	

}
