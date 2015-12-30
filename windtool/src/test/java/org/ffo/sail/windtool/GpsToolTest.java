package org.ffo.sail.windtool;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GpsToolTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(GpsToolTest.class);


	@Test
	public void testDistancePointGpsPointGps() {
		LOG.debug("dist:" + GpsTool.distance(new PointGps(45.00F, 1.0F), new PointGps(46.0F, 1.0F)));
		LOG.debug("dist:" + GpsTool.distance(new PointGps(45.21F, 1.25F), new PointGps(46.35F, 3.5F)));
	}
	
	@Test
	public void testcap() {
		PointGps A = new PointGps(0.2F, 0.3F);
		PointGps B = new PointGps(3.80F, 1.70F);
		LOG.debug("CapAB:{}°", GpsTool.getCap(A, B));
		LOG.debug("CapBA:{}°", GpsTool.getCap(B, A));
		A = new PointGps(-0.2F, -0.3F);
		B = new PointGps(1.0F, 3.30F);
		LOG.debug("CapAB:{}°", GpsTool.getCap(A, B));
		LOG.debug("CapBA:{}°", GpsTool.getCap(B, A));
		
	}

	@Test
	public void testDistanceListOfPointGps() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTrajectoirePlusCourte() {
		PointGps A = new PointGps(0.2F, 0.3F);
		PointGps B = new PointGps(3.80F, 1.70F);
		Carte carte = new Carte(1,-45,-55,-136,177);
		Trajectoire trajet;
		int cpt = 0;
		float d1;
		float d2;
		float err;
		
		//Test avec droite montant en positif,positif
		LOG.debug("Test avec droite montant en positif,positif");
		trajet = GpsTool.getTrajectoirePlusCourte(A, B, carte);		
		for (PointGps p : trajet.getListePoints()) {
			cpt++;
			LOG.debug("Point " + cpt + ":" + p.lat() + "-" + p.lon());
		}
		d1 = GpsTool.distance(A, B);
		d2 = GpsTool.distance(trajet.getListePoints());
		err = (d1-d2)/d1;
		LOG.debug("Dist AB:" + d1);
		LOG.debug("Dist trajet AB:" + d2);
		LOG.debug("Erreur:" + err);
		
		//idem
		LOG.debug("idem");
		A = new PointGps(-0.2F, -0.3F);
		B = new PointGps(1.0F, 3.30F);
		trajet = GpsTool.getTrajectoirePlusCourte(A, B, carte);
		cpt = 0;
		for (PointGps p : trajet.getListePoints()) {
			cpt++;
			LOG.debug("Point " + cpt + ":" + p.lat() + "-" + p.lon());
		}
		LOG.debug("Dist AB:" + GpsTool.distance(A, B));
		LOG.debug("Dist trajet AB:" + GpsTool.distance(trajet.getListePoints()));
		LOG.debug("");
		
		//negatif,negatif
		LOG.debug("negatif,negatif");
		trajet = GpsTool.getTrajectoirePlusCourte(B, A, carte);
		cpt = 0;
		for (PointGps p : trajet.getListePoints()) {
			cpt++;
			LOG.debug("Point " + cpt + ":" + p.lat() + "-" + p.lon());
		}
		LOG.debug("Dist BA:" + GpsTool.distance(A, B));
		LOG.debug("Dist trajet BA:" + GpsTool.distance(trajet.getListePoints()));
		LOG.debug("");
		
		//negatif,positif
		LOG.debug("negatif,positif");
		A = new PointGps(3.8F, 0.1F);
		B = new PointGps(1.8F, 2.9F);
		trajet = GpsTool.getTrajectoirePlusCourte(A, B, carte);
		cpt = 0;
		for (PointGps p : trajet.getListePoints()) {
			cpt++;
			LOG.debug("Point " + cpt + ":" + p.lat() + "-" + p.lon());
		}
		LOG.debug("Dist AB:" + GpsTool.distance(A, B));
		LOG.debug("Dist trajet AB:" + GpsTool.distance(trajet.getListePoints()));
		LOG.debug("");
		
		LOG.debug("positif/negatif");
		//positif/negatif
		trajet = GpsTool.getTrajectoirePlusCourte(B, A, carte);
		cpt = 0;
		for (PointGps p : trajet.getListePoints()) {
			cpt++;
			LOG.debug("Point " + cpt + ":" + p.lat() + ";" + p.lon());
		}
		LOG.debug("Dist BA:" + GpsTool.distance(A, B));
		LOG.debug("Dist trajet BA:" + GpsTool.distance(trajet.getListePoints()));
		LOG.debug("");
		
		//Test avec droite sur un croisement en théorie
		LOG.debug("Test avec droite sur un croisement en théorie");
		A = new PointGps(1F, 1.2F);
		B = new PointGps(2F, 1.8F);
		trajet = GpsTool.getTrajectoirePlusCourte(A, B, carte);
		cpt = 0;
		for (PointGps p : trajet.getListePoints()) {
			cpt++;
			LOG.debug("Point " + cpt + ":" + p.lat() + ";" + p.lon());
		}
		LOG.debug("Dist AB:" + GpsTool.distance(A, B));
		LOG.debug("Dist trajet AB:" + GpsTool.distance(trajet.getListePoints()));
		LOG.debug("");
		//=> cas impossible, ouf!
		
		//Test même case
		LOG.debug("Test Même case");
		A = new PointGps(0.8F, -0.2F);
		B = new PointGps(1.4F, 0.1F);
		trajet = GpsTool.getTrajectoirePlusCourte(A, B, carte);
		cpt = 0;
		for (PointGps p : trajet.getListePoints()) {
			cpt++;
			LOG.debug("Point " + cpt + ":" + p.lat() + ";" + p.lon());
		}
		LOG.debug("Dist AB:" + GpsTool.distance(A, B));
		LOG.debug("Dist trajet AB:" + GpsTool.distance(trajet.getListePoints()));
		LOG.debug("erreur:" + GpsTool.distance(trajet.getListePoints()));
		LOG.debug("");
		
		//Test avec point sur geodesique
		LOG.debug("Test avec point sur geodesique");
		A = new PointGps(1.7F, -0.1F);
		B = new PointGps(3.5F, 0.8F);
		trajet = GpsTool.getTrajectoirePlusCourte(A, B, carte);
		cpt = 0;
		for (PointGps p : trajet.getListePoints()) {
			cpt++;
			LOG.debug("Point " + cpt + ":" + p.lat() + "-" + p.lon());
		}
		LOG.debug("Dist AB:" + GpsTool.distance(A, B));
		LOG.debug("Dist trajet AB:" + GpsTool.distance(trajet.getListePoints()));
		LOG.debug("");
		
		
	}

}
