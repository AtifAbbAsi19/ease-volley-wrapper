package allaudin.github.io.ease;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * Default ease configuration
 *
 * @author M.Allaudin
 */

public abstract class EaseDefaultConfig implements EaseConfig {

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
    public int socketTimeOut() {
        return (int) TimeUnit.SECONDS.toMillis(10);
    }

    @Override
    public int numOfRetries() {
        return 0;
    }

    @Override
    public boolean debugMode() {
        return false;
    }
}
