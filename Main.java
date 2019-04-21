import java.util.Scanner;
import java.util.Arrays;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;
import java.io.File;
import java.io.IOException;

public class Main{
	public static void main(String args[]) throws IOException {

		Scanner reader = new Scanner(System.in);

		System.out.println("Numero de generaciones:");
		int gen = reader.nextInt();

		System.out.println("Numero de individuos por generacion:");
		int n = reader.nextInt();

		System.out.println("Numero de individuos a considerar para siguiente generación:");
		int s = reader.nextInt();

		System.out.println("Numero de triangulos por individuo:");
		int tri = reader.nextInt();

		System.out.println("Probabilidad de mutación:");
		double mutR = reader.nextDouble();

		System.out.println("Cantidad de mutación:");
		double mutA = reader.nextDouble();

		System.out.println("Nombre del archivo de la imagen a recrear:");
		reader.nextLine();

		String file = reader.nextLine();
		BufferedImage img = null;
		img = ImageIO.read(new File(file));
		Random rndGen = new Random( System.currentTimeMillis() );
		
		Individual[] ind = new Individual[n];
		Individual[] old = new Individual[n];
		
		int i,j,m,f;

		for(i = 0; i < n; i++)
			ind[i] = new Individual(tri,img);
		
		for(j = 0; j < gen; j++){
			for(i = 0; i < n; i++)
				old[i]=ind[i];

			Arrays.sort(old);
			System.out.println("Gen "+j+": "+old[n-1].fitness+", "+old[n-2].fitness+",... , "+old[n-s].fitness+",... , "+old[0].fitness);
			if(j%10 == 0){
				File output = new File("gen"+j+".png");
				ImageIO.write(old[n-1].myImage, "png", output);
			}

			for(i = 0; i < n; i++){
				if(i<s) ind[i]=old[n-1-i];
				else{
					m = rndGen.nextInt(s);
					f = rndGen.nextInt(s);
					ind[i] = new Individual(old[n-1-m], old[n-1-f], mutR, mutA);
				}
			}
		}
		Arrays.sort(ind);
		System.out.println("Gen "+j+": "+ind[n-1].fitness+", "+ind[n-2].fitness+",... , "+ind[0].fitness);
		File output = new File("image.png");
		ImageIO.write(ind[n-1].myImage, "png", output);
	}
}