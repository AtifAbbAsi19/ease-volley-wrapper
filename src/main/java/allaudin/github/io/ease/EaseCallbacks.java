package allaudin.github.io.ease;

import android.support.annotation.NonNull;

/**
 * Presents <em>parsed</em> network response in a clean manner.
 * Each different output type is handled separately.
 * <p>
 * <p>
 * <b>Note:</b> This callback is preferred over {@link com.android.volley.Response.Listener}
 *
 * @param <T> type of this response
 * @author M.Allaudin
 * @see <a href="https://www.w3.org/Protocols/HTTP/HTRESP.html">HTTP Response Codes</a>
 * @since 1.0
 * <p>
 */

public interface EaseCallbacks<T> {

    // TODO: 5/30/2017 update code logic for redirects, typically not required for API calls

    /**
     * Called when response is successful i.e. when {@code statusCode == 200} holds true.
     *
     * @param request     request form which response is parsed
     * @param description description retrieved from response
     * @param data        data retrieved from response, null only if returned from response
     */
    void onSuccess(@NonNull EaseRequest<T> request, @NonNull String description, T data);

    /**
     * Called when server returns status code in range of failure i.e. {@code statusCode >= 400}
     *
     * @param request     request form which response is parsed
     * @param description description retrieved from response
     */
    void onFailure(@NonNull EaseRequest<T> request, @NonNull String description);

    /**
     * Called when an exception occurs while connecting to server.
     *
     * @param request request form which response is parsed
     * @param e       exception wrapping the cause of failure (if available)
     */
    void onError(@NonNull EaseRequest<T> request, @NonNull EaseException e);

    /**
     * Called on background thread, after parsing response and before sending response to UI thread.
     * <p>
     * <b>Note:</b> This will only be called if response is successful and no error occured during parsing.
     *
     * @param request     request for which response is parsed
     * @param description description retrieved from response
     * @param data        data retrieved from response
     */
   /* @WorkerThread
    void onAfterParse(@NonNull EaseRequest<T> request, @NonNull String description, T data);*/

} // EaseCallbacks
