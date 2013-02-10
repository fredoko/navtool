package org.ffo.navtool.carte;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GenerateImage {

	 static public void main(String args[]) throws Exception {
		    try {
		    	int width = 1920 * 4, height = 1080 * 4;
		    	JCanvas jc = new JCanvas(new Point(width, height));
				jc.setBackground(Color.WHITE);
				jc.setPreferredSize(new Dimension(width,height));
		    	
		      

		      // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
		      // into integer pixels
		      BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		      Graphics2D ig2 = bi.createGraphics();
		      ig2.setBackground(Color.WHITE);		      
		      jc.dessinerOcean(ig2);

//		      Font font = new Font("TimesRoman", Font.BOLD, 20);
//		      ig2.setFont(font);
//		      String message = "www.java2s.com!";
//		      FontMetrics fontMetrics = ig2.getFontMetrics();
//		      int stringWidth = fontMetrics.stringWidth(message);
//		      int stringHeight = fontMetrics.getAscent();
//		      ig2.setPaint(Color.black);
//		      ig2.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);

		      ImageIO.write(bi, "PNG", new File("world8k1.PNG"));
//		      ImageIO.write(bi, "JPEG", new File("c:\\yourImageName.JPG"));
		      ImageIO.write(bi, "gif", new File("world8k1.gif"));
		      //ImageIO.write(bi, "BMP", new File("c:\\yourImageName.BMP"));
		      
		    } catch (IOException ie) {
		      ie.printStackTrace();
		    }

		  }

}
