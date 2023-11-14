public class PVector {
    private float x;
    private float y;

    public PVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public PVector() {
        this.x = 0;
        this.y = 0;
    }

    public static PVector random2D() {
        float x = (float) (Math.random() * 2 - 1);
        float y = (float) (Math.random() * 2 - 1);
        return new PVector(x, y);
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

    public void add(PVector other) {
        x += other.x;
        y += other.y;
    }

    public void sub(PVector other) {
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

    public static PVector add(PVector vector1, PVector vector2) {
        float x = vector1.x + vector2.x;
        float y = vector1.y + vector2.y;
        return new PVector(x, y);
    }

    public static PVector sub(PVector vector1, PVector vector2) {
        float x = vector1.x - vector2.x;
        float y = vector1.y - vector2.y;
        return new PVector(x, y);
    }

    public static PVector mult(PVector vector, float scalar) {
        float x = vector.x * scalar;
        float y = vector.y * scalar;
        return new PVector(x, y);
    }

    public static PVector div(PVector vector, float scalar) {
        if (scalar != 0) {
            float x = vector.x / scalar;
            float y = vector.y / scalar;
            return new PVector(x, y);
        }
        return vector;
    }
}