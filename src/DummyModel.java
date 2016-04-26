import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;

public class DummyModel implements IBouncingBallsModel {

	private final double areaWidth;
	private final double areaHeight;

	private double x, y, vx, vy, r, m;
    private double x2, y2, vx2, vy2, r2, m2;

    private double GRAVITY = 0.0;

	public DummyModel(double width, double height) {
		this.areaWidth = width;
		this.areaHeight = height;
		x = 8;
		y = 8;
		vx = 5.3;
		vy = 2;
		r = 1;
        m = 1;
        x2 = 2;
        y2 = 2;
        vx2 = 2.3;
        vy2 = 2;
        r2 = 2;
        m2 = 2;

	}

	@Override
	public void tick(double deltaT) {

        //if kolliderar
        //fiXA det
        //annars -g

        double deltaX = x-x2;
        double deltaY = y-y2;

        double distance = Math.sqrt(Math.pow(deltaX,2)+Math.pow(deltaY,2));

        if (distance<=r+r2){
            System.out.println("---------------- KOLLISION ----------------");

            /*

            double newVy = calculateVelocity(vy,vy2,m,m2);
            double newVy2 = calculateVelocity(vy2,vy,m2,m);

            double newVx = calculateVelocity(vx,vx2,m,m2);
            double newVx2 = calculateVelocity(vx2,vx,m2,m);

            //System.out.println("Y-led");
           // System.out.println("u1: " + vy + " u2: " + vy2 + " v1: " + newVy + " v2: " + newVy2);
            //System.out.println("m1v1+m2v2=" + (m*newVy+m2*newVy2) + ", m1u1+m2u2=" + (m*vy+m2*vy2));

            System.out.println("X-led");
            System.out.println("u1: " + vx + " u2: " + vx2 + " v1: " + newVx + " v2: " + newVx2);
            System.out.println("m1v1+m2v2=" + (m*newVx+m2*newVx2) + ", m1u1+m2u2=" + (m*vx+m2*vx2));

            vy = newVy;
            vx = newVx;
            vy2 = newVy2;
            vx2 = newVx2;
            */








            double beta = Math.tan(deltaY/deltaX);
            double alpha = (Math.PI/2 - beta)%(Math.PI*2);
            //double alpha = Math.PI/2 - beta;

            double uBall1 = Math.sqrt(Math.pow(vx,2)+Math.pow(vy,2));
            double myBall1 = alpha - Math.acos(vy/uBall1);
            double AC = Math.cos(myBall1)*uBall1;
            double BD = Math.sin(myBall1)*uBall1;

            double uBall2 = Math.sqrt(Math.pow(vx2,2)+Math.pow(vy2,2));
            double myBall2 = alpha - Math.acos(vy2/uBall2);
            double AC2 = Math.cos(myBall2)*uBall2;
            double BD2 = Math.sin(myBall2)*uBall2;


           /* double AC = Math.cos(alpha)*vy+Math.cos(beta)*vx;
            double BD = Math.sin(alpha)*vy-Math.sin(beta)*vx;

            double AC2 = Math.cos(beta)*vy2+Math.cos(alpha)*vx2;
            double BD2 = Math.sin(beta)*vy2-Math.sin(alpha)*vx2;*/

            //System.out.println("AC: " + AC + ", AC2: " + AC2);

            double newV1 = calculateVelocity(AC,AC2,m,m2);
            double newV2 = -(AC2-AC)+newV1;
            //double newV2 = calculateVelocity(AC2,AC,m2,m);

            System.out.println("Alpha: " + Math.toDegrees(alpha) + ", Beta: " + Math.toDegrees(beta));

            /*System.out.println("-- BALL 1 --");
            System.out.println("uBall1: " + uBall1);
            System.out.println("myBall1: " + myBall1);
            System.out.println("AC: " + AC + ", BD: " + BD);
            System.out.println("newV1: " + newV1);

            System.out.println("-- BALL 2 --");
            System.out.println("uBall2: " + uBall2);
            System.out.println("myBall2: " + myBall2);
            System.out.println("AC2: " + AC2 + ", BD2: " + BD2);
            System.out.println("newV2: " + newV2);*/



            double newX1Ball1 = newV1*Math.sin(alpha);
            double newY1Ball1 = newV1*Math.cos(alpha);

            double newX2Ball1 = BD*Math.cos(alpha);
            double newY2Ball1 = BD*Math.sin(alpha);

            double newVy = newY1Ball1 + newY2Ball1;
            double newVx = newX1Ball1 + newX2Ball1;


            double newX1Ball2 = newV2*Math.sin(alpha);
            double newY1Ball2 = newV2*Math.cos(alpha);

            double newX2Ball2 = BD2*Math.cos(alpha);
            double newY2Ball2 = BD2*Math.sin(alpha);

            double newVy2 = newY1Ball2 + newY2Ball2;
            double newVx2 = newX1Ball2 + newX2Ball2;

            System.out.println("vx: " + vx + ", vy: " + vy);
            System.out.println("vx2: " + vx2 + ", vy2: " + vy2);

            vy = newVy;
            vx = newVx;
            vy2 = newVy2;
            vx2 = newVx2;
            System.out.println("Nya hastigheter");
            System.out.println("vx: " + vx + ", vy: " + vy);
            System.out.println("vx2: " + vx2 + ", vy2: " + vy2);


        } else {


            if (x < r || x > areaWidth - r) {
                vx *= -1;
            }
            if (y < r || y > areaHeight - r) {
                vy *= -1;
            } else { vy-=GRAVITY;}



            if (x2 < r2 || x2 > areaWidth - r2) {
                vx2 *= -1;
            }
            if (y2 < r2 || y2 > areaHeight - r2) {
                vy2 *= -1;
            } else { vy2-=GRAVITY;}





        }
        x += vx * deltaT;
        y += vy * deltaT;
        x2 += vx2 * deltaT;
        y2 += vy2 * deltaT;



	}





    private double calculateVelocity(double u1, double u2, double m1, double m2){

        double I = m1*u1+m2*u2;

        return (I+m2*u2-m2*u1)/(m1+m2);
    }



	@Override
	public List<Ellipse2D> getBalls() {
		List<Ellipse2D> myBalls = new LinkedList<Ellipse2D>();
		myBalls.add(new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r));
        myBalls.add(new Ellipse2D.Double(x2 - r2, y2 - r2, 2 * r2, 2 * r2));

        return myBalls;
	}
}
