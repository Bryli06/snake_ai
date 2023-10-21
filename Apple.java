import java.awt.Color;
import java.awt.Graphics2D;

public class Apple {
	public static int x,y;
	public Apple() {
		x=(int) (Math.random()*15);
		y=(int) (Math.random()*15);
	}
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect(100+x*22,100+y*22,20,20);
	}
}
