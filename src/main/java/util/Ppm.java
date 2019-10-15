package util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Ppm {
	private static ArrayList pic;
	private static String matrix = "";
	private static int[] cores;

	/**
	 * @param args the command line arguments
	 */
	public static void ppmGenerate(String nome) throws IOException {
		createImage(200, 138);
		writeImage("src/files/" + nome);
	}

	public static void createImage(int width, int height){
		pic = new ArrayList();
		cores = new int[3];
		int[] rgb = new int[3];
		matrix += "P3\n" + width + "\n" + height + "\n255\n";
		for (int i = 0; i <= height; i++) {
			for (int j = 0; j <= width; j++){ 
				Color c = getColor(width, height, j, i);
				if (c == Color.red) {
					rgb[0] = (int) (255*factor(width, height, j, i));
					rgb[1] = 0;
					rgb[2] = 0;
				} else if (c == Color.green) {
					rgb[0] = 0;
					rgb[1] = (int) (255*factor(width, height, j, i));
					rgb[2] = 0;
				} else if (c == Color.blue) {
					rgb[0] = 0;
					rgb[1] = 0;
					rgb[2] = 0;
				} else if (c == Color.white) {
					rgb[0] = (int) (255*factor(width, height, j, i));
					rgb[1] = (int) (255*factor(width, height, j, i));
					rgb[2] = (int) (255*factor(width, height, j, i));
				}
				matrix += ""+ rgb[0] + " " + rgb[1] + " " + rgb[2] + "  " ;
				if (rgb[0] != 0) {
					cores[0] += 1;
				} else if (rgb[1] != 0) {
					cores[1] += 1;
				} else if (rgb[2] != 0) {
					cores[2] += 1;
				}
			} 
			matrix += "\n";
		}   
	}

	public static Color getColor(int width, int height, int a, int b){
		double d1 = ((double) height / width) * a;
		double d2 = ((double) -height / width) * a + width;

		if(d1 > b && d2 > b) return Color.green;
		if(d1 > b && d2 < b) return Color.blue;
		if(d1 < b && d2 > b) return Color.red;
		return Color.white;
	}

	public static double  factor(int width, int height, int a, int b){
		double factorX = (double) Math.min(a, width - a) / width * 2;
		double factorY = (double) Math.min(b, height - b) / height * 2;

		return Math.min(factorX, factorY);
	}

	public static int[] qtdCores() {
		return cores;
	}

	public static void writeImage(String fn) throws FileNotFoundException, IOException {
		FileOutputStream fos = new FileOutputStream(fn);
		fos.write(new String(matrix).getBytes());
		fos.close();
	}
}