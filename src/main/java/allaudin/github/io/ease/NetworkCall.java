package allaudin.github.io.ease;

import android.content.Context;

/**
 * A network call which can be executed or canceled.
 *
 * @author M.Allaudin
 * @since 1.0
 */

public interface NetworkCall<T> {

    /**
     * Execute a network call.
     *
     * @param context context for showing dialog or initializing Volley request queue
     */
    EaseRequest<T> execute(Context context);


    /**
     * Cancel this request
     */
    void cancel();

}
