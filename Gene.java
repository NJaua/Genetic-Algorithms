import java.awt.Polygon;
import java.awt.Color;

public class Gene{
	protected Polygon triangle;
	protected Color color;

	Gene(Polygon t, Color c){
		triangle = t;
		color = c;
	}

	Polygon getTrian(){ return triangle; }
	Color getColor(){ return color; }

	public Gene mutate(int w, int h, double mutRatio, double mutAmount){
		int[] x = new int[3];
		int[] y = new int[3];
		for(int i = 0; i < 3; i++){
			x[i] = triangle.xpoints[i];
			y[i] = triangle.ypoints[i];

			if(Math.random() < mutRatio){
				int deltax = (int) (mutAmount * Math.random() * w);
				
				if(Math.random() < 0.5)
					x[i] += deltax;
				else
					x[i] -= deltax;

				x[i] = Math.max(0, x[i]);
				x[i] = Math.min(w-1, x[i]);
			}

			if(Math.random() < mutRatio){
				int deltay = (int) (mutAmount * Math.random() * w);

				if(Math.random() < 0.5)
					y[i] += deltay;
				else
					y[i] -= deltay;

				y[i] = Math.max(0, y[i]);
				y[i] = Math.min(h-1, y[i]);
			}
		}

		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		int a = color.getAlpha();

		if(Math.random() < mutRatio){
			int delta = (int) (mutAmount * Math.random() * 256);
			if(Math.random() < 0.5) r += delta;
			else r -= delta;

			r = Math.max(0, r);
			r = Math.min(255, r);
		}
		
		if(Math.random() < mutRatio){
			int delta = (int) (mutAmount * Math.random() * 256);
			if(Math.random() < 0.5) g += delta;
			else g -= delta;

			g = Math.max(0, g);
			g = Math.min(255, g);
		}

		if(Math.random() < mutRatio){
			int delta = (int) (mutAmount * Math.random() * 256);
			if(Math.random() < 0.5) b += delta;
			else b -= delta;

			b = Math.max(0, b);
			b = Math.min(255, b);
		}

		if(Math.random() < mutRatio){
			int delta = (int) (mutAmount * Math.random() * 256);
			if(Math.random() < 0.5) a += delta;
			else a -= delta;

			a = Math.max(0, a);
			a = Math.min(255, a);
		}

		return new Gene(new Polygon(x, y, 3), new Color(r, g, b, a));
	}
}