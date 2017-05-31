package allaudin.github.io.ease;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
}
