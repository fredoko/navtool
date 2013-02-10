package org.ffo.navtool.carte;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JCanvas jc = new JCanvas(new Point(1920,1080));
		jc.setBackground(Color.WHITE);
		jc.setPreferredSize(new Dimension(1920,1080));
		GUIHelper.showOnFrame(jc,"test");

	}

}
