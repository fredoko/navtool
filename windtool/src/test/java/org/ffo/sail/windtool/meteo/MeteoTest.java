package org.ffo.sail.windtool.meteo;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Date;

import org.ffo.sail.windtool.Carte;
import org.ffo.sail.windtool.Cte;
import org.ffo.sail.windtool.PointGps;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MeteoTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(MeteoTest.class);
	
	static Meteo meteo = new Meteo();

	@Test
	public void testGetPrev() {
		loadPrevision();
		Date date;
		try {
			PointGps p = new PointGps(-52.25F, -175.25F); 
			date = LoadPrevisionGrib2.spf_heure.parse("20121225-08");
			CaseVent vent = meteo.getPrev(p, date);
			LOG.debug("Vent en km/h:" + vent.getVitesse() * Cte.MSenKM);
			LOG.debug("Angle en degrés:" + vent.getAngle());

			date = LoadPrevisionGrib2.spf_heure.parse("20121225-21");
			vent = meteo.getPrev(p, date);
			LOG.debug("Vent en km/h:" + vent.getVitesse() * Cte.MSenKM);
			LOG.debug("Angle en degrés:" + vent.getAngle());
			
			date = LoadPrevisionGrib2.spf_heure.parse("20121226-10");
			vent = meteo.getPrev(p, date);
			LOG.debug("Vent en km/h:" + vent.getVitesse() * Cte.MSenKM);
			LOG.debug("Angle en degrés:" + vent.getAngle());

			
		} catch (ParseException e) {
			LOG.error("",e);
			Assert.fail(e.getMessage());
		}
		 
	}

	
	public void loadPrevision() {
		//Chargement des beans necessaires au test
		LoadPrevisionGrib2 loader = new LoadPrevisionGrib2();
		Carte carte = new Carte(1,-45,-55,-136,177);
		
		try {
			Date date = LoadPrevisionGrib2.spf.parse("20121225");
			//PrevisionVent prev00 = loader.getPrevision(carte, date, "00", "06");
			meteo.ajouterPrevision(loader.getPrevision(carte, date, "00", "06"));
			meteo.ajouterPrevision(loader.getPrevision(carte, date, "00", "108"));
			meteo.ajouterPrevision(loader.getPrevision(carte, date, "00", "120"));
			meteo.ajouterPrevision(loader.getPrevision(carte, date, "00", "144"));
			
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
