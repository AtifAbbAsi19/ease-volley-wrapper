package allaudin.github.io.ease;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

/**
 * @author M.Allaudin
 */

public class RequestBodyTest {

    private RequestBody requestBody;

    @Before
    public void prepareRequestBody() {
        requestBody = RequestBody.newInstance();
    }

    @Test
    public void testValuePairs() {
        requestBody.put("foo", "bar");
        Map<String, String> map = requestBody.get();
        assertTrue(map.containsKey("foo"));
        assertEquals("bar", map.get("foo"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testNonModifiableMap() {
        requestBody.get().put("foo", "bar");
    }

    @Test
    public void verifyNewMapInstanceForGet() {
        assertNotSame(requestBody.get(), requestBody.get());
    }
}
