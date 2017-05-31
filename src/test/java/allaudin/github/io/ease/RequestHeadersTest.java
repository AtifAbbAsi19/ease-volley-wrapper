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

public class RequestHeadersTest {

    private RequestHeaders requestHeader;

    @Before
    public void prepareRequestBody() {
        requestHeader = RequestHeaders.newInstance();
    }

    @Test
    public void testValuePairs() {
        requestHeader.put("foo", "bar");
        Map<String, String> map = requestHeader.get();
        assertTrue(map.containsKey("foo"));
        assertEquals("bar", map.get("foo"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testNonModifiableMap() {
        requestHeader.get().put("foo", "bar");
    }

    @Test
    public void verifyNewMapInstanceForGet() {
        assertNotSame(requestHeader.get(), requestHeader.get());
    }

    // TODO: 5/31/2017 add json header test 
}
