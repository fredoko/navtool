/**
 * 
 */
package org.ffo.navtool.carte;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.ffo.navtool.gps.PointGps;



/**
 * 
 * <br>
 * @author administrator - 11 févr. 2013
 * <br>
 */
@Path("/dataMap")
public class DataMap {
    
    @GET
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON })
    public MapDataPart getZone(
            @QueryParam("gpsx") float gpsx, 
            @QueryParam("gpsy") float gpsy, 
            @QueryParam("zoom") float zoom, 
            @QueryParam("canvasx") int canvasx, 
            @QueryParam("canvasy") int canvasy) {
        MapDataPart retour = new MapDataPart();
        
        retour.setCenter(new PointGps(gpsy, gpsx));
        retour.setZoom(zoom);
        
        List<int[]> geoMarker = new ArrayList<int[]>();
        geoMarker.add(new int[]{41,54,84,98});
        geoMarker.add(new int[]{11,64,54,48});
        retour.setGeoLineMarker(geoMarker);
        return retour;
    }

}
