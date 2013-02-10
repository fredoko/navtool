package org.ffo.navtool.bateau;

import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Représente la polaire d'un bateau,
 * L'objet est de retourner une vitesse de bateau selon une vitesse de vent et un angle de bateau par rapport au vent.
 * Si la polaire est incomplète elle doit être interpolée. 
 * 
 * L'interpolation est faite à la demande.
 * 
 * On utilise des TreeMap pour stocker les valeurs, ainsi on peut récupérer les valeurs immédiatement inférieur et supérieure par rapport à une clé.
 * @author Fred
 *
 */
public class Polaire {
	
	private static final Logger LOG = LoggerFactory.getLogger(Polaire.class);
	
	private TreeMap<Float, TreeMap<Float, BaWsBs> > arbre = new TreeMap<Float, TreeMap<Float,BaWsBs>>();
	
		
	
	/**
	 * Calcule la vitesse du bateau par rapport à l'angle et la vitesse du vent
	 * @param angle en degrés
	 * @param vitesseVent en noeuds
	 * @return vitesse en noeuds
	 */
	public float getVitesseBateau(Float angle, Float vitesseVent) {
		float retourVitesse = 0.0F;
		TreeMap<Float, BaWsBs> arbreVitesse = arbre.get(angle);
		if (arbreVitesse == null) {
			//il faut interpoler cet angle
			Map.Entry<Float,TreeMap<Float, BaWsBs>> floorEntry = arbre.floorEntry(angle);
			Map.Entry<Float,TreeMap<Float, BaWsBs>> highEntry = arbre.higherEntry(angle);
			
			float ratioAngle = (highEntry.getKey() - floorEntry.getKey()) /  (angle - floorEntry.getKey());

			float vitesseAngleFloor = calculVitesseBateau(floorEntry.getValue(), vitesseVent);
			float vitesseAngleHigh = calculVitesseBateau(highEntry.getValue(), vitesseVent);
			
			retourVitesse = (vitesseAngleHigh - vitesseAngleFloor)/ ratioAngle + vitesseAngleFloor;
			
		} else {
			retourVitesse = calculVitesseBateau(arbreVitesse, vitesseVent);
		}
		return retourVitesse;
	}

	private float calculVitesseBateau(TreeMap<Float, BaWsBs> arbreVitesse, Float vitesseVent) {
		float retourVitesse = 0.0F;
		BaWsBs boatSpeed = arbreVitesse.get(vitesseVent);
		if (boatSpeed == null) {
			Map.Entry<Float, BaWsBs> floorEntry = arbreVitesse.floorEntry(vitesseVent);
			Map.Entry<Float, BaWsBs> highEntry = arbreVitesse.higherEntry(vitesseVent);
			
			float ratioVitesse = (highEntry.getKey() - floorEntry.getKey()) /  (vitesseVent - floorEntry.getKey());
			retourVitesse = (highEntry.getValue().boatSpeed - floorEntry.getValue().boatSpeed)/ ratioVitesse + floorEntry.getValue().boatSpeed; 
			
		} else  {
			retourVitesse = boatSpeed.boatSpeed;
		}
		return retourVitesse;
	}
	
	class BaWsBs {
		float windSpeed;
		float boatAngle;//angle bateau par rapport au vent TWA
		float boatSpeed;
		int indexVoile;
		public BaWsBs(float windSpeed, float boatAngle, float boatSpeed,
				int indexVoile) {
			super();
			this.windSpeed = windSpeed;
			this.boatAngle = boatAngle;
			this.boatSpeed = boatSpeed;
			this.indexVoile = indexVoile;
		}	
		
	}
	
	public void addAngle(float angle, float[] vitesseVent, float[] vitesseBateau, int[] numeroVoile) {
		TreeMap<Float, BaWsBs> arbreVitesse = new TreeMap<Float, Polaire.BaWsBs>();
		for (int i = 0; i < vitesseVent.length; i++) {
			arbreVitesse.put(Float.valueOf(vitesseVent[i]), new BaWsBs(vitesseVent[i], angle, vitesseBateau[i], numeroVoile[i]));
		}
		arbre.put(Float.valueOf(angle), arbreVitesse);		
	}
	

}
