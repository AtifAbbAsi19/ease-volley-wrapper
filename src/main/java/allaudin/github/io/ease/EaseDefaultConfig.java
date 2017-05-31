package allaudin.github.io.ease;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;

import java.util.concurrent.TimeUnit;

/**
 * Default ease configuration
 *
 * @author M.Allaudin
 */

final class EaseDefaultConfig implements EaseConfig {

    @NonNull
    @Override
    public String getBaseUrl() {
        return "";
    }

    @NonNull
    @Override
    public EaseDialog getDialog() {
        return DefaultEaseDialog.newDialog("Loading ...");
    }

    @Nullable
    @Override
    public RequestHeaders defaultHeaders() {
        return null;
    }

    @Override
    public boolean shouldCacheResponse() {
        return false;
    }

    @Override
    public boolean enableLogging() {
        return false;
    }

    @Override
    public RetryPolicy retryPolicy() {
        return new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return (int) TimeUnit.SECONDS.toMillis(10);
            }

            @Override
            public int getCurrentRetryCount() {
                return 0;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        };
    }
}
