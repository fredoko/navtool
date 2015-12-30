package org.ffo.sail.windtool.meteo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.ffo.sail.windtool.Carte;

/**
 * @author Fred
 *
 */
public class LoadPrevisionGrib2 {

    /**
     * Chaine de récupération des fichiers.
     * file=gfs.t00z.pgrbf06.grib2&lev_10_m_above_ground
     * =on&var_UGRD=on&var_VGRD=
     * on&leftlon=-179&rightlon=180&toplat=0&bottomlat=-
     * 70&dir=%2Fgfs.2012122300";<br>
     * file=gfs.t00z.pgrbf[tranche].grib2&lev_10_m_above_ground
     * =on&var_UGRD=on&var_VGRD
     * =on&leftlon=[ouestLon]&rightlon=[estlon]&toplat=[nordlat
     * ]&bottomlat=[sudLat]&dir=%2Fgfs.[jour]";
     *
     * Chaine exemple de
     * http://nomads.ncep.noaa.gov/txt_descriptions/grib_filter_doc.shtml <br>
     * http://nomads.ncep.noaa.gov/cgi-bin/filter_gfs_1p00.pl?file=gfs.t06z.
     * pgrb2
     * .1p00.f000&lev_10_m_above_ground=on&var_UGRD=on&var_VGRD=on&leftlon=0
     * &rightlon=360&toplat=90&bottomlat=-90&dir=%2Fgfs.2015123006
     * <ul>
     * <li>[previs] : 00, 06, 12, 18. les prévision sont mises à jour toutes les
     * 6 heures. Ce code indique l'heure de sortie du fichier, donc minuit, 6h,
     * 12h et 18h GMT.</li>
     * <li>[tranche] : La prévision dans le temps est faite de 3 heure en 3
     * heures. Cet indicateur sur 3 indique la tranche de temps voulue. de 000 à
     * 384. Cette "heure" est à ajouter à l'heure [previs] et [jour] pour
     * obtenir la vrai date de prévision</li>
     * <li>[jour] : jour au format yyyyMMdd, indiquant le jour auquel [previs]
     * se rapporte.</li>
     * <li>lat/lon : le rectangle désiré</li>
     * <li>Noter qu'on ne récupère que le vent à 10 mètre du sol, avec une
     * résolution de 1 degrès (on peut avoir à 0.25 degrès près). Un fichier
     * fait 150ko</li>
     * <ul>
     *
     */
    public static String source = "http://nomads.ncep.noaa.gov/cgi-bin/filter_gfs_1p00.pl?file=gfs.t[previs]z.pgrb2.1p00.f[tranche]&lev_10_m_above_ground=on&var_UGRD=on&var_VGRD=on&leftlon=[ouestLon]&rightlon=[estlon]&toplat=[nordlat]&bottomlat=[sudLat]&dir=%2Fgfs.[jour][previs]";

    public final static SimpleDateFormat spf = new SimpleDateFormat("yyyyMMdd");
    public final static SimpleDateFormat spf_heure = new SimpleDateFormat("yyyyMMdd-HH");

    /**
     * Stocke la correspondance entre le delais Grib et le nom d'heure.
     */
    private Map<String, Integer> delaisHeure = new HashMap<String, Integer>();

    // Variable necessaire à la construction du bean
    /**
     * Chemin variable en fonction de l'environnment d'installation.
     */
    private String cheminArchivage = "C:/travail/JeuVoile/archiveGrib/";

