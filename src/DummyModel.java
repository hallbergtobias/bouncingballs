import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;

public class DummyModel implements IBouncingBallsModel {

	private final double areaWidth;
	private final double areaHeight;

	private double x, y, vx, vy, r;
    private double x2, y2, vx2, vy2, r2;

    private double GRAVITY = 0.5;

	public DummyModel(double width, double height) {
		this.areaWidth = width;
		this.areaHeight = height;
		x = 4;
		y = 4;
		vx = 5.3;
		vy = 8;
		r = 1;
        x2 = 2;
        y2 = 2;
        vx2 = 4.3;
        vy2 = 8;
        r2 = 2;
	}

	@Override
	public void tick(double deltaT) {

        //if kolliderar
        //fiXA det
        //annars -g

        double deltaX = Math.abs(x-x2);
        double deltaY = Math.abs(y-y2);

        double distance = Math.sqrt(Math.pow(deltaX,2)+Math.pow(deltaY,2));

        if (distance<=r+r2){
            System.out.println("KOLLISION");
        }

		if (x < r || x > areaWidth - r) {
			vx *= -1;
		}
		if (y < r || y > areaHeight - r) {
			vy *= -1;
		} else { vy-=GRAVITY;}
		x += vx * deltaT;
		y += vy * deltaT;


        if (x2 < r2 || x2 > areaWidth - r2) {
            vx2 *= -1;
        }
        if (y2 < r2 || y2 > areaHeight - r2) {
            vy2 *= -1;
        } else { vy2-=GRAVITY;}
        x2 += vx2 * deltaT;
        y2 += vy2 * deltaT;


	}



	@Override
	public List<Ellipse2D> getBalls() {
		List<Ellipse2D> myBalls = new LinkedList<Ellipse2D>();
		myBalls.add(new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r));
        myBalls.add(new Ellipse2D.Double(x2 - r2, y2 - r2, 2 * r2, 2 * r2));

        return myBalls;
	}
}
