import java.util.Random;

/**
 * Un champ Vectoriel.
 *
 * Génére les champs vectoriels pour les déplacements des Boids sur la fenêtre graphique.
 * Possède trois valeur possible:
 *  -Center qui attire les boids vers le milieu de l'écran
 *  -Perlin qui génére un champ avec du bruit
 *  -Random qui génère un champ aléatoire
 */
public class FlowField {
    protected Grid<Vector2> field;

    FlowEnum flowEnum;
    //I want an enum of strings

    public FlowField(int resolution, int width, int height) {
        field = new Grid<>(resolution, width, height);
    }

    void initField(FlowEnum flowEnum) {
        this.flowEnum = flowEnum;
        if(flowEnum == FlowEnum.RANDOM){
            for(int i = 0; i < field.getCols(); i++){
                for(int j = 0; j < field.getRows(); j++){
                    field.setCase(i, j, Vector2.random2D());
                    field.getCase(i, j).normalize();
                    field.getCase(i, j).mult(10);
                }
            }
        } else if (flowEnum == FlowEnum.PERLIN) {
            //Marche pas
            PerlinNoiseGenerator perlinNoiseGenerator = new PerlinNoiseGenerator();
            float xoff = 0;
            Random random = new Random();
            for (int i = 0; i < field.getCols(); i++) {
                float yoff = 0;
                for (int j = 0; j < field.getRows(); j++) {
                    float noiseValue = random.nextFloat();
                    float theta = map(perlinNoiseGenerator.noise(xoff, yoff), 0, 1, 0, (float) Math.PI * 2);
                    field.setCase(i, j, new Vector2((float) Math.cos(theta), (float) Math.sin(theta)));
                    yoff += 0.1F;
                }

                xoff += 0.1F;
            }
        } else if (flowEnum == FlowEnum.CENTER) {
            float centerX = field.getCols() / 2;
            float centerY = field.getRows() / 2;

            for (int i = 0; i < field.getCols(); i++) {
                for (int j = 0; j < field.getRows(); j++) {
                    Vector2 v = new Vector2(centerX - i, centerY - j);
                    v.normalize();
                    v.mult(10);
                    field.setCase(i, j, v);
                }
            }
        }

    }


    double constrain(double value, double min, double max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }

    Vector2 lookup(Vector2 lookup) {
        int column = (int) constrain(lookup.getX() / field.getResolution(), 0, field.getCols() - 1);
        int row = (int) constrain(lookup.getY() / field.getResolution(), 0, field.getRows() - 1);

        return field.getCase(column, row);
    }

    public static float map(float value, float start1, float stop1, float start2, float stop2) {
        return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
    }

    public FlowEnum getFlowEnum() {
        return flowEnum;
    }
}



enum FlowEnum {
    RANDOM,
    PERLIN,
    CENTER;
}
