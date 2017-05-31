package allaudin.github.io.ease;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Base request for lifting heavy work for parsing response
 * into give asString.
 *
 * @author M.Allaudin
 * @see EaseRequest
 * @since 1.0
 */

class EaseBaseRequest<T> extends Request<EaseResponse<T>> {

    /**
     * Response listener for propagating parsed response
     */
    private Response.Listener<EaseResponse<T>> mResponseListener;

    /**
     * Type token for converting response to specific asString
     */
    private TypeToken<EaseResponse<T>> mTypeToken;

    /**
     * Request headers
     */
    private RequestHeaders mHeaders;

    /**
     * Request body parameters
     */
    private RequestBody mBodyParams;

    public EaseBaseRequest(TypeToken<EaseResponse<T>> token, int method, String url, RequestHeaders headers, RequestBody bodyParams, Response.Listener<EaseResponse<T>> responseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mResponseListener = responseListener;
        mHeaders = headers;
        mTypeToken = token;
        mBodyParams = bodyParams;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mBodyParams == null ? super.getParams() : mBodyParams.get();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders == null ? super.getHeaders() : mHeaders.get();
    }


    static <T> EaseBaseRequest<T> getInstance(TypeToken<EaseResponse<T>> token, int method, String url, RequestHeaders headers, RequestBody bodyParams, Response.Listener<EaseResponse<T>> responseListener, Response.ErrorListener errorListener) {
        return new EaseBaseRequest<>(token, method, url, headers, bodyParams, responseListener, errorListener);
    }

    @Override
    protected Response<EaseResponse<T>> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            EaseResponse<T> allayResponse;

            if (mTypeToken instanceof EaseType && ((EaseType) mTypeToken).getActualType().equals(String.class)) {
                allayResponse = new EaseResponse<>();
                allayResponse.setStatusCode(response.statusCode);
                JsonObject object = new JsonParser().parse(json).getAsJsonObject();
                allayResponse.setDescription(parseJsonKey(object, "result_description", ""));
                // Safe cast to type <T> as both T and data are of type String.
                //noinspection unchecked
                allayResponse.setData((T) parseJsonKey(object, "data", null));
            } else {
                allayResponse = new Gson().fromJson(json, mTypeToken.getType());
                allayResponse.setStatusCode(response.statusCode);

            }
            return Response.success(allayResponse, HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    /**
     * Return a String representation of key.
     *
     * @param json       json from which key is extracted.
     * @param key        key for json field
     * @param defaultVal default value to be return when json key has null value or key is not found.
     * @return String representation of data against key or empty if key is not present or value is {@code null}
     */
    private String parseJsonKey(JsonObject json, String key, String defaultVal) {
        boolean hasValue = json.has(key) && !json.get(key).isJsonNull();
        return hasValue ? json.get(key).toString() : defaultVal;
    }


    @Override
    protected void deliverResponse(EaseResponse<T> response) {
        mResponseListener.onResponse(response);
    }

} // EaseBaseRequest
