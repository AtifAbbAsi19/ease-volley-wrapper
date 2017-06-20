package allaudin.github.io.ease;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;

import allaudin.github.io.ease.mock.MockErrorListener;
import allaudin.github.io.ease.mock.MockResponseListener;
import allaudin.github.io.ease.model.UserTestModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Test typeRequest request.
 *
 * @author M.Allaudin
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class BaseRequestTest {

    private JsonObject jsonResponse, dataObject;
    private static final String dataKey = "data";
    private static final String descriptionKey = "result_description";

    private EaseBaseRequest<String> stringRequest;
    private EaseBaseRequest<UserTestModel> typeRequest;


    @Before
    public void prepareNetworkResponse() {

        jsonResponse = new JsonObject();
        dataObject = new JsonObject();
        dataObject.addProperty("name", "foo");
        dataObject.addProperty("id", 100);

        jsonResponse.addProperty(descriptionKey, "foo bar");
        jsonResponse.add(dataKey, dataObject);

        MockErrorListener mockErrorListener = new MockErrorListener();

        TypeToken<EaseResponse<UserTestModel>> userTypeToken = new TypeToken<EaseResponse<UserTestModel>>() {
        };

        typeRequest = getEaseRequest(userTypeToken, new MockResponseListener<UserTestModel>(), mockErrorListener);

        stringRequest = getEaseRequest(EaseStringType.getType(String.class), new MockResponseListener<String>(), mockErrorListener);
    }

    private <T> EaseBaseRequest<T> getEaseRequest(TypeToken<EaseResponse<T>> token, MockResponseListener<T> mockResponseListener, MockErrorListener mockErrorListener) {
        return new EaseBaseRequest<>(token, Request.Method.GET, "http://foo", null, null, mockResponseListener, mockErrorListener);
    }

    private NetworkResponse getNetworkResponse(String response) {
        return new NetworkResponse(200, response.getBytes(), new HashMap<String, String>(), true);
    }

    @Test
    public void responseParsingTest() {
        EaseResponse<UserTestModel> response = typeRequest.parseNetworkResponse(getNetworkResponse(jsonResponse.toString())).result;
        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testStringResponse() {
        EaseResponse<String> response = stringRequest.parseNetworkResponse(getNetworkResponse(jsonResponse.toString())).result;
        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        assertEquals(dataObject.toString(), response.getData());
    }

    @Test
    public void testNonNullDescription() {
        jsonResponse.add(descriptionKey, JsonNull.INSTANCE);
        EaseResponse<String> response = stringRequest.parseNetworkResponse(getNetworkResponse(jsonResponse.toString())).result;
        assertNotNull(response.getDescription());
        assertEquals(dataObject.toString(), response.getData());
    }


    @Test
    public void testNullData() {
        jsonResponse.add(dataKey, JsonNull.INSTANCE);
        EaseResponse<String> response = stringRequest.parseNetworkResponse(getNetworkResponse(jsonResponse.toString())).result;
        assertNotNull(response.getDescription());
        assertNull(response.getData());
    }
}
