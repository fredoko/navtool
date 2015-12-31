package org.ffo.navtool.bateau;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

public class LoadPolaireVR {
	
	
	
	public static void main(String[] args) {
		//PolaireBateau polaireVRStd = LoadPolaireVR.construirePolaireVR();
		
		Polaire polaireTree = LoadPolaireVR.construirePolaireTreeVR();
		
		Date debut = new Date();
		//System.out.println("Vitesse bateau:" + polaireVRStd.getVitesseBateau(140, 4.5));		
		System.out.println("Vitesse bateau Tree:" + polaireTree.getVitesseBateau(140F, 4.5F));
		System.out.println("Vitesse bateau Tree:" + polaireTree.getVitesseBateau(121F, 4.7F));
		
		System.out.println("Vitesse bateau Tree:" + polaireTree.getVitesseBateau(113F, 4.32F));
		System.out.println("Vitesse bateau Tree:" + polaireTree.getVitesseBateau(113.58F, 4.32F));
		System.out.println("Vitesse bateau Tree:" + polaireTree.getVitesseBateau(114F, 4.32F));
		
	}
	
	public LoadPolaireVR() {
		
	}
	
	public static Polaire construirePolaireTreeVR() {
		String path = "C:/travail/JeuVoile/vendeeglobe_std-full.csv";
		float[] vitesseVent;
		float[] vitesseBateau;
		int[] indexVoile;
		Polaire polaireVRStd = new Polaire();
		
		
		try {
			BufferedReader in  = new BufferedReader(new FileReader(path));
			//lire entete contenant les vitesses vent, la première entrée de la ligne vide est vide, séparateur ";"
			//les nombres ont un séparatuer décimal "."
			//ensuite chaque case est composé d'une vitesse de bateau et du nméro de voile
			String line = in.readLine();
			String[] ts =  line.split(";");
			vitesseVent = new float[ts.length -1];
			for (int i = 1; i < ts.length; i++) {
				vitesseVent[i-1] = Float.parseFloat(ts[i]);
			}
			
			int angle = -1;
			String[] vb_iv;
			
			//ensuite on parse chaque ligne de vitesse de bateau pour chaque angle
			while ( (line = in.readLine()) != null) {
				ts =  line.split(";");
				angle = Integer.parseInt(ts[0]);
				vitesseBateau = new float[ts.length -1];
				indexVoile = new int[ts.length -1];
				for (int i = 1; i < ts.length; i++) {
					vb_iv = ts[i].split(":");
					vitesseBateau[i-1] = Float.parseFloat(vb_iv[0]);
					indexVoile[i-1] = Integer.parseInt(vb_iv[1]);
				}
				
				//System.out.println("Add angle" + angle);
				polaireVRStd.addAngle((float) angle, vitesseVent, vitesseBateau, indexVoile);
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
	
	/**
	public static PolaireBateau construirePolaireVR() {
		String path = "C:/travail/JeuVoile/vendeeglobe_std-full.csv";
		double[] vitesseVent;
		double[] vitesseBateau;
		int[] indexVoile;
		PolaireBateau polaireVRStd = new PolaireBateau();
		
		
		try {
			BufferedReader in  = new BufferedReader(new FileReader(path));
			//lire entete contenant les vitesses vent, la première entrée de la ligne vide est vide, séparateur ";"
			//les nombres ont un séparatuer décimal "."
			//ensuite chaque case est composé d'une vitesse de bateau et du nméro de voile
			String line = in.readLine();
			String[] ts =  line.split(";");
			vitesseVent = new double[ts.length -1];
			for (int i = 1; i < ts.length; i++) {
				vitesseVent[i-1] = Double.parseDouble(ts[i]);
			}
			
			int dernierAngle = -1;
			int angle = -1;
			String[] vb_iv;
			
			//ensuite on parse chaque ligne de vitesse de bateau pour chaque angle
			while ( (line = in.readLine()) != null) {
				ts =  line.split(";");
				angle = Integer.parseInt(ts[0]);
				if (dernierAngle == -1) {
					dernierAngle = angle -1;
				}
				vitesseBateau = new double[ts.length -1];
				indexVoile = new int[ts.length -1];
				for (int i = 1; i < ts.length; i++) {
					vb_iv = ts[i].split(":");
					vitesseBateau[i-1] = Double.parseDouble(vb_iv[0]);
					indexVoile[i-1] = Integer.parseInt(vb_iv[1]);
				}
				if (angle !=  dernierAngle + 1) {
					//TODO interpolation sur les angles manquant
					for (int i = dernierAngle + 1; i < angle; i++) {
						
					}
					
				}
				//System.out.println("Add angle" + angle);
				polaireVRStd.addAngle(angle, vitesseVent, vitesseBateau, indexVoile);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return polaireVRStd;
	}
	*/

}
