package org.ffo.sail.windtool.routage;

import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Date;

import org.ffo.sail.windtool.Carte;
import org.ffo.sail.windtool.Cte;
import org.ffo.sail.windtool.PointGps;
import org.ffo.sail.windtool.bateau.LoadPolaireVLM;
import org.ffo.sail.windtool.bateau.Polaire;
import org.ffo.sail.windtool.meteo.LoadPrevisionGrib2;
import org.ffo.sail.windtool.meteo.Meteo;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoutageTest {

    private static final Logger LOG = LoggerFactory.getLogger(RoutageTest.class);

    private static Routage routage;

    @BeforeClass
    public static void initTest() {

        LoadPolaireVLM vlm = new LoadPolaireVLM();

        try {
            routage = new Routage();
            Meteo meteo = new Meteo();
            Polaire polaire = vlm.loadPolaire(new FileInputStream("src/main/resources/LSK Maxi-Trimaran XL_Auto.csv"));

            routage.setMeteo(meteo);
            routage.setPolaire(polaire);

            LoadPrevisionGrib2 loader = new LoadPrevisionGrib2();
            Carte carte = new Carte(1, -45, -55, -136, 177);

            Date date = LoadPrevisionGrib2.spf.parse("20121225");
            // PrevisionVent prev00 = loader.getPrevision(carte, date, "00",
            // "06");
            meteo.ajouterPrevision(loader.getPrevision(carte, date, "00", "06"));
            meteo.ajouterPrevision(loader.getPrevision(carte, date, "00", "108"));
            meteo.ajouterPrevision(loader.getPrevision(carte, date, "00", "120"));
            meteo.ajouterPrevision(loader.getPrevision(carte, date, "00", "144"));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    @Test
    public void testCalculTemps() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetVitesseBateau() {
        Date date;
        try {
            PointGps p = new PointGps(-52.25F, -175.25F);
            date = LoadPrevisionGrib2.spf_heure.parse("20121225-08");
            float v = routage.getVitesseBateau(p, date, 83F);
            LOG.debug("Vitesse {} km/h", v * Cte.MSenKM);
            LOG.debug("Vitesse {} noeuds", v * Cte.MSenMILLEMARIN);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
