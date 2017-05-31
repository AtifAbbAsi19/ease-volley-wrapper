package allaudin.github.io.ease;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Request headers backed by {@link Map}
 *
 * @author M.Allaudin
 * @since 1.0
 */

public final class RequestHeaders {


    /**
     * Map for storing request headers in
     * key-value pairs.
     */
    private Map<String, String> mMap;

    private RequestHeaders() {
        mMap = new HashMap<>();
    }

    private RequestHeaders(Map<String, String> map) {
        mMap = new HashMap<>(map);
    }

    public static RequestHeaders newInstance() {
        return new RequestHeaders();
    }

    public static RequestHeaders newInstance(RequestHeaders headers) {
        return new RequestHeaders(headers.get());
    }

    public RequestHeaders put(String key, String value) {
        mMap.put(key.trim(), value);
        return this;
    }

    public JsonHeader asJson(String key) {
        return new JsonHeader() {
            private JSONObject json = new JSONObject();

            public JsonHeader addJsonField(@NonNull String name, Object value) {
                try {
                    json.put(name, value);
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Don't use @Nullable here. Let it
                    // fail if something goes wrong while
                    // adding json key.
                    return null;
                }
                return this;
            }

            @Override
            public RequestHeaders endJson() {
                return RequestHeaders.this;
            }
        };
    }

    public RequestHeaders remove(String key) {
        mMap.remove(key);
        return this;
    }


    public interface JsonHeader {
        JsonHeader addJsonField(@NonNull String name, Object value);

        RequestHeaders endJson();
    }


    /**
     * Returns readonly view of request headers.
     *
     * @return requestHeaders - map of request headers
     */
    public Map<String, String> get() {
        return new HeaderMap<>(mMap);
    }

    /**
     * Readonly view of request headers.
     *
     * @param <K> type of key
     * @param <V> type of value
     */
    private class HeaderMap<K, V> extends HashMap<K, V> {

        HeaderMap(Map<? extends K, ? extends V> m) {
            super(m);
        }

        @Override
        public V put(K key, V value) {
            throw new UnsupportedOperationException(String.format(Locale.US, "Can't modify headers. Use %s class for header manipulation.", RequestHeaders.class.getName()));
        } // put

    } // HeaderMap

    @Override
    public String toString() {
        return mMap.toString();
    }
}
