public class Vector2 {
    private float x;
    private float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public static Vector2 random2D() {
        float x = (float) (Math.random() * 2 - 1);
        float y = (float) (Math.random() * 2 - 1);
        return new Vector2(x, y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2 other) {
        x += other.x;
        y += other.y;
    }

    public void sub(Vector2 other) {
        x -= other.x;
        y -= other.y;
    }

    public void mult(float scalar) {
        x *= scalar;
        y *= scalar;
    }

    public void div(float scalar) {
        if (scalar != 0) {
            x /= scalar;
            y /= scalar;
        }
    }

    public static double dist(Vector2 vector1, Vector2 vector2) {
        float dx = vector1.x - vector2.x;
        float dy = vector1.y - vector2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public float mag() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public void normalize() {
        float m = mag();
        if (m != 0) {
            x /= m;
            y /= m;
        }
    }

    public void limit(float max) {
        float magnitude = mag();
        if (magnitude > max) {
            normalize();
            mult(max);
        }
    }

    public float heading() {
        return (float) Math.atan2(y, x);
    }

    public static Vector2 add(Vector2 vector1, Vector2 vector2) {
        float x = vector1.x + vector2.x;
        float y = vector1.y + vector2.y;
        return new Vector2(x, y);
    }

    public static Vector2 sub(Vector2 vector1, Vector2 vector2) {
        float x = vector1.x - vector2.x;
        float y = vector1.y - vector2.y;
        return new Vector2(x, y);
    }

    public static Vector2 mult(Vector2 vector, float scalar) {
        float x = vector.x * scalar;
        float y = vector.y * scalar;
        return new Vector2(x, y);
    }

    public static Vector2 div(Vector2 vector, float scalar) {
        if (scalar != 0) {
            float x = vector.x / scalar;
            float y = vector.y / scalar;
            return new Vector2(x, y);
        }
        return vector;
    }
}