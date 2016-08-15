package app.transform;

import org.junit.Assert;
import org.junit.Test;

public class TransformModuleFactoryTest {

    @Test
    public void testFactoryCreation() {
        Assert.assertTrue(TransformModuleFactory.createTransformer() instanceof DataTransformer);
    }
}
