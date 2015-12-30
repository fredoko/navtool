package org.ffo.sail.windtool;

/**
 * @author Fred
 *
 */
public class BestPath {
	
	
	/**
	 * Plus l'angle est faible, plus de possibilité sont testées mais plus c'est lent.
	 * 
	 */
	public static int precisionAngle = 15;
	
	/**
	 * Plus le temps est faible plsu chaque itération est petite, donc plus c'est précis mais c'est plus lent.
	 * Ce temps doit être mis en rapport avec le niveau de détail du maillage des vents. Pour 1 vent par degrès 1-2 heure est une bonne valeurs.
	 */
	public static int precisionTemps = 3600;
	
	public static void main(String[] args) {
		
	}
	
	public BestPath() {
		
	}

}
