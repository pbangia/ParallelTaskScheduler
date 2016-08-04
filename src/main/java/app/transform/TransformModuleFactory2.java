package app.transform;

public class TransformModuleFactory2 {

    public static DataTransformer2 createTransformer() {
        StringToMatrixTransformation toMapTransformation = new StringToMatrixTransformation();
        return new DataTransformer2(toMapTransformation);
    }
}