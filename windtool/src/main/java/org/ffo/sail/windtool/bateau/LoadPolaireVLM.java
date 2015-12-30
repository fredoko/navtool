package org.ffo.sail.windtool.bateau;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * Utilisation du format de Polaire virtual loup de mer, http://wiki.virtual-
 * loup-de-mer.org/index.php/Fournir_des_polaires_de_vitesse Liste de polaire
 * existante http://www.virtual-loup-de-mer.org/Polaires/
 *
 * Le format est :
 * <ul>
 * <li>extension du nom de fichier en .csv</li>
 * <li>séparateur ; (point-virgule)</li>
 * <li>tableau à double entrée
 * <ul>
 * <li>la première cellule contient toujours TWA\TWS</li>
 * <li>la première ligne liste les forces de vent. Vlm ne va pas au dela de 60
 * noeuds de vent.</li>
 * <li>chaque début de ligne donne l'allure, puis chaque valeur correspondant à
 * l'allure et la force du vent (tête de colonne)</li>
 * </ul>
 * </li>
 * <li>les données numériques de la polaires utilisent un séparateur décimal,
 * c'est à dire le point. Un entier (sans point termina) est valide.</li>
 * <li>les blancs sont valides (valeur = 0.0) mais déconseillé. Merci d'être
 * explicite</li>
 * <li>le format texte du fichier est UNIX c'est à dire que les lignes sont
 * terminées par des LF (Line Feed) et non par CR (Carriage Return) + LF. Un bon
 * éditeur de texte sous windows (PsPad ou Notepad++) sait réenregistrer sous ce
 * format.Si la ligne précédente est obscure, signalez que vous ne le savez pas
 * en adressant votre fichier.</li>
 * </ul>
 *
 * @author Frederic Foret - IMA&#8482
 */
public class LoadPolaireVLM {

    public static void main(String[] args) {
        // PolaireBateau polaireVRStd = LoadPolaireVR.construirePolaireVR();

        LoadPolaireVLM vlm = new LoadPolaireVLM();

        Polaire polaireTree;
        try {
            polaireTree = vlm.loadPolaire(new FileInputStream("src/main/resources/boat_imoca60.csv"));
            System.out.println("Vitesse bateau Tree 140°, 4.5 nds:" + polaireTree.getVitesseBateau(140F, 4.5F));
            System.out.println("Vitesse bateau Tree 121°, 4.7 nds:" + polaireTree.getVitesseBateau(121F, 4.7F));
            System.out.println("Vitesse bateau Tree 135°, 30 nds:" + polaireTree.getVitesseBateau(135F, 30F));

            System.out.println("Vitesse bateau Tree 113°, 4.32 nds:" + polaireTree.getVitesseBateau(113F, 4.32F));
            System.out.println("Vitesse bateau Tree 113.58°, 4.32 nds:" + polaireTree.getVitesseBateau(113.58F, 4.32F));
            System.out.println("Vitesse bateau Tree 114°, 4.32 nds:" + polaireTree.getVitesseBateau(114F, 4.32F));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public LoadPolaireVLM() {

    }

    public Polaire loadPolaire(InputStream inputStream) {

        Polaire polaireVRStd = new Polaire();
        float[] vitesseVent;
        float[] vitesseBateau;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            // lire entete contenant les vitesses vent, la première entrée de la
            // ligne vide est vide, séparateur ";"
            // les nombres ont un séparatuer décimal "."
            // ensuite chaque case est composé d'une vitesse de bateau et du
            // nméro de voile
            String line = in.readLine();
            String[] ts = line.split(";");
            vitesseVent = new float[ts.length - 1];
            for (int i = 1; i < ts.length; i++) {
                vitesseVent[i - 1] = Float.parseFloat(ts[i]);
            }

            int angle = -1;
            String[] vb_iv;

            // ensuite on parse chaque ligne de vitesse de bateau pour chaque
            // angle
            while ((line = in.readLine()) != null) {
                ts = line.split(";");
                angle = Integer.parseInt(ts[0]);
                vitesseBateau = new float[ts.length - 1];
                for (int i = 1; i < ts.length; i++) {
                    vitesseBateau[i - 1] = Float.parseFloat(ts[i]);
                }

                // System.out.println("Add angle" + angle);
                polaireVRStd.addAngle(angle, vitesseVent, vitesseBateau);
            }
            in.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return polaireVRStd;
    }

}
