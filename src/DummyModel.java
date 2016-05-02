import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;

public class DummyModel implements IBouncingBallsModel {

    private final double areaWidth;
    private final double areaHeight;

    private double x, y, vx, vy, r, m;
    private double x2, y2, vx2, vy2, r2, m2;

    private double GRAVITY = .2;


    public DummyModel(double width, double height) {
        this.areaWidth = width;
        this.areaHeight = height;

        //Ball 1
        x = 2;
        y = 3;
        vx = -5;
        vy = 5;
        r = 2;
        m = 5;

        //Ball 2
        x2 = 2;
        y2 = 6;
        vx2 = -1;
        vy2 = -5;
        r2 = 1;
        m2 = 1;
    }

    @Override
    public void tick(double deltaT) {

        double deltaX = x - x2;
        double deltaY = y - y2;

        double distance = distance(deltaX, deltaY);

        if (distance <= r + r2) { //if colliding
            double alpha = Math.atan(deltaY / deltaX);

            //Projecting to another plane
            double newVx = project(vx,vy,alpha, Math.PI/2-alpha);
            double newVy = project(vx,vy,Math.PI/2+alpha,alpha);

            double newVx2 = project(vx2,vy2,alpha, Math.PI/2-alpha);
            double newVy2 = project(vx2,vy2,Math.PI/2+alpha,alpha);

            //Calculates velocities in X after collision
            double collisionVx = calculateVelocity(newVx, newVx2, m, m2);
            double collisionVx2 = calculateVelocity(newVx2, newVx, m2, m);

            //Projecting back
            vy = project(collisionVx,newVy,Math.PI/2-alpha,alpha);
            vx = project(collisionVx,newVy,alpha,alpha+Math.PI/2);

            vy2 = project(collisionVx2,newVy2,Math.PI/2-alpha,alpha);
            vx2 = project(collisionVx2,newVy2,alpha,alpha+Math.PI/2);


        } else { //if not colliding, means of gravity
            if (x < r || x > areaWidth - r) {
                vx *= -1;
            }
            if (y < r || y > areaHeight - r) {
                vy *= -1;
            } else {
                vy -= GRAVITY;
            }

            if (x2 < r2 || x2 > areaWidth - r2) {
                vx2 *= -1;
            }
            if (y2 < r2 || y2 > areaHeight - r2) {
                vy2 *= -1;
            } else {
                vy2 -= GRAVITY;
            }
        }

        x += vx * deltaT;
        y += vy * deltaT;
        x2 += vx2 * deltaT;
        y2 += vy2 * deltaT;
    }

    /**
     * Calculates velocity after collision between two bodies using conservation of momentum and kinetic energy
     * @param u1 Velocity body 1
     * @param u2 Velocity body 2
     * @param m1 Mass body 1
     * @param m2 Mass body 2
     * @return New velocity
     */
    private double calculateVelocity(double u1, double u2, double m1, double m2) {
        double I = m1 * u1 + m2 * u2;
        return (I + m2 * u2 - m2 * u1) / (m1 + m2);
    }

    /**
     * Projects two vectors vx and vy to a new vector
     * @param vx Vector vx
     * @param vy Vector vy
     * @param a Angle a
     * @param b Angle b
     * @return New vector
     */
    private double project(double vx, double vy, double a, double b){
        return vx * Math.cos(a) + vy * Math.cos(b);
    }

    /**
     * Calculates distance between two bodies using pythagoras
     * @param deltaX distance X
     * @param deltaY distance Y
     * @return distance
     */
    private double distance(double deltaX, double deltaY){
        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }

    @Override
    public List<Ellipse2D> getBalls() {
        List<Ellipse2D> myBalls = new LinkedList<Ellipse2D>();
        myBalls.add(new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r));
        myBalls.add(new Ellipse2D.Double(x2 - r2, y2 - r2, 2 * r2, 2 * r2));

        return myBalls;
    }
}
