import java.awt.*;
import java.awt.geom.AffineTransform;

public class Shark extends Boid {
    private Color bodyColor;
    private Color finColor;

    private BoidShoal preys;

    public Shark(int x, int y, int size, double rotationAngle, Color bodyColor, Color finColor) {
        super();
        super.size = size;
        this.rotationAngle = rotationAngle;
        this.bodyColor = bodyColor;
        this.finColor = finColor;
        maxSpeed = 2f;
        maxForce = 0.05f;
    }

    public Shark(int size) {
        super();
        super.size = size;
        maxSpeed = 2f;
        maxForce = 0.05f;
        randomColor();
    }

    @Override
    public void update() {
        if (preys != null) {
            Vector2 target = preys.closestBoidLocation(this);
            seek(target);
        }
        super.update();
    }

    public void randomColor() {
        // Random color
        Color color = new Color((int) (Math.random() * 0x1000000));
        // Same color darker
        Color darkerColor = new Color(color.getRed() / 2, color.getGreen() / 2, color.getBlue() / 2);
        this.bodyColor = color;
        this.finColor = darkerColor;
    }

    @Override
    public void paint(Graphics2D g2) {
        AffineTransform oldTransform = g2.getTransform();

        g2.translate(location.getX(), location.getY());
        g2.rotate(getHeadingAngle() + Math.PI);
        int[] xPoints = {size/5, (int) (size / 1.5), (int) (size / 1.5)};
        int[] yPoints = {0, -size / 4, size / 4};

        g2.setColor(bodyColor);
        g2.fillOval(-size / 2, -size / 4, size, size / 2); // Corps
        g2.setColor(finColor);
        g2.fillPolygon(xPoints, yPoints, 3); // Nageoire caudale

        xPoints = new int[]{-size / 5, 0, size / 5};
        yPoints = new int[]{size /5, size / 2, size / 5};
        g2.fillPolygon(xPoints, yPoints, 3); // Nageoire dorsale

// Dents
        g2.setColor(Color.WHITE);
        drawTeeth(g2, size);

// Œil
        g2.setColor(finColor);
        g2.fillOval(-size / 3, size/8, size / 6, size / 6);


        g2.setColor(Color.RED);
        g2.fillOval(-size / 3, size/8, size / 8, size / 8);

        g2.setTransform(oldTransform);
    }

    private void drawFin(Graphics2D g2, int x, int y, int size) {
        int[] xPoints = {x, x + size, x + size};
        int[] yPoints = {y, y - size / 4, y + size / 4};
        Polygon fin = new Polygon(xPoints, yPoints, 3);
        g2.fill(fin);
    }

    private void drawTeeth(Graphics2D g2, int size) {
        int toothWidth = size / 12;
        int toothHeight = size / 6;
        int toothGap = size / 24;
        int numTeeth = 3;

        for (int i = 0; i < numTeeth; i++) {
            int toothX = i * (toothWidth + toothGap) - size / 2;
            int toothY = -size / 8;

            g2.fillRect(toothX, toothY, toothWidth, toothHeight);
        }
    }

    public String toString() {
        return bodyColor.toString() + " shark";
    }

    public void setPreys(BoidShoal preys) {
        this.preys = preys;
    }
}