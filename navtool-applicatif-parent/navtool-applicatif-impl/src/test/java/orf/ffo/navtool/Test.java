package orf.ffo.navtool;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getY(88));
		System.out.println(getY(88.59));
		System.out.println(getY(1));
		System.out.println(getY(15));
		System.out.println(getY(30));
		

	}
	
	public static double getY(double angle) {
		//return Math.log(Math.tan(Math.PI/4 + Math.toRadians(angle)/2));
		return (Math.tan(Math.PI/4 + Math.toRadians(angle)/2));
	}

}
