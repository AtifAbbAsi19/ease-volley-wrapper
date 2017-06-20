package allaudin.github.io.ease;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.util.Locale;

/**
 * A request which converts {@link Response.Listener} to {@link EaseCallbacks} and
 * does heavy lifting of initializing request, setting proper types, request body
 * and headers etc.
 *
 * @param <T> type of request
 * @author M.Allaudin
 * @see RequestHeaders
 * @see RequestBody
 * @see EaseCallbacks
 * @since 1.0
 */

// TODO: 5/31/2017 Create unit test
public class EaseRequest<T> implements Response.Listener<EaseResponse<T>>, Response.ErrorListener, NetworkCall<T> {


    /**
     * Either run service call in background or foreground.
     * <p>
     * When run in background, progress dialog is not shown and vice versa.
     */
    private boolean mRunInBackground;


    /**
     * Dialog listener for showing and hiding dialog before network calls.
     */
    private EaseDialog mEaseDialog;

    /**
     * Type of request method
     */
    private int mMethod;

    /**
     * Id for this request. This will be passed back to caller
     * via {@link EaseCallbacks}
     */
    private int mRequestId;

    /**
     * End point for this request
     */
    private String mEndPoint;

    /**
     * Headers for this request, default headers are merged if set.
     *
     * @see EaseConfig
     */
    private RequestHeaders mHeaders;

    /**
     * Body for this request
     */
    private RequestBody mRequestBody;

    /**
     * Type token for parsing network response to specific asString
     */
    private TypeToken<EaseResponse<T>> mTypeToken;

    /**
     * Callbacks for sending back parsed network response
     */
    private EaseCallbacks<T> mResponseCallbacks;

    /**
     * Base request for handling real network call and response parsing
     */
    private EaseBaseRequest<T> mEaseBaseRequest;

    /**
     * Should cache response. If set to -1, fetch flag from configuration.
     *
     * @see EaseConfig
     */

    private int mShouldCache;

    /**
     * Socket timeout for this request. If set to -1, fetch from configuration.
     */
    private int mSocketTimeout;


    private EaseRequest(int requestId, TypeToken<EaseResponse<T>> typeToken, int method, String endpoint, RequestHeaders headers, RequestBody requestBody,
                        EaseCallbacks<T> responseCallbacks, EaseDialog easeDialog, boolean runInBackground, int shouldCache, int socketTimeout) {
        mRequestId = requestId;
        mEndPoint = endpoint;
        mHeaders = headers;
        mMethod = method;
        mShouldCache = shouldCache;
        mRequestBody = requestBody;
        mTypeToken = typeToken;
        mEaseDialog = easeDialog;
        mRunInBackground = runInBackground;
        mResponseCallbacks = responseCallbacks;
        mSocketTimeout = socketTimeout;
    }


    private static <T> EaseRequest<T> newInstance(int requestId, TypeToken<EaseResponse<T>> typeToken, int method, String endpoint, RequestHeaders headers, RequestBody requestBody,
                                                  EaseCallbacks<T> responseCallbacks,
                                                  EaseDialog dialog, boolean runInBackground,
                                                  int shouldCache, int socketTimeout) {
        return new EaseRequest<>(requestId, typeToken, method, endpoint, headers, requestBody, responseCallbacks,
                dialog, runInBackground, shouldCache, socketTimeout);
    }

    /**
     * Get builder for Parametrized type token
     *
     * @param type type token for specific asString
     * @param <T>  type of response
     * @return RequestBuilder for this type
     */
    public static <T> RequestBuilder<T> type(TypeToken<EaseResponse<T>> type) {
        return new RequestBuilder<>(type);
    }

    /**
     * Get builder for non-parametrized type token
     *
     * @param token    non-parametrized string token
     * @param <String> type of response
     * @return RequestBuilder for this type
     */
    public static <String> RequestBuilder<String> asString(Class<String> token) {
        return new RequestBuilder<>(EaseStringType.getType(token));
    }


