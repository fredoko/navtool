package org.ffo.navtool.carte;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.ffo.navtool.gps.KmlPolygons;
import org.ffo.navtool.gps.PointGps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Cet objet permet de parse un fichier kml(xml) an sax afin de récupérer tous
 * les polygons de pays.
 * 
 * @author Fred
 * 
 */
/**
 * @author Fred
 *
 */
public class KmlCountryParser extends DefaultHandler {

	private static final Logger LOG = LoggerFactory
			.getLogger(KmlCountryParser.class);

	

	private KmlPolygons currentCountry;

	/** Stocke le contenu des balise. */
	private StringBuffer buffer;

	private List<KmlPolygons> polygonsList;

	private SAXParser parseur;
	private File fichier;
	
	private String reading;
	
	

	public KmlCountryParser() {
		try {
			SAXParserFactory fabrique = SAXParserFactory.newInstance();
			parseur = fabrique.newSAXParser();
			
		} catch (ParserConfigurationException e) {
			LOG.error("ParserConfigurationException", e);
		} catch (SAXException e) {
			LOG.error("SAXException", e);
		}
	}

	public void parse(String kmlPath) {
		try {
			polygonsList = new ArrayList<KmlPolygons>();
			fichier = new File(kmlPath);
			parseur.parse(fichier, this);
		} catch (SAXException e) {
			LOG.error("SAXException", e);
		} catch (IOException e) {
			LOG.error("IOException", e);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		if (qName.equals("Placemark")) {
			currentCountry = new KmlPolygons();
		} else if (qName.equals("SimpleData")) {
			if(attributes.getValue("name") != null) {				
				buffer = new StringBuffer();
				reading =attributes.getValue("name");				
			}
			
			//currentCountry.setIso3(attributes.getValue("ISO3"));
			
		} else if (qName.equals("coordinates")) {
			reading = "coord";
			buffer = new StringBuffer();
		} else if(qName.equals("LinearRing")) {
			//new Polygon			
			currentCountry.newPolygon();
			buffer = new StringBuffer();
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		String[] coord;
		String[] gpst;	
		String cs;
		// On ne détecte que la fin de la balise coordiantes et placemark
		if (qName.equals("Placemark")) {
			if (currentCountry != null) {
				polygonsList.add(currentCountry);
			}
		} else if (qName.equals("coordinates")) {
			cs = buffer.toString();
			coord = cs .split(" ");
			for (String gps : coord) {
				gpst = gps.split(",");
				currentCountry.add(new PointGps(Float.valueOf(gpst[1]), Float
						.valueOf(gpst[0])));
			}
		}  else if (qName.equals("SimpleData")) {
			if (reading.equals("Name")) {
				currentCountry.setName(buffer.toString());
			}
			if (reading.equals("ISO3")) {
				currentCountry.setCode(buffer.toString());
			}
			
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String lecture = new String(ch, start, length);
		if (buffer != null)
			buffer.append(lecture);
	}

	@Override
	public void startDocument() throws SAXException {
		LOG.info("Start coutry parsing");
	}

	@Override
	public void endDocument() throws SAXException {
		LOG.info("End parsing ");
	}

	public List<KmlPolygons> getPolygons() {
		return polygonsList;
	}

	public void setPolygonsList(List<KmlPolygons> paysList) {
		this.polygonsList = paysList;
	}
	
	
	/**
	 * Détermine les polygons pour le cadre donné.
	 * 
	 * @param sw
	 * @param ne
	 * @return
	 */
	public List<KmlPolygons> getPolygons(PointGps sw, PointGps ne) {
		List<KmlPolygons> res = null;
		
		return res;
	}

}
