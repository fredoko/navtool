package org.ffo.sail.windtool;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contient différente fonction de calcul
 * @author Fred
 *
 */
public class GpsTool {
	
	private static final Logger LOG = LoggerFactory.getLogger(GpsTool.class);
	
	
	public static void main(String[] args) {		  
		System.out.println("dist:" + distance(new PointGps(45.00F, 1.0F), new PointGps(46.0F, 1.0F)));
		System.out.println("dist:" + distance(new PointGps(45.21F, 1.25F), new PointGps(46.35F, 3.5F)));
		
		PointGps A = new PointGps(0.2F, 0.3F);
		PointGps B = new PointGps(3.80F, 1.70F);
		Trajectoire trajet = getTrajectoirePlusCourte(A, B, new Carte(1,-45,-55,-136,177));
		int cpt = 0;
		for (PointGps p : trajet.getListePoints()) {
			cpt++;
			System.out.println("Point " + cpt + ":" + p.lat() + ":" + p.lon());
		}
		System.out.println("Dist AB:" + distance(A, B));
		System.out.println("Dist trajet AB:" + distance(trajet.getListePoints()));
		
		A = new PointGps(-0.2F, -0.3F);
		B = new PointGps(1.0F, 3.30F);
		trajet = getTrajectoirePlusCourte(A, B, new Carte(1,-45,-55,-136,177));
		cpt = 0;
		for (PointGps p : trajet.getListePoints()) {
			cpt++;
			System.out.println("Point " + cpt + ":" + p.lat() + ":" + p.lon());
		}
		System.out.println("Dist AB:" + distance(A, B));
		System.out.println("Dist trajet AB:" + distance(trajet.getListePoints()));
		
	}
	
	public static final double rayonTerreMoyen = 6371000.0;
	
	public static final double coefDegToRad = Math.PI / 180;
	
	/**
	 * Calcule la distance en métre entre duex points GPS
	 * @param a
	 * @param b
	 * @return distance en metre
	 */
	public static float distance(PointGps a, PointGps b) {
		float resultat = (float) ( Cte.RAYON_TERRE
				* Math.acos(Math.sin(a.getLatitude() * Cte.DEGenRAD)
						* Math.sin(b.getLatitude() * Cte.DEGenRAD)
						+ Math.cos(a.getLatitude() * Cte.DEGenRAD)
						* Math.cos(b.getLatitude() * Cte.DEGenRAD)
						* Math.cos(a.getLongitude() * Cte.DEGenRAD
								- b.getLongitude() * Cte.DEGenRAD)));
		return resultat;
	}
	
	public static float distance(PointGps[] tabPoints) {
		if (tabPoints.length <2) {
			return 0.0F;
		}
		float resultat = 0;
		for (int i = 0; i < tabPoints.length -1; i++) {
			resultat += distance(tabPoints[i], tabPoints[i+1]);
		}
		return resultat;		
	}
	
	public static float distance(List<PointGps> tabPoints) {		
		if (tabPoints.size() <2) {
			return 0.0F;
		}
		float resultat = 0;
		for (int i = 0; i < tabPoints.size() -1; i++) {
			resultat += distance(tabPoints.get(i), tabPoints.get(i+1));
		}
		return resultat;		
	}
	
	/**
	 * Retourne une liste de points dont les extremités sont a et b, avec les points interieurs étant les intersections avec la définition de la grille de la carte.
	 * On prend considere comme negligeable la difference entre le parallèle limitant la case horizontalement et le geodesique des latitudes correspondantes. 
	 * Trajectoire nommée généralement trajet Orthodromie.
	 * @param a
	 * @param b
	 * @param precision 
	 * @return 
	 */
	public static Trajectoire getTrajectoirePlusCourte(PointGps a, PointGps b, Carte carte) {
		Trajectoire trajet = new Trajectoire(a);
		PointGps pi = a;
		float latAg = Math.round(a.lat()*carte.getPrecision()) / carte.getPrecision();
		float lonAg = Math.round(a.lon()*carte.getPrecision()) / carte.getPrecision();
		PointGps centreCase = new PointGps(latAg, lonAg);
		while ((pi = getIntersectionGrilleSuivante(pi, b, carte,centreCase)) != null) {
			//On ajoute la case centre courante, elle correspond à la case traversée par le segment [pi-1,pi]
			trajet.addCase(centreCase);
			trajet.addPoint(pi);
			//LOG.debug("Point lat:" + pi.lat() + " lon:" + pi.lon() + "  Casecourante:" + centreCase.getLatitude() + ";" + centreCase.getLongitude());
			//Trouver la case suivante
			centreCase = carte.getCaseAdjacente(centreCase,pi.getDirection());
						
		}
		trajet.addCase(centreCase); //on ajoute la dernière case (celle du point b en fait)
		trajet.addPoint(b);
		
		return trajet;
	}
	