    public static void main(String[] args) {
        // Carte carte = new Carte(1,-45,-55,-136,177);
        //
        // try {
        // Date date = spf.parse("20121225");
        // PrevisionVent prev00 = getPrevision(carte, date, "00", "06");
        //
        // } catch (ParseException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // Pour Virtual regatta au changement de 8h (CET donc 7h UTC), les
        // previsions sont celles générées à minuit utc (00),
        // la méteo "temps réel" de 8h00 CET est la prévision de 6h utc(06),
        // donc 7h CET
        // On a donc un décalage de une heure au départ, mais ce n'ets rien avec
        // le fait que pendant 12 heure on garde la même prévision
        // VR aurait pu prendre les fichiers générés à 6h00 utc (soit 7h CET)
        // mais ca devait faire trop court pour les integrer dans leur système.

        // PrevisionVent prev12 = getPrevision(carte, date, "00", "108");
        // PrevisionVent prev24 = getPrevision(carte, date, "00", "120");
        // PrevisionVent prev36 = getPrevision(carte, date, "00", "132");
        // PrevisionVent prev48 = getPrevision(carte, date, "00", "144");

        // gribToCsv(cheminArchivage + "20121225/" + "gfs.t00z.pgrbf06.grib2");
        // gribToCsv(cheminArchivage + "20121225/" + "gfs.t00z.pgrbf108.grib2");
        // gribToCsv(cheminArchivage + "20121225/" + "gfs.t00z.pgrbf120.grib2");
        // gribToCsv(cheminArchivage + "20121225/" + "gfs.t00z.pgrbf132.grib2");
        // gribToCsv(cheminArchivage + "20121225/" + "gfs.t00z.pgrbf144.grib2");

    }

    public LoadPrevisionGrib2() {
        delaisHeure.put("00", 0);
        delaisHeure.put("03", 3);
        delaisHeure.put("06", 3);
        delaisHeure.put("09", 9);
        delaisHeure.put("102", 12);
        delaisHeure.put("105", 15);
        delaisHeure.put("108", 18);
        delaisHeure.put("111", 21);
        delaisHeure.put("114", 24);
        delaisHeure.put("117", 27);
        delaisHeure.put("120", 30);
        delaisHeure.put("123", 33);
        delaisHeure.put("126", 36);
        delaisHeure.put("129", 39);
        delaisHeure.put("132", 42);
        delaisHeure.put("135", 45);
        delaisHeure.put("138", 48);
        delaisHeure.put("141", 51);
        delaisHeure.put("144", 54);
        delaisHeure.put("147", 57);
        delaisHeure.put("150", 60);
        delaisHeure.put("153", 63);
        delaisHeure.put("156", 66);
        delaisHeure.put("159", 69);
        delaisHeure.put("162", 72);
        delaisHeure.put("165", 75);
        delaisHeure.put("168", 78);
        delaisHeure.put("171", 81);
        delaisHeure.put("174", 84);
        delaisHeure.put("177", 87);
        delaisHeure.put("180", 90);
        delaisHeure.put("183", 93);
        delaisHeure.put("186", 96);
        delaisHeure.put("189", 99);
        delaisHeure.put("192", 102);
        delaisHeure.put("204", 114);
        delaisHeure.put("216", 126);
        delaisHeure.put("228", 138);
        delaisHeure.put("240", 150);
        delaisHeure.put("252", 162);

    }

