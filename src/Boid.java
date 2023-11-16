import java.awt.Graphics2D;

import gui.GraphicalElement;

/**
 * Implement le  principe générale d'un boid et  comment il doit bouger (classe parente).
 * Un boid bouge en fonction de sa vitesse actuelle et de ses différents voisins dans son champ de vision.
 */
public abstract class Boid implements GraphicalElement {

    protected float maxForce;
    protected float maxSpeed;
    protected Vector2 location;
    protected Vector2 velocity;
    protected Vector2 acceleration;

    protected Vector2 target;

    protected int size;
    protected double rotationAngle;

    public Boid() {
        acceleration = new Vector2(0, 0);
        velocity = new Vector2(0, 0);
        location = new Vector2(0, 0);
        randomPos();
        //target = new Vector2((float) (Math.random() * 800), (float) (Math.random() * 600));
    }

    public void randomPos(){
        location = new Vector2((float) (Math.random() * 800), (float) (Math.random() * 600));
    }

    void update() {
        velocity.add(acceleration);
        velocity.limit(maxSpeed);
        location.add(velocity);
        acceleration.mult(0);
        if(location.getX() > 800F){
            location.setX(0F);
        }
        if(location.getX() < 0F){
            location.setX(800F);
        }
        if(location.getY() > 600F){
            location.setY(0F);
        }
        if(location.getY() < 0F){
            location.setY(600F);
        }
    }

    void applyForce(Vector2 force) {
        acceleration.add(force);
    }

    void seek(Vector2 target) {
        Vector2 desired = Vector2.sub(target, location);
        desired.normalize();
        desired.mult(maxSpeed);
        Vector2 steer = Vector2.sub(desired, velocity);
        steer.limit(maxForce);
        applyForce(steer);
    }

    void arrive(Vector2 target) {
        Vector2 desired = Vector2.sub(target, location);
        double d = desired.mag();
        desired.normalize();
        if (d < 100) {
            float m = (float) (d / 100 * maxSpeed);
            desired.mult((float) m);
        } else {
            desired.mult(maxSpeed);
        }
        Vector2 steer = Vector2.sub(desired, velocity);
        steer.limit(maxForce);
        applyForce(steer);
    }

    void follow(FlowField flow) {
        Vector2 desired = flow.lookup(location);
        //desired.mult(maxSpeed);
        Vector2 steer = Vector2.sub(desired, velocity);
        steer.limit(maxForce);
        applyForce(steer);
    }

    public double getHeadingAngle() {
        return velocity.heading();
    }

    @Override
    public abstract void paint(Graphics2D g2);

    public String toString() {
        return "Boid";
    }
}