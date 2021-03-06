import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Color;
import java.util.Random;
import java.awt.Rectangle;

public class Individual implements Comparable<Individual>{
	public int dnaLen;
	public Gene[] dna;
	public BufferedImage refImage;
	public BufferedImage myImage;
	public double fitness;

	Individual(int len, BufferedImage ref){
		refImage = ref;
		dnaLen = len;
		dna = new Gene[dnaLen];

		int w = refImage.getWidth(), h = refImage.getHeight();
		Random rndGen = new Random( System.currentTimeMillis() );

		for(int i = 0; i < dnaLen; i++){
			int[] x = new int[3];
			int[] y = new int[3];

			for(int j = 0; j < 3; j++){
				x[j] = rndGen.nextInt(w);
				y[j] = rndGen.nextInt(h); 
			}

			int r = rndGen.nextInt(256);
			int g = rndGen.nextInt(256);
			int b = rndGen.nextInt(256);
			int a = rndGen.nextInt(256);

			dna[i] = new Gene(new Polygon(x, y, 3), new Color(r, g, b, a));
		}

		createImage();
		calculateFitness();
	}

	Individual(Individual mother, Individual father, double mutRatio, double mutAmount){
		refImage = mother.refImage;
		dnaLen = mother.dnaLen;
		dna = new Gene[dnaLen];
		int w = refImage.getWidth(), h = refImage.getHeight();

		for(int i = 0; i < dnaLen; i++){
			if(Math.random() < 0.5)
				dna[i] = mother.dna[i].mutate(w, h, mutRatio, mutAmount);
			else
				dna[i] = father.dna[i].mutate(w, h, mutRatio, mutAmount);
		}

		createImage();
		calculateFitness();
	}

	private void createImage(){
		myImage = new BufferedImage(refImage.getWidth(), refImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = myImage.createGraphics();
		g2d.setColor(new Color(0,0,0));
		g2d.fill(new Rectangle(refImage.getWidth()-1,refImage.getHeight()-1));
		for(int i = 0; i < dnaLen; i++){
			g2d.setColor( dna[i].getColor() );
			g2d.fill( dna[i].getTrian() );
		}
		g2d.dispose();
	}

	private void calculateFitness(){
		int w = refImage.getWidth(), h = refImage.getHeight();
		double diff = 0.0;
		for(int x = 0; x < w; x++)
			for(int y = 0; y < h; y++){
				Color c1 = new Color(refImage.getRGB(x, y), true);
				Color c2 = new Color(myImage.getRGB(x,y), true);
				diff += Math.abs(c1.getBlue() - c2.getBlue());
				diff += Math.abs(c1.getGreen() - c2.getGreen());
				diff += Math.abs(c1.getRed() - c2.getRed());
			}

		fitness = 1.0 - (diff / (w * h * 3 * 256));
	}

	public int compareTo(Individual i){
		if(this.fitness>i.fitness) return 1;
		else if(this.fitness<i.fitness) return -1;
		else return 0;
	}
}