	/**
	 * Détermine le point d'intersection entre le segment [AB] et un geodesique vertical ou horizontal de la grille.
	 * @param a point de départ A 
	 * @param b point d'arrivée B
	 * @param carte contient la définition de la grille.
	 * @return  le point suivant ou null si le point suivant est le point B. (donc que A et B sont dans la même case)
	 * 
	 */
	public static PointGps getIntersectionGrilleSuivante(PointGps a, PointGps b, Carte carte, PointGps caseTraversee) {
		PointGps retour = null;
		float angleA = (b.lat() - a.lat()) * Cte.DEGenRAD; //angle opposé à A dans le triangle rectangle dont le segment AB et l'hypothénuse
		float angleB = (b.lon() - a.lon()) * Cte.DEGenRAD; //angle opposé à B dans le triangle rectangle dont le segment AB et l'hypothénuse
		float angleCaseA; //Angle opposé au point A dans la limite de la case, correspond à aprime si le segmant croise un gedesique horizontal en premier
		float angleCaseB; //Angle opposé au point B dans la limite de la case, correspond à bprime si le segmant croise un gedesique vertical en premier
		float aprime; //angle opposé à A dans une case
		float bprime; //angle opposé à B dans une case
				
		int sensLon = (b.lon()>a.lon()) ? 1:-1;
		int sensLat = (b.lat()>a.lat()) ? 1:-1;
		
		//On utilise l'arrondi mathematique pour "snapper" les points de la grille le plus proche, ensuite on ajoute les longueurs de case (en angle)
		//float latAg = Math.round(a.lat()*carte.getPrecision()) / carte.getPrecision();
		//float lonAg = Math.round(a.lon()*carte.getPrecision()) / carte.getPrecision();
		//en fait on utilise l'info passée en paramètre.
		float latAg = caseTraversee.getLatitude();
		float lonAg = caseTraversee.getLongitude();
		
		
		float latBg = Math.round(b.lat()*carte.getPrecision()) / carte.getPrecision();
		float lonBg = Math.round(b.lon()*carte.getPrecision()) / carte.getPrecision();
		if (latAg == latBg && lonAg == lonBg) {
			//les deux points sont dans la même case
			return null;
		}
		
		//Le point d'intersection est soit une droite verticale, soit une droite horizontale, cela dépend de l'angle su segment AB
		
		//les angles du point A aux bords sont
		angleCaseA = (latAg - a.lat() + sensLat * 1/carte.getPrecision()/2F) * Cte.DEGenRAD;  
		angleCaseB = (lonAg - a.lon() + sensLon * 1/carte.getPrecision()/2F)  *  Cte.DEGenRAD ;
		
		//Calcule des angles opposés dans la case, sorte de projection de l'intersection
		bprime =(float)  (Math.asin(Math.sin(angleCaseA)/Math.sin(angleA)*Math.sin(angleB)));
		aprime =(float)  (Math.asin(Math.sin(angleCaseB)/Math.sin(angleB)*Math.sin(angleA)));
		//Si la droite allant de A à B croise un geodesique horizontal d'abord alors bprime<angleCaseB 
		//Si la droite allant de A à B croise un geodesique vertical d'abord alors aprime<angleCaseA
		//valeur en absolue sinon dans avec les négatif ç a ne marche pas
		if (Math.abs(bprime) < Math.abs(angleCaseB)) {
			retour = new PointGps(a.lat() + angleCaseA/Cte.DEGenRAD, a.lon() + bprime/Cte.DEGenRAD);
			retour.setDirection(sensLat * 1);
			return retour;
		} else if (Math.abs(aprime) < Math.abs(angleCaseA)) {
			retour = new PointGps(a.lat() + aprime/Cte.DEGenRAD, a.lon() + angleCaseB/Cte.DEGenRAD);
			retour.setDirection(sensLon * 2);
			return retour;
		} else {
			//on est pile dans le coin !!
			//Bon faut gérer ce cas !!
			LOG.error("Problème de calcul de trajectoire, on tombe pile dans un coin !");
			return new PointGps(a.lat() + angleCaseA/Cte.DEGenRAD, a.lon() + angleCaseB/Cte.DEGenRAD);
			
		}
		
	}
	
	
	/**
	 * Calcule le cap en degré pour un bateau allant de point A à point B n eligne droite (enfin en suivant un geodesique!)
	 * @param a  point de départ
	 * @param b point d'arrivée
	 * @return le cap en degré
	 */
	public static float getCap(PointGps a, PointGps b) {
		float angleA = (b.lat() - a.lat()) * Cte.DEGenRAD; //angle opposé à A dans le triangle rectangle dont le segment AB et l'hypothénuse
		float angleB = (b.lon() - a.lon()) * Cte.DEGenRAD; //angle opposé à B dans le triangle rectangle dont le segment AB et l'hypothénuse
		float tmp = (float) (Math.cos(angleA)*Math.cos(angleB));
		float rad = (float) Math.acos(Math.cos(angleA) * Math.sin(angleB)/Math.sqrt(1-tmp*tmp));
		//Ensuite il faut replacer cet angle dans le référentiel navigation (0° bateau vers le nord, positif dans le sens des aiguilles d'une montre.
		// alors que notre angle a un 0 bateau vers l'est (donc décalé de 90°) et un sens de rotation inversé (donc inversement de rad)
		rad = (float) (Math.PI/2 - rad);		
		return rad / Cte.DEGenRAD;
	}

}
