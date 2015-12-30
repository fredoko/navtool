package org.ffo.sail.windtool.carto;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.ffo.sail.windtool.PointGps;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KmlCountryParserTest {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(KmlCountryParserTest.class);
	
	private static final Logger LOGJ = LoggerFactory.getLogger("JSON");

	@Test
	public void testParse() {
		//String kmlFile = "C:/travail/JeuVoile/worldsataset/2012-12-28-18-42-16_sgw4qWorldKML/TM_WORLD_BORDERS-0.3.kml";
		String kmlFile = "C:/travail/JeuVoile/worldsataset/ne_110m_ocean/ne_110m_ocean.kml";
		KmlCountryParser parser = new KmlCountryParser();
		
		Date debut = new Date();
		parser.parse(kmlFile);
		LOG.debug("time:{}", new Date().getTime() - debut.getTime());
		List<Pays> liste = parser.getPaysList();
		int cpt = 1;
		int nbPoint = 0;
		for (Pays pays : liste) {
			//LOG.debug("{}:{}:{}",new Object[] {cpt++,pays.getName(), pays.getPolygon().size()});
			
			for(List<PointGps> polygon : pays.getPolygons()) {
				nbPoint += polygon.size();
			}
			
			
			
			if(pays.getIso3().equals("PA1") || pays.getIso3().equals("PA2") ) {
				String json="";
				cpt = 1;
				LOG.info("nb Polygon=" + pays.getPolygons().size() );
				for(List<PointGps> polygon : pays.getPolygons()) {
					json="";
					for (PointGps gps : polygon) {
						json += "[" + round(gps.getLatitude()) +  "," + round(gps.getLongitude()) + "],";	
					}
					//json = json.substring(0,json.length() - 1);
					LOGJ.debug(json);
					LOG.debug("" + cpt++ );
					//json += "],";
				}
				//json = json.substring(0,json.length() - 1);
				//LOG.info(json);
			}
		}
		
		
		LOG.debug("nbPoints:{}", nbPoint);
		
	}
	
	private double round(double value) {
		return Math.rint(Math.toRadians(value)*10000)/10000;
	}

	
	
}
