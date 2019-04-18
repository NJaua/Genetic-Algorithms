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
}