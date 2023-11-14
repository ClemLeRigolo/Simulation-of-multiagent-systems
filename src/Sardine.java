import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import gui.GraphicalElement;


public class Fish extends Boid {
    private Color bodyColor;
    private Color finColor;

    public Fish(int x, int y, int size, double rotationAngle, Color bodyColor, Color finColor) {
        super();
        super.size = size;
        this.rotationAngle = rotationAngle;
        this.bodyColor = bodyColor;
        this.finColor = finColor;
        maxspeed = 4;
        maxforce = 0.1f;
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