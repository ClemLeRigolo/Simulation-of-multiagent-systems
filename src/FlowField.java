import java.util.Random;

public class FlowField {
    protected Grid<Vector2> field;

    //I want an enum of strings

    public FlowField(int resolution, int width, int height) {
        field = new Grid<>(resolution, width, height);
    }

    void initField(FlowEnum flowEnum) {
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




    Vector2 lookup(Vector2 lookup) {
        int column = (int) (lookup.getX() / field.getResolution());
        int row = (int) (lookup.getY() / field.getResolution());
        if(column < 0 || column >= field.getCols() || row < 0 || row >= field.getRows()){
            return new Vector2(0, 0);
        }

        return field.getCase(column, row);
    }

    public static float map(float value, float start1, float stop1, float start2, float stop2) {
        return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
    }

}



enum FlowEnum {
    RANDOM,
    PERLIN,
    CENTER;
}
