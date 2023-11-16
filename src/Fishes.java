import java.awt.*;

public class Fishes extends BoidShoal{
    /*public Fishes(int nbSardines) {
        super(nbSardines);
        for (int i = 0; i < nbSardines; i++) {
            //random color
            Color color = new Color((int) (Math.random() * 0x1000000));
            //same color darker
            Color darkerColor = new Color(color.getRed() / 2, color.getGreen() / 2, color.getBlue() / 2);
            boids.add(new Sardine(100, 100, 30, Math.PI/4, color, darkerColor));
        }
    }*/

    public void addFishes(Boid[] boids) {
        for (Boid boid : boids) {
            this.boids.add(boid);
        }
    }
}