    @Override
    public EaseRequest<T> execute(Context context) {


        if (!isDeviceOnline(context) && mResponseCallbacks != null) {
            mResponseCallbacks.onError(this, new EaseException("No internet connection."));
            return this;
        }

        final EaseConfig config = EaseUtils.getConfig();

        RequestHeaders defaultHeaders = config.defaultHeaders();

        mHeaders = defaultHeaders != null ? RequestHeaders.newInstance(defaultHeaders) : mHeaders;

        mEaseBaseRequest = EaseBaseRequest.getInstance(mTypeToken, mMethod, config.getBaseUrl() + mEndPoint,
                mHeaders, mRequestBody,
                this, this);
        mEaseBaseRequest.setShouldCache(mShouldCache == -1 ? config.shouldCacheResponse() : mShouldCache == 1);
        showDialog(context);


        int timeout = mSocketTimeout == -1 ? config.socketTimeOut() : mSocketTimeout;

        mEaseBaseRequest.setRetryPolicy(new EaseRetryPolicy(timeout, config.numOfRetries()));

        EaseVolleyWrapper.addRequest(context, mEaseBaseRequest);
        return this;
    }

    /**
     * Retry policy for this request.
     */
    private class EaseRetryPolicy implements RetryPolicy {

        int timeout;
        int retryCount;

        EaseRetryPolicy(int timeout, int retryCount) {
            this.timeout = timeout;
            this.retryCount = retryCount;
        }

        @Override
        public int getCurrentTimeout() {
            // use default 10 sec socketTimeout if socketTimeout is 0.
            return timeout == 0 ? 10000 : timeout;
        }

        @Override
        public int getCurrentRetryCount() {
            return retryCount;
        }

        @Override
        public void retry(VolleyError error) throws VolleyError {

        }
    }

    /**
     * Returns status, if service was running in background or not.
     *
     * @return true - if service was running in background, false otherwise.
     */
    public boolean wasRunningInBackground() {
        return mRunInBackground;
    }

    /**
     * Check internet connectivity.
     *
     * @return true if device is connect to <b>any</b> network, false otherwise.
     */
    private boolean isDeviceOnline(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }


    /**
     * Initialize and show dialog if call is not running in background.
     *
     * @param context activity context for initializing dialog.
     */
    private void showDialog(Context context) {
        if (!mRunInBackground) {
            mEaseDialog = mEaseDialog == null ? mEaseDialog = EaseUtils.getConfig().getDialog().init(context) : mEaseDialog.init(context);
            mEaseDialog.show();
        }
    } // showDialog

    private void hideDialog() {
        if (!mRunInBackground && mEaseDialog != null) {
            mEaseDialog.hide();
        }
    } // hideDialog

    /**
     * Get original base request offer.
     *
     * @return EaseBaseRequest - underlying base request
     */
    @Nullable
    public EaseBaseRequest<T> getBaseRequest() {
        return mEaseBaseRequest;
    }

    @Override
    public void cancel() {
        mEaseBaseRequest.cancel();
    }

    @Override
    public void onResponse(EaseResponse<T> response) {
        hideDialog();

        if (mResponseCallbacks == null) {
            Log.w("ease", "response callbacks are not specified for request [" + mEndPoint + "]");
            return;
        }


        String description = response.getDescription() == null ? "" : response.getDescription();


        if (response.isSuccessful()) {
            mResponseCallbacks.onSuccess(this, description, response.getData());
        } else {
            mResponseCallbacks.onFailure(this, description);
        }

    } // onResponse

    public int method() {
        return mMethod;
    }

    public int id() {
        return mRequestId;
    }

    public boolean idEqualsTo(int id) {
        return mRequestId == id;
    }

    public String endPoint() {
        return mEndPoint;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        hideDialog();
        if (mResponseCallbacks != null) {
            mResponseCallbacks.onError(this, new EaseException(error));
        } else {
            Log.w("ease", "response callbacks are not specified for request [" + mEndPoint + "]");
        }
    } // onErrorResponse


