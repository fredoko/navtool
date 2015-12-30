package org.ffo.sail.windtool.bateau;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LiveSkipperToVLM {

    public static void main(String[] args) {

        LiveSkipperToVLM vlm = new LiveSkipperToVLM();
        vlm.convertir("src/main/resources/Polaire-LSK Maxi-Trimaran XL-min.xml", "src/main/resources/");

    }

    /**
     * Converti des polaires récupérées avec firebug exprimée en xml. Contient
     * les polaires de toutes les voiles, y compris la voile auto. <br>
     * parse le fichier xml et sort un fichier par voile au format VLM) <br>
     * le format du fichier est name_sailname.csv <br>
     * le fichier est formé de telle sorte qu'il faut constituer tout les
     * fichiers à la fois angle par angle.
     *
     * @param pathSource
     *            : fichier xml contenant les polaires (réucpérée du cache web
     *            liveskipper).
     * @param repCible
     *            , répertoire où stocker les polaires,
     */
    public void convertir(String pathSource, String repCible) {
        try {

            File fXmlFile = new File(pathSource);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            String boatName;
            float factor;
            // l'index de la voile dans le fichier commence à 1;
            String[] sailNames;
            BufferedWriter[] sailFile;

            // optional, but recommended
            // read this -
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("name");
            boatName = nList.item(0).getTextContent();
            nList = doc.getElementsByTagName("factor");
            factor = Float.parseFloat(nList.item(0).getTextContent());
            System.out.println("Baot name & factor:" + boatName + " & " + factor);

            NodeList sailNodes = doc.getElementsByTagName("sailname");
            sailNames = new String[sailNodes.getLength()];
            sailFile = new BufferedWriter[sailNodes.getLength()];
            int nbSail = sailNodes.getLength();
            for (int i = 0; i < sailNodes.getLength(); i++) {
                Node nNode = sailNodes.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    sailNames[i] = eElement.getAttribute("name");
                    System.out.println("SailName :" + sailNames[i]);

                    sailFile[i] = new BufferedWriter(new FileWriter(repCible + "/" + boatName + "_" + sailNames[i]
                            + ".csv"));
                }
            }

            // Les thresholds nous donne toutes les vitesses et permet donc de
            // créer l'entête de tous les fichiers
            NodeList thresholds = doc.getElementsByTagName("threshold");
            String speed;
            String header = "TWA\\TWS";
            for (int i = 0; i < thresholds.getLength(); i++) {
                Node nNode = thresholds.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    speed = eElement.getAttribute("value");
                    header = header + ";" + speed;
                }
            }
            for (BufferedWriter bw : sailFile) {
                bw.write(header);
                bw.newLine();
                bw.flush();
            }

            // Maintenant on reparcourt les threshold qui contiennent pour
            // chaque voile tous les angles.
            // Pour chaque treeshold on récupère une colonne de vitesse pour les
            // angle allant de 0 à 180 de 5 en 5, soit 37 valeurs
            // On fabrique un tableau dont la seconde dimension est le treeshold
            // puis l'angle. On inversera le tableau ensuite dans une autre
            // double boucle
            // La première dimension est la voile
            NodeList sailDetail;
            NodeList speedsForTreeShold;
            float fSpeed;
            String speeds[][][] = new String[sailNames.length][thresholds.getLength()][37];
            for (int iThreeshold = 0; iThreeshold < thresholds.getLength(); iThreeshold++) {
                Node nNode = thresholds.item(iThreeshold);
                sailDetail = nNode.getChildNodes();
                for (int iSail = 0; iSail < sailDetail.getLength(); iSail++) {
                    Node nSail = sailDetail.item(iSail);
                    speedsForTreeShold = nSail.getChildNodes();
                    for (int iSpeed = 0; iSpeed < speedsForTreeShold.getLength(); iSpeed++) {

                        speeds[iSail][iThreeshold][iSpeed] = speedsForTreeShold.item(iSpeed).getTextContent();
                    }
                }
            }
            String line;
            for (int iSail = 0; iSail < nbSail; iSail++) {
                for (int angle = 0; angle < 37; angle++) {
                    line = "" + (angle * 5);
                    for (int iThreeshold = 0; iThreeshold < thresholds.getLength(); iThreeshold++) {
                        fSpeed = Float.parseFloat(speeds[iSail][iThreeshold][angle]) * factor;
                        line = line + ";" + fSpeed;
                    }
                    sailFile[iSail].write(line);
                    sailFile[iSail].newLine();
                    sailFile[iSail].flush();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
