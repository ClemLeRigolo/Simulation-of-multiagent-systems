import java.util.Random;

/**
 * Générateur des bruits pour le champ vectoriel (en prototype)
 */
public class PerlinNoiseGenerator {
    private int[] permutations;

    public PerlinNoiseGenerator() {
        permutations = new int[256];
        Random random = new Random();

        // Remplir le tableau des permutations avec des valeurs aléatoires
        for (int i = 0; i < 256; i++) {
            permutations[i] = i;
        }

        // Mélanger les valeurs du tableau des permutations
        for (int i = 0; i < 256; i++) {
            int j = random.nextInt(256);
            swap(permutations, i, j);
        }
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private float fade(float t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }

    private float grad(int hash, float x, float y) {
        int h = hash & 15;
        float u = h < 8 ? x : y;
        float v = h < 4 ? y : (h == 12 || h == 14) ? x : 0;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    public float noise(float x, float y) {
        int xi = (int) Math.floor(x) & 255;
        int yi = (int) Math.floor(y) & 255;
        float xf = x - (int) Math.floor(x);
        float yf = y - (int) Math.floor(y);

        float u = fade(xf);
        float v = fade(yf);

        int aa = permutations[permutations[xi] + yi];
        int ab = permutations[permutations[xi] + yi + 1];
        int ba = permutations[permutations[xi + 1] + yi];
        int bb = permutations[permutations[xi + 1] + yi + 1];

        float x1 = lerp(grad(aa, xf, yf), grad(ba, xf - 1, yf), u);
        float x2 = lerp(grad(ab, xf, yf - 1), grad(bb, xf - 1, yf - 1), u);

        return (lerp(x1, x2, v) + 1) / 2;  // Normalisation entre 0 et 1
    }
}