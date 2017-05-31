package allaudin.github.io.ease;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author M.Allaudin
 */

public class EaseExceptionTest {

    @Test public void testNonNullMsg() {
        try {
            throw new EaseException();
        } catch (EaseException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }
}
