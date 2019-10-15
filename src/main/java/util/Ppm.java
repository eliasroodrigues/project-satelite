package util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ppm {
	private static ArrayList pic;
	private static String matrix = "";
	private static int[] cores;
	private static int[][] matriz;

	/**
	 * @param args the command line arguments
	 */
	public static void ppmGenerate(String nome) throws IOException {
		createImage(50, 50);
		writeImage("src/files/" + nome);
	}

	public static void createImage(int width, int height){
		pic = new ArrayList();
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
			} 
			matrix += "\n";
		}   
	}
	
	public static int[] porcentagem(String nomeImagem) {
		String imagePath = "src/files/" + nomeImagem + ".ppm";
		cores = new int[3];
		
		File input = new File(imagePath);
		
		try {
			//DataInputStream in = new DataInputStream(new FileInputStream(input));
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(input)));
			
			in.readLine();
			int w = Integer.parseInt(in.readLine());
            int h = Integer.parseInt(in.readLine());
            System.out.println("W: " + w + "\tH: " + h);
            matriz = new int[w][h];
            System.out.println(in.readLine());
			
            /*while (in.readLine()) {
				String[] numbers = line.split("\\s+");
				for (int i = 0; i < numbers.length; i++){
					int rawloc = loc / 3;
					row = rawloc / numcolumns;
					column = rawloc % numcolumns;
					int color = loc % 3;
					if(color == 0) {
						cores[0] += 1;
					}else if( color ==1){
						cores[1] += 1;
					} else if (color ==2){
						cores[2] += 1;		
					}
					loc += 1;
				}
            }*/
            
            /*for (int i = 0; i < w * h; i++) {
            	int r = Integer.parseInt(in.readLine());
                int g = Integer.parseInt(in.readLine());
                int b = Integer.parseInt(in.readLine());
                
                if (r != 0) {
	    			cores[0] += 1;
	    		} else if (g != 0) {
	    			cores[1] += 1;
	    		} else if (b != 0) {
	    			cores[2] += 1;
	    		}
            }*/
            
            in.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return cores;
		
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