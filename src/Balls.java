import java.awt.geom.Point2D;
public class Balls {
    private Point2D[] balls = new Point2D[3];

    public Balls() {
        for (int i = 0; i < balls.length; i++) {
            balls[i] = new Point2D.Double(Math.random() * 100, Math.random() * 100);
        }
    }

    public int size(){
        return balls.length;
    }
    public Point2D[] getBalls() {
        return balls;
    }

    public Point2D getBall(int i) {
        return balls[i];
    }

    void translate(int dx, int dy){
        for (Point2D ball : balls) {
            ball.setLocation(ball.getX() + dx, ball.getY() + dy);
        }
    }

    void reInit(){
        for (int i = 0; i < balls.length; i++) {
            balls[i] = new Point2D.Double(Math.random() * 100, Math.random() * 100);
        }
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for (Point2D ball : balls) {
            s.append(ball.toString()).append("\n");
        }
        return s.toString();
    }

}
