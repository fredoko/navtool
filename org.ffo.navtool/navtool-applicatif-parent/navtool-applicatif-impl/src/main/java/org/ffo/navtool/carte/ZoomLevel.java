package org.ffo.navtool.carte;

/**
 * Liste des différents niveau, la valeur et la taille de la case. 
 * LE choix d'appliquer le niveau en fonctionn de la taille de la fenêtre et laisser � l'implémentation : bon choix ?
 * @author Fred
 *
 */
public enum ZoomLevel {
	
	NIVEAU1(1.0f),
	NIVEAU2(50.0f),
	NIVEAU3(500.0f);
	
	private float tailleCase;
	
	private ZoomLevel(float tailleCase) {
		this.tailleCase = tailleCase;
	}

	public float getTailleCase() {
		return tailleCase;
	}
	
	

}
