import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * Une sardine !
 * Permet d'instancier un boid en forme de poisson et qui agit comme tel
 */
public class Sardine extends Boid {
    private Color bodyColor;
    private Color finColor;

    private BoidShoal predators;

    public Sardine(int x, int y, int size, double rotationAngle, Color bodyColor, Color finColor) {
        super();
        super.size = size;
        this.rotationAngle = rotationAngle;
        this.bodyColor = bodyColor;
        this.finColor = finColor;
        maxSpeed = 4f;
        maxForce = 0.1f;
    }

    public Sardine(int size) {
        super();
        super.size = size;
        maxSpeed = 4f;
        maxForce = 0.1f;
        randomColor();
    }

    @Override
    public void update() {
        //avoid the slayers if they are too close
        if (predators != null) {
            Boid predator = predators.closestBoidFrom(this);
            Vector2 target = predator.location;
            if (Vector2.dist(location, target) < size * 10) {
                Vector2 avoid = Vector2.sub(location, target);
                avoid.normalize();
                avoid.mult(maxSpeed);
                Vector2 steer = Vector2.sub(avoid, velocity);
                steer.limit(maxForce);
                applyForce(steer);
            }
        }
        super.update();
    }

    public void randomColor(){
            //random color
            Color color = new Color((int) (Math.random() * 0x1000000));
            //same color darker
            Color darkerColor = new Color(color.getRed() / 2, color.getGreen() / 2, color.getBlue() / 2);
            this.bodyColor = color;
            this.finColor = darkerColor;
    }

    public void setOpacity(float opacity) {
        bodyColor = new Color(bodyColor.getRed(), bodyColor.getGreen(), bodyColor.getBlue(), (int) (opacity * 255));
        finColor = new Color(finColor.getRed(), finColor.getGreen(), finColor.getBlue(), (int) (opacity * 255));
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

    public void setPredators(BoidShoal predators) {
        this.predators = predators;
    }
}