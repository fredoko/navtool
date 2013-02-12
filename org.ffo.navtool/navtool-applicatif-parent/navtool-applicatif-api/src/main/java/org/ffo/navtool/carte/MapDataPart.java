/**
 * 
 */
package org.ffo.navtool.carte;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.ffo.navtool.gps.PointGps;

/**
 * Contient des elements geographique de carte à afficher
 *  - limite/zone de terre
 *  - limite administrative
 *  - zone hauteur d'eau
 *  - trace geo (meridien et parallèle)
 * 
 * Est identifié par un point GPS (le centre) et un degrés de zoom. 
 * Les données sont des points GPS projetés et "pixelisé", cet objet est donc très près du dessin 
 * et permet donc un affichage direct des points/polygon/polylogne.
 * L'information de type de projection et de taille de canvas est transmit lors de la requête, 
 * 
 * En théorie cet objet est utilisable quelque soit la projection. Il défini un carré sur une sphère
 * dont le centre est le point GPS et le zoom la largeur. Les 2 sont exprimés en degrès décimaux.  
 * <br>
 modif pour commit
 * @author administrator - 11 févr. 2013
 * <br>
 */
@XmlRootElement(name = "mapDataPart")
public class MapDataPart implements Serializable {
    
    /** Center of the square.*/
    private PointGps center;
    
    /** square length .*/
    private float zoom;
    
    /** Polygons list, a polygon is a [x,y] list.*/
    private List<List<int[]>> landZones;
    
    /** Polygons list, a polygon is a [x,y] list.*/
    private List<List<int[]>> waterZones;
    
    /** line list, list of [x1,y1,x2,y2] .*/
    private List<int[]> geoLineMarker;
    
    /** 4 points list that define the 4 edges of the zone.  */
    private List<int[]> edgePoint;

    /**
     * Getter de center.
     * @return center
     */
    public PointGps getCenter() {
        return center;
    }

    /**
     * Setter de center.
     * @param pCenter center à attribuer
     */
    public void setCenter(PointGps pCenter) {
        center = pCenter;
    }

    /**
     * Getter de zoom.
     * @return zoom
     */
    public float getZoom() {
        return zoom;
    }

    /**
     * Setter de zoom.
     * @param pZoom zoom à attribuer
     */
    public void setZoom(float pZoom) {
        zoom = pZoom;
    }

    /**
     * Getter de landZones.
     * @return landZones
     */
    public List<List<int[]>> getLandZones() {
        return landZones;
    }

    /**
     * Setter de landZones.
     * @param pLandZones landZones à attribuer
     */
    public void setLandZones(List<List<int[]>> pLandZones) {
        landZones = pLandZones;
    }

    /**
     * Getter de waterZones.
     * @return waterZones
     */
    public List<List<int[]>> getWaterZones() {
        return waterZones;
    }

    /**
     * Setter de waterZones.
     * @param pWaterZones waterZones à attribuer
     */
    public void setWaterZones(List<List<int[]>> pWaterZones) {
        waterZones = pWaterZones;
    }

    /**
     * Getter de geoLineMarker.
     * @return geoLineMarker
     */
    public List<int[]> getGeoLineMarker() {
        return geoLineMarker;
    }

    /**
     * Setter de geoLineMarker.
     * @param pGeoLineMarker geoLineMarker à attribuer
     */
    public void setGeoLineMarker(List<int[]> pGeoLineMarker) {
        geoLineMarker = pGeoLineMarker;
    }
    
    

}
