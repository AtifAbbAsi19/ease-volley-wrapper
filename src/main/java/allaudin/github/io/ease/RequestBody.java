package allaudin.github.io.ease;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Request body backed by {@link Map}
 *
 * @author M. Allaudin
 * @since 1.0
 */

public final class RequestBody {

    /**
     * Map for storing request body in key-value pairs.
     */
    private Map<String, String> mMap;

    private RequestBody() {
        mMap = new HashMap<>();
    }

    public static RequestBody newInstance() {
        return new RequestBody();
    }

    /**
     * Put a value in request body
     *
     * @param key   key
     * @param value value
     * @return this object
     */
    public RequestBody put(String key, String value) {
        mMap.put(key.trim(), value);
        return this;
    }

    /**
     * Remove entry from request body
     *
     * @param key remove key
     * @return this object
     */
    public RequestBody remove(String key) {
        mMap.remove(key);
        return this;
    }

    /**
     * Returns readonly view of request headers.
     *
     * @return requestHeaders - map of request headers
     */

    public Map<String, String> get() {
        return new RequestBodyMap<>(mMap);
    }

    /**
     * Readonly view of request body.
     *
     * @param <K> type of key
     * @param <V> type of value
     */
    private class RequestBodyMap<K, V> extends HashMap<K, V> {

        RequestBodyMap(Map<? extends K, ? extends V> m) {
            super(m);
        }

        @Override
        public V put(K key, V value) {
            throw new UnsupportedOperationException(String.format(Locale.US, "Can't modify body. Use %s class for body manipulation.", RequestBody.class.getName()));
        } // put

    } // HeaderMap

    @Override
    public String toString() {
        return mMap.toString();
    }
} // RequestBody
