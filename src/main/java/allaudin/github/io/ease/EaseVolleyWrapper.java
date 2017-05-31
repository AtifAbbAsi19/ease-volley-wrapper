package allaudin.github.io.ease;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Locale;

/**
 * Utility class for caching instance of request queue and
 * provide helper methods for inserting requests to queue.
 *
 * @author M.Allaudin
 * @see RequestQueue
 * @since 1.0
 */

class EaseVolleyWrapper {

    private static RequestQueue sRequestQueue;

    private EaseVolleyWrapper() {
        throw new AssertionError(String.format(Locale.US, "Can't instantiate %s.", EaseVolleyWrapper.class));
    }

    /**
     * Utility method for passing in customized {@link RequestQueue}
     *
     * @param requestQueue request queue to be set for this wrapper <em>if it is not set already</em>
     */
    public static synchronized void init(@NonNull RequestQueue requestQueue) {
        if (sRequestQueue == null) {
            sRequestQueue = requestQueue;
        }
    }

    /**
     * Returns cached instance of request queue if available or new instance otherwise.
     *
     * @param context context for request queue
     * @return Volley's default request queue
     */
    @NonNull
    public static synchronized RequestQueue getRequestQueue(@NonNull Context context) {
        return sRequestQueue == null ? sRequestQueue = Volley.newRequestQueue(context.getApplicationContext()) : sRequestQueue;
    } // getRequestQueue


    /**
     * Helper method for add request to Volley request queue.
     *
     * @param context context for volley request queue
     * @param request request to be added in the queue
     * @param <T>     type of response
     */
    static <T> void addRequest(@NonNull Context context, EaseBaseRequest<T> request) {
        getRequestQueue(context).add(request);
    } // addRequest

} // EaseVolleyWrapper