    /**
     * Récupère les prévisions faites à la date datecreation, pour la tranche
     * indiquée
     *
     * @param carte
     *            carte sous jacente (contient la précision et le rectangle)
     * @param dateCreation
     * @param tranche
     *            00 correspond à la tranche [dateCreation, dateCreation + 3h],
     *            06 à [dateCreation + 6h, dateCreation + 9h], noter qu'on
     * @return une representation objet de la carte
     */
    public PrevisionVent getPrevision(Carte carte, Date jourCreation, String heureCreation, String tranche) {
        String dateS = spf.format(jourCreation);
        String nomFichierGrib = "gfs.t" + heureCreation + "z.pgrbf" + tranche + ".grib2";
        String pathGrib = cheminArchivage + dateS + "/" + nomFichierGrib;
        String pathUGRD = cheminArchivage + dateS + "/gfs.t" + heureCreation + "z.pgrbf" + tranche + "-UGRD.csv";
        String pathVGRD = cheminArchivage + dateS + "/gfs.t" + heureCreation + "z.pgrbf" + tranche + "-VGRD.csv";

        // GetHttp
        // File archive = new File(cheminArchivage + date);
        // archive.mkdir();

        // transformation wgrib2 to csv
        // gribToCsv(pathGrib);

        // Le fichier csv contient
        // -la longitude en 5 position
        // - la latitude en 6 position
        // - la vitesse en m/s en 7 position
        // les deux fichiers sont ordonnés dans le même sens

        PrevisionVent prev = new PrevisionVent();
        try {
            // dateCreation c'est le jour de creation plus l'heure de creation
            // 00,06,12 ou 18 (toutes les 6 heures pour les fichiers Grib2.
            prev.setDateCreationPrevision(spf_heure.parse(spf.format(jourCreation) + "-" + heureCreation));
            // datePrevision, c'est la date de creation plus un delais nommé ici
            // tranche (au minimum de 3 heures en heures)
            GregorianCalendar greg = new GregorianCalendar();
            greg.setTime(prev.getDateCreationPrevision());
            greg.add(Calendar.HOUR, delaisHeure.get(tranche));
            prev.setDatePrevisionDebut(greg.getTime());

        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        // Date datePrevision = spf_heure.parse(spf.format(jourCreation) + "-" +
        // heureCreation);
        prev.setCarte(carte);

        // prev.setPeriodeValidite(12); //on garde la même prévision pendant 12h
        // prev.setDatePrevisionDebut(dateCreation + 6heure);

        try {
            BufferedReader inUGRD = new BufferedReader(new FileReader(pathUGRD));
            BufferedReader inVGRD = new BufferedReader(new FileReader(pathVGRD));
            String lineLat;
            String lineLon;
            String[] tLat;
            String[] tLon;
            float lat;
            float lon;
            CaseVent cVent;
            float vLat;
            float vLon;
            while ((lineLat = inUGRD.readLine()) != null) {
                lineLon = inVGRD.readLine();
                tLat = lineLat.split(",");
                tLon = lineLon.split(",");
                vLat = Float.valueOf(tLat[6]);
                vLon = Float.valueOf(tLon[6]);
                lat = Float.valueOf(tLat[5]);
                lon = Float.valueOf(tLat[4]);
                cVent = new CaseVent();
                // la vitesse vaut la longueur du vecteur représentée par les
                // deux composantes
                cVent.setVitesse((float) Math.sqrt(vLat * vLat + vLon * vLon));

                // l'angle vaut arctan côté opposé sur côté adjacent soit arctan
                // (lat/lon)
                // le réferentiel n'est pas le même, en effet la latitude va du
                // sud vers le nord et la longitude d'ouest en est, donc l'angle
                // résultant
                // tourne dans le sens inverse des aiguilles d'une montre et le
                // zéro est ouest=>est
                // En se situant dans le référentiel standard de navigation
                // (positif dans le sens des aiguilles d'une montre et zéro nord
                // => sud)
                // l'angle calculé a son zéro décalé de 270°, et le sens de
                // rotation est inversé
                cVent.setAngle((float) (180 * (3 * Math.PI / 2 - Math.atan(vLon / vLat)) / Math.PI));
                prev.addCaseVent(cVent, lat, lon);
            }

            inUGRD.close();
            inVGRD.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return prev;
    }

    /**
     * Extrait deux fichiers CSV du fichier grib2 telechargé. Le premier
     * contient la composant UGRD (latitude) de la vitesse du vent, le second
     * contient la composante VGRD(longitude). C'est deux composantes permettent
     * de retrouver l'angle et la direction.
     *
     * @param path
     *            chemine du fichcier grib2
     */
    public static void gribToCsv(String path) {
        File gribFile = new File(path);
        String target = gribFile.getParent() + "/" + gribFile.getName().substring(0, gribFile.getName().length() - 6);
        String commandeUGRD = "wgrib2 -match \":UGRD\" " + path + " -csv " + target + "-UGRD.csv";
        String commandeVGRD = "wgrib2 -match \":VGRD\" " + path + " -csv " + target + "-VGRD.csv";
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(commandeUGRD);
            runtime.exec(commandeVGRD);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // runtime.exec(paramVGRD, null, repertoireTravail);

    }

    /**
     * récupère et sauvegarde le fichier de prévision dans cheminArchivage selon
     * la notation du provider prefixé avec la date de creation soit par exemple
     * 20121223-gfs.t00z.pgrbf06.grib2 pour une prévision faite le 2012/12/23 à
     * 00h pour la tranche 6h-9h
     *
     * @param dateCreation
     * @param tranche
     * @param precision
     */
    public static void getHttpForecast(Date dateCreation, String heureCreation, String tranche, int precision) {

    }

}
