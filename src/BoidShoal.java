import java.util.ArrayList;
import java.util.function.Consumer;

public abstract class BoidShoal {

    protected ArrayList<Boid> boids;

    public BoidShoal(int nbBoids) {
        boids = new ArrayList<Boid>();
    }

    public void applyToAllBoids(Consumer<Boid> function) {
        for (Boid boid : boids) {
            // Appel de la fonction sur chaque boid
            function.accept(boid);
        }
    }

    public void separate(Boid boid, double separation) {
        Vector2 sum = new Vector2();
        int count = 0;
        for (Boid other : boids) {
            double d = Vector2.dist(boid.location, other.location);
            if ((d > 0) && (d < separation)) {
                Vector2 diff = Vector2.sub(boid.location, other.location);
                diff.normalize();
                diff.div((float) d);
                sum.add(diff);
                count++;
            }
        }
        if (count > 0) {
            sum.div(count);
            sum.normalize();
            sum.mult(boid.maxSpeed);
            Vector2 steer = Vector2.sub(sum, boid.velocity);
            steer.limit(boid.maxForce);
            boid.applyForce(steer);
        }
    }

    public void align(Boid boid, double neighborDist) {
        Vector2 sum = new Vector2();
        int count = 0;
        for (Boid other : boids) {
            double d = Vector2.dist(boid.location, other.location);
            if ((d > 0) && (d < neighborDist)) {
                sum.add(other.velocity);
                count++;
            }
        }
        if (count > 0) {
            sum.div(count);
            sum.normalize();
            sum.mult(boid.maxSpeed);
            Vector2 steer = Vector2.sub(sum, boid.velocity);
            steer.limit(boid.maxForce);
            boid.applyForce(steer);
        }
    }

}
