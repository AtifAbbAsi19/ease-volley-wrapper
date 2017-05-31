package allaudin.github.io.ease;

import org.junit.Assert;
import org.junit.Test;

import allaudin.github.io.ease.mock.MockConfig;

/**
 * Test EaseUtils
 *
 * @author M.Allaudin
 */

public class EaseUtilsTest {

    @Test
    public void configIllegalTest() {
        Assert.assertTrue(EaseUtils.getConfig() instanceof EaseDefaultConfig);
    }

    @Test
    public void configInitTest() {
        EaseUtils.init(new MockConfig());
        EaseConfig config = EaseUtils.getConfig();
        Assert.assertNotNull(config);
    }
}
