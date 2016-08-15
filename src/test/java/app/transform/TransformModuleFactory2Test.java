package app.transform;

import org.junit.Assert;
import org.junit.Test;

public class TransformModuleFactory2Test {

    @Test
    public void testFactoryCreation() {
        Assert.assertTrue(TransformModuleFactory2.createTransformer() instanceof DataTransformer2);
    }
}