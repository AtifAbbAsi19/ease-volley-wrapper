package allaudin.github.io.ease.mock;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import allaudin.github.io.ease.EaseConfig;
import allaudin.github.io.ease.EaseDialog;
import allaudin.github.io.ease.RequestHeaders;

/**
 * Created on 5/31/17.
 *
 * @author allaudin
 */

public class MockConfig implements EaseConfig {

    @NonNull
    @Override
    public String getBaseUrl() {
        return null;
    }

    @NonNull
    @Override
    public EaseDialog getDialog() {
        return null;
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