    /**
     * A builder for building requests of asString {@link EaseRequest}
     *
     * @param <K>
     */
    public static class RequestBuilder<K> {

        private int method;
        private int requestId;
        private String endpoint;
        private RequestBody requestBody;
        private RequestHeaders headers;
        private EaseDialog dialog;
        private int shouldCache;
        private int socketTimeout;
        private boolean runInBackground;
        private TypeToken<EaseResponse<K>> typeToken;
        private EaseCallbacks<K> responseCallbacks;

        RequestBuilder(TypeToken<EaseResponse<K>> typeToken) {
            method = Request.Method.GET;
            runInBackground = false;
            this.typeToken = typeToken;
            this.shouldCache = -1; // fall back to default
            this.socketTimeout = -1; // fall back to default
        }

        public RequestBuilder<K> requestId(int requestId) {
            this.requestId = requestId;
            return this;
        }

        public RequestBuilder<K> socketTimeOut(int timeout) {
            this.socketTimeout = timeout;
            return this;
        }


        public RequestBuilder<K> dialog(@Nullable EaseDialog dialog) {
            this.dialog = dialog;
            return this;
        }

        public RequestBuilder<K> runInBackground() {
            this.runInBackground = true;
            return this;
        }

        public RequestBuilder<K> cache() {
            this.shouldCache = 1;
            return this;
        }

        public RequestBuilder<K> noCache() {
            this.shouldCache = 0;
            return this;
        }

        public RequestBuilder<K> body(RequestBody requestBody) {
            this.requestBody = requestBody;
            return this;
        }


        public RequestBuilder<K> headers(RequestHeaders headers) {
            this.headers = headers;
            return this;
        }


        public RequestBuilder<K> endPoint(String endPoint) {
            this.endpoint = endPoint;
            return this;
        }

        public RequestBuilder<K> method(int method) {
            this.method = method;
            return this;
        }

        public RequestMethod<K> method() {
            return new RequestMethod<K>() {
                @Override
                public RequestBuilder<K> get() {
                    method = Request.Method.GET;
                    return RequestBuilder.this;
                }

                @Override
                public RequestBuilder<K> post() {
                    method = Request.Method.POST;
                    return RequestBuilder.this;
                }

                @Override
                public RequestBuilder<K> put() {
                    method = Request.Method.PUT;
                    return RequestBuilder.this;
                }

                @Override
                public RequestBuilder<K> patch() {
                    method = Request.Method.PATCH;
                    return RequestBuilder.this;
                }

                @Override
                public RequestBuilder<K> delete() {
                    method = Request.Method.DELETE;
                    return RequestBuilder.this;
                }

                @Override
                public RequestBuilder<K> head() {
                    method = Request.Method.HEAD;
                    return RequestBuilder.this;
                }

            };
        }


        public RequestBuilder<K> responseCallbacks(EaseCallbacks<K> responseCallbacks) {
            this.responseCallbacks = responseCallbacks;
            return this;
        }


        public NetworkCall<K> build() {
            return EaseRequest.newInstance(requestId, typeToken, method, endpoint, headers,
                    requestBody, responseCallbacks, dialog, runInBackground, shouldCache, socketTimeout);
        }


        public interface RequestMethod<T> {
            RequestBuilder<T> get();

            RequestBuilder<T> post();

            RequestBuilder<T> put();

            RequestBuilder<T> patch();

            RequestBuilder<T> delete();

            RequestBuilder<T> head();
        } // RequestMethod

    } // RequestBuilder


    @Override
    public String toString() {
        return String.format(Locale.US, "\n\nRequest ID: %d\nEndpoint: %s\nRequest Headers:\n%s\nRequest Body:\n%s\n\n",
                mRequestId,
                mEndPoint == null ? "" : mEndPoint,
                mHeaders == null ? "{}" : mHeaders.toString(),
                mRequestBody == null ? "{}" : mRequestBody.toString());
    }
} // EaseRequest
