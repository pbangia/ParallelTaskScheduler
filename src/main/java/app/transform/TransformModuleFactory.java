package app.transform;

public class TransformModuleFactory {

    public static DataTransformer createTransformer() {
        StringToMapTransformation toMapTransformation = new StringToMapTransformation();
        return new DataTransformer(toMapTransformation);
    }
}
