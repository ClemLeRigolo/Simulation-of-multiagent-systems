import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import gui.GraphicalElement;


public class Fish implements GraphicalElement {

    private float maxforce;
    private float maxspeed;
    PVector location;
    PVector velocity;
    PVector acceleration;

    private int size;
    private double rotationAngle;
    private Color bodyColor;
    private Color finColor;

    public Fish(int x, int y, int size, double rotationAngle, Color bodyColor, Color finColor) {
        this.size = size;
        this.rotationAngle = rotationAngle;
        this.bodyColor = bodyColor;
        this.finColor = finColor;
        acceleration = new PVector(0, 0);
        velocity = new PVector(0, 0);
        location = new PVector(x, y);
        maxspeed = 4;
        maxforce = 0.1f;
    }

    void update() {
        velocity.add(acceleration);
        velocity.limit(maxspeed);
        location.add(velocity);
        acceleration.mult(0);
    }

    void applyForce(PVector force) {
        acceleration.add(force);
    }

    void seek(PVector target) {
        PVector desired = PVector.sub(target, location);
        desired.normalize();
        desired.mult(maxspeed);
        PVector steer = PVector.sub(desired, velocity);
        steer.limit(maxforce);
        applyForce(steer);
    }

    public void rotate(double angle) {
        rotationAngle += angle;
    }

    public void translate(int dx, int dy) {
        location.add(new PVector(dx, dy));
    }

    public void moveTo(int targetX, int targetY) {
        seek(new PVector(targetX, targetY));
    }

    public double getHeadingAngle() {
        return velocity.heading();
    }

    @Override
    public void paint(Graphics2D g2) {
        AffineTransform oldTransform = g2.getTransform();

        g2.translate(location.getX(), location.getY());
        g2.rotate(getHeadingAngle() + Math.PI);
        int[] xPoints = {0, size / 2, size / 2};
        int[] yPoints = {0, -size / 4, size / 4};

        g2.setColor(bodyColor);
        g2.fillOval(-size / 2, -size / 4, size, size / 2); // Corps
        g2.setColor(finColor);
        g2.fillPolygon(xPoints, yPoints, 3); // Nageoire caudale

        // Œil
        g2.setColor(Color.WHITE);
        g2.fillOval(- size / 5, -size / 16, size / 8, size / 8);

        g2.setTransform(oldTransform);
    }

    public String toString() {
        return bodyColor.toString() + " fish";
    }
}