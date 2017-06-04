package allaudin.github.io.ease;

import android.support.annotation.NonNull;

import com.android.volley.VolleyLog;

import java.util.Locale;

/**
 * Static utility methods for Ease wrapper.
 *
 * @author M. Allaudin
 * @since 1.0
 */

public class EaseUtils {


    private static EaseConfig mInstance;


    private EaseUtils() {
        throw new AssertionError(String.format(Locale.US, "Can't instantiate %s.", EaseUtils.class.getSimpleName()));
    }

    public static void init(EaseConfig easeConfig) {
        mInstance = mInstance == null ? mInstance = easeConfig : mInstance;

        if (mInstance.enableLogging()) {
            VolleyLog.DEBUG = true;
            VolleyLog.TAG = "ease";
        }
    }

    public static EaseConfig getConfig() {
        return mInstance == null ? new EaseDefaultConfig() {
            @NonNull
            @Override
            public String getBaseUrl() {
                return "";
            }
        } : mInstance;
    }


} // EaseUtils